package br.applogin.travessia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.applogin.travessia.model.Usuario;
import br.applogin.travessia.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
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
    public String dashboard(Model model, HttpSession session) {
        String nome = (String) session.getAttribute("nomeUsuario");
        if(nome == null) {
            return "redirect:/login";
        }
        model.addAttribute("nome", nome);
        return "index";
    }

    @PostMapping("/logar")
    public String loginUsuario(Usuario usuario, Model model, HttpSession session){
        Usuario usuarioLogado = this.usuarioService.logarUsuario(usuario.getEmail(), usuario.getSenha());

        if(usuarioLogado != null) {
            session.setAttribute("usuarioId", usuarioLogado.getId());
            session.setAttribute("nomeUsuario", usuarioLogado.getNome());
            return "redirect:/";
        }
            
        model.addAttribute("error", "Email ou senha inválidos");
        return "login";
    }

    @GetMapping("/sair")
    public String sair(HttpSession session){
        session.invalidate();
        return "redirect:/login";
    }
    
    @RequestMapping(value="/cadastro", method=RequestMethod.POST)
    public String cadastroUsuario(@Valid Usuario usuario, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "cadastro";
        }
        
        // Validate password length before encryption
        if (usuario.getSenha() == null || usuario.getSenha().length() < 6 || usuario.getSenha().length() > 20) {
            model.addAttribute("error", "Senha deve ter entre 6 e 20 caracteres");
            return "cadastro";
        }
        
        usuarioService.salvarUsuario(usuario);
        return "redirect:/login";
    }
}
