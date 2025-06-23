package br.com.rebeca.ToDoList.Repository;

import br.com.rebeca.ToDoList.dto.AtualizarUsuarioDTO;
import br.com.rebeca.ToDoList.dto.UsuarioDTO;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepositoryCustom {
    void atualizarDadosDeUsuario(AtualizarUsuarioDTO  atualizarUsuarioDTO);

    void cadastraUsuario(UsuarioDTO usuarioDTO);
}
