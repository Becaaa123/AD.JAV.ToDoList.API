package br.com.rebeca.ToDoList.Repository.Impl;

import br.com.rebeca.ToDoList.Exception.BaseException;
import br.com.rebeca.ToDoList.Repository.TarefaRepositoryCustom;
import br.com.rebeca.ToDoList.Util.ConverterUtil;
import br.com.rebeca.ToDoList.dto.EditarTarefaDTO;
import br.com.rebeca.ToDoList.dto.TarefaDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.FlushModeType;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Log4j2
@Repository
public class TarefaRepositoryImplCustom implements TarefaRepositoryCustom {
    @PersistenceContext
    private EntityManager em;

    @Autowired
    private ConverterUtil converterUtil;

    @Transactional
    public List<Object[]> buscarTarefa(String titulo){
        try {
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT ");
            sql.append(" tarefa.id AS idTarefa, ");
            sql.append(" tarefa.titulo AS titulo, ");
            sql.append(" tarefa.descricao AS descricao, ");
            sql.append(" tarefa.data_limite AS dataLimite, ");
            sql.append(" tarefa.categoria AS categoria, ");
            sql.append(" tarefa.status AS status ");
            sql.append(" FROM tarefas tarefa ");
            sql.append(" WHERE LOWER(tarefa.titulo) LIKE LOWER(CONCAT('%', :titulo, '%')) ");
            sql.append(" ORDER BY tarefa.id; ");

            Query query = em.createNativeQuery(sql.toString());
            query.setParameter("titulo", titulo);
            return query.getResultList();
        } catch (NoResultException e) {
            log.warn("Tarefa com titulo " + titulo + " não encontrado.");
            return Collections.emptyList();
        } catch (Exception e) {
            log.error("ERRO: " + e.getMessage(), e);
            throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao buscar tarefa.");
        }
    }

    @Transactional
    public void criarTarefa(TarefaDTO tarefaDTO){
        StringBuilder sql = new StringBuilder();
        sql.append(" INSERT INTO  tarefas(titulo, descricao, dataLimite, categoria, status, usuario_id) ");
        sql.append(" (:titulo, ");
        sql.append(" :descricao, ");
        sql.append(" :dataLimite, ");
        sql.append(" :categoria, ");
        sql.append(" :status, ");
        sql.append(" :usuario_id) ");

        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("titulo", tarefaDTO.getTitulo());
        query.setParameter("descricao", tarefaDTO.getDescricao());
        query.setParameter("dataLimite", tarefaDTO.getDataLimite());
        query.setParameter("categoria", tarefaDTO.getCategoria());
        query.setParameter("status", tarefaDTO.getStatus());
        query.setParameter("usuario_id", tarefaDTO.getUsuarioId());
        query.setFlushMode(FlushModeType.COMMIT);
        query.executeUpdate();
    }

    @Transactional
    public void editaDadosDeTarefa(EditarTarefaDTO editarTarefaDTO){
        StringBuilder sql = new StringBuilder();

        sql.append(" UPDATE tarefas ");
        sql.append(" SET titulo = :titulo, ");
        sql.append(" descricao = :descricao, ");
        sql.append(" data_limite = :data_limite, ");
        sql.append(" categoria = :categoria, ");
        sql.append(" status = :status ");
        sql.append(" WHERE id = :id ");

        try{
            Query query = em.createNativeQuery(sql.toString());

            query.setParameter("titulo", editarTarefaDTO.getTitulo());
            query.setParameter("descricao", editarTarefaDTO.getDescricao());
            query.setParameter("data_limite", editarTarefaDTO.getDataLimite());
            query.setParameter("categoria", editarTarefaDTO.getCategoria());
            query.setParameter("status", editarTarefaDTO.getStatus());
            query.setParameter("id", editarTarefaDTO.getId());
            query.executeUpdate();

            log.info("Tarefa: " + editarTarefaDTO.getTitulo() + " de id " + editarTarefaDTO.getId() + " atualizado com sucesso.");
        }catch (Exception exception){
            log.error("Erro ao atualzar tarefa: " + exception.getMessage(), exception);
            throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao atualizar tarefa.");
        }
    }
}
