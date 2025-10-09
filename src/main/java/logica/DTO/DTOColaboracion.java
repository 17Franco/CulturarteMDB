
package logica.DTO;

import java.time.LocalDate;
import logica.Colaboracion.Colaboracion;
import logica.Usuario.Colaborador;
import logica.Propuesta.Propuesta;


public class DTOColaboracion {
        private TipoRetorno tipoRetorno;

        private int monto;
        private String imgDePropuesta;
        private String  colaborador;
        private String propuesta;
        private DTOPropuesta propuestaP;// veo inesesarios tener los punteros en el dtoColaboracion
        private DTOColaborador colaboradorP;
        private LocalDate creado;
    
    private Long id;

public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
    public boolean equals(DTOColaboracion obj) {
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
    public DTOColaboracion(){}
    public DTOColaboracion(TipoRetorno tipoRetorno, int monto, String colaborador, String propuesta, LocalDate creado) {
        this.tipoRetorno = tipoRetorno;
        this.monto = monto;
        this.colaborador = colaborador;
        this.propuesta = propuesta;
        this.creado = creado;
    }
    
    public DTOColaboracion(TipoRetorno tipoRetorno, int monto, String colaborador, String propuesta, LocalDate creado, DTOColaborador colaboradorP, DTOPropuesta propuestaP) 
    {
        this.tipoRetorno = tipoRetorno;
        this.monto = monto;
        this.colaborador = colaborador;
        this.propuesta = propuesta;
        this.colaboradorP = colaboradorP;
        this.propuestaP = propuestaP;
        this.creado = creado;
    }
    
     public DTOColaboracion(Colaboracion colaboracion){
         tipoRetorno=colaboracion.getTipoRetorno();
         monto=colaboracion.getMonto();
         creado=colaboracion.getCreado();
         colaborador=colaboracion.getColaborador().getNickname();
         propuesta=colaboracion.getPropuesta().getTitulo();
         imgDePropuesta=colaboracion.getPropuesta().getImagen();
     }

     
    public LocalDate getCreado() {
        return creado;
    }

    public String getImgDePropuesta() {
        return imgDePropuesta;
    }

    
    public void setCreado(LocalDate creado) {
        this.creado = creado;
    }


    public String getPropuesta() {
        return propuesta;
    }

    public void setPropuesta(String propuesta) {
        this.propuesta = propuesta;
    }


   
    public String getColaborador() {
        return colaborador;
    }

    
    public void setColaborador(String  colaborador) {
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
    
    public void setPropuestaP (DTOPropuesta propuestaP)
    {
        this.propuestaP = propuestaP;
    }
    
    public void setColaboradorP (DTOColaborador colaboradorP)
    {
       this.colaboradorP = colaboradorP;
    }
    
    public DTOPropuesta getPropuestaP()
    {
        return propuestaP;
    }
    
    public DTOColaborador getColaboradorP()
    {
       return colaboradorP;
    }
}
