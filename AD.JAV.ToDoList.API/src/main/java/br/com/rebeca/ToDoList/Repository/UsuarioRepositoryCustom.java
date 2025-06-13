package br.com.rebeca.ToDoList.Repository;

import br.com.rebeca.ToDoList.dto.AtualizarUsuarioDTO;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepositoryCustom {
    void atualizarDadosDeUsuario(AtualizarUsuarioDTO  atualizarUsuarioDTO, String tokenEmail);
}
