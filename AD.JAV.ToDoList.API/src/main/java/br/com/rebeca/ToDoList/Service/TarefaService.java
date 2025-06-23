package br.com.rebeca.ToDoList.Service;

import br.com.rebeca.ToDoList.dto.TarefaDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class TarefaService {

    public TarefaDTO criarTarefa(TarefaDTO tarefaDTO){
        TarefaDTO tarefa = new TarefaDTO();
        return tarefa;
    }

    public TarefaDTO editarTarefa(TarefaDTO tarefaDTO){
        TarefaDTO tarefa = new TarefaDTO();
        return tarefa;
    }

    public TarefaDTO buscarTarefa(TarefaDTO tarefaDTO){
        TarefaDTO tarefa = new TarefaDTO();
        return tarefa;
    }

    public TarefaDTO deletarTarefa(TarefaDTO tarefaDTO){
        TarefaDTO tarefa = new TarefaDTO();
        return tarefa;
    }
}