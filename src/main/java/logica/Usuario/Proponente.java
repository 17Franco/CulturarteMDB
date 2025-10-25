
package logica.Usuario;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Reference;

import jakarta.persistence.Lob;
import jakarta.persistence.MapKey;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import logica.DTO.DTOProponente;
import logica.Propuesta.Propuesta;


@jakarta.persistence.Entity
@Entity
public class Proponente extends Usuario{
    private String direccion;
    
    @Lob// tipo de dato mas grande
    @Column(columnDefinition = "TEXT")
    private String biografia;
    private String webSite;
    
    @OneToMany(mappedBy = "usr", cascade = CascadeType.ALL, orphanRemoval = true)
    @MapKey(name = "Titulo") 
    @Reference(lazy = false)
    private Map<String,Propuesta> propCreadas=new HashMap<>();

    public Proponente() {
    }

    public Proponente(String direccion, String biografia, String webSite, String nickname, String nombre, String apellido, String email, LocalDate fecha, String rutaImg) {
        super(nickname, nombre, apellido, email, fecha, rutaImg);
        this.direccion = direccion;
        this.biografia = biografia;
        this.webSite = webSite;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }
    public void setPropuestaCreada(Propuesta p){
        propCreadas.put(p.getTitulo(), p);
    }
    
    public String getBiografia() {
        return biografia;
    }

    public String getWebSite() {
        return webSite;
    }

    public String getDireccion() {
        return direccion;
    }

    public Map<String, Propuesta> getPropCreadas() {
        return propCreadas;
    }
    public Proponente(DTOProponente dto){
        super(dto.getNickname(),dto.getPass(),dto.getNombre(), dto.getApellido(),dto.getEmail(), dto.getFecha(), dto.getRutaImg());
        this.direccion = dto.getDireccion();
        this.biografia = dto.getBiografia();
        this.webSite = dto.getWebSite();
    }
    
    
    
     public boolean isProponente(){
         return true;
     }
   
}
