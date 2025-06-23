package br.com.rebeca.ToDoList.Service;

import br.com.rebeca.ToDoList.Exception.BaseException;
import br.com.rebeca.ToDoList.Model.TarefaModel;
import br.com.rebeca.ToDoList.Repository.TarefaRepository;
import br.com.rebeca.ToDoList.Repository.TarefaRepositoryCustom;
import br.com.rebeca.ToDoList.dto.TarefaDTO;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class TarefaService {

    @Autowired
    private TarefaRepositoryCustom tarefaRepositoryCustom;

    @Autowired
    private TarefaRepository tarefaRepository;

    @Transactional
    public TarefaDTO criarTarefa(TarefaDTO tarefaDTO) {
        log.info("Cadastrando tarefa...");

        try {

            TarefaModel tarefaModel = new TarefaModel();
            tarefaModel.setTitulo(tarefaDTO.getTitulo());

            tarefaRepository.save(tarefaModel);

            log.info("Nova tafera de titulo: " + tarefaModel.getTitulo() + " criada.");
            return tarefaDTO;
        } catch (Exception exception) {
            log.info("ERRO NA CRIAÇÃO DA TAREFA!! " + exception.getMessage());
            throw new BaseException(HttpStatus.BAD_REQUEST, "ERRO AO CADASTRAR TAREFA NO SISTEMA!!");
        }
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