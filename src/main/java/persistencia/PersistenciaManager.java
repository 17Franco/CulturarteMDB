
package persistencia;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class PersistenciaManager {
    
    // creo la fabrica de EntityManager 
    private static final EntityManagerFactory emf = 
            Persistence.createEntityManagerFactory("LabPA");

    // Método para obtener un EntityManager 
    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    // Cerrar la fábrica al final de la aplicación
    public static void close() {
        emf.close();
    }
}