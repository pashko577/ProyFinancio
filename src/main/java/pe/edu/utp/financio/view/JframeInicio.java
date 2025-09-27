/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package pe.edu.utp.financio.view;

import java.awt.CardLayout;
import java.awt.Component;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import pe.edu.utp.financio.dao.impl.CategoriaDaoImpl;
import pe.edu.utp.financio.dao.impl.MetaDAOMongo;
import pe.edu.utp.financio.dao.impl.MetodoPagoDaoImpl;
import pe.edu.utp.financio.dao.impl.MovimientoDaoImpl;
import pe.edu.utp.financio.modelo.Categoria;
import pe.edu.utp.financio.modelo.Meta;
import pe.edu.utp.financio.modelo.Metodopago;
import pe.edu.utp.financio.modelo.Movimiento;
import pe.edu.utp.financio.modelo.Usuario;
import pe.edu.utp.financio.service.MovimientoService;
import pe.edu.utp.financio.service.impl.MovimientoServiceImpl;

/**
 *
 * @author User
 */
public class JframeInicio extends javax.swing.JFrame {

    /**
     * Creates new form JframeInicio
     */
    private Usuario usuario;
    CardLayout cardLayout;
    private MovimientoService movimientoService = new MovimientoServiceImpl();
    MovimientoDaoImpl daoMov = new MovimientoDaoImpl();
    private Map<String, JProgressBar> barrasMetas = new HashMap<>();

