
package ui;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Image;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import logica.DTO.DTOColaboracion;
import logica.DTO.DTOPropuesta;
import logica.Fabrica;
import logica.IController;
import logica.DTO.TipoRetorno;
import static ui.Utilities.validarNoVacio;

/**
 *
 * @author fran
 */
public class AltaColaboracion extends javax.swing.JInternalFrame {

        private IController controller = Fabrica.getInstance().getController();
    
        DTOPropuesta propuestaSeleccionada;
        List<DTOPropuesta> propuestas;
        
        private JDialog dialog;
        private JTextField tituloField;
        private JTextField descField;
        private JTextField lugarField;
        private JTextField fechaField;
        private JTextField precioField;
        private JTextField montoField;
        private JComboBox retornoBox;
        private JLabel imgLabel;

        
    public AltaColaboracion() {
        initComponents();
        cargarDatosAMostrar();
        
          jTable1.getSelectionModel().addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) return; // evita eventos intermedios

                int viewRow = jTable1.getSelectedRow();
                if (viewRow >= 0) {
                    int modelRow = jTable1.convertRowIndexToModel(viewRow); 
                    propuestaSeleccionada = propuestas.get(modelRow);
                    mostrarPopup(propuestaSeleccionada);
                    tipoRetornocbox.removeAllItems(); 
                    tipoRetornocbox.addItem("SeleccionarRetorno"); 
                    Iterator<TipoRetorno> it = propuestaSeleccionada.getRetorno().iterator();
                    while (it.hasNext()) {
                        TipoRetorno retorno = it.next();
                        tipoRetornocbox.addItem(retorno.toString());
                    }
             }
        }); 
         
    }
    
    public void cargarDatosAMostrar(){
        propuestas = new java.util.ArrayList<>(controller.ListarPropuestas("PUBLICADA", "EN_FINANCIACION"));//traigo a las que les puedo invertir
        jTable1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // el adm solo puede seleccionar una sola opcion
                
        String[] cols = {"Titulo", "Nickname"};
        DefaultTableModel model =  new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        for (DTOPropuesta p : propuestas) {
            model.addRow(new Object[]{ p.getTitulo(), p.getProponente().getNickname()});
        }
        jTable1.setModel(model);
        jTable1.setRowSorter(new javax.swing.table.TableRowSorter<>(model));
        jTable1.setVisible(true);
     
        Colaborador.removeAllItems(); 
        
        Colaborador.addItem("SeleccionarUsuario"); 
        for (String u : controller.ListaColaborador()) {
            Colaborador.addItem(u); 
        }
        tipoRetornocbox.removeAllItems(); 
        tipoRetornocbox.addItem("SeleccionarRetorno"); 
    }
    private void mostrarPopup(DTOPropuesta propuesta) {
    if (dialog == null) {
        dialog = new JDialog((Frame) null, "Detalle de Propuesta", true);
        dialog.setSize(350, 450);
        dialog.setLayout(new BorderLayout(10, 10));

        JPanel mainPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Inicializamos campos
        tituloField = createField();
        descField = createField();
        lugarField = createField();
        fechaField = createField();
        precioField = createField();
        montoField = createField();
        retornoBox = createComboBox();
        imgLabel = new JLabel("", JLabel.CENTER);

        // Agregamos etiquetas y campos
        mainPanel.add(new JLabel("Título:"));
        mainPanel.add(tituloField);

        mainPanel.add(new JLabel("Descripción:"));
        mainPanel.add(descField);

        mainPanel.add(new JLabel("Lugar:"));
        mainPanel.add(lugarField);

        mainPanel.add(new JLabel("Fecha:"));
        mainPanel.add(fechaField);

        mainPanel.add(new JLabel("Precio:"));
        mainPanel.add(precioField);

        mainPanel.add(new JLabel("Monto total:"));
        mainPanel.add(montoField);

        mainPanel.add(new JLabel("Retorno:"));
        mainPanel.add(retornoBox);

        // Panel para la imagen
        JPanel imagePanel = new JPanel();
        imagePanel.add(imgLabel);

        dialog.add(mainPanel, BorderLayout.CENTER);
        dialog.add(imagePanel, BorderLayout.SOUTH);
}

        // Actualizamos valores cada vez que se abre
        tituloField.setText(propuesta.getTitulo());
        descField.setText(propuesta.getDescripcion());
        lugarField.setText(propuesta.getLugar());
        fechaField.setText(propuesta.getFecha().toString());
        precioField.setText(String.valueOf(propuesta.getPrecio()));
        montoField.setText(String.valueOf(propuesta.getMontoTotal()));
        retornoBox.removeAllItems();
        for(TipoRetorno t: propuesta.getRetorno()){
            retornoBox.addItem(t);
    }
    //retornoBox.addItem(propuesta.getRetorno() != null ? propuesta.getRetorno().getFirst().toString() : "-");
    //retornoBox.addItem(propuesta.getRetorno() != null ? propuesta.getRetorno().getLast().toString() : "-");

    // Imagen
    if (propuesta.getImagen() != null && !propuesta.getImagen().isEmpty()) {
        ImageIcon icon = new ImageIcon(propuesta.getImagen());
        Image img = icon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
        imgLabel.setIcon(new ImageIcon(img));
        imgLabel.setText("");
    } else {
        imgLabel.setIcon(null);
        imgLabel.setText("Sin imagen");
    }

    dialog.setLocationRelativeTo(null);
    dialog.setVisible(true);
}

    private JTextField createField() {
        JTextField field = new JTextField();
        field.setEditable(false);
        return field;
    }
    private JComboBox createComboBox(){
        JComboBox box=new JComboBox();
           return box;
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Colaborador = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        tipoRetornocbox = new javax.swing.JComboBox<>();
        campoMonto = new javax.swing.JTextField();
        btnCancelar = new javax.swing.JButton();
        BtnConfirmar = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("ColaboracionPropuesta");
        setPreferredSize(new java.awt.Dimension(490, 558));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Colaborador.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        getContentPane().add(Colaborador, new org.netbeans.lib.awtextra.AbsoluteConstraints(239, 211, 156, -1));

        jLabel1.setText("Colaboracion a Propuesta");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(151, 15, -1, -1));

        tipoRetornocbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tipoRetornocboxActionPerformed(evt);
            }
        });
        getContentPane().add(tipoRetornocbox, new org.netbeans.lib.awtextra.AbsoluteConstraints(239, 254, 156, -1));

        campoMonto.setName("Monto"); // NOI18N
        getContentPane().add(campoMonto, new org.netbeans.lib.awtextra.AbsoluteConstraints(239, 296, 156, -1));

        btnCancelar.setText("Cancelar");
        btnCancelar.setToolTipText("");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        getContentPane().add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(284, 426, 111, -1));

        BtnConfirmar.setText("Confirmar");
        BtnConfirmar.setToolTipText("");
        BtnConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnConfirmarActionPerformed(evt);
            }
        });
        getContentPane().add(BtnConfirmar, new org.netbeans.lib.awtextra.AbsoluteConstraints(108, 426, 111, -1));

        jLabel12.setText("Tipo de retorno");
        getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(121, 257, -1, -1));

        jLabel11.setText("Monto");
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 299, -1, -1));

        jLabel13.setText("Colaborador");
        getContentPane().add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(139, 214, -1, -1));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Titulo", "Nickname"
            }
        ));
        jScrollPane3.setViewportView(jTable1);

        getContentPane().add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(75, 39, 322, 160));

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    
        private TipoRetorno tipoRetorno(String descripcion) {
        for (TipoRetorno t : TipoRetorno.values()) {
            if (t.toString().equals(descripcion)) {
                return t;
            }
        }
        return null;
    }

    private void BtnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnConfirmarActionPerformed
       String colaborador=(String) Colaborador.getSelectedItem();
       String retorno=(String)  tipoRetornocbox.getSelectedItem();
        if("SeleccionarUsuario".equals(colaborador) && "SeleccionarRetorno".equals(retorno)){
            JOptionPane.showMessageDialog(this, "Por favor, selecciona un usuario o un retorno valido");
            return;
        }
        if(!validarNoVacio(campoMonto)){
            //JOptionPane.showMessageDialog(this, "Ingrese un monto válido");
            return;
         }
        String titulo = propuestaSeleccionada.getTitulo();

        // 3️⃣ Validar que el colaborador no haya invertido antes
        if (controller.colaboracionExiste(colaborador, titulo)) {
            JOptionPane.showMessageDialog(this, "Este usuario ya colaboró en esta propuesta");
            return;
        }

    // 4️⃣ Validar monto máximo
    int montoAInvertir = Integer.parseInt(campoMonto.getText());
    int montoMax = propuestaSeleccionada.getMontoTotal(); //maximo que recauda la propuesta
    int montoActual = controller.getMontoRecaudado(titulo); //lo que tiene recaudado por ahora
    int disponible = montoMax - montoActual;//lo que queda por recaudar

    if (disponible <= 0) { //si ya llego al maximo no permito invetir 
        JOptionPane.showMessageDialog(this, "La propuesta ya alcanzó el monto máximo");
        return;
    }

    if (montoAInvertir > disponible) { // si supero lo disponible pregunto si quiere inverti lo que queda disponible
            int opcion = JOptionPane.showConfirmDialog(this,
            "Solo quedan $" + disponible + " disponibles. ¿Desea invertir esa cantidad en lugar de " + montoAInvertir + "?",
            "Monto máximo excedido",
            JOptionPane.YES_NO_OPTION);

            if (opcion == JOptionPane.YES_OPTION) {
                montoAInvertir = disponible;
            } else {
                return;
            }
        }
        
        TipoRetorno retornoEnum = tipoRetorno(retorno);
       // DTFecha  fecha = new DTFecha(LocalDate.now());
        DTOColaboracion colab = new DTOColaboracion(retornoEnum, montoAInvertir, colaborador, titulo, LocalDate.now());

        controller.altaColaboracion(colab); //si llego hasta aca es porque cumple con no haber colaborado a la propuesta antes y no a alcanzado el monto
        JOptionPane.showMessageDialog(this, "Colaboración registrada correctamente");
        limpiar();
       cargarDatosAMostrar();
       
        
                       
    }//GEN-LAST:event_BtnConfirmarActionPerformed
    private void limpiar(){
    
        propuestaSeleccionada=null;
     
                
        tipoRetornocbox.removeAllItems(); 
        tipoRetornocbox.addItem("SeleccionarRetorno"); 
        
        campoMonto.setText("");
    }
    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
       limpiar();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void tipoRetornocboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tipoRetornocboxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tipoRetornocboxActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnConfirmar;
    private javax.swing.JComboBox<String> Colaborador;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JTextField campoMonto;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JComboBox<String> tipoRetornocbox;
    // End of variables declaration//GEN-END:variables
}
