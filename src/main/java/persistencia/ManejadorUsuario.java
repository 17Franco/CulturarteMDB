
package persistencia;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import logica.Colaboracion.Colaboracion;
import logica.DTO.DTOColaboracion;
import logica.DTO.DTOColaborador;
import logica.DTO.DTOProponente;
import logica.DTO.DTOPropuesta;
import logica.DTO.DTOUsuario;
import logica.Propuesta.Propuesta;
import logica.Propuesta.Registro_Estado;
import logica.Usuario.Colaborador;
import logica.Usuario.Proponente;
import logica.Usuario.Usuario;
import logica.DTO.Estado;



public class ManejadorUsuario {
   
    
    private static ManejadorUsuario instancia = null;
    private EntityManager em;

        
    private ManejadorUsuario() {
  
    }
    
    public static ManejadorUsuario getInstance() {
        if (instancia == null)
            instancia = new ManejadorUsuario();
        return instancia;
    }
    //agrego a la bd un usuario tipo proponente
    public void addProponente(DTOProponente u){
        Proponente p=new Proponente(u); //creo proponente
        //usuarios.put(u.getNickname(),p);
        
        em= PersistenciaManager.getEntityManager();//instancia del manegador de la persistencia 
        EntityTransaction t = em.getTransaction(); // intancia de una transaccion nesesario si se hace alta baja y modificado 
        try{
            t.begin(); // aca inicio transaccion
            em.persist(p);// persisto los datos 
            t.commit();// los aseguro  
        }
        catch(Exception e){
            t.rollback();    // en caso de error hace rollback
        }
        em.close();// cierro el manejador 

    }
    
    
    
    //devuelvo dtoUsuario con info minima de todos los usuarios cargados
    public List<DTOUsuario> getListDTOUsuario(){
         em= PersistenciaManager.getEntityManager();
         List<DTOUsuario> usuarios=new ArrayList<>();
        try{
            List<Usuario> lista = em.createQuery("FROM Usuario", Usuario.class).getResultList();
            for(Usuario u: lista){
                DTOUsuario usr=new DTOUsuario();
                usr.setNickname(u.getNickname());
                usr.setRutaImg(u.getRutaImg());
                if(u instanceof Proponente){
                    usr.setTipoUsr("Proponente");
                }else{
                    usr.setTipoUsr("Colaborador");
                }
                usuarios.add(usr);
            }
            return usuarios;
        }finally{
            em.close();
        }
    
    }
     //insertar un colaborador a la bd
     public void addColaborador(DTOColaborador u){
        Colaborador p=new Colaborador(u);
        em= PersistenciaManager.getEntityManager();
        EntityTransaction t = em.getTransaction();
        try{
            t.begin();
            em.persist(p);
            t.commit();
            
        }
        catch(Exception e){
            t.rollback();    
        }
        em.close();
    
    }
     //devuelvo las propuestas del usuario identificado por nick
    public List<DTOPropuesta> getFavoritas(String nick){
        em= PersistenciaManager.getEntityManager();
         List<DTOPropuesta> p=new ArrayList<>();
        try{
            Usuario u = em.find(Usuario.class, nick);
            if(u!=null && !u.getPropFavorita().isEmpty()){
                for(Propuesta prop: u.getPropFavorita().values()){
                    DTOPropuesta aux= new DTOPropuesta();
                    aux.setTitulo(prop.getTitulo());
                    aux.setImagen(prop.getImagen());
                    aux.setCategoria(prop.getCategoria().getNombreCategoria());
                    aux.setEstadoAct(prop.getUltimoEstado().getEstado());
                    aux.setFechaPublicacion(prop.getFechaPublicacion());
                    p.add(aux);
                }
            }
            return p;
        }finally{
            em.close();
        }
    }
    
