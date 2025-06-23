package br.com.rebeca.ToDoList.Repository;

import br.com.rebeca.ToDoList.Model.TarefaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TarefaRepository extends  JpaRepository<TarefaModel, Long> {
    Optional<TarefaModel> findById(Long id);
}