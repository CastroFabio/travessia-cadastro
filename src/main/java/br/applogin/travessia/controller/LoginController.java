package br.applogin.travessia.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.applogin.travessia.model.Usuario;
import br.applogin.travessia.service.CookieService;
import br.applogin.travessia.service.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;




@Controller
public class LoginController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/cadastro")
    public String cadastro(){
        return "cadastro";
    }


    @GetMapping("/")
    public String dashboard(Model model, HttpServletRequest request) throws UnsupportedEncodingException {
        model.addAttribute("nome", CookieService.getCookie(request, "nomeUsuario"));
        return "index";
    }


    @PostMapping("/logar")
    public String loginUsuario(Usuario usuario, Model model, HttpServletResponse response) throws UnsupportedEncodingException{
        Usuario usuarioLogado = this.usuarioService.logarUsuario(usuario.getEmail(), usuario.getSenha());

        if(usuarioLogado != null)
        {
            CookieService.setCookie(response, "usuarioId", String.valueOf(usuarioLogado.getId()),10000);
            CookieService.setCookie(response, "nomeUsuario", String.valueOf(usuarioLogado.getNome()),10000);
            return "redirect:/";
        }
            
        model.addAttribute("error", "Email ou senha inválidos");
        return "login";
    }


    @GetMapping("/sair")
    public String sair(HttpServletResponse response) throws UnsupportedEncodingException{
        CookieService.setCookie(response, "usuarioId", "",0);
        return "login";
    }
    

    @RequestMapping(value="/cadastro", method=RequestMethod.POST)
    public String cadastroUsuario(@Valid Usuario usuario, BindingResult result) {
        if (result.hasErrors()) {
            return "redirect:/cadastro";
        }
        usuarioService.salvarUsuario(usuario);
        return "redirect:/login";
    }
    
}
