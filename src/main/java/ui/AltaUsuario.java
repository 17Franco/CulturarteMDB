/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package ui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import javax.swing.BoxLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import logica.DTO.DTOColaborador;
import logica.DTO.DTOProponente;
import logica.Fabrica;
import logica.IController;


public class AltaUsuario extends javax.swing.JInternalFrame {
    private IController controller = Fabrica.getInstance().getController();
    private String rutaImagenTemp = null; 
    private String direccion;
    private String biografia;
    private String web;
    
    private JPanel optionPanel = new JPanel();
    
   
    private JLabel lblDireccion = new JLabel("Dirección:");
    private JTextField txtDireccion = new JTextField(15);
    private JLabel lblBiografia = new JLabel("Biografía:");
    private JTextArea txtBiografia = new JTextArea(5,15); 
    private JLabel lblWeb = new JLabel("Website:");
    private JTextField txtWeb = new JTextField(15);
    
    
    
    public AltaUsuario() {
        initComponents();
        optionPanel.setLayout(new BoxLayout(optionPanel, BoxLayout.Y_AXIS));
        
       // optionPanel.setSize(50,100);
        txtDireccion.setName("Direccion");
        txtBiografia.setName("Biografia");
        JScrollPane scrollPane = new JScrollPane(txtBiografia);
        txtWeb.setName("WebSite");
        
        JPanel fieldsPanel1= new JPanel();
        fieldsPanel1.add(lblDireccion);
        fieldsPanel1.add(txtDireccion);
       
        JPanel fieldsPanel3= new JPanel();
        fieldsPanel3.add(lblWeb);
        fieldsPanel3.add(txtWeb);
        
        optionPanel.add(fieldsPanel1);
        optionPanel.add(fieldsPanel3);
        
        JPanel fieldsPanel2 = new JPanel();
        txtBiografia.setLineWrap(true); //salta automatiucamente cuando llege al final horizontalmente
        txtBiografia.setWrapStyleWord(true);//no corta una palabra por la mitad
        fieldsPanel2.add(lblBiografia);
        fieldsPanel2.add(scrollPane);
        optionPanel.add(fieldsPanel2);
        
        
        
    }

  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jDialog1 = new javax.swing.JDialog();
        jDialog2 = new javax.swing.JDialog();
        txtNick = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtApellido = new javax.swing.JTextField();
        colaborador = new javax.swing.JRadioButton();
        proponente = new javax.swing.JRadioButton();
        btnFile = new javax.swing.JButton();
        aceptar = new javax.swing.JButton();
        cancelar = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtDia = new javax.swing.JTextField();
        txtMes = new javax.swing.JTextField();
        txtAnio = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jDialog2Layout = new javax.swing.GroupLayout(jDialog2.getContentPane());
        jDialog2.getContentPane().setLayout(jDialog2Layout);
        jDialog2Layout.setHorizontalGroup(
            jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jDialog2Layout.setVerticalGroup(
            jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Registrar Usuario");
        setName("Registrear Usuario"); // NOI18N
        setPreferredSize(new java.awt.Dimension(542, 622));

        txtNick.setName("NickName"); // NOI18N

        txtNombre.setName("Nombre"); // NOI18N

        jLabel1.setText("Nickname");

        jLabel2.setText("Nombre");

        jLabel3.setText("Apellido");

        jLabel4.setText("Email");

        txtApellido.setName("Apellido"); // NOI18N

        buttonGroup1.add(colaborador);
        colaborador.setSelected(true);
        colaborador.setText("Colaborador");

        buttonGroup1.add(proponente);
        proponente.setText("Proponenete");
        proponente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                proponenteActionPerformed(evt);
            }
        });

        btnFile.setText("File");
        btnFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFileActionPerformed(evt);
            }
        });

        aceptar.setText("Aceptar");
        aceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aceptarActionPerformed(evt);
            }
        });

        cancelar.setText("Cancelar");
        cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelarActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Liberation Sans", 1, 18)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Ingrese los siguientes datos");

        jLabel6.setText("Fecha Nacimiento");

        txtDia.setName("Dia"); // NOI18N

        txtMes.setName("Mes"); // NOI18N

        txtAnio.setName("Anio"); // NOI18N

        jLabel7.setText("Anio");

        jLabel8.setText("Mes");

        jLabel9.setText("Dia");

        txtEmail.setName("Email"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(75, 75, 75)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(70, 70, 70))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDia, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMes, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(txtAnio, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(41, 41, 41))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(6, 6, 6)
                        .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(6, 6, 6)
                        .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(6, 6, 6)
                        .addComponent(txtNick, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(aceptar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtEmail, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(colaborador)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(proponente)))
                            .addComponent(btnFile, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(138, Short.MAX_VALUE))
            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel5)
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNick, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel7)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAnio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(proponente)
                    .addComponent(colaborador))
                .addGap(18, 18, 18)
                .addComponent(btnFile, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(aceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addComponent(cancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(74, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFileActionPerformed
         rutaImagenTemp=Utilities.elejirArchivo();
    }//GEN-LAST:event_btnFileActionPerformed


    private boolean validarEmail(JTextField campo, String texto) {
        if (!texto.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")) {
            JOptionPane.showMessageDialog(this,
                "Email inválido en el campo: " + campo.getName(),
                "Formato incorrecto",
                JOptionPane.WARNING_MESSAGE);
            campo.requestFocus();
            return false;
        }
        return true;
    }

    private boolean validarWeb(JTextField campo, String texto) {
        if (!texto.matches("^(https?://)?([\\w.-]+)\\.([a-zA-Z]{2,6})([/\\w .-]*)*/?$")) {
            JOptionPane.showMessageDialog(this,
                "URL inválida en el campo: " + campo.getName(),
                "Formato incorrecto",
                JOptionPane.WARNING_MESSAGE);
            campo.requestFocus();
            return false;
        }
        return true;
    }


    private boolean validarCampos(List<JTextField> campos) {
        for (JTextField campo : campos) {
            String texto = campo.getText().trim();
            if (!Utilities.validarNoVacio(campo)) return false;
            
            if (campo == txtEmail && !validarEmail(campo, texto)) return false;
            
            if (campo == txtWeb && !validarWeb(campo, texto)) return false;
        }


        if (!Utilities.validarFecha(txtDia.getText(), txtMes.getText(), txtAnio.getText())) {
            return false;
        }

        return true; 
    }
    private void limpiarCampos(){
         txtNick.setText("");
         txtNombre.setText("");
         txtApellido.setText("");
         txtEmail.setText("");
         txtDia.setText("");
         txtMes.setText("");
         txtAnio.setText("");
         txtDireccion.setText("");
         txtBiografia.setText("");
         txtWeb.setText("");
    
    }
      public static boolean validarTextArea(JTextArea campo) { 
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
    
    private void aceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aceptarActionPerformed
         String nickName = txtNick.getText();  
         String nom = txtNombre.getText();
         String apelli = txtApellido.getText();
         String email = txtEmail.getText();
         String dia = txtDia.getText();
         String mes =txtMes.getText();
         String anio =txtAnio.getText();
         //System.out.println(jFormattedTextField1.getText().formatted("dd/MM/YYYY"));
         JTextField validarBiografia=new JTextField();
         validarBiografia.setName("Biografia");
         validarBiografia.setText(txtBiografia.getText());
         
         
        List<JTextField> campos = proponente.isSelected()
        ? Arrays.asList(txtNick, txtNombre, txtApellido, txtEmail, txtDia, txtMes, txtAnio, txtDireccion, validarBiografia, txtWeb)
        : Arrays.asList(txtNick, txtNombre, txtApellido, txtEmail, txtDia, txtMes, txtAnio);
         
        if(validarCampos(campos)){
            if(controller.existeUsuario(nickName, email)){
                JOptionPane.showMessageDialog(this, "Usuario ya registrado");
            }else{
                    rutaImagenTemp=Utilities.copiarImagen(rutaImagenTemp,nickName);
                    LocalDate f=LocalDate.of(Integer.parseInt(anio),Integer.parseInt(mes),Integer.parseInt(dia));
                    if(proponente.isSelected()){
                        DTOProponente p=new DTOProponente(direccion,biografia,web,nickName,nom,apelli,email,f,rutaImagenTemp);
                        controller.altaUsuario(p);
                    }else{
                         DTOColaborador c=new DTOColaborador(nickName,nom,apelli,email,f,rutaImagenTemp);
                        controller.altaUsuario(c);  
                    }   
                    JOptionPane.showMessageDialog(this, "Usuario registrado con exito");
                    limpiarCampos();
            }
            
        }
    }//GEN-LAST:event_aceptarActionPerformed
                                           

    private void cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelarActionPerformed
        limpiarCampos();
         
    }//GEN-LAST:event_cancelarActionPerformed

    private void proponenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_proponenteActionPerformed
        optionPanel.setVisible(true);
        Object[] options={"Aceptar"};
         int result = JOptionPane.showOptionDialog( this,optionPanel,"Datos de Proponente",JOptionPane.DEFAULT_OPTION,JOptionPane.PLAIN_MESSAGE,null,options, options[0]);

        if (result == JOptionPane.OK_OPTION) {
             direccion = txtDireccion.getText();
             biografia = txtBiografia.getText();
             //System.out.println(biografia);
             web = txtWeb.getText();
        }
    }//GEN-LAST:event_proponenteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton aceptar;
    private javax.swing.JButton btnFile;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton cancelar;
    private javax.swing.JRadioButton colaborador;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JDialog jDialog2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JRadioButton proponente;
    private javax.swing.JTextField txtAnio;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtDia;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtMes;
    private javax.swing.JTextField txtNick;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
