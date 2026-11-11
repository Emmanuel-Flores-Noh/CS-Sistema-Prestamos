package gestor.prestamos.consola;

import gestor.prestamos.persistencia.RepositorioCsv;
import gestor.prestamos.servicio.GestorPrestamos;

import java.util.ArrayList;
import java.util.List;

import gestor.prestamos.consola.comandos.*;

/**
 * Menú principal de la aplicación. Muestra las opciones, pide los datos necesarios
 * y delega las operaciones en el servicio. Tras cada cambio, guarda los datos.
 */
public final class MenuConsola {

    private static final String MENU = """

            ========== GESTOR DE PRÉSTAMOS ==========
              1. Ver herramientas y disponibilidad
              2. Añadir herramienta
              3. Prestar herramienta
              4. Registrar devolución
              5. Ver préstamos activos
              6. Ver historial y penalizaciones
              7. Dar de baja una herramienta
              8. Reactivar herramienta
              0. Salir
            =========================================
            """;

    private static final List<Integer> OPCIONES = new ArrayList<>();

    List<IComando> LISTA_COMANDOS = List.of(
            new ComandoListarHerramientas(),
            new ComandoAnadirHerramienta(),
            new ComandoPrestar(),
            new ComandoDevolver(),
            new ComandoVerPrestamosActivos(),
            new ComandoVerHistorial(),
            new ComandoDarDeBaja(),
            new ComandoReactivar()
    );
    private final ContextoConsola contexto;
    private final Entrada entrada;

    /**
     * Crea el menú con sus dependencias.
     *
     * @param gestor servicio de préstamos
     * @param repositorio repositorio donde se guardan los datos
     * @param entrada lectura de datos desde la consola
     */
    public MenuConsola(GestorPrestamos gestor, RepositorioCsv repositorio, Entrada entrada) {
        this.contexto = new ContextoConsola(gestor, repositorio, entrada);
        this.entrada = entrada;

        for(int i = 0; i <= LISTA_COMANDOS.size(); i++) {
            OPCIONES.add(i);
        }
    }

    /**
     * Muestra el menú en bucle hasta que el usuario elige salir. Los errores de
     * negocio se muestran sin cerrar la aplicación.
     */
    public void ejecutar() {
        boolean salir = false;
        while (!salir) {
            System.out.println(MENU);
            int opcion = entrada.pedirOpcion("Elige una opción: ", OPCIONES);
            try {
                salir = ejecutarOpcion(opcion);
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("  Error: " + e.getMessage());
            }
        }
        System.out.println("¡Hasta luego!");
    }

    /**
     * Ejecuta la opción elegida.
     *
     * @param opcion número de opción del menú
     * @return {@code true} si el usuario quiere salir de la aplicación
     */
    private boolean ejecutarOpcion(int opcion) {
        if( opcion == 0 ) {
            return true;
        }

        LISTA_COMANDOS.get(opcion - 1).ejecutar(contexto);

        return false;
    }
}
