package gestor.prestamos.consola.comandos;

import gestor.prestamos.consola.ContextoConsola;
import gestor.prestamos.consola.Entrada;
import gestor.prestamos.modelo.Prestamo;
import gestor.prestamos.servicio.GestorPrestamos;

import java.time.LocalDate;
import java.util.List;

public class ComandoDevolver implements IComando {
    @Override
    public void ejecutar(ContextoConsola contexto) {
        GestorPrestamos gestor = contexto.getGestorPrestamos();
        List<Prestamo> activos = gestor.prestamosActivos();

        if (activos.isEmpty()) {
            System.out.println("\n  No hay préstamos pendientes de devolución.");
            return;
        }

        System.out.println("\n  Préstamos activos:");
        for (Prestamo p : activos) {
            System.out.printf("    %d. %s - %s (límite %s)%n",
                    p.getId(), p.getHerramienta().getNombre(), p.getPrestatario(), p.getFechaLimite());
        }

        List<Integer> ids = activos.stream().map(Prestamo::getId).toList();

        Entrada entrada = contexto.getEntrada();
        int id = entrada.pedirOpcion("ID del préstamo a devolver: ", ids);

        LocalDate hoy = LocalDate.now();
        LocalDate fecha = entrada.pedirFecha("Fecha de devolución (AAAA-MM-DD, Enter = " + hoy + "): ", hoy);

        Prestamo devuelto = gestor.devolver(id, fecha);
        contexto.guardar();

        System.out.println("  Devolución registrada.");

        if (devuelto.getDiasRetraso() > 0) {
            System.out.printf("  Retraso de %d día(s). Penalización: %.2f €%n",
                    devuelto.getDiasRetraso(), devuelto.getPenalizacion());
        } else {
            System.out.println("  Devuelta a tiempo, sin penalización.");
        }
    }
}