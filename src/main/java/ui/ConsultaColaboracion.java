
package ui;

import java.util.ArrayList;
import java.util.List;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import logica.DTO.DTOColaboracion;
import logica.DTO.DTOColaborador;
import logica.Fabrica;
import logica.IController;


public class ConsultaColaboracion extends javax.swing.JInternalFrame {
     private IController controller = Fabrica.getInstance().getController();
     private List<DTOColaboracion> colaboracionesActuales = new ArrayList<>();
     
    public ConsultaColaboracion() {
        initComponents();
        
        jBoxColaboradores.removeAllItems();
        jBoxColaboradores.addItem("SeleccionarUsuario"); 
        for (String u : controller.ListaColaborador()) {
            jBoxColaboradores.addItem(u); 
        }
        
        ColabAProp.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        jBoxColaboradores.addActionListener(e -> {
             String[] columna= {"Fecha", "Monto", "Retorno"};
            DefaultTableModel model = new DefaultTableModel(columna, 0);
            jTable1.setModel(model);
            
            String[] columnas = {"NickName", "Colaboracion"};
            DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
            ColabAProp.setModel(modelo);
            String item = (String) jBoxColaboradores.getSelectedItem();
            if(item != null && controller.existe(item)){
                List<DTOColaboracion> c = controller.colaboraciones(item);
                colaboracionesActuales =c;
                mostrarColaboracionesUsr(c,modelo);
                
           
              
            }
        });
        
            ColabAProp.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) { // evita eventos intermedios
                int fila = ColabAProp.getSelectedRow();
            if (fila >= 0) {
                DTOColaboracion seleccionada = colaboracionesActuales.get(fila);
                mostrarDetalleColaboracion(seleccionada);
            }
            }
        });

    }
    public void  mostrarColaboracionesUsr(List<DTOColaboracion> c,DefaultTableModel modelo){
        for(DTOColaboracion col: c){
            modelo.addRow(new Object[]{col.getColaborador(), col.getPropuesta()});
        }
    }
    public void mostrarDetalleColaboracion(DTOColaboracion c){
        String[] columnas = {"Fecha", "Monto", "Retorno"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

        // Agregamos la fila con los datos
        modelo.addRow(new Object[]{c.getCreado(),c.getMonto(), c.getTipoRetorno()});

        jTable1.setModel(modelo);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jBoxColaboradores = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        ColabAProp = new javax.swing.JTable();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("ConsultaColaboracion");

        jLabel1.setText("Colaboradores");

        jBoxColaboradores.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Fecha", "Monto Aportado", "Retorno"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        ColabAProp.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "NickName", "Colaboracion"
            }
        ));
        jScrollPane2.setViewportView(ColabAProp);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 466, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(111, 111, 111)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBoxColaboradores, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 379, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jBoxColaboradores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 100, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable ColabAProp;
    private javax.swing.JComboBox<String> jBoxColaboradores;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
