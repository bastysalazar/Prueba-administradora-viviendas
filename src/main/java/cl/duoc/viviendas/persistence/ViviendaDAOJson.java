package cl.duoc.viviendas.persistence;

import cl.duoc.viviendas.model.Casa;
import cl.duoc.viviendas.model.Departamento;
import cl.duoc.viviendas.model.Vivienda;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ViviendaDAOJson implements ViviendaDAO {

    private static final String ARCHIVO_DEPARTAMENTOS = "departamentos.json";
    private static final String ARCHIVO_CASAS = "casas.json";

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public List<Vivienda> leerTodas() throws IOException {
        List<Vivienda> resultado = new ArrayList<>();
        resultado.addAll(leerArchivo(ARCHIVO_DEPARTAMENTOS, Departamento[].class));
        resultado.addAll(leerArchivo(ARCHIVO_CASAS, Casa[].class));
        return resultado;
    }

    @Override
    public void guardarTodas(List<Vivienda> viviendas) throws IOException {
        List<Departamento> departamentos = new ArrayList<>();
        List<Casa> casas = new ArrayList<>();

        for (Vivienda v : viviendas) {
            if (v instanceof Departamento d) {
                departamentos.add(d);
            }
            // TU TURNO: si es Casa, agregarla a la lista de casas
        }

        escribirArchivo(ARCHIVO_DEPARTAMENTOS, departamentos);
        // TU TURNO: escribir también las casas en ARCHIVO_CASAS
    }

    private <T extends Vivienda> List<T> leerArchivo(String nombre, Class<T[]> tipo) throws IOException {
        Path ruta = Path.of(nombre);
        if (!Files.exists(ruta)) {
            return new ArrayList<>();   // primera ejecución: sin archivo, sin error
        }
        try (Reader lector = Files.newBufferedReader(ruta)) {
            T[] datos = gson.fromJson(lector, tipo);
            return datos == null ? new ArrayList<>() : new ArrayList<>(Arrays.asList(datos));
        }
    }

    private void escribirArchivo(String nombre, List<? extends Vivienda> datos) throws IOException {
        try (Writer escritor = Files.newBufferedWriter(Path.of(nombre))) {
            gson.toJson(datos, escritor);
        }
    }
}