
package logica;

//Singleton//lugar cenrtralizado para intanciar objetos
public class Fabrica {
    
    //private static IController controller;
    private static Fabrica fabrica=null;
    private Fabrica() {
        // Constructor privado para que no se pueda instanciar
    }
    
    public static Fabrica getInstance() { //singleton
        if (fabrica == null) {
            fabrica= new Fabrica(); // instancia única
        }
        
        return fabrica;
    }
    
    public IController getController() {//no es singleton 
        
            return new Controller(); 
    }
}
