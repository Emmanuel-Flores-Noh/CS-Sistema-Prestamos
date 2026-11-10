package gestor.prestamos.consola.comandos;

import gestor.prestamos.consola.ContextoConsola;
import gestor.prestamos.consola.Entrada;
import gestor.prestamos.modelo.Herramienta;
import gestor.prestamos.modelo.Prestamo;
import gestor.prestamos.servicio.GestorPrestamos;

import java.time.LocalDate;
import java.util.List;

public class ComandoPrestar implements IComando {
    @Override
    public void ejecutar(ContextoConsola contexto) {
        GestorPrestamos gestor = contexto.getGestorPrestamos();
        List<Herramienta> disponibles = gestor.herramientasDisponibles();

        if ( disponibles.isEmpty() ) {
            System.out.println("\n  No hay herramientas disponibles para prestar.");
            return;
        }

        System.out.println("\n  Herramientas disponibles:");
        for (Herramienta herramienta : disponibles) {
            System.out.printf("    %d. %s%n", herramienta.getId(), herramienta.getNombre());
        }

        List<Integer> ids = disponibles.stream().map(Herramienta::getId).toList();

        Entrada entrada = contexto.getEntrada();
        int id = entrada.pedirOpcion("ID de la herramienta: ", ids);
        String prestatario = entrada.pedirTexto("Nombre de quien la recibe: ");

        LocalDate hoy = LocalDate.now();
        LocalDate inicio = entrada.pedirFecha("Fecha de préstamo (AAAA-MM-DD, Enter = " + hoy + "): ", hoy);
        LocalDate limitePorDefecto = inicio.plusDays(GestorPrestamos.DIAS_PRESTAMO_DEFECTO);
        LocalDate limite = entrada.pedirFecha("Fecha límite (AAAA-MM-DD, Enter = " + limitePorDefecto + "): ", limitePorDefecto);

        Prestamo nuevo = gestor.prestar(id, prestatario, inicio, limite);
        contexto.guardar();
        System.out.printf("  '%s' prestada a %s hasta %s.%n", nuevo.getHerramienta().getNombre(), nuevo.getPrestatario(), nuevo.getFechaLimite());
    }
}
