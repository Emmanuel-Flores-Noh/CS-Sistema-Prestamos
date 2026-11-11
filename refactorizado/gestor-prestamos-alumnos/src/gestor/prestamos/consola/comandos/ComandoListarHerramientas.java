package gestor.prestamos.consola.comandos;

import gestor.prestamos.consola.ContextoConsola;
import gestor.prestamos.modelo.Herramienta;
import gestor.prestamos.modelo.Prestamo;
import gestor.prestamos.servicio.GestorPrestamos;

import java.util.List;
import java.util.Optional;

public class ComandoListarHerramientas implements IComando {
    @Override
    public void ejecutar(ContextoConsola contexto) {
        GestorPrestamos gestor = contexto.getGestorPrestamos();
        List<Herramienta> herramientas = gestor.getHerramientas();

        if ( herramientas.isEmpty() ) {
            System.out.println("\n  No hay herramientas registradas.");
            return;
        }

        System.out.println("\n  ID  Herramienta             Estado");
        System.out.println("  " + "-".repeat(60));

        for (Herramienta herramienta : herramientas) {
            Optional<Prestamo> activo = gestor.prestamoActivo(herramienta);
            String estado = activo
                    .map(p -> "Prestada a " + p.getPrestatario() + " (hasta " + p.getFechaLimite() + ")")
                    .orElse(herramienta.isDadaDeBaja() ? "Dada de baja" : "Disponible");
            System.out.printf("  %-4d%-24s%s%n", herramienta.getId(), herramienta.getNombre(), estado);
        }
    }
}
