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
public class EditarTarefaDTO {

    private Long id;
    private String titulo;
    private String descricao;
    private String dataLimite;
    private String categoria;
    private String status;
    private Long usuarioId;
}
