
package logica.Usuario;
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Reference;
import jakarta.persistence.CascadeType;

import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import logica.Colaboracion.Colaboracion;
import logica.DTO.DTOColaborador;


@jakarta.persistence.Entity

public class Colaborador extends Usuario{
    
     
    @OneToMany(mappedBy = "colaborador", cascade = CascadeType.ALL)
    @Reference
    private List<Colaboracion> colaboraciones= new ArrayList<>();

    public Colaborador() {
    }

    public Colaborador(String nickname, String nombre, String apellido, String email, LocalDate fecha, String rutaImg) {
        super(nickname, nombre, apellido, email, fecha, rutaImg);
    }

    public List<Colaboracion> getColaboraciones() {
        return colaboraciones;
    }

    public void setColaboraciones(Colaboracion r){
        colaboraciones.add(r);
    }
    
    public Colaborador(DTOColaborador dto){
        super(dto.getNickname(),dto.getPass(), dto.getNombre(), dto.getApellido(),dto.getEmail(), dto.getFecha(), dto.getRutaImg());
        
    }
    
     
        
     public boolean isColaborador(){
         return true;
     }
}
