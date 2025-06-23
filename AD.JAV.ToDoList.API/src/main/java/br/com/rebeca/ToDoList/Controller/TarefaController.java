package br.com.rebeca.ToDoList.Controller;

import br.com.rebeca.ToDoList.Base.BaseController;
import br.com.rebeca.ToDoList.Base.BaseResponseDTO;
import br.com.rebeca.ToDoList.Exception.BaseException;
import br.com.rebeca.ToDoList.Service.TarefaService;
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
import org.springframework.web.bind.annotation.RestController;

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
            @ApiResponse(responseCode = "401", description = "Unauthorized - Usuário não autenticado"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Ação não permitida para este usuário"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Erro inesperado no servidor"),
            @ApiResponse(responseCode = "501", description = "Not Implemented - Funcionalidade ainda não implementada"),
            @ApiResponse(responseCode = "502", description = "Bad Gateway - Erro na comunicação com serviço intermediário"),
            @ApiResponse(responseCode = "503", description = "Service Unavailable - Sistema temporariamente indisponível"),
            @ApiResponse(responseCode = "504", description = "Gateway Timeout - Tempo de resposta excedido")
    })
    @GetMapping("/buscar")
    public ResponseEntity<TarefaDTO> buscar(@RequestBody TarefaDTO tarefa) {
        TarefaDTO buscarTarefa = tarefaService.buscarTarefa(tarefa);
        return ResponseEntity.ok(buscarTarefa);
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
            @ApiResponse(responseCode = "401", description = "Unauthorized - Usuário não autenticado"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Ação não permitida para este usuário"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Erro inesperado no servidor"),
            @ApiResponse(responseCode = "501", description = "Not Implemented - Funcionalidade ainda não implementada"),
            @ApiResponse(responseCode = "502", description = "Bad Gateway - Erro na comunicação com serviço intermediário"),
            @ApiResponse(responseCode = "503", description = "Service Unavailable - Sistema temporariamente indisponível"),
            @ApiResponse(responseCode = "504", description = "Gateway Timeout - Tempo de resposta excedido")
    })
    @PutMapping("/editar")
    public ResponseEntity<TarefaDTO> editar(@RequestBody TarefaDTO tarefa) {
        TarefaDTO editarTarefa = tarefaService.editarTarefa(tarefa);
        return ResponseEntity.ok(editarTarefa);
    }

    @Operation(summary = "Deleta tarefas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = TarefaDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Bad Request - Dados inválidos ou ausentes"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Usuário não autenticado"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Ação não permitida para este usuário"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Erro inesperado no servidor"),
            @ApiResponse(responseCode = "501", description = "Not Implemented - Funcionalidade ainda não implementada"),
            @ApiResponse(responseCode = "502", description = "Bad Gateway - Erro na comunicação com serviço intermediário"),
            @ApiResponse(responseCode = "503", description = "Service Unavailable - Sistema temporariamente indisponível"),
            @ApiResponse(responseCode = "504", description = "Gateway Timeout - Tempo de resposta excedido")
    })
    @DeleteMapping("/deletar")
    public ResponseEntity<TarefaDTO> deletar(@PathVariable TarefaDTO tarefa) {
        TarefaDTO deletarTarefa = tarefaService.deletarTarefa(tarefa);
        return ResponseEntity.ok(deletarTarefa);
    }
}
