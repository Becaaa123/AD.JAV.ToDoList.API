package br.com.rebeca.ToDoList.Controller;

import br.com.rebeca.ToDoList.Base.BaseController;
import br.com.rebeca.ToDoList.Base.BaseResponseDTO;
import br.com.rebeca.ToDoList.Exception.BaseException;
import br.com.rebeca.ToDoList.Service.UsuarioService;
import br.com.rebeca.ToDoList.dto.AtualizarUsuarioDTO;
import br.com.rebeca.ToDoList.dto.UsuarioDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log
@RestController
@RequestMapping(value = "usuario")
@CrossOrigin(origins = "*")
@PreAuthorize("hasRole('ROLE_MASTER')")
public class UsuarioController extends BaseController {
    @Autowired
    private UsuarioService usuarioService;

    public static final String OCORREU_UM_ERRO_DESCONHECIDO_CONTATE_O_ADMINISTRADOR_DO_SISTEMA = "Ocorreu um erro desconhecido. Contate o administrador do sistema.";

    @Operation(summary = "Busca um usuário pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/buscar/{id}")
    public ResponseEntity<List<Object[]>> buscarUsuarioPorId(
            @Parameter(description = "ID do usuário a ser buscado")
            @PathVariable Long id) {
        try {
            List<Object[]> usuario = usuarioService.buscarUsuarioPorId(id);
            if (usuario.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            log.warning("Erro ao buscar usuário: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(summary = "Realiza a criação de usuarios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Bad Request - Dados inválidos ou ausentes"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Erro inesperado no servidor"),
    })
    @PostMapping("/cadastro")
    public ResponseEntity<BaseResponseDTO> cadastrarUsuario(
            @Parameter(description = "Cadastrar novos usuarios no sistema")
            @RequestBody(required = true) UsuarioDTO usuario) {
        try {
            return response(HttpStatus.OK, usuarioService.cadastraUsuario(usuario), "Usuario cadastrado com sucesso", SUCCESS);
        } catch (BaseException baseException) {
            log.warning(baseException.getMessage());
            return errorWithStatusCode(baseException.getMessage(), baseException.getHttpStatus());
        } catch (Exception exception) {
            log.warning(exception.getMessage() + exception);
            return errorWithStatusCode(OCORREU_UM_ERRO_DESCONHECIDO_CONTATE_O_ADMINISTRADOR_DO_SISTEMA, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Realiza a atualização de usuarios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Bad Request - Dados inválidos ou ausentes"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Erro inesperado no servidor"),
    })
    @PutMapping("/atualizar")
    public ResponseEntity<BaseResponseDTO> atualizarUsuario(
            @Parameter(description = "Atualização de dados do usuario")
            @RequestBody(required = true) AtualizarUsuarioDTO atualizarUsuario) {
        try {
            return response(HttpStatus.OK, usuarioService.atualizarUsuario(atualizarUsuario), "Usuario atualizado com sucesso", SUCCESS);
        } catch (BaseException be) {
            log.warning(be.getMessage());
            return errorWithStatusCode(be.getMessage(), be.getHttpStatus());
        } catch (Exception exception) {
            log.warning(exception.getMessage() + exception);
            return errorWithStatusCode(OCORREU_UM_ERRO_DESCONHECIDO_CONTATE_O_ADMINISTRADOR_DO_SISTEMA, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}