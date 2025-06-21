package br.com.rebeca.ToDoList.Controller;

import br.com.rebeca.ToDoList.Base.BaseController;
import br.com.rebeca.ToDoList.Base.BaseException;
import br.com.rebeca.ToDoList.Service.UsuarioService;
import br.com.rebeca.ToDoList.dto.AtualizarUsuarioDTO;
import br.com.rebeca.ToDoList.Base.BaseResponseDTO;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Log
@RestController
@RequestMapping(value = "usuario")
@CrossOrigin(origins = "*")
@PreAuthorize("hasRole('ROLE_MASTER')")
public class UsuarioController extends BaseController {
    @Autowired
    private UsuarioService usuarioService;

    public static final String OCORREU_UM_ERRO_DESCONHECIDO_CONTATE_O_ADMINISTRADOR_DO_SISTEMA = "Ocorreu um erro desconhecido. Contate o administrador do sistema.";

    @Operation(summary = "Realiza a busca de usuarios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioDTO.class))
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
    public ResponseEntity<BaseResponseDTO> buscarUsuario(
            @Parameter(description = "Buscar usuarios no sistema")
            @RequestBody(required = true) UsuarioDTO usuario,
            Authentication authentication) {
        try {
            Jwt jwt = (Jwt) authentication.getPrincipal();
            String tokenEmail = jwt.getClaim("email");

            return response(HttpStatus.OK, usuarioService.buscarUsuario(usuario), tokenEmail, SUCCESS);
        } catch (BaseException baseException) {
            log.warning(baseException.getMessage());

            return errorWithStatusCode(OCORREU_UM_ERRO_DESCONHECIDO_CONTATE_O_ADMINISTRADOR_DO_SISTEMA, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            log.warning(e.getMessage() + e);

            return errorWithStatusCode(OCORREU_UM_ERRO_DESCONHECIDO_CONTATE_O_ADMINISTRADOR_DO_SISTEMA, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Realiza a criação de usuarios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioDTO.class))
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
    @PostMapping("/cadastro")
    public  ResponseEntity<BaseResponseDTO> cadastrarUsuario(
            @Parameter(description = "Cadastrar novos usuarios no sistema")
            @RequestBody(required = true) UsuarioDTO usuario,
            Authentication authentication){
        try {
            Jwt jwt = (Jwt) authentication.getPrincipal();
            String tokenEmail = jwt.getClaim("email");

            return response(HttpStatus.OK, usuarioService.cadastraUsuario(usuario, tokenEmail), "Usuario cadastrado com sucesso", SUCCESS);
        }catch (BaseException baseException){
            log.warning(baseException.getMessage());

            return errorWithStatusCode(baseException.getMessage(), baseException.getHttpStatus());
        }catch (Exception exception){
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
            @ApiResponse(responseCode = "401", description = "Unauthorized - Usuário não autenticado"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Ação não permitida para este usuário"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Erro inesperado no servidor"),
            @ApiResponse(responseCode = "501", description = "Not Implemented - Funcionalidade ainda não implementada"),
            @ApiResponse(responseCode = "502", description = "Bad Gateway - Erro na comunicação com serviço intermediário"),
            @ApiResponse(responseCode = "503", description = "Service Unavailable - Sistema temporariamente indisponível"),
            @ApiResponse(responseCode = "504", description = "Gateway Timeout - Tempo de resposta excedido")
    })
    @PutMapping("/atualizar")
    public ResponseEntity<BaseResponseDTO> atualizarUsuario(
            @Parameter(description = "Atualização de dados do usuario")
            @RequestBody(required = true) AtualizarUsuarioDTO atualizarUsuario,
            Authentication authentication) {
        try {
            Jwt jwt = (Jwt) authentication.getPrincipal();
            String tokenEmail = jwt.getClaim("email");

            return response(HttpStatus.OK, usuarioService.atualizarUsuario(atualizarUsuario, tokenEmail), "Usuario atualizado com sucesso" ,SUCCESS);
        } catch (BaseException be) {
            log.warning(be.getMessage());

            return errorWithStatusCode(be.getMessage(), be.getHttpStatus());
        } catch (Exception exception) {
            log.warning(exception.getMessage() + exception);

            return errorWithStatusCode(OCORREU_UM_ERRO_DESCONHECIDO_CONTATE_O_ADMINISTRADOR_DO_SISTEMA, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

//@PostMapping("/cadastro")
//public ResponseEntity cadastarUsuario(
//        @RequestParam(required = false)  String nome,
//        @RequestParam(required = false) String email,
//        @RequestParam(required = false) String senha){
//    @RequestParam(required = false) Integer idPedidoHistorico,
//    @RequestParam(required = false) String idPedidoMongo) {
//        BaseResponseDTO baseResponseDTO = new BaseResponseDTO();
//    public  ResponseEntity consultarPedido(@PathVariable Integer numeroPedido){
//        BaseResponseDTO baseResponseDTO = new BaseResponseDTO();
//        try {
//            var dadosConsulta = pedidoService.consultarPedido(numeroPedido);
//            return ResponseEntity.ok(BaseResponseDTO.builder()
//                    .data(dadosConsulta)
//                    .build()
//            );
//        }catch (BusinessException be) {
//            baseResponseDTO.setMessage(be.getMessage());
//            return ResponseEntity.status(be.getHttpStatus()).body(baseResponseDTO);
//        } catch (Exception e){
//            baseResponseDTO.setMessage("Erro interno ao consultar o pedido.");
//            log.error("Erro interno ao consultar número do pedido {}: {}", numeroPedido, e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(baseResponseDTO);
//        }
//    }