package br.com.rebeca.ToDoList.Service;

import br.com.rebeca.ToDoList.Base.BaseException;
import br.com.rebeca.ToDoList.Model.UsuarioModel;
import br.com.rebeca.ToDoList.Repository.UsuarioRepository;
import br.com.rebeca.ToDoList.Repository.UsuarioRepositoryCustom;
import br.com.rebeca.ToDoList.dto.AtualizarUsuarioDTO;
import br.com.rebeca.ToDoList.dto.UsuarioDTO;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Log4j2
public class UsuarioService {

    @Autowired
    private UsuarioRepositoryCustom usuarioRepositoryCustom;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private final String ERRO_ALTERACAO = "Erro na alteração de usuario!";

    public UsuarioDTO cadastraUsuario(UsuarioDTO usuarioDTO){
        UsuarioDTO usuario = new UsuarioDTO();
        return usuario;
    }

    @Transactional
    public AtualizarUsuarioDTO atualizarUsuario(AtualizarUsuarioDTO atualizarUsuarioDTO, String tokenEmail){
        log.info("Alteração de dados do usuario");

        if (atualizarUsuarioDTO.getUsuarioId() == null){
            log.warn("É necessario informar qual usuario será alterado");
            throw  new BaseException(HttpStatus.BAD_REQUEST, "Por favor informe um usuario para ser alterado!!");
        }try {
            usuarioRepositoryCustom.atualizarDadosUsuario(atualizarUsuarioDTO, tokenEmail);

            log.info("Usuario " + atualizarUsuarioDTO.getUsuarioId() + " atualizado por " + tokenEmail + ".");

            return atualizarUsuarioDTO;
        }catch (BaseException baseException){
            log.info(baseException.getMessage());
            throw new BaseException(HttpStatus.BAD_REQUEST, baseException.getMessage());
        }catch (Exception exception){
            log.info(ERRO_ALTERACAO + exception.getMessage());
            throw new BaseException(HttpStatus.BAD_REQUEST, ERRO_ALTERACAO);
        }
    }

    public  void  atualizarDadosUsuario(AtualizarUsuarioDTO atualizarUsuarioDTO, String tokenEmail){
        UsuarioModel usuarioModel = usuarioRepository.findById(atualizarUsuarioDTO.getUsuarioId())
                .orElseThrow(() -> new BaseException(HttpStatus.BAD_REQUEST, "Usuario não encontrado!"));

        usuarioModel.setNome(atualizarUsuarioDTO.getNome());
        usuarioModel.setEmail(atualizarUsuarioDTO.getEmail());
        usuarioModel.setSenha(atualizarUsuarioDTO.getSenha());
        usuarioModel.setData(LocalDateTime.now());

        usuarioRepository.save(usuarioModel);
    }

    public UsuarioDTO buscarUsuario(UsuarioDTO usuario){
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        return usuarioDTO;
    }
}
