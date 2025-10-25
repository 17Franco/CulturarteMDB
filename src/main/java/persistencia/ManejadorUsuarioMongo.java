
package persistencia;


import dev.morphia.query.FindOptions;
import dev.morphia.DeleteOptions;
import dev.morphia.query.Sort;
import dev.morphia.Datastore;
import java.util.List;
import java.util.ArrayList;
import com.mongodb.MongoSocketException;



import dev.morphia.query.filters.Filters;

import java.util.ArrayList;

import java.util.HashMap;

import java.util.Map;

import java.util.TimeZone;
import logica.DTO.DTOColaborador;

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
    
    private Usuario buscarUsuarioPorNick(Datastore datastore, String nick) {
        
        Usuario c = datastore.find(Colaborador.class).filter(Filters.eq("_id", nick)).first();
        if (c != null) {
            return c;
        }

        
        Usuario p = datastore.find(Proponente.class).filter(Filters.eq("_id", nick)).first();
        if (p != null) {
            return p;
        }

        
        return null;
    }
    //coprueba si el email ya a sido registrado por algun usuario
    public boolean emailUsado(String email){
         
        try{
            Datastore datastore = PersistenciaMongo.getDatastore("francoechaide_db_user", "Hhn9xVioZZnm7bXk");
            String emailParaBusqueda = email.trim();
           
            Usuario c = datastore.find(Colaborador.class).filter(Filters.eq("Email", emailParaBusqueda)).first();
            Usuario p = datastore.find(Proponente.class).filter(Filters.eq("Email", emailParaBusqueda)).first();
            
            return c!=null || p!=null;
        }catch (MongoSocketException e) {
            System.err.println("🚨 ERROR DE CONEXIÓN: No se pudo conectar a la base de datos Culturarte.");
            return false; 
        } catch (Exception e) {
            
            System.err.println("ERROR INESPERADO al averiguar el tipo de Usuario" +e.getMessage());
            return false; 
        } 
   
    }
    
    //compruebo que existe un usuario identificado por nick
    public boolean existe(String nick){
        
        
     try{
        Datastore datastore = PersistenciaMongo.getDatastore("francoechaide_db_user", "Hhn9xVioZZnm7bXk");
        
        Usuario usuario= buscarUsuarioPorNick(datastore, nick);
 
        return usuario!=null;

      }catch (MongoSocketException e) {
            System.err.println("🚨 ERROR DE CONEXIÓN: No se pudo conectar a la base de datos Culturarte.");
            return false; 
        } catch (Exception e) {

            System.err.println("ERROR INESPERADO al averiguar el tipo de Usuario" +e.getMessage());
            return false; 
        }
    }
    
    //DEVUELVO TODOS LOS USUARIOS COMO DTO A CONTROLLER
    public Map<String, DTOUsuario> getUsuarios() {
        
        Map<String, DTOUsuario> resu = new HashMap<>();

        try{
            Datastore datastore = PersistenciaMongo.getDatastore("francoechaide_db_user", "Hhn9xVioZZnm7bXk");
            //Traigo todos los usuarios en una lista 
            List<Colaborador> listUsuariosC=datastore.find(Colaborador.class).iterator().toList();
            List<Proponente> listUsuariosP=datastore.find(Proponente.class).iterator().toList();
            List<Usuario> listUsuarios=new ArrayList<>();
            
            listUsuarios.addAll(listUsuariosC);

            listUsuarios.addAll(listUsuariosP);
            
            for(Usuario u: listUsuarios){
               if(u instanceof Colaborador){
                    DTOColaborador c= new DTOColaborador( (Colaborador) u);
                    resu.put(c.getNickname(), c);
                }else{
                    DTOProponente p=new DTOProponente((Proponente) u);
                   resu.put(p.getNickname(), p);
                }   
               }
           
           return resu;
        }catch (MongoSocketException e) {
            System.err.println("🚨 ERROR DE CONEXIÓN: No se pudo conectar a la base de datos Culturarte.");
            return resu; 
        } catch (Exception e) {
            
            System.err.println("ERROR INESPERADO al devolver los usuarios." +e.getMessage());
            
            return resu;
        }
    }

    //devuelvo una Intancia de Usuario
    public Usuario getUsuario(String nick) {

        Usuario usu=null;
        try {
            Datastore datastore = PersistenciaMongo.getDatastore("francoechaide_db_user", "Hhn9xVioZZnm7bXk");
            
            Usuario usuario= buscarUsuarioPorNick(datastore, nick);
 
        
            return usuario;
        }catch (MongoSocketException e) {
            System.err.println("🚨 ERROR DE CONEXIÓN: No se pudo conectar a la base de datos Culturarte.");
            return usu; // Devuelve null
        } catch (Exception e) {
            
            System.err.println("ERROR INESPERADO al buscar el Usuario." +e.getMessage());
            return usu; // Devuelve null
        }
    }

    //devuelvo solo los nick de los seguidos del usuario identificado por nick
    public List<String> listaSeguidos(String nick){
        List<String> aux = new ArrayList<>();
        try{
            Datastore datastore = PersistenciaMongo.getDatastore("francoechaide_db_user", "Hhn9xVioZZnm7bXk");
            
            //no traigo todo solo el campo seguido del usuario identificado por nick
            Usuario usuario= buscarUsuarioPorNick(datastore, nick);
           
            //verifico que se encuentre el usuario
            if (usuario != null && usuario.getUsuarioSeguido() != null) {
                for(Usuario u:usuario.getUsuarioSeguido().values()){
                    aux.add(u.getNickname());
                }
            }
            return aux;
        }catch (MongoSocketException e) {
            System.err.println("🚨 ERROR DE CONEXIÓN: No se pudo conectar a la base de datos Culturarte.");
            return aux;//mejorar si devuelvo el usuario no sabe si funciona o no
        } catch (Exception e) {
            
            System.err.println("ERROR INESPERADO al buscar los seguidos Del Usuario." +e.getMessage());
            return aux;
        }

    }
    
    
    public boolean seguirUsr(String nick1, String nick2){
        if (nick1.equals(nick2)) return false;
       
        
        try{
            Datastore datastore = PersistenciaMongo.getDatastore("francoechaide_db_user", "Hhn9xVioZZnm7bXk");
            Usuario usuario2 = buscarUsuarioPorNick(datastore, nick2);
            if (usuario2 == null) {
                return false;
            }

            Usuario seguidor = buscarUsuarioPorNick(datastore, nick1);
        
            if (seguidor != null) {
                seguidor.seguir(usuario2); 
                datastore.save(seguidor);
                return true;
            }
        
            return false;

        }catch (MongoSocketException e) {
            System.err.println("🚨 ERROR DE CONEXIÓN: No se pudo conectar a la base de datos Culturarte.");
            return false;//mejorar si devuelvo el usuario no sabe si funciona o no
        } catch (Exception e) {
            
            System.err.println("ERROR INESPERADO al buscar los seguidos Del Usuario." +e.getMessage());
            return false;
        }

    }
    
    //operacion para dejar de seguir a usuario
    public boolean dejarDeSeguirUsuario(String nick1,String nick2){
         if (nick1.equals(nick2)) return false;
        
        
        try{
          Datastore datastore = PersistenciaMongo.getDatastore("francoechaide_db_user", "Hhn9xVioZZnm7bXk");
            Usuario usuario2 = buscarUsuarioPorNick(datastore, nick2);
            if (usuario2 == null) {
                return false;
            }

            Usuario seguidor = buscarUsuarioPorNick(datastore, nick1);
        
            if (seguidor != null) {
//                seguidor.getUsuarioSeguido();
//                System.out.println(usuario2!=null);
                seguidor.getUsuarioSeguido().remove(usuario2.getNickname());
                datastore.save(seguidor);
                return true;
            }
        
            return false;

        }catch (MongoSocketException e) {
            System.err.println("🚨 ERROR DE CONEXIÓN: No se pudo conectar a la base de datos Culturarte.");
            return false;//mejorar si devuelvo el usuario no sabe si funciona o no
        } catch (Exception e) {
            
            System.err.println("ERROR INESPERADO al buscar los seguidos Del Usuario." +e.getMessage());
            return false;
        }
    }
    
