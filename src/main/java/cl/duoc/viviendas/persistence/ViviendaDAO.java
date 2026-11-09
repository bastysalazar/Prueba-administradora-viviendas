package cl.duoc.viviendas.persistence;

import cl.duoc.viviendas.model.Vivienda;

import java.io.IOException;
import java.util.List;

public interface ViviendaDAO {

    List<Vivienda> leerTodas() throws IOException;

    void guardarTodas(List<Vivienda> viviendas) throws IOException;
}