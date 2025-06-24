package br.com.rebeca.ToDoList.Repository;

import br.com.rebeca.ToDoList.dto.AtualizarUsuarioDTO;
import br.com.rebeca.ToDoList.dto.EditarTarefaDTO;
import br.com.rebeca.ToDoList.dto.TarefaDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TarefaRepositoryCustom {
    void editaDadosDeTarefa(EditarTarefaDTO editarTarefaDTO);

    void criarTarefa(TarefaDTO tarefaDTO);

    List<Object[]> buscarUsuario(Long id);
}
