package ulloa.tareas.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ulloa.tareas.modelo.Tarea;
import ulloa.tareas.repositorio.ITareaRepositorio;

import java.util.List;

/**
 * Clase que implementa la interfaz ITareaServicio, esta clase contiene los métodos CRUD para las tareas.
 *
 * @author Cesar
 */
@Service
public class TareaServicio implements ITareaServicio {

    @Autowired
    private ITareaRepositorio tareaRepositorio;

    /**
     * Método que devuelve una lista con todas las tareas.
     *
     * @return Lista de tareas
     */
    @Override
    public List<Tarea> listarTareas() {
        return tareaRepositorio.findAll();
    }

    /**
     * Método que busca una tarea por su ID.
     *
     * @param idTarea Identificador de la tarea a buscar
     * @return Tarea encontrada o null si no se encuentra
     */
    @Override
    public Tarea buscarTareaPorId(Integer idTarea) {
        return tareaRepositorio.findById(idTarea).orElse(null);
    }

    /**
     * Método que guarda una tarea.
     *
     * @param tarea Tarea a guardar
     */
    @Override
    public void guardarTarea(Tarea tarea) {
        tareaRepositorio.save(tarea);
    }

    /**
     * Método que elimina una tarea.
     *
     * @param tarea Tarea a eliminar
     */
    @Override
    public void eliminarTarea(Tarea tarea) {
        tareaRepositorio.delete(tarea);
    }
}
