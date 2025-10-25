package logica.Propuesta;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Reference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import java.time.LocalDate;
import logica.DTO.TipoRetorno;
import logica.Categoria.Categoria;
import logica.Usuario.Proponente;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import logica.Colaboracion.Colaboracion;
import logica.DTO.DTORegistro_Estado;
import logica.DTO.Estado;

@jakarta.persistence.Entity
@Entity("Propuesta")
public class Propuesta {
    @jakarta.persistence.Id 
    @Id
    private String Titulo;
    @Column(length = 2000)
    private String Descripcion;
    private String Imagen;
    private String Lugar;
    private LocalDate Fecha;
    private int Precio; //tambien debe ser int 
    private int MontoTotal;  //deberia ser int o por lo menos controlar si es texto que se pueda transformar a numero
    private LocalDate FechaPublicacion;
    private LocalDate fechaExpiracion;
    
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "comentarios", joinColumns = @JoinColumn(name = "propuesta"))
    @MapKeyColumn(name = "usuario")   //Mapeo de la Key
    @Column(name = "comentario")       //Mapeo del value
    private Map<String,String> comentarios = new HashMap<>();
            
    @ElementCollection(targetClass = TipoRetorno.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "retorno", // nombre de la tabla 
    joinColumns = @JoinColumn(name = "propuesta") )
    @Column(name = "retorno") // columna donde se guarda el enum
    private List<TipoRetorno> Retorno = new ArrayList<>();//guardo los retornos soportados
    
    @ManyToOne
    @JoinColumn(name = "categoria")
    @Reference
    private Categoria cat;
    
    @ManyToOne
    @JoinColumn(name = "proponente")
    @Reference
    private Proponente usr;
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
    @JoinColumn(name = "propuesta") // FK en la tabla registro_estado
    @OrderBy("id DESC") //Orden de más nuevo a más antiguo
    @Reference
    private List<Registro_Estado> historialEstados = new ArrayList<>();
    
    @OneToMany(mappedBy = "propuesta", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
    @Reference
    private List<Colaboracion> Aporte= new ArrayList<>();// se guarda los aportes que a recibido la propuesta 
            
    public Propuesta(){}
    public Propuesta(String Titulo,String Descripcion,String Imagen ,String Lugar, LocalDate Fecha, int Precio, int MontoTotal,LocalDate FechaPublicacion,List<TipoRetorno> Retorno,Categoria cat,Proponente usr,Estado estadoAct)
    {
        this.Titulo=Titulo;
        this.Descripcion=Descripcion;
        this.Imagen=Imagen;
        this.Lugar=Lugar;
        this.Fecha=Fecha;
        this.Precio=Precio;
        this.MontoTotal=MontoTotal;
        this.FechaPublicacion=FechaPublicacion;
        this.fechaExpiracion=null;
        this.cat=cat;
        this.usr=usr;
        this.Retorno = Retorno;
        this.historialEstados.add(0,(new Registro_Estado(LocalDate.now(), estadoAct)));    //Añade al inicio!  
    }
    public  String getTitulo() {
        return Titulo;
    }
    public  String getDescripcion() {
        return Descripcion;
    }
    public  String getImagen() {
        return Imagen;
    }
    public  String getLugar() {
        return Lugar;
    }
    public  LocalDate getFecha() {
        return Fecha;
    }
    public LocalDate getFechaExpiracion()
    {
        return fechaExpiracion;
    }
    public int getPrecio() {
        return Precio;
    }
    public int getMontoTotal() {
        return MontoTotal;
    }
    public LocalDate getFechaPublicacion() {
        return FechaPublicacion;
    }
    public List<TipoRetorno> getRetorno() {
        return Retorno;
    }
  
    public Categoria getCategoria(){
        return cat;
    }
    public Proponente getProponente(){
        return usr;
    }
    public List<Registro_Estado> getHistorialEstados() 
    {
        return historialEstados;
    }
    public void setTitulo(String titulo) {
        Titulo = titulo;
    }
    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }
    public void setImagne(String imagen) {
        Imagen = imagen;
    }
    public void setLugar(String lugar) {
        Lugar = lugar;
    }

    public void setFecha(LocalDate fecha) {
        Fecha = fecha;
    }

    public void setPrecio(int precio) {
        Precio = precio;
    }

    public void setMontoTotal(int montoTotal) {
        MontoTotal = montoTotal;
    }

    public void setFechaPublicacion(LocalDate _FechaPublicacion) {
        FechaPublicacion = _FechaPublicacion;
    }
    public void setRetornos(List<TipoRetorno> retorno) { //aca retorno debe clickear sobre los dos que se quiera tener en la propuesta sino los reemplaza (osea borra uno si no lo selecciona)
        this.Retorno = retorno;                            
    }
    public void setCategoria(Categoria Cat){
        cat = Cat;
    }
    public void setProponente(Proponente Propo){
        usr = Propo;
    }
    public void setHistorialEstados(List<Registro_Estado> _historial) 
    {
        historialEstados = _historial;
    }
    
    public void setComentarios(Map<String,String> input)
    {
        this.comentarios = input;
    }
    
    public void addNewComentario(String usuario, String comentario)
    {
        if(!usuario.isEmpty() && !comentario.isEmpty())
        {
            String comentarioConFecha = "<span style='color:gray; font-style:italic;'>" + LocalDate.now().toString() + "</span><br><p>" + comentario + "</p>"; // El <br> ese es un salto de línea  y le agrego italc gris a la fecha en HTML
            this.comentarios.put(usuario,comentarioConFecha);
        }
       
    }

    public List<Colaboracion> getAporte() {
        return Aporte;
    }
    public void setColaboracion(Colaboracion c){
        Aporte.add(c);
    }
    public String getUltimoEstadoString()
    {
        if(!historialEstados.isEmpty()) 
        {
            return historialEstados.get(0).getEstado().name();
        } 
        else 
        {
            return "";
        }
    }
    public DTORegistro_Estado getUltimoEstado()
    {
        DTORegistro_Estado almacen = new DTORegistro_Estado();
        
        if(!historialEstados.isEmpty())
        {
            almacen.extraerDatos(historialEstados.get(0));  //El ultimo nodo ingresado se almacena en el DTO
            return almacen;
        }
        return almacen;
    }
    public void addEstHistorial(Estado aux1){     
        Registro_Estado nuevoReg = new Registro_Estado(LocalDate.now(),aux1);
       
        if(aux1.equals(Estado.PUBLICADA))   //A partir de que es publicada, se indica la fecha de expiracion
        {
            this.fechaExpiracion= LocalDate.now().plusDays(30); //Al ser publicada se agregan 30 días antes de vencimiento.
        }
        //this.historialEstados.addFirst(nuevoReg);
        this.historialEstados.add(0, nuevoReg);
    }
    public void extenderFinanciacion()
    {
        addEstHistorial(Estado.PUBLICADA);
               
    }
    public void addRetorno(List<TipoRetorno> retornos) { //NO lo uso por el momento pero esto en vez de sobreescibir te deja agregar algo que falte en los retonos
        for (TipoRetorno r : retornos) {
            if (!this.Retorno.contains(r)) {
                this.Retorno.add(r);
            }
        }
    }
    
    public Map<String,String> getComentarios()
    {
        return comentarios;
    }
    
    public boolean usuarioHaComentadoSN(String nick)    
    {
        if(comentarios.containsKey(nick))
        {
            return true;    //El usuario ya comentó
        }
        
        return false;
    }
}
