/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package pe.edu.utp.financio.view;

import java.awt.CardLayout;
import java.awt.Component;
import java.io.FileOutputStream;
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
import pe.edu.utp.financio.dao.impl.UsuarioDAOPostgres;
import pe.edu.utp.financio.modelo.Categoria;
import pe.edu.utp.financio.modelo.Meta;
import pe.edu.utp.financio.modelo.Metodopago;
import pe.edu.utp.financio.modelo.Movimiento;
import pe.edu.utp.financio.modelo.Usuario;
import pe.edu.utp.financio.service.MovimientoService;
import pe.edu.utp.financio.service.impl.MovimientoServiceImpl;

//word
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

//excel
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import pe.edu.utp.financio.dao.impl.CajaDAOImpl;
import pe.edu.utp.financio.dao1.CajaDAO;
import pe.edu.utp.financio.modelo.Caja;



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

    // 🔹 Atributos
    private CajaDAO cajaDao = new CajaDAOImpl();
    private DefaultTableModel modeloCaja;

    public JframeInicio() {
        initComponents(); // inicializa tus campos, botones, tabla, etc.
        mostrarCajas(); // puedes llamar aquí para cargar al inicio
        jDateChooserResumen.addPropertyChangeListener("date", evt -> {
    txtresumen.setText(""); // limpia el resumen cada vez que cambias la fecha
});

    }

    // 🔹 Aquí va tu método mostrarCajas()
    private void mostrarCajas() {
        try {
            modeloCaja.setRowCount(0);
            List<Caja> cajas = cajaDao.listar();

            for (Caja c : cajas) {
                modeloCaja.addRow(new Object[]{
                    c.getIdCaja(),
                    c.getIdUsuario(),
                    c.getNombre(),
                    c.getFondo(),
                    c.getCierre(),
                    c.getFechaApertura(),
                    c.getFechaCierre()
                });
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al mostrar cajas: " + ex.getMessage());
        }
    }

    // 🔹 Otros métodos como limpiarCampos(), eventos de botones, etc.


    public JframeInicio(Usuario usuario) {
        initComponents();
        inicializarTablaMetas();
        txtTotal.setEditable(false);
       

        //mostramos el nombre en el label
        // Si el rol es ADMIN
        if ("ADMIN".equalsIgnoreCase(usuario.getRol())) {
            lblNombre.setText(" Administrador (" + usuario.getNombre() + ")");
        } else if ("EMPLEADO".equalsIgnoreCase(usuario.getRol())) {
            lblNombre.setText(" Empleado (" + usuario.getNombre() + ")");
        } else {
            // Por si en el futuro hay más roles
            lblNombre.setText(" " + usuario.getRol() + " (" + usuario.getNombre() + ")");
        }

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
        btnMovimiento = new javax.swing.JButton();
        btnCajachica = new javax.swing.JButton();
        btnAnalisisFinanzas = new javax.swing.JButton();
        btnMetas = new javax.swing.JButton();
        btnExportarDatos = new javax.swing.JButton();
        btncerrarsesion = new javax.swing.JButton();
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
        jLabel20 = new javax.swing.JLabel();
        jdcFechaMeta = new com.toedter.calendar.JDateChooser();
        jLabel21 = new javax.swing.JLabel();
        txtPorcentajedestinadoMeta = new javax.swing.JTextField();
        btnMeta = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        PanelExportarDatos = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        cbxExportarDatos = new javax.swing.JComboBox<>();
        jLabel23 = new javax.swing.JLabel();
        jdcInicio = new com.toedter.calendar.JDateChooser();
        jLabel24 = new javax.swing.JLabel();
        jdcFinal = new com.toedter.calendar.JDateChooser();
        btnExportarPDF = new javax.swing.JButton();
        btnImprimirExcel = new javax.swing.JButton();
        jLabel34 = new javax.swing.JLabel();
        Panelnicio = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        lblNombre = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        btncomenzar = new javax.swing.JButton();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        PanelCajachica = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        txtfondo = new javax.swing.JTextField();
        txtcierre = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        txtresumen = new javax.swing.JTextArea();
        btneliminar = new javax.swing.JButton();
        btnagregarfondo = new javax.swing.JButton();
        btnmostrar = new javax.swing.JButton();
        jDateChooserfondo = new com.toedter.calendar.JDateChooser();
        jDateChooserResumen = new com.toedter.calendar.JDateChooser();
        jLabel35 = new javax.swing.JLabel();
        txtid = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        txtIdUsuario = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        jLabel44 = new javax.swing.JLabel();
        jDateChoosercierre = new com.toedter.calendar.JDateChooser();
        btnagregarcierre = new javax.swing.JButton();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        txtIdCaja = new javax.swing.JTextField();
        jLabel47 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        JPpanelMenu.setLayout(new java.awt.GridLayout(0, 1));

        btnInicio.setBackground(new java.awt.Color(153, 255, 204));
        btnInicio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pe/edu/utp/financio/images/Inicio.png"))); // NOI18N
        btnInicio.setText("Inicio");
        btnInicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInicioActionPerformed(evt);
            }
        });
        JPpanelMenu.add(btnInicio);

        btnIngreso.setBackground(new java.awt.Color(153, 255, 204));
        btnIngreso.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pe/edu/utp/financio/images/flujo-de-ingresos.png"))); // NOI18N
        btnIngreso.setText("Ingresos");
        btnIngreso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIngresoActionPerformed(evt);
            }
        });
        JPpanelMenu.add(btnIngreso);

        btnGastos.setBackground(new java.awt.Color(153, 255, 204));
        btnGastos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pe/edu/utp/financio/images/costo.png"))); // NOI18N
        btnGastos.setText("Gastos");
        btnGastos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGastosActionPerformed(evt);
            }
        });
        JPpanelMenu.add(btnGastos);

        btnMovimiento.setBackground(new java.awt.Color(153, 255, 204));
        btnMovimiento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pe/edu/utp/financio/images/transferencia-de-dinero.png"))); // NOI18N
        btnMovimiento.setText("Movimientos");
        btnMovimiento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMovimientoActionPerformed(evt);
            }
        });
        JPpanelMenu.add(btnMovimiento);

        btnCajachica.setBackground(new java.awt.Color(153, 255, 204));
        btnCajachica.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pe/edu/utp/financio/images/caja-registradora.png"))); // NOI18N
        btnCajachica.setText("Caja ");
        btnCajachica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCajachicaActionPerformed(evt);
            }
        });
        JPpanelMenu.add(btnCajachica);

        btnAnalisisFinanzas.setBackground(new java.awt.Color(153, 255, 204));
        btnAnalisisFinanzas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pe/edu/utp/financio/images/analisis-de-mercado.png"))); // NOI18N
        btnAnalisisFinanzas.setText("Análisis de finanzas");
        btnAnalisisFinanzas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnalisisFinanzasActionPerformed(evt);
            }
        });
        JPpanelMenu.add(btnAnalisisFinanzas);

        btnMetas.setBackground(new java.awt.Color(153, 255, 204));
        btnMetas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pe/edu/utp/financio/images/objetivo.png"))); // NOI18N
        btnMetas.setText("Metas");
        JPpanelMenu.add(btnMetas);

        btnExportarDatos.setBackground(new java.awt.Color(153, 255, 204));
        btnExportarDatos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pe/edu/utp/financio/images/carpeta.png"))); // NOI18N
        btnExportarDatos.setText("Exportar datos");
        btnExportarDatos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportarDatosActionPerformed(evt);
            }
        });
        JPpanelMenu.add(btnExportarDatos);

        btncerrarsesion.setBackground(new java.awt.Color(153, 255, 204));
        btncerrarsesion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pe/edu/utp/financio/images/cerrar-con-llave.png"))); // NOI18N
        btncerrarsesion.setText("Cerrar Sesion");
        btncerrarsesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncerrarsesionActionPerformed(evt);
            }
        });
        JPpanelMenu.add(btncerrarsesion);

        JPpanelcontenido.setLayout(new java.awt.CardLayout());

        PanelIngreso.setBackground(new java.awt.Color(102, 153, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI Semibold", 1, 36)); // NOI18N
        jLabel1.setText("Nuevo Ingreso");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Monto:");

        txtingresocantidad.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtingresocantidad.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtingresocantidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtingresocantidadActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Categoria:");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Descripción:");

        txtingresodescripcion.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtingresodescripcion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtingresodescripcionActionPerformed(evt);
            }
        });

        cbxingrsocategoria.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        cbxingrsocategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxingrsocategoriaActionPerformed(evt);
            }
        });

        btningresoguardar.setBackground(new java.awt.Color(153, 255, 204));
        btningresoguardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pe/edu/utp/financio/images/Guardar.png"))); // NOI18N
        btningresoguardar.setText("Guardar");
        btningresoguardar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btningresoguardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btningresoguardarActionPerformed(evt);
            }
        });

        tblIngreso.setBackground(new java.awt.Color(204, 255, 255));
        tblIngreso.setFont(new java.awt.Font("Sitka Text", 1, 12)); // NOI18N
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

        btnAgregar.setBackground(new java.awt.Color(153, 255, 204));
        btnAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pe/edu/utp/financio/images/Crear.png"))); // NOI18N
        btnAgregar.setText("Agregar Categoria");
        btnAgregar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        btnEliminar.setBackground(new java.awt.Color(153, 255, 204));
        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pe/edu/utp/financio/images/Eliminar.png"))); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        cbxMetodoPagoIngreso.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel3.setFont(new java.awt.Font("Sitka Text", 1, 12)); // NOI18N
        jLabel3.setText("Total:");

        txtTotal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
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
                .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addComponent(btningresoguardar, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(PanelIngresoLayout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(PanelIngresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2)
                    .addComponent(txtingresodescripcion)
                    .addComponent(cbxingrsocategoria, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtingresocantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(cbxMetodoPagoIngreso, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addContainerGap(218, Short.MAX_VALUE))
        );

        JPpanelcontenido.add(PanelIngreso, "card3");

        PanelGastos.setBackground(new java.awt.Color(102, 153, 255));

        jLabel6.setFont(new java.awt.Font("Segoe UI Semibold", 1, 36)); // NOI18N
        jLabel6.setText("Nuevo Gasto");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setText("Monto:");

        txtGastoMonto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setText("Categoria:");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel10.setText("Descripción:");

        txtGastoDescripcion.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        cbxGastoCategoria.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btnGastoGuardar.setBackground(new java.awt.Color(153, 255, 204));
        btnGastoGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pe/edu/utp/financio/images/Guardar.png"))); // NOI18N
        btnGastoGuardar.setText("Guardar");
        btnGastoGuardar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnGastoGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGastoGuardarActionPerformed(evt);
            }
        });

        tblGastos.setBackground(new java.awt.Color(204, 255, 255));
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

        btnGastoAgrCategoria.setBackground(new java.awt.Color(153, 255, 204));
        btnGastoAgrCategoria.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pe/edu/utp/financio/images/Crear.png"))); // NOI18N
        btnGastoAgrCategoria.setText("Agregar Categoria");
        btnGastoAgrCategoria.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnGastoAgrCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGastoAgrCategoriaActionPerformed(evt);
            }
        });

        btnGastoEliminar.setBackground(new java.awt.Color(153, 255, 204));
        btnGastoEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pe/edu/utp/financio/images/Eliminar.png"))); // NOI18N
        btnGastoEliminar.setText("Eliminar");
        btnGastoEliminar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnGastoEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGastoEliminarActionPerformed(evt);
            }
        });

        jLabel26.setFont(new java.awt.Font("Sitka Text", 1, 12)); // NOI18N
        jLabel26.setText("Total:");

        txtGastoTotal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel29.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel29.setText("Método de pago:");

        cbxMetodoPagoGasto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

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
                                .addComponent(btnGastoAgrCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnGastoGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addComponent(btnGastoEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(574, Short.MAX_VALUE))
                    .addGroup(PanelGastosLayout.createSequentialGroup()
                        .addGroup(PanelGastosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtGastoDescripcion, javax.swing.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
                            .addComponent(txtGastoMonto, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)
                            .addComponent(jLabel29)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10)
                            .addComponent(cbxGastoCategoria, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbxMetodoPagoGasto, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(PanelGastosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 556, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelGastosLayout.createSequentialGroup()
                                .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(32, 32, 32)
                                .addComponent(txtGastoTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(173, 173, 173))))
        );
        PanelGastosLayout.setVerticalGroup(
            PanelGastosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelGastosLayout.createSequentialGroup()
                .addGroup(PanelGastosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelGastosLayout.createSequentialGroup()
                        .addGap(72, 72, 72)
                        .addComponent(jLabel6)
                        .addGap(29, 29, 29)
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
                        .addComponent(txtGastoDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addGroup(PanelGastosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnGastoAgrCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnGastoGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnGastoEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(PanelGastosLayout.createSequentialGroup()
                        .addGap(163, 163, 163)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(PanelGastosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel26)
                            .addComponent(txtGastoTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(249, Short.MAX_VALUE))
        );

        JPpanelcontenido.add(PanelGastos, "card4");

        PanelAnalisisFinanzas.setBackground(new java.awt.Color(204, 204, 255));

        javax.swing.GroupLayout PanelAnalisisFinanzasLayout = new javax.swing.GroupLayout(PanelAnalisisFinanzas);
        PanelAnalisisFinanzas.setLayout(PanelAnalisisFinanzasLayout);
        PanelAnalisisFinanzasLayout.setHorizontalGroup(
            PanelAnalisisFinanzasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1172, Short.MAX_VALUE)
        );
        PanelAnalisisFinanzasLayout.setVerticalGroup(
            PanelAnalisisFinanzasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 809, Short.MAX_VALUE)
        );

        JPpanelcontenido.add(PanelAnalisisFinanzas, "card5");

        PanelMovimientos.setBackground(new java.awt.Color(102, 153, 255));

        jLabel11.setFont(new java.awt.Font("Segoe UI Semibold", 1, 36)); // NOI18N
        jLabel11.setText("Movimientos");

        tblMovimientos.setBackground(new java.awt.Color(204, 255, 255));
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

        jPanel1.setBackground(new java.awt.Color(255, 255, 204));

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

        btnBuscar.setBackground(new java.awt.Color(153, 255, 204));
        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pe/edu/utp/financio/images/Buscar.png"))); // NOI18N
        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(153, 255, 204));
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pe/edu/utp/financio/images/Eliminar.png"))); // NOI18N
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
                            .addComponent(btnBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
                            .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addGap(208, 208, 208))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(btnBuscar)
                            .addGap(68, 68, 68))
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
                                .addComponent(cbxCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(127, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout PanelMovimientosLayout = new javax.swing.GroupLayout(PanelMovimientos);
        PanelMovimientos.setLayout(PanelMovimientosLayout);
        PanelMovimientosLayout.setHorizontalGroup(
            PanelMovimientosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelMovimientosLayout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(jLabel11)
                .addContainerGap(898, Short.MAX_VALUE))
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

        PanelMetas.setBackground(new java.awt.Color(102, 153, 255));

        jLabel17.setFont(new java.awt.Font("Segoe UI Semibold", 1, 36)); // NOI18N
        jLabel17.setText("Metas:");

        tblMetas.setBackground(new java.awt.Color(204, 255, 255));
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

        jLabel18.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel18.setText("Nombre:");

        jLabel19.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel19.setText("Objetivo:");

        txtObjetivoMeta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtObjetivoMetaActionPerformed(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel20.setText("Fecha limite meta:");

        jLabel21.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel21.setText("% destinado:");

        txtPorcentajedestinadoMeta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPorcentajedestinadoMetaActionPerformed(evt);
            }
        });

        btnMeta.setBackground(new java.awt.Color(153, 255, 204));
        btnMeta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pe/edu/utp/financio/images/Adicionar.png"))); // NOI18N
        btnMeta.setText("Crear");
        btnMeta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMetaActionPerformed(evt);
            }
        });

        jButton8.setBackground(new java.awt.Color(153, 255, 204));
        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pe/edu/utp/financio/images/Eliminar.png"))); // NOI18N
        jButton8.setText("Eliminar");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelMetasLayout = new javax.swing.GroupLayout(PanelMetas);
        PanelMetas.setLayout(PanelMetasLayout);
        PanelMetasLayout.setHorizontalGroup(
            PanelMetasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelMetasLayout.createSequentialGroup()
                .addGroup(PanelMetasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelMetasLayout.createSequentialGroup()
                        .addGap(67, 67, 67)
                        .addComponent(jLabel17))
                    .addGroup(PanelMetasLayout.createSequentialGroup()
                        .addGap(77, 77, 77)
                        .addGroup(PanelMetasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PanelMetasLayout.createSequentialGroup()
                                .addGroup(PanelMetasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(PanelMetasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(txtObjetivoMeta, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PanelMetasLayout.createSequentialGroup()
                                            .addGap(67, 67, 67)
                                            .addComponent(txtNombreMeta, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(jLabel19)
                                    .addComponent(jLabel18)
                                    .addComponent(jLabel20))
                                .addGap(95, 95, 95)
                                .addGroup(PanelMetasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnMeta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(PanelMetasLayout.createSequentialGroup()
                                .addGroup(PanelMetasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel21)
                                    .addGroup(PanelMetasLayout.createSequentialGroup()
                                        .addGap(72, 72, 72)
                                        .addComponent(jdcFechaMeta, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(PanelMetasLayout.createSequentialGroup()
                                        .addGap(74, 74, 74)
                                        .addComponent(txtPorcentajedestinadoMeta, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 623, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(210, 210, 210))
        );
        PanelMetasLayout.setVerticalGroup(
            PanelMetasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelMetasLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel17)
                .addGap(32, 32, 32)
                .addComponent(jLabel18)
                .addGap(4, 4, 4)
                .addGroup(PanelMetasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNombreMeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMeta))
                .addGroup(PanelMetasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelMetasLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtObjetivoMeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PanelMetasLayout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jButton8)))
                .addGap(18, 18, 18)
                .addComponent(jLabel20)
                .addGroup(PanelMetasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelMetasLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jdcFechaMeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPorcentajedestinadoMeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PanelMetasLayout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(187, Short.MAX_VALUE))
        );

        JPpanelcontenido.add(PanelMetas, "card7");

        PanelExportarDatos.setBackground(new java.awt.Color(102, 153, 255));

        jLabel22.setFont(new java.awt.Font("Segoe UI Semibold", 1, 36)); // NOI18N
        jLabel22.setText("Exportar datos:");

        cbxExportarDatos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Opciones", "Ingresos", "Gastos", "Movimientos" }));
        cbxExportarDatos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxExportarDatosActionPerformed(evt);
            }
        });

        jLabel23.setText("Desde fecha:");

        jLabel24.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel24.setText("a");

        btnExportarPDF.setBackground(new java.awt.Color(153, 255, 204));
        btnExportarPDF.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pe/edu/utp/financio/images/exportar.png"))); // NOI18N
        btnExportarPDF.setText("Exportar a PDF");
        btnExportarPDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportarPDFActionPerformed(evt);
            }
        });

        btnImprimirExcel.setBackground(new java.awt.Color(153, 255, 204));
        btnImprimirExcel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pe/edu/utp/financio/images/exportar.png"))); // NOI18N
        btnImprimirExcel.setText("Exportar a xlsx");
        btnImprimirExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirExcelActionPerformed(evt);
            }
        });

        jLabel34.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel34.setText("Tipo de datos:");

        javax.swing.GroupLayout PanelExportarDatosLayout = new javax.swing.GroupLayout(PanelExportarDatos);
        PanelExportarDatos.setLayout(PanelExportarDatosLayout);
        PanelExportarDatosLayout.setHorizontalGroup(
            PanelExportarDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelExportarDatosLayout.createSequentialGroup()
                .addGroup(PanelExportarDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelExportarDatosLayout.createSequentialGroup()
                        .addGap(66, 66, 66)
                        .addGroup(PanelExportarDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel22)
                            .addComponent(jLabel23)))
                    .addGroup(PanelExportarDatosLayout.createSequentialGroup()
                        .addGap(132, 132, 132)
                        .addComponent(cbxExportarDatos, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(PanelExportarDatosLayout.createSequentialGroup()
                .addGroup(PanelExportarDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelExportarDatosLayout.createSequentialGroup()
                        .addGap(132, 132, 132)
                        .addComponent(jdcInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jdcFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PanelExportarDatosLayout.createSequentialGroup()
                        .addGap(204, 204, 204)
                        .addGroup(PanelExportarDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnExportarPDF, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnImprimirExcel, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(720, Short.MAX_VALUE))
        );
        PanelExportarDatosLayout.setVerticalGroup(
            PanelExportarDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelExportarDatosLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel22)
                .addGap(37, 37, 37)
                .addComponent(jLabel34)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxExportarDatos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(jLabel23)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PanelExportarDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel24)
                    .addComponent(jdcInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jdcFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(56, 56, 56)
                .addComponent(btnImprimirExcel, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(btnExportarPDF, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(381, Short.MAX_VALUE))
        );

        JPpanelcontenido.add(PanelExportarDatos, "card8");

        Panelnicio.setBackground(new java.awt.Color(102, 153, 255));

        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 38)); // NOI18N
        jLabel25.setText("Bienvenido:");

        lblNombre.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblNombre.setText("\"Nombre \"");

        jLabel28.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel28.setText("Tu dinero, tus reglas. Empieza a tomar el control hoy.");

        btncomenzar.setBackground(new java.awt.Color(153, 255, 204));
        btncomenzar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pe/edu/utp/financio/images/ingreso.png"))); // NOI18N
        btncomenzar.setText("Comenzar");
        btncomenzar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncomenzarActionPerformed(evt);
            }
        });

        jLabel37.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pe/edu/utp/financio/images/financiar.png"))); // NOI18N
        jLabel37.setText("jLabel37");

        jLabel38.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel38.setText("°Registrar tus ingresos y gastos diarios.");

        jLabel39.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel39.setText("Con Financio podrás:");

        jLabel40.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel40.setText("°Visualizar todos tus movimientos financieros en un solo lugar.");

        jLabel41.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel41.setText("°Establecer y seguir tus metas de ahorro.");

        jLabel42.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel42.setText("°Imprimir reportes personalizados de ingresos, gastos o movimientos combinados, según tus necesidades.");

        jLabel43.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel43.setText("°Controlar tu caja chica para gastos menores.");

        javax.swing.GroupLayout PanelnicioLayout = new javax.swing.GroupLayout(Panelnicio);
        Panelnicio.setLayout(PanelnicioLayout);
        PanelnicioLayout.setHorizontalGroup(
            PanelnicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelnicioLayout.createSequentialGroup()
                .addGroup(PanelnicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelnicioLayout.createSequentialGroup()
                        .addGap(237, 237, 237)
                        .addGroup(PanelnicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PanelnicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel41, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE)
                                .addComponent(jLabel38, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 694, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 434, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(PanelnicioLayout.createSequentialGroup()
                        .addGap(130, 130, 130)
                        .addGroup(PanelnicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(PanelnicioLayout.createSequentialGroup()
                                .addComponent(jLabel25)
                                .addGap(40, 40, 40)
                                .addComponent(lblNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(PanelnicioLayout.createSequentialGroup()
                        .addGap(433, 433, 433)
                        .addComponent(btncomenzar, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PanelnicioLayout.createSequentialGroup()
                        .addGap(291, 291, 291)
                        .addComponent(jLabel28))
                    .addGroup(PanelnicioLayout.createSequentialGroup()
                        .addGap(313, 313, 313)
                        .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(241, Short.MAX_VALUE))
        );
        PanelnicioLayout.setVerticalGroup(
            PanelnicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelnicioLayout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(PanelnicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel25)
                    .addComponent(lblNombre))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btncomenzar)
                .addGap(25, 25, 25))
        );

        JPpanelcontenido.add(Panelnicio, "card2");

        jPanel2.setBackground(new java.awt.Color(102, 153, 255));

        jLabel31.setFont(new java.awt.Font("Segoe UI Black", 0, 36)); // NOI18N
        jLabel31.setText("Caja Registradora");

        jLabel30.setFont(new java.awt.Font("Sitka Text", 1, 18)); // NOI18N
        jLabel30.setText("ID de Caja:");

        jLabel32.setFont(new java.awt.Font("Sitka Text", 1, 18)); // NOI18N
        jLabel32.setText("Resumen del dia:");

        jLabel33.setFont(new java.awt.Font("Sitka Text", 1, 18)); // NOI18N
        jLabel33.setText("Cierre (Noche):");

        txtfondo.setFont(new java.awt.Font("Sitka Text", 1, 18)); // NOI18N
        txtfondo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtfondo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtfondoActionPerformed(evt);
            }
        });

        txtcierre.setFont(new java.awt.Font("Sitka Text", 1, 18)); // NOI18N
        txtcierre.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtcierre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtcierreActionPerformed(evt);
            }
        });

        txtresumen.setColumns(20);
        txtresumen.setFont(new java.awt.Font("Sitka Text", 1, 14)); // NOI18N
        txtresumen.setRows(5);
        txtresumen.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jScrollPane5.setViewportView(txtresumen);

        btneliminar.setBackground(new java.awt.Color(204, 204, 204));
        btneliminar.setFont(new java.awt.Font("Sitka Text", 1, 24)); // NOI18N
        btneliminar.setText("ELIMINAR");
        btneliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneliminarActionPerformed(evt);
            }
        });

        btnagregarfondo.setBackground(new java.awt.Color(204, 204, 204));
        btnagregarfondo.setFont(new java.awt.Font("Sitka Text", 1, 18)); // NOI18N
        btnagregarfondo.setText("AGREGAR");
        btnagregarfondo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnagregarfondoActionPerformed(evt);
            }
        });

        btnmostrar.setBackground(new java.awt.Color(204, 204, 204));
        btnmostrar.setFont(new java.awt.Font("Sitka Text", 1, 24)); // NOI18N
        btnmostrar.setText("MOSTRAR");
        btnmostrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnmostrarActionPerformed(evt);
            }
        });

        jLabel35.setFont(new java.awt.Font("Sitka Text", 1, 18)); // NOI18N
        jLabel35.setText("ID Usuario:");

        txtid.setFont(new java.awt.Font("Sitka Text", 1, 18)); // NOI18N
        txtid.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtidActionPerformed(evt);
            }
        });

        jLabel36.setFont(new java.awt.Font("Sitka Text", 1, 18)); // NOI18N
        jLabel36.setText("Id a Elimar:");

        txtIdUsuario.setFont(new java.awt.Font("Sitka Text", 1, 18)); // NOI18N
        txtIdUsuario.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtIdUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdUsuarioActionPerformed(evt);
            }
        });

        txtNombre.setFont(new java.awt.Font("Sitka Text", 1, 18)); // NOI18N
        txtNombre.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreActionPerformed(evt);
            }
        });

        jLabel44.setFont(new java.awt.Font("Sitka Text", 1, 18)); // NOI18N
        jLabel44.setText("Nombre:");

        btnagregarcierre.setBackground(new java.awt.Color(204, 204, 204));
        btnagregarcierre.setFont(new java.awt.Font("Sitka Text", 1, 18)); // NOI18N
        btnagregarcierre.setText("AGREGAR");
        btnagregarcierre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnagregarcierreActionPerformed(evt);
            }
        });

        jLabel45.setFont(new java.awt.Font("Sitka Text", 1, 18)); // NOI18N
        jLabel45.setText("Fecha:");

        jLabel46.setFont(new java.awt.Font("Sitka Text", 1, 18)); // NOI18N
        jLabel46.setText("Fecha");

        txtIdCaja.setFont(new java.awt.Font("Sitka Text", 1, 18)); // NOI18N
        txtIdCaja.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtIdCaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdCajaActionPerformed(evt);
            }
        });

        jLabel47.setFont(new java.awt.Font("Sitka Text", 1, 18)); // NOI18N
        jLabel47.setText("Fondo (Mañana): ");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel35)
                            .addComponent(txtIdUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtIdCaja, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(jLabel30))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel46)))
                        .addGap(201, 943, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(111, 111, 111)
                                .addComponent(jLabel31))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                    .addComponent(jDateChoosercierre, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(98, 98, 98)
                                    .addComponent(btnagregarcierre, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel47)
                                                .addComponent(txtfondo, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGap(48, 48, 48))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(btnmostrar, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jDateChooserResumen, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jLabel32))
                                            .addGap(49, 49, 49)))
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtcierre, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 784, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel44)
                                                .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel36)
                                                .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel2Layout.createSequentialGroup()
                                                    .addGap(6, 6, 6)
                                                    .addComponent(jLabel45))
                                                .addComponent(jDateChooserfondo, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jLabel33))
                                            .addGap(47, 47, 47)
                                            .addComponent(btnagregarfondo, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(btneliminar)
                                            .addGap(23, 23, 23))))))
                        .addGap(56, 56, 56))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jLabel31)
                        .addGap(24, 24, 24)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel35)
                            .addComponent(jLabel44))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtIdUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel45)
                            .addComponent(jLabel47))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jDateChooserfondo, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtfondo, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnagregarfondo, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(40, 40, 40)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel30)
                            .addComponent(jLabel33))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtIdCaja, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtcierre, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel46)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jDateChoosercierre, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnagregarcierre, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(108, 108, 108)
                        .addComponent(jLabel36)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btneliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 209, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel32)
                        .addGap(18, 18, 18)
                        .addComponent(jDateChooserResumen, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(btnmostrar, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(111, 111, 111))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(101, 101, 101))))
        );

        javax.swing.GroupLayout PanelCajachicaLayout = new javax.swing.GroupLayout(PanelCajachica);
        PanelCajachica.setLayout(PanelCajachicaLayout);
        PanelCajachicaLayout.setHorizontalGroup(
            PanelCajachicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelCajachicaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(23, 23, 23))
        );
        PanelCajachicaLayout.setVerticalGroup(
            PanelCajachicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

            JProgressBar barra = new JProgressBar(0, 100);
            barra.setValue(0);
            barra.setString("0%");
            barra.setStringPainted(true);

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

            UsuarioDAOPostgres daousuario = new UsuarioDAOPostgres();
            int idAdmin = daousuario.obtenerIdAdmin();
            int creadoPor = usuario.getId();
            MovimientoDaoImpl daoMov = new MovimientoDaoImpl();
            int idGenerado = daoMov.registrarmovimiento(m, idAdmin, creadoPor);

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

            UsuarioDAOPostgres daousuario = new UsuarioDAOPostgres();
            int idAdmin = daousuario.obtenerIdAdmin();
            int creadoPor = usuario.getId();
            MovimientoDaoImpl daoMov = new MovimientoDaoImpl();
            int idGenerado = daoMov.registrarmovimiento(m, idAdmin, creadoPor);

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

    private void txtfondoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtfondoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtfondoActionPerformed

    private void txtcierreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcierreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcierreActionPerformed

    private void btncerrarsesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncerrarsesionActionPerformed
        cerrarSesion();
    }//GEN-LAST:event_btncerrarsesionActionPerformed

    private void btnExportarPDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportarPDFActionPerformed
         String opcion = (String) cbxExportarDatos.getSelectedItem();

    try {
        // 1. Obtener rango de fechas
        Date desde = jdcInicio.getDate();
        Date hasta = jdcFinal.getDate();

        if (desde == null || hasta == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un rango de fechas");
            return;
        }

        LocalDateTime inicio = desde.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime fin = hasta.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        // 2. Obtener lista según la opción
        List<Movimiento> lista = new ArrayList<>();
        if ("Ingresos".equals(opcion)) {
            lista = movimientoService.listarIngresosPorUsuario(usuario);
            lista = filtrarPorFecha(lista, inicio, fin);
            exportarIngresosAPdf(lista);

        } else if ("Gastos".equals(opcion)) {
            lista = movimientoService.listarGastosPorUsuario(usuario);
            lista = filtrarPorFecha(lista, inicio, fin);
            exportarGastosAPdf(lista);

        } else if ("Movimientos".equals(opcion)) {
            lista = movimientoService.listarPorUsuario(usuario);
            lista = filtrarPorFecha(lista, inicio, fin);
            exportarMovimientosAPdf(lista);

        } else {
            JOptionPane.showMessageDialog(this, "Seleccione una opción válida");
        }

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error al exportar PDF: " + e.getMessage());
    }

    }//GEN-LAST:event_btnExportarPDFActionPerformed

    private void cbxExportarDatosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxExportarDatosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxExportarDatosActionPerformed

    private void btnImprimirExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirExcelActionPerformed
       String opcion = (String) cbxExportarDatos.getSelectedItem();

    try {
        Date desde = jdcInicio.getDate();
        Date hasta = jdcFinal.getDate();

        if (desde == null || hasta == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un rango de fechas");
            return;
        }

        LocalDateTime inicio = desde.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime fin = hasta.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        if ("Ingresos".equals(opcion)) {
            List<Movimiento> lista = movimientoService.listarIngresosPorUsuario(usuario);
            lista = filtrarPorFecha(lista, inicio, fin);
            exportarIngresosAExcel(lista);

        } else if ("Gastos".equals(opcion)) {
            List<Movimiento> lista = movimientoService.listarGastosPorUsuario(usuario);
            lista = filtrarPorFecha(lista, inicio, fin);
            exportarGastosAExcel(lista);

        } else if ("Movimientos".equals(opcion)) {
            List<Movimiento> lista = movimientoService.listarPorUsuario(usuario);
            lista = filtrarPorFecha(lista, inicio, fin);
            exportarMovimientosAExcel(lista);

        } else {
            JOptionPane.showMessageDialog(this, "Seleccione una opción válida");
        }

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error al exportar Excel: " + e.getMessage());
    }

    }//GEN-LAST:event_btnImprimirExcelActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton8ActionPerformed

    private void btnCajachicaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCajachicaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCajachicaActionPerformed

    private void btncomenzarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncomenzarActionPerformed
        btncomenzar.addActionListener(e -> cardLayout.show(JPpanelcontenido, "panelIngresos"));
    }//GEN-LAST:event_btncomenzarActionPerformed

    private void btnagregarfondoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnagregarfondoActionPerformed
