package persistencia;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import logica.DTO.DTOCategoria;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.List;
import logica.Categoria.Categoria;

public class ManejadorCategoria {

    private Map<String, Categoria> AlmacenCategorias; //El nombre de la categorpia al ser unico servirá para usar como id.
    private static ManejadorCategoria instancia = null; 
    private EntityManager dbManager;    //Conector a base de datos.
    
    
    private ManejadorCategoria() 
    {
        AlmacenCategorias = new HashMap<String, Categoria>();
    }

    public static ManejadorCategoria getInstance() 
    {
        if (instancia == null) 
        {
            instancia = new ManejadorCategoria();
        }
        return instancia;
    }

    public List<DTOCategoria> getCategorias()
    {
        List<DTOCategoria> almacen = new ArrayList<>();
        
        dbManager = PersistenciaManager.getEntityManager(); //Se asigna base de datos
        
        try 
        {
            List<Categoria> datosImportadosDb = dbManager.createQuery("select catImport from Categoria catImport where catImport.catPadre is NULL", Categoria.class).getResultList();   //Se traen todos los nodos padre.

            for (Categoria ct : datosImportadosDb) 
            {
                DTOCategoria temp = ct.Cat_a_DTO(); //Se pasa a DTO.
                almacen.add(temp);                  //Se almacena en la lista.
            }    
        } 
        finally 
        {
            dbManager.close();
        }
        
        return almacen;
    }

    public DTOCategoria obtenerCategoriaPorNombre(String nombreCategoria) 
    {
        Categoria temp = AlmacenCategorias.get(nombreCategoria);
        
        DTOCategoria almacen = new DTOCategoria(temp.getNombreCategoria(), null,"",temp.getSubcategorias());
        
        return almacen;
    }

    public boolean addCategoria(DTOCategoria categoriaIngresada) 
    { 
        boolean pass = false;
        
        dbManager = PersistenciaManager.getEntityManager();
        EntityTransaction transaccionActual = dbManager.getTransaction();

        transaccionActual.begin(); //Se inicia el token de db
        
        
        if(categoriaIngresada.getCatPadre().isEmpty()) //Si es categoría padre
        {       
            
            Categoria cat1 = new Categoria(categoriaIngresada.getNombreCategoria(),null);
            
            try
            {
                dbManager.persist(cat1);
                transaccionActual.commit(); //Se cierra token de db
                System.out.print("\nSe almacenaron datos de categoría " + cat1.getNombreCategoria() + " en la base de datos\n");  //Esto es para corroborar en consola

                pass = true;    //Aviso en bool que fué todo asignado correctamente o a la espera de posible exception db.
            }
            catch(Exception wtf)
            {
                if(transaccionActual.isActive())    //Si no hubo desconexión inesperada...
                {
                    System.out.print("\nError, no se alacenaron datos de categoría " + cat1.getNombreCategoria() + " en la base de datos\n");  //Esto es para corroborar en consola
                    transaccionActual.rollback();   //Se cancela la transacción y se deshace cualquier cambio.
                }
                
                pass = false;   //Almaceno en bool que hubo un error inesperado al ingresar los datos...
            }
            finally
            {
                dbManager.close(); 
            }  
        }

        if (!categoriaIngresada.getCatPadre().isEmpty()) //Si es una subcategoria:
        {

            
            //Almacenamiento en DB
            Categoria temp = dbManager.find(Categoria.class,categoriaIngresada.getCatPadre());
            
            if(temp != null && dbManager.find(Categoria.class, categoriaIngresada.getNombreCategoria()) == null)    //Si encuentra algo y la subcat ingresada no existe...
            {
                categoriaIngresada.setNodoPadre(temp);              //Se le asigna en nodo padre a la subcat...
                temp.addDTOSubcategoria(categoriaIngresada);        //Se actualiza el nodo padre.

                try
                {
                    dbManager.merge(temp);              //Se sobreescribe al que estaba antes.
                    transaccionActual.commit();         //Se cierra token de db alv
                    
                    if(temp.getSubcategorias() != null)
                    {
                        System.out.print("\nSe almacenaron datos de subcategoría " + categoriaIngresada.getNombreCategoria() + " en la categoría " + temp.getNombreCategoria() + " en la base de datos\n");  //Esto es para corroborar en consola
                    }
                    
                    pass = true;    //Aviso en bool que fué todo asignado correctamente o a la espera de posible exception db.
                }
                catch(Exception wtf2)
                {
                    if(transaccionActual.isActive())    //Si no hubo desconexión inesperada...
                    {
                        System.out.print("\nError, no se alacenaron datos de subcategoría " + temp.getNombreCategoria() + " en la base de datos\n");  //Esto es para corroborar en consola
                        transaccionActual.rollback();   //Se cancela la transacción y se deshace cualquier cambio.
                    }

                    pass = false;   //Almaceno en bool que hubo un error inesperado al ingresar los datos...
                }
                finally
                {
                    dbManager.close(); 
                } 
            }

        }
        
        return pass;    //Retorno de mensaje de estado...
    }

