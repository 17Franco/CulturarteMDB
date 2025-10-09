/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package ui;

import java.util.Arrays;
import java.util.List;
import logica.DTO.DTOPropuesta;
import logica.DTO.TipoRetorno;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.JCheckBox;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import logica.DTO.DTOCategoria;
import logica.Fabrica;
import logica.IController;
import logica.DTO.TipoRetorno;
import logica.DTO.DTORegistro_Estado;
import logica.DTO.Estado;

/**
 *
 * @author asus
 */
public class ChangeDataProp extends javax.swing.JInternalFrame {
    
    private IController controller = Fabrica.getInstance().getController();
    private String rutaImagen = null;
    String categoria;
    DTOPropuesta datos = new DTOPropuesta();
    /**
     * Creates new form ChangeDataProp
     */
    public ChangeDataProp() {
        initComponents();
        EstadoM.removeAllItems(); 
        for (Estado e : Estado.values()) {
            EstadoM.addItem(e);
        }
        //Seleccion de editables
        DescripcionField.setEditable(false);
        LugarField.setEditable(false);
        PrecioEntradaField.setEditable(false);
        MontoTotalField.setEditable(false);
        d.setEditable(false);
        m.setEditable(false);
        a.setEditable(false);
        
        D1.addActionListener(e -> {
            if (D1.isSelected()) {
                DescripcionField.setEditable(true);
            } else {
                DescripcionField.setEditable(false);
            }
        });
        L1.addActionListener(e -> {
            if (L1.isSelected()) {
                LugarField.setEditable(true);
            } else {
                LugarField.setEditable(false);
            }
        });
        P1.addActionListener(e -> {
            if (P1.isSelected()) {
                PrecioEntradaField.setEditable(true);
            } else {
                PrecioEntradaField.setEditable(false);
            }
        });
        M1.addActionListener(e -> {
            if (M1.isSelected()) {
                MontoTotalField.setEditable(true);
            } else {
                MontoTotalField.setEditable(false);
            }
        });
        F1.addActionListener(e -> {
            if (F1.isSelected()) {
                d.setEditable(true);
                m.setEditable(true);
                a.setEditable(true);
            } else {
                d.setEditable(false);
                m.setEditable(false);
                a.setEditable(false);    
            }
        });
        //fin editables
    }
    public void AsignarCategoria(String cat){
        this.categoria=cat;
        ListCat.setText(categoria);
    }
    private boolean validarCampo(List<JTextField> campos) {
        for (JTextField campo : campos) {
            if (campo.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this,"Faltan datos por completar","Error", JOptionPane.ERROR_MESSAGE);
                campo.requestFocus();
                return false;
            }
        }
        try {
            Integer.parseInt(PrecioEntradaField.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El campo Precio debe ser un numero entero", "Error", JOptionPane.ERROR_MESSAGE);
            PrecioEntradaField.requestFocus();
            return false;
        }
        try {
            Integer.parseInt(MontoTotalField.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El campo Monto debe ser un numero entero", "Error", JOptionPane.ERROR_MESSAGE);
            MontoTotalField.requestFocus();
            return false;
        }
        if (!Utilities.validarFecha(d.getText(), m.getText(), a.getText())) {
            return false;
        }
        return true;
    }
    public static boolean validarRetorno(JCheckBox t1, JCheckBox t2) {
        if (!t1.isSelected() && !t2.isSelected()) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar al menos un tipo de retorno",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
   public void obtenerT (DTOPropuesta a){
          datos = a; 
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jCheckBox7 = new javax.swing.JCheckBox();
        Encabezado = new javax.swing.JLabel();
        Descripcion = new javax.swing.JLabel();
        Lugar = new javax.swing.JLabel();
        Fecha = new javax.swing.JLabel();
        PrecioEntrada = new javax.swing.JLabel();
        Monto = new javax.swing.JLabel();
        EstadoModi = new javax.swing.JLabel();
        DescripcionField = new javax.swing.JTextField();
        LugarField = new javax.swing.JTextField();
        d = new javax.swing.JTextField();
        PrecioEntradaField = new javax.swing.JTextField();
        MontoTotalField = new javax.swing.JTextField();
        EstadoM = new javax.swing.JComboBox<>();
        Dia = new javax.swing.JLabel();
        Mes = new javax.swing.JLabel();
        m = new javax.swing.JTextField();
        Anio = new javax.swing.JLabel();
        a = new javax.swing.JTextField();
        Cerrar = new javax.swing.JButton();
        Modificar = new javax.swing.JButton();
        img = new javax.swing.JLabel();
        Imagen = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        tt1 = new javax.swing.JCheckBox();
        tt2 = new javax.swing.JCheckBox();
        ListCat = new javax.swing.JButton();
        D1 = new javax.swing.JCheckBox();
        L1 = new javax.swing.JCheckBox();
        F1 = new javax.swing.JCheckBox();
        P1 = new javax.swing.JCheckBox();
        M1 = new javax.swing.JCheckBox();
        E1 = new javax.swing.JCheckBox();
        r1 = new javax.swing.JCheckBox();
        C2 = new javax.swing.JCheckBox();
        I1 = new javax.swing.JCheckBox();

        jCheckBox7.setText("jCheckBox7");

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Modificando Datos");

        Encabezado.setFont(new java.awt.Font("DejaVu Sans Mono", 0, 18)); // NOI18N
        Encabezado.setText("Modificar los datos ");
        Encabezado.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                EncabezadoInputMethodTextChanged(evt);
            }
        });

