package com.ites.li.practicaregistrodealumnos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.transformation.FilteredList;

public class HelloController implements Initializable {

    /*------------------------------------*/
    // Formulario
    @FXML private TextField txtNoControl;
    @FXML private TextField txtNombre;
    @FXML private TextField txtApellidoP;
    @FXML private TextField txtApellidoM;
    @FXML private TextField txtDomicilio;
    @FXML private DatePicker dpFechaNac;
    @FXML private ComboBox<String> cmbCarrera;
    @FXML private VBox seccFormulario;

    // Sección: Opciones
    @FXML private Button btnAgregar;
    @FXML private Button btnEditar;
    @FXML private Button btnEliminar;

    // filtro y busqueda
    @FXML private TextField txtFiltro;
    @FXML private Button btnBuscar;

    @FXML private VBox seccListadoAlumnos;

    // Tabla
    @FXML private TableView<Alumno> tblAlumnos;
        // columnas
        @FXML private TableColumn<Alumno, String> tblColNoControl;
        @FXML private TableColumn<Alumno, String> tblColNombre;
        @FXML private TableColumn<Alumno, String> tblColApellidoP;
        @FXML private TableColumn<Alumno, String> tblColApellidoM;
        @FXML private TableColumn<Alumno, String> tblColCarrera;

    private ObservableList<Alumno> listaAlumnos = FXCollections.observableArrayList();

    /*------------------------------------*/



    // ACTION EVENT (ON BUTTONS)
    @FXML
    public void OnbtnAgregarClick(ActionEvent actionEvent) {

        // habilitar sección de formulario completo
        seccFormulario.setDisable(false);

        // limpiar formulario
        txtNoControl.clear();
        txtNombre.clear();
        txtApellidoP.clear();
        txtApellidoM.clear();
        txtDomicilio.clear();
        dpFechaNac.setValue(null);
        cmbCarrera.setValue(null);

        // Limpiar campo filtro
        txtFiltro.clear();

        // deshabilita la sección de listado de alumnos
        seccListadoAlumnos.setDisable(true);

        // Deshabilitar editar y eliminar
        btnEditar.setDisable(true);
        btnEliminar.setDisable(true);

        // Cambiar texto y acción del botón
        btnAgregar.setText("Finalizar registro");
        // Cambiar el action del botón al nuevo metodo
        btnAgregar.setOnAction(e -> OnbtnFinalizarRegistroClick());

    }

    /*R-008*/
    public void OnbtnFinalizarRegistroClick() {

        // Alerta de confirmación
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar registro");
        confirmacion.setHeaderText("¿Desea registrar al alumno?");
        confirmacion.setContentText("Presione Aceptar para registrar o Cancelar para cancelar la operación.");

        // Obtener los botones y cambiarles el texto
        ButtonType btnAceptar = new ButtonType("Aceptar");
        ButtonType btnCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);

        // Reemplazar los botones por defecto
        confirmacion.getButtonTypes().setAll(btnAceptar, btnCancelar);

        Optional<ButtonType> resultado = confirmacion.showAndWait();

        // SI ACEPTA
        if (resultado.isPresent() && resultado.get() == btnAceptar) {

            // Validar número de control duplicado
            String noControl = txtNoControl.getText();
            for (Alumno a : tblAlumnos.getItems()) {
                if (a.getNoControl().equals(noControl)) {
                    Alert alerta = new Alert(Alert.AlertType.ERROR);
                    alerta.setTitle("Error");
                    alerta.setHeaderText("Número de control inválido");
                    alerta.setContentText("El número de control ya pertenece a otro alumno.");
                    alerta.showAndWait();
                    return; // Detiene el registro
                }
            }

            // Validar que se haya seleccionado una fecha
            if (dpFechaNac.getValue() == null) {
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setTitle("Error");
                alerta.setHeaderText("Fecha inválida");
                alerta.setContentText("Debe seleccionar una fecha de nacimiento.");
                alerta.showAndWait();
                return;
            }

            // Validar que la fecha no sea mayor a la actual
            LocalDate fechaNac = dpFechaNac.getValue();
            LocalDate hoy = LocalDate.now();

            if (fechaNac.isAfter(hoy)) {
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setTitle("Error");
                alerta.setHeaderText("Fecha inválida");
                alerta.setContentText("La fecha de nacimiento no puede ser mayor a la fecha actual.");
                alerta.showAndWait();
                return;
            }

            // Validar que el alumno tenga al menos 18 años
            if (hoy.getYear() - fechaNac.getYear() < 18) {
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setTitle("Error");
                alerta.setHeaderText("Fecha inválida");
                alerta.setContentText("El alumno debe tener al menos 18 años.");
                alerta.showAndWait();
                return;
            }

            // Todas las validaciones pasaron: registrar alumno
            Alumno alumno = new Alumno();
            alumno.setNoControl(txtNoControl.getText());
            alumno.setNombre(txtNombre.getText());
            alumno.setApellidoP(txtApellidoP.getText());
            alumno.setApellidoM(txtApellidoM.getText());
            alumno.setDomicilio(txtDomicilio.getText());
            alumno.setFechaNac(dpFechaNac.toString());
            alumno.setCarrera(cmbCarrera.getValue());

            listaAlumnos.add(alumno);
            tblAlumnos.setItems(listaAlumnos);
        }