    //obengo la lista de usuario que siguen al usuario identificado por seguido
    public List<DTOUsuario> obtenerSeguidores(String seguido){
        em= PersistenciaManager.getEntityManager();
        try{
            //select count(*) from usuario_seguidos where seguidor='franco2' and seguido='mbusca';
            List<DTOUsuario> usuarios=new ArrayList<>();
            //esta consulta seria dame todos los usuario que en su lista seguidos es igual al pasado por parametro
            String consulta = "SELECT u FROM Usuario u JOIN u.usuarioSeguido s " + 
                      "WHERE  s.nickname = :seguido";
            List<Usuario> u = em.createQuery(consulta, Usuario.class).setParameter("seguido", seguido).getResultList();
            
            for(Usuario usr:u){
                DTOUsuario dtousr=new DTOUsuario();
                dtousr.setNickname(usr.getNickname());
                dtousr.setRutaImg(usr.getRutaImg());
                if(usr instanceof Proponente){
                    dtousr.setTipoUsr("Proponente");
                }else{
                    dtousr.setTipoUsr("Colaborador");
                }
                usuarios.add(dtousr);
            }
            return usuarios;

        }finally{
            em.close(); 
        }
    }
    //compruebo si seguidor ya sigue a seguido 
    public boolean sigue(String seguidor,String seguido){
        em= PersistenciaManager.getEntityManager();
        try{
            //select count(*) from usuario_seguidos where seguidor='franco2' and seguido='mbusca';
           
            String consulta = "SELECT COUNT(s) FROM Usuario u JOIN u.usuarioSeguido s " + 
                      "WHERE u.nickname = :seguidor AND s.nickname = :seguido";
            
            String consulta2="SELECT ";
                      
            Long count = em.createQuery(consulta, Long.class)
                       .setParameter("seguidor", seguidor)
                       .setParameter("seguido", seguido)
                       .getSingleResult();
            return count > 0;

        }finally{
            em.close(); 
        }
    
    }
     //compruebo que existe un usuario identificado por nick
    public boolean existe(String nick){

        em= PersistenciaManager.getEntityManager();
      try{
            Usuario u = em.find(Usuario.class, nick);
            return  (u != null);
      }finally{
            em.close(); 
      }
    }
    
    //devuelvo una Intancia de Usuario
    public Usuario getUsuario(String nick) {
         em = PersistenciaManager.getEntityManager();
         try {
             return em.find(Usuario.class, nick);
         } finally {
             em.close(); // se ejecuta SIEMPRE, haya error o no
         }
     }
    // devuelvo dtoUusario con la info minima de los que sigue el usuario identificado por nick
    public List<DTOUsuario> getSeguidos(String nick){
    
        em = PersistenciaManager.getEntityManager();
         try {
            List<DTOUsuario> listSeguidos=new ArrayList<>();
            Usuario usuario=em.find(Usuario.class, nick);//usuario del cual  quiero los seguidores
            if (usuario != null && usuario.getUsuarioSeguido() != null) {
                for(Usuario u:usuario.getUsuarioSeguido().values()){
                    DTOUsuario dtou=new DTOUsuario();
                    dtou.setNickname(u.getNickname());
                    dtou.setRutaImg(u.getRutaImg());
                    if(u instanceof Proponente){
                        dtou.setTipoUsr("Proponente");
                    }else{
                        dtou.setTipoUsr("Colaborador");
                    }
                    listSeguidos.add(dtou);
                }
             }
            return listSeguidos;
         }finally {
            em.close(); // se ejecuta SIEMPRE, haya error o no
         }
    }
    //devuelvo un map de las propuestas creada por un usuario de tipo proponente
    public Map<String,DTOPropuesta> getPropuestasCreadas(DTOProponente proponente){
      
        em = PersistenciaManager.getEntityManager();
        String nick=proponente.getNickname();
        Map<String,DTOPropuesta> resu=new HashMap<>();
         try {
             List<Propuesta> listaPropuesta = em.createQuery("SELECT p FROM Propuesta p WHERE p.usr.nickname=:nick", Propuesta.class).setParameter("nick", nick).getResultList();
            for(Propuesta p: listaPropuesta){
                DTOPropuesta prop=new DTOPropuesta(p,proponente);
                resu.put(prop.getTitulo(), prop);
            }
             return resu;
         } finally {
             em.close(); // se ejecuta SIEMPRE, haya error o no
         }
      
    }
    
    //coprueba si el mail ya a sido registrado por algun usuario
    public boolean emailUsado(String email){
        em = PersistenciaManager.getEntityManager();
         
         try{
             String norm = email == null ? null : email.trim().toLowerCase();
             Long count = em.createQuery(
            "SELECT COUNT(u) FROM Usuario u WHERE u.email = :email", Long.class).setParameter("email", norm).getSingleResult();
            return count > 0;
         }finally{
            em.close();
         }
    }
    
