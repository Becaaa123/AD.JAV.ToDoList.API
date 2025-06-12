package br.com.rebeca.ToDoList.Model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Usuario")
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UsuarioModel {
    private static final long serialVersionUID = -8502585883952542324L;

    @Id
    @Column(name = "idUsuario", unique = true)
    private Long id;

    @Column(name = "nome")
    private  String nome;

    @Column(name = "email")
    private  String email;

    @Column(name = "senha")
    private String senha;

    @Column(name = "dataCriacao")
    private LocalDateTime data;
}
