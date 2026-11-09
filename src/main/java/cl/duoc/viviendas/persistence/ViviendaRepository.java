package cl.duoc.viviendas.persistence;

import cl.duoc.viviendas.model.Vivienda;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;

public class ViviendaRepository {

    private final ObservableList<Vivienda> viviendas = FXCollections.observableArrayList();
    private final ViviendaDAO dao;

    public ViviendaRepository(ViviendaDAO dao) {
        this.dao = dao;
    }

    public ObservableList<Vivienda> getViviendas() {
        return viviendas;
    }

    public void cargar() throws IOException {
        viviendas.setAll(dao.leerTodas());
    }

    public void agregar(Vivienda vivienda) throws IOException {
        viviendas.add(vivienda);
        try {
            dao.guardarTodas(viviendas);
        } catch (IOException e) {
            viviendas.remove(vivienda);
            throw e;
        }
    }

    public void eliminar(Vivienda vivienda) throws IOException {
        viviendas.remove(vivienda);
        try {
            dao.guardarTodas(viviendas);
        } catch (IOException e) {
            viviendas.add(vivienda);
            throw e;
        }
    }
}