    //DEVUELVO TODOS LOS USUARIOS COMO DTO A CONTROLLER
    public Map<String, DTOUsuario> getUsuarios() {
        
         Map<String, DTOUsuario> resu = new HashMap<>();
       em = PersistenciaManager.getEntityManager();
         
         try{
           // Traigo todos los usuarios en una lista
            List<Usuario> lista = em.createQuery("FROM Usuario", Usuario.class).getResultList();
            Map<String,DTOUsuario> mapDtoUsuario=new HashMap<>();
            for(Usuario u: lista){
                if(u instanceof Colaborador){
                    DTOColaborador c= new DTOColaborador( (Colaborador) u);
                    mapDtoUsuario.put(c.getNickname(), c);
                }else{
                    DTOProponente p=new DTOProponente((Proponente) u);
                    mapDtoUsuario.put(p.getNickname(), p);
                }
            }
            return mapDtoUsuario;
         }finally{
            em.close();
         }
    }
    
    // aca devuelvo dto de usuarios que solo son colaboradores
    public Set<DTOColaborador> listaColaboradores(){
        Set<DTOColaborador> results = new HashSet<DTOColaborador>();
        
        em = PersistenciaManager.getEntityManager();
        try{
             List<Colaborador> listaColaborador = em.createQuery("SELECT u FROM Usuario u WHERE u INSTANCE OF Colaborador", Colaborador.class).getResultList();
             for(Colaborador c: listaColaborador){
                     DTOColaborador col=new DTOColaborador(c);
                     results.add(col);
                 
             }
              return results;
        }finally{
            em.close();
        }
    }
    
    //comprueba si un colaborador ya colaboro con la propuesta identificada por titulo
    public boolean existeColaboracion(String colaborador, String titulo){
        em = PersistenciaManager.getEntityManager();
         try{
             
            Long count = em.createQuery(
            "SELECT COUNT(c) FROM Colaboracion c WHERE c.colaborador.nickname = :colaborador and c.propuesta.Titulo = :titulo", Long.class)
                     .setParameter("colaborador", colaborador)
                     .setParameter("titulo", titulo)
                     .getSingleResult();
              return count > 0;
         }finally{
            //em.close();
         }
    }
    //devuelve una lista de las  dto colaboraciones que son de un usuario identificado por nick
    public List<DTOColaboracion> getDTOColaboraciones(String nick){
         //List<DTOColaboracion> resu=new ArrayList<>();
        em = PersistenciaManager.getEntityManager();
        List<DTOColaboracion> resu=new ArrayList<>();
        try{
            Colaborador c=em.find(Colaborador.class, nick);
             for(Colaboracion colab: c.getColaboraciones()){
               
                DTOColaboracion DTOColab = new DTOColaboracion(colab);
                resu.add(DTOColab);
            }
             return resu;
        }finally{
            em.close();
        }
        }
        
//    //elimino la colaboracion echa a propuesta por el usuario identificado por nick 
//    public void eliminarColaboracion(String nick,String propuesta){
//        Colaborador c=(Colaborador) usuarios.get(nick);
//        Colaboracion aux=null;
//            for(Colaboracion col: c.getColaboraciones()){
//               if(col.getPropuesta().getTitulo().equals(propuesta)){
//               aux=col;
//               break;
//            }
//        }
//            if(aux!=null){
//                c.getColaboraciones().remove(aux);
//            }
//
//        }
    //operacion para seguir a usuario 
    public boolean seguirUsr(String nick1, String nick2){
        em = PersistenciaManager.getEntityManager();
        EntityTransaction t = em.getTransaction();
        try{

            Usuario usuario1 = em.find(Usuario.class,nick1);
            Usuario usuario2 = em.find(Usuario.class,nick2);

            if (usuario1 == null || usuario2 == null) return false;
            if (nick1.equals(nick2)) return false;
            if (usuario1.getUsuarioSeguido().containsKey(nick2)) return false;


            t.begin();

            usuario1.seguir(usuario2);
            //em.merge(usuario2); // sincriniza los cambios de un objeto  se usa cuando el objeto no es manage por el entitymanager 
            t.commit();
            return true;

        }catch(Exception e){
            t.rollback();
            return false;
        }finally{
            em.close();
        }

        }
        //operacion para dejar de seguir a usuario
        public boolean dejarDeSeguirUsuario(String nick1,String nick2){
             em = PersistenciaManager.getEntityManager();
             EntityTransaction t = em.getTransaction();
             try{
                     System.out.println(nick1 + nick2);
                    Usuario usuario1 = em.find(Usuario.class,nick1);
                    Usuario usuario2 = em.find(Usuario.class,nick2);
                  
                     if (usuario1 == null || usuario2 == null) return false;
                     if (nick1.equals(nick2)) return false;
                     
                     if (usuario1.getUsuarioSeguido().containsKey(nick2)){
                            
                            t.begin();
                            usuario1.unfollow(usuario2);
                            //em.merge(usuario2); // sincriniza los cambios de un objeto  se usa cuando el objeto no es manage por el entitymanager 
                            t.commit();
                         return true;
                     }
                    return false;
             }catch(Exception e){
                 if (t.isActive()){
                    t.rollback();
                 }
             }finally{
                 em.close();
             }
            return false;
        }
        
