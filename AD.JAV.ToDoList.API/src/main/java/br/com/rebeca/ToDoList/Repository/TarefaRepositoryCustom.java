package br.com.rebeca.ToDoList.Repository;

import br.com.rebeca.ToDoList.dto.AtualizarUsuarioDTO;
import br.com.rebeca.ToDoList.dto.TarefaDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TarefaRepositoryCustom {
    void atualizarDadosDeUsuario(AtualizarUsuarioDTO  atualizarUsuarioDTO);

    void criarTarefa(TarefaDTO tarefaDTO);

    List<Object[]> buscarUsuario(Long id);
}
