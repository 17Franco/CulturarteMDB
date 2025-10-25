package logica.Propuesta;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.time.LocalDate;
import logica.DTO.Estado;

@jakarta.persistence.Entity
@Entity("Registro_Estado")
public class Registro_Estado {
    @jakarta.persistence.Id 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private LocalDate fechaReg;
    
    @Enumerated(EnumType.STRING)
    private Estado estado;

    public Registro_Estado() 
    {
    }

    public Registro_Estado(LocalDate _fechaReg, Estado _estado)
    {
        fechaReg = _fechaReg;
        estado = _estado;

    }

    public LocalDate getFechaReg() 
    {
        return fechaReg;
    }

    public Estado getEstado() 
    {
        return estado;
    }

    public void setFechaReg(LocalDate _fechaReg) 
    {
        fechaReg = _fechaReg;
    }

    public void setEstados(Estado _estado) 
    {
        estado = _estado;
    }

}