        //devuelvo solo los nick de los seguidos del usuario identificado por nick
        public List<String> listaSeguidos(String nick){
        em = PersistenciaManager.getEntityManager();
        try{
            List<String> aux = new ArrayList<>();
            if(nick!=null){
                Usuario usuario = em.find(Usuario.class,nick);
                if (usuario != null && usuario.getUsuarioSeguido() != null) {
                   for(Usuario u:usuario.getUsuarioSeguido().values()){
                       aux.add(u.getNickname());
                   }
                }
            }
            return aux;
        }finally{
            em.close();
        }
            
        }
        
        //verifico si la pass del usuario identificado por nick es correcta
        public boolean verificarCredenciales(String nick, String pass){
            em = PersistenciaManager.getEntityManager();
            try{
              Usuario usuario = em.find(Usuario.class,nick);
                if(usuario !=null && usuario.getPass().equals(pass)){
                  return true;      
                }
                return false;
            }finally{
                em.close();
            }
            
        }
        
        //devuelvo si es proponente o no
        public boolean isProponente(String nick){
            em = PersistenciaManager.getEntityManager();
            try{
              //Colaborador tipoC = em.find(Colaborador.class,nick);
              Proponente tipoP = em.find(Proponente.class,nick);
                if(tipoP !=null ){
                  return true;      
                }
                return false;
            }finally{
                em.close();
            }
        }
        //obtengo la info minima de la propuestas creadas de un proponente 
        public Set<DTOPropuesta> getPropuestasCreadasPorProponente(String nick){
                em = PersistenciaManager.getEntityManager();
                   Proponente  p=em.find(Proponente.class,nick);
                   Set<DTOPropuesta> resu=new HashSet<>();
                try{
                    DTOProponente dtoProp=new DTOProponente(p);
                    for(Propuesta prop: p.getPropCreadas().values()){
                        Estado aux=prop.getHistorialEstados().get(0).getEstado();
                        DTOPropuesta dtoP=new DTOPropuesta(prop,dtoProp);
                        dtoP.setEstadoAct(aux);
                        resu.add(dtoP);
                    }
                    return resu;
                }finally{
                    em.close();
                }
        
        }
        
        //agregom una propuesta como favorita al usuario 
        public void marcarComoFavorita(String nickname, String tituloPropuesta) {
                em = PersistenciaManager.getEntityManager();
                try {
                    em.getTransaction().begin();
                    Usuario usuario = em.find(Usuario.class, nickname);
                    Propuesta propuesta = em.find(Propuesta.class, tituloPropuesta);
                    if (!usuario.getPropFavorita().containsKey(propuesta.getTitulo())) {
                        usuario.Favorita(propuesta);
                    }
                    if (usuario != null && propuesta != null) {
                        usuario.Favorita(propuesta);
                    }
                    em.getTransaction().commit();
                } finally {
                    em.close();
                }
        }
        