btnagregarfondo.addActionListener(e -> {
    try {
        int idUsuario = Integer.parseInt(txtIdUsuario.getText());
        String nombre = txtNombre.getText();
        double fondo = Double.parseDouble(txtfondo.getText());
        LocalDate fecha = jDateChooserfondo.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        Caja caja = new Caja(0, idUsuario, nombre, fondo, 0.0, fecha, null);
        int id = cajaDao.guardarFondo(caja);

        if (id > 0) {
            JOptionPane.showMessageDialog(null, "✅ Fondo registrado con ID: " + id);
            mostrarCajas();
        } else {
            JOptionPane.showMessageDialog(null, "❌ No se pudo registrar el fondo");
        }

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
    }
});


    }//GEN-LAST:event_btnagregarfondoActionPerformed

    private void btneliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneliminarActionPerformed

    btneliminar.addActionListener(e -> {
    try {
        String textoId = txtid.getText().trim();
        if (textoId.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingresa un ID para eliminar");
            return;
        }

        int id = Integer.parseInt(textoId);
        boolean eliminado = cajaDao.eliminar(id);

        if (eliminado) {
            JOptionPane.showMessageDialog(null, "Registro eliminado con ID: " + id);
            txtid.setText(""); // limpiar campo
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró ningún registro con ese ID");
        }
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(null, "El ID debe ser un número válido");
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "Error al eliminar: " + ex.getMessage());
    }
});



    }//GEN-LAST:event_btneliminarActionPerformed

    private void btnmostrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnmostrarActionPerformed
 btnmostrar.addActionListener(e -> {
    try {
        Date fechaSeleccionada = jDateChooserResumen.getDate();
        if (fechaSeleccionada == null) {
            JOptionPane.showMessageDialog(null, "Selecciona una fecha para mostrar el resumen");
            return;
        }

        LocalDate fechaFiltro = fechaSeleccionada.toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDate();

        List<Caja> cajas = cajaDao.listar(); // obtiene todas las cajas
        StringBuilder resumen = new StringBuilder();

        for (Caja c : cajas) {
            if (c.getFechaApertura().equals(fechaFiltro)) {
                resumen.append("ID: ").append(c.getIdCaja())
                       .append(" | Usuario: ").append(c.getIdUsuario())
                       .append(" | Nombre: ").append(c.getNombre())
                       .append(" | Fondo: ").append(c.getFondo())
                       .append(" | Cierre: ").append(c.getCierre())
                       .append("\n");
            }
        }

        if (resumen.length() == 0) {
            txtresumen.setText("No hay cajas registradas en esa fecha.");
        } else {
            txtresumen.setText(resumen.toString());
        }

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(null, "Error al mostrar resumen: " + ex.getMessage());
    }
   
});


    }//GEN-LAST:event_btnmostrarActionPerformed

    private void txtidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtidActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtidActionPerformed

    private void txtIdUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdUsuarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdUsuarioActionPerformed

    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreActionPerformed

    private void btnagregarcierreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnagregarcierreActionPerformed
        btnagregarcierre.addActionListener(e -> {
    try {
        int idCaja = Integer.parseInt(txtIdCaja.getText());
        double cierre = Double.parseDouble(txtcierre.getText());
        LocalDate fechaCierre = jDateChoosercierre.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        int actualizado = cajaDao.guardarCierre(idCaja, cierre, fechaCierre);

        if (actualizado > 0) {
            JOptionPane.showMessageDialog(null, "✅ Cierre actualizado para ID: " + idCaja);
            mostrarCajas();
        } else {
            JOptionPane.showMessageDialog(null, "❌ No se pudo actualizar el cierre");
        }

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
    }
});

    }//GEN-LAST:event_btnagregarcierreActionPerformed

    private void txtIdCajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdCajaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdCajaActionPerformed

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
            List<Movimiento> lista = movimientoService.listarIngresosPorUsuario(usuario);

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
            List<Movimiento> lista = movimientoService.listarGastosPorUsuario(usuario);
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
                lista = movimientoService.listarIngresosPorUsuario(usuario);
            } else if ("Egresos".equals(opcionTipo)) {
                lista = movimientoService.listarGastosPorUsuario(usuario);
            } else {
                lista = movimientoService.listarPorUsuario(usuario); // Todos
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
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 3 ? JProgressBar.class : Object.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

        };
        modelo.setColumnIdentifiers(new Object[]{"Nombre", "Objetivo", "Acumulado", "Progreso"});
        tblMetas.setModel(modelo);

        tblMetas.setDefaultRenderer(JProgressBar.class, new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                JProgressBar barra = (JProgressBar) value;
                barra.setStringPainted(true);
                return barra;
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

    private void cerrarSesion() {
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "¿Seguro que deseas cerrar sesión?",
                "Cerrar Sesión",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            // Limpiar usuario actual
            usuario = null;

            // Cerrar la ventana actual
            this.dispose();

            // Volver a la ventana de login
            JframeLogin login = new JframeLogin(); // tu JFrame de login
            login.setVisible(true);
        }
    }
   
    private void exportarIngresosAPdf(List<Movimiento> lista) throws Exception {
    Document doc = new Document();
    PdfWriter.getInstance(doc, new FileOutputStream("Ingresos.pdf"));
    doc.open();

    doc.add(new Paragraph("Reporte de Ingresos"));
    doc.add(new Paragraph(" ")); // salto de línea

    PdfPTable table = new PdfPTable(5); // 5 columnas
    table.addCell("ID");
    table.addCell("Categoria");
    table.addCell("Método Pago");
    table.addCell("Descripción");
    table.addCell("Monto");

    BigDecimal total = BigDecimal.ZERO;

    for (Movimiento m : lista) {
        table.addCell(String.valueOf(m.getId()));
        table.addCell(m.getCategoria());
        table.addCell(m.getNombreMetodoPago());
        table.addCell(m.getDescripcion());
        table.addCell(m.getMonto().toString());

        total = total.add(m.getMonto());
    }

    doc.add(table);
    doc.add(new Paragraph("Total: " + total.toString()));
    doc.close();

    JOptionPane.showMessageDialog(this, "PDF generado correctamente ✅");
}

    private void exportarGastosAPdf(List<Movimiento> lista) throws Exception {
    Document doc = new Document();
    PdfWriter.getInstance(doc, new FileOutputStream("Gastos.pdf"));
    doc.open();

    doc.add(new Paragraph("Reporte de Gastos"));
    doc.add(new Paragraph(" "));

    PdfPTable table = new PdfPTable(5);
    table.addCell("ID");
    table.addCell("Categoria");
    table.addCell("Método Pago");
    table.addCell("Descripción");
    table.addCell("Monto");

    BigDecimal total = BigDecimal.ZERO;

    for (Movimiento m : lista) {
        table.addCell(String.valueOf(m.getId()));
        table.addCell(m.getCategoria());
        table.addCell(m.getNombreMetodoPago());
        table.addCell(m.getDescripcion());
        table.addCell(m.getMonto().toString());

        total = total.add(m.getMonto());
    }

    doc.add(table);
    doc.add(new Paragraph("Total Gastos: " + total.toString()));
    doc.close();

    JOptionPane.showMessageDialog(this, "PDF de Gastos generado ✅");
}

    private void exportarMovimientosAPdf(List<Movimiento> lista) throws Exception {
    Document doc = new Document();
    PdfWriter.getInstance(doc, new FileOutputStream("Movimientos.pdf"));
    doc.open();

    doc.add(new Paragraph("Reporte de Movimientos"));
    doc.add(new Paragraph(" "));

    PdfPTable table = new PdfPTable(5);
    table.addCell("Fecha");
    table.addCell("Tipo");
    table.addCell("Categoria");
    table.addCell("Descripción");
    table.addCell("Monto");

    for (Movimiento m : lista) {
        String fechaFormateada = m.getFecha().toLocalDateTime()
                                  .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        table.addCell(fechaFormateada);
        table.addCell(m.getTipo());
        table.addCell(m.getCategoria());
        table.addCell(m.getDescripcion());
        table.addCell(m.getMonto().toString());
    }

    doc.add(table);
    doc.close();

    JOptionPane.showMessageDialog(this, "PDF de Movimientos generado ✅");
}

    private List<Movimiento> filtrarPorFecha(List<Movimiento> lista, LocalDateTime inicio, LocalDateTime fin) {
    return lista.stream()
            .filter(m -> {
                LocalDateTime fecha = m.getFecha().toLocalDateTime();
                return (fecha.isEqual(inicio) || fecha.isAfter(inicio)) &&
                       (fecha.isEqual(fin) || fecha.isBefore(fin));
            })
            .toList();
}

    //exportar Excel
    private void exportarIngresosAExcel(List<Movimiento> lista) throws Exception {
    Workbook workbook = new XSSFWorkbook();
    Sheet sheet = workbook.createSheet("Ingresos");

    // Encabezados
    String[] columnas = {"ID", "Categoria", "Método Pago", "Descripción", "Monto"};
    Row headerRow = sheet.createRow(0);
    for (int i = 0; i < columnas.length; i++) {
        headerRow.createCell(i).setCellValue(columnas[i]);
    }

    int rowNum = 1;
    BigDecimal total = BigDecimal.ZERO;

    for (Movimiento m : lista) {
        Row row = sheet.createRow(rowNum++);
        row.createCell(0).setCellValue(m.getId());
        row.createCell(1).setCellValue(m.getCategoria());
        row.createCell(2).setCellValue(m.getNombreMetodoPago());
        row.createCell(3).setCellValue(m.getDescripcion());
        row.createCell(4).setCellValue(m.getMonto().doubleValue());

        total = total.add(m.getMonto());
    }

    // Total
    Row totalRow = sheet.createRow(rowNum);
    totalRow.createCell(3).setCellValue("TOTAL:");
    totalRow.createCell(4).setCellValue(total.doubleValue());

    // Ajustar ancho columnas
    for (int i = 0; i < columnas.length; i++) {
        sheet.autoSizeColumn(i);
    }

    try (FileOutputStream fileOut = new FileOutputStream("Ingresos.xlsx")) {
        workbook.write(fileOut);
    }
    workbook.close();

    JOptionPane.showMessageDialog(this, "Excel de Ingresos generado ✅");
}

    private void exportarGastosAExcel(List<Movimiento> lista) throws Exception {
    Workbook workbook = new XSSFWorkbook();
    Sheet sheet = workbook.createSheet("Gastos");

    String[] columnas = {"ID", "Categoria", "Método Pago", "Descripción", "Monto"};
    Row headerRow = sheet.createRow(0);
    for (int i = 0; i < columnas.length; i++) {
        headerRow.createCell(i).setCellValue(columnas[i]);
    }

    int rowNum = 1;
    BigDecimal total = BigDecimal.ZERO;

    for (Movimiento m : lista) {
        Row row = sheet.createRow(rowNum++);
        row.createCell(0).setCellValue(m.getId());
        row.createCell(1).setCellValue(m.getCategoria());
        row.createCell(2).setCellValue(m.getNombreMetodoPago());
        row.createCell(3).setCellValue(m.getDescripcion());
        row.createCell(4).setCellValue(m.getMonto().doubleValue());

        total = total.add(m.getMonto());
    }

    Row totalRow = sheet.createRow(rowNum);
    totalRow.createCell(3).setCellValue("TOTAL:");
    totalRow.createCell(4).setCellValue(total.doubleValue());

    for (int i = 0; i < columnas.length; i++) {
        sheet.autoSizeColumn(i);
    }

    try (FileOutputStream fileOut = new FileOutputStream("Gastos.xlsx")) {
        workbook.write(fileOut);
    }
    workbook.close();

    JOptionPane.showMessageDialog(this, "Excel de Gastos generado ✅");
}

    private void exportarMovimientosAExcel(List<Movimiento> lista) throws Exception {
    Workbook workbook = new XSSFWorkbook();
    Sheet sheet = workbook.createSheet("Movimientos");

    String[] columnas = {"Fecha", "Tipo", "Categoria", "Descripción", "Monto"};
    Row headerRow = sheet.createRow(0);
    for (int i = 0; i < columnas.length; i++) {
        headerRow.createCell(i).setCellValue(columnas[i]);
    }

    int rowNum = 1;

    for (Movimiento m : lista) {
        Row row = sheet.createRow(rowNum++);
        String fechaFormateada = m.getFecha().toLocalDateTime()
                                  .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        row.createCell(0).setCellValue(fechaFormateada);
        row.createCell(1).setCellValue(m.getTipo());
        row.createCell(2).setCellValue(m.getCategoria());
        row.createCell(3).setCellValue(m.getDescripcion());
        row.createCell(4).setCellValue(m.getMonto().doubleValue());
    }

    for (int i = 0; i < columnas.length; i++) {
        sheet.autoSizeColumn(i);
    }

    try (FileOutputStream fileOut = new FileOutputStream("Movimientos.xlsx")) {
        workbook.write(fileOut);
    }
    workbook.close();

    JOptionPane.showMessageDialog(this, "Excel de Movimientos generado ✅");
}

    


   
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
    private javax.swing.JButton btnExportarPDF;
    private javax.swing.JButton btnGastoAgrCategoria;
    private javax.swing.JButton btnGastoEliminar;
    private javax.swing.JButton btnGastoGuardar;
    public javax.swing.JButton btnGastos;
    private javax.swing.JButton btnImprimirExcel;
    public javax.swing.JButton btnIngreso;
    public javax.swing.JButton btnInicio;
    private javax.swing.JButton btnMeta;
    public javax.swing.JButton btnMetas;
    public javax.swing.JButton btnMovimiento;
    private javax.swing.JButton btnagregarcierre;
    private javax.swing.JButton btnagregarfondo;
    private javax.swing.JButton btncerrarsesion;
    private javax.swing.JButton btncomenzar;
    private javax.swing.JButton btneliminar;
    private javax.swing.JButton btningresoguardar;
    private javax.swing.JButton btnmostrar;
    private javax.swing.JComboBox<String> cbxCategoria;
    private javax.swing.JComboBox<String> cbxExportarDatos;
    private javax.swing.JComboBox<Categoria> cbxGastoCategoria;
    private javax.swing.JComboBox<Metodopago> cbxMetodoPagoGasto;
    private javax.swing.JComboBox<Metodopago> cbxMetodoPagoIngreso;
    private javax.swing.JComboBox<String> cbxTipo;
    private javax.swing.JComboBox<Categoria> cbxingrsocategoria;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton8;
    private com.toedter.calendar.JDateChooser jDateChooserResumen;
    private com.toedter.calendar.JDateChooser jDateChoosercierre;
    private com.toedter.calendar.JDateChooser jDateChooserfondo;
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
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private com.toedter.calendar.JDateChooser jdcFecha1;
    private com.toedter.calendar.JDateChooser jdcFecha2;
    private com.toedter.calendar.JDateChooser jdcFechaMeta;
    private com.toedter.calendar.JDateChooser jdcFinal;
    private com.toedter.calendar.JDateChooser jdcInicio;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JTable tblGastos;
    private javax.swing.JTable tblIngreso;
    private javax.swing.JTable tblMetas;
    private javax.swing.JTable tblMovimientos;
    private javax.swing.JTextField txtGastoDescripcion;
    private javax.swing.JTextField txtGastoMonto;
    private javax.swing.JTextField txtGastoTotal;
    private javax.swing.JTextField txtIdCaja;
    private javax.swing.JTextField txtIdUsuario;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtNombreMeta;
    private javax.swing.JTextField txtObjetivoMeta;
    private javax.swing.JTextField txtPorcentajedestinadoMeta;
    private javax.swing.JTextField txtTotal;
    private javax.swing.JTextField txtcierre;
    private javax.swing.JTextField txtfondo;
    private javax.swing.JTextField txtid;
    private javax.swing.JTextField txtingresocantidad;
    private javax.swing.JTextField txtingresodescripcion;
    private javax.swing.JTextArea txtresumen;
    // End of variables declaration//GEN-END:variables
}
