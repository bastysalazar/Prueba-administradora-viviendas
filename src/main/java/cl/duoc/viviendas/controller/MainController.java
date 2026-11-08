package cl.duoc.viviendas.controller;

import cl.duoc.viviendas.model.Departamento;
import cl.duoc.viviendas.model.Vivienda;
import cl.duoc.viviendas.persistence.ViviendaRepository;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class MainController {

    @FXML
    private ComboBox<String> cmbTipo;
    @FXML
    private TextField txtCodigo;
    @FXML
    private TextField txtSuperficie;
    @FXML
    private TextField txtHabitaciones;
    @FXML
    private TextField txtPiso;
    @FXML
    private CheckBox chkGastoComun;
    @FXML
    private CheckBox chkEstacionamiento;
    @FXML
    private CheckBox chkPatio;
    @FXML
    private Button btnRegistrar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Label lblMensaje;

    @FXML
    private TableView<Vivienda> tblViviendas;
    @FXML
    private TableColumn<Vivienda, String> colTipo;
    @FXML
    private TableColumn<Vivienda, String> colCodigo;
    @FXML
    private TableColumn<Vivienda, Double> colSuperficie;
    @FXML
    private TableColumn<Vivienda, Integer> colHabitaciones;
    @FXML
    private TableColumn<Vivienda, String> colCosto;

    private final ViviendaRepository repository = new ViviendaRepository();

    @FXML
    public void initialize() {
        cmbTipo.setOnAction(e -> actualizarCamposSegunTipo());
        cmbTipo.getItems().addAll("Departamento", "Casa");
        txtPiso.setDisable(true);
        chkGastoComun.setDisable(true);
        chkEstacionamiento.setDisable(true);
        chkPatio.setDisable(true);
        tblViviendas.setItems(repository.getViviendas());
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colSuperficie.setCellValueFactory(new PropertyValueFactory<>("superficie"));
        colHabitaciones.setCellValueFactory(new PropertyValueFactory<>("habitaciones"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colCosto.setCellValueFactory(celda ->
                new SimpleStringProperty("$" + String.format("%,.0f", celda.getValue().calcularCostoArriendo())));
        repository.agregar(new Departamento("PROP-D01", 65.0, 3, 8, false));
        System.out.println("Viviendas en lista: " + repository.getViviendas().size());
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
        }

    }

    @FXML
    private void onRegistrar() {
    }

    @FXML
    private void onEliminar() {
    }

}