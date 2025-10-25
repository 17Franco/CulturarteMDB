/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import logica.Categoria.Categoria;
import logica.DTO.DTOCategoria;
import logica.DTO.DTOPropuesta;
import logica.DTO.DTOColaborador;
import logica.DTO.DTOProponente;
import logica.Colaboracion.Colaboracion;
import logica.DTO.DTOColaboracion;
import logica.DTO.DTOUsuario;
import logica.DTO.Estado;
import logica.DTO.TipoRetorno;


public interface IController {
    //Usuarios
    void altaUsuario(DTOUsuario usu);
    
    boolean existeUsuario(String nick, String email);
    
    boolean emailUsado(String email);
    
    boolean existe(String nick);
    
     List<String> ListaUsuarios();
    
    List<String> ListaProponentes();
    
    List<String> ListaColaborador();
    
    List<DTOColaboracion>  colaboraciones(String nick);
    
    List<String>ListaSeguidosPorUsuario(String nick);
    
    DTOProponente getDTOProponente(String nick);
    
    DTOColaborador getDTOColaborador(String nick);
    
    List<String> colaboradoresAPropuesta(String titulo);
    
    boolean seguir(String nick1,String nick2);
    
    boolean unFollowUser(String usuarioActual, String usuarioToUnfollow);
    
    Set<DTOPropuesta> getPropuestasCreadasPorProponente(String nick);
    
    //SON DE WEB (OTRO PROYECTO NO SE USAN ACA)
    void registroUsuario(String nickname, String pass, String nombre, String apellido, String email, LocalDate fecha, byte[] contenido,String nombreArchivo,boolean isProponente,String direccion,String web,String Biografia);
    
    boolean login(String nick,String Pass);
    
    boolean isProponente(String nick);
    
    List<DTOPropuesta> getFavoritas(String nick);
    
    List<DTOUsuario> getSeguidores(String nick);
    
    List<DTOUsuario>Seguidos(String nick);
    
    boolean sigueAUsuario(String seguidor,String Seguido);
    
    void marcarComoFavorita(String nickname, String tituloPropuesta);
    
    void quitarFavorita(String nickname, String tituloPropuesta);
    
    boolean esFavorita(String nickname, String tituloPropuesta);
    
    List<DTOUsuario> ListaDTOUsuarios();

    //Fin SON DE WEB (OTRO PROYECTO NO SE USAN ACA)
    
    //Fin Usuario
    
    //Propuestas
    void altaPropuesta(String Titulo, String Descripcion, String Imagen, String Lugar, LocalDate Fecha, int Precio, int MontoTotal, LocalDate fechaPublicacio,List<TipoRetorno> Retorno, String cat, String usr,Estado est);
    void modificarPropuesta(String titulo, String descripcion, String rutaImagen, String lugar, LocalDate fechaEvento,int precio, int montoTotal, List<TipoRetorno> retorno, String categoria, String usuarios, Estado estado);
    
    boolean existeProp(String Titulo);
    
    Set<DTOPropuesta> obtenerPropuestas(String estado);
    
    DTOPropuesta getPropuestaDTO(String propuestaSel);
    
    Set<DTOPropuesta> ListarPropuestas(String estado1, String estado2);
    
    String creadorPropuesta(String titulo);
    
    String estadoPropuestas(String titulo);
    
    
    //SON DE WEB (OTRO PROYECTO NO SE USAN ACA)
    Set<DTOPropuesta> obtenerPropuestasExceptoINGRESADAS();
    
    int extenderOCancelarPropuesta(String accionUsuario,String tituloPropuesta);
    
    boolean nuevoComentario(String comentario,String userNick,String tituloPropuesta);
    
    int accionesSobrePropuesta(String userNick, int permisos, String accionUsuario,String comentario, DTOPropuesta propuestaActual, String montoStr, String tipoRetorno);
    
    
    int permisosSobrePropuesta(String userNick, String tipoUsuario, DTOPropuesta propuestaActual);
    
    
    int accionSobrePropuesta(String nickUsuario, DTOPropuesta propuestaSel);
    //FIN SON DE WEB (OTRO PROYECTO NO SE USAN ACA)
    
    //Fin Propuesta
    
    //Categoria
    boolean altaDeCategoria(DTOCategoria categoriaIngresada);
    
    List<DTOCategoria> getCategorias();
    
    //WEB
    List<String> ListaCategoria();
    
    //Fin Categoria
    
    //COLABORACION
    void altaColaboracion(DTOColaboracion colaboracion); 
    
    boolean colaboracionExiste(String colaborador, String titulo);
    
    int  getMontoRecaudado(String titulo);

    
    void CancelarColaboracion(Long id);  
    
    Set<DTOColaboracion> getDTOColaboraciones();
    //Fin COLABORACION
    
    //CARGA DE DATOS
    void cargarDatosPruebaProponente();
    
    void cargarDatosPruebaColaborador();
    
    void cargarSeguidos();

    void cargarPropuesta();
    
    void cargarCategorias();
    
    void cargarColaboraciones();
    //FIN CARGA DE DATOS
    
    //verificaciones y auxiliares
    //WEB
    int string_A_Int_Con_verificacion(String input);
    //WEB
    String formateoEstado(String estado);
   
    public void cerrarAplicacion();
}
