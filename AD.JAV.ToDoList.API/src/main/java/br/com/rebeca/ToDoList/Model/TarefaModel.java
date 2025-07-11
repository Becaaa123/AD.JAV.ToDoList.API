package br.com.rebeca.ToDoList.Model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tarefas")
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TarefaModel {
    private static final long serialVersionUID = -8502585883952542324L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "titulo")
    private  String titulo;

    @Column(name = "descricao")
    private  String descricao;

    @Column(name = "dataLimite")
    private LocalDate dataLimite;

    @Column(name = "categoria")
    private String categoria;

    @Column(name = "status")
    private String status;

    @Column(name = "usuario_id")
    private Long usuario_id;
}