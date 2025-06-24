package br.com.rebeca.ToDoList.Repository.Impl;

import br.com.rebeca.ToDoList.Exception.BaseException;
import br.com.rebeca.ToDoList.Exception.BusinessException;
import br.com.rebeca.ToDoList.Repository.UsuarioRepositoryCustom;
import br.com.rebeca.ToDoList.Util.ConverterUtil;
import br.com.rebeca.ToDoList.dto.AtualizarUsuarioDTO;
import br.com.rebeca.ToDoList.dto.UsuarioDTO;
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

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Log4j2
@Repository
public  class UsuarioRepositoryImplCustom implements UsuarioRepositoryCustom {
    @PersistenceContext
    private EntityManager em;

    @Autowired
    private ConverterUtil converterUtil;

    private UsuarioDTO infosUsuario(final  List<Object[]> result){

        if (result.isEmpty()){
            throw new BusinessException("Usuário não encontrado", HttpStatus.NOT_FOUND);
        }

        Object[] linha = result.get(0);

        UsuarioDTO usuario = new UsuarioDTO();

        usuario.setUsuarioId(converterUtil.toLong(linha[1]));
        usuario.setNome(converterUtil.toString(linha[2]));
        usuario.setEmail(converterUtil.toString(linha[3]));
        usuario.setSenha(converterUtil.toString(linha[4]));
        usuario.setDataCriacao(converterUtil.toString(linha[5]));
        usuario.setDataAtualizacao(converterUtil.toString(linha[6]));

        return usuario;
    }

    @Transactional
    public List<Object[]> buscarUsuario(Long id) {
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT ");
            sql.append(" usuario.id AS idUsuario, ");
            sql.append(" usuario.nome AS nome, ");
            sql.append(" usuario.email AS email, ");
            sql.append(" usuario.senha_hash AS senha, ");
            sql.append(" usuario.data_criacao AS dataCriacao, ");
            sql.append(" usuario.data_atualizacao AS data_atualizacao ");
            sql.append(" FROM usuario ");
            sql.append(" WHERE usuario.id = :id");

            Query query = em.createNativeQuery(sql.toString());
            query.setParameter("id", id);
            return query.getResultList();
        } catch (NoResultException e) {
            log.warn("Usuário com id " + id + " não encontrado.");
            return Collections.emptyList();
        } catch (Exception e) {
            log.error("Erro ao buscar usuário: " + e.getMessage(), e);
            throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao buscar usuário.");
        }
    }

    @Transactional
    public void cadastraUsuario(UsuarioDTO usuarioDTO){
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
    public void atualizarDadosDeUsuario(AtualizarUsuarioDTO atualizarUsuarioDTO){
        StringBuilder sql = new StringBuilder();

        sql.append(" UPDATE usuario ");
        sql.append(" SET nome = :nome, ");
        sql.append(" email = :email, ");

        if(atualizarUsuarioDTO.getSenha() != null){
            sql.append(" senha_hash = :senha_hash, ");
        }

        sql.append(" data_atualizacao = :data_atualizacao ");
        sql.append(" WHERE id = :usuarioId ");

        try{
            Query query = em.createNativeQuery(sql.toString());

            query.setParameter("nome", atualizarUsuarioDTO.getNome());
            query.setParameter("email", atualizarUsuarioDTO.getEmail());
            query.setParameter("data_atualizacao", LocalDateTime.now());
            query.setParameter("usuarioId", atualizarUsuarioDTO.getUsuarioId());

            if(atualizarUsuarioDTO.getSenha() != null){
                query.setParameter("senha_hash", atualizarUsuarioDTO.getSenha());
            }

            query.executeUpdate();
            log.info("Usuário " + atualizarUsuarioDTO.getUsuarioId() + " atualizado por " + atualizarUsuarioDTO.getEmail() + ".");
        }catch (Exception exception){
            log.error("Erro ao atualzar usuário: " + exception.getMessage(), exception);
            throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao atualizar usuário.");
        }
    }
}