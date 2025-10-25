
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
import java.util.HashSet;

import java.util.Map;
import java.util.Set;

import java.util.TimeZone;
import logica.Colaboracion.Colaboracion;
import logica.DTO.DTOColaboracion;
import logica.DTO.DTOColaborador;

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
     
    //obtengo la info minima de la propuestas creadas de un proponente 
    public Set<DTOPropuesta> getPropuestasCreadasPorProponente(String nick){
            
           
        Set<DTOPropuesta> resu=new HashSet<>();
        try{
            Datastore datastore = PersistenciaMongo.getDatastore("francoechaide_db_user", "Hhn9xVioZZnm7bXk");
            Proponente p = (Proponente) buscarUsuarioPorNick(datastore, nick);
            DTOProponente dtoProp=new DTOProponente(p);
            for(Propuesta prop: p.getPropCreadas().values()){
                Estado aux=prop.getHistorialEstados().get(0).getEstado();
                DTOPropuesta dtoP=new DTOPropuesta(prop,dtoProp);
                dtoP.setEstadoAct(aux);
                resu.add(dtoP);
            }
            return resu;
        }catch (MongoSocketException e) {
            System.err.println("🚨 ERROR DE CONEXIÓN: No se pudo conectar a la base de datos Culturarte.");
            return resu;//mejorar si devuelvo el usuario no sabe si funciona o no
        } catch (Exception e) {

            System.err.println("ERROR INESPERADO al buscar los seguidos Del Usuario." +e.getMessage());
            return resu;
        }
        
    }
    
    //devuelve una lista de las  dto colaboraciones que son de un usuario identificado por nick
    public List<DTOColaboracion> getDTOColaboraciones(String nick){
         //List<DTOColaboracion> resu=new ArrayList<>();
        
        List<DTOColaboracion> resu=new ArrayList<>();
        try{
            Datastore datastore = PersistenciaMongo.getDatastore("francoechaide_db_user", "Hhn9xVioZZnm7bXk");
            Colaborador c = (Colaborador) buscarUsuarioPorNick(datastore, nick);
             for(Colaboracion colab: c.getColaboraciones()){
               
                DTOColaboracion DTOColab = new DTOColaboracion(colab);
                resu.add(DTOColab);
            }
            return resu;
        }catch (MongoSocketException e) {
            System.err.println("🚨 ERROR DE CONEXIÓN: No se pudo conectar a la base de datos Culturarte.");
            return resu;//mejorar si devuelvo el usuario no sabe si funciona o no
        } catch (Exception e) {

            System.err.println("ERROR INESPERADO al buscar los seguidos Del Usuario." +e.getMessage());
            return resu;
        }
    }
    
    
    
 
}
    

