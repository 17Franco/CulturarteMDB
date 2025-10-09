package logica.Colaboracion;

import java.time.LocalDate;
import logica.DTO.DTOColaboracion;
import logica.Propuesta.Propuesta;
import logica.Usuario.Colaborador;
import logica.DTO.TipoRetorno;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Colaboracion {
    
         @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;  
         
         @Enumerated(EnumType.STRING)
        private TipoRetorno tipoRetorno;

        private int monto;
        
        @ManyToOne
        @JoinColumn(name = "colaborador")
        private Colaborador colaborador;
        
        @ManyToOne
        @JoinColumn(name = "propuesta")
        private Propuesta propuesta;

        private LocalDate creado;
    
    public Colaboracion() {
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public boolean equals(Colaboracion obj) {
        if (this.monto != obj.monto) {
            return false;
        }
        if (this.tipoRetorno != obj.tipoRetorno) {
            return false;
        }
        if (!this.colaborador.equals(obj.colaborador)) {
            return false;
        }
        if (this.propuesta.equals(obj.propuesta) ) {
            return false;
        }
        return this.creado.equals(obj.creado);
    }

     public Colaboracion(TipoRetorno tipoRetorno, int monto, Colaborador colaborador, Propuesta propuesta, LocalDate creado) {
        this.tipoRetorno = tipoRetorno;
        this.monto = monto;
        this.colaborador = colaborador;
        this.propuesta = propuesta;
        this.creado = creado;
    }
     public Colaboracion(DTOColaboracion colaboracion,Colaborador c, Propuesta p){
         tipoRetorno=colaboracion.getTipoRetorno();
         monto=colaboracion.getMonto();
         creado=colaboracion.getCreado();
         colaborador=c;
         propuesta=p;
     }
     
    public LocalDate getCreado() {
        return creado;
    }

    
    public void setCreado(LocalDate creado) {
        this.creado = creado;
    }


    public Propuesta getPropuesta() {
        return propuesta;
    }

    public void setPropuesta(Propuesta propuesta) {
        this.propuesta = propuesta;
    }


   
    public Colaborador getColaborador() {
        return colaborador;
    }

    
    public void setColaborador(Colaborador colaborador) {
        this.colaborador = colaborador;
    }


    public int getMonto() {
        return monto;
    }

  
    public void setMonto(int monto) {
        this.monto = monto;
    }


    public TipoRetorno getTipoRetorno() {
        return tipoRetorno;
    }

    
    public void setTipoRetorno(TipoRetorno tipoRetorno) {
        this.tipoRetorno = tipoRetorno;
    }

}