
package persistencia;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

//clase que nos devuelve una conexion a la bd con user y pass para cada uno
public class PersistenciaMongo {
    
    //private static MongoClient mongoClient = null; //guardo la conexion
    
    //devuelve la conexion a bd especificada
    public static MongoClient getConetion(String user,String pass) {
        
        MongoClient mongoClient =Conection(user, pass); 
        
        return mongoClient;
    }
    
    //nos conecta al cluster de atlas usando user y pass diferentes
    public static MongoClient Conection(String usuario,String pass){
         
        String connectionString = "mongodb+srv://" + usuario + ":" + pass +"@culturartecrud.npauvta.mongodb.net/?retryWrites=true&w=majority&appName=CulturarteCRUD";

        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .serverApi(serverApi)
                .build();

        //System.out.println("Conexión a MongoDB creada correctamente.");
        
        return MongoClients.create(settings);
    }
    
//     //metodo para cerrar la conexion 
//     public static void cerrar(MongoClient conn) {
//        if (conn != null) {
//            conn.close();
//            conn = null;
//            System.out.println("🔒Conexión a MongoDB cerrada.");
//        }
//    }
    
}
