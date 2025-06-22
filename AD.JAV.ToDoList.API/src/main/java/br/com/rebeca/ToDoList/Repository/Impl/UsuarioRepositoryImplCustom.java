package br.com.rebeca.ToDoList.Repository.Impl;

import br.com.rebeca.ToDoList.Base.BaseException;
import br.com.rebeca.ToDoList.Repository.UsuarioRepositoryCustom;
import br.com.rebeca.ToDoList.Util.ConverterUtil;
import br.com.rebeca.ToDoList.dto.AtualizarUsuarioDTO;
import br.com.rebeca.ToDoList.dto.UsuarioDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.FlushModeType;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Log4j2
@Repository
public  class UsuarioRepositoryImplCustom implements UsuarioRepositoryCustom {
    @PersistenceContext
    private EntityManager em;

    @Autowired
    private ConverterUtil converterUtil;

    @Transactional
    public void cadastraUsuario(UsuarioDTO usuarioDTO) {
        StringBuilder sql = new StringBuilder();
        sql.append(" INSERT INTO usuario (nome, email, senha_hash) VALUES ");
        sql.append(" (:nome, ");
        sql.append(" :email, ");
        sql.append(" :senha_hash) ");

        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("nome", usuarioDTO.getNome());
        query.setParameter("email", usuarioDTO.getEmail());
        query.setParameter("senha_hash", usuarioDTO.getSenha());
        query.setFlushMode(FlushModeType.COMMIT);
        query.executeUpdate();
    }

    @Transactional
    public void atualizarDadosDeUsuario(AtualizarUsuarioDTO atualizarUsuarioDTO, String tokenEmail){
        StringBuilder sql = new StringBuilder();

        sql.append(" UPDATE usuario ");
        sql.append(" SET nome = :nome, ");
        sql.append(" email = :email, ");

        if(atualizarUsuarioDTO.getSenha() != null){
            sql.append(" senha_hash = :senha_hash, ");
        }

        sql.append(" dataAtualizacao = :dataAtualizacao ");
        sql.append(" WHERE id = :usuarioId ");

        try{
            Query query = em.createNativeQuery(sql.toString());

            query.setParameter("nome", atualizarUsuarioDTO.getNome());
            query.setParameter("email", atualizarUsuarioDTO.getEmail());
            query.setParameter("dataAtualizacao", LocalDateTime.now());
            query.setParameter("usuarioId", atualizarUsuarioDTO.getUsuarioId());

            if(atualizarUsuarioDTO.getSenha() != null){
                query.setParameter("senha_hash", atualizarUsuarioDTO.getSenha());
            }

            query.executeUpdate();
            log.info("Usuário " + atualizarUsuarioDTO.getUsuarioId() + " atualizado por " + tokenEmail + ".");
        }catch (Exception exception){
            log.error("Erro ao atualzar usuário: " + exception.getMessage(), exception);
            throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao atualizar usuário.");
        }
    }
}