    public boolean addCategoriaB(DTOCategoria categoriaIngresada) //Sin uso (Ingreso de datos opción B (7) sólo para RAM) Ingreso de subcategoría en subcategorías.
    { 
        boolean pass = false;
        
        dbManager = PersistenciaManager.getEntityManager();
        EntityTransaction transaccionActual = dbManager.getTransaction();

        transaccionActual.begin(); //Se inicia el token de db
        
        Categoria nodoPadre = dbManager.find(Categoria.class, categoriaIngresada.getCatPadre());    //Se busca y almacena nodo padre en db
        
        if(nodoPadre != null) //Si no existe tal categoría padre que se ingresa...
        {
            categoriaIngresada.setNodoPadre(nodoPadre);         //Lo mismo de antes, se asigna el nodo padre.
            nodoPadre.addDTOSubcategoria(categoriaIngresada);   //Actualizado.
            
                try
                {
                    dbManager.merge(nodoPadre);              //Se sobreescribe al que estaba antes.
                    transaccionActual.commit();         //Se cierra token de db alv
                    
                    if(nodoPadre.getSubcategorias() != null)
                    {
                        System.out.print("\nSe almacenaron datos de subcategoría " + categoriaIngresada.getNombreCategoria() + " en la categoría " + nodoPadre.getNombreCategoria() + " en la base de datos\n");  //Esto es para corroborar en consola
                    }
                    
                    pass = true;    //Aviso en bool que fué todo asignado correctamente o a la espera de posible exception db.
                }
                catch(Exception ouNao)
                {
                    if(transaccionActual.isActive())    //Si no hubo desconexión inesperada...
                    {
                        System.out.print("\nError, no se alacenaron datos de subcategoría " + nodoPadre.getNombreCategoria() + " en la base de datos\n");  //Esto es para corroborar en consola
                        transaccionActual.rollback();   //Se cancela la transacción y se deshace cualquier cambio.
                    }

                    pass = false;   //Almaceno en bool que hubo un error inesperado al ingresar los datos...
                }
                finally
                {
                    dbManager.close(); 
                } 
                        
        }
        
        return pass;
    }

    public Categoria buscadorC(String nombreCat) {
        EntityManager em = PersistenciaManager.getEntityManager();
        try {
            return em.find(Categoria.class, nombreCat);
        } finally {
            em.close();
        }
    }
    
