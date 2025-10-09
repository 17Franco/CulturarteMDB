package persistencia;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import logica.Colaboracion.Colaboracion;
import logica.DTO.DTOColaboracion;
import logica.Propuesta.Propuesta;
import logica.Usuario.Colaborador;
import logica.DTO.TipoRetorno;
import logica.DTO.Estado;


public class ManejadorColaboracion {
    private ManejadorPropuesta mPropuesta=ManejadorPropuesta.getinstance();
    private ManejadorUsuario mUsuario= ManejadorUsuario.getInstance();
    private static ManejadorColaboracion instancia = null;
    
    private EntityManager em;
    
    public static ManejadorColaboracion getInstance() 
    {
        if (instancia == null){
            instancia = new ManejadorColaboracion();
        }   
        return instancia;
    }
    
    public Set< DTOColaboracion> getColaboraciones(){
        // iniializo la variable para retornar
        Set<DTOColaboracion> results=new HashSet<>();
       
        // obtengo la instancia del EM
        em= PersistenciaManager.getEntityManager();//instancia del manegador de la persistencia 
        try{
            // Traigo de la base de datos todas las colaboraciones
            List<Colaboracion> listaColaboraciones = em
                    .createQuery("SELECT c FROM Colaboracion c", Colaboracion.class)
                    .getResultList();
            for(Colaboracion colab: listaColaboraciones){
                // Convierto cada colaboracion en un dto (abreviado)
                DTOColaboracion dtoColab=new DTOColaboracion(
                        colab.getTipoRetorno(),
                        colab.getMonto(),
                        colab.getColaborador().getNickname(),
                        colab.getPropuesta().getTitulo(),
                        colab.getCreado()
                );
                dtoColab.setId(colab.getId());
                // Agrego cada dto a la lista de resultados
                results.add( dtoColab);
            }
        }
        finally{
            // Cierro el EM en cualquier caso
            em.close();// cierro el manejador 
        }
        
        return results;
    }
    
    public void addColaboracion(DTOColaboracion colaboracion){
        //AlmacenColaboraciones.add();
        Propuesta p= mPropuesta.getPropuesta(colaboracion.getPropuesta());
        Colaborador c= (Colaborador) mUsuario.getUsuario(colaboracion.getColaborador());
        Colaboracion colab= new Colaboracion(colaboracion,c,p);
    
        em= PersistenciaManager.getEntityManager();//instancia del manegador de la persistencia 
        EntityTransaction t = em.getTransaction(); // intancia de una transaccion nesesario si se hace alta baja y modificado 
        try{
            t.begin(); // aca inicio transaccion
          
            em.persist(colab);// persisto los datos 
            t.commit();// los aseguro  
        }
        catch(Exception e){
            t.rollback();    // en caso de error hace rollback
        }
        em.close();// cierro el manejador 
    }
    
    public void deleteColaboracion(Long id){
        em= PersistenciaManager.getEntityManager();//instancia del manegador de la persistencia 
        EntityTransaction t = em.getTransaction(); // intancia de una transaccion nesesario si se hace alta baja y modificado 
        try{
            t.begin(); // aca inicio transaccion
            
            Colaboracion c= em.find(Colaboracion.class, id);
            Propuesta p=em.find(Propuesta.class, c.getPropuesta().getTitulo());
            p.getAporte().remove(c); 
            
            //tengo actualizar despues de remover la colaboracion y en una misma transaccion
            if (p != null) {
                
                   int montoRecibido = 0;
                   for (Colaboracion co : p.getAporte()) {
                       montoRecibido += co.getMonto();
                   }

                if (montoRecibido == 0) {
                    p.addEstHistorial(Estado.PUBLICADA);
                } else if (montoRecibido > 0 && montoRecibido != p.getMontoTotal()) {
                    p.addEstHistorial(Estado.EN_FINANCIACION);
                } else if (montoRecibido > 0 && montoRecibido == p.getMontoTotal()) {
                    p.addEstHistorial(Estado.FINANCIADA);
                }

            }
           // em.createQuery("DELETE FROM Colaboracion c WHERE c.id = :id").setParameter("id", id).executeUpdate();
            
           em.remove(c); //me aseguro de eliminar por ophralremove cuando p.getAporte().remove(c); seria ya eliminado 
           
           t.commit();
        }
        catch(Exception e){
            t.rollback();    // en caso de error hace rollback
        }
        em.close();// cierro el manejador 
    }
    
