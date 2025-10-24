
package logica.DTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DTOUsuario {
    private String nickname;
    private String pass;
    private String nombre;
    private String apellido;
    private String email;
    private LocalDate fecha;
    private String rutaImg;
    private String tipoUsr;
    private List<String> usuarioSeguido=new ArrayList<>(); //las saco porque si solicito la info no pongo todo dentro del dto
    private List<String> propFavorita=new ArrayList<>();     // el dto es para manda info basica 

    
    public DTOUsuario(){}
    
    public DTOUsuario(String nickname, String nombre, String apellido, String email, LocalDate fecha, String rutaImg) {
        this.nickname = nickname;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.fecha = fecha;
        this.rutaImg = rutaImg;
        
    }

    public void setTipoUsr(String tipoUsr) {
        this.tipoUsr = tipoUsr;
    }
    
    public DTOUsuario(String nickname, String pass, String nombre, String apellido, String email, LocalDate fecha, String rutaImg,String tipoUsr) {
        this.nickname = nickname;
        this.pass = pass;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.fecha = fecha;
        this.rutaImg = rutaImg;
    }
    
    public String getNickname() {
        return nickname;
    }

    public String getPass() {
        return pass;
    }
    
    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getEmail() {
        return email;
    }

    public String getTipoUsr() {
        return tipoUsr;
    }
   
    

    public LocalDate getFecha() {
        return fecha;
    }
    

    public String getRutaImg() {
        return rutaImg;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setRutaImg(String rutaImg) {
        this.rutaImg = rutaImg;
    }
    
    
}
