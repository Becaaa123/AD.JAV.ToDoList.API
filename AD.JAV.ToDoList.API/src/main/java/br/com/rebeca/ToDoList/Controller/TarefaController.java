package br.com.rebeca.ToDoList.Controller;

import br.com.rebeca.ToDoList.Base.BaseController;
import br.com.rebeca.ToDoList.Base.BaseResponseDTO;
import br.com.rebeca.ToDoList.Exception.BaseException;
import br.com.rebeca.ToDoList.Service.TarefaService;
import br.com.rebeca.ToDoList.dto.AtualizarUsuarioDTO;
import br.com.rebeca.ToDoList.dto.EditarTarefaDTO;
import br.com.rebeca.ToDoList.dto.TarefaDTO;
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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log
@RestController
@RequestMapping(value = "tarefa")
@CrossOrigin(origins = "*")
public class TarefaController extends BaseController {

    @Autowired
    private TarefaService tarefaService;

    public static final String OCORREU_UM_ERRO_DESCONHECIDO_CONTATE_O_ADMINISTRADOR_DO_SISTEMA = "Ocorreu um erro desconhecido. Contate o administrador do sistema.";

    @Operation(summary = "Realiza a busca de tarefas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = TarefaDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Bad Request - Dados inválidos ou ausentes"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Erro inesperado no servidor"),
    })
    @GetMapping("/buscar/{titulo}")
        public ResponseEntity<List<Object[]>> buscar(
            @Parameter(description = "Buscar tarefa via titulo")
            @PathVariable String titulo) {
        try {
            List<Object[]> tarefa = tarefaService.buscarTarefa(titulo);
            if (tarefa.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return  ResponseEntity.ok(tarefa);
        }catch (Exception exception){
            log.warning("Erro ao buscar tarefa: " + exception.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(summary = "Realiza a criação de tarefas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Bad Request - Dados inválidos ou ausentes"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Erro inesperado no servidor"),
    })
    @PostMapping("/criar")
    public ResponseEntity<BaseResponseDTO> criarTarefa(
            @Parameter(description = "Criar novas tarefas no sistema")
            @RequestBody(required = true) TarefaDTO tarefa) {
        try {
            return response(HttpStatus.OK, tarefaService.criarTarefa(tarefa), "Tarefa cadastrada com sucesso", SUCCESS);
        } catch (BaseException baseException) {
            log.warning(baseException.getMessage());
            return errorWithStatusCode(baseException.getMessage(), baseException.getHttpStatus());
        } catch (Exception exception) {
            log.warning(exception.getMessage() + exception);
            return errorWithStatusCode(OCORREU_UM_ERRO_DESCONHECIDO_CONTATE_O_ADMINISTRADOR_DO_SISTEMA, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Realiza a edição de tarefas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = TarefaDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Bad Request - Dados inválidos ou ausentes"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Erro inesperado no servidor"),
    })
    @PutMapping("/editar")
    public ResponseEntity<BaseResponseDTO> editar(
            @Parameter(description = "Atualizar/editar dados de tarefa existente no sistema")
            @RequestBody(required = true)EditarTarefaDTO editarTarefaDTO) {
        try{
            return response(HttpStatus.OK, tarefaService.editarTarefa(editarTarefaDTO), "Tarefa atualizada com susceso", SUCCESS);
        }catch (BaseException baseException){
            log.warning(baseException.getMessage());

            return errorWithStatusCode(baseException.getMessage(), baseException.getHttpStatus());
        }catch (Exception exception){
            log.warning(exception.getMessage() + exception);

            return errorWithStatusCode(OCORREU_UM_ERRO_DESCONHECIDO_CONTATE_O_ADMINISTRADOR_DO_SISTEMA, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Deleta tarefa pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarefa deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro inesperado no servidor")
    })
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<BaseResponseDTO> deletarPorId(
            @Parameter(description = "ID da tarefa a ser deletada")
            @PathVariable Long id) {
        try {
            tarefaService.deletarTarefa(id);
            return response(HttpStatus.OK, null, "Tarefa deletada com sucesso", "SUCCESS");
        } catch (BaseException e) {
            return errorWithStatusCode(e.getMessage(), e.getHttpStatus());
        } catch (Exception e) {
            log.warning(e.getMessage() + e);
            return errorWithStatusCode("Erro ao deletar tarefa", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
