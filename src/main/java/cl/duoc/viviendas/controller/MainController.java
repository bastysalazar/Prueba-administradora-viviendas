package cl.duoc.viviendas.controller;

import cl.duoc.viviendas.model.Casa;
import cl.duoc.viviendas.model.Departamento;
import cl.duoc.viviendas.model.Vivienda;
import cl.duoc.viviendas.persistence.ViviendaRepository;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class MainController {

    @FXML private ComboBox<String> cmbTipo;
    @FXML private TextField txtCodigo;
    @FXML private TextField txtSuperficie;
    @FXML private TextField txtHabitaciones;
    @FXML private TextField txtPiso;
    @FXML private CheckBox chkGastoComun;
    @FXML private CheckBox chkEstacionamiento;
    @FXML private CheckBox chkPatio;
    @FXML private Button btnRegistrar;
    @FXML private Button btnEliminar;
    @FXML private Label lblMensaje;

    @FXML private TableView<Vivienda> tblViviendas;
    @FXML private TableColumn<Vivienda, String> colTipo;
    @FXML private TableColumn<Vivienda, String> colCodigo;
    @FXML private TableColumn<Vivienda, Double> colSuperficie;
    @FXML private TableColumn<Vivienda, Integer> colHabitaciones;
    @FXML private TableColumn<Vivienda, String> colCosto;

    private final ViviendaRepository repository = new ViviendaRepository();

    @FXML
    public void initialize() {
        cmbTipo.getItems().addAll("Departamento", "Casa");
        cmbTipo.setOnAction(e -> actualizarCamposSegunTipo());
        actualizarCamposSegunTipo();

        tblViviendas.setItems(repository.getViviendas());
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colSuperficie.setCellValueFactory(new PropertyValueFactory<>("superficie"));
        colHabitaciones.setCellValueFactory(new PropertyValueFactory<>("habitaciones"));
        colCosto.setCellValueFactory(celda ->
                new SimpleStringProperty("$" + String.format("%,.0f", celda.getValue().calcularCostoArriendo())));
    }

    private void actualizarCamposSegunTipo() {
        String tipo = cmbTipo.getValue();

        if ("Departamento".equals(tipo)) {
            txtPiso.setDisable(false);
            chkGastoComun.setDisable(false);
            chkEstacionamiento.setDisable(false);
            chkPatio.setDisable(true);
        } else if ("Casa".equals(tipo)) {
            txtPiso.setDisable(true);
            chkGastoComun.setDisable(true);
            chkEstacionamiento.setDisable(true);
            chkPatio.setDisable(false);
        } else {
            txtPiso.setDisable(true);
            chkGastoComun.setDisable(true);
            chkEstacionamiento.setDisable(true);
            chkPatio.setDisable(true);
        }
    }

    @FXML
    private void onRegistrar() {
        String tipo = cmbTipo.getValue();
        if (tipo == null) {
            lblMensaje.setText("Debe seleccionar un tipo de vivienda.");
            return;
        }

        String codigo = txtCodigo.getText().trim();
        if (codigo.isEmpty()) {
            lblMensaje.setText("El código de propiedad no puede estar vacío.");
            return;
        }

        double superficie;
        try {
            superficie = Double.parseDouble(txtSuperficie.getText().trim());
        } catch (NumberFormatException e) {
            lblMensaje.setText("La superficie debe ser un número (acepta decimales).");
            return;
        }
        if (superficie < 20 || superficie > 500) {
            lblMensaje.setText("La superficie debe estar entre 20 y 500 metros cuadrados.");
            return;
        }

        int habitaciones;
        try {
            habitaciones = Integer.parseInt(txtHabitaciones.getText().trim());
        } catch (NumberFormatException e) {
            lblMensaje.setText("Las habitaciones deben ser un número entero.");
            return;
        }
        if (habitaciones <= 0) {
            lblMensaje.setText("El número de habitaciones debe ser mayor que cero.");
            return;
        }

        int piso = 0;
        if ("Departamento".equals(tipo)) {
            try {
                piso = Integer.parseInt(txtPiso.getText().trim());
            } catch (NumberFormatException e) {
                lblMensaje.setText("El piso debe ser un número entero.");
                return;
            }
            if (piso <= 0) {
                lblMensaje.setText("El piso debe ser mayor que cero.");
                return;
            }
        }

        try {
            Vivienda vivienda;

            if ("Departamento".equals(tipo)) {
                Departamento depto = new Departamento(codigo, superficie, habitaciones, piso,
                        chkGastoComun.isSelected());
                if (chkEstacionamiento.isSelected()) {
                    depto.asignarEstacionamiento();
                }
                vivienda = depto;
            } else {
                vivienda = new Casa(codigo, superficie, habitaciones, chkPatio.isSelected());
            }

            repository.agregar(vivienda);
            lblMensaje.setText(codigo + " (" + tipo + ") Registrada correctamente.");
            limpiarFormulario();

        } catch (IllegalArgumentException e) {
            lblMensaje.setText(e.getMessage());
        }
    }

    @FXML
    private void onEliminar() {
        Vivienda seleccionada = tblViviendas.getSelectionModel().getSelectedItem();

        if (seleccionada == null) {
            lblMensaje.setText("Seleccionar una vivienda de la tabla para eliminarla");
            return;
        }
        repository.eliminar(seleccionada);
        lblMensaje.setText(seleccionada.getCodigo() + " (" + seleccionada.getTipo() + ") Eliminada.");

    }

    private void limpiarFormulario() {
        txtCodigo.clear();
        txtSuperficie.clear();
        txtHabitaciones.clear();
        txtPiso.clear();

        chkGastoComun.setSelected(false);
        chkEstacionamiento.setSelected(false);
        chkPatio.setSelected(false);

        cmbTipo.setValue(null);
    }
}