        // TANTO SI ACEPTA COMO SI CANCELA: restablecer pantalla
        seccFormulario.setDisable(true);
        seccListadoAlumnos.setDisable(false);

        txtNoControl.clear();
        txtNombre.clear();
        txtApellidoP.clear();
        txtApellidoM.clear();
        txtDomicilio.clear();
        dpFechaNac.setValue(null);
        cmbCarrera.setValue(null);

        btnEditar.setDisable(true);
        btnEliminar.setDisable(true);
        btnAgregar.setText("Agregar nuevo");
        btnAgregar.setOnAction(e -> OnbtnAgregarClick(null));
    }

    /*R-013*/
    @FXML
    public void OnbtnEditarClick(ActionEvent actionEvent) {

        // Habilitar formulario excepto número de control
        seccFormulario.setDisable(false);
        txtNoControl.setDisable(true);

        // Deshabilitar agregar y eliminar
        btnAgregar.setDisable(true);
        btnEliminar.setDisable(true);

        // Cambiar texto y acción del botón
        btnEditar.setText("Finalizar edición");
        btnEditar.setOnAction(e -> OnbtnFinalizarEdicionClick());
    }

    /*R-014*/
    public void OnbtnFinalizarEdicionClick() {

        // Validar fecha
        if (dpFechaNac.getValue() == null) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("Fecha inválida");
            alerta.setContentText("Debe seleccionar una fecha de nacimiento.");
            alerta.showAndWait();
            return;
        }

        LocalDate fechaNac = dpFechaNac.getValue();
        LocalDate hoy = LocalDate.now();

        if (fechaNac.isAfter(hoy)) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("Fecha inválida");
            alerta.setContentText("La fecha de nacimiento no puede ser mayor a la fecha actual.");
            alerta.showAndWait();
            return;
        }

        if (hoy.getYear() - fechaNac.getYear() < 18) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("Fecha inválida");
            alerta.setContentText("El alumno debe tener al menos 18 años.");
            alerta.showAndWait();
            return;
        }

        // Alerta de confirmación
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar edición");
        confirmacion.setHeaderText("¿Desea aplicar los cambios?");

        ButtonType btnAplicar = new ButtonType("Aplicar cambios");
        ButtonType btnCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
        confirmacion.getButtonTypes().setAll(btnAplicar, btnCancelar);

        Optional<ButtonType> resultado = confirmacion.showAndWait();

        // SI APLICA CAMBIOS
        if (resultado.isPresent() && resultado.get() == btnAplicar) {
            Alumno alumnoSeleccionado = tblAlumnos.getSelectionModel().getSelectedItem();

            alumnoSeleccionado.setNombre(txtNombre.getText());
            alumnoSeleccionado.setApellidoP(txtApellidoP.getText());
            alumnoSeleccionado.setApellidoM(txtApellidoM.getText());
            alumnoSeleccionado.setDomicilio(txtDomicilio.getText());
            alumnoSeleccionado.setFechaNac(fechaNac.toString());
            alumnoSeleccionado.setCarrera(cmbCarrera.getValue());

            // Refrescar tabla
            tblAlumnos.refresh();
        }

        // TANTO SI APLICA COMO SI CANCELA: restablecer pantalla
        seccFormulario.setDisable(true);
        txtNoControl.setDisable(false);

        txtNoControl.clear();
        txtNombre.clear();
        txtApellidoP.clear();
        txtApellidoM.clear();
        txtDomicilio.clear();
        dpFechaNac.setValue(null);
        cmbCarrera.setValue(null);

        tblAlumnos.getSelectionModel().clearSelection();

        btnEditar.setText("Editar");
        btnEditar.setOnAction(e -> OnbtnEditarClick(null));
        btnEditar.setDisable(true);
        btnEliminar.setDisable(true);
        btnAgregar.setDisable(false);
        btnAgregar.setText("Agregar nuevo");
        btnAgregar.setOnAction(e -> OnbtnAgregarClick(null));
    }

    /*R-015*/
    @FXML
    public void OnbtnEliminarClick(ActionEvent actionEvent) {

        Alumno alumnoSeleccionado = tblAlumnos.getSelectionModel().getSelectedItem();

        // Alerta de confirmación con datos del alumno
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText("¿Desea eliminar al siguiente alumno?");
        confirmacion.setContentText(
                "No. Control: " + alumnoSeleccionado.getNoControl() + "\n" +
                        "Nombre: " + alumnoSeleccionado.getNombre() + "\n" +
                        "Apellido paterno: " + alumnoSeleccionado.getApellidoP() + "\n" +
                        "Apellido materno: " + alumnoSeleccionado.getApellidoM()
        );

        ButtonType btnConfirmar = new ButtonType("Eliminar");
        ButtonType btnCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
        confirmacion.getButtonTypes().setAll(btnConfirmar, btnCancelar);

        Optional<ButtonType> resultado = confirmacion.showAndWait();

        // SI CONFIRMA ELIMINACIÓN
        if (resultado.isPresent() && resultado.get() == btnConfirmar) {

            // Eliminar alumno de la lista y la tabla
            listaAlumnos.remove(alumnoSeleccionado);

            // Limpiar y deshabilitar formulario
            seccFormulario.setDisable(true);

            txtNoControl.clear();
            txtNombre.clear();
            txtApellidoP.clear();
            txtApellidoM.clear();
            txtDomicilio.clear();
            dpFechaNac.setValue(null);
            cmbCarrera.setValue(null);

            // Seleccionar el nuevo alumno que queda en la tabla
            if (!listaAlumnos.isEmpty()) {
                tblAlumnos.getSelectionModel().selectFirst();
                Alumno nuevoSeleccionado = tblAlumnos.getSelectionModel().getSelectedItem();

                txtNoControl.setText(nuevoSeleccionado.getNoControl());
                txtNombre.setText(nuevoSeleccionado.getNombre());
                txtApellidoP.setText(nuevoSeleccionado.getApellidoP());
                txtApellidoM.setText(nuevoSeleccionado.getApellidoM());
                txtDomicilio.setText(nuevoSeleccionado.getDomicilio());
                dpFechaNac.setValue(LocalDate.parse(nuevoSeleccionado.getFechaNac()));
                cmbCarrera.setValue(nuevoSeleccionado.getCarrera());
            } else {
                // Si no quedan alumnos deshabilitar editar y eliminar
                btnEditar.setDisable(true);
                btnEliminar.setDisable(true);
            }
        }
    }

    /*R-011*/
    @FXML
    public void OnbtnBuscarClick(ActionEvent actionEvent) {

        String filtro = txtFiltro.getText().toLowerCase();

        FilteredList<Alumno> listaFiltrada = new FilteredList<>(listaAlumnos, alumno -> {
            return alumno.getNoControl().toLowerCase().contains(filtro) ||
                    alumno.getNombre().toLowerCase().contains(filtro) ||
                    alumno.getApellidoP().toLowerCase().contains(filtro) ||
                    alumno.getApellidoM().toLowerCase().contains(filtro) ||
                    alumno.getCarrera().toLowerCase().contains(filtro);
        });

        tblAlumnos.setItems(listaFiltrada);
    }



    // INICIALIZADOR
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> listaDeElementos = cmbCarrera.getItems();

        /*R-005: Estado inicial de botones (opciones) y formulario*/
        seccFormulario.setDisable(true);
        btnAgregar.setDisable(false);
        btnEditar.setDisable(true);
        btnEliminar.setDisable(true);
        btnBuscar.setDisable(true);


        /*R-006: tabla vacia*/
        tblAlumnos.setPlaceholder(new Label("No hay alumnos registrados"));

        /*R-010: Habilitar botón buscar solo cuando el campo no está vacío*/
        txtFiltro.textProperty().addListener((observable, oldValue, newValue) -> {
            btnBuscar.setDisable(newValue.trim().isEmpty());
        });


        /*R-011*/
        txtFiltro.textProperty().addListener((observable, oldValue, newValue) -> {
            btnBuscar.setDisable(newValue.trim().isEmpty());

            // Si el campo se vacía, mostrar todos los alumnos
            if (newValue.trim().isEmpty()) {
                tblAlumnos.setItems(listaAlumnos);
            }
        });

        /* R-012: Seleccionar alumno de la tabla */
        tblAlumnos.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Habilitar botones
                btnEditar.setDisable(false);
                btnEliminar.setDisable(false);

                // Mostrar datos del alumno en el formulario
                txtNoControl.setText(newValue.getNoControl());
                txtNombre.setText(newValue.getNombre());
                txtApellidoP.setText(newValue.getApellidoP());
                txtApellidoM.setText(newValue.getApellidoM());
                txtDomicilio.setText(newValue.getDomicilio());
                dpFechaNac.setValue(LocalDate.parse(newValue.getFechaNac()));
                cmbCarrera.setValue(newValue.getCarrera());
            }
        });

        // columnas (tableview)
        tblColNoControl.setCellValueFactory(new PropertyValueFactory<Alumno, String>("noControl"));
        tblColNombre.setCellValueFactory(new PropertyValueFactory<Alumno, String>("nombre"));
        tblColApellidoP.setCellValueFactory(new PropertyValueFactory<Alumno, String>("apellidoP"));
        tblColApellidoM.setCellValueFactory(new PropertyValueFactory<Alumno, String>("apellidoM"));
        tblColCarrera.setCellValueFactory(new PropertyValueFactory<Alumno, String>("carrera"));

        // combobox carreras
        listaDeElementos.add("Arquitectura");
        listaDeElementos.add("Contador Público");
        listaDeElementos.add("Gastronomía");
        listaDeElementos.add("Ing. Civil");
        listaDeElementos.add("Ing. Electromecánica");
        listaDeElementos.add("Ing. Administración");
        listaDeElementos.add("Ing. en Administración");
        listaDeElementos.add("Ing. En Sistemas Computacionales");
        listaDeElementos.add("Lic. En Turismo");
    }


}
