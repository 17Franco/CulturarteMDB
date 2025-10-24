
package persistencia;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import java.util.concurrent.TimeUnit;
import org.bson.Document;

//clase que nos devuelve una conexion a la bd con user y pass para cada uno
public class PersistenciaMongo {
    
    private static MongoClient mongoClient = null; //guardo la conexion
    
    
    //usuario y pass deberia se leido por algun archivo de configuracion pero por ahora lo dejo asi
    public static MongoClient getConnection(String usuario, String pass) {
        if (mongoClient == null) { //la crea la primera ves luego devuelve la que existe
            String connectionString = "mongodb+srv://" + usuario + ":" + pass +"@culturartecrud.npauvta.mongodb.net/?retryWrites=true&w=majority&appName=CulturarteCRUD";

            ConnectionString connStr = new ConnectionString(connectionString);

            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(connStr)
                    // Configuración del pool
                    .applyToConnectionPoolSettings(builder -> builder
                            .maxSize(50) // de conexiones simultáneas
                            .minSize(5)  // conexiones que se mantienen abiertas
                            .maxConnectionIdleTime(60, TimeUnit.SECONDS)
                    )
                    .serverApi(ServerApi.builder()
                            .version(ServerApiVersion.V1)
                            .build())
                    .build();

            mongoClient = MongoClients.create(settings);
            //System.out.println("Conexión MongoDB inicializada con pool de conexiones.");
        }
        return mongoClient;
    }

    // Método para cerrar todo el pool
    public static void cerrar() {
        if (mongoClient != null) {
            mongoClient.close();
            mongoClient = null;
            System.out.println("🔒Conexión MongoDB cerrada correctamente.");
        }
    }
    
}
