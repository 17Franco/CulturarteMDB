package ui;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.DateTimeException;
import java.time.LocalDate;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


public class Utilities {
    
    //permite elejir un archivo
    public static String elejirArchivo(){
        JFileChooser fileChooser = new JFileChooser("/hostDesktop");
        fileChooser.setDialogTitle("Seleccionar imagen");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
     
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "Imagenes", "jpg","png"));

        int resultado = fileChooser.showOpenDialog(null); 

        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();
           return archivo.getAbsolutePath(); 
        }else return null;
    }
    //validacion de campo
    public static boolean validarNoVacio(JTextField campo) { 
        if (campo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null,
                "Por favor completa el campo: " + campo.getName(),
                "Campo vacío",
                JOptionPane.WARNING_MESSAGE);
            campo.requestFocus();
            return false;
        }
        return true;
    }
    
    
    //copia la img seleccionada y la guarda en la LA CARPETA NomUsuario/IMG
    public static String copiarImagen(String rutaOriginal, String nick) {
        if (rutaOriginal == null || rutaOriginal.isEmpty()) {
            return null; // retornamos null si no hay ruta
        }

        File archivo = new File(rutaOriginal);
        String carpetaDestino = "IMG" + File.separator + nick;
        File dir = new File(carpetaDestino);
        if (!dir.exists()) dir.mkdirs();

        Path origen = archivo.toPath();
        Path destino = Paths.get(carpetaDestino, archivo.getName());

        try {
            Files.copy(origen, destino, StandardCopyOption.REPLACE_EXISTING);
            return destino.toString(); // convertimos Path a String
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error copiando la imagen");
            return null; // en caso de error devolvemos null
        }
    }

    
    public static boolean validarFecha(String dia, String mes, String anio) {
        try {
            int d = Integer.parseInt(dia);
            int m = Integer.parseInt(mes);
            int a = Integer.parseInt(anio);

            LocalDate.of(a, m, d);
            return true;
        } catch (NumberFormatException e) { //valida que se ponga numeros
            JOptionPane.showMessageDialog(null,
                "La fecha debe contener solo números",
                "Formato incorrecto",
                JOptionPane.WARNING_MESSAGE);
            return false;
        } catch (DateTimeException e) { // valida que mes sea del 1 al 12 y  que no ponga por ej 30/02 
            JOptionPane.showMessageDialog(null,
                "La fecha ingresada no es válida",
                "Fecha incorrecta",
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
    }
}
