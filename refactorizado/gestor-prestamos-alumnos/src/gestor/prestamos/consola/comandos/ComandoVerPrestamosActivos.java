package gestor.prestamos.consola.comandos;

import gestor.prestamos.consola.ContextoConsola;
import gestor.prestamos.modelo.Prestamo;

import java.time.LocalDate;
import java.util.List;

public class ComandoVerPrestamosActivos implements IComando {

    // Refactoring: Extract Method.
    // Se extrajeron estadoAtrasadoPrestamo y estadoAlDiaPrestamo para separar
    // el cálculo y la presentación que antes estaban dentro del condicional.

    /**
     * Metodo que devuelve el texto correspondiente a un prestamo atrasado
     * @param prestamo el prestamo que esta atrasado
     * @return El texto en el formato correspondiente
     */
    private String estadoAtrasadoPrestamo(Prestamo prestamo) {
        LocalDate hoy = LocalDate.now();
        long dias = prestamo.getDiasRetrasoA(hoy);
        double estimada = prestamo.calcularPenalizacion(hoy, Prestamo.PENALIZACION_POR_DIA);

        return String.format("ATRASADO %d día(s), penalización estimada %.2f €", dias, estimada);
    }

    /**
     * Mtodo que devuelve el texto correspondiente a un prestamo al dia
     * @param prestamo el prestamo al dia
     * @return El texto en el formato correspondiente
     */
    private String estadoAlDiaPrestamo(Prestamo prestamo) {
        return "Al día, vence " + prestamo.getFechaLimite();
    }

    @Override
    public void ejecutar(ContextoConsola contexto) {
        List<Prestamo> activos = contexto.getGestorPrestamos().prestamosActivos();
        if (activos.isEmpty()) {
            System.out.println("\n  No hay préstamos activos.");
            return;
        }

        System.out.println("\n  Préstamos activos:");
        for (Prestamo p : activos) {
            String situacion;

            // Refactoring: Replace Temp with Query.
            // La variable temporal 'hoy' se sustituyó por la consulta estaAtrasado(),
            // que obtiene la fecha actual dentro del método.

            /**
             * Refactoring: Decompose Conditional.
             * El condicional original mezclaba cálculo y presentación; ahora delega
             * en los métodos extraídos estadoAtrasadoPrestamo y estadoAlDiaPrestamo.
             */
            if( p.estaAtrasado() ) {
                situacion = estadoAtrasadoPrestamo(p);
            }
            else {
                situacion = estadoAlDiaPrestamo(p);
            }

            System.out.printf("    %s - %s | desde %s | %s%n",
                    p.getHerramienta().getNombre(), p.getPrestatario(), p.getFechaPrestamo(), situacion);
        }
    }
}
