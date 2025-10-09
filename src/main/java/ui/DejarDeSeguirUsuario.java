package ui;
import java.util.List;
import javax.swing.JOptionPane;
import logica.Fabrica;
import logica.IController;


public class DejarDeSeguirUsuario extends javax.swing.JInternalFrame {
    private IController controller = Fabrica.getInstance().getController();
    boolean usrSeleccionado0 = false;

    public void cargarComboBoxUser()
    {
        JCBUsuarioActual.removeAllItems();
      JCBUsuarioActual.addItem("SeleccionarUsuario"); 
       for (String u : controller.ListaUsuarios()) 
        {
           JCBUsuarioActual.addItem(u);
        }
    }
    
    public DejarDeSeguirUsuario() 
    {
        initComponents();
        JCBUsuario_toUnfollow.setEnabled(false);
        buttonDejarDeSeguir.setVisible(false);

        
        if(!controller.ListaUsuarios().isEmpty())
        {
         cargarComboBoxUser();
        }
        else
        {
            JOptionPane.showMessageDialog(this, "No existen usuarios en el sistema!");
            this.dispose(); //Si no hay usuarios, se cancela el caso de uso alv.
        }
        
    }
    
    
        
    public void cargarComboBoxSeguidos() 
    {
       
        String input = (String) JCBUsuarioActual.getSelectedItem();
        JCBUsuario_toUnfollow.removeAllItems();
        JCBUsuario_toUnfollow.addItem("Dejar de seguir a...");

        List<String> seguidos = controller.ListaSeguidosPorUsuario(input);

        for (String ct : controller.ListaUsuarios()) 
        {
            if (!ct.equals(input) && seguidos.contains(ct)) 
            {
                JCBUsuario_toUnfollow.addItem(ct);
            }
        }

        if(JCBUsuario_toUnfollow.getItemCount() > 1) //Si existen usuarios seguidos para eliminar
        {
            JCBUsuario_toUnfollow.setEnabled(true);
        } 
        else //Si no existen usuarios seguidos
        {
            JCBUsuario_toUnfollow.setEnabled(false);
            JCBUsuario_toUnfollow.revalidate();
            buttonDejarDeSeguir.setVisible(false);
            buttonDejarDeSeguir.revalidate();
        }
        
    }

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        JCBUsuario_toUnfollow = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        JCBUsuarioActual = new javax.swing.JComboBox<>();
        buttonDejarDeSeguir = new javax.swing.JButton();
        botonCancelar = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Dejar de seguir Usuario");

        jLabel1.setText("Tu cuenta de usuario");

        JCBUsuario_toUnfollow.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        JCBUsuario_toUnfollow.setEnabled(false);
        JCBUsuario_toUnfollow.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                JCBUsuario_toUnfollowItemStateChanged(evt);
            }
        });
        JCBUsuario_toUnfollow.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JCBUsuario_toUnfollowMouseClicked(evt);
            }
        });
        JCBUsuario_toUnfollow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JCBUsuario_toUnfollowActionPerformed(evt);
            }
        });

        jLabel2.setText("Desea dejar de seguir a...");

        JCBUsuarioActual.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        JCBUsuarioActual.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                JCBUsuarioActualItemStateChanged(evt);
            }
        });
        JCBUsuarioActual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JCBUsuarioActualActionPerformed(evt);
            }
        });

        buttonDejarDeSeguir.setText("Dejar de seguir");
        buttonDejarDeSeguir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonDejarDeSeguirActionPerformed(evt);
            }
        });

        botonCancelar.setText("Cancelar");
        botonCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(JCBUsuario_toUnfollow, 0, 353, Short.MAX_VALUE)
                    .addComponent(jLabel2)
                    .addComponent(JCBUsuarioActual, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1)
                    .addComponent(buttonDejarDeSeguir)
                    .addComponent(botonCancelar))
                .addGap(63, 63, 63))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel1)
                .addGap(16, 16, 16)
                .addComponent(JCBUsuarioActual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(jLabel2)
                .addGap(26, 26, 26)
                .addComponent(JCBUsuario_toUnfollow, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 74, Short.MAX_VALUE)
                .addComponent(buttonDejarDeSeguir, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(botonCancelar)
                .addGap(40, 40, 40))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonDejarDeSeguirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonDejarDeSeguirActionPerformed

        String usuarioActual = (String) JCBUsuarioActual.getSelectedItem();
        String UsrADejarDeSeguir = (String) JCBUsuario_toUnfollow.getSelectedItem();
        
        if (!"Su usuario...".equals(usuarioActual) && !"Dejar de seguir a...".equals(UsrADejarDeSeguir)) 
        {         
            if(controller.unFollowUser((String) JCBUsuarioActual.getSelectedItem(), (String) JCBUsuario_toUnfollow.getSelectedItem())) 
            {
                JOptionPane.showMessageDialog(this,"'" + usuarioActual + "'" + " ya no sigue al usuario " + "'" + UsrADejarDeSeguir + "'");
                cargarComboBoxSeguidos();
                buttonDejarDeSeguir.setVisible(false);
            } 
            else 
            {
                JOptionPane.showMessageDialog(this, "Error inesperado");
            }
        }
        
        if("Dejar de seguir a...".equals(UsrADejarDeSeguir))
        {
            JOptionPane.showMessageDialog(this, "Seleccione el usuario que desea dejar de seguir...");
        }

    }//GEN-LAST:event_buttonDejarDeSeguirActionPerformed

    
    

    private void JCBUsuario_toUnfollowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JCBUsuario_toUnfollowActionPerformed

    }//GEN-LAST:event_JCBUsuario_toUnfollowActionPerformed

    private void JCBUsuario_toUnfollowMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JCBUsuario_toUnfollowMouseClicked


    }//GEN-LAST:event_JCBUsuario_toUnfollowMouseClicked

    private void JCBUsuario_toUnfollowItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_JCBUsuario_toUnfollowItemStateChanged

        if(usrSeleccionado0 == true)
        {
            buttonDejarDeSeguir.setVisible(true);
            buttonDejarDeSeguir.revalidate();
        }
    }//GEN-LAST:event_JCBUsuario_toUnfollowItemStateChanged

    private void botonCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_botonCancelarActionPerformed

    private void JCBUsuarioActualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JCBUsuarioActualActionPerformed

    }//GEN-LAST:event_JCBUsuarioActualActionPerformed

    private void JCBUsuarioActualItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_JCBUsuarioActualItemStateChanged

        cargarComboBoxSeguidos();   //Carga el comboBox de los usuarios a dejar de seguir.
        usrSeleccionado0 = true;    //Para lograr que el botón aparezca.
        buttonDejarDeSeguir.setVisible(false);

    }//GEN-LAST:event_JCBUsuarioActualItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> JCBUsuarioActual;
    private javax.swing.JComboBox<String> JCBUsuario_toUnfollow;
    private javax.swing.JButton botonCancelar;
    private javax.swing.JButton buttonDejarDeSeguir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    // End of variables declaration//GEN-END:variables
}
