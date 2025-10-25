
package persistencia;

import com.mongodb.MongoSecurityException;
import com.mongodb.MongoSocketException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Updates;
import static com.mongodb.client.model.Updates.pull;
import com.mongodb.client.result.UpdateResult;
import dev.morphia.Datastore;
import jakarta.persistence.EntityTransaction;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import logica.DTO.DTOColaborador;
import org.bson.Document;
import logica.DTO.DTOProponente;
import logica.DTO.DTOPropuesta;
import logica.DTO.DTOUsuario;
import logica.DTO.Estado;
import logica.Propuesta.Propuesta;
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
        Datastore datastore = PersistenciaMongo.getDatastore("francoechaide_db_user", "Hhn9xVioZZnm7bXk");    
        try {
            datastore.save(p);  
            System.out.println("Proponente insertado exitosamente.");
        } catch (Exception e) {
            System.err.println("ERROR INESPERADO al insertar Proponente: " + e.getMessage());
        }

             
    }
    //insertar un colaborador a la bd
    public void addColaborador(DTOColaborador u){
        Colaborador c=new Colaborador(u);
         
        Datastore datastore = PersistenciaMongo.getDatastore("francoechaide_db_user", "Hhn9xVioZZnm7bXk");    
        try {
            datastore.save(c);  
            System.out.println("Colaborador insertado exitosamente.");
        } catch (Exception e) {
            System.err.println("ERROR INESPERADO al insertar Proponente: " + e.getMessage());
        }
    
    }
