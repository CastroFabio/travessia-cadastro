package br.applogin.travessia.repository;
/* import org.springframework.data.jpa.repository.Query; */
import org.springframework.data.repository.CrudRepository;

import br.applogin.travessia.model.Usuario;

public interface UsuarioRepository extends CrudRepository <Usuario, Long> {
    Usuario findById(long id);
    Usuario findByEmail(String email);
}
