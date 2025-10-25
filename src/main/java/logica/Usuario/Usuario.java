
package logica.Usuario;


 
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Property;
import dev.morphia.annotations.Reference;

import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.MapKey;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import logica.Propuesta.Propuesta;
import java.util.Iterator;


//@jakarta.persistence.Entity
//@jakarta.persistence.Inheritance(strategy = InheritanceType.JOINED)
@Entity(value="usuarios", useDiscriminator=true)
public class Usuario {
    
    //@jakarta.persistence.Id //solo por ahora para que funciones con bd relaciona o mongo
    @Id
    private String nickname;
    private String pass;
    private String nombre;
    private String apellido;
    @Property("Email")
    private String email;
    private LocalDate fecha;
    private String rutaImg;
    
//    @ManyToMany
//    @JoinTable(
//        name = "usuario_seguidos",
//        joinColumns = @JoinColumn(name = "seguidor"),          // el que sigue
//        inverseJoinColumns = @JoinColumn(name = "seguido")     // al que sigo
//    )
//    @MapKey(name = "nickname")
    @Reference(lazy=true) //carga perezosa evita bucle al traer y mapear objeto entero (solo trae referenci hasta que le digamos getusuarioSeguidos)
    private Map<String,Usuario> usuarioSeguido=new HashMap<>();
    
//    @ManyToMany
//    @JoinTable(
//        name = "propuesta_favorita",
//        joinColumns = @JoinColumn(name = "usuario"),          // el usuario
//        inverseJoinColumns = @JoinColumn(name = "propuesta")     // la prop favorita
//    )
//    @MapKey(name = "Titulo") // asigna 
    @Reference(lazy=true)
    private Map<String,Propuesta> propFavorita=new HashMap<>();

   
    public Usuario(){}
   
    public Usuario(String nickname, String nombre, String apellido, String email, LocalDate fecha, String rutaImg) {
        this.nickname = nickname;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.fecha = fecha;
        this.rutaImg = rutaImg;
    }

    public Usuario(String nickname, String pass, String nombre, String apellido, String email, LocalDate fecha, String rutaImg) {
        this.nickname = nickname;
        this.pass = pass;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.fecha = fecha;
        this.rutaImg = rutaImg;
    }
    
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    
    public String getPass() {
        return pass;
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
    
    public void setRutaImg(String rutaImg) {
        this.rutaImg = rutaImg;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getNickname() {
        return nickname;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public String getEmail() {
        return email;
    }
    
     public String getRutaImg() {
        return rutaImg;
    }
    public Map<String, Propuesta> getPropFavorita() {
        return propFavorita;
    } 
    public boolean isColaborador() {
        return false;
    };
    /*public String toString() {///?????
        return getNickname();
    }*/
    
    public void seguir(Usuario usu){
        String n = usu.getNickname();
        this.usuarioSeguido.put(n, usu);
    }
    public void Favorita(Propuesta prop){
        this.propFavorita.put(prop.getTitulo(),prop);
    }
    public boolean esFavorita(Propuesta prop) {
        return propFavorita.containsKey(prop.getTitulo());
    }
    public Map<String, Usuario> getUsuarioSeguido() {
        return usuarioSeguido;
    }
    public void quitarFavorita(Propuesta prop) {
            propFavorita.remove(prop.getTitulo());
    }
    public boolean unfollow(Usuario usr)
    {
        if(usr != null) //Raro que suceda pero por las dudas...
        {
            if(!usuarioSeguido.isEmpty())
            {
                Iterator<Map.Entry<String, Usuario>> ct = usuarioSeguido.entrySet().iterator();

                while(ct.hasNext()) 
                {
                    Map.Entry<String,Usuario> nodoActual = ct.next();

                    if(nodoActual.getValue() == usr)
                    {
                        System.out.println("llego a remover?");
                        ct.remove();    //Se borra el nodo del usuario a dejar de seguir desde el iterator.
                        return true;
                    }

                }
            }
        }
        
        return false;   //Posible error
        
    }
    
}

