package gestor.prestamos.consola.comandos;

import gestor.prestamos.consola.ContextoConsola;
import gestor.prestamos.modelo.Herramienta;
import gestor.prestamos.servicio.GestorPrestamos;

import java.util.List;

public class ComandoReactivar implements IComando {
    @Override
    public void ejecutar(ContextoConsola contexto) {
        GestorPrestamos gestor = contexto.getGestorPrestamos();
        List<Herramienta> dadas = gestor.getHerramientas().stream()
                .filter(Herramienta::isDadaDeBaja)
                .toList();

        if (dadas.isEmpty()) {
            System.out.println("\n  No hay herramientas dadas de baja.");
            return;
        }


        System.out.println("\n  Herramientas dadas de baja:");
        for (Herramienta herramienta : dadas) {
            System.out.printf("    %d. %s%n", herramienta.getId(), herramienta.getNombre());
        }


        List<Integer> ids = dadas.stream().map(Herramienta::getId).toList();
        int id = contexto.getEntrada().pedirOpcion("ID de la herramienta a reactivar: ", ids);
        Herramienta elegida = dadas.stream()
                .filter(h -> h.getId() == id)
                .findFirst()
                .orElseThrow();

        gestor.reactivar(id);
        contexto.guardar();
        System.out.printf("  '%s' reactivada y disponible para préstamo.%n", elegida.getNombre());
    }
}
