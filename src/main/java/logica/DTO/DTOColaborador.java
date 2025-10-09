/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica.DTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import logica.Usuario.Colaborador;



public class DTOColaborador extends DTOUsuario{
    private List<DTOColaboracion> colaboraciones= new ArrayList<>();

    public DTOColaborador(String nickname, String nombre, String apellido, String email, LocalDate fecha, String rutaImg) {
        super(nickname, nombre, apellido, email, fecha, rutaImg);
    }

    public DTOColaborador(String nickname, String pass, String nombre, String apellido, String email, LocalDate fecha, String rutaImg,String tipoUsr) {
        super(nickname, pass, nombre, apellido, email, fecha, rutaImg,tipoUsr);
    }
    
    public void setColaboracion(DTOColaboracion r){
            colaboraciones.add(r);
    }
      public DTOColaborador(Colaborador c){
        super(c.getNickname(), c.getNombre(), c.getApellido(),c.getEmail(), c.getFecha(), c.getRutaImg());  
    }
      
     public boolean isColaborador(){
         return true;
     }

    public List<DTOColaboracion> getColaboraciones() {
        return colaboraciones;
    }
     
}
