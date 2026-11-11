package gestor.prestamos.consola.comandos;

import gestor.prestamos.consola.ContextoConsola;
import gestor.prestamos.modelo.Prestamo;
import gestor.prestamos.servicio.GestorPrestamos;

import java.util.List;

public class ComandoVerHistorial implements IComando {
    @Override
    public void ejecutar(ContextoConsola contexto) {
        GestorPrestamos gestor = contexto.getGestorPrestamos();
        List<Prestamo> historial = gestor.historial();

        if (historial.isEmpty()) {
            System.out.println("\n  No hay devoluciones registradas.");
            return;
        }

        System.out.println("\n  Historial de devoluciones:");
        for (Prestamo p : historial) {
            System.out.printf("    %s - %s | %s -> %s | retraso %d día(s) | penalización %.2f €%n",
                    p.getHerramienta().getNombre(), p.getPrestatario(), p.getFechaPrestamo(),
                    p.getFechaDevolucion(), p.getDiasRetraso(), p.getPenalizacion());
        }

        System.out.printf("  Total de penalizaciones cobradas: %.2f €%n", gestor.totalPenalizaciones());
    }
}
