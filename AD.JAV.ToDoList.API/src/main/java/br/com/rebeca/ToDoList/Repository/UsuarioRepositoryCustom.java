package br.com.rebeca.ToDoList.Repository;

import br.com.rebeca.ToDoList.dto.AtualizarUsuarioDTO;
import br.com.rebeca.ToDoList.dto.UsuarioDTO;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepositoryCustom {
    void atualizarDadosDeUsuario(AtualizarUsuarioDTO  atualizarUsuarioDTO, String tokenEmail);

    void cadastraUsuario(UsuarioDTO usuarioDTO);
}
