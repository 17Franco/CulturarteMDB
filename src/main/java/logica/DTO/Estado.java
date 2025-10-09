package logica.DTO;

public enum Estado
{   
     INGRESADA, 
     PUBLICADA, 
     EN_FINANCIACION, 
     FINANCIADA, 
     NO_FINANCIADA, 
     CANCELADA; 
     
    public static String formateoEstado(String estado)
    {//Es para mostrar los estados en interfaz web
        
        switch (estado) 
        {
            case "INGRESADA":estado= " Ingresada";break;
            case "PUBLICADA":estado= " Publicada";break;
            case "EN_FINANCIACION":estado= " En financiación";break;
            case "FINANCIADA":estado= " Financiada";break;
            case "NO_FINANCIADA":estado= " No financiada";break;
            case "CANCELADA":estado= " Cancelada";break;
            default:estado= " Desconocido";break;
        }
        
        return estado;
    }
}

