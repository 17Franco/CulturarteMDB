/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package ui;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import logica.Fabrica;
import logica.IController;


public class SeguirUsuario extends javax.swing.JInternalFrame {
    private IController controller = Fabrica.getInstance().getController();
    
    
    public void cargarComboBox(){
         // filtrar cambios válidos
            String item = (String) jcbUsuario1.getSelectedItem();
            if(item != null && controller.existe(item)){
                jcbUsuarioASeguir.removeAllItems(); 
                jcbUsuarioASeguir.addItem("SeleccionarUsuario"); 
                //1 dependiendo del cual selecciono me traigo sus seguidos
                List<String> usuariosSeguidos = new ArrayList<>();
                usuariosSeguidos=controller.ListaSeguidosPorUsuario(item);
                //2 en jcbUsuarioASeguido debo mostrar usuarios no seguidos y tampoco el seleccionado en jcbUsuarioASeguir
                for(String u: controller.ListaUsuarios()){
                    if(!u.equals(item) && !usuariosSeguidos.contains(u)){
                        jcbUsuarioASeguir.addItem(u);
                    }
                }
                //habilito jcbUsuarioASeguir 
                jcbUsuarioASeguir.setEnabled(true);
                // verificar datos cuando doy al btnSeguir 
            }
    }
    public SeguirUsuario() {
        initComponents();
        jcbUsuario1.removeAllItems(); 
        jcbUsuarioASeguir.removeAllItems(); 
        
         jcbUsuario1.addItem("SeleccionarUsuario"); 
         jcbUsuarioASeguir.addItem("SeleccionarUsuario"); 
        for (String u : controller.ListaUsuarios()) {
            jcbUsuario1.addItem(u); 
        }
        
        
        jcbUsuario1.addActionListener(e -> {
           cargarComboBox();
        });
    }

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jcbUsuarioASeguir = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jcbUsuario1 = new javax.swing.JComboBox<>();
        btnSeguir = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Seguir Usuario");

        jLabel1.setText("Usuario");

        jcbUsuarioASeguir.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jcbUsuarioASeguir.setEnabled(false);

        jLabel2.setText("Seguir A");

        jcbUsuario1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnSeguir.setText("Seguir");
        btnSeguir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeguirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jcbUsuarioASeguir, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2)
                    .addComponent(jcbUsuario1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1)
                    .addComponent(btnSeguir, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(63, 63, 63))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel1)
                .addGap(16, 16, 16)
                .addComponent(jcbUsuario1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(jLabel2)
                .addGap(26, 26, 26)
                .addComponent(jcbUsuarioASeguir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 201, Short.MAX_VALUE)
                .addComponent(btnSeguir, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(94, 94, 94))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSeguirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeguirActionPerformed

        String usuario=(String) jcbUsuario1.getSelectedItem();
        String aSeguir=(String) jcbUsuarioASeguir.getSelectedItem();
        if (!"SeleccionarUsuario".equals(usuario) && !"SeleccionarUsuario".equals(aSeguir)) {
            
           
            
            boolean exito = controller.seguir((String)jcbUsuario1.getSelectedItem(), (String)jcbUsuarioASeguir.getSelectedItem());
            if (exito) {
                JOptionPane.showMessageDialog(this, usuario + " ahora sigue a " + aSeguir);
                cargarComboBox();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo realizar la acción");
            }
        }else{
            JOptionPane.showMessageDialog(this, "Por favor, selecciona un usuario");
        }

    }//GEN-LAST:event_btnSeguirActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSeguir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JComboBox<String> jcbUsuario1;
    private javax.swing.JComboBox<String> jcbUsuarioASeguir;
    // End of variables declaration//GEN-END:variables
}
