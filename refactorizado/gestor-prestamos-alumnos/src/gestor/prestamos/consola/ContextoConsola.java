package gestor.prestamos.consola;

import gestor.prestamos.persistencia.RepositorioCsv;
import gestor.prestamos.servicio.GestorPrestamos;

import java.io.IOException;

/**
 * Clase dedicada para pasarla como atributo a los comandos, le pasa el contexto del programa y permite la persistencia de datos
 */
public class ContextoConsola {
    final private GestorPrestamos gestorPrestamos;
    final private RepositorioCsv repositorio;
    final private Entrada entrada;

    public ContextoConsola(GestorPrestamos gestorPrestamos, RepositorioCsv repositorioCsv, Entrada entrada) {
        this.gestorPrestamos = gestorPrestamos;
        this.repositorio = repositorioCsv;
        this.entrada = entrada;
    }

    public GestorPrestamos getGestorPrestamos() {
        return gestorPrestamos;
    }

    public RepositorioCsv getRepositorio() {
        return repositorio;
    }

    public Entrada getEntrada() {
        return entrada;
    }

    /**
     * Guarda los datos en CSV
     */
    public void guardar() {
        GestorPrestamos gestorPrestamos = getGestorPrestamos();
        RepositorioCsv repositorio = getRepositorio();
        try {
            repositorio.guardarHerramientas(gestorPrestamos.getHerramientas());
            repositorio.guardarPrestamos(gestorPrestamos.getPrestamos());
        } catch (IOException e) {
            System.out.println("  Aviso: no se pudieron guardar los datos: " + e.getMessage());
        }
    }
}
