package br.com.rebeca.ToDoList.Controller;

import br.com.rebeca.ToDoList.Service.JwtService;
import br.com.rebeca.ToDoList.dto.AuthDTO;
import br.com.rebeca.ToDoList.dto.UsuarioDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/usuario")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Operation(summary = "Realiza a autenticação do token")
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
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthDTO authDTO){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authDTO.getEmail(), authDTO.getSenha()));

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authDTO.getEmail());
        final  String jwt = jwtService.generateToken(userDetails);

        return ResponseEntity.ok(Map.of("token", jwt));
    }
}