package br.com.rebeca.ToDoList.Service;

import br.com.rebeca.ToDoList.Exception.BaseException;
import br.com.rebeca.ToDoList.Model.UsuarioModel;
import br.com.rebeca.ToDoList.Repository.UsuarioRepository;
import br.com.rebeca.ToDoList.Repository.UsuarioRepositoryCustom;
import br.com.rebeca.ToDoList.dto.AtualizarUsuarioDTO;
import br.com.rebeca.ToDoList.dto.UsuarioDTO;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Log4j2
public class UsuarioService {

    @Autowired
    private UsuarioRepositoryCustom usuarioRepositoryCustom;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final String ERRO_ALTERACAO = "Erro na alteração de usuario!";

    @Transactional
    public UsuarioDTO cadastraUsuario(UsuarioDTO usuarioDTO) {
        log.info("Cadastrando usuario...");

        try {
            String senhaHash = passwordEncoder.encode(usuarioDTO.getSenha());
            usuarioDTO.setSenha(senhaHash);

            UsuarioModel usuarioModel = new UsuarioModel();
            usuarioModel.setNome(usuarioDTO.getNome());
            usuarioModel.setEmail(usuarioDTO.getEmail());
            usuarioModel.setSenha_hash(senhaHash);

            usuarioRepository.save(usuarioModel);

            log.info("Novo usuario " + usuarioDTO.getNome() + " criado.");
            return usuarioDTO;
        } catch (Exception exception) {
            log.info("ERRO NA CRIAÇÃO DO USUARIO!! " + exception.getMessage());
            throw new BaseException(HttpStatus.BAD_REQUEST, "ERRO AO CADASTRAR USUARIO NO SISTEMA!! ");
        }
    }

    @Transactional
    public List<Object[]> buscarUsuarioPorId(Long id) {
        List<Object[]> usuario = usuarioRepositoryCustom.buscarUsuario(id); // Chame o método do repositório customizado
        if (usuario.isEmpty()) {
            throw new BaseException(HttpStatus.NOT_FOUND, "Usuário não encontrado!");
        }
        return usuario;
    }

    @Transactional
    public AtualizarUsuarioDTO atualizarUsuario(AtualizarUsuarioDTO atualizarUsuarioDTO) {
        log.info("Alteração de dados do usuario");
        if (atualizarUsuarioDTO.getUsuarioId() == null) {
            log.warn("É necessário informar qual usuario será alterado");
            throw new BaseException(HttpStatus.BAD_REQUEST, "Por favor informe um usuario para ser alterado!!");
        }
        try {
            String senhaHash = passwordEncoder.encode(atualizarUsuarioDTO.getSenha());
            atualizarUsuarioDTO.setSenha(senhaHash);
            usuarioRepositoryCustom.atualizarDadosDeUsuario(atualizarUsuarioDTO);
            log.info("Usuario " + atualizarUsuarioDTO.getUsuarioId() + " atualizado.");
            return atualizarUsuarioDTO;
        } catch (BaseException baseException) {
            log.info(baseException.getMessage());
            throw new BaseException(HttpStatus.BAD_REQUEST, baseException.getMessage());
        } catch (Exception exception) {
            log.info("Erro na atualização: " + exception.getMessage());
            throw new BaseException(HttpStatus.BAD_REQUEST, "Erro ao atualizar usuario.");
        }
    }

    public  void  atualizarDadosUsuario(AtualizarUsuarioDTO atualizarUsuarioDTO){
        UsuarioModel usuarioModel = usuarioRepository.findById(atualizarUsuarioDTO.getUsuarioId())
                .orElseThrow(() -> new BaseException(HttpStatus.BAD_REQUEST, "Usuario não encontrado!"));

        usuarioModel.setNome(atualizarUsuarioDTO.getNome());
        usuarioModel.setEmail(atualizarUsuarioDTO.getEmail());
        usuarioModel.setSenha_hash(atualizarUsuarioDTO.getSenha());
        usuarioModel.setDataAtualizacao(LocalDate.now());

        if (atualizarUsuarioDTO.getSenha() != null) {
            usuarioModel.setSenha_hash(atualizarUsuarioDTO.getSenha());
        }

        usuarioRepository.save(usuarioModel);
    }

    @Transactional
    public void deletarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new BaseException(HttpStatus.NOT_FOUND, "Tarefa não encontrada para deletar");
        }
        usuarioRepository.deleteById(id);
        log.info("Tarefa deletada: id = {}", id);
    }
}
