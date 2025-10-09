
package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import logica.DTO.DTOColaboracion;
import logica.Fabrica;
import logica.IController;


public class MostrarColaboraciones extends javax.swing.JInternalFrame {
    private IController controller = Fabrica.getInstance().getController();
   
    public MostrarColaboraciones() {
        initComponents();
    }
    public void mostrarColaboraciones(String tituloP,String NickProponente,int monto, String estado,DefaultTableModel modelo){

            modelo.addRow(new Object[]{tituloP, NickProponente,monto, estado});

    }
    
    public void obtenerDatos(List<DTOColaboracion> registros,DefaultTableModel modelo){
        Colaboraciones.setModel(modelo);
        for(DTOColaboracion r:registros){
            //titulo propuesta r.getPropuestaFinanciada();  aca lo tengo
            String titulo= r.getPropuesta();
            //necesito nombre proponente que la creo
            
            String nombreProponente=controller.creadorPropuesta(titulo);
            //necesito monto recuadado por la propuesta 
            int monto = controller.getMontoRecaudado(titulo);
               
            //y nesesito el estado de la propuesta 
            String estado =controller.estadoPropuestas(titulo);
          
            mostrarColaboraciones(titulo,nombreProponente,monto,estado,modelo);
           
        }
  
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblPropuestas = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Colaboraciones = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Colaboraciones Realizadas");

        lblPropuestas.setText("Colaboraciones");

        Colaboraciones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"", "", null, null},
                {null, "", null, null},
                {null, "", null, null},
                {null, "", null, null},
                {null, "", null, null},
                {null, "", null, null},
                {null, "", null, null},
                {null, "", null, null}
            },
            new String [] {
                "Titulo", "Creador", "Recaudacion", "Estado"
            }
        ));
        Colaboraciones.setShowHorizontalLines(false);
        Colaboraciones.setShowVerticalLines(false);
        Colaboraciones.setIntercellSpacing(new java.awt.Dimension(0, 0));

        // Variable para guardar la fila sobre la que está el mouse
        final int[] hoveredRow = {-1};

        Colaboraciones.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            @Override
            public void mouseMoved(java.awt.event.MouseEvent e) {
                hoveredRow[0] = Colaboraciones.rowAtPoint(e.getPoint());
                Colaboraciones.repaint();
            }
        });

        Colaboraciones.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table,Object value, boolean isSelected, boolean hasFocus, int row, int column) {

                java.awt.Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (row == hoveredRow[0] && !isSelected) {
                    c.setBackground(new java.awt.Color(220, 220, 220)); // gris clarito al pasar el mouse
                } else if (isSelected) {
                    c.setBackground(new java.awt.Color(180, 200, 240)); // color para la fila seleccionada
                } else {
                    c.setBackground(java.awt.Color.WHITE); // fondo normal
                }
                //COLOR GRIS new java.awt.Color(220, 220, 220)
                return c;
            }
        });

        Colaboraciones.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {

                JLabel lbl = (JLabel) super.getTableCellRendererComponent(
                    table, value, isSelected, hasFocus, row, column);

                lbl.setBorder(BorderFactory.createEmptyBorder()); // sin líneas
                //lbl.setHorizontalAlignment(CENTER);
                lbl.setBackground(Color.BLACK);// opcional, centrar texto
                lbl.setForeground(Color.WHITE);
                lbl.setFont(new java.awt.Font("Roboto", java.awt.Font.BOLD, 12));

                return lbl;
            }
        });

        Colaboraciones.setFont(new java.awt.Font("Roboto", java.awt.Font.PLAIN, 10));
        //Propuestas.setRowHeight(22);

        JTableHeader header = Colaboraciones.getTableHeader();

        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 25));
        jScrollPane1.setViewportView(Colaboraciones);

        jButton1.setText("Salir");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblPropuestas)
                        .addGap(187, 187, 187))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lblPropuestas)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 381, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(30, 30, 30))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Colaboraciones;
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblPropuestas;
    // End of variables declaration//GEN-END:variables
}
