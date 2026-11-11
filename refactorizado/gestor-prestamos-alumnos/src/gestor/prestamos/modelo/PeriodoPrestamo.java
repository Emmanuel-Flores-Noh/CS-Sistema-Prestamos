package gestor.prestamos.modelo;

import java.time.LocalDate;

/**
 * Agrupa la fecha en que se presta una herramienta y la fecha límite de devolución,
 * que antes viajaban como dos parámetros sueltos. Es inmutable.
 */
public final class PeriodoPrestamo {

    private final LocalDate fechaPrestamo;
    private final LocalDate fechaLimite;

    /**
     * Crea el periodo de un préstamo. No valida las fechas: las validaciones
     * se mantienen en el constructor de {@link Prestamo}.
     *
     * @param fechaPrestamo fecha en que se presta la herramienta
     * @param fechaLimite fecha máxima de devolución
     */
    public PeriodoPrestamo(LocalDate fechaPrestamo, LocalDate fechaLimite) {
        this.fechaPrestamo = fechaPrestamo;
        this.fechaLimite = fechaLimite;
    }

    /**
     * Devuelve la fecha en que se presta la herramienta.
     *
     * @return fecha de préstamo
     */
    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    /**
     * Devuelve la fecha máxima de devolución.
     *
     * @return fecha límite
     */
    public LocalDate getFechaLimite() {
        return fechaLimite;
    }
}