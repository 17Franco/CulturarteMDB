
package persistencia;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import dev.morphia.Datastore;
import dev.morphia.Morphia;

import java.util.concurrent.TimeUnit;
import java.util.TimeZone;
import logica.Categoria.Categoria;
import logica.Colaboracion.Colaboracion;



import logica.Usuario.Colaborador;
import logica.Propuesta.Propuesta;
import logica.Propuesta.Registro_Estado;
import logica.Usuario.Proponente;
import logica.Usuario.Usuario;



//clase que nos devuelve una conexion a la bd con user y pass para cada uno
public class PersistenciaMongo {
    
    private static MongoClient mongoClient = null; //guardo la conexion
    private static Datastore datastore = null;
    private static final String NOMBRE_BD = "Culturarte2";
    
    //usuario y pass deberia se leido por algun archivo de configuracion pero por ahora lo dejo asi
    public static synchronized MongoClient getConnection(String usuario, String pass) {
        if (mongoClient == null) { //la crea la primera ves luego devuelve la que existe
            String connectionString = "mongodb+srv://" + usuario + ":" + pass +"@culturartecrud.npauvta.mongodb.net/?retryWrites=true&w=majority&appName=CulturarteCRUD";

            ConnectionString connStr = new ConnectionString(connectionString);

            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(connStr)
                    // Configuración del pool
                    .applyToConnectionPoolSettings(builder -> builder
                            .maxSize(10) // de conexiones simultáneas
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
  
    public static synchronized Datastore getDatastore(String usuario, String pass) {
        
        if (datastore == null) {
            MongoClient client = getConnection(usuario, pass);
            Datastore morphia = Morphia.createDatastore(client, NOMBRE_BD);

//            morphia.getMapper().map(Usuario.class);  
//            morphia.getMapper().map(Colaborador.class);  
//            morphia.getMapper().map(Proponente.class);     
//            morphia.getMapper().map(Propuesta.class); 
//            morphia.getMapper().map(Colaboracion.class);
//            morphia.getMapper().map(Categoria.class);
//            morphia.getMapper().map(Registro_Estado.class);

            datastore = morphia; // Asigna el Datastore
            datastore.ensureIndexes();  // Opcional, asegura que los índices sean creados
        }
        return datastore;
    }

    // Método para cerrar todo el pool
    public static void cerrar() {
        if (mongoClient != null) {
            mongoClient.close();
            mongoClient = null;
            System.out.println("🔒Conexión MongoDB cerrada correctamente.");
        }
        if (datastore != null) {
            datastore = null;
            System.out.println("🔒Datastore de Morphia cerrado correctamente.");
        }
    }
    
}
