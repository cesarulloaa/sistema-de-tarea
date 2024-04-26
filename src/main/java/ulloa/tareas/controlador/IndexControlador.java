package ulloa.tareas.controlador;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ulloa.tareas.modelo.Tarea;
import ulloa.tareas.servicio.TareaServicio;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.*;
@Component
public class IndexControlador implements Initializable {

    private static final Logger logger = LoggerFactory.getLogger(IndexControlador.class);
    @Autowired
    private TareaServicio tareaServicio;

    @FXML
    private TableView<Tarea> tareaTabla;

    @FXML
    private TableColumn<Tarea, Integer> idTareaColumna;

    @FXML
    private TableColumn<Tarea, String> nombreTareaColumna;


    @FXML
    private TableColumn<Tarea, String> responsableColumna;

    @FXML
    private TableColumn<Tarea, String> estatusColumna;

    private final ObservableList<Tarea> tareaList = FXCollections.observableArrayList();
    @FXML
    private TextField nombreTareaTexto;
    @FXML
    private TextField responsableTexto;
    @FXML
    private TextField estatusTexto;

    private Integer idTareaInterno;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tareaTabla.getSelectionModel().setSelectionMode(SelectionMode.SINGLE); // Solo se puede seleccionar una tarea a la vez
        configurarColumnas(); // Configura las columnas de la tabla
        listarTareas(); // Lee las tareas del servicio y las muestra en la tabla
    }

    private void configurarColumnas() {
        // Enlaza la columna ID con la propiedad idTarea del objeto Tarea
        idTareaColumna.setCellValueFactory(new PropertyValueFactory<>("idTarea"));
        // Enlaza la columna nombre con la propiedad nombreTarea del objeto Tarea
        nombreTareaColumna.setCellValueFactory(new PropertyValueFactory<>("nombreTarea"));
        // Enlaza la columna responsable con la propiedad responsable del objeto Tarea
        responsableColumna.setCellValueFactory(new PropertyValueFactory<>("responsable"));
        // Enlaza la columna estado con la propiedad estatus del objeto Tarea
        estatusColumna.setCellValueFactory(new PropertyValueFactory<>("estatus"));
    }

    private void listarTareas() {
        logger.info("Ejecutando listado de tareas"); // Registra un mensaje informativo
        tareaList.clear(); // Limpia la lista de tareas
        tareaList.addAll(tareaServicio.listarTareas()); // Obtiene las tareas del servicio y las agrega a la lista
        tareaTabla.setItems(tareaList); // Establece la lista observable como los elementos de la tabla
    }

    public void agregarTarea() {
        if (nombreTareaTexto.getText().isEmpty()) {
            mostrarMensaje("Error de validacion!", "Debe proporcionar una tarea"); // Muestra un mensaje de error
            nombreTareaTexto.requestFocus(); // Solicita el foco para el campo de texto del nombre
            return;
        } else {
            var tarea = new Tarea(); // Crea una nueva instancia de Tarea
            recolectarDatosDelFormulario(tarea); // Obtiene los datos del formulario y los asigna a la tarea
            tarea.setIdTarea(null); // Establece el ID de la tarea como null (para una nueva tarea)
            tareaServicio.guardarTarea(tarea); // Guarda la tarea en el servicio
            mostrarMensaje("Información", "Tarea agregada"); // Muestra un mensaje de información
            limpiarFormulario(); // Limpia el formulario
            listarTareas(); // Actualiza la lista de tareas
        }
    }

    public void cargarTareaFormulario() {
        var tarea = tareaTabla.getSelectionModel().getSelectedItem();
        if (tarea!=null) {
            idTareaInterno = tarea.getIdTarea();
            nombreTareaTexto.setText(tarea.getNombreTarea());
            responsableTexto.setText(tarea.getResponsable());
            estatusTexto.setText(tarea.getEstatus());
        }
    }
    private void recolectarDatosDelFormulario(Tarea tarea) {
        if (idTareaInterno != null) {
            tarea.setIdTarea(idTareaInterno);
        }
        tarea.setNombreTarea(nombreTareaTexto.getText());
        tarea.setResponsable(responsableTexto.getText());
        tarea.setEstatus(estatusTexto.getText());
    }

    public void modificarTarea() {
        if (idTareaInterno == null) {
            mostrarMensaje("Información", "Debe seleccionar una tarea.");
            return;
        }
        if (nombreTareaTexto.getText().isEmpty()) {
            mostrarMensaje("Error de validación", "Debe proporcionar una tarea");
            nombreTareaTexto.requestFocus();
            return;
        }

        var tarea = new Tarea();
        recolectarDatosDelFormulario(tarea);
        tareaServicio.guardarTarea(tarea);
        mostrarMensaje("Información", "Tarea modificada");
        limpiarFormulario();
        listarTareas();
    }

    public void eliminarTarea() {
        if (idTareaInterno == null) {
            mostrarMensaje("Información", "Debe seleccionar una tarea.");
            return;
        }
        if (nombreTareaTexto.getText().isEmpty()) {
            mostrarMensaje("Error de validación", "Debe proporcionar una tarea");
            nombreTareaTexto.requestFocus();
            return;
        }
        var tareaObj = new Tarea();
        recolectarDatosDelFormulario(tareaObj);
        tareaServicio.eliminarTarea(tareaObj);
        mostrarMensaje("Información", "Tarea eliminada.");
        limpiarFormulario();
        listarTareas();

    }

    public void limpiarFormulario() {
        idTareaInterno = null;
        nombreTareaTexto.clear();
        responsableTexto.clear();
        estatusTexto.clear();
    }

    private void mostrarMensaje(String titulo, String  mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();

    }
}
