package todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import todo.model.Tarefa;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {

}