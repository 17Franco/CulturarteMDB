package logica;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import persistencia.ManejadorPropuesta;
import persistencia.ManejadorCategoria;
import persistencia.ManejadorColaboracion;
import logica.DTO.DTOCategoria;
import logica.DTO.DTOColaborador;
import logica.DTO.DTOProponente;
import logica.DTO.DTOUsuario;
import logica.Colaboracion.Colaboracion;
import logica.DTO.DTOColaboracion;
import logica.Propuesta.Propuesta;
import persistencia.ManejadorUsuario;
import logica.DTO.TipoRetorno;
import logica.DTO.DTOPropuesta;
import logica.DTO.DTORegistro_Estado;
import logica.Propuesta.Registro_Estado;
import logica.Usuario.Colaborador;
import logica.Usuario.Proponente;
import logica.DTO.Estado;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import persistencia.ManejadorUsuarioMongo;
import persistencia.PersistenciaMongo;

public class Controller  implements IController {
    private ManejadorUsuario mUsuario=ManejadorUsuario.getInstance();
    private ManejadorUsuarioMongo mUsuarioMongo=ManejadorUsuarioMongo.getInstance();
    private ManejadorCategoria mCategoria=ManejadorCategoria.getInstance();
    private ManejadorPropuesta mPropuesta=ManejadorPropuesta.getinstance();
    private ManejadorColaboracion mColaboraciones = ManejadorColaboracion.getInstance();
    

       
    //Usuarios
    @Override
    public void altaUsuario(DTOUsuario usu) {
        if(usu instanceof DTOProponente){
            mUsuarioMongo.addProponente((DTOProponente) usu);
        }else{
            mUsuarioMongo.addColaborador((DTOColaborador) usu);
        }
    }
    
