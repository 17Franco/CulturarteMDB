
package logica.DTO;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import logica.Usuario.Proponente;




public class DTOProponente extends DTOUsuario{
    private String direccion;
    private String biografia;
    private String webSite;
    //private Map<String,DTOPropuesta> propCreadas=new HashMap<>();

    public DTOProponente() {
    }
    
    public DTOProponente(String direccion, String biografia, String webSite, String nickname, String nombre, String apellido, String email, LocalDate fecha, String rutaImg) {
        super(nickname, nombre, apellido, email, fecha, rutaImg);
        this.direccion = direccion;
        this.biografia = biografia;
        this.webSite = webSite;
    }

    public DTOProponente(String direccion, String biografia, String webSite, String nickname, String pass, String nombre, String apellido, String email, LocalDate fecha, String rutaImg,String tipoUsr) {
        super(nickname, pass, nombre, apellido, email, fecha, rutaImg,tipoUsr);
        this.direccion = direccion;
        this.biografia = biografia;
        this.webSite = webSite;
    }
    
    public String getDireccion() {
        return direccion;
    }

    public String getBiografia() {
        return biografia;
    }

    public String getWebSite() {
        return webSite;
    }
    
   /* public void addDTOPropuesta(DTOPropuesta p){
        propCreadas.put(p.getTitulo(), p);
    }

    public void setPropCreadas(Map<String, DTOPropuesta> propCreadas) {
        this.propCreadas = propCreadas;
    }
    
    public Map<String, DTOPropuesta> getPropCreadas() {
        return propCreadas;
    }*/
    public DTOProponente(Proponente p){
        super(p.getNickname(), p.getNombre(), p.getApellido(),p.getEmail(), p.getFecha(), p.getRutaImg());
        this.direccion = p.getDireccion();
        this.biografia = p.getBiografia();
        this.webSite = p.getWebSite();
    }
        
    public boolean isProponente(){
        return true;
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
    
}