        //quito de favorito la propuesta al usuario
        public void quitarFavorita(String nickname, String tituloPropuesta) {
                em = PersistenciaManager.getEntityManager();
                try {
                    em.getTransaction().begin();
                    Usuario usuario = em.find(Usuario.class, nickname);
                    Propuesta propuesta = em.find(Propuesta.class, tituloPropuesta);
                    if (usuario != null && propuesta != null) {
                        usuario.quitarFavorita(propuesta);
                    }
                    em.getTransaction().commit();
                } finally {
                    em.close();
                }
        }
        //verifico si ina propuesta esta como favorita
        public boolean esFavorita(String nickname, String tituloPropuesta) {
                em = PersistenciaManager.getEntityManager();
                try {
                    Usuario usuario = em.find(Usuario.class, nickname);
                    Propuesta propuesta = em.find(Propuesta.class, tituloPropuesta);
                    return usuario != null && usuario.esFavorita(propuesta);
                } finally {
                    em.close();
                }
        }
        //empieza los metodos para cargar datos de usuario y colaboraciones
        public void cargarpProponente(){
              em = PersistenciaManager.getEntityManager();
              EntityTransaction t = em.getTransaction();
              try{
                t.begin();
                    Proponente p1Existente = em.find(Proponente.class, "hrubino");
                if (p1Existente == null) {
                    Proponente p1 = new Proponente();
                    p1.setDireccion("18 de Julio 1234");
                    p1.setBiografia("Horacio Rubino Torres nace el 25 de febrero de 1962, es conductor, actor y libretista. Debuta en 1982 en carnaval\n" +
                                  "en Los \"Klaper´s\", donde estuvo cuatro años, actuando y libretando. Luego para \"Gaby´s\" (6 años), escribió en\n" +
                                  "categoría revistas y humoristas y desde el comienzo y hasta el presente en su propio conjunto Momosapiens.");
                    p1.setWebSite("https://twitter.com/horaciorubino");
                    p1.setNickname("hrubino");
                    p1.setNombre("Horacio");
                    p1.setApellido("Rubino");
                    p1.setEmail("horacio.rubino@guambia.com.uy");
                    p1.setFecha(LocalDate.of(1962,02,25));
                    p1.setRutaImg("IMG/hrubino/547129.JPG");
                    em.persist(p1);
                }

                // 2. Check and persist p2
                Proponente p2Existente = em.find(Proponente.class, "mbusca");
                if (p2Existente == null) {
                    Proponente p2 = new Proponente();
                    p2.setDireccion("Colonia 4321");
                    p2.setBiografia("Martín Buscaglia (Montevideo, 1972) es un artista, músico, compositor y productor uruguayo. Tanto con su banda\n" +
                                  "(\"Los Bochamakerns\") como en su formato \"Hombre orquesta\", o solo con su guitarra, ha recorrido el mundo\n" +
                                  "tocando entre otros países en España, Estados Unidos, Inglaterra, Francia, Australia, Brasil, Colombia, Argentina,\n" +
                                  "Chile, Paraguay, México y Uruguay. (Actualmente los Bochamakers son Matías Rada, Martín Ibarburu, Mateo\n" +
                                  "Moreno, Herman Klang) Paralelamente, tiene proyectos a dúo con el español Kiko Veneno, la cubana Yusa,\n" +
                                  "el argentino Lisandro Aristimuño, su compatriota Antolín, y trío junto a los brasileros Os Mulheres Negras.");
                    p2.setWebSite("https://www.martinbuscaglia.com/");
                    p2.setNickname("mbusca");
                    p2.setNombre("Martín");
                    p2.setApellido("Buscaglia");
                    p2.setEmail("Martin.bus@agadu.org.uy");
                    p2.setFecha(LocalDate.of(1972, 6, 14));
                    p2.setRutaImg("IMG/mbusca/583283.jpg");
                    em.persist(p2);
                }

                // 3. Check and persist p3
                Proponente p3Existente = em.find(Proponente.class, "tabarec");
                if (p3Existente == null) {
                    Proponente p3 = new Proponente();
                    p3.setDireccion("Santiago Rivas 1212");
                    p3.setBiografia("Tabaré Cardozo (Montevideo, 24 de julio de 1971) es un cantante, compositor y murguista uruguayo; conocido por\n" +
                                  "su participación en la murga Agarrate Catalina, conjunto que fundó junto a su hermano Yamandú y Carlos\n" +
                                  "Tanco en el año 2001.");
                    p3.setWebSite("https://www.facebook.com/Tabar%C3%A9-Cardozo-55179094281/?ref=br_rs");
                    p3.setNickname("tabarec");
                    p3.setNombre("Tabaré");
                    p3.setApellido("Cardozo");
                    p3.setEmail("tabare.car@agadu.org.uy");
                    p3.setFecha(LocalDate.of(1971, 7, 24));
                    p3.setRutaImg("IMG/tabarec/images.jpeg");
                    em.persist(p3);
                }

                // 4. Check and persist p4
                Proponente p4Existente = em.find(Proponente.class, "cachilas");
                if (p4Existente == null) {
                    Proponente p4 = new Proponente();
                    p4.setDireccion("Br. Artigas 4567");
                    p4.setBiografia("Nace en el año 1947 en el conventillo \"Medio Mundo\" ubicado en pleno Barrio Sur. Es heredero parcialmente\n" +
                                  "junto al resto de sus hermanos- de la Comparsa \"Morenada\" (inactiva desde el fallecimiento de Juan Ángel Silva),\n" +
                                  "en 1999 forma su propia Comparsa de negros y libolos \"Cuareim 1080\". Director responsable, compositor y\n" +
                                  "cantante de la misma.");
                    p4.setWebSite("https://www.facebook.com/C1080?ref=br_rs");
                    p4.setNickname("cachilas");
                    p4.setNombre("Waldemar \"Cachila\"");
                    p4.setApellido("Silva");
                    p4.setEmail("Cachila.sil@c1080.org.uy");
                    p4.setFecha(LocalDate.of(1947, 1, 1));
                    p4.setRutaImg("IMG/cachilas/8273.jpg");
                    em.persist(p4);
                }

                // 5. Check and persist p5
                Proponente p5Existente = em.find(Proponente.class, "hectorg");
                if (p5Existente == null) {
                    Proponente p5 = new Proponente();
                    p5.setDireccion("Gral. Flores 5645");
                    p5.setBiografia("En 1972 ingresó a la Escuela de Arte Dramático del teatro El Galpón. Participó en más de treinta obras teatrales y\n" +
                                  "varios largometrajes. Integró el elenco estable de Radioteatro del Sodre, y en 2006 fue asesor de su Consejo\n" +
                                  "Directivo. Como actor recibió múltiples reconocimientos: cuatro premios Florencio, premio al mejor actor\n" +
                                  "extranjero del Festival de Miami y premio Mejor Actor de Cine 2008. Durante varios períodos fue directivo del\n" +
                                  "teatro El Galpón y dirigente de la Sociedad Uruguaya de Actores (SUA); integró también la Federación Uruguaya\n" +
                                  "de Teatros Independientes (FUTI). Formó parte del equipo de gestión de la refacción de los teatros La Máscara,\n" +
                                  "Astral y El Galpón, y del equipo de gestión en la construcción del teatro De la Candela y de la sala Atahualpa de El\n" +
                                  "Galpón.");
                    p5.setWebSite("");
                    p5.setNickname("hectorg");
                    p5.setNombre("Héctor");
                    p5.setApellido("Guido");
                    p5.setEmail("hector.gui@1galpon.org.uy");
                    p5.setFecha(LocalDate.of(1954, 1, 7));
                    p5.setRutaImg("IMG/hectorg/7232.jpg");
                    em.persist(p5);
                }

                // 6. Check and persist p6
                Proponente p6Existente = em.find(Proponente.class, "juliob");
                if (p6Existente == null) {
                    Proponente p6 = new Proponente();
                    p6.setDireccion("Benito Blanco 4321");
                    p6.setBiografia("");
                    p6.setWebSite("");
                    p6.setNickname("juliob");
                    p6.setNombre("Julio");
                    p6.setApellido("Bocca");
                    p6.setEmail("juliobocca@sodre.com.uy");
                    p6.setFecha(LocalDate.of(1967, 3, 16));
                    p6.setRutaImg("");
                    em.persist(p6);
                }

                // 7. Check and persist p7
                Proponente p7Existente = em.find(Proponente.class, "diegop");
                if (p7Existente == null) {
                    Proponente p7 = new Proponente();
                    p7.setDireccion("Emilio Frugoni 1138 Ap. 02");
                    p7.setBiografia("");
                    p7.setWebSite("http://www.efectocine.com");
                    p7.setNickname("diegop");
                    p7.setNombre("Diego");
                    p7.setApellido("Parodi");
                    p7.setEmail("diego@efectocine.com");
                    p7.setFecha(LocalDate.of(1975, 1, 1));
                    p7.setRutaImg("");
                    em.persist(p7);
                }

                // 8. Check and persist p8
                Proponente p8Existente = em.find(Proponente.class, "kairoh");
                if (p8Existente == null) {
                    Proponente p8 = new Proponente();
                    p8.setDireccion("Paraguay 1423");
                    p8.setBiografia("");
                    p8.setWebSite("");
                    p8.setNickname("kairoh");
                    p8.setNombre("Kairo");
                    p8.setApellido("Herrera");
                    p8.setEmail("kairoher@pilsenrock.com.uy");
                    p8.setFecha(LocalDate.of(1840, 4, 25));
                    p8.setRutaImg("IMG/kairoh/images.jpeg");
                    em.persist(p8);
                }

                // 9. Check and persist p9
                Proponente p9Existente = em.find(Proponente.class, "losBardo");
                if (p9Existente == null) {
                    Proponente p9 = new Proponente();
                    p9.setDireccion("8 de Octubre 1429");
                    p9.setBiografia("Queremos ser vistos y reconocidos como una organización: referente en divulgación científica con un fuerte\n" +
                                  "espíritu didáctico y divertido, a través de acciones coordinadas con otros divulgadores científicos, que permitan\n" +
                                  "establecer puentes de comunicación. Impulsora en la generación de espacios de democratización y apropiación\n" +
                                  "social del conocimiento científico.");
                    p9.setWebSite("https://bardocientifico.com/");
                    p9.setNickname("losBardo");
                    p9.setNombre("Los");
                    p9.setApellido("Bardo");
                    p9.setEmail("losbardo@bardocientifico.com");
                    p9.setFecha(LocalDate.of(1980, 10, 31));
                    p9.setRutaImg("IMG/losBardo/BC-head-home-heroe-color-final.jpg");
                    em.persist(p9);
                }
                  t.commit();
              }catch(Exception e){
                  t.rollback();
                  
              }finally{
                  em.close();
              }
    
        }
        