    public void cargarDatosColaboracion(){
       EntityManager local = PersistenciaManager.getEntityManager();
        ManejadorUsuario mUsuario=ManejadorUsuario.getInstance();
        
        
        Colaborador c1=local.find(Colaborador.class, "novick");
        Propuesta p1=local .find(Propuesta.class, "Cine en el Botánico");
        if(!mUsuario.existeColaboracion(c1.getNickname(), p1.getTitulo())){
            DTOColaboracion dto1=new DTOColaboracion();
            dto1.setColaborador("novick");
            dto1.setPropuesta("Cine en el Botanico");
            dto1.setCreado(LocalDate.of(2017,05,20));
            dto1.setMonto(50000);
            dto1.setTipoRetorno(TipoRetorno.PorcentajeGanancia);
            addColaboracion(dto1);
        }
        
        Colaborador c2=local.find(Colaborador.class, "robinh");
        Propuesta p2=local.find(Propuesta.class, "Cine en el Botánico");
        if(!mUsuario.existeColaboracion(c2.getNickname(), p2.getTitulo())){
            DTOColaboracion dto2 = new DTOColaboracion();
            dto2.setColaborador("robinh");
            dto2.setPropuesta("Cine en el Botánico");
            dto2.setCreado(LocalDate.of(2017, 5, 24));
            dto2.setMonto(50000);
            dto2.setTipoRetorno(TipoRetorno.PorcentajeGanancia);
            addColaboracion(dto2);
        }
        
        Colaborador c3=local.find(Colaborador.class, "nicoJ");
        Propuesta p3=local.find(Propuesta.class, "Cine en el Botánico");
        if(!mUsuario.existeColaboracion(c3.getNickname(), p3.getTitulo())){
            DTOColaboracion dto3 = new DTOColaboracion();
            dto3.setColaborador("nicoJ");
            dto3.setPropuesta("Cine en el Botánico");
            dto3.setCreado(LocalDate.of(2017, 5, 30));
            dto3.setMonto(50000);
            dto3.setTipoRetorno(TipoRetorno.PorcentajeGanancia);
            addColaboracion(dto3);
        }
        Colaborador c4=local.find(Colaborador.class, "marcelot");
        Propuesta p4=local.find(Propuesta.class, "Religiosamente");
        if(!mUsuario.existeColaboracion(c4.getNickname(), p4.getTitulo())){
            DTOColaboracion dto4 = new DTOColaboracion();
            dto4.setColaborador("marcelot");
            dto4.setPropuesta("Religiosamente");
            dto4.setCreado(LocalDate.of(2017, 6, 30));
            dto4.setMonto(200000);
            dto4.setTipoRetorno(TipoRetorno.PorcentajeGanancia);
            addColaboracion(dto4);
        }
        Colaborador c5=local.find(Colaborador.class, "Tiajaci");
        Propuesta p5=local.find(Propuesta.class, "Religiosamente");
        if(!mUsuario.existeColaboracion(c5.getNickname(), p5.getTitulo())){
            DTOColaboracion dto5 = new DTOColaboracion();
            dto5.setColaborador("Tiajaci");
            dto5.setPropuesta("Religiosamente");
            dto5.setCreado(LocalDate.of(2017, 7, 1));
            dto5.setMonto(500);
            dto5.setTipoRetorno(TipoRetorno.EntradaGratis);
            addColaboracion(dto5);
        }
        Colaborador c6=local.find(Colaborador.class, "Mengano");
        Propuesta p6=local.find(Propuesta.class, "Religiosamente");
        if(!mUsuario.existeColaboracion(c6.getNickname(),p6.getTitulo())){
            DTOColaboracion dto6 = new DTOColaboracion();
            dto6.setColaborador("Mengano");
            dto6.setPropuesta("Religiosamente");
            dto6.setCreado(LocalDate.of(2017, 7, 7));
            dto6.setMonto(600);
            dto6.setTipoRetorno(TipoRetorno.EntradaGratis);
            addColaboracion(dto6);
        }
        
        Colaborador c7=local.find(Colaborador.class, "novick");
        Propuesta p7=local.find(Propuesta.class, "Religiosamente");
        if(!mUsuario.existeColaboracion(c7.getNickname(), p7.getTitulo())){
            DTOColaboracion dto7 = new DTOColaboracion();
            dto7.setColaborador("novick");
            dto7.setPropuesta("Religiosamente");
            dto7.setCreado(LocalDate.of(2017, 7, 10));
            dto7.setMonto(50000);
            dto7.setTipoRetorno(TipoRetorno.PorcentajeGanancia);
            addColaboracion(dto7);
        }
        
        Colaborador c8=local.find(Colaborador.class, "sergiop");
        Propuesta p8=local.find(Propuesta.class, "Religiosamente");
        if(!mUsuario.existeColaboracion(c8.getNickname(), p8.getTitulo())){
            DTOColaboracion dto8 = new DTOColaboracion();
            dto8.setColaborador("sergiop");
            dto8.setPropuesta("Religiosamente");
            dto8.setCreado(LocalDate.of(2017, 7, 15));
            dto8.setMonto(50000);
            dto8.setTipoRetorno(TipoRetorno.PorcentajeGanancia);
            addColaboracion(dto8);
        }
        Colaborador c9=local.find(Colaborador.class, "robinh");
        Propuesta p9=local.find(Propuesta.class, "El Pimiento Indomable");
        if(!mUsuario.existeColaboracion(c9.getNickname(), p9.getTitulo())){
            DTOColaboracion dto9 = new DTOColaboracion();
            dto9.setColaborador("marcelot");
            dto9.setPropuesta("El Pimiento Indomable");
            dto9.setCreado(LocalDate.of(2017, 8, 1));
            dto9.setMonto(200000);
            dto9.setTipoRetorno(TipoRetorno.PorcentajeGanancia);
            addColaboracion(dto9);
        }
       Colaborador c10=local.find(Colaborador.class, "sergiop");
        Propuesta p10=local.find(Propuesta.class, "El Pimiento Indomable");
        if(!mUsuario.existeColaboracion(c10.getNickname(), p10.getTitulo())){
            DTOColaboracion dto10 = new DTOColaboracion();
            dto10.setColaborador("sergiop");
            dto10.setPropuesta("El Pimiento Indomable");
            dto10.setCreado(LocalDate.of(2017, 8, 3));
            dto10.setMonto(80000);
            dto10.setTipoRetorno(TipoRetorno.PorcentajeGanancia);
            addColaboracion(dto10);
        }
        Colaborador c11=local.find(Colaborador.class, "chino");
        Propuesta p11=local.find(Propuesta.class, "Pilsen Rock");
        if(!mUsuario.existeColaboracion(c11.getNickname(), p11.getTitulo())){
            DTOColaboracion dto11 = new DTOColaboracion();
            dto11.setColaborador("chino");
            dto11.setPropuesta("Pilsen Rock");
            dto11.setCreado(LocalDate.of(2017, 8, 5));
            dto11.setMonto(50000);
            dto11.setTipoRetorno(TipoRetorno.EntradaGratis);
            addColaboracion(dto11);
        }
        Colaborador c12=local.find(Colaborador.class, "novick");
        Propuesta p12=local.find(Propuesta.class, "Pilsen Rock");
        
        if(!mUsuario.existeColaboracion(c12.getNickname(), p12.getTitulo())){
            DTOColaboracion dto12 = new DTOColaboracion();
            dto12.setColaborador("novick");
            dto12.setPropuesta("Pilsen Rock");
            dto12.setCreado(LocalDate.of(2017, 8, 10));
            dto12.setMonto(120000);
            dto12.setTipoRetorno(TipoRetorno.PorcentajeGanancia);
            addColaboracion(dto12);
        }
        Colaborador c13=local.find(Colaborador.class, "tonyp");
        Propuesta p13=local.find(Propuesta.class, "Pilsen Rock");
        
        if(!mUsuario.existeColaboracion(c13.getNickname(), p13.getTitulo())){
            DTOColaboracion dto13 = new DTOColaboracion();
            dto13.setColaborador("tonyp");
            dto13.setPropuesta("Pilsen Rock");
            dto13.setCreado(LocalDate.of(2017, 8, 15));
            dto13.setMonto(120000);
            dto13.setTipoRetorno(TipoRetorno.EntradaGratis);
            addColaboracion(dto13);
        }
        
        Colaborador c14=local.find(Colaborador.class, "sergiop");
        Propuesta p14=local.find(Propuesta.class, "Romeo y Julieta");
        if(!mUsuario.existeColaboracion(c14.getNickname(), p14.getTitulo())){
            DTOColaboracion dto14 = new DTOColaboracion();
            dto14.setColaborador("sergiop");
            dto14.setPropuesta("Romeo y Julieta");
            dto14.setCreado(LocalDate.of(2017, 8, 13));
            dto14.setMonto(100000);
            dto14.setTipoRetorno(TipoRetorno.PorcentajeGanancia);
            addColaboracion(dto14);
        }
        
        Colaborador c15=local.find(Colaborador.class, "marcelot");
        Propuesta p15=local.find(Propuesta.class, "Romeo y Julieta");
        if(!mUsuario.existeColaboracion(c15.getNickname(), p15.getTitulo())){
            DTOColaboracion dto15 = new DTOColaboracion();
            dto15.setColaborador("marcelot");
            dto15.setPropuesta("Romeo y Julieta");
            dto15.setCreado(LocalDate.of(2017, 8, 14));
            dto15.setMonto(200000);
            dto15.setTipoRetorno(TipoRetorno.PorcentajeGanancia);
            addColaboracion(dto15);
        }
        
        Colaborador c16=local.find(Colaborador.class, "tonyp");
        Propuesta p16=local.find(Propuesta.class, "Un día de Julio");
        if(!mUsuario.existeColaboracion(c16.getNickname(), p16.getTitulo())){      
            DTOColaboracion dto16 = new DTOColaboracion();
            dto16.setColaborador("tonyp");
            dto16.setPropuesta("Un día de Julio");
            dto16.setCreado(LocalDate.of(2017, 8, 15));
            dto16.setMonto(30000);
            dto16.setTipoRetorno(TipoRetorno.EntradaGratis);
            addColaboracion(dto16);
        }
        
        Colaborador c17=local.find(Colaborador.class, "marcelot");
        Propuesta p17=local.find(Propuesta.class, "Un día de Julio");
        if(!mUsuario.existeColaboracion(c17.getNickname(), p17.getTitulo())){
            DTOColaboracion dto17 = new DTOColaboracion();
            dto17.setColaborador("marcelot");
            dto17.setPropuesta("Un día de Julio");
            dto17.setCreado(LocalDate.of(2017, 8, 17));
            dto17.setMonto(150000);
            dto17.setTipoRetorno(TipoRetorno.PorcentajeGanancia);
            addColaboracion(dto17);
        }
    
    }
    
}