    public JframeInicio(Usuario usuario) {
        initComponents();
        inicializarTablaMetas();
        txtTotal.setEditable(false);

        // Configurar columnas de la tabla de movimientos
        DefaultTableModel modeloMovimientos = new DefaultTableModel(
                new Object[]{"Fecha", "Tipo", "Categoría", "Descripción", "Monto"}, 0
        );
        tblMovimientos.setModel(modeloMovimientos);
        // Acción del botón Movimiento
        btnMovimiento.addActionListener(e -> {
            cardLayout.show(JPpanelcontenido, "panelMovimientos");
            cargarMovimientos(); // 👈 cargar datos en la tabla
        });

        this.usuario = usuario;

        JPpanelcontenido.setLayout(new CardLayout());
        cardLayout = (CardLayout) JPpanelcontenido.getLayout();
        JPpanelcontenido.add(Panelnicio, "panelInicio");
        JPpanelcontenido.add(PanelIngreso, "panelIngresos");
        JPpanelcontenido.add(PanelGastos, "panelGastos");
        JPpanelcontenido.add(PanelAnalisisFinanzas, "panelAnalisisFinanzas");
        JPpanelcontenido.add(PanelMovimientos, "panelMovimientos");
        JPpanelcontenido.add(PanelMetas, "panelMetas");
        JPpanelcontenido.add(PanelExportarDatos, "panelExportarDatos");
        JPpanelcontenido.add(PanelCajachica, "PanelCajachica");
        btnInicio.addActionListener(e -> cardLayout.show(JPpanelcontenido, "panelInicio"));
        btnIngreso.addActionListener(e -> cardLayout.show(JPpanelcontenido, "panelIngresos"));
        btnGastos.addActionListener(e -> cardLayout.show(JPpanelcontenido, "panelGastos"));
        btnAnalisisFinanzas.addActionListener(e -> cardLayout.show(JPpanelcontenido, "panelAnalisisFinanzas"));
        btnMovimiento.addActionListener(e -> cardLayout.show(JPpanelcontenido, "panelMovimientos"));
        btnMetas.addActionListener(e -> cardLayout.show(JPpanelcontenido, "panelMetas"));
        btnExportarDatos.addActionListener(e -> cardLayout.show(JPpanelcontenido, "panelExportarDatos"));
        btnCajachica.addActionListener(e -> cardLayout.show(JPpanelcontenido, "PanelCajachica"));

        btnMovimiento.addActionListener(e -> {
            cardLayout.show(JPpanelcontenido, "panelMovimientos");
            cargarMovimientos(); // 👈 aquí cargas la tabla de movimientos
        });
        btnMetas.addActionListener(e -> cardLayout.show(JPpanelcontenido, "panelMetas"));
        btnExportarDatos.addActionListener(e -> cardLayout.show(JPpanelcontenido, "panelExportarDatos"));

        // Aquí enganchas el listener al combo de tipo
        cbxTipo.addActionListener(e -> {
            try {
                cargarCategoriasFiltro();
//           cargarMovimientos();  // para que se actualize al hacer click
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al cargar categorías: " + ex.getMessage());
            }
        });

        try {
            cargarCategoriasFiltro();
            cargarCategorias();
            cargarIngresos();
            cargarGastos();
            cargarCategoriasIngresos();
            cargarCategoriasGastos();
            cargarMetodosPago();
            cargarMetas();

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar categorías: " + ex.getMessage());
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        JPpanelMenu = new javax.swing.JPanel();
        btnInicio = new javax.swing.JButton();
        btnIngreso = new javax.swing.JButton();
        btnGastos = new javax.swing.JButton();
        btnAnalisisFinanzas = new javax.swing.JButton();
        btnMovimiento = new javax.swing.JButton();
        btnMetas = new javax.swing.JButton();
        btnExportarDatos = new javax.swing.JButton();
        btnCajachica = new javax.swing.JButton();
        JPpanelcontenido = new javax.swing.JPanel();
        PanelIngreso = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtingresocantidad = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtingresodescripcion = new javax.swing.JTextField();
        cbxingrsocategoria = new javax.swing.JComboBox<>();
        btningresoguardar = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblIngreso = new javax.swing.JTable();
        btnAgregar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        cbxMetodoPagoIngreso = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        PanelGastos = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtGastoMonto = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtGastoDescripcion = new javax.swing.JTextField();
        cbxGastoCategoria = new javax.swing.JComboBox<>();
        btnGastoGuardar = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblGastos = new javax.swing.JTable();
        btnGastoAgrCategoria = new javax.swing.JButton();
        btnGastoEliminar = new javax.swing.JButton();
        jLabel26 = new javax.swing.JLabel();
        txtGastoTotal = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        cbxMetodoPagoGasto = new javax.swing.JComboBox<>();
        PanelAnalisisFinanzas = new javax.swing.JPanel();
        PanelMovimientos = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMovimientos = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jdcFecha1 = new com.toedter.calendar.JDateChooser();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jdcFecha2 = new com.toedter.calendar.JDateChooser();
        cbxTipo = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        cbxCategoria = new javax.swing.JComboBox<>();
        btnBuscar = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        PanelMetas = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblMetas = new javax.swing.JTable();
        txtNombreMeta = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        txtObjetivoMeta = new javax.swing.JTextField();
        jProgressBar1 = new javax.swing.JProgressBar();
        jLabel20 = new javax.swing.JLabel();
        jdcFechaMeta = new com.toedter.calendar.JDateChooser();
        jLabel21 = new javax.swing.JLabel();
        txtPorcentajedestinadoMeta = new javax.swing.JTextField();
        btnMeta = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        PanelExportarDatos = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox<>();
        jLabel23 = new javax.swing.JLabel();
        jDateChooser4 = new com.toedter.calendar.JDateChooser();
        jLabel24 = new javax.swing.JLabel();
        jDateChooser5 = new com.toedter.calendar.JDateChooser();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        Panelnicio = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        lblNombre = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        btncomenzar = new javax.swing.JButton();
        PanelCajachica = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        JPpanelMenu.setLayout(new java.awt.GridLayout(0, 1));

        btnInicio.setText("Inicio");
        btnInicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInicioActionPerformed(evt);
            }
        });
        JPpanelMenu.add(btnInicio);

        btnIngreso.setText("Ingresos");
        btnIngreso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIngresoActionPerformed(evt);
            }
        });
        JPpanelMenu.add(btnIngreso);

        btnGastos.setText("Gastos");
        btnGastos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGastosActionPerformed(evt);
            }
        });
        JPpanelMenu.add(btnGastos);

        btnAnalisisFinanzas.setText("Análisis de finanzas");
        btnAnalisisFinanzas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnalisisFinanzasActionPerformed(evt);
            }
        });
        JPpanelMenu.add(btnAnalisisFinanzas);

        btnMovimiento.setText("Movimientos");
        btnMovimiento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMovimientoActionPerformed(evt);
            }
        });
        JPpanelMenu.add(btnMovimiento);

        btnMetas.setText("Metas");
        JPpanelMenu.add(btnMetas);

        btnExportarDatos.setText("Exportar datos");
        btnExportarDatos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportarDatosActionPerformed(evt);
            }
        });
        JPpanelMenu.add(btnExportarDatos);

        btnCajachica.setText("Caja ");
        JPpanelMenu.add(btnCajachica);

        JPpanelcontenido.setLayout(new java.awt.CardLayout());

        PanelIngreso.setBackground(new java.awt.Color(204, 153, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 0, 36)); // NOI18N
        jLabel1.setText("Nuevo Ingreso");

        jLabel2.setText("Monto:");

        txtingresocantidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtingresocantidadActionPerformed(evt);
            }
        });

        jLabel4.setText("Categoria:");

        jLabel5.setText("Descripción:");

        txtingresodescripcion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtingresodescripcionActionPerformed(evt);
            }
        });

        cbxingrsocategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxingrsocategoriaActionPerformed(evt);
            }
        });

        btningresoguardar.setText("Guardar");
        btningresoguardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btningresoguardarActionPerformed(evt);
            }
        });

        tblIngreso.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Categoria", "Método de Pago", "Descripción", "Monto"
            }
        ));
        jScrollPane3.setViewportView(tblIngreso);

        btnAgregar.setText("Agregar Categoria");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        jLabel3.setText("Total:");

        jLabel8.setText("Método de pago:");

        javax.swing.GroupLayout PanelIngresoLayout = new javax.swing.GroupLayout(PanelIngreso);
        PanelIngreso.setLayout(PanelIngresoLayout);
        PanelIngresoLayout.setHorizontalGroup(
            PanelIngresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelIngresoLayout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(PanelIngresoLayout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addComponent(btnAgregar)
                .addGap(57, 57, 57)
                .addComponent(btningresoguardar, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56)
                .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(PanelIngresoLayout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(PanelIngresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtingresocantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(PanelIngresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel2)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtingresodescripcion, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
                        .addComponent(cbxMetodoPagoIngreso, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cbxingrsocategoria, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(PanelIngresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelIngresoLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PanelIngresoLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 595, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(100, 100, 100))
        );
        PanelIngresoLayout.setVerticalGroup(
            PanelIngresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelIngresoLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jLabel1)
                .addGap(51, 51, 51)
                .addGroup(PanelIngresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelIngresoLayout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(PanelIngresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)))
                    .addGroup(PanelIngresoLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtingresocantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbxMetodoPagoIngreso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbxingrsocategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(txtingresodescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(68, 68, 68)
                .addGroup(PanelIngresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btningresoguardar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(121, Short.MAX_VALUE))
        );

        JPpanelcontenido.add(PanelIngreso, "card3");

        PanelGastos.setBackground(new java.awt.Color(204, 204, 255));

        jLabel6.setFont(new java.awt.Font("Segoe UI Black", 0, 36)); // NOI18N
        jLabel6.setText("Nuevo Gasto");

        jLabel7.setText("Monto:");

        jLabel9.setText("Categoria:");

        jLabel10.setText("Descripción:");

        btnGastoGuardar.setText("Guardar");
        btnGastoGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGastoGuardarActionPerformed(evt);
            }
        });

        tblGastos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Categoria", "Método de pago", "Descripción", "Monto"
            }
        ));
        jScrollPane4.setViewportView(tblGastos);

        btnGastoAgrCategoria.setText("Agregar Categoria");
        btnGastoAgrCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGastoAgrCategoriaActionPerformed(evt);
            }
        });

        btnGastoEliminar.setText("Eliminar");
        btnGastoEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGastoEliminarActionPerformed(evt);
            }
        });

        jLabel26.setText("Total:");

        jLabel29.setText("Método de pago:");

        javax.swing.GroupLayout PanelGastosLayout = new javax.swing.GroupLayout(PanelGastos);
        PanelGastos.setLayout(PanelGastosLayout);
        PanelGastosLayout.setHorizontalGroup(
            PanelGastosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelGastosLayout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(PanelGastosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelGastosLayout.createSequentialGroup()
                        .addGroup(PanelGastosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addGroup(PanelGastosLayout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addComponent(btnGastoAgrCategoria)
                                .addGap(38, 38, 38)
                                .addComponent(btnGastoGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(40, 40, 40)
                                .addComponent(btnGastoEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(386, Short.MAX_VALUE))
                    .addGroup(PanelGastosLayout.createSequentialGroup()
                        .addGroup(PanelGastosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtGastoDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxGastoCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtGastoMonto, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxMetodoPagoGasto, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(460, 460, 460)
                        .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtGastoTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 112, Short.MAX_VALUE))
                    .addGroup(PanelGastosLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 556, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(81, 81, 81))))
        );
        PanelGastosLayout.setVerticalGroup(
            PanelGastosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelGastosLayout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addComponent(jLabel6)
                .addGap(29, 29, 29)
                .addGroup(PanelGastosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelGastosLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtGastoMonto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel29)
                        .addGap(12, 12, 12)
                        .addComponent(cbxMetodoPagoGasto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbxGastoCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel10)
                        .addGap(18, 18, 18)
                        .addComponent(txtGastoDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PanelGastosLayout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addGroup(PanelGastosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel26)
                            .addComponent(txtGastoTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(36, 36, 36)
                .addGroup(PanelGastosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGastoAgrCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGastoGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGastoEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(166, Short.MAX_VALUE))
        );

        JPpanelcontenido.add(PanelGastos, "card4");

        PanelAnalisisFinanzas.setBackground(new java.awt.Color(204, 204, 255));

        javax.swing.GroupLayout PanelAnalisisFinanzasLayout = new javax.swing.GroupLayout(PanelAnalisisFinanzas);
        PanelAnalisisFinanzas.setLayout(PanelAnalisisFinanzasLayout);
        PanelAnalisisFinanzasLayout.setHorizontalGroup(
            PanelAnalisisFinanzasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1000, Short.MAX_VALUE)
        );
        PanelAnalisisFinanzasLayout.setVerticalGroup(
            PanelAnalisisFinanzasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 716, Short.MAX_VALUE)
        );

        JPpanelcontenido.add(PanelAnalisisFinanzas, "card5");

        PanelMovimientos.setBackground(new java.awt.Color(204, 204, 255));

        jLabel11.setFont(new java.awt.Font("Segoe UI Black", 0, 36)); // NOI18N
        jLabel11.setText("Movimientos");

        tblMovimientos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Fecha", "Tipo", "Categoria", "Descripcion", "Monto"
            }
        ));
        jScrollPane1.setViewportView(tblMovimientos);

        jLabel12.setText("Filtrar:");

        jLabel13.setText("a");

        cbxTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todos", "Ingresos", "Egresos", " " }));
        cbxTipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxTipoActionPerformed(evt);
            }
        });

        jLabel14.setText("Tipo movimiento:");

        jLabel15.setText("Por Fecha:");

        jLabel16.setText("Tipo categoria");

        cbxCategoria.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todas" }));

        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        jButton5.setText("Limpiar Filtros");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(636, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbxTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jdcFecha1, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbxCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel13)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jdcFecha2, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(208, 208, 208))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(68, 68, 68)
                                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(btnBuscar)
                                .addGap(62, 62, 62)))
                        .addGap(6, 6, 6))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel15)
                        .addGap(7, 7, 7)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jdcFecha2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jdcFecha1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(15, 15, 15)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbxTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(32, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout PanelMovimientosLayout = new javax.swing.GroupLayout(PanelMovimientos);
        PanelMovimientos.setLayout(PanelMovimientosLayout);
        PanelMovimientosLayout.setHorizontalGroup(
            PanelMovimientosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelMovimientosLayout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(jLabel11)
                .addContainerGap(706, Short.MAX_VALUE))
            .addGroup(PanelMovimientosLayout.createSequentialGroup()
                .addGap(140, 140, 140)
                .addGroup(PanelMovimientosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 685, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        PanelMovimientosLayout.setVerticalGroup(
            PanelMovimientosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelMovimientosLayout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(jLabel11)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );

        JPpanelcontenido.add(PanelMovimientos, "card6");

        jLabel17.setText("Metas:");

        tblMetas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Nombre", "Obetivo", "Progreso", "%Importe de ingresos"
            }
        ));
        jScrollPane2.setViewportView(tblMetas);

        txtNombreMeta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreMetaActionPerformed(evt);
            }
        });

        jLabel18.setText("Nombre:");

        jLabel19.setText("Objetivo:");

        txtObjetivoMeta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtObjetivoMetaActionPerformed(evt);
            }
        });

        jLabel20.setText("Fecha limite meta:");

        jLabel21.setText("% destinado:");

        txtPorcentajedestinadoMeta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPorcentajedestinadoMetaActionPerformed(evt);
            }
        });

        btnMeta.setText("Crear");
        btnMeta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMetaActionPerformed(evt);
            }
        });

        jButton8.setText("Eliminar");

        javax.swing.GroupLayout PanelMetasLayout = new javax.swing.GroupLayout(PanelMetas);
        PanelMetas.setLayout(PanelMetasLayout);
        PanelMetasLayout.setHorizontalGroup(
            PanelMetasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelMetasLayout.createSequentialGroup()
                .addGroup(PanelMetasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelMetasLayout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(PanelMetasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 576, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(PanelMetasLayout.createSequentialGroup()
                                .addGroup(PanelMetasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(PanelMetasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(PanelMetasLayout.createSequentialGroup()
                                            .addGap(6, 6, 6)
                                            .addComponent(jLabel18)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(txtNombreMeta, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(PanelMetasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(PanelMetasLayout.createSequentialGroup()
                                            .addComponent(jLabel20)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jdcFechaMeta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(PanelMetasLayout.createSequentialGroup()
                                            .addComponent(jLabel19)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(txtObjetivoMeta, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(42, 42, 42)
                                .addGroup(PanelMetasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnMeta)
                                    .addComponent(jButton8))
                                .addGap(249, 249, 249))))
                    .addGroup(PanelMetasLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(PanelMetasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PanelMetasLayout.createSequentialGroup()
                                .addComponent(jLabel21)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtPorcentajedestinadoMeta, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 576, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(405, Short.MAX_VALUE))
        );
        PanelMetasLayout.setVerticalGroup(
            PanelMetasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelMetasLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel17)
                .addGap(6, 6, 6)
                .addGroup(PanelMetasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(txtNombreMeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMeta))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelMetasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(txtObjetivoMeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelMetasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel20)
                    .addComponent(jdcFechaMeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelMetasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPorcentajedestinadoMeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(55, Short.MAX_VALUE))
        );

        JPpanelcontenido.add(PanelMetas, "card7");

        jLabel22.setText("Exportar datos:");

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Opciones", "Ingresos", "gatos ", "movimientos" }));

        jLabel23.setText("Desde fecha:");

        jLabel24.setText("a");

        jButton9.setText("Exportar a PDF");

        jButton10.setText("Exportar a xlsx");

        javax.swing.GroupLayout PanelExportarDatosLayout = new javax.swing.GroupLayout(PanelExportarDatos);
        PanelExportarDatos.setLayout(PanelExportarDatosLayout);
        PanelExportarDatosLayout.setHorizontalGroup(
            PanelExportarDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelExportarDatosLayout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addGroup(PanelExportarDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel23)
                    .addComponent(jLabel22))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(PanelExportarDatosLayout.createSequentialGroup()
                .addGroup(PanelExportarDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelExportarDatosLayout.createSequentialGroup()
                        .addGap(177, 177, 177)
                        .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PanelExportarDatosLayout.createSequentialGroup()
                        .addGap(91, 91, 91)
                        .addComponent(jDateChooser4, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(jLabel24)
                        .addGap(18, 18, 18)
                        .addComponent(jDateChooser5, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PanelExportarDatosLayout.createSequentialGroup()
                        .addGap(202, 202, 202)
                        .addGroup(PanelExportarDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton9, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE))))
                .addContainerGap(573, Short.MAX_VALUE))
        );
        PanelExportarDatosLayout.setVerticalGroup(
            PanelExportarDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelExportarDatosLayout.createSequentialGroup()
                .addGroup(PanelExportarDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelExportarDatosLayout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addComponent(jLabel22)
                        .addGap(70, 70, 70)
                        .addComponent(jLabel23))
                    .addGroup(PanelExportarDatosLayout.createSequentialGroup()
                        .addGap(90, 90, 90)
                        .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(PanelExportarDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelExportarDatosLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jDateChooser4, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PanelExportarDatosLayout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(jLabel24))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelExportarDatosLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jDateChooser5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(70, 70, 70)
                .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(319, Short.MAX_VALUE))
        );

        JPpanelcontenido.add(PanelExportarDatos, "card8");

        Panelnicio.setBackground(new java.awt.Color(204, 204, 255));

        jLabel25.setFont(new java.awt.Font("Segoe UI", 0, 48)); // NOI18N
        jLabel25.setText("Bienvenido:");

        lblNombre.setText("\"Nombre \"");

        jLabel27.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel27.setText("Gestiona tus finanzas con financio ");

        jLabel28.setText("Controla los ingresos y gatos de manera sencilla");

        btncomenzar.setText("Comenzar");

        javax.swing.GroupLayout PanelnicioLayout = new javax.swing.GroupLayout(Panelnicio);
        Panelnicio.setLayout(PanelnicioLayout);
        PanelnicioLayout.setHorizontalGroup(
            PanelnicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelnicioLayout.createSequentialGroup()
                .addGroup(PanelnicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelnicioLayout.createSequentialGroup()
                        .addGap(103, 103, 103)
                        .addGroup(PanelnicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PanelnicioLayout.createSequentialGroup()
                                .addComponent(jLabel25)
                                .addGap(18, 18, 18)
                                .addComponent(lblNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(PanelnicioLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 603, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(PanelnicioLayout.createSequentialGroup()
                        .addGap(248, 248, 248)
                        .addComponent(btncomenzar)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PanelnicioLayout.setVerticalGroup(
            PanelnicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelnicioLayout.createSequentialGroup()
                .addGroup(PanelnicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelnicioLayout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addComponent(jLabel25))
                    .addGroup(PanelnicioLayout.createSequentialGroup()
                        .addGap(87, 87, 87)
                        .addComponent(lblNombre)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jLabel28)
                .addGap(81, 81, 81)
                .addComponent(btncomenzar)
                .addContainerGap(311, Short.MAX_VALUE))
        );

        JPpanelcontenido.add(Panelnicio, "card2");

        javax.swing.GroupLayout PanelCajachicaLayout = new javax.swing.GroupLayout(PanelCajachica);
        PanelCajachica.setLayout(PanelCajachicaLayout);
        PanelCajachicaLayout.setHorizontalGroup(
            PanelCajachicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1000, Short.MAX_VALUE)
        );
        PanelCajachicaLayout.setVerticalGroup(
            PanelCajachicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 716, Short.MAX_VALUE)
        );

        JPpanelcontenido.add(PanelCajachica, "card9");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(JPpanelMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JPpanelcontenido, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(JPpanelMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(JPpanelcontenido, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnIngresoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIngresoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnIngresoActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        limpiarFiltros();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void txtNombreMetaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreMetaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreMetaActionPerformed

    private void txtObjetivoMetaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtObjetivoMetaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtObjetivoMetaActionPerformed

    private void txtPorcentajedestinadoMetaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPorcentajedestinadoMetaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPorcentajedestinadoMetaActionPerformed

    private void btnMetaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMetaActionPerformed
        try {
            String nombre = txtNombreMeta.getText();
            double objetivo = Double.parseDouble(txtObjetivoMeta.getText());
            double porcentaje = Double.parseDouble(txtPorcentajedestinadoMeta.getText());

            // Validar fecha
            Date fechaSeleccionada = jdcFechaMeta.getDate();
            if (fechaSeleccionada == null) {
                JOptionPane.showMessageDialog(this, "⚠️ Selecciona una fecha válida.");
                return;
            }

            // Convertir java.util.Date → LocalDate
            LocalDate fechaLimite = fechaSeleccionada.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

            // Crear objeto Meta
            Meta meta = new Meta();
            meta.setIdUsuario(usuario.getId()); // ID del usuario logueado
            meta.setNombre(nombre);
            meta.setMontoObjetivo(objetivo);
            meta.setAcumulado(0.0); // al inicio siempre 0
            meta.setPorcentaje(porcentaje);
            meta.setFechaLimite(fechaLimite);
            meta.setActiva(true);

            // Registrar en Mongo
            MetaDAOMongo daoMeta = new MetaDAOMongo();
            daoMeta.registrar(meta);

            JOptionPane.showMessageDialog(this, "✅ Meta registrada correctamente.");

            // Recargar tabla
            cargarMetas();

        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "⚠️ Ingresa números válidos en Objetivo y Porcentaje.");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "⚠️ Error al registrar la meta: " + ex.getMessage());
        }

    }//GEN-LAST:event_btnMetaActionPerformed

    private void btnGastosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGastosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnGastosActionPerformed

    private void btnInicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInicioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnInicioActionPerformed

    private void btnExportarDatosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportarDatosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnExportarDatosActionPerformed

    private void btnMovimientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMovimientoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnMovimientoActionPerformed

    private void btnAnalisisFinanzasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnalisisFinanzasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAnalisisFinanzasActionPerformed

    private void txtingresocantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtingresocantidadActionPerformed

    }//GEN-LAST:event_txtingresocantidadActionPerformed

    private void cbxingrsocategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxingrsocategoriaActionPerformed

    }//GEN-LAST:event_cbxingrsocategoriaActionPerformed

    private void txtingresodescripcionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtingresodescripcionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtingresodescripcionActionPerformed

    private void btningresoguardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btningresoguardarActionPerformed

        try {
            // 1️⃣ Leer datos del ingreso
            BigDecimal monto = new BigDecimal(txtingresocantidad.getText().trim());
            String descripcion = txtingresodescripcion.getText().trim();
            Metodopago metodoPagoSeleccionado = (Metodopago) cbxMetodoPagoIngreso.getSelectedItem();
            Categoria seleccionada = (Categoria) cbxingrsocategoria.getSelectedItem();

            if (seleccionada == null || seleccionada.getId() <= 0) {
                JOptionPane.showMessageDialog(this, "⚠️ Selecciona una categoría válida.");
                return;
            }
            if (metodoPagoSeleccionado == null) {
                JOptionPane.showMessageDialog(this, "⚠️ Selecciona un método de pago válido.");
                return;
            }

            // 2️⃣ Registrar ingreso en PostgreSQL
            Movimiento m = new Movimiento();
            m.setIdUsuario(usuario.getId());
            m.setIdCategoria(seleccionada.getId());
            m.setMonto(monto);
            m.setCategoria(seleccionada.getNombre());
            m.setDescripcion(descripcion);
            m.setIdMetodoPago(metodoPagoSeleccionado.getId());

            MovimientoDaoImpl daoMov = new MovimientoDaoImpl();
            int idGenerado = daoMov.registrarmovimiento(m);

            if (idGenerado > 0) {
                JOptionPane.showMessageDialog(this, "✅ Ingreso registrado con ID: " + idGenerado);
                txtingresocantidad.setText("");
                txtingresodescripcion.setText("");

                // 3️⃣ Actualizar metas en Mongo según porcentaje destinado
                MetaDAOMongo daoMeta = new MetaDAOMongo();
                List<Meta> metas = daoMeta.listarActivasPorUsuario(usuario.getId());

                for (Meta meta : metas) {
                    // Calculas el aporte aquí
                    BigDecimal aporte = monto.multiply(BigDecimal.valueOf(meta.getPorcentaje())
                            .divide(BigDecimal.valueOf(100)));
                    daoMeta.actualizarAcumulado(meta.getIdMeta(), aporte);
                    daoMeta.desactivarSiCumplida(meta.getIdMeta());
                }

// luego recargas metas para actualizar la tabla
                cargarMetas();
                cargarIngresos();

            } else {
                JOptionPane.showMessageDialog(this, "❌ No se pudo registrar el ingreso");
            }

        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "⚠️ Ingresa un monto válido.");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "⚠️ Error: " + ex.getMessage());
        }

    }//GEN-LAST:event_btningresoguardarActionPerformed

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        String nuevaCat = JOptionPane.showInputDialog(this, "Nombre de la nueva Categoria:");
        if (nuevaCat != null && !nuevaCat.trim().isEmpty()) {
            try {
                CategoriaDaoImpl daoCat = new CategoriaDaoImpl();
                int idNueva = daoCat.registrar(new Categoria(0, usuario.getId(), nuevaCat, "INGRESO"));

                //refrescamos
                cargarCategorias();

                //seleccionamos automaticamente la nueva categoria creada
                for (int i = 0; i < cbxingrsocategoria.getItemCount(); i++) {
                    Categoria c = (Categoria) cbxingrsocategoria.getItemAt(i);
                    if (c.getId() == idNueva) {
                        cbxingrsocategoria.setSelectedIndex(i);
                        break;
                    }
                }
                JOptionPane.showMessageDialog(this, "Categoria creada: " + nuevaCat);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al crear categoria: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "No ingresaste un nombre valido.");
        }
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed

        int fila = tblIngreso.getSelectedRow();

        if (fila >= 0) {
            // ID está en la primera columna
            int id = Integer.parseInt(tblIngreso.getValueAt(fila, 0).toString());

            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "¿Seguro que deseas eliminar el ingreso con ID " + id + "?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                try {

                    int filasEliminadas = daoMov.eliminarMovimiento(id);

                    if (filasEliminadas > 0) {
                        JOptionPane.showMessageDialog(this, "✅ Ingreso eliminado correctamente.");
                        cargarIngresos(); // 👈 refresca toda la tabla
                    } else {
                        JOptionPane.showMessageDialog(this, "⚠️ No se encontró el ingreso con ID: " + id);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "⚠️ Error al eliminar: " + ex.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "⚠️ Selecciona un ingreso primero.");
        }


    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnGastoAgrCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGastoAgrCategoriaActionPerformed
        String nuevaCat = JOptionPane.showInputDialog(this, "Nombre de la nueva Categoria:");
        if (nuevaCat != null && !nuevaCat.trim().isEmpty()) {
            try {
                CategoriaDaoImpl daoCat = new CategoriaDaoImpl();
                int idNueva = daoCat.registrar(new Categoria(0, usuario.getId(), nuevaCat, "GASTO"));

                //refrescamos
                cargarCategorias();

                //seleccionamos automaticamente la nueva categoria creada
                for (int i = 0; i < cbxGastoCategoria.getItemCount(); i++) {
                    Categoria c = (Categoria) cbxGastoCategoria.getItemAt(i);
                    if (c.getId() == idNueva) {
                        cbxGastoCategoria.setSelectedIndex(i);
                        break;
                    }
                }
                JOptionPane.showMessageDialog(this, "Categoria creada: " + nuevaCat);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al crear categoria: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "No ingresaste un nombre valido.");
        }
    }//GEN-LAST:event_btnGastoAgrCategoriaActionPerformed

    private void btnGastoEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGastoEliminarActionPerformed
        int fila = tblGastos.getSelectedRow();

        if (fila >= 0) {
            // ID está en la primera columna
            int id = Integer.parseInt(tblGastos.getValueAt(fila, 0).toString());

            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "¿Seguro que deseas eliminar el ingreso con ID " + id + "?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                try {

                    int filasEliminadas = daoMov.eliminarMovimiento(id);

                    if (filasEliminadas > 0) {
                        JOptionPane.showMessageDialog(this, "✅ Ingreso eliminado correctamente.");
                        cargarGastos(); // 👈 refresca toda la tabla
                    } else {
                        JOptionPane.showMessageDialog(this, "⚠️ No se encontró el ingreso con ID: " + id);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "⚠️ Error al eliminar: " + ex.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "⚠️ Selecciona un ingreso primero.");
        }

    }//GEN-LAST:event_btnGastoEliminarActionPerformed

    private void btnGastoGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGastoGuardarActionPerformed
        try {
            BigDecimal monto = new BigDecimal(txtGastoMonto.getText().trim());
            String descripcion = txtGastoDescripcion.getText().trim();
            Metodopago metodoPagoSeleccionado = (Metodopago) cbxMetodoPagoGasto.getSelectedItem();

            // Obtenemos la categoría seleccionada
            Categoria seleccionada = (Categoria) cbxGastoCategoria.getSelectedItem();

            if (seleccionada == null || seleccionada.getId() <= 0) {
                JOptionPane.showMessageDialog(this, "⚠️ Selecciona una categoría válida.");
                return;
            }

            if (metodoPagoSeleccionado == null) {
                JOptionPane.showMessageDialog(this, "Selecciona un método de pago valido.");
                return;

            }

            // Registramos el gasto
            Movimiento m = new Movimiento();
            m.setIdUsuario(usuario.getId());
            m.setIdCategoria(seleccionada.getId());
            m.setMonto(monto);
            m.setCategoria(seleccionada.getNombre());
            m.setDescripcion(descripcion);
            m.setIdMetodoPago(metodoPagoSeleccionado.getId());

            MovimientoDaoImpl daoMov = new MovimientoDaoImpl();
            int idGenerado = daoMov.registrarmovimiento(m);

            if (idGenerado > 0) {
                JOptionPane.showMessageDialog(this, "✅ Ingreso registrado con ID: " + idGenerado);
                txtGastoMonto.setText("");
                txtGastoDescripcion.setText("");

                //recargar la tabla desde la BD
                cargarGastos();
            } else {
                JOptionPane.showMessageDialog(this, "❌ No se pudo registrar el ingreso");
            }

        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "⚠️ Monto no válido.");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "⚠️ Error: " + ex.getMessage());
        }


    }//GEN-LAST:event_btnGastoGuardarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        cargarMovimientos();
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void cbxTipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxTipoActionPerformed

    }//GEN-LAST:event_cbxTipoActionPerformed

    private void cargarMetodosPagoPorDefecto(JComboBox<Metodopago> combo, String[] opciones) {
        try {
            MetodoPagoDaoImpl dao = new MetodoPagoDaoImpl();

            // Insertar métodos por defecto si no existen
            for (String tipo : opciones) {
                if (!dao.existeMetodopago(usuario.getId(), tipo)) {
                    dao.registrar(new Metodopago(0, usuario.getId(), tipo));
                }
            }

            // Limpiar combo y cargar desde la BD
            combo.removeAllItems();
            List<Metodopago> metodos = dao.listarPorUsuario(usuario.getId());
            for (Metodopago mp : metodos) {
                combo.addItem(mp);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "⚠ Error al cargar métodos de pago: " + ex.getMessage());
        }
    }

    private void cargarMetodosPago() {
        String[] opcionesMetodos = {
            "Efectivo",
            "Transferencia bancaria",
            "Depósito",
            "Tarjeta de crédito",
            "Tarjeta de débito",
            "Yape / Plin",
            "Otro"
        };
        cargarMetodosPagoPorDefecto(cbxMetodoPagoIngreso, opcionesMetodos);
        cargarMetodosPagoPorDefecto(cbxMetodoPagoGasto, opcionesMetodos);

    }

    private void cargarCategoriasPorDefecto(JComboBox<Categoria> combo, String tipo, String[] opciones) {
        try {
            CategoriaDaoImpl dao = new CategoriaDaoImpl();

            // Insertar categorías por defecto si no existen
            for (String nombre : opciones) {
                if (!dao.existeCategoria(usuario.getId(), nombre, tipo)) {
                    dao.registrar(new Categoria(0, usuario.getId(), nombre, tipo));
                }
            }

            // Limpiar combo y cargar desde la BD
            combo.removeAllItems();
            List<Categoria> categorias = dao.listarPorUsuario(usuario.getId(), tipo);
            for (Categoria c : categorias) {
                combo.addItem(c);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "⚠ Error al cargar categorías de " + tipo + ": " + ex.getMessage());
        }
    }

    private void cargarCategoriasIngresos() {
        String[] opcionesIngresos = {
            "Ventas en tienda",
            "Ventas online",
            "Ventas por redes sociales",
            "Ventas al por mayor",
            "Ingresos por personalización",
            "Otros ingresos"
        };
        cargarCategoriasPorDefecto(cbxingrsocategoria, "INGRESO", opcionesIngresos);
    }

    private void cargarCategoriasGastos() {
        String[] opcionesGastos = {
            "Alquiler",
            "Servicios básicos",
            "Publicidad y marketing",
            "Sueldos y salarios",
            "Insumos y materiales",
            "Transporte",
            "Otros gastos"
        };
        cargarCategoriasPorDefecto(cbxGastoCategoria, "GASTO", opcionesGastos);
    }

    private void cargarIngresos() {
        try {
            //obtener el modelo de la tabla
            DefaultTableModel modelo = (DefaultTableModel) tblIngreso.getModel();
            modelo.setRowCount(0); //limpiar filas previas

            //obtener lista desde la BD
            List<Movimiento> lista = daoMov.listarIngresosPorUsuario(usuario.getId());

            // acumulador del total
            BigDecimal total = BigDecimal.ZERO;

            //agregar cada ingreso a la tabla
            for (Movimiento m : lista) {
                modelo.addRow(new Object[]{
                    m.getId(),
                    m.getCategoria(),
                    m.getNombreMetodoPago(),
                    m.getDescripcion(),
                    m.getMonto()
                });

                // Acumular el monto
                total = total.add(m.getMonto());

            }

            // Mostrar en el textfield
            txtTotal.setText(total.toString());

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "⚠ Error al cargar ingresos: " + ex.getMessage());
        }
    }

    private void cargarGastos() {
        try {
            //obtener el modelo de la tabla
            DefaultTableModel modelo = (DefaultTableModel) tblGastos.getModel();
            modelo.setRowCount(0); //limpiar filas previas

            //obtener lista desde la BD
            List<Movimiento> lista = daoMov.listarGastosPorUsuario(usuario.getId());

            //acumulador del total 
            BigDecimal total = BigDecimal.ZERO;

            //agregar cada ingreso a la tabla
            for (Movimiento m : lista) {
                modelo.addRow(new Object[]{
                    m.getId(),
                    m.getCategoria(),
                    m.getNombreMetodoPago(),
                    m.getDescripcion(),
                    m.getMonto()
                });

                //acumular el monto
                total = total.add(m.getMonto());
            }

            txtGastoTotal.setText(total.toString());

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "⚠ Error al cargar ingresos: " + ex.getMessage());
        }
    }

    private void cargarMovimientos() {
        try {
            DefaultTableModel modelo = (DefaultTableModel) tblMovimientos.getModel();
            modelo.setRowCount(0); // limpiar filas previas

            // Obtenemos valores seleccionados de los combos
            String opcionTipo = (String) cbxTipo.getSelectedItem();
            String opcionCategoria = (String) cbxCategoria.getSelectedItem();

            List<Movimiento> lista;

            // Filtrado por tipo
            if ("Ingresos".equals(opcionTipo)) {
                lista = movimientoService.listarIngresosPorUsuario(usuario.getId());
            } else if ("Egresos".equals(opcionTipo)) {
                lista = movimientoService.listarGastosPorUsuario(usuario.getId());
            } else {
                lista = movimientoService.listarPorUsuario(usuario.getId()); // Todos
            }

            // Filtrado adicional por categoría
            if (opcionCategoria != null && !"Todas".equals(opcionCategoria)) {
                lista = lista.stream()
                        .filter(m -> opcionCategoria.equalsIgnoreCase(m.getCategoria()))
                        .toList();
            }

            // Filtro por rango de fechas
            java.util.Date fechaDesde = jdcFecha1.getDate();
            java.util.Date fechaHasta = jdcFecha2.getDate();

            if (fechaDesde != null && fechaHasta != null) {
                lista = lista.stream()
                        .filter(m -> {
                            java.util.Date fechaMovimiento = new java.util.Date(m.getFecha().getTime());
                            return !fechaMovimiento.before(fechaDesde) && !fechaMovimiento.after(fechaHasta);
                        })
                        .toList();
            }

            // Formato de fecha sin segundos
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

            for (Movimiento m : lista) {
                String fechaFormateada = m.getFecha().toLocalDateTime().format(formatter);

                modelo.addRow(new Object[]{
                    fechaFormateada,
                    m.getTipo(),
                    m.getCategoria(),
                    m.getDescripcion(),
                    m.getMonto()
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "⚠ Error al cargar movimientos: " + ex.getMessage());
        }
    }

    private void limpiarFiltros() {
        // Resetear fechas
        jdcFecha1.setDate(null);
        jdcFecha2.setDate(null);

        // Resetear combos
        cbxTipo.setSelectedItem("Todos");
        cbxCategoria.setSelectedItem("Todas");

        // Volver a cargar la tabla con todos los movimientos
        cargarMovimientos();
    }

    private void cargarCategorias() throws SQLException {
        CategoriaDaoImpl dao = new CategoriaDaoImpl();
        List<Categoria> categoriasIngresos = dao.listarPorUsuario(usuario.getId(), "INGRESO");
        List<Categoria> categoriasGastos = dao.listarPorUsuario(usuario.getId(), "GASTO");

        cbxingrsocategoria.removeAllItems();
        cbxGastoCategoria.removeAllItems();

        // Llenar combos
        for (Categoria c : categoriasIngresos) {
            cbxingrsocategoria.addItem(c);
        }
        for (Categoria cg : categoriasGastos) {
            cbxGastoCategoria.addItem(cg);
        }

        // Depuración
        System.out.println("Categorías INGRESO en combo: " + categoriasIngresos.size());
        for (Categoria c : categoriasIngresos) {
            System.out.println(" -> " + c.getNombre());
        }

        System.out.println("Categorías GASTO en combo: " + categoriasGastos.size());
        for (Categoria cg : categoriasGastos) {
            System.out.println(" -> " + cg.getNombre());
        }
    }

    private void cargarCategoriasFiltro() throws SQLException {
        CategoriaDaoImpl dao = new CategoriaDaoImpl();

        cbxCategoria.removeAllItems();
        cbxCategoria.addItem("Todas"); // opción default

        String opcionTipo = (String) cbxTipo.getSelectedItem();

        List<Categoria> categorias;

        if ("Ingresos".equalsIgnoreCase(opcionTipo)) {
            categorias = dao.listarPorUsuario(usuario.getId(), "INGRESO");
        } else if ("Egresos".equalsIgnoreCase(opcionTipo)) {
            categorias = dao.listarPorUsuario(usuario.getId(), "GASTO");
        } else {
            categorias = new ArrayList<>();
        }

        for (Categoria c : categorias) {
            cbxCategoria.addItem(c.getNombre());
        }

    }

    private void cargarMetas() {
        try {
            MetaDAOMongo dao = new MetaDAOMongo();
            List<Meta> metas = dao.listarActivasPorUsuario(usuario.getId());

            DefaultTableModel modelo = (DefaultTableModel) tblMetas.getModel();
            modelo.setRowCount(0); // limpiar tabla
            barrasMetas.clear();   // limpiar referencias anteriores

            for (Meta m : metas) {
                // Barra de progreso por cada meta
                JProgressBar barra = new JProgressBar(0, 100);
                int progreso = m.getMontoObjetivo() > 0
                        ? (int) ((m.getAcumulado() / m.getMontoObjetivo()) * 100)
                        : 0;

                barra.setValue(progreso);
                barra.setStringPainted(true);
                barra.setString(progreso + "%");

                // Guardar referencia para futuras actualizaciones
                barrasMetas.put(m.getIdMeta(), barra);

                // Agregar fila con la barra
                modelo.addRow(new Object[]{
                    m.getNombre(),
                    m.getMontoObjetivo(),
                    m.getAcumulado(),
                    barra
                });
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar metas: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void inicializarTablaMetas() {
        // Configurar columnas
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.setColumnIdentifiers(new Object[]{"Nombre", "Objetivo", "Acumulado", "Progreso"});
        tblMetas.setModel(modelo);

        // Configurar renderer para la columna de progreso
        tblMetas.setDefaultRenderer(JProgressBar.class, new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus,
                    int row, int column) {
                return (JProgressBar) value;
            }
        });
    }

    public void actualizarProgresoMeta(int idMeta, double nuevoAcumulado, double montoObjetivo) {
        JProgressBar barra = barrasMetas.get(idMeta);
        if (barra != null) {
            int progreso = montoObjetivo > 0
                    ? (int) ((nuevoAcumulado / montoObjetivo) * 100)
                    : 0;
            barra.setValue(progreso);
            barra.setString(progreso + "%");
        }
    }

    //combo box pago
    /**/
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JframeInicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JframeInicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JframeInicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JframeInicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JframeLogin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel JPpanelMenu;
    private javax.swing.JPanel JPpanelcontenido;
    private javax.swing.JPanel PanelAnalisisFinanzas;
    private javax.swing.JPanel PanelCajachica;
    private javax.swing.JPanel PanelExportarDatos;
    private javax.swing.JPanel PanelGastos;
    private javax.swing.JPanel PanelIngreso;
    private javax.swing.JPanel PanelMetas;
    private javax.swing.JPanel PanelMovimientos;
    private javax.swing.JPanel Panelnicio;
    private javax.swing.JButton btnAgregar;
    public javax.swing.JButton btnAnalisisFinanzas;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCajachica;
    private javax.swing.JButton btnEliminar;
    public javax.swing.JButton btnExportarDatos;
    private javax.swing.JButton btnGastoAgrCategoria;
    private javax.swing.JButton btnGastoEliminar;
    private javax.swing.JButton btnGastoGuardar;
    public javax.swing.JButton btnGastos;
    public javax.swing.JButton btnIngreso;
    public javax.swing.JButton btnInicio;
    private javax.swing.JButton btnMeta;
    public javax.swing.JButton btnMetas;
    public javax.swing.JButton btnMovimiento;
    private javax.swing.JButton btncomenzar;
    private javax.swing.JButton btningresoguardar;
    private javax.swing.JComboBox<String> cbxCategoria;
    private javax.swing.JComboBox<Categoria> cbxGastoCategoria;
    private javax.swing.JComboBox<Metodopago> cbxMetodoPagoGasto;
    private javax.swing.JComboBox<Metodopago> cbxMetodoPagoIngreso;
    private javax.swing.JComboBox<String> cbxTipo;
    private javax.swing.JComboBox<Categoria> cbxingrsocategoria;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox<String> jComboBox4;
    private com.toedter.calendar.JDateChooser jDateChooser4;
    private com.toedter.calendar.JDateChooser jDateChooser5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private com.toedter.calendar.JDateChooser jdcFecha1;
    private com.toedter.calendar.JDateChooser jdcFecha2;
    private com.toedter.calendar.JDateChooser jdcFechaMeta;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JTable tblGastos;
    private javax.swing.JTable tblIngreso;
    private javax.swing.JTable tblMetas;
    private javax.swing.JTable tblMovimientos;
    private javax.swing.JTextField txtGastoDescripcion;
    private javax.swing.JTextField txtGastoMonto;
    private javax.swing.JTextField txtGastoTotal;
    private javax.swing.JTextField txtNombreMeta;
    private javax.swing.JTextField txtObjetivoMeta;
    private javax.swing.JTextField txtPorcentajedestinadoMeta;
    private javax.swing.JTextField txtTotal;
    private javax.swing.JTextField txtingresocantidad;
    private javax.swing.JTextField txtingresodescripcion;
    // End of variables declaration//GEN-END:variables
}
