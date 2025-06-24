package br.com.rebeca.ToDoList.Service;

import br.com.rebeca.ToDoList.Exception.BaseException;
import br.com.rebeca.ToDoList.Model.TarefaModel;
import br.com.rebeca.ToDoList.Model.UsuarioModel;
import br.com.rebeca.ToDoList.Repository.TarefaRepository;
import br.com.rebeca.ToDoList.Repository.TarefaRepositoryCustom;
import br.com.rebeca.ToDoList.dto.AtualizarUsuarioDTO;
import br.com.rebeca.ToDoList.dto.EditarTarefaDTO;
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

    @Transactional
    public EditarTarefaDTO editarTarefa(EditarTarefaDTO editarTarefaDTO){
        log.info("Editando informações da tarefa...");
        if (editarTarefaDTO.getId() == null){
            log.warn("É necessário informar qual tarefa será alterada!");
            throw  new BaseException(HttpStatus.BAD_REQUEST, "Por favor digite o titulo da tarefa que deseja alterar");
        }
        try {
            tarefaRepositoryCustom.editaDadosDeTarefa(editarTarefaDTO);
            log.info("Tarefa de titulo: " + editarTarefaDTO.getTitulo() + " atualizada com sucesso!!");

            return editarTarefaDTO;
        }catch (BaseException baseException){
            log.info(baseException.getMessage());
            throw  new BaseException(HttpStatus.BAD_REQUEST, baseException.getMessage());
        }catch (Exception exception){
            log.info("Erro ao atualizar/editar informações de tarefa: " + exception.getMessage());
            throw new BaseException(HttpStatus.BAD_REQUEST, "Erro ao atualizar/editar tarefa!");
        }
    }

    public  void editarDadosdeTarefa(EditarTarefaDTO editarTarefaDTO){

        TarefaModel tarefaModel = tarefaRepository.findById((editarTarefaDTO.getId()))
                .orElseThrow(() -> new BaseException(HttpStatus.BAD_REQUEST, "Tarefa não encontrada"));

        tarefaModel.setTitulo(editarTarefaDTO.getTitulo());
        tarefaModel.setDescricao(editarTarefaDTO.getDescricao());
        tarefaModel.setCategoria(editarTarefaDTO.getCategoria());
        tarefaModel.setStatus(editarTarefaDTO.getStatus());
        tarefaModel.setDataLimite(LocalDate.parse(editarTarefaDTO.getDataLimite()));
        tarefaModel.setUsuario_id(editarTarefaDTO.getUsuarioId());

        tarefaRepository.save(tarefaModel);
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