
package ui;


import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import logica.DTO.DTOProponente;
import logica.DTO.DTOPropuesta;
import logica.Fabrica;
import logica.IController;


public class ConsultaProponente extends javax.swing.JInternalFrame {
     private IController controller = Fabrica.getInstance().getController();
     List<DTOPropuesta> propuestas=new ArrayList<>();
  
    public ConsultaProponente() {
        initComponents();
        
        Proponentes.removeAllItems(); 
        
        Proponentes.addItem("SeleccionarUsuario"); 
        for (String u : controller.ListaProponentes()) {
            Proponentes.addItem(u); 
        }
        
        
        Proponentes.addActionListener(e -> {
            limpiador();
            // filtrar cambios válidos
            String item = (String) Proponentes.getSelectedItem();
            if(item != null && controller.existe(item)){
                DTOProponente usr = controller.getDTOProponente(item);
                mostrarPerfilProponente(usr);
                // jScrollPane2.setVisible(true);
            }
        });
        
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        Proponentes = new javax.swing.JComboBox<>();
        lblImagen = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtBiografia = new javax.swing.JTextArea();
        txtNombre = new javax.swing.JTextField();
        txtApellido = new javax.swing.JTextField();
        txtNickName = new javax.swing.JTextField();
        txtFecha = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        txtDireccion = new javax.swing.JTextField();
        txtWeb = new javax.swing.JTextField();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Consulta Proponente");
        setPreferredSize(new java.awt.Dimension(490, 558));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Liberation Sans", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Consulta Perfil Proponente");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 6, 480, 20));

        Proponentes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        Proponentes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ProponentesActionPerformed(evt);
            }
        });
        getContentPane().add(Proponentes, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 30, 235, 22));

        lblImagen.setText("No file");
        lblImagen.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lblImagen.setMaximumSize(new java.awt.Dimension(135, 155));
        getContentPane().add(lblImagen, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 72, 140, 170));

        jButton1.setText("Ver Propuestas");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 490, -1, 22));

        jLabel2.setText("NickName");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 70, 75, -1));

        jLabel3.setText("Nombre");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 110, 75, -1));

        jLabel4.setText("Apellido");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 150, 75, -1));

        jLabel5.setText("Fecha");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 190, 75, -1));

        jLabel6.setText("Email");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 230, 75, -1));

        jLabel7.setText("Direccion");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 270, 75, -1));

        jLabel8.setText("Web");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 310, 75, 20));

        jLabel9.setText("Biografia");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 350, 75, 20));

        txtBiografia.setColumns(20);
        txtBiografia.setLineWrap(true);
        txtBiografia.setRows(5);
        txtBiografia.setWrapStyleWord(true);
        txtBiografia.setBorder(null);
        jScrollPane2.setViewportView(txtBiografia);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 380, 293, 94));

        txtNombre.setBorder(null);
        getContentPane().add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 110, 200, -1));

        txtApellido.setBorder(null);
        getContentPane().add(txtApellido, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 150, 200, -1));

        txtNickName.setBorder(null);
        getContentPane().add(txtNickName, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 70, 200, -1));

        txtFecha.setBorder(null);
        getContentPane().add(txtFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 190, 200, -1));

        txtEmail.setBorder(null);
        getContentPane().add(txtEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 230, 200, -1));

        txtDireccion.setBorder(null);
        getContentPane().add(txtDireccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 270, 200, -1));

        txtWeb.setBorder(null);
        getContentPane().add(txtWeb, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 310, 200, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void limpiador(){
       /* lblNick.setText("");
        lblNombre.setText("");
        lblApellido.setText("");
        lblFecha.setText("");
        lblEmail.setText("");
        lblImagen.setIcon(null);
        lblDireccion.setText("");
        lblBiografia.setText("");
        lblWeb.setText("");
        //DefaultTableModel modelo = (DefaultTableModel) Propuestas.getModel();
       // modelo.setRowCount(0);*/
    }
    
   


    private void mostrarPerfilProponente(DTOProponente usr) {
        
         
        txtNickName.setText(usr.getNickname());
        txtNombre.setText(usr.getNombre());
        txtApellido.setText(usr.getApellido());
        txtFecha.setText(usr.getFecha().getDayOfMonth()+"/"+ usr.getFecha().getMonthValue()+"/"+usr.getFecha().getYear());
        txtEmail.setText(usr.getEmail());
        txtDireccion.setText(usr.getDireccion());
        txtBiografia.setText(usr.getBiografia());
        txtWeb.setText(usr.getWebSite());
      

        
        ImageIcon icon=new ImageIcon(usr.getRutaImg());
        Image img =icon.getImage().getScaledInstance(lblImagen.getWidth(),lblImagen.getHeight(), Image.SCALE_SMOOTH);
        lblImagen.setIcon(new ImageIcon(img));
        
       
         propuestas = new ArrayList<>(controller.getPropuestasCreadasPorProponente(usr.getNickname()));
      
    }


    private void ProponentesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ProponentesActionPerformed
        
       
    }//GEN-LAST:event_ProponentesActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        
        if(propuestas.isEmpty()){
            JOptionPane.showMessageDialog(null, "No hay propuestas para mostrar.", "Información", JOptionPane.INFORMATION_MESSAGE);
        }else{
            PropuestasCreadas mostrar=new PropuestasCreadas();
            mostrar.cargaDeDatos(propuestas);
             JDesktopPane fond = getDesktopPane();
            if (fond != null) {
                fond.add(mostrar);
                mostrar.setSize(fond.getSize());
                mostrar.setVisible(true);
            }
        }
            
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> Proponentes;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblImagen;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextArea txtBiografia;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtFecha;
    private javax.swing.JTextField txtNickName;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtWeb;
    // End of variables declaration//GEN-END:variables
}
