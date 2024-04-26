package ulloa.tareas.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import ulloa.tareas.modelo.Tarea;

public interface ITareaRepositorio extends JpaRepository<Tarea, Integer> {
}