    public void cargarCategorias() {
        EntityManager em = PersistenciaManager.getEntityManager();
        EntityTransaction t = em.getTransaction();
        try {
            t.begin();
            // Nivel raíz
            Categoria catTeatro = em.find(Categoria.class, "Teatro");
            if (catTeatro == null) {
                catTeatro = new Categoria("Teatro");
                em.persist(catTeatro);
            }
            Categoria catLiteratura = em.find(Categoria.class, "Literatura");
            if (catLiteratura == null) {
                catLiteratura = new Categoria("Literatura");
                em.persist(catLiteratura);
            }

            Categoria catMusica = em.find(Categoria.class, "Música");
            if (catMusica == null) {
                catMusica = new Categoria("Música");
                em.persist(catMusica);
            }

            Categoria catCine = em.find(Categoria.class, "Cine");
            if (catCine == null) {
                catCine = new Categoria("Cine");
                em.persist(catCine);
            }

            Categoria catDanza = em.find(Categoria.class, "Danza");
            if (catDanza == null) {
                catDanza = new Categoria("Danza");
                em.persist(catDanza);
            }

            Categoria catCarnaval = em.find(Categoria.class, "Carnaval");
            if (catCarnaval == null) {
                catCarnaval = new Categoria("Carnaval");
                em.persist(catCarnaval);
            }

            // Subcategorías de Teatro
            Categoria catTeatroDramatico = em.find(Categoria.class, "Teatro Dramático");
            if (catTeatroDramatico == null) {
                catTeatroDramatico = new Categoria("Teatro Dramático", catTeatro);
                em.persist(catTeatroDramatico);
            }

            Categoria catTeatroMusical = em.find(Categoria.class, "Teatro Musical");
            if (catTeatroMusical == null) {
                catTeatroMusical = new Categoria("Teatro Musical", catTeatro);
                em.persist(catTeatroMusical);
            }

            Categoria catComedia = em.find(Categoria.class, "Comedia");
            if (catComedia == null) {
                catComedia = new Categoria("Comedia", catTeatro);
                em.persist(catComedia);
            }

            Categoria catStandUp = em.find(Categoria.class, "Stand-up");
            if (catStandUp == null) {
                catStandUp = new Categoria("Stand-up", catComedia);
                em.persist(catStandUp);
            }

            // Subcategorías de Música
            Categoria catFestival = em.find(Categoria.class, "Festival");
            if (catFestival == null) {
                catFestival = new Categoria("Festival", catMusica);
                em.persist(catFestival);
            }

            Categoria catConcierto = em.find(Categoria.class, "Concierto");
            if (catConcierto == null) {
                catConcierto = new Categoria("Concierto", catMusica);
                em.persist(catConcierto);
            }

            // Subcategorías de Cine
            Categoria catCineAireLibre = em.find(Categoria.class, "Cine al Aire Libre");
            if (catCineAireLibre == null) {
                catCineAireLibre = new Categoria("Cine al Aire Libre", catCine);
                em.persist(catCineAireLibre);
            }

            Categoria catCinePedal = em.find(Categoria.class, "Cine a Pedal");
            if (catCinePedal == null) {
                catCinePedal = new Categoria("Cine a Pedal", catCine);
                em.persist(catCinePedal);
            }

            // Subcategorías de Danza
            Categoria catBallet = em.find(Categoria.class, "Ballet");
            if (catBallet == null) {
                catBallet = new Categoria("Ballet", catDanza);
                em.persist(catBallet);
            }

            Categoria catFlamenco = em.find(Categoria.class, "Flamenco");
            if (catFlamenco == null) {
                catFlamenco = new Categoria("Flamenco", catDanza);
                em.persist(catFlamenco);
            }

            // Subcategorías de Carnaval
            Categoria catMurga = em.find(Categoria.class, "Murga");
            if (catMurga == null) {
                catMurga = new Categoria("Murga", catCarnaval);
                em.persist(catMurga);
            }

            Categoria catHumoristas = em.find(Categoria.class, "Humoristas");
            if (catHumoristas == null) {
                catHumoristas = new Categoria("Humoristas", catCarnaval);
                em.persist(catHumoristas);
            }

            Categoria catParodistas = em.find(Categoria.class, "Parodistas");
            if (catParodistas == null) {
                catParodistas = new Categoria("Parodistas", catCarnaval);
                em.persist(catParodistas);
            }

            Categoria catLubolos = em.find(Categoria.class, "Lubolos");
            if (catLubolos == null) {
                catLubolos = new Categoria("Lubolos", catCarnaval);
                em.persist(catLubolos);
            }

            Categoria catRevista = em.find(Categoria.class, "Revista");
            if (catRevista == null) {
                catRevista = new Categoria("Revista", catCarnaval);
                em.persist(catRevista);
            }

            t.commit();
        } catch (Exception e) {
            if (t.isActive()) {
                t.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }


}
