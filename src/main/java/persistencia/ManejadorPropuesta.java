/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import logica.Categoria.Categoria;
import logica.DTO.DTOPropuesta;
import logica.DTO.DTORegistro_Estado;
import logica.Propuesta.Registro_Estado;
import logica.Colaboracion.Colaboracion;
import logica.DTO.DTOColaboracion;
import logica.DTO.DTOColaborador;
import logica.DTO.DTOProponente;
import logica.Propuesta.Propuesta;
import logica.Usuario.Proponente;
import logica.Usuario.Colaborador;
import logica.DTO.Estado;
import logica.DTO.TipoRetorno;


public class ManejadorPropuesta {
    private static ManejadorPropuesta instancia = null;

    private ManejadorPropuesta() {}
    
    public static ManejadorPropuesta getinstance() {
        if (instancia == null)
            instancia = new ManejadorPropuesta();
        return instancia;
    }
    public void nuevaPropuesta(Propuesta p) {
        if (p == null) {
            return;
        }
        EntityManager em = PersistenciaManager.getEntityManager();
        EntityTransaction t = em.getTransaction();
        t.begin();
        try {
            Proponente prop = em.find(Proponente.class, p.getProponente().getNickname());
            if (prop != null) {
                p.setProponente(prop);
               // prop.getPropCreadas().put(p.getTitulo(), p);
            }
            
            em.persist(p);
            t.commit();
        } catch (Exception e) {
            if (t.isActive()) {
                t.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
    public boolean existeProp(String titulo) {
        EntityManager em = PersistenciaManager.getEntityManager();
        try {
            Propuesta prop = em.find(Propuesta.class, titulo);
            return prop != null;
        } finally {
            em.close();
        }
    } 
    public Propuesta buscarPropuestaPorTitulo(String titulo) {
        EntityManager em = PersistenciaManager.getEntityManager();
        try {
            
            Propuesta retorno = em.find(Propuesta.class, titulo);
            propuestaStatusUpdater(null,new DTOPropuesta(titulo,retorno.getFechaExpiracion()));
            
            return em.find(Propuesta.class, titulo);
  
        } finally {
            em.close();
        }
    }
    public List<Propuesta> listarPropuestas() {
        EntityManager em = PersistenciaManager.getEntityManager();
        try {
            return em.createQuery("SELECT p FROM Propuesta p", Propuesta.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }
    public Set<DTOPropuesta> obtenerPropuestas(String estadoInput) 
    {
        EntityManager em = PersistenciaManager.getEntityManager();
        Set<DTOPropuesta> result = new HashSet<>();
 
        try 
        {
           
            TypedQuery<Propuesta> q = em.createQuery("SELECT DISTINCT p FROM Propuesta p", Propuesta.class);
            List<Propuesta> propuestas = q.getResultList();
                        
            
            for (Propuesta p : propuestas)
            {   
                p.getHistorialEstados().size(); //Hibernate no me quiere cargar estas listas desde el inixio (forzado)
                p.getRetorno().size();
                p.getAporte().size();

                if(!estadoInput.isEmpty())  //Solo almacenará propuestas que coincidan en el estado imputeado
                {

                    if(p.getUltimoEstado() != null && p.getUltimoEstadoString().equals(estadoInput))   //Si existe ultimo estado y coincide con el necesario
                    {       
                        DTOPropuesta temp = new DTOPropuesta();
                        temp.extraerDatosPropuesta(p);           
                        result.add(temp);                       
                    }    
                }
                else    //Almacena todas las propuestas
                {
                    DTOPropuesta dto = new DTOPropuesta();
                    dto.extraerDatosPropuesta(p);
                    result.add(dto);
                }
            }
        } 
        finally
        {
            em.close();
        }
        
        propuestaStatusUpdater(result,null); //Se analiza si hay propuestas con el estado desactualizado
        
        return result;
    }
    
    public void propuestaStatusUpdater(Set<DTOPropuesta> setInput, DTOPropuesta singleInput)
    {
        //Se puede optar por enviar un Set o un solo DTO, sólo pone un null en el que no necesites...
        //Con esta función se consigue actualizar el estado de las propuestas vencidas sin depender de servicios extras

        //Si se envió un Set:
        if(setInput != null && !setInput.isEmpty())
        {

            for (DTOPropuesta ct : setInput)   //Se analiza el estado actual de la propuesta y su fecha de cierre para verificar si debe ser cancelada.
            {
                if(ct.getFechaExpiracion() != null && ct.getFechaExpiracion().isBefore(LocalDate.now()))  //Si la fecha actual es mayor a la de vencimiento...
                {
                    cancelarPropuestaSeleccionada(ct.getTitulo());  //Se cancela.

                    //También se actualiza para poder ser manipulado.
                    ct.setEstadoAct(Estado.CANCELADA);  
                    ct.setHistorialEstados(new DTORegistro_Estado(LocalDate.now(),Estado.CANCELADA));

                }
            }    
        }
        
        //Si se envió un DTO unicamente:
        if(singleInput != null && singleInput.getFechaExpiracion() != null && singleInput.getFechaExpiracion().isBefore(LocalDate.now()))
        {
            cancelarPropuestaSeleccionada(singleInput.getTitulo());
            singleInput.setEstadoAct(Estado.CANCELADA);
            singleInput.setHistorialEstados(new DTORegistro_Estado(LocalDate.now(),Estado.CANCELADA));
        }
        
        //El set y o el dto single quedan modificados! se pueden usar con seguridad.
    }
    
    public void cancelarPropuestaSeleccionada(String tituloPropuesta)
    {
        
        EntityManager dbManager = PersistenciaManager.getEntityManager();
        EntityTransaction transaccion = dbManager.getTransaction();
        
        transaccion.begin();
        
        try 
        {
            Propuesta temp = dbManager.find(Propuesta.class, tituloPropuesta);
            
            if (temp != null) 
            {
                temp.addEstHistorial(Estado.CANCELADA); 
            }
            
            transaccion.commit();
        }
        catch (Exception e) 
        {
            if (transaccion.isActive()) 
            {
                transaccion.rollback();
            }    
        } 
        finally 
        {
            dbManager.close();
        }
        
    }

    public boolean extenderFinanciacion(String tituloProp)
    {
        EntityManager dbManager = PersistenciaManager.getEntityManager();
        EntityTransaction transaccion = dbManager.getTransaction();
        boolean resultado = false;
        
        transaccion.begin();

        try 
        {
            Propuesta temp = dbManager.find(Propuesta.class, tituloProp);

            if (temp != null && (temp.getUltimoEstado().getEstado() == Estado.PUBLICADA || temp.getUltimoEstado().getEstado() == Estado.EN_FINANCIACION)) 
            {
                temp.addEstHistorial(Estado.PUBLICADA); //Se añade de nuevo el estado publicada y se extiende el tiempo.
                resultado = true;
            }

            transaccion.commit();
        } 
        catch (Exception e) 
        {
            if (transaccion.isActive()) 
            {
                transaccion.rollback();
                
            }
        } 
        finally 
        {
            dbManager.close();
            
        }
        return resultado;
    }
    
    public boolean nuevoComentario(String comentario,String userNick,String tituloPropuesta)
    {
        EntityManager dbManager = PersistenciaManager.getEntityManager();
        EntityTransaction transaccion = dbManager.getTransaction();
        boolean resultado = false;
        
        transaccion.begin();

        try 
        {
            Propuesta temp = dbManager.find(Propuesta.class, tituloPropuesta);

            if (temp != null && !temp.usuarioHaComentadoSN(userNick))  //Si user aun no ha comentado
            {
                temp.addNewComentario(userNick, comentario);    //Se añade comentario
                resultado = true;
            }

            transaccion.commit();
        } 
        catch (Exception e) 
        {
            if (transaccion.isActive()) 
            {
                transaccion.rollback();
                
            }
        } 
        finally 
        {
            dbManager.close();
            
        }
        return resultado;
        
    }
    
    public List<DTOColaboracion> Colaboraciones_A_DTO(List<Colaboracion> input)
    {
        List<DTOColaboracion> almacen = new ArrayList<>();
        
        for (Colaboracion ct : input)
        {
            DTOProponente prop=new DTOProponente(ct.getPropuesta().getProponente());
            DTOColaborador c=new DTOColaborador(ct.getColaborador());
            DTOPropuesta p=new DTOPropuesta(ct.getPropuesta(),prop);
            almacen.add(new DTOColaboracion(ct.getTipoRetorno(),ct.getMonto(),ct.getColaborador().getNickname(),p.getTitulo(),ct.getCreado(),c,p));
        }
        
        return almacen;
    }
    public int getMontoRecaudado(String titulo) {
        EntityManager em = PersistenciaManager.getEntityManager();
        Propuesta p =em.find(Propuesta.class,titulo);
        try{
             if (p == null) {
                return 0;
            }

             int recaudado = 0;
            for (Colaboracion c : p.getAporte()) {
                recaudado += c.getMonto();
            }
            return recaudado;
        }finally{
            em.close();
        }
        
    }

    public List<String> listColaboradores(String titulo) {
        EntityManager em = PersistenciaManager.getEntityManager(); //
        Propuesta p = em.find(Propuesta.class,titulo);
        try{
                List<String> colab = new ArrayList<>();
           if (p != null) {
               for (Colaboracion c : p.getAporte()) {
                   colab.add(c.getColaborador().getNickname());
               }
           }
           return colab;
        }finally{
            em.close();
        }
       
    }
    public void actualizarEstado(String titulo) {
        EntityManager em = PersistenciaManager.getEntityManager();
        EntityTransaction t = em.getTransaction();

        try {
            t.begin();
            Propuesta p = em.find(Propuesta.class, titulo);
            if (p != null) {
                int montoRecibido = getMontoRecaudado(titulo);

                if (montoRecibido == 0) {
                    p.addEstHistorial(Estado.PUBLICADA);
                } else if (montoRecibido > 0 && montoRecibido != p.getMontoTotal()) {
                    p.addEstHistorial(Estado.EN_FINANCIACION);
                } else if (montoRecibido > 0 && montoRecibido == p.getMontoTotal()) {
                    p.addEstHistorial(Estado.FINANCIADA);
                }

                em.merge(p);
            }
            t.commit();
        } catch (Exception e) {
            if (t.isActive()) {
                t.rollback();
                e.printStackTrace();
            }
            
        } finally {
            em.close();
        }
    }
    public void eliminarColaboracion(String nick, String titulo) {
        EntityManager em = PersistenciaManager.getEntityManager();
        EntityTransaction t = em.getTransaction();

        try {
            t.begin();
            Propuesta p = em.find(Propuesta.class, titulo);
            if (p != null) {
                Colaboracion aux = p.getAporte().stream()
                        .filter(c -> c.getColaborador().getNickname().equals(nick))
                        .findFirst()
                        .orElse(null);

                if (aux != null) {
                    p.getAporte().remove(aux);
                    em.remove(em.contains(aux) ? aux : em.merge(aux));
                }
            }
            t.commit();
        } catch (Exception e) {
            if (t.isActive()) {
                t.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
    public Propuesta getPropuesta(String titulo) {
        EntityManager em = PersistenciaManager.getEntityManager();
        try {
            return em.find(Propuesta.class, titulo);  // busca por PK
        } finally {
            em.close();
        }
    }
    public DTOPropuesta getPropuestaDTO(String propuestaSel) {
        EntityManager em = PersistenciaManager.getEntityManager();
        try {
            Propuesta temp = em.find(Propuesta.class, propuestaSel);

            if (temp != null) {
                if (temp.getCategoria() != null && temp.getCategoria().getSubcategorias() != null) {
                    temp.getCategoria().getSubcategorias().size(); // fuerza la carga
                }
                temp.getHistorialEstados().size();
                temp.getRetorno().size();
                temp.getAporte().size();
                DTOPropuesta temp1 = new DTOPropuesta();
                temp1.extraerDatosPropuesta(temp);
                propuestaStatusUpdater(null,temp1); //Se analiza si hay propuestas con el estado desactualizado
                return temp1;
            } else {
                return null;
            }
        } finally {
            em.close();
        }
    }
    /*
    public DTOPropuesta getPropuestaDTO(String propuestaSel)
    {
        EntityManager em = PersistenciaManager.getEntityManager();
        try {
            Propuesta temp = em.find(Propuesta.class, propuestaSel);

            if (temp != null) {
                if (temp.getCategoria() != null && temp.getCategoria().getSubcategorias() != null) {
                    temp.getCategoria().getSubcategorias().size(); // fuerza la carga
                }
                temp.getHistorialEstados().size();
                temp.getRetorno().size();
                temp.getAporte().size();
                DTOPropuesta temp1 = new DTOPropuesta();
                temp1.extraerDatosPropuesta(temp);
                return temp1;
            } else {
                return null;
            }
        } finally {
            em.close();
        }  
    }
    */
    public int accionSobrePropuesta(String nickUsuario, DTOPropuesta propuestaSel) 
    {  
        //Permite habilitar botones en cliente web (CU Obtener propuestas):
        //Retorna:  
        //          1: El usuario es proponente.
        //          2: El usuario es colaborador.
        //          3: El usuario no ha participado aún en la propuesta.
        
        if(propuestaSel.nickProponenteToString().equals(nickUsuario))   //Si es proponente
        {
            
            return 1;
        }
        else
        {
            List<DTOColaboracion> t1 = propuestaSel.getAporte();
            
            for(DTOColaboracion ct : t1)
            {
                if(ct.getColaborador().equals(nickUsuario)) //Si es colaborador
                {
                    
                    return 2;
                }
            
            }
        }
        
        
        
        return 3;   //Si no es ninguno de los dos.
    }
    
    public void UpdatePropuesta(String titulo, String descripcion, String rutaImagen,String lugar, LocalDate fechaEvento, int precio, int montoTotal,List<TipoRetorno> retorno, String categoria, String usuario, Estado estado) {
       
        EntityManager em = PersistenciaManager.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            
            Propuesta propuesta = em.find(Propuesta.class, titulo);

            if (propuesta != null) {
                propuesta.setDescripcion(descripcion);
                propuesta.setImagne(rutaImagen);
                propuesta.setLugar(lugar);
                propuesta.setFecha(fechaEvento);
                propuesta.setPrecio(precio);
                propuesta.setMontoTotal(montoTotal);
                propuesta.setRetornos(retorno);
                Categoria cat = em.find(Categoria.class, categoria);
                propuesta.setCategoria(cat);
                propuesta.addEstHistorial(estado);
            }

            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
    
    public String obtenerNombreCreadorPropuesta(String titulo){
        EntityManager em = PersistenciaManager.getEntityManager();
        
        try{
            Propuesta p=em.find(Propuesta.class, titulo);
            return p.getProponente().getNickname();
        }finally{
        em.close();
        }
    }
    
    public String obtenerEstado(String titulo){
        EntityManager em = PersistenciaManager.getEntityManager();
        
        try{
            Propuesta p=em.find(Propuesta.class, titulo);
            return p.getUltimoEstadoString();
        }finally{
        em.close();
        }
    }
    public void cargarPropuesta() {
        EntityManager em = PersistenciaManager.getEntityManager();
        EntityTransaction t = em.getTransaction();
        try {
            t.begin();
            // Cine en el Botánico
            Propuesta pr1Existente = em.find(Propuesta.class, "Cine en el Botanico");
            if (pr1Existente == null) {
                Propuesta p1 = new Propuesta();
                Proponente proponente = em.find(Proponente.class, "diegop");
                p1.setProponente(proponente);
                p1.setTitulo("Cine en el Botanico");
                Categoria categoria = em.find(Categoria.class,"Cine al Aire Libre");
                p1.setCategoria(categoria);
                p1.setDescripcion("El 16 de Diciembre a la hora 20 se proyectará la película \"Clever\", en el Jardín Botánico (Av. 19 de Abril 1181) en el marco\n"
                                + "de las actividades realizadas por el ciclo Cultura al Aire Libre. El largometraje uruguayo de ficción Clever es dirigido por\n"
                                + "Federico Borgia y Guillermo Madeiro. Es apto para mayores de 15 años.");
                p1.setFecha(LocalDate.of(2017, 9, 16));
                p1.setLugar("Jardin Botanico");
                p1.setPrecio(200);
                p1.setMontoTotal(150000);
                List<TipoRetorno> retornoCEB = new ArrayList<>();
                retornoCEB.add(TipoRetorno.PorcentajeGanancia);
                p1.setRetornos(retornoCEB);
                p1.addEstHistorial(Estado.CANCELADA);
                p1.addEstHistorial(Estado.FINANCIADA);
                p1.addEstHistorial(Estado.EN_FINANCIACION);
                p1.addEstHistorial(Estado.PUBLICADA);
                p1.addEstHistorial(Estado.INGRESADA);
                p1.setImagne("");
                p1.setFechaPublicacion(LocalDate.now());
                em.persist(p1);
            }
            // Religiosamente
            Propuesta pr2Existente = em.find(Propuesta.class, "Religiosamente");
            if (pr2Existente == null) {
                Propuesta p2 = new Propuesta();
                Proponente proponente = em.find(Proponente.class, "hrubino");
                p2.setProponente(proponente);
                p2.setTitulo("Religiosamente");
                Categoria categoria = em.find(Categoria.class, "Parodistas");
                p2.setCategoria(categoria);
                p2.setDescripcion("MOMOSAPIENS presenta \"Religiosamente\". Mediante dos parodias y un hilo conductor que aborda la temática de la\n"
                        + "religión Momosapiens, mediante el humor y la reflexión, hilvana una historia que muestra al hombre inmerso en el tema\n"
                        + "religioso. El libreto está escrito utilizando diferentes lenguajes de humor, dando una visión satírica y reflexiva desde\n"
                        + "distintos puntos de vista, logrando mediante situaciones paródicas armar una propuesta plena de arte carnavalero.");
                p2.setFecha(LocalDate.of(2017, 10, 7));
                p2.setLugar("Teatro de Verano");
                p2.setPrecio(300);
                p2.setMontoTotal(300000);
                List<TipoRetorno> retornoMOM = new ArrayList<>();
                retornoMOM.add(TipoRetorno.EntradaGratis);
                retornoMOM.add(TipoRetorno.PorcentajeGanancia);
                p2.setRetornos(retornoMOM);
                p2.addEstHistorial(Estado.FINANCIADA);
                p2.addEstHistorial(Estado.EN_FINANCIACION);
                p2.addEstHistorial(Estado.PUBLICADA);
                p2.addEstHistorial(Estado.INGRESADA);
                p2.setImagne("IMG/MOM/MOMO.jpg");
                p2.setFechaPublicacion(LocalDate.now());
                em.persist(p2);
            }
            // El Pimiento Indomable
            Propuesta pr3Existente = em.find(Propuesta.class, "El Pimiento Indomable");
            if (pr3Existente == null) {
                Propuesta p3 = new Propuesta();
                Proponente proponente = em.find(Proponente.class, "mbusca");
                p3.setProponente(proponente);
                p3.setTitulo("El Pimiento Indomable");
                Categoria categoria = em.find(Categoria.class, "Concierto");
                p3.setCategoria(categoria);
                p3.setDescripcion("El Pimiento Indomable, formación compuesta por Kiko Veneno y el uruguayo Martín Buscaglia, presentará este 19 de\n"
                        + "Octubre, su primer trabajo. Bajo un título homónimo al del grupo, es un disco que según los propios protagonistas\n"
                        + "“no se parece al de ninguno de los dos por separado. Entre los títulos que se podrán escuchar se encuentran “Nadador\n"
                        + "salvador”, “América es más grande”, “Pescaito Enroscado” o “La reina del placer”.");
                p3.setFecha(LocalDate.of(2017, 10, 19));
                p3.setLugar("Teatro Solís");
                p3.setPrecio(400);
                p3.setMontoTotal(400000);
                List<TipoRetorno> retornoPIM = new ArrayList<>();
                retornoPIM.add(TipoRetorno.PorcentajeGanancia);
                p3.setRetornos(retornoPIM);
                p3.addEstHistorial(Estado.EN_FINANCIACION);
                p3.addEstHistorial(Estado.PUBLICADA);
                p3.addEstHistorial(Estado.INGRESADA);
                p3.setImagne("IMG/PIM/pim.jpg");
                p3.setFechaPublicacion(LocalDate.now());
                em.persist(p3);
            }
            // Pilsen Rock
            Propuesta pr4Existente = em.find(Propuesta.class, "Pilsen Rock");
            if (pr4Existente == null) {
                Propuesta p4 = new Propuesta();
                Proponente proponente = em.find(Proponente.class, "kairoh");
                p4.setProponente(proponente);
                p4.setTitulo("Pilsen Rock");
                Categoria categoria = em.find(Categoria.class, "Festival");
                p4.setCategoria(categoria);
                p4.setDescripcion("La edición 2017 del Pilsen Rock se celebrará el 21 de Octubre en la Rural del Prado y contará con la participación de más\n"
                        + "de 15 bandas nacionales. Quienes no puedan trasladarse al lugar, tendrán la posibilidad de disfrutar los shows a través de\n"
                        + "Internet, así como entrevistas en vivo a los músicos una vez finalizados los conciertos.");
                p4.setFecha(LocalDate.of(2017, 10, 21));
                p4.setLugar("Rural del Prado");
                p4.setPrecio(1000);
                p4.setMontoTotal(900000);
                List<TipoRetorno> retornoPIL = new ArrayList<>();
                retornoPIL.add(TipoRetorno.EntradaGratis);
                retornoPIL.add(TipoRetorno.PorcentajeGanancia);
                p4.setRetornos(retornoPIL);
                p4.addEstHistorial(Estado.EN_FINANCIACION);
                p4.addEstHistorial(Estado.PUBLICADA);
                p4.addEstHistorial(Estado.INGRESADA);
                p4.setImagne("IMG/PIL/pil.jpg");
                p4.setFechaPublicacion(LocalDate.now());
                em.persist(p4);
            }

            // Romeo y Julieta
            Propuesta pr5Existente = em.find(Propuesta.class, "Romeo y Julieta");
            if (pr5Existente == null) {
                Propuesta p5 = new Propuesta();
                Proponente proponente = em.find(Proponente.class, "juliob");
                p5.setProponente(proponente);
                p5.setTitulo("Romeo y Julieta");
                Categoria categoria = em.find(Categoria.class, "Ballet");
                p5.setCategoria(categoria);
                p5.setDescripcion("Romeo y Julieta de Kenneth MacMillan, uno de los ballets favoritos del director artístico Julio Bocca, se presentará\n"
                        + "nuevamente el 5 de Noviembre en el Auditorio Nacional del Sodre. Basada en la obra homónima de William Shakespeare,\n"
                        + "Romeo y Julieta es considerada la coreografía maestra del MacMillan. La producción de vestuario y escenografía se realizó\n"
                        + "en los Talleres del Auditorio Adela Reta, sobre los diseños originales.");
                p5.setFecha(LocalDate.of(2017, 11, 5));
                p5.setLugar("Auditorio Nacional del Sodre");
                p5.setPrecio(800);
                p5.setMontoTotal(750000);
                List<TipoRetorno> retornoRYJ = new ArrayList<>();
                retornoRYJ.add(TipoRetorno.PorcentajeGanancia);
                p5.setRetornos(retornoRYJ);
                p5.addEstHistorial(Estado.EN_FINANCIACION);
                p5.addEstHistorial(Estado.PUBLICADA);
                p5.addEstHistorial(Estado.INGRESADA);
                p5.setImagne("IMG/RYJ/RYJ.jpg");
                p5.setFechaPublicacion(LocalDate.now());
                em.persist(p5);
            }

            // Un día de Julio
            Propuesta pr6Existente = em.find(Propuesta.class, "Un día de Julio");
            if (pr6Existente == null) {
                Propuesta p6 = new Propuesta();
                Proponente proponente = em.find(Proponente.class, "tabarec");
                p6.setProponente(proponente);
                p6.setTitulo("Un día de Julio");
                Categoria categoria = em.find(Categoria.class, "Murga");
                p6.setCategoria(categoria);
                p6.setDescripcion("La Catalina presenta el espectáculo \"Un Día de Julio\" en Landia. Un hombre misterioso y solitario vive encerrado entre las\n"
                        + "cuatro paredes de su casa. Intenta, con sus teorías extravagantes, cambiar el mundo exterior que le resulta inhabitable.\n"
                        + "Un día de Julio sucederá algo que cambiará su vida y la de su entorno para siempre.");
                p6.setFecha(LocalDate.of(2017, 11, 16));
                p6.setLugar("Landia");
                p6.setPrecio(650);
                p6.setMontoTotal(300000);
                List<TipoRetorno> retornoUDJ = new ArrayList<>();
                retornoUDJ.add(TipoRetorno.EntradaGratis);
                retornoUDJ.add(TipoRetorno.PorcentajeGanancia);
                p6.setRetornos(retornoUDJ);
                p6.addEstHistorial(Estado.EN_FINANCIACION);
                p6.addEstHistorial(Estado.PUBLICADA);
                p6.addEstHistorial(Estado.INGRESADA);
                p6.setImagne("IMG/UDJ/UDIJ.jpg");
                p6.setFechaPublicacion(LocalDate.now());
                em.persist(p6);
            }

            // El Lazarillo de Tormes
            Propuesta pr7Existente = em.find(Propuesta.class, "El Lazarillo de Tormes");
            if (pr7Existente == null) {
                Propuesta p7 = new Propuesta();
                Proponente proponente = em.find(Proponente.class, "hectorg");
                p7.setProponente(proponente);
                p7.setTitulo("El Lazarillo de Tormes");
                Categoria categoria = em.find(Categoria.class, "Teatro Dramático");
                p7.setCategoria(categoria);
                p7.setDescripcion("Vuelve unas de las producciones de El Galpón más aclamadas de los últimos tiempos. Esta obra se ha presentado en\n"
                        + "Miami, Nueva York, Washington, México, Guadalajara, Río de Janeiro y La Habana. En nuestro país, El Lazarillo de\n"
                        + "Tormes fue nominado en los rubros mejor espectáculo y mejor dirección a los Premios Florencio 1995, obteniendo su\n"
                        + "protagonista Héctor Guido el Florencio a Mejor actor de ese año.");
                p7.setFecha(LocalDate.of(2017, 12, 3));
                p7.setLugar("Teatro el Galpón");
                p7.setPrecio(350);
                p7.setMontoTotal(175000);
                List<TipoRetorno> retornoLDT = new ArrayList<>();
                retornoLDT.add(TipoRetorno.EntradaGratis);
                p7.setRetornos(retornoLDT);
                p7.addEstHistorial(Estado.PUBLICADA);
                p7.addEstHistorial(Estado.INGRESADA);
                p7.setImagne("");
                p7.setFechaPublicacion(LocalDate.now());
                em.persist(p7);
            }

            // Bardo en la FING
            Propuesta pr8Existente = em.find(Propuesta.class, "Bardo en la FING");
            if (pr8Existente == null) {
                Propuesta p8 = new Propuesta();
                Proponente proponente = em.find(Proponente.class, "losBardo");
                p8.setProponente(proponente);
                p8.setTitulo("Bardo en la FING");
                Categoria categoria = em.find(Categoria.class, "Stand-up");
                p8.setCategoria(categoria);
                p8.setDescripcion("El 10 de Diciembre se presentará Bardo Científico en la FING. El humor puede ser usado como una herramienta importante\n"
                        + "para el aprendizaje y la democratización de la ciencia, los monólogos científicos son una forma didáctica de apropiación del\n"
                        + "conocimiento científico y contribuyen a que el público aprenda ciencia de forma amena. Los invitamos a pasar un rato\n"
                        + "divertido, en un espacio en el cual aprenderán cosas de la ciencia que los sorprenderán. ¡Los esperamos!");
                p8.setFecha(LocalDate.of(2017, 12, 10));
                p8.setLugar("Anfiteatro Edificio \"José Luis Massera\"");
                p8.setPrecio(200);
                p8.setMontoTotal(100000);
                List<TipoRetorno> retornoBEF = new ArrayList<>();
                retornoBEF.add(TipoRetorno.EntradaGratis);
                p8.setRetornos(retornoBEF);
                p8.addEstHistorial(Estado.INGRESADA);
                p8.setImagne("");
                p8.setFechaPublicacion(LocalDate.now());
                em.persist(p8);
            }
            t.commit();
        } catch (Exception e) {
            t.rollback();

        } finally {
            em.close();
        }
    }
    
    
    public Set<DTOPropuesta> obtenerPropuestasExceptoINGRESADAS()
    {
        //almacena todo menos propuestas "INGRESADAS"

        Set<DTOPropuesta> temp = obtenerPropuestas("");
        Set<DTOPropuesta> temp2 = new HashSet<>();
        

        for (DTOPropuesta ct : temp)
        {
            if( !(ct.getUltimoEstado().getEstadoString().equals("INGRESADA")) ) //Si la propuesta no son "INGRESADA"
            {
                temp2.add(ct);
            }
        }
        

        return temp2;
    }
    
}