    //web
    public String obtenerPathImg(String nick,byte[] contenido,String nombreArchivo){
        if(!nombreArchivo.equals("")){
        String RUTA_IMAGENES = "/home/fran/Escritorio/Lab1PA/IMG"; //configurar en cada maquina o buscar solucion
        String resdir="IMG" + File.separator+ nick +File.separator+ nombreArchivo;//la direccion que guardare en la bd
        String carpetaDestino = RUTA_IMAGENES + File.separator + nick;
        File dir = new File(carpetaDestino);
        if (!dir.exists()) dir.mkdirs();
        
        Path destino = Paths.get(carpetaDestino, nombreArchivo);

        try {
            Files.write(destino, contenido); 
            return resdir;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        }
        
        return "";    
    }
    //web
    @Override
    public byte[] getImg(String ruta) {
        String RUTA_IMAGENES = "/home/fran/Escritorio/Lab1PA";
        try{
        File img=new File(RUTA_IMAGENES + File.separator +ruta);
        if(!img.exists()) return null;
        //byte[] img= new ;
        
            return Files.readAllBytes(img.toPath());//devuelve array de bytes de la img 
        }catch(IOException i){
            i.printStackTrace();
            return null;
        }
    }
    //web
    @Override
    public List<DTOUsuario> getSeguidores(String nick){
        return mUsuario.obtenerSeguidores(nick);
    }
    
    //web
    @Override
    public void registroUsuario(String nickname, String pass, String nombre, String apellido, String email, LocalDate fecha, byte[] contenido,String nombreArchivo,boolean isProponente,String direccion,String web,String Biografia){
        String ruta = obtenerPathImg(nickname,contenido,nombreArchivo);
        if(isProponente){
            
            DTOProponente p=new DTOProponente(direccion,Biografia,web,nickname,pass,nombre,apellido,email,fecha,ruta,"Proponente");
            mUsuario.addProponente((DTOProponente) p);
        }else{
            DTOColaborador c= new DTOColaborador(nickname,pass,nombre,apellido,email,fecha,ruta,"Colaborador");
            mUsuario.addColaborador((DTOColaborador) c);
        }
    
    }
    @Override
    public List<DTOUsuario> ListaDTOUsuarios(){
    return mUsuario.getListDTOUsuario();
    }
    
    @Override
    public boolean sigueAUsuario(String seguidor,String Seguido){
    
    return mUsuario.sigue(seguidor,Seguido);
    }
    
    @Override
    public boolean existeUsuario(String nick, String email) {
           //return false; 
           return (mUsuarioMongo.existe(nick) || mUsuarioMongo.emailUsado(email));
    }
    
    public boolean emailUsado(String email){
        return mUsuarioMongo.emailUsado(email);
    }
    
     public boolean existe(String nick){
            return mUsuarioMongo.existe(nick);
    }
     
    @Override
    public List<DTOUsuario>Seguidos(String nick){
    
        return mUsuario.getSeguidos(nick);
    }
    
    @Override
    public List<String> ListaUsuarios(){
        List<String> aux = new ArrayList<>();
        for (DTOUsuario c : mUsuarioMongo.getUsuarios().values()){
                aux.add(c.getNickname());
        }
       return aux;    
    }
     
    @Override
     public List<String> ListaProponentes(){
         List<String> aux = new ArrayList<>();
         for (DTOUsuario c : mUsuario.getUsuarios().values()){
            if (c instanceof DTOProponente){
                 aux.add(c.getNickname());
            }
         }
        return aux;    
     }
     @Override
     public List<String> ListaColaborador(){
         List<String> aux = new ArrayList<>();
         for (DTOUsuario c : mUsuario.getUsuarios().values()){
            if (c instanceof DTOColaborador){
                 aux.add(c.getNickname());
            }
         }
        return aux;    
     }
    @Override
    public void marcarComoFavorita(String nickname, String tituloPropuesta) {
        ManejadorUsuario manejadorU = ManejadorUsuario.getInstance();
        manejadorU.marcarComoFavorita(nickname, tituloPropuesta);
    }
    @Override
    public void quitarFavorita(String nickname, String tituloPropuesta) {
        ManejadorUsuario manejadorU = ManejadorUsuario.getInstance();
        manejadorU.quitarFavorita(nickname, tituloPropuesta);
    }
    @Override
    public boolean esFavorita(String nickname, String tituloPropuesta) {
        ManejadorUsuario manejadorU = ManejadorUsuario.getInstance();
        return manejadorU.esFavorita(nickname, tituloPropuesta);
    }
     @Override
     public List<String> ListaSeguidosPorUsuario(String nick){
        /* List<String> aux = new ArrayList<>();
         Usuario usuario = mUsuario.buscador(nick);
         if (usuario != null && usuario.getUsuarioSeguido() != null) {
            for(Usuario u:usuario.getUsuarioSeguido().values()){
                aux.add(u.getNickname());
            }
         }*/
         return mUsuario.listaSeguidos(nick);
     }
     @Override
    public List<DTOColaboracion>  colaboraciones(String nick){
           return mUsuario.getDTOColaboraciones(nick);
    }
     
     
     @Override
      public List<String> colaboradoresAPropuesta(String titulo){
         return  mPropuesta.listColaboradores(titulo);
      }
     
     @Override
     public boolean seguir(String nick1,String nick2){
         
         return mUsuario.seguirUsr(nick1,nick2);

     }
     @Override
     public boolean unFollowUser(String usuarioActual, String usuarioToUnfollow)
     {
        return mUsuario.dejarDeSeguirUsuario(usuarioActual, usuarioToUnfollow);  
     }
     
    // Funciones que devuelven Distintos DTO 
    public DTOColaboracion getDTOAporte(Colaboracion r,String titulo){
        return new DTOColaboracion(r.getTipoRetorno(),r.getMonto(),r.getColaborador().getNickname(),titulo,r.getCreado());
    }
    
  
    public DTORegistro_Estado getDTORegistroEstado(Registro_Estado r){
        return new DTORegistro_Estado(r.getFechaReg(),r.getEstado());
    }
    
    //devuelve un dtoPropuesta con el historial de estado y aportes Recibido
    public DTOPropuesta getDTOPropuesta(Propuesta p,DTOProponente prop){
            DTOPropuesta propuesta= new DTOPropuesta(p,prop);
            List<Registro_Estado> r=p.getHistorialEstados();
            List<Colaboracion> rA = p.getAporte();
            
            for(Registro_Estado re:r){
                propuesta.setHistorialEstados(getDTORegistroEstado(re));
            }

            return propuesta;
    }
    
    @Override
    //me crea un dtoProponente datos basicos
    public DTOProponente getDTOProponente(String nick) { 
           Proponente usr= (Proponente) mUsuario.getUsuario(nick);
          DTOProponente resu=new DTOProponente(usr);
          
          //Map<String,DTOPropuesta> p=mUsuario.getPropuestasCreadas(resu);
           
          //resu.setPropCreadas(p);
           
           return resu;
    }

    public Set<DTOPropuesta> getPropuestasCreadasPorProponente(String nick){

        return mUsuario.getPropuestasCreadasPorProponente(nick);
    }

 
    @Override
     public DTOColaborador getDTOColaborador(String nick) { 
           Colaborador usr= (Colaborador) mUsuario.getUsuario(nick);
           DTOColaborador resu=new DTOColaborador(usr);

           return resu;
    }
     //Fin de Devolucion de DTO
    
     
       
    //web
    @Override
    public List<DTOPropuesta> getFavoritas(String nick){
        
        return mUsuario.getFavoritas(nick);
    
    }
    
    //web
    @Override
    public boolean login(String nick,String Pass){
        return mUsuario.verificarCredenciales(nick,Pass);
    }
    //web
    @Override
    public boolean isProponente(String nick){
        return mUsuarioMongo.isProponente(nick);
    }
   
    @Override
    public void altaPropuesta(String Titulo, String Descripcion, String Imagen, String Lugar, LocalDate Fecha, int Precio, int MontoTotal,LocalDate fechaPublicacio, List<TipoRetorno> Retorno, String cat, String usr,Estado est) {
        
        Propuesta propuesta = new Propuesta (Titulo, Descripcion,Imagen, Lugar, Fecha, Precio, MontoTotal, fechaPublicacio ,Retorno, mCategoria.buscadorC(cat), (Proponente) mUsuario.getUsuario(usr),est);
        mPropuesta.nuevaPropuesta(propuesta);
    }
    @Override
     public Set<DTOPropuesta> obtenerPropuestas(String estado){
        return  mPropuesta.obtenerPropuestas(estado);
     }
     
    @Override
    public Set<DTOPropuesta> obtenerPropuestasExceptoINGRESADAS()
    {
        return mPropuesta.obtenerPropuestasExceptoINGRESADAS();
    }
    
    @Override
    public int accionSobrePropuesta(String nickUsuario, DTOPropuesta propuestaSel)
    {
        return mPropuesta.accionSobrePropuesta(nickUsuario, propuestaSel);
    }
    
    @Override
    public DTOPropuesta getPropuestaDTO(String propuestaSel)
    {
        return mPropuesta.getPropuestaDTO(propuestaSel);

    }
    
    @Override
      public boolean existeProp(String Titulo){
         return (mPropuesta.existeProp(Titulo));
    }
      
      @Override
      public String creadorPropuesta(String titulo){
          return mPropuesta.obtenerNombreCreadorPropuesta(titulo);
      }
      @Override
      public String estadoPropuestas(String titulo){
          return mPropuesta.obtenerEstado(titulo);
      }
      //Fin Propuesta
    
      //Categoria
    @Override
    public boolean altaDeCategoria(DTOCategoria categoriaIngresada) 
    {
        return mCategoria.addCategoria(categoriaIngresada);

    }
    @Override
    public List<DTOCategoria> getCategorias() 
    {

        return mCategoria.getCategorias();
    }
    
    @Override
    public List<String> ListaCategoria()
    {
         List<String> aux2 = new ArrayList<>();
         for(DTOCategoria c : mCategoria.getCategorias())
         {
             aux2.add(c.getNombreCategoria());
         }
             return aux2;
    }
    
    //Propuesta
    
    @Override
    public void modificarPropuesta(String titulo, String descripcion,String rutaImagen, String lugar, LocalDate fechaEvento,int precio, int montoTotal, List<TipoRetorno> retorno,String categoria, String usuarios, Estado estado) {
        Propuesta propuestaSeleccionada = null;
        propuestaSeleccionada = mPropuesta.buscarPropuestaPorTitulo(titulo);
        if (propuestaSeleccionada != null){  
            mPropuesta.UpdatePropuesta(titulo, descripcion,rutaImagen, lugar, fechaEvento,precio, montoTotal, retorno, categoria, usuarios, estado);
        }
     }
     @Override
    public Set<DTOPropuesta> ListarPropuestas(String estado1, String estado2) {
        Set<DTOPropuesta>propuestaEstado1=mPropuesta.obtenerPropuestas(estado1); //estado publicado
        Set<DTOPropuesta> propuestaEstado2=mPropuesta.obtenerPropuestas(estado2);//estado en financiacion
        propuestaEstado1.addAll(propuestaEstado2);
        return propuestaEstado1;
    }
    @Override
    public int extenderOCancelarPropuesta(String accionUsuario,String tituloPropuesta)
    {
        //CASO CANCELAR PROPUESTA
        if (accionUsuario.equals("CANCELAR")) 
        {
            mPropuesta.cancelarPropuestaSeleccionada(tituloPropuesta);
            return 2; //Proponente logra cancelar.
        }

        //CASO EXTENDER FINANCIACION
        if(accionUsuario.equals("EXTENDER")) 
        {
            mPropuesta.extenderFinanciacion(tituloPropuesta);
            return 3; //Proponente logra extender.
        }
        
        return 0;
    }
  
    @Override
    public int accionesSobrePropuesta(String userNick, int permisos, String accionUsuario,String comentario, DTOPropuesta propuestaActual, String montoStr, String tipoRetorno)
    {
        //Seteo tipos de retorno.
        TipoRetorno retorno = null;
        if(tipoRetorno != null)
        {
            if(tipoRetorno.equals("EntradaGratis"))     { retorno = TipoRetorno.EntradaGratis; }
            if(tipoRetorno.equals("PorcentajeGanancia")){ retorno = TipoRetorno.PorcentajeGanancia; }
        }
        
        int resultadoOperacion = 0;
        
        //Si es proponente...
        if(permisos == 1)   
        {        
            //Se verifica que sea una propuesta de este proponente (esto puede ser pasado a una funcion en controller).
            Set<DTOPropuesta> temp = getPropuestasCreadasPorProponente(userNick);
            
            for(DTOPropuesta ct : temp)
            {
                if(ct.nickProponenteToString().equals(userNick))
                {
                    resultadoOperacion = extenderOCancelarPropuesta(accionUsuario,ct.getTitulo());
                    //resultado 3, logra extender, resultado 2, logra cancelar, 0, no sucedió nada.
                }
            }
        }  
    
        //Si es colaborador que ya colaboró...
        if(permisos == 2)
        {
            if(accionUsuario.equals("COMENTAR"))
            {
                if(nuevoComentario(comentario,userNick,propuestaActual.getTitulo()))
                {
                    resultadoOperacion = 1; //Usuario logra comentar.
                }
                
            }   
        }
        
        //Si es colaborador que no colaboró aún y decide colaborar con la propuesta...
        if(permisos == 3) 
        {   
            if(accionUsuario.equals("COLABORAR"))
            {
                int monto = string_A_Int_Con_verificacion(montoStr); //Aca se verifica que esté correcto el ingreso
                
                DTOColaborador usuarioActual = (DTOColaborador) getDTOColaborador(userNick);
                DTOColaboracion nuevaColaboracion = new DTOColaboracion(retorno,monto,usuarioActual.getNickname(),propuestaActual.getTitulo(),LocalDate.now(),usuarioActual,propuestaActual);
                altaColaboracion(nuevaColaboracion);    
                resultadoOperacion = 4;
            }
        }
        
        return resultadoOperacion;
    }
    
    @Override
    public int permisosSobrePropuesta(String userNick, String tipoUsuario, DTOPropuesta propuestaActual)
    {
        int permisos = 0;
        
        if (tipoUsuario != null && !userNick.equals("VISITANTE") && propuestaActual.getTitulo() != null) 
        {
            
            if(!propuestaActual.usuarioHaComentadoSN(userNick))
            {
                permisos = accionSobrePropuesta(userNick, propuestaActual);  //Se obtienen permisos de usuario en propuesta.
            }
            
            if(permisos == 3 && tipoUsuario.equals("Proponente"))   //Esto es por si un proponente visita otras props...
            {
                permisos = 0;   //Le quito el permiso de colaborar, lo dejo por si más adelante se agrega que puede o algo así.
            }
            
        }
        
        return permisos;
    }
    
    @Override
    public boolean nuevoComentario(String comentario,String userNick,String tituloPropuesta)
    {
        if(userNick != null && mPropuesta.nuevoComentario(comentario, userNick, tituloPropuesta))
        {
            return true;
        }
        
        return false;
    }
    
    @Override
    public void altaColaboracion(DTOColaboracion colaboracion){
  
        mColaboraciones.addColaboracion(colaboracion);
        //actualizo despues de agregar la colaboracion 
        mPropuesta.actualizarEstado(colaboracion.getPropuesta());
        
    }


    public Set<DTOColaborador> ListarColaboradores() {
        return mUsuario.listaColaboradores();
    }

  
    
    @Override
    public void CancelarColaboracion(Long id){
         mColaboraciones.deleteColaboracion(id);
    }

    @Override
    public Set<DTOColaborador> ListarColaboradres() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    @Override
    public int getMontoRecaudado(String titulo){
        return mPropuesta.getMontoRecaudado(titulo);
    }
    @Override
    public boolean colaboracionExiste(String colaborador, String titulo){
        
        return mUsuario.existeColaboracion(colaborador, titulo); 
    }
    @Override
    public Set<DTOColaboracion> getDTOColaboraciones(){
        return mColaboraciones.getColaboraciones();
        
    }
    @Override
    public void cargarDatosPruebaProponente(){
        mUsuario.cargarpProponente();
        
    
    }
    @Override
    public void cargarDatosPruebaColaborador(){
        mUsuario.cargarpColaboradores();
    
    }
    @Override
    public void cargarSeguidos(){
        mUsuario.CargarSeguidos();
    }
    @Override
    public void cargarPropuesta(){
        mPropuesta.cargarPropuesta();
    }
    public void cargarCategorias(){
        mCategoria.cargarCategorias();
    }
    
    public void cargarColaboraciones(){
        mColaboraciones.cargarDatosColaboracion();
    }
    @Override
    public int string_A_Int_Con_verificacion(String input)
    {
     //Retorna -1 si el usuario puso algo mal
        int valor = -1;

        if (input != null && !input.isEmpty()) 
        {
            try 
            {
                valor = Integer.parseInt(input);
            } 
            catch (NumberFormatException e) 
            {
                
            }
        }
        
        return valor;
    }
    
    @Override
    public String formateoEstado(String estado)
    {
        return Estado.formateoEstado(estado);
    }
    
    @Override
    public void cerrarAplicacion() {
        PersistenciaMongo.cerrar();
    }

}

  