//     devuelvo dtoUusario con la info minima de los que sigue el usuario identificado por nick (se usa en el CulturarteWeb)
//    public List<DTOUsuario> getSeguidos(String nick){
//         List<DTOUsuario> listSeguidos=new ArrayList<>();
//         MongoClient conn=null;
//        
//         try {
//            pido conexion
//            conn=PersistenciaMongo.getConnection("francoechaide_db_user","Hhn9xVioZZnm7bXk");
//            traigo la bd que quiero 
//            MongoDatabase database=conn.getDatabase("Culturarte");
//            establece la collecion con la que trabajare
//            MongoCollection<Document> coleccionUsuarios = database.getCollection("Usuarios");
//            
//            no traigo todo solo el campo seguido del usuario identificado por nick
//            Document documentoEncontrado = coleccionUsuarios.find(Filters.eq("_id", nick)).projection(Projections.include("UsuarioSeguidos")).first();
//            
//            verifico que se encuentre el usuario
//            if (documentoEncontrado != null) {
//                
//                List<String> usuariosSeguidos = documentoEncontrado.getList("UsuarioSeguidos", String.class);
//                
//                si sigue a otros usuarioos quiero recorrer la lista
//                if(usuariosSeguidos!=null && !usuariosSeguidos.isEmpty()){
//                    me traigo en una consulta los documentos de Usuarios seguidos
//                    coleccionUsuarios.find(Filters.in("_id", usuariosSeguidos)).forEach(usuSeguido -> {
//                        if ("Proponente".equals(usuSeguido.getString("tipo_usuario"))) {
//                            listSeguidos.add(documentToDtoProponente(usuSeguido));
//                        } else if ("Colaborador".equals(usuSeguido.getString("tipo_usuario"))) {
//                            listSeguidos.add(documentToDtoColaborador(usuSeguido));
//                        }
//                    });
//                }
//            }
//            return listSeguidos;
//         }catch (MongoSocketException e) {
//            System.err.println("🚨 ERROR DE CONEXIÓN: No se pudo conectar a la base de datos Culturarte.");
//            return listSeguidos;//mejorar si devuelvo el usuario no sabe si funciona o no
//        } catch (Exception e) {
//            
//            System.err.println("ERROR INESPERADO al buscar los seguidos Del Usuario." +e.getMessage());
//            return listSeguidos;
//        }
//    }
//    
//       
//    devuelvo si es proponente o no
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
        //    
//    obtengo la info minima de la propuestas creadas de un proponente 
//    public Set<DTOPropuesta> getPropuestasCreadasPorProponente(String nick){
//            em = PersistenciaManager.getEntityManager();
//               Proponente  p=em.find(Proponente.class,nick);
//           Set<DTOPropuesta> resu=new HashSet<>();
//            try{
//                DTOProponente dtoProp=new DTOProponente(p);
//                for(Propuesta prop: p.getPropCreadas().values()){
//                    Estado aux=prop.getHistorialEstados().get(0).getEstado();
//                    DTOPropuesta dtoP=new DTOPropuesta(prop,dtoProp);
//                    dtoP.setEstadoAct(aux);
//                    resu.add(dtoP);
//                }
//                return resu;
//            }finally{
//                em.close();
//            }
//        return resu;
//    }
//    
//    
//    
 
}
    

