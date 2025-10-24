package ui;
import javax.swing.JOptionPane;

import java.beans.PropertyVetoException;
import javax.swing.JInternalFrame;
import javax.swing.JMenuItem;
import logica.Fabrica;
import logica.IController;

public class Main extends javax.swing.JFrame {
    private IController controller = Fabrica.getInstance().getController();
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Main.class.getName());

   
    public Main() {
        initComponents();
        jMenu2.setVisible(false);
        setLocationRelativeTo(null);
        
        
        // Detectar cuando la ventana se cierra (no se donde se cerraria la ia me mando aca xd)
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                System.out.println("Cerrando Conexiones");
                controller.cerrarAplicacion();  // Llamamos al método de cierre de la conexión MongoDB
                System.exit(0);//no preguntar porque esto hace que cierre todas la conexiones
            }
        });
        
        
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            controller.cerrarAplicacion();
           
        }));
    }
       
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem4 = new javax.swing.JMenuItem();
        fondo = new javax.swing.JDesktopPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        CargarDatos = new javax.swing.JMenu();

        jMenuItem4.setText("jMenuItem4");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Culturarte");

        javax.swing.GroupLayout fondoLayout = new javax.swing.GroupLayout(fondo);
        fondo.setLayout(fondoLayout);
        fondoLayout.setHorizontalGroup(
            fondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 490, Short.MAX_VALUE)
        );
        fondoLayout.setVerticalGroup(
            fondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 558, Short.MAX_VALUE)
        );

        jMenu1.setText("Sistema");

        jMenuItem1.setText("Usuario");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("Propuesta");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem3.setText("Categoria");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuItem5.setText("Colaboracion");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem5);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        CargarDatos.setText("Cargar Datos");
        CargarDatos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CargarDatosMouseClicked(evt);
            }
        });
        CargarDatos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CargarDatosActionPerformed(evt);
            }
        });
        jMenuBar1.add(CargarDatos);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(fondo)
                .addContainerGap(21, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(fondo)
                .addContainerGap(33, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


        private void abrirInternalFrame(Class<? extends JInternalFrame> claseFrame) {
        // Buscar si ya existe una instancia abierta
            for (JInternalFrame frame : fondo.getAllFrames()) {//recorre cada internalframe abierto
                if (claseFrame.isInstance(frame)) { // pregunta si es el que quiero crear 
                    try {
                        frame.setIcon(false);  //si entra y esta minimizado lo maximiza 
                        frame.setSelected(true);// y lo selecciona ("le da el foco")
                    } catch (PropertyVetoException ex) { //captura un error 
                        ex.printStackTrace();
                    }
                    frame.toFront();//si esta atras de otros internal lo pasa para el rente 
                    return;
                }
            }

            // Si no existe crea el internal frame que le pasamos por parametro
            try {
                JInternalFrame ventana = claseFrame.getDeclaredConstructor().newInstance();
                fondo.add(ventana); // lo anade al desktop pane
                ventana.setSize(fondo.getSize()); // le da el tamano del desktop pane
                ventana.setVisible(true); 
            } catch (Exception e) { // captura error en caso de que no pueda crearlo 
                e.printStackTrace();
            }
         }

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        
        jMenu2.setText("Categoria");
        jMenu2.removeAll();
        JMenuItem altaCategoriaItem = new JMenuItem("Alta Categoria");

        altaCategoriaItem.addActionListener(e ->{
            //AltaDeCategoria altaCat = new AltaDeCategoria();
            abrirInternalFrame(AltaDeCategoria.class);
            //fondo.add(altaCat);
            //altaCat.setSize(fondo.getSize());
            //altaCat.setVisible(true);
        });

        jMenu2.add(altaCategoriaItem);
        jMenu2.setVisible(true);
    
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        //AltaUsuario Alta=new AltaUsuario();
        jMenu2.setText("Usuario");
        jMenu2.removeAll();


        String[] opcionesUsuario = { "Alta Usuario", "Consulta Proponente", "Consulta Colaborador","Seguir Usuario","Dejar de seguir usuario"};

        for (String op : opcionesUsuario) {
            JMenuItem item = new JMenuItem(op);

             item.addActionListener(e -> {

                switch (op) {
                    case "Alta Usuario" -> 
                    {
                        abrirInternalFrame(AltaUsuario.class);
                       // fondo.add(Alta);
                        //Alta.setSize(fondo.getSize());
                        //Alta.setVisible(true);
                    }
                    case "Consulta Proponente" ->
                    {
                         abrirInternalFrame(ConsultaProponente.class);
                       // fondo.add(ConsultaP);
                        //ConsultaP.setSize(fondo.getSize());
                        //ConsultaP.setVisible(true);
                    }
                    case "Consulta Colaborador" ->
                    {
                       abrirInternalFrame(ConsultaColaborador.class);
                        //fondo.add(ConsultaC);
                        //ConsultaC.setSize(fondo.getSize());
                        //ConsultaC.setVisible(true);
                    }  
                    case "Seguir Usuario" ->
                    {
                        abrirInternalFrame(SeguirUsuario.class);
                        //fondo.add(SeguirU);
                        //SeguirU.setSize(fondo.getSize());
                        //SeguirU.setVisible(true);
                    }
                    case "Dejar de seguir usuario" ->
                    {
                        abrirInternalFrame(DejarDeSeguirUsuario.class);
                        //fondo.add(SeguirU);
                        //SeguirU.setSize(fondo.getSize());
                        //SeguirU.setVisible(true);
                    }  
                }
            });
             jMenu2.add(item);
    }

          
        jMenu2.setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
            
        jMenu2.setText("Propuesta");
        jMenu2.removeAll();
            
            String[] opcionesPropuesta = { "Alta Propuesta", "Modificar Propuesta","Listar Propuesta", "Consultar Propuestas Por Estado","Evaluar Propuesta" };
            
            for (String sel : opcionesPropuesta){
                JMenuItem menuPropuesta = new JMenuItem(sel);
                
                menuPropuesta.addActionListener (e -> 
                    {
                        switch (sel) 
                        {
                            case "Alta Propuesta" -> 
                            {
                               // AltaPropuesta PropNew = new AltaPropuesta(); 
                                abrirInternalFrame(AltaPropuesta.class);
                               // fondo.add(PropNew);
                                //PropNew.setSize(fondo.getSize());
                                //PropNew.setVisible(true);
                            }
                            case "Modificar Propuesta" -> 
                            {
                               // ModificarDatosPropuesta MProp = new ModificarDatosPropuesta(); 
                                abrirInternalFrame(ModificarDatosPropuesta.class);
                                //fondo.add(MProp);
                                //MProp.setSize(fondo.getSize());
                                //MProp.setVisible(true);
                            }
                            case "Listar Propuesta" -> 
                            {
                               //ListaPropuesta PropNew = new ListaPropuesta(); 
                                abrirInternalFrame(ListaPropuesta.class);
                                //fondo.add(PropNew);
                                //PropNew.setSize(fondo.getSize());
                               // PropNew.setVisible(true);
                            }
                            case "Consultar Propuestas Por Estado" -> 
                            {                           
                               // ConsultaPropuestaPorEstado consultaPorEstado = new ConsultaPropuestaPorEstado();
                                abrirInternalFrame(ConsultaPropuestaPorEstado.class);
                               // fondo.add(consultaPorEstado);
                                //consultaPorEstado.setSize(fondo.getSize());
                                //consultaPorEstado.setVisible(true);
                            }
                            case "Evaluar Propuesta" -> {
                                // ConsultaPropuestaPorEstado consultaPorEstado = new ConsultaPropuestaPorEstado();
                                abrirInternalFrame(EvaluarPropuesta.class);
                                // fondo.add(consultaPorEstado);
                                //consultaPorEstado.setSize(fondo.getSize());
                                //consultaPorEstado.setVisible(true);
                            }
                        }
                    }
                );
                
                jMenu2.add(menuPropuesta);
            }
            
            jMenu2.setVisible(true);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
    
    jMenu2.setText("Colaboracion");
    jMenu2.removeAll();
    String[] opcionesColaboracion = { "Colaborar a Propuesta", "Consultar Colaboracion", "Cancelar Colaboracion" };


    for (String op : opcionesColaboracion) {
        JMenuItem menuItem = new JMenuItem(op);

           menuItem.addActionListener(e -> {
            switch (op) {
                case "Colaborar a Propuesta" -> {
                    //AltaColaboracion frame = new AltaColaboracion();
                    abrirInternalFrame(AltaColaboracion.class);
                    //fondo.add(frame);
                   // frame.setSize(fondo.getSize());
                    //frame.setVisible(true);
                    break;
                }
                case "Consultar Colaboracion" -> {
                        abrirInternalFrame(ConsultaColaboracion.class);
                    // ConsultarPropuesta frame = new ConsultarPropuesta();
                    // fondo.add(frame); frame.setSize(fondo.getSize()); frame.setVisible(true);
                     break;
                }
                case "Cancelar Colaboracion" -> {
                    // Aquí iría tu lógica para cancelar colaboración
                      abrirInternalFrame(CancelarColaboracion.class);
                    //JOptionPane.showMessageDialog(this, "Función Cancelar Colaboración aún no implementada");
                     break;
                }
            }
        });

        jMenu2.add(menuItem);
    }//GEN-LAST:event_jMenuItem5ActionPerformed
      jMenu2.setVisible(true);

}
    
    public static void main(String args[]) {
    
        java.awt.EventQueue.invokeLater(() -> new Main().setVisible(true));
    }

    private void CargarDatosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CargarDatosActionPerformed
       
    }//GEN-LAST:event_CargarDatosActionPerformed

    private void CargarDatosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CargarDatosMouseClicked
       controller.cargarDatosPruebaProponente();
       controller.cargarDatosPruebaColaborador();
       controller.cargarSeguidos();
       controller.cargarCategorias();
       controller.cargarPropuesta();
       controller.cargarColaboraciones();
       JOptionPane.showMessageDialog(this, "Se han cargado los datos a la bd.", "Información", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_CargarDatosMouseClicked
    
    
   
    
    

    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu CargarDatos;
    private javax.swing.JDesktopPane fondo;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    // End of variables declaration//GEN-END:variables
}
