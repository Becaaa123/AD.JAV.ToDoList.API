package br.com.rebeca.ToDoList.Repository.Impl;

import br.com.rebeca.ToDoList.Repository.UsuarioRepositoryCustom;
import br.com.rebeca.ToDoList.Util.ConverterUtil;
import br.com.rebeca.ToDoList.dto.AtualizarUsuarioDTO;
import jakarta.persistence.PersistenceContext;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;

@Log4j2
@Repository
public class UsuarioRepositoryImplCustom implements UsuarioRepositoryCustom {
    @PersistenceContext
    private EntityManager em;

    @Autowired
    private ConverterUtil converterUtil;

    public void atualizarDadosUsuario(AtualizarUsuarioDTO atualizarUsuarioDTO, String tokenEmail){
        StringBuilder sql = new StringBuilder();

        sql.append(" UPDATE usuario ");
    }
}
//    @Autowired
//    private ConverterUtil converterUtil;
//
//    @Transactional
//    public void alterarFornecedor(AtualizarFornecedorDTO atualizarFornecedorDTO, String tokenEmail) {
//        StringBuilder sql = new StringBuilder();
//
//        sql.append(" UPDATE t_config_loja SET usuario = :tokenEmail, gfnseq = :fornecedor, data_alteracao = NOW() WHERE fk_loja = :fkloja ");
//
//        Query qry = em.createNativeQuery(sql.toString());
//
//        qry.setParameter("tokenEmail", tokenEmail);
//        qry.setParameter("fornecedor", atualizarFornecedorDTO.getId_fornecedor());
//        qry.setParameter("fkloja", atualizarFornecedorDTO.getId_polo());
//
//        qry.setFlushMode(FlushModeType.COMMIT);
//        qry.executeUpdate();
//    }