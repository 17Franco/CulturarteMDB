package ui;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JDesktopPane;
import logica.DTO.DTOPropuesta;
import javax.swing.table.DefaultTableModel;
import java.util.Iterator;



//Esta UI solo muestra una lista de propuestas por estado al usuario, es parte de "ConsultaPropuestasPorEstado"
public class ListaPropuestasPorEstado extends javax.swing.JInternalFrame {

    Set<DTOPropuesta> lista; //lista a mostrar
    String elemento; //Elemento seleccionado de la tabla.
    
    public ListaPropuestasPorEstado() 
    {
        initComponents();
        lista = new HashSet<>();
        botonContinuar.setVisible(false);
    }
    
    public void SetListaPropuesta(Set<DTOPropuesta> _lista)
    {
        lista = _lista;
        actualizarTabla();  //Luego de actualizar la lista se debe refrescar la tabla.
        
    }
    
    public DTOPropuesta extraerSeleccionadoLista()
    {
        Iterator<DTOPropuesta> ct = lista.iterator();
        
        while(ct.hasNext()) 
        {
            DTOPropuesta nodo = ct.next();

            if(nodo.getTitulo().equals(elemento)) 
            {
                
                return nodo;   
            }
        }
        
        return null;    //Algo raro pasó ahi si llega a esto...
    }
    
    public void SetElementoSeleccionadoTabla(String _elemento)
    {
        elemento = _elemento;
    }
    
    public void actualizarTabla() 
    {
        String[] c1 = {"Nombre","Descripción"};
        
        DefaultTableModel tabla = new DefaultTableModel(c1, 0) //Se inicializa directamente
        {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Impide que se pueda editar
            }
        };

        for(DTOPropuesta ct : lista) //Iteracion para ir ingresando contenido.
        {
            tabla.addRow(new Object[]{ct.getTitulo(), ct.getDescripcion()});
        }

        tablaPropuestasFiltradas.setModel(tabla);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrollTabla = new javax.swing.JScrollPane();
        tablaPropuestasFiltradas = new javax.swing.JTable();
        subtitulo = new javax.swing.JLabel();
        botonAtras = new javax.swing.JButton();
        botonContinuar = new javax.swing.JButton();
        botonSalir = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Listado de Propuestas");

        tablaPropuestasFiltradas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaPropuestasFiltradasMouseClicked(evt);
            }
        });
        scrollTabla.setViewportView(tablaPropuestasFiltradas);

        subtitulo.setText("Seleccione una propuesta...");

        botonAtras.setText("Atrás");
        botonAtras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAtrasActionPerformed(evt);
            }
        });

        botonContinuar.setText("Siguiente...");
        botonContinuar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonContinuarActionPerformed(evt);
            }
        });

        botonSalir.setText("Salir");
        botonSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonSalirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(scrollTabla, javax.swing.GroupLayout.PREFERRED_SIZE, 381, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(botonSalir)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(botonAtras)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(botonContinuar)))
                    .addComponent(subtitulo))
                .addContainerGap(7, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(subtitulo)
                .addGap(11, 11, 11)
                .addComponent(scrollTabla, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonAtras)
                    .addComponent(botonContinuar)
                    .addComponent(botonSalir))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonAtrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAtrasActionPerformed
        
        ConsultaPropuestaPorEstado setup = new ConsultaPropuestaPorEstado();            //Se inicializa ventana con el setup de Consulta.
       
        JDesktopPane fondo1 = this.getDesktopPane();
        fondo1.add(setup);
        setup.setSize(fondo1.getSize());
        setup.setVisible(true);
        
        this.dispose();
    }//GEN-LAST:event_botonAtrasActionPerformed

    private void botonContinuarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonContinuarActionPerformed
        DTOPropuesta almacenDatos = extraerSeleccionadoLista();                 //Se extrae y prepara el elemento a mostrar a continuación.
        
        MostrarDatosPropuesta mostrar = new MostrarDatosPropuesta();            //Se inicializa ventana con datos.
        mostrar.SetDatosPropuesta(almacenDatos);                                //Se le ingresan esos datos preparados

        JDesktopPane fondo3Final = this.getDesktopPane();
        fondo3Final.add(mostrar);
        mostrar.setSize(fondo3Final.getSize());
        mostrar.setVisible(true);

        this.dispose(); //Para cerrar la ventana.
        
    }//GEN-LAST:event_botonContinuarActionPerformed

    private void botonSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonSalirActionPerformed
        this.dispose();
    }//GEN-LAST:event_botonSalirActionPerformed

    private void tablaPropuestasFiltradasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaPropuestasFiltradasMouseClicked
        //Acá se almacena el dato clicado.
        int fila = tablaPropuestasFiltradas.getSelectedRow();   //Se obtiene la fila seleccionada
        elemento  = tablaPropuestasFiltradas.getValueAt(fila, 0).toString(); //Elemento seleccionado, se almacena unicamente el nombre de la propuesta y forzado a String (variable de UI).
        
        botonContinuar.setVisible(true); //Se habilita botón para continuar
        
    }//GEN-LAST:event_tablaPropuestasFiltradasMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonAtras;
    private javax.swing.JButton botonContinuar;
    private javax.swing.JButton botonSalir;
    private javax.swing.JScrollPane scrollTabla;
    private javax.swing.JLabel subtitulo;
    private javax.swing.JTable tablaPropuestasFiltradas;
    // End of variables declaration//GEN-END:variables
}
