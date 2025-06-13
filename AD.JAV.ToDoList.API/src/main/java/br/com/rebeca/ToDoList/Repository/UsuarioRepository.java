package br.com.rebeca.ToDoList.Repository;

import br.com.rebeca.ToDoList.Model.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends  JpaRepository<UsuarioModel, Long> {
    Optional<UsuarioModel> findById(Long id);
}