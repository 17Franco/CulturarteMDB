/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package ui;

import java.awt.Color;
import java.awt.Component;
import java.util.Comparator;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import logica.DTO.DTOPropuesta;
import logica.Fabrica;
import logica.IController;
import java.awt.Dimension;
import logica.DTO.Estado;
import java.util.Map;
import java.util.HashMap;


public class PropuestasCreadas extends javax.swing.JInternalFrame {
    
        String[] columnas = {"Título", "Monto Recaudado", "Usuarios", "Estado"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
        private IController controller = Fabrica.getInstance().getController();
        private Map<Integer, String[]> usuariosPorFila = new HashMap<>();
        
        
    public PropuestasCreadas() {
        initComponents();
        Propuestas.setModel(modelo);
      
        TableColumn columnaUsuarios = Propuestas.getColumnModel().getColumn(2);
        columnaUsuarios.setCellEditor(new DefaultCellEditor(new JComboBox()){
        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
             String[] usuariosFila = usuariosPorFila.getOrDefault(row, new String[]{""}); //podria cargar el combo llamando al controller 
            JComboBox<String> combo = new JComboBox<>(usuariosFila);
            return combo;
         }
        
        });
        
        columnaUsuarios.setCellRenderer((table, value, isSelected, hasFocus, row, column) -> {
        JLabel label = new JLabel();
        label.setOpaque(true);

        if (! hasFocus ) {
            String[] usuariosDeEstaFila = usuariosPorFila.getOrDefault(row, new String[]{""});
           label.setText(usuariosDeEstaFila[0]);
        }
            return label;
        });
    }
    
public void mostrarPropuestas(String titulo, int monto, Estado estado, String[] usuarios) {
        int fila = modelo.getRowCount();
        usuariosPorFila.put(fila, usuarios);
        modelo.addRow(new Object[]{titulo, monto, usuarios[0], estado});
   
        Propuestas.setRowHeight(20);
 }

    public void cargaDeDatos( List<DTOPropuesta> propuestas){
        if(!propuestas.isEmpty()){
            propuestas.sort(Comparator.comparing(p -> p.getEstadoAct()));
        }
        for(DTOPropuesta p: propuestas){
            //System.out.println("jhola");
            List<String> colaboradores=controller.colaboradoresAPropuesta(p.getTitulo());
            String[] Ausuarios=new String[colaboradores.size()+1];
            
            Ausuarios[0]="Colaboradores";
            System.arraycopy(colaboradores.toArray(), 0, Ausuarios, 1, colaboradores.size());
          
            int monto=controller.getMontoRecaudado(p.getTitulo());
            mostrarPropuestas(p.getTitulo(),monto,p.getEstadoAct(),Ausuarios);
        }
    }
  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        Propuestas = new javax.swing.JTable();
        lblPropuestas = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Propuestas Creadas");

        Propuestas.setModel(new javax.swing.table.DefaultTableModel(
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
                "Nombre", "Recaudacion", "Usuarios", "Estado"
            }
        ));
        Propuestas.setShowHorizontalLines(false);
        Propuestas.setShowVerticalLines(false);
        Propuestas.setIntercellSpacing(new java.awt.Dimension(0, 0));

        // Variable para guardar la fila sobre la que está el mouse
        final int[] hoveredRow = {-1};

        Propuestas.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            @Override
            public void mouseMoved(java.awt.event.MouseEvent e) {
                hoveredRow[0] = Propuestas.rowAtPoint(e.getPoint());
                Propuestas.repaint();
            }
        });

        Propuestas.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table,
                Object value, boolean isSelected, boolean hasFocus, int row, int column) {

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

        Propuestas.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
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

        Propuestas.setFont(new java.awt.Font("Roboto", java.awt.Font.PLAIN, 10));
        //Propuestas.setRowHeight(22);

        JTableHeader header = Propuestas.getTableHeader();

        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 25));
        jScrollPane1.setViewportView(Propuestas);

        lblPropuestas.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPropuestas.setText("Propuestas Creadas");

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
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblPropuestas, javax.swing.GroupLayout.DEFAULT_SIZE, 466, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 466, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(lblPropuestas)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(21, 21, 21))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Propuestas;
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblPropuestas;
    // End of variables declaration//GEN-END:variables
}
