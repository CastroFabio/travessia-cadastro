package br.applogin.travessia.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.applogin.travessia.model.Usuario;
import br.applogin.travessia.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public void salvarUsuario(Usuario usuario) {
        
        String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCriptografada);
        
        usuarioRepository.save(usuario);
    }

    public Usuario logarUsuario(String email, String senha){
        Usuario usuario = this.usuarioRepository.findByEmail(email);
        if(passwordEncoder.matches(senha, usuario.getSenha())) {
            return usuario;
        }
        return null;
    }




}