        Descripcion.setText("Descripcion");

        Lugar.setText("Lugar");

        Fecha.setText("Fecha");

        PrecioEntrada.setText("Precio Entrada");

        Monto.setText("Monto Total");

        EstadoModi.setText("Estado");

        DescripcionField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DescripcionFieldActionPerformed(evt);
            }
        });

        LugarField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LugarFieldActionPerformed(evt);
            }
        });

        d.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dActionPerformed(evt);
            }
        });

        PrecioEntradaField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PrecioEntradaFieldActionPerformed(evt);
            }
        });

        MontoTotalField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MontoTotalFieldActionPerformed(evt);
            }
        });

        EstadoM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EstadoMActionPerformed(evt);
            }
        });

        Dia.setText("Dia");

        Mes.setText("Mes");

        m.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mActionPerformed(evt);
            }
        });

        Anio.setText("Año");

        a.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aActionPerformed(evt);
            }
        });

        Cerrar.setText("Cerrar");
        Cerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CerrarActionPerformed(evt);
            }
        });

        Modificar.setText("Modificar");
        Modificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ModificarActionPerformed(evt);
            }
        });

        img.setText("Imagen");

        Imagen.setText("Subir Nueva Imagen");
        Imagen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ImagenActionPerformed(evt);
            }
        });

        jLabel1.setText("Retorno");

        jLabel2.setText("Categoria");

        tt1.setText("Entrada Gratis");

        tt2.setText("Procentaje de Ganancia");

        ListCat.setText("Seleccione Categoria");
        ListCat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ListCatActionPerformed(evt);
            }
        });

        D1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                D1ActionPerformed(evt);
            }
        });

        L1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                L1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(Monto)
                            .addGap(18, 18, 18)
                            .addComponent(MontoTotalField, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(M1))
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(Fecha)
                                .addComponent(EstadoModi))
                            .addGap(18, 18, 18)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(EstadoM, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(E1))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(Dia)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(d, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(Mes)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(m, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(Anio)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(a, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(F1))))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(324, 324, 324)
                                .addComponent(Cerrar))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(Lugar)
                                            .addComponent(Descripcion))
                                        .addGap(18, 18, 18))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(PrecioEntrada)
                                        .addGap(18, 18, 18)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(LugarField)
                                            .addComponent(DescripcionField, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(L1)
                                            .addComponent(D1)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(PrecioEntradaField, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(P1))))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(img)
                            .addComponent(Modificar))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(ListCat, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(C2))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(tt1)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(tt2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(r1))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(Imagen, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(I1)))))
                .addContainerGap(42, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(Encabezado)
                .addGap(89, 89, 89))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Encabezado)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(DescripcionField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Descripcion)
                    .addComponent(D1))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(LugarField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(Lugar))
                    .addComponent(L1))
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(PrecioEntrada)
                        .addComponent(PrecioEntradaField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(P1))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(Monto)
                        .addComponent(MontoTotalField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(M1))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(EstadoM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(EstadoModi))
                    .addComponent(E1))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(d, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(Dia)
                        .addComponent(Mes)
                        .addComponent(m, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(Anio)
                        .addComponent(a, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(Fecha))
                    .addComponent(F1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(r1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(C2))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tt1)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tt2)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(ListCat))))
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(I1)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(Imagen)
                        .addComponent(img)))
                .addGap(62, 62, 62)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Cerrar)
                    .addComponent(Modificar))
                .addGap(34, 34, 34))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ModificarActionPerformed

        List<JTextField> campos = Arrays.asList(DescripcionField,LugarField,PrecioEntradaField,MontoTotalField,d,m,a);
        String auxTitulo = datos.getTitulo();
        String auxUsuario = datos.getProponente().getNickname();
        String auxD = datos.getDescripcion();
        String auxL = datos.getLugar();
        String auxR = datos.getImagen();
        LocalDate auxF = datos.getFecha();
        int auxP = datos.getPrecio();
        int auxM = datos.getMontoTotal();
        List<TipoRetorno> auxRet = datos.getRetorno();
        String auxC = datos.getCategoria().getNombreCategoria();
        
        Estado newEstado = (Estado) EstadoM.getSelectedItem();
        
        if (E1.isSelected()){
            controller.modificarPropuesta(auxTitulo,auxD,auxR, auxL, auxF, auxP, auxM,auxRet,auxC, auxUsuario,newEstado);
            JOptionPane.showMessageDialog(this, "Estado Modificado con exito");
            this.dispose(); 
            return;
        }
        /*  Por si se quiere en un futuro cambiar solo algunos
        if (D1.isSelected()){
            controller.modificarPropuesta(auxTitulo,auxD,auxR, auxL, auxF, auxP, auxM,auxRet,auxC, auxUsuario,newEstado);
            JOptionPane.showMessageDialog(this, "Estado Modificado con exito");
            this.dispose(); 
            return;
        }
        if (L1.isSelected()){
            controller.modificarPropuesta(auxTitulo,auxD,auxR, auxL, auxF, auxP, auxM,auxRet,auxC, auxUsuario,newEstado);
            JOptionPane.showMessageDialog(this, "Estado Modificado con exito");
            this.dispose(); 
            return;
        }
        if (P1.isSelected()){
            controller.modificarPropuesta(auxTitulo,auxD,auxR, auxL, auxF, auxP, auxM,auxRet,auxC, auxUsuario,newEstado);
            JOptionPane.showMessageDialog(this, "Estado Modificado con exito");
            this.dispose(); 
            return;
        }
        if (M1.isSelected()){
            controller.modificarPropuesta(auxTitulo,auxD,auxR, auxL, auxF, auxP, auxM,auxRet,auxC, auxUsuario,newEstado);
            JOptionPane.showMessageDialog(this, "Estado Modificado con exito");
            this.dispose(); 
            return;
        }
        if (F1.isSelected()){
            controller.modificarPropuesta(auxTitulo,auxD,auxR, auxL, auxF, auxP, auxM,auxRet,auxC, auxUsuario,newEstado);
            JOptionPane.showMessageDialog(this, "Estado Modificado con exito");
            this.dispose(); 
            return;
        }
        if (r1.isSelected()){
            controller.modificarPropuesta(auxTitulo,auxD,auxR, auxL, auxF, auxP, auxM,auxRet,auxC, auxUsuario,newEstado);
            JOptionPane.showMessageDialog(this, "Estado Modificado con exito");
            this.dispose(); 
            return;
        }
        if (C2.isSelected()){
            controller.modificarPropuesta(auxTitulo,auxD,auxR, auxL, auxF, auxP, auxM,auxRet,auxC, auxUsuario,newEstado);
            JOptionPane.showMessageDialog(this, "Estado Modificado con exito");
            this.dispose(); 
            return;
        }
        if (I1.isSelected()){
            controller.modificarPropuesta(auxTitulo,auxD,auxR, auxL, auxF, auxP, auxM,auxRet,auxC, auxUsuario,newEstado);
            JOptionPane.showMessageDialog(this, "Estado Modificado con exito");
            this.dispose(); 
            return;
        }
*/
        String descripcion = DescripcionField.getText();
        String lugar = LugarField.getText();
        String dia = d.getText();
        int precio = Integer.parseInt(PrecioEntradaField.getText());
        int montoTotal = Integer.parseInt(MontoTotalField.getText());
        List<TipoRetorno> retorno = new ArrayList<>();
        if (tt1.isSelected()) {
            retorno.add(TipoRetorno.EntradaGratis);
        }
        if (tt2.isSelected()) {
            retorno.add(TipoRetorno.PorcentajeGanancia);
        }
        String mes =m.getText();
        String anio =a.getText();
            if(validarCampo(campos) && validarRetorno(tt1,tt2)){
                Utilities.copiarImagen(rutaImagen,auxTitulo);
                LocalDate fechaEvento=LocalDate.of(Integer.parseInt(anio),Integer.parseInt(mes),Integer.parseInt(dia));
                controller.modificarPropuesta(auxTitulo, descripcion,rutaImagen, lugar, fechaEvento, precio, montoTotal,retorno,categoria, auxUsuario,newEstado);
                JOptionPane.showMessageDialog(this, "Propuesta Modificada con exito");
            }
     this.dispose();       
    }//GEN-LAST:event_ModificarActionPerformed

    private void EncabezadoInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_EncabezadoInputMethodTextChanged

    }//GEN-LAST:event_EncabezadoInputMethodTextChanged

    private void DescripcionFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DescripcionFieldActionPerformed
       
    }//GEN-LAST:event_DescripcionFieldActionPerformed

    private void LugarFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LugarFieldActionPerformed
        
    }//GEN-LAST:event_LugarFieldActionPerformed

    private void dActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dActionPerformed
       
    }//GEN-LAST:event_dActionPerformed

    private void MontoTotalFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MontoTotalFieldActionPerformed
       
    }//GEN-LAST:event_MontoTotalFieldActionPerformed

    private void EstadoMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EstadoMActionPerformed
        
    }//GEN-LAST:event_EstadoMActionPerformed

    private void ImagenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ImagenActionPerformed
        JFileChooser fileProp = new JFileChooser();
        fileProp.setDialogTitle("Seleccionar imagen");
        fileProp.setFileSelectionMode(JFileChooser.FILES_ONLY);

        fileProp.setAcceptAllFileFilterUsed(false);
        fileProp.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "Imagenes", "jpg", "png"));

        int resultado = fileProp.showOpenDialog(this);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivo = fileProp.getSelectedFile();
            rutaImagen = archivo.getAbsolutePath();
        }        
    }//GEN-LAST:event_ImagenActionPerformed

    private void PrecioEntradaFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PrecioEntradaFieldActionPerformed
        
    }//GEN-LAST:event_PrecioEntradaFieldActionPerformed

    private void mActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mActionPerformed
        
    }//GEN-LAST:event_mActionPerformed

    private void aActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aActionPerformed
        
    }//GEN-LAST:event_aActionPerformed

    private void CerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CerrarActionPerformed
        this.dispose();
    }//GEN-LAST:event_CerrarActionPerformed

    private void ListCatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ListCatActionPerformed
        SelectCategoria mostrar = new SelectCategoria(this);
        JDesktopPane fondo3Final = this.getDesktopPane();
        fondo3Final.add(mostrar);
        mostrar.setSize(fondo3Final.getSize());
        mostrar.setVisible(true);
    }//GEN-LAST:event_ListCatActionPerformed

    private void D1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_D1ActionPerformed

    }//GEN-LAST:event_D1ActionPerformed

    private void L1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_L1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_L1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Anio;
    private javax.swing.JCheckBox C2;
    private javax.swing.JButton Cerrar;
    private javax.swing.JCheckBox D1;
    private javax.swing.JLabel Descripcion;
    private javax.swing.JTextField DescripcionField;
    private javax.swing.JLabel Dia;
    private javax.swing.JCheckBox E1;
    private javax.swing.JLabel Encabezado;
    private javax.swing.JComboBox<Estado> EstadoM;
    private javax.swing.JLabel EstadoModi;
    private javax.swing.JCheckBox F1;
    private javax.swing.JLabel Fecha;
    private javax.swing.JCheckBox I1;
    private javax.swing.JButton Imagen;
    private javax.swing.JCheckBox L1;
    private javax.swing.JButton ListCat;
    private javax.swing.JLabel Lugar;
    private javax.swing.JTextField LugarField;
    private javax.swing.JCheckBox M1;
    private javax.swing.JLabel Mes;
    private javax.swing.JButton Modificar;
    private javax.swing.JLabel Monto;
    private javax.swing.JTextField MontoTotalField;
    private javax.swing.JCheckBox P1;
    private javax.swing.JLabel PrecioEntrada;
    private javax.swing.JTextField PrecioEntradaField;
    private javax.swing.JTextField a;
    private javax.swing.JTextField d;
    private javax.swing.JLabel img;
    private javax.swing.JCheckBox jCheckBox7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField m;
    private javax.swing.JCheckBox r1;
    private javax.swing.JCheckBox tt1;
    private javax.swing.JCheckBox tt2;
    // End of variables declaration//GEN-END:variables
}