//    
//     //coprueba si el email ya a sido registrado por algun usuario
//    public boolean emailUsado(String email){
//        MongoClient conn=null;
//
//        try{
//            conn=PersistenciaMongo.getConnection("francoechaide_db_user","Hhn9xVioZZnm7bXk");
//
//            MongoDatabase database=conn.getDatabase("Culturarte");//traigo la bd que quiero
//
//            MongoCollection<Document> coleccionUsuarios = database.getCollection("Usuarios");//establece la collecion con la que trabajare
//
//            Document documentoEncontrado = coleccionUsuarios.find(Filters.eq("email", email)).first();//me quedo con la primera que coicide (aunque al ser unico solo existe ese)
//
//            return documentoEncontrado!=null;
//        }catch (MongoSocketException e) {
//            System.err.println("🚨 ERROR DE CONEXIÓN: No se pudo conectar a la base de datos Culturarte.");
//            return false; 
//        } catch (Exception e) {
//            
//            System.err.println("ERROR INESPERADO al averiguar el tipo de Usuario" +e.getMessage());
//            return false; 
//        } 
//        
//        
//    }
//    
//    //compruebo que existe un usuario identificado por nick
//    public boolean existe(String nick){
//        MongoClient conn=null;
//      try{
//        conn=PersistenciaMongo.getConnection("francoechaide_db_user","Hhn9xVioZZnm7bXk");
//        MongoDatabase database=conn.getDatabase("Culturarte");//traigo la bd que quiero
//            
//        MongoCollection<Document> coleccionUsuarios = database.getCollection("Usuarios");//establece la collecion con la que trabajare
//        Document documentoEncontrado =coleccionUsuarios.find(Filters.eq("_id", nick)).first();//me quedo con la primera que coicide (aunque al ser unico solo existe ese)
//        
//        return documentoEncontrado!=null;
//      }catch (MongoSocketException e) {
//            System.err.println("🚨 ERROR DE CONEXIÓN: No se pudo conectar a la base de datos Culturarte.");
//            return false; 
//        } catch (Exception e) {
//
//            System.err.println("ERROR INESPERADO al averiguar el tipo de Usuario" +e.getMessage());
//            return false; 
//        }
//    }
//    
//    //transformo los documentos en DTOProponente
//    public DTOProponente documentToDtoProponente(Document d){
//        if(d.isEmpty()){
//            return null;
//        }
//        DTOProponente p= new DTOProponente();
//        p.setNickname(d.getString("_id"));
//        p.setNombre(d.getString("nombre"));
//        p.setApellido(d.getString("apellido"));
//        p.setEmail(d.getString("email"));
//
//        Date fechaUtil = d.getDate("fecha"); // Obtienes la fecha/hora de MongoDB
//        LocalDate fechaLocalDate=null;
//        if (fechaUtil != null) {
//
//            java.time.Instant instant = fechaUtil.toInstant(); 
//
//            fechaLocalDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
//
//            //System.out.println("Fecha convertida a LocalDate: " + fechaLocalDate);
//        }
//        p.setFecha(fechaLocalDate);
//
//        p.setRutaImg(d.getString("rutaImg"));
//        p.setDireccion(d.getString("direccion"));
//        p.setBiografia(d.getString("biografia"));
//        p.setWebSite(d.getString("webSite"));
//
//        
//        return p;
//        
//        
//    }
//    
//    //transformo los documentos en DTOColaborador
//    public DTOColaborador documentToDtoColaborador(Document d){
//        
//        if(d.isEmpty()){
//                return null;
//            }
//            DTOColaborador c= new DTOColaborador();
//            c.setNickname(d.getString("_id"));
//            c.setNombre(d.getString("nombre"));
//            c.setApellido(d.getString("apellido"));
//            c.setEmail(d.getString("email"));
//            
//            Date fechaUtil = d.getDate("fecha"); // Obtienes la fecha/hora de MongoDB
//            LocalDate fechaLocalDate=null;
//            if (fechaUtil != null) {
//                
//                java.time.Instant instant = fechaUtil.toInstant(); 
//
//                fechaLocalDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
//
//                //System.out.println("Fecha convertida a LocalDate: " + fechaLocalDate);
//            }
//            c.setFecha(fechaLocalDate);
//            
//            c.setRutaImg(d.getString("rutaImg"));
//
//        return c;
//    }
//    
//     //DEVUELVO TODOS LOS USUARIOS COMO DTO A CONTROLLER
//    public Map<String, DTOUsuario> getUsuarios() {
//        MongoClient conn=null;
//        Map<String, DTOUsuario> resu = new HashMap<>();
//
//        try{
//  
//            //Reliazo conexion
//            conn=PersistenciaMongo.getConnection("francoechaide_db_user","Hhn9xVioZZnm7bXk");
//            
//            MongoDatabase database=conn.getDatabase("Culturarte");//traigo la bd que quiero
//            
//            MongoCollection<Document> coleccionUsuarios = database.getCollection("Usuarios");//establece la collecion con la que trabajare
//            
//            //Traigo todos los usuarios en una lista
//            
//            FindIterable<Document> todosLosDocumentos = coleccionUsuarios.find();
//            for(Document u: todosLosDocumentos){
//               if("Colaborador".equals(u.getString("tipo_usuario"))){
//                   DTOColaborador c= documentToDtoColaborador(u);
//                   if(c!=null){
//                    resu.put(c.getNickname(), c);
//                   }
//               }else if("Proponente".equals(u.getString("tipo_usuario"))){
//                   DTOProponente p=documentToDtoProponente(u);
//                   if(p!=null){
//                    resu.put(p.getNickname(), p);
//                   }
//               }
//           }
//           return resu;
//        }catch (MongoSocketException e) {
//            System.err.println("🚨 ERROR DE CONEXIÓN: No se pudo conectar a la base de datos Culturarte.");
//            return resu; 
//        } catch (Exception e) {
//            
//            System.err.println("ERROR INESPERADO al devolver los usuarios." +e.getMessage());
//            
//            return resu;
//        }
//    }
//    
//    //devuelvo si es proponente o no
//    public boolean isProponente(String nick){
//        MongoClient conn=null;
//
//        try{
//            conn=PersistenciaMongo.getConnection("francoechaide_db_user","Hhn9xVioZZnm7bXk");
//
//            MongoDatabase database=conn.getDatabase("Culturarte");//traigo la bd que quiero 
//
//            MongoCollection<Document> coleccionUsuarios = database.getCollection("Usuarios");//establece la collecion con la que trabajare
//
//            Document documentoEncontrado =coleccionUsuarios.find(Filters.eq("_id", nick)).first();//me quedo con la primera que coicide (aunque al ser unico solo existe ese)
//
//            return !documentoEncontrado.isEmpty() && "Proponente".equals(documentoEncontrado.getString("tipo_usuario"));
//
//        }catch (MongoSocketException e) {
//            System.err.println("🚨 ERROR DE CONEXIÓN: No se pudo conectar a la base de datos Culturarte.");
//            return false; 
//        } catch (Exception e) {
//            
//            System.err.println("ERROR INESPERADO al averiguar el tipo de Usuario" +e.getMessage());
//            return false; 
//        }
//    }
//    
//    //transformo los documentos en Proponente
//    public Proponente documentToProponente(Document d){
//        if(d.isEmpty()){
//            return null;
//        }
//        Proponente p= new Proponente();
//        p.setNickname(d.getString("_id"));
//        p.setNombre(d.getString("nombre"));
//        p.setApellido(d.getString("apellido"));
//        p.setEmail(d.getString("email"));
//
//        Date fechaUtil = d.getDate("fecha"); // Obtienes la fecha/hora de MongoDB
//        LocalDate fechaLocalDate=null;
//        if (fechaUtil != null) {
//
//            java.time.Instant instant = fechaUtil.toInstant(); 
//
//            fechaLocalDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
//
//            //System.out.println("Fecha convertida a LocalDate: " + fechaLocalDate);
//        }
//        p.setFecha(fechaLocalDate);
//
//        p.setRutaImg(d.getString("rutaImg"));
//        p.setDireccion(d.getString("direccion"));
//        p.setBiografia(d.getString("biografia"));
//        p.setWebSite(d.getString("webSite"));
//
//        
//        return p;
//        
//        
//    }
//    
//    //transformo los documentos en Colaborador
//    public Colaborador documentToColaborador(Document d){
//        
//        if(d.isEmpty()){
//                return null;
//            }
//            Colaborador c= new Colaborador();
//            c.setNickname(d.getString("_id"));
//            c.setNombre(d.getString("nombre"));
//            c.setApellido(d.getString("apellido"));
//            c.setEmail(d.getString("email"));
//            
//            Date fechaUtil = d.getDate("fecha"); // Obtienes la fecha/hora de MongoDB
//            LocalDate fechaLocalDate=null;
//            if (fechaUtil != null) {
//                
//                java.time.Instant instant = fechaUtil.toInstant(); 
//
//                fechaLocalDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
//
//                //System.out.println("Fecha convertida a LocalDate: " + fechaLocalDate);
//            }
//            c.setFecha(fechaLocalDate);
//            
//            c.setRutaImg(d.getString("rutaImg"));
//
//        return c;
//    }
//    
//    //devuelvo una Intancia de Usuario
//    public Usuario getUsuario(String nick) {
//        MongoClient conn=null;
//
//        Usuario usu=null;
//        try {
//            conn=PersistenciaMongo.getConnection("francoechaide_db_user","Hhn9xVioZZnm7bXk");
//
//            MongoDatabase database=conn.getDatabase("Culturarte");//traigo la bd que quiero 
//
//            MongoCollection<Document> coleccionUsuarios = database.getCollection("Usuarios");//establece la collecion con la que trabajare
//
//            Document documentoEncontrado =coleccionUsuarios.find(Filters.eq("_id", nick)).first();
//            
//            if(documentoEncontrado!=null && "Proponente".equals(documentoEncontrado.getString("tipo_usuario"))){
//                usu=documentToProponente(documentoEncontrado);
//            }else if(documentoEncontrado!=null && "Colaborador".equals(documentoEncontrado.getString("tipo_usuario"))){
//                usu=documentToColaborador(documentoEncontrado);
//            }
//            
//            return usu;
//        }catch (MongoSocketException e) {
//            System.err.println("🚨 ERROR DE CONEXIÓN: No se pudo conectar a la base de datos Culturarte.");
//            return usu; // Devuelve null
//        } catch (Exception e) {
//            
//            System.err.println("ERROR INESPERADO al buscar el Usuario." +e.getMessage());
//            return usu; // Devuelve null
//        }
//    }
//    
//    //obtengo la info minima de la propuestas creadas de un proponente 
//    public Set<DTOPropuesta> getPropuestasCreadasPorProponente(String nick){
////            em = PersistenciaManager.getEntityManager();
////               Proponente  p=em.find(Proponente.class,nick);
//           Set<DTOPropuesta> resu=new HashSet<>();
////            try{
////                DTOProponente dtoProp=new DTOProponente(p);
////                for(Propuesta prop: p.getPropCreadas().values()){
////                    Estado aux=prop.getHistorialEstados().get(0).getEstado();
////                    DTOPropuesta dtoP=new DTOPropuesta(prop,dtoProp);
////                    dtoP.setEstadoAct(aux);
////                    resu.add(dtoP);
////                }
////                return resu;
////            }finally{
////                em.close();
////            }
//        return resu;
//    }
//    
//    
//    
//    //devuelvo solo los nick de los seguidos del usuario identificado por nick
//    public List<String> listaSeguidos(String nick){
//        MongoClient conn=null;
//        List<String> aux = new ArrayList<>();
//        try{
//            //pido conexion
//            conn=PersistenciaMongo.getConnection("francoechaide_db_user","Hhn9xVioZZnm7bXk");
//            //traigo la bd que quiero 
//            MongoDatabase database=conn.getDatabase("Culturarte");
//            //establece la collecion con la que trabajare
//            MongoCollection<Document> coleccionUsuarios = database.getCollection("Usuarios");
//            
//            //no traigo todo solo el campo seguido del usuario identificado por nick
//            Document documentoEncontrado = coleccionUsuarios.find(Filters.eq("_id", nick)).projection(Projections.include("UsuarioSeguidos")).first();
//            
//            //verifico que se encuentre el usuario
//            if (documentoEncontrado != null) {
//                aux = documentoEncontrado.getList("UsuarioSeguidos", String.class);
//            }
//            return aux;
//        }catch (MongoSocketException e) {
//            System.err.println("🚨 ERROR DE CONEXIÓN: No se pudo conectar a la base de datos Culturarte.");
//            return aux;//mejorar si devuelvo el usuario no sabe si funciona o no
//        } catch (Exception e) {
//            
//            System.err.println("ERROR INESPERADO al buscar los seguidos Del Usuario." +e.getMessage());
//            return aux;
//        }
//
//    }
//    
//    public boolean seguirUsr(String nick1, String nick2){
//        if (nick1.equals(nick2)) return false;
//        MongoClient conn=null;
//        
//        try{
//            //pido conexion
//            conn=PersistenciaMongo.getConnection("francoechaide_db_user","Hhn9xVioZZnm7bXk");
//            //traigo la bd que quiero 
//            MongoDatabase database=conn.getDatabase("Culturarte");
//            //establece la collecion con la que trabajare
//            MongoCollection<Document> coleccionUsuarios = database.getCollection("Usuarios");
//
//            Document usuario2 = coleccionUsuarios.find(Filters.eq("_id", nick2)).projection(Projections.include("_id")).first();
//            
//            if (usuario2 == null) {
//            
//                return false; 
//            }
//            
//            UpdateResult result = coleccionUsuarios.updateOne(
//            Filters.eq("_id", nick1), 
//            //anade el usuario solo si no lo  sigue ya 
//            Updates.addToSet("UsuarioSeguidos", nick2)
//            );
//            
//            
//            return result.getModifiedCount() == 1;
//
//        }catch (MongoSocketException e) {
//            System.err.println("🚨 ERROR DE CONEXIÓN: No se pudo conectar a la base de datos Culturarte.");
//            return false;//mejorar si devuelvo el usuario no sabe si funciona o no
//        } catch (Exception e) {
//            
//            System.err.println("ERROR INESPERADO al buscar los seguidos Del Usuario." +e.getMessage());
//            return false;
//        }
//
//    }
//    
//    //operacion para dejar de seguir a usuario
//    public boolean dejarDeSeguirUsuario(String nick1,String nick2){
//         if (nick1.equals(nick2)) return false;
//        MongoClient conn=null;
//        
//        try{
//            //pido conexion
//            conn=PersistenciaMongo.getConnection("francoechaide_db_user","Hhn9xVioZZnm7bXk");
//            //traigo la bd que quiero 
//            MongoDatabase database=conn.getDatabase("Culturarte");
//            //establece la collecion con la que trabajare
//            MongoCollection<Document> coleccionUsuarios = database.getCollection("Usuarios");
//
//            
//            
//            UpdateResult result = coleccionUsuarios.updateOne(
//            Filters.eq("_id", nick1), 
//            //anade el usuario solo si no lo  sigue ya 
//            pull("UsuarioSeguidos", nick2)
//            );
//            
//            
//            return result.getModifiedCount() == 1;
//
//        }catch (MongoSocketException e) {
//            System.err.println("🚨 ERROR DE CONEXIÓN: No se pudo conectar a la base de datos Culturarte.");
//            return false;//mejorar si devuelvo el usuario no sabe si funciona o no
//        } catch (Exception e) {
//            
//            System.err.println("ERROR INESPERADO al buscar los seguidos Del Usuario." +e.getMessage());
//            return false;
//        }
//    }
////    // devuelvo dtoUusario con la info minima de los que sigue el usuario identificado por nick (se usa en el CulturarteWeb)
////    public List<DTOUsuario> getSeguidos(String nick){
////         List<DTOUsuario> listSeguidos=new ArrayList<>();
////         MongoClient conn=null;
////        
////         try {
////            //pido conexion
////            conn=PersistenciaMongo.getConnection("francoechaide_db_user","Hhn9xVioZZnm7bXk");
////            //traigo la bd que quiero 
////            MongoDatabase database=conn.getDatabase("Culturarte");
////            //establece la collecion con la que trabajare
////            MongoCollection<Document> coleccionUsuarios = database.getCollection("Usuarios");
////            
////            //no traigo todo solo el campo seguido del usuario identificado por nick
////            Document documentoEncontrado = coleccionUsuarios.find(Filters.eq("_id", nick)).projection(Projections.include("UsuarioSeguidos")).first();
////            
////            //verifico que se encuentre el usuario
////            if (documentoEncontrado != null) {
////                
////                List<String> usuariosSeguidos = documentoEncontrado.getList("UsuarioSeguidos", String.class);
////                
////                //si sigue a otros usuarioos quiero recorrer la lista
////                if(usuariosSeguidos!=null && !usuariosSeguidos.isEmpty()){
////                    //me traigo en una consulta los documentos de Usuarios seguidos
////                    coleccionUsuarios.find(Filters.in("_id", usuariosSeguidos)).forEach(usuSeguido -> {
////                        if ("Proponente".equals(usuSeguido.getString("tipo_usuario"))) {
////                            listSeguidos.add(documentToDtoProponente(usuSeguido));
////                        } else if ("Colaborador".equals(usuSeguido.getString("tipo_usuario"))) {
////                            listSeguidos.add(documentToDtoColaborador(usuSeguido));
////                        }
////                    });
////                }
////            }
////            return listSeguidos;
////         }catch (MongoSocketException e) {
////            System.err.println("🚨 ERROR DE CONEXIÓN: No se pudo conectar a la base de datos Culturarte.");
////            return listSeguidos;//mejorar si devuelvo el usuario no sabe si funciona o no
////        } catch (Exception e) {
////            
////            System.err.println("ERROR INESPERADO al buscar los seguidos Del Usuario." +e.getMessage());
////            return listSeguidos;
////        }
////    }
//    
   
 
}
    

