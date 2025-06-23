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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
            tarefaModel.setDescricao(tarefaDTO.getDescricao());

            // Conversão da String para LocalDate
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate data = LocalDate.parse(tarefaDTO.getDataLimite(), formatter);
            tarefaModel.setDataLimite(data);

            tarefaModel.setCategoria(tarefaDTO.getCategoria());
            tarefaModel.setStatus(tarefaDTO.getStatus());
            tarefaModel.setUsuario_id(tarefaDTO.getUsuarioId());

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