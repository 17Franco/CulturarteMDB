
package ui;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import logica.DTO.DTOColaboracion;
import logica.Fabrica;
import logica.IController;


public class CancelarColaboracion extends javax.swing.JInternalFrame {

    private IController controller = Fabrica.getInstance().getController();
    private List<DTOColaboracion> c;
    
    public CancelarColaboracion() {
        initComponents();
        //ColaboracionesTable
        cargarTabla();
        ColaboracionesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
         ColaboracionesTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) { // evita eventos intermedios
                int fila = ColaboracionesTable.getSelectedRow();
            if (fila >= 0) {
                DTOColaboracion seleccionada = c.get(fila);
                colaboracionesSelecionada(seleccionada);
            }
            }
        });
         
    }
    public void cargarTabla(){
         
         Set<DTOColaboracion> setCol=controller.getDTOColaboraciones();
         c= new ArrayList<>(setCol);
         
         String[] cols = {"NickName", "Propuesta"};
         DefaultTableModel model =  new DefaultTableModel(cols, 0);
         
         for(DTOColaboracion col: c){
            model.addRow(new Object[]{col.getColaborador(), col.getPropuesta()});
        }
         ColaboracionesTable.setModel(model);
    }
   public void  colaboracionesSelecionada(DTOColaboracion seleccionada){
       String[] columnas = {"NickName", "Monto","Fecha","Retorno"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

        // Agregamos la fila con los datos
        modelo.addRow(new Object[]{seleccionada.getColaborador(),seleccionada.getMonto(),seleccionada.getCreado(),seleccionada.getTipoRetorno()});

        table.setModel(modelo);
   }
    @SuppressWarnings("unchecked")
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        ColaboracionesTable = new javax.swing.JTable();
        Eliminar = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("CancelarColaboracion");

        jLabel1.setText("Colaboraciones");

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "NickName", "Monto", "Fecha", "Retorno"
            }
        ));
        jScrollPane1.setViewportView(table);

        ColaboracionesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "NickName", "Propuesta"
            }
        ));
        jScrollPane2.setViewportView(ColaboracionesTable);

        Eliminar.setText("Eliminar");
        Eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EliminarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(188, 188, 188)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 466, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Eliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(126, 126, 126))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 466, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 193, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(Eliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(70, 70, 70)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(300, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void EliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EliminarActionPerformed
        int fila = ColaboracionesTable.getSelectedRow();
        if (fila >= 0) {
            DTOColaboracion seleccionada = c.get(fila);
            // acá llamás al controller para borrar la colaboracion con el id seleccionado
            controller.CancelarColaboracion(seleccionada.getId());
            cargarTabla();
            DefaultTableModel modeloVacio = new DefaultTableModel(
            new Object[][]{}, 
            new String[]{"NickName", "Monto", "Fecha", "Retorno"});
            table.setModel(modeloVacio);
        }else{
            JOptionPane.showMessageDialog(this,"Selecciones una Colaboracion");
        }

    }//GEN-LAST:event_EliminarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable ColaboracionesTable;
    private javax.swing.JButton Eliminar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables
}
