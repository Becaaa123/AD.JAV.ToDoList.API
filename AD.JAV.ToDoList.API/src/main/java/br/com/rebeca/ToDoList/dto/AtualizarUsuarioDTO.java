package br.com.rebeca.ToDoList.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AtualizarUsuarioDTO {
    private Long usuarioId;
    private String nome;
    private String email;
    private String senha;
    private String dataAtualizacao;
}