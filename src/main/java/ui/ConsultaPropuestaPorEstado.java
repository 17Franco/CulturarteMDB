package ui;
import java.util.HashSet;
import javax.swing.JOptionPane;
import java.util.Set;
import logica.DTO.DTOPropuesta;
import logica.Fabrica;
import logica.IController;
import javax.swing.JDesktopPane;

/**
 *
 * @author klaas
 */
public class ConsultaPropuestaPorEstado extends javax.swing.JInternalFrame {

    Set<DTOPropuesta> almacenTabla;            //Almacena la tabla recibida desde la base de datos.
    private String estadoSeleccionado;     //Almacen para el estado.
    private IController controller = Fabrica.getInstance().getController();
            
            
    public ConsultaPropuestaPorEstado() 
    {

        initComponents();
        almacenTabla = new HashSet<>();
        estadoSeleccionado = "";
        botonContinuar.setVisible(false);   //Esto es para que el botón solo aparezca si se presiona una de las opciones!
       
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        etiqueta_subtitulo = new javax.swing.JLabel();
        botonPropuestasFinanciadas = new javax.swing.JButton();
        botonPropuestasNoFinanciadas = new javax.swing.JButton();
        botonPropuestasCanceladas = new javax.swing.JButton();
        botonPropuestasPublicadas = new javax.swing.JButton();
        botonPropuestasIngresadas = new javax.swing.JButton();
        botonPropuestasEnFinanciacion = new javax.swing.JButton();
        no_se_si_esto_permanezca_aca = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        botonContinuar = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Consulta De Propuestas");

        etiqueta_subtitulo.setText("Seleccione un estado:");

        botonPropuestasFinanciadas.setText("Propuestas Financiadas");
        botonPropuestasFinanciadas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonPropuestasFinanciadasActionPerformed(evt);
            }
        });

        botonPropuestasNoFinanciadas.setText("Propuestas No Financiadas");
        botonPropuestasNoFinanciadas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonPropuestasNoFinanciadasActionPerformed(evt);
            }
        });

        botonPropuestasCanceladas.setText("Propuestas Canceladas");
        botonPropuestasCanceladas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonPropuestasCanceladasActionPerformed(evt);
            }
        });

        botonPropuestasPublicadas.setText("Propuestas Publicadas");
        botonPropuestasPublicadas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonPropuestasPublicadasActionPerformed(evt);
            }
        });

        botonPropuestasIngresadas.setText("Propuestas Ingresadas");
        botonPropuestasIngresadas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonPropuestasIngresadasActionPerformed(evt);
            }
        });

        botonPropuestasEnFinanciacion.setText("Propuestas En Financiación");
        botonPropuestasEnFinanciacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonPropuestasEnFinanciacionActionPerformed(evt);
            }
        });

        no_se_si_esto_permanezca_aca.setBackground(new java.awt.Color(102, 153, 255));

        jLabel2.setFont(new java.awt.Font("Manjari", 1, 24)); // NOI18N
        jLabel2.setText("CUL");
        jLabel2.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jLabel3.setFont(new java.awt.Font("Manjari", 1, 24)); // NOI18N
        jLabel3.setText("ARTE");
        jLabel3.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jLabel4.setFont(new java.awt.Font("Manjari", 1, 24)); // NOI18N
        jLabel4.setText("TUR");
        jLabel4.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        botonContinuar.setText("Continuar");
        botonContinuar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonContinuarActionPerformed(evt);
            }
        });

        jSeparator1.setBackground(new java.awt.Color(102, 153, 255));
        jSeparator1.setForeground(new java.awt.Color(102, 153, 255));

        javax.swing.GroupLayout no_se_si_esto_permanezca_acaLayout = new javax.swing.GroupLayout(no_se_si_esto_permanezca_aca);
        no_se_si_esto_permanezca_aca.setLayout(no_se_si_esto_permanezca_acaLayout);
        no_se_si_esto_permanezca_acaLayout.setHorizontalGroup(
            no_se_si_esto_permanezca_acaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, no_se_si_esto_permanezca_acaLayout.createSequentialGroup()
                .addGap(0, 74, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(70, 70, 70))
            .addGroup(no_se_si_esto_permanezca_acaLayout.createSequentialGroup()
                .addGroup(no_se_si_esto_permanezca_acaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(no_se_si_esto_permanezca_acaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(no_se_si_esto_permanezca_acaLayout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jLabel2))
                        .addGroup(no_se_si_esto_permanezca_acaLayout.createSequentialGroup()
                            .addGap(22, 22, 22)
                            .addComponent(botonContinuar))
                        .addGroup(no_se_si_esto_permanezca_acaLayout.createSequentialGroup()
                            .addGap(20, 20, 20)
                            .addComponent(jLabel3))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        no_se_si_esto_permanezca_acaLayout.setVerticalGroup(
            no_se_si_esto_permanezca_acaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(no_se_si_esto_permanezca_acaLayout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(botonContinuar)
                .addGap(50, 50, 50)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(9, 9, 9)
                .addComponent(jLabel4)
                .addGap(28, 28, 28)
                .addComponent(jLabel3)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(botonPropuestasNoFinanciadas)
                    .addComponent(botonPropuestasFinanciadas)
                    .addComponent(botonPropuestasCanceladas)
                    .addComponent(botonPropuestasEnFinanciacion)
                    .addComponent(botonPropuestasPublicadas)
                    .addComponent(botonPropuestasIngresadas)
                    .addComponent(etiqueta_subtitulo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(no_se_si_esto_permanezca_aca, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(no_se_si_esto_permanezca_aca, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(etiqueta_subtitulo)
                .addGap(18, 18, 18)
                .addComponent(botonPropuestasIngresadas)
                .addGap(18, 18, 18)
                .addComponent(botonPropuestasPublicadas)
                .addGap(18, 18, 18)
                .addComponent(botonPropuestasEnFinanciacion)
                .addGap(18, 18, 18)
                .addComponent(botonPropuestasFinanciadas)
                .addGap(18, 18, 18)
                .addComponent(botonPropuestasNoFinanciadas)
                .addGap(18, 18, 18)
                .addComponent(botonPropuestasCanceladas)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonPropuestasIngresadasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonPropuestasIngresadasActionPerformed
        
        estadoSeleccionado = "INGRESADA";   //Para almacenar el resultado del botón presionado.
        botonContinuar.setVisible(true);    //Se habilita el botón "continuar".
        
    }//GEN-LAST:event_botonPropuestasIngresadasActionPerformed

    private void botonContinuarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonContinuarActionPerformed
        
        Set<DTOPropuesta> almacenTabla = controller.obtenerPropuestas(estadoSeleccionado);  //Se almacenan las propuestas seleccionadas.

        if(!almacenTabla.isEmpty()) //Si existen propuestas en ese estado...
        {   
            ListaPropuestasPorEstado lista = new ListaPropuestasPorEstado();            //Se inicializa ventana con la lista.
            lista.SetListaPropuesta(almacenTabla);                                      //Se le ingresan datos seleccionados por user

            JDesktopPane fondo2 = this.getDesktopPane();
            fondo2.add(lista);
            lista.setSize(fondo2.getSize());
            lista.setVisible(true);
            
            this.dispose(); //Para cerrar la ventana.
        }  
                
        if(almacenTabla.isEmpty())  //Si no existen propuestas en ese estado...
        {
            JOptionPane.showMessageDialog(this, "No hay ninguna propuesta en este estado actualmente");
            botonContinuar.setVisible(false);   //Se oculta para molestar y que seleccione otro estado lel.
        }
 
    }//GEN-LAST:event_botonContinuarActionPerformed

    private void botonPropuestasPublicadasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonPropuestasPublicadasActionPerformed
        estadoSeleccionado = "PUBLICADA";   //Para almacenar el resultado del botón presionado.
        botonContinuar.setVisible(true);    //Se habilita el botón "continuar".

    }//GEN-LAST:event_botonPropuestasPublicadasActionPerformed

    private void botonPropuestasEnFinanciacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonPropuestasEnFinanciacionActionPerformed
        estadoSeleccionado = "EN_FINANCIACION";   //Para almacenar el resultado del botón presionado.
        botonContinuar.setVisible(true);    //Se habilita el botón "continuar".
    }//GEN-LAST:event_botonPropuestasEnFinanciacionActionPerformed

    private void botonPropuestasFinanciadasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonPropuestasFinanciadasActionPerformed
        estadoSeleccionado = "FINANCIADA";   //Para almacenar el resultado del botón presionado.
        botonContinuar.setVisible(true);    //Se habilita el botón "continuar".
    }//GEN-LAST:event_botonPropuestasFinanciadasActionPerformed

    private void botonPropuestasNoFinanciadasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonPropuestasNoFinanciadasActionPerformed
        estadoSeleccionado = "NO_FINANCIADA";   //Para almacenar el resultado del botón presionado.
        botonContinuar.setVisible(true);    //Se habilita el botón "continuar".
    }//GEN-LAST:event_botonPropuestasNoFinanciadasActionPerformed

    private void botonPropuestasCanceladasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonPropuestasCanceladasActionPerformed
        estadoSeleccionado = "CANCELADA";   //Para almacenar el resultado del botón presionado.
        botonContinuar.setVisible(true);    //Se habilita el botón "continuar".
    }//GEN-LAST:event_botonPropuestasCanceladasActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonContinuar;
    private javax.swing.JButton botonPropuestasCanceladas;
    private javax.swing.JButton botonPropuestasEnFinanciacion;
    private javax.swing.JButton botonPropuestasFinanciadas;
    private javax.swing.JButton botonPropuestasIngresadas;
    private javax.swing.JButton botonPropuestasNoFinanciadas;
    private javax.swing.JButton botonPropuestasPublicadas;
    private javax.swing.JLabel etiqueta_subtitulo;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPanel no_se_si_esto_permanezca_aca;
    // End of variables declaration//GEN-END:variables
}