        public void cargarpColaboradores(){
             em = PersistenciaManager.getEntityManager();
              EntityTransaction t = em.getTransaction();
              try{
                t.begin();
                Colaborador c0Existente = em.find(Colaborador.class, "robinh");
                if (c0Existente == null) {
                    Colaborador c0 = new Colaborador();
                    c0.setNickname("robinh");
                    c0.setNombre("Robin");
                    c0.setApellido("Henderson");
                    c0.setEmail("Robin.h@tinglesa.com.uy");
                    c0.setFecha(LocalDate.of(1940, 8, 3));
                    c0.setRutaImg("");
                    em.persist(c0);
                }

                
                Colaborador c1Existente = em.find(Colaborador.class, "marcelot");
                if (c1Existente == null) {
                    Colaborador c1 = new Colaborador();
                    c1.setNickname("marcelot");
                    c1.setNombre("Marcelo");
                    c1.setApellido("Tinelli");
                    c1.setEmail("marcelot@ideasdelsur.com.ar");
                    c1.setFecha(LocalDate.of(1960, 4, 1));
                    c1.setRutaImg("IMG/marcelot/2132.jpg");
                    em.persist(c1);
                }

              
                Colaborador c2Existente = em.find(Colaborador.class, "novick");
                if (c2Existente == null) {
                    Colaborador c2 = new Colaborador();
                    c2.setNickname("novick");
                    c2.setNombre("Edgardo");
                    c2.setApellido("Novick");
                    c2.setEmail("edgardo@novick.com.uy");
                    c2.setFecha(LocalDate.of(1952, 7, 17));
                    c2.setRutaImg("IMG/novick/images.jpeg");
                    em.persist(c2);
                }

             
                Colaborador c3Existente = em.find(Colaborador.class, "sergiop");
                if (c3Existente == null) {
                    Colaborador c3 = new Colaborador();
                    c3.setNickname("sergiop");
                    c3.setNombre("Sergio");
                    c3.setApellido("Puglia");
                    c3.setEmail("puglia@alpanpan.com.uy");
                    c3.setFecha(LocalDate.of(1950, 1, 28));
                    c3.setRutaImg("IMG/sergiop/puglia01.jpg");
                    em.persist(c3);
                }

               
                Colaborador c4Existente = em.find(Colaborador.class, "chino");
                if (c4Existente == null) {
                    Colaborador c4 = new Colaborador();
                    c4.setNickname("chino");
                    c4.setNombre("Alvaro");
                    c4.setApellido("Recoba");
                    c4.setEmail("chino@trico.org.uy");
                    c4.setFecha(LocalDate.of(1976, 3, 17));
                    c4.setRutaImg("");
                    em.persist(c4);
                }

              
                Colaborador c5Existente = em.find(Colaborador.class, "tonyp");
                if (c5Existente == null) {
                    Colaborador c5 = new Colaborador();
                    c5.setNickname("tonyp");
                    c5.setNombre("Antonio");
                    c5.setApellido("Pacheco");
                    c5.setEmail("eltony@manya.org.uy");
                    c5.setFecha(LocalDate.of(1955, 2, 14));
                    c5.setRutaImg("IMG/tonyp/antonio.jpg");
                    em.persist(c5);
                }

                
                Colaborador c6Existente = em.find(Colaborador.class, "nicoJ");
                if (c6Existente == null) {
                    Colaborador c6 = new Colaborador();
                    c6.setNickname("nicoJ");
                    c6.setNombre("Nicolás");
                    c6.setApellido("Jodal");
                    c6.setEmail("jodal@artech.com.uy");
                    c6.setFecha(LocalDate.of(1960, 9, 8));
                    c6.setRutaImg("IMG/nicoJ/Jodal-Nicolas.jpg");
                    em.persist(c6);
                }

               
                Colaborador c7Existente = em.find(Colaborador.class, "juanP");
                if (c7Existente == null) {
                    Colaborador c7 = new Colaborador();
                    c7.setNickname("juanP");
                    c7.setNombre("Juan");
                    c7.setApellido("Perez");
                    c7.setEmail("juanp@elpueblo.com");
                    c7.setFecha(LocalDate.of(1970, 1, 1));
                    c7.setRutaImg("");
                    em.persist(c7);
                }

                
                Colaborador c8Existente = em.find(Colaborador.class, "Mengano");
                if (c8Existente == null) {
                    Colaborador c8 = new Colaborador();
                    c8.setNickname("Mengano");
                    c8.setNombre("Mengano");
                    c8.setApellido("Gómez");
                    c8.setEmail("menganog@elpueblo.com");
                    c8.setFecha(LocalDate.of(1982, 2, 2));
                    c8.setRutaImg("");
                    em.persist(c8);
                }

              
                Colaborador c9Existente = em.find(Colaborador.class, "Peregano");
                if (c9Existente == null) {
                    Colaborador c9 = new Colaborador();
                    c9.setNickname("Peregano");
                    c9.setNombre("Perengano");
                    c9.setApellido("López");
                    c9.setEmail("peregano@elpueblo.com");
                    c9.setFecha(LocalDate.of(1985, 3, 3));
                    c9.setRutaImg("");
                    em.persist(c9);
                }

               
                Colaborador c10Existente = em.find(Colaborador.class, "Tiajaci");
                if (c10Existente == null) {
                    Colaborador c10 = new Colaborador();
                    c10.setNickname("Tiajaci");
                    c10.setNombre("Tía");
                    c10.setApellido("Jacinta");
                    c10.setEmail("jacinta@elpueblo.com");
                    c10.setFecha(LocalDate.of(1990, 4, 4));
                    c10.setRutaImg("");
                    em.persist(c10);
                }
                  
                  t.commit();
              }catch(Exception e){
                  t.rollback();
                  
              }finally{
                  em.close();
              }
        
        }
        
