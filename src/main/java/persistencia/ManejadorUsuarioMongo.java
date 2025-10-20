
package persistencia;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import jakarta.persistence.EntityTransaction;
import logica.DTO.DTOColaborador;
import org.bson.Document;
import logica.DTO.DTOProponente;
import logica.Usuario.Colaborador;
import logica.Usuario.Proponente;
import logica.Usuario.Usuario;





public class ManejadorUsuarioMongo {
    
    private static ManejadorUsuarioMongo instancia = null;

    public ManejadorUsuarioMongo() {}

    public static ManejadorUsuarioMongo getInstance() {
        
        return new ManejadorUsuarioMongo();
    }
    
    
    public void addProponente(DTOProponente u){
        Proponente p = new Proponente(u); //por usarla puedo usar el dto directamente
        
        MongoClient conn=null;
        
        Document proponenteDocumento = new Document();

        proponenteDocumento.append("tipo_usuario", "Proponente"); 
        proponenteDocumento.append("_id", p.getNickname());
        proponenteDocumento.append("pass", p.getPass());
        proponenteDocumento.append("nombre", p.getNombre());
        proponenteDocumento.append("apellido", p.getApellido());
        proponenteDocumento.append("email", p.getEmail());
        proponenteDocumento.append("fecha", p.getFecha());
        //System.out.println(p.getRutaImg());
        proponenteDocumento.append("rutaImg", p.getRutaImg());
        proponenteDocumento.append("direccion", p.getDireccion());
        proponenteDocumento.append("biografia", p.getBiografia());
        proponenteDocumento.append("webSite", p.getWebSite());
        proponenteDocumento.append("UsuarioSeguidos", new java.util.ArrayList<>(p.getUsuarioSeguido().keySet()));
        proponenteDocumento.append("propuestasCreadas", new java.util.ArrayList<>(p.getPropCreadas().keySet()));
        proponenteDocumento.append("propuestasFavoritas", new java.util.ArrayList<>(p.getPropFavorita().keySet())); 
        
        
        try{
            
            conn=PersistenciaMongo.getConetion("francoechaide_db_user","Hhn9xVioZZnm7bXk");//conexion al cluster
            
            MongoDatabase database=conn.getDatabase("Culturarte");//traigo la bd que quiero
            
            MongoCollection<Document> coleccionUsuarios = database.getCollection("Usuarios");//establece la collecion con la que trabajare

            coleccionUsuarios.insertOne(proponenteDocumento);//inserta
            
            //System.out.println("Proponente insertado exitosamente.");
        }catch(Exception e){
            System.out.println("Error" +e.getMessage());
        }finally{
            if(conn!=null){
                conn.close();//cierro conexion
            }
        }
             
    }
    //insertar un colaborador a la bd
    public void addColaborador(DTOColaborador u){
        Colaborador c=new Colaborador(u);
        MongoClient conn=null;
        
        Document colaboradorDocumento = new Document();

        colaboradorDocumento.append("tipo_usuario", "Colaborador"); 
        colaboradorDocumento.append("_id", c.getNickname());
        colaboradorDocumento.append("pass", c.getPass());
        colaboradorDocumento.append("nombre", c.getNombre());
        colaboradorDocumento.append("apellido", c.getApellido());
        colaboradorDocumento.append("email", c.getEmail());
        colaboradorDocumento.append("fecha", c.getFecha());
        colaboradorDocumento.append("rutaImg", c.getRutaImg());
        //guardo nick
        colaboradorDocumento.append("UsuarioSeguidos", new java.util.ArrayList<>(c.getUsuarioSeguido().keySet())); 
        //guardo titulo
        colaboradorDocumento.append("propuestasFavoritas", new java.util.ArrayList<>(c.getPropFavorita().keySet())); 
        //guardo toda la info de la colaboracion (monto, titulo,tipo retorno y fecha)
        colaboradorDocumento.append("Colaboraciones", new java.util.ArrayList<Document>()); 
        
        try{
            conn=PersistenciaMongo.getConetion("francoechaide_db_user","Hhn9xVioZZnm7bXk");
            MongoDatabase database=conn.getDatabase("Culturarte");//traigo la bd que quiero
            
            MongoCollection<Document> coleccionUsuarios = database.getCollection("Usuarios");//establece la collecion con la que trabajare

            coleccionUsuarios.insertOne(colaboradorDocumento);//agrego coleccion
           
        }
        catch(Exception e){
            System.out.println("Error" +e.getMessage());    
        }finally{
            if(conn!=null){
                conn.close();//cierro conexion
            }
        }
    
    }
    
     //coprueba si el email ya a sido registrado por algun usuario
    public boolean emailUsado(String email){
        MongoClient conn=null;

        try{
           conn=PersistenciaMongo.getConetion("francoechaide_db_user","Hhn9xVioZZnm7bXk");
           
           MongoDatabase database=conn.getDatabase("Culturarte");//traigo la bd que quiero

           MongoCollection<Document> coleccionUsuarios = database.getCollection("Usuarios");//establece la collecion con la que trabajare
           
           Document documentoEncontrado = coleccionUsuarios.find(Filters.eq("email", email)).first();//me quedo con la primera que coicide (aunque al ser unico solo existe ese)

           return documentoEncontrado!=null;
        }finally{
           if(conn!=null){
               conn.close();//cierro conexion
           }
        }
    }
    
      //compruebo que existe un usuario identificado por nick
    public boolean existe(String nick){
        MongoClient conn=null;
      try{
        conn=PersistenciaMongo.getConetion("francoechaide_db_user","Hhn9xVioZZnm7bXk");
        MongoDatabase database=conn.getDatabase("Culturarte");//traigo la bd que quiero
            
        MongoCollection<Document> coleccionUsuarios = database.getCollection("Usuarios");//establece la collecion con la que trabajare
        Document documentoEncontrado =coleccionUsuarios.find(Filters.eq("_id", nick)).first();//me quedo con la primera que coicide (aunque al ser unico solo existe ese)
        
        return documentoEncontrado!=null;
      }finally{
        if(conn!=null){
            conn.close();//cierro conexion
        }
      }
    }
    
    
    
    
    
    
}
    

