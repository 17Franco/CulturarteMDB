
package persistencia;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import jakarta.persistence.EntityTransaction;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import logica.DTO.DTOColaborador;
import org.bson.Document;
import logica.DTO.DTOProponente;
import logica.DTO.DTOUsuario;
import logica.Usuario.Colaborador;
import logica.Usuario.Proponente;
import logica.Usuario.Usuario;





public class ManejadorUsuarioMongo {
    
    private static ManejadorUsuarioMongo instancia = null;

    public ManejadorUsuarioMongo() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

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
            
            conn=PersistenciaMongo.getConnection("francoechaide_db_user","Hhn9xVioZZnm7bXk");//conexion al cluster
            
            MongoDatabase database=conn.getDatabase("Culturarte");//traigo la bd que quiero
            
            MongoCollection<Document> coleccionUsuarios = database.getCollection("Usuarios");//establece la collecion con la que trabajare

            coleccionUsuarios.insertOne(proponenteDocumento);//inserta
            
            //System.out.println("Proponente insertado exitosamente.");
        }catch(Exception e){
            System.out.println("Error" +e.getMessage());
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
            conn=PersistenciaMongo.getConnection("francoechaide_db_user","Hhn9xVioZZnm7bXk");
            MongoDatabase database=conn.getDatabase("Culturarte");//traigo la bd que quiero
            
            MongoCollection<Document> coleccionUsuarios = database.getCollection("Usuarios");//establece la collecion con la que trabajare

            coleccionUsuarios.insertOne(colaboradorDocumento);//agrego coleccion
           
        }
        catch(Exception e){
            System.out.println("Error" +e.getMessage());    
        }
    
    }
    
     //coprueba si el email ya a sido registrado por algun usuario
    public boolean emailUsado(String email){
        MongoClient conn=null;

        
           conn=PersistenciaMongo.getConnection("francoechaide_db_user","Hhn9xVioZZnm7bXk");
           
           MongoDatabase database=conn.getDatabase("Culturarte");//traigo la bd que quiero

           MongoCollection<Document> coleccionUsuarios = database.getCollection("Usuarios");//establece la collecion con la que trabajare
           
           Document documentoEncontrado = coleccionUsuarios.find(Filters.eq("email", email)).first();//me quedo con la primera que coicide (aunque al ser unico solo existe ese)

           return documentoEncontrado!=null;
        
        
        
    }
    
    //compruebo que existe un usuario identificado por nick
    public boolean existe(String nick){
        MongoClient conn=null;
      try{
        conn=PersistenciaMongo.getConnection("francoechaide_db_user","Hhn9xVioZZnm7bXk");
        MongoDatabase database=conn.getDatabase("Culturarte");//traigo la bd que quiero
            
        MongoCollection<Document> coleccionUsuarios = database.getCollection("Usuarios");//establece la collecion con la que trabajare
        Document documentoEncontrado =coleccionUsuarios.find(Filters.eq("_id", nick)).first();//me quedo con la primera que coicide (aunque al ser unico solo existe ese)
        
        return documentoEncontrado!=null;
      }finally{
        
      }
    }
    
    //transformo los documentos en DTOProponente
    public DTOProponente documentToDtoProponente(Document d){
        if(d.isEmpty()){
            return null;
        }
        DTOProponente p= new DTOProponente();
        p.setNickname(d.getString("_id"));
        p.setNombre(d.getString("nombre"));
        p.setApellido(d.getString("apellido"));
        p.setEmail(d.getString("email"));

        Date fechaUtil = d.getDate("fecha"); // Obtienes la fecha/hora de MongoDB
        LocalDate fechaLocalDate=null;
        if (fechaUtil != null) {

            java.time.Instant instant = fechaUtil.toInstant(); 

            fechaLocalDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();

            System.out.println("Fecha convertida a LocalDate: " + fechaLocalDate);
        }
        p.setFecha(fechaLocalDate);

        p.setRutaImg(d.getString("rutaImg"));
        p.setDireccion(d.getString("direccion"));
        p.setBiografia(d.getString("biografia"));
        p.setWebSite(d.getString("webSite"));

        
        return p;
        
        
    }
    
    //transformo los documentos en DTOColaborador
    public DTOColaborador documentToDtoColaborador(Document d){
        
        if(d.isEmpty()){
                return null;
            }
            DTOColaborador c= new DTOColaborador();
            c.setNickname(d.getString("_id"));
            c.setNombre(d.getString("nombre"));
            c.setApellido(d.getString("apellido"));
            c.setEmail(d.getString("email"));
            
            Date fechaUtil = d.getDate("fecha"); // Obtienes la fecha/hora de MongoDB
            LocalDate fechaLocalDate=null;
            if (fechaUtil != null) {
                
                java.time.Instant instant = fechaUtil.toInstant(); 

                fechaLocalDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();

                System.out.println("Fecha convertida a LocalDate: " + fechaLocalDate);
            }
            c.setFecha(fechaLocalDate);
            
            c.setRutaImg(d.getString("rutaImg"));

        return c;
    }
     //DEVUELVO TODOS LOS USUARIOS COMO DTO A CONTROLLER
    public Map<String, DTOUsuario> getUsuarios() {
        MongoClient conn=null;
        Map<String, DTOUsuario> resu = new HashMap<>();

        try{
  
            //Reliazo conexion
            conn=PersistenciaMongo.getConnection("francoechaide_db_user","Hhn9xVioZZnm7bXk");
            
            MongoDatabase database=conn.getDatabase("Culturarte");//traigo la bd que quiero
            
            MongoCollection<Document> coleccionUsuarios = database.getCollection("Usuarios");//establece la collecion con la que trabajare
            
            //Traigo todos los usuarios en una lista
            
            FindIterable<Document> todosLosDocumentos = coleccionUsuarios.find();
            for(Document u: todosLosDocumentos){
               if("Colaborador".equals(u.getString("tipo_usuario"))){
                   DTOColaborador c= documentToDtoColaborador(u);
                   if(c!=null){
                    resu.put(c.getNickname(), c);
                   }
               }else if("Proponente".equals(u.getString("tipo_usuario"))){
                   DTOProponente p=documentToDtoProponente(u);
                   if(p!=null){
                    resu.put(p.getNickname(), p);
                   }
               }
           }
           return resu;
        }finally{
            
        }
    }
    
    //devuelvo si es proponente o no
    public boolean isProponente(String nick){
        MongoClient conn=null;

        try{
            conn=PersistenciaMongo.getConnection("francoechaide_db_user","Hhn9xVioZZnm7bXk");

            MongoDatabase database=conn.getDatabase("Culturarte");//traigo la bd que quiero 

            MongoCollection<Document> coleccionUsuarios = database.getCollection("Usuarios");//establece la collecion con la que trabajare

            Document documentoEncontrado =coleccionUsuarios.find(Filters.eq("_id", nick)).first();//me quedo con la primera que coicide (aunque al ser unico solo existe ese)

            return !documentoEncontrado.isEmpty() && "Proponente".equals(documentoEncontrado.getString("_id"));

        }finally{
            
        }
    }
    
//      //devuelvo dtoUsuario con info minima de todos los usuarios cargados
//    public List<DTOUsuario> getListDTOUsuario(){
////         em= PersistenciaManager.getEntityManager();
////         List<DTOUsuario> usuarios=new ArrayList<>();
////        try{
////            List<Usuario> lista = em.createQuery("FROM Usuario", Usuario.class).getResultList();
////            for(Usuario u: lista){
////                DTOUsuario usr=new DTOUsuario();
////                usr.setNickname(u.getNickname());
////                usr.setRutaImg(u.getRutaImg());
////                if(u instanceof Proponente){
////                    usr.setTipoUsr("Proponente");
////                }else{
////                    usr.setTipoUsr("Colaborador");
////                }
////                usuarios.add(usr);
////            }
////            return usuarios;
////        }finally{
////            em.close();
////        }
//    
//    }
 
}
    