         public void CargarSeguidos(){
             seguirUsr("hrubino","hectorg");
             seguirUsr("hrubino","diegop");
             seguirUsr("hrubino","losBardo");
             seguirUsr("mbusca","tabarec");
             seguirUsr("mbusca","cachilas");
             seguirUsr("mbusca","kairoh");
             seguirUsr("hectorg","mbusca");
             seguirUsr("hectorg","juliob");
             seguirUsr("tabarec","hrubino");
             seguirUsr("tabarec","cachilas");
             seguirUsr("cachilas","hrubino");
             seguirUsr("juliob","mbusca");
             seguirUsr("juliob","diegop");
             seguirUsr("diegop","hectorg");
             seguirUsr("diegop","losBardo");
             seguirUsr("kairoh","sergiop");
             seguirUsr("losBardo","hrubino");
             seguirUsr("losBardo","nicoJ");
             seguirUsr("robinh","hectorg");
             seguirUsr("robinh","juliob");
             seguirUsr("robinh","diegop");
             seguirUsr("marcelot","cachilas");
             seguirUsr("marcelot","juliob");
             seguirUsr("marcelot","kairoh");
             seguirUsr("novick","hrubino");
             seguirUsr("novick","tabarec");
             seguirUsr("novick","cachilas");
             seguirUsr("sergiop","mbusca");
             seguirUsr("sergiop","juliob");
             seguirUsr("sergiop","diegop");
             seguirUsr("chino","tonyp");
             seguirUsr("tonyp","chino");
             seguirUsr("nicoJ","diegop");
             seguirUsr("nicoJ","losBardo");
             seguirUsr("juanP","tabarec");
             seguirUsr("juanP","cachilas");
             seguirUsr("juanP","kairoh");
             seguirUsr("Mengano","hectorg");
             seguirUsr("Mengano","juliob");
             seguirUsr("Mengano","sergiop");
             seguirUsr("Perengano","diegop");
             seguirUsr("Perengano","tonyp");
             seguirUsr("Tiajaci","juliob");
             seguirUsr("Tiajaci","kairoh");
             seguirUsr("Tiajaci","novick");
             
             
             
             
                  

        }
}
