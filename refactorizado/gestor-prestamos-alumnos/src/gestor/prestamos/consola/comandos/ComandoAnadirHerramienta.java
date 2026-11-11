package gestor.prestamos.consola.comandos;

import gestor.prestamos.consola.ContextoConsola;
import gestor.prestamos.modelo.Herramienta;

public class ComandoAnadirHerramienta implements IComando {
    @Override
    public void ejecutar(ContextoConsola contexto) {
        String nombre = contexto.getEntrada().pedirTexto("Nombre de la herramienta: ");
        Herramienta nueva = contexto.getGestorPrestamos().anadirHerramienta(nombre);
        contexto.guardar();
        System.out.printf("  Herramienta '%s' añadida con ID %d.%n", nueva.getNombre(), nueva.getId());
    }
}
