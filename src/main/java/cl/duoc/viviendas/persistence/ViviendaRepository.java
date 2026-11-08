package cl.duoc.viviendas.persistence;

import cl.duoc.viviendas.model.Vivienda;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ViviendaRepository {

    private final ObservableList<Vivienda> viviendas = FXCollections.observableArrayList();

    public ObservableList<Vivienda> getViviendas() {
        return viviendas;
    }

    public void agregar(Vivienda vivienda) {
        viviendas.add(vivienda);
    }

    public void eliminar(Vivienda vivienda) {
        viviendas.remove(vivienda);
    }
}