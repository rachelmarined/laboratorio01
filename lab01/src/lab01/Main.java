package lab01;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

public class Main extends JFrame {

    private final int ASIENTOS = 30;
    private final int COLUMNAS = 6;

    private final Color VERDE = new Color(200, 240, 190);
    private final Color ROJO = new Color(250, 185, 185);
    private final Color AMARILLO = new Color(255, 240, 120);

    private paliair vuelo = new paliair();
    private JButton[] asientos = new JButton[ASIENTOS];
    private JTextField campoNombre = new JTextField();
    private JLabel lblValidacion = new JLabel(" ");
    private JTextArea consola = new JTextArea();
    private JLabel lblDisponibles = crearContador("Disponibles");
    private JLabel lblOcupados = crearContador("Ocupados");
    private JLabel lblPalindromos = crearContador("Palíndromos");
    private JLabel lblIngresos = crearContador("Ingresos");

    public Main() {
        super("PalíndromoAir - Sistema de venta de boletos | Vuelo PA-030");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.WHITE);
        ((JPanel) getContentPane()).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel contadores = new JPanel(new GridLayout(1, 4, 10, 0));
        contadores.setBackground(Color.WHITE);
        contadores.add(lblDisponibles);
        contadores.add(lblOcupados);
        contadores.add(lblPalindromos);
        contadores.add(lblIngresos);

        JPanel izquierda = new JPanel(new BorderLayout(10, 10));
        izquierda.setBackground(Color.WHITE);
        izquierda.add(contadores, BorderLayout.NORTH);
        izquierda.add(crearCabina(), BorderLayout.CENTER);

        add(izquierda, BorderLayout.CENTER);
        add(crearPanelDerecho(), BorderLayout.EAST);
        add(crearConsola(), BorderLayout.SOUTH);

        actualizar();
        mostrar("Sistema listo. " + ASIENTOS + " asientos disponibles.");

        setSize(1000, 700);
        setLocationRelativeTo(null);
    }

    private Border borde(String titulo) {
        return BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), titulo);
    }

    private JLabel crearContador(String titulo) {
        JLabel l = new JLabel("0", SwingConstants.CENTER);
        l.setFont(new Font("Arial", Font.BOLD, 20));
        l.setBorder(borde(titulo));
        return l;
    }

    private JPanel crearCabina() {
        JPanel grid = new JPanel(new GridLayout(ASIENTOS / COLUMNAS, COLUMNAS, 6, 6));
        grid.setBackground(Color.WHITE);
        crearAsientos(0, grid);

        JPanel cabina = new JPanel(new BorderLayout());
        cabina.setBackground(Color.WHITE);
        cabina.setBorder(borde("CABINA - " + ASIENTOS + " asientos (5 filas x 6 columnas)"));
        cabina.add(grid, BorderLayout.CENTER);
        return cabina;
    }

    private void crearAsientos(int i, JPanel grid) {
        if (i >= ASIENTOS) {
            return;
        }
        JButton b = new JButton();
        b.setLayout(new GridLayout(3, 1));
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        b.addActionListener(e -> verDetalle(i));
        asientos[i] = b;
        grid.add(b);
        crearAsientos(i + 1, grid);
    }

    private JPanel crearPanelDerecho() {
        campoNombre.addActionListener(e -> vender());

        JPanel nombre = new JPanel(new GridLayout(2, 1, 0, 5));
        nombre.setBackground(Color.WHITE);
        nombre.setBorder(borde("Nombre del pasajero"));
        nombre.add(campoNombre);
        nombre.add(lblValidacion);

        JPanel botones = new JPanel(new GridLayout(3, 2, 8, 8));
        botones.setBackground(Color.WHITE);
        botones.setPreferredSize(new Dimension(0, 130));
        botones.add(crearBoton("Sell Ticket", () -> vender()));
        botones.add(crearBoton("Cancel Ticket", () -> cancelar()));
        botones.add(crearBoton("Search Passenger", () -> buscar()));
        botones.add(crearBoton("Print Passengers", () -> imprimir()));
        botones.add(crearBoton("View Income", () -> verIngresos()));
        botones.add(crearBoton("Dispatch", () -> despachar()));

        JTextArea info = new JTextArea(
                "Tarifa base: $100.00\n"
                        + "Descuento palíndromo: 20%\n"
                        + "Capacidad: " + ASIENTOS + " asientos\n\n"
                        + "Atajos y ayudas:\n"
                        + "- Enter = Sell Ticket\n"
                        + "- Clic en asiento = ver detalle\n"
                        + "- Dispatch pide confirmación");
        info.setEditable(false);
        info.setBorder(borde("Información"));

        JPanel arriba = new JPanel(new BorderLayout(10, 10));
        arriba.setBackground(Color.WHITE);
        arriba.add(nombre, BorderLayout.NORTH);
        arriba.add(botones, BorderLayout.CENTER);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(300, 0));
        panel.add(arriba, BorderLayout.NORTH);
        panel.add(info, BorderLayout.CENTER);
        return panel;
    }

    private JButton crearBoton(String texto, Runnable accion) {
        JButton b = new JButton(texto);
        b.setFocusPainted(false);
        b.setBackground(Color.WHITE);
        b.addActionListener(e -> accion.run());
        return b;
    }

    private JScrollPane crearConsola() {
        consola.setEditable(false);
        consola.setBackground(Color.BLACK);
        consola.setForeground(Color.WHITE);
        consola.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane scroll = new JScrollPane(consola);
        scroll.setBorder(borde("Consola del sistema"));
        scroll.setPreferredSize(new Dimension(0, 160));
        return scroll;
    }

    private void actualizar() {
        pintarAsientos(0);
        int ocupados = contarOcupados(0);
        lblDisponibles.setText(String.valueOf(ASIENTOS - ocupados));
        lblOcupados.setText(String.valueOf(ocupados));
        lblPalindromos.setText(String.valueOf(contarPalindromos(0)));
        lblIngresos.setText(dinero(vuelo.income(0)));
    }

    private void pintarAsientos(int i) {
        if (i >= ASIENTOS) {
            return;
        }
        Ticket t = vuelo.getSeat(i);
        JButton b = asientos[i];
        b.removeAll();
        b.add(new JLabel(codigo(i), SwingConstants.CENTER));
        if (t == null) {
            b.add(new JLabel("Libre", SwingConstants.CENTER));
            b.setBackground(VERDE);
        } else {
            b.add(new JLabel(t.getNombre(), SwingConstants.CENTER));
            b.add(new JLabel(dinero(t.getmontop()), SwingConstants.CENTER));
            b.setBackground(t.palindromo() ? AMARILLO : ROJO);
        }
        b.revalidate();
        b.repaint();
        pintarAsientos(i + 1);
    }

    private int contarOcupados(int i) {
        if (i >= ASIENTOS) {
            return 0;
        }
        return (vuelo.getSeat(i) != null ? 1 : 0) + contarOcupados(i + 1);
    }

    private int contarPalindromos(int i) {
        if (i >= ASIENTOS) {
            return 0;
        }
        Ticket t = vuelo.getSeat(i);
        return (t != null && t.palindromo() ? 1 : 0) + contarPalindromos(i + 1);
    }

    private String leerNombre() {
        String nombre = campoNombre.getText().trim();
        if (nombre.isEmpty()) {
            lblValidacion.setText("Validación: el nombre no puede estar vacío");
            mostrar("Ingrese un nombre válido.");
            return null;
        }
        lblValidacion.setText(" ");
        return nombre;
    }

    private void vender() {
        String nombre = leerNombre();
        if (nombre == null) {
            return;
        }
        int libre = vuelo.firstAvailable(0);
        String respuesta = vuelo.sellTicket(nombre);
        Ticket t = (libre == -1) ? null : vuelo.getSeat(libre);
        if (t != null) {
            String texto = "Vendido: " + t.getNombre() + " en " + codigo(libre) + ", pagó " + dinero(t.getmontop());
            if (t.palindromo()) {
                texto += " (palíndromo, 20% de descuento)";
            }
            mostrar(texto);
            campoNombre.setText("");
        } else {
            mostrar(respuesta);
        }
        actualizar();
    }

    private void cancelar() {
        String nombre = leerNombre();
        if (nombre == null) {
            return;
        }
        int pos = vuelo.searchPassenger(nombre, 0);
        double reembolso = (pos == -1) ? 0 : vuelo.getSeat(pos).getmontop();
        if (vuelo.cancelTicket(nombre)) {
            mostrar("Cancelado: " + nombre + ". Reembolso de " + dinero(reembolso));
            campoNombre.setText("");
        } else {
            mostrar("No se encontró a " + nombre + ". No se pudo cancelar.");
        }
        actualizar();
    }

    private void buscar() {
        String nombre = leerNombre();
        if (nombre == null) {
            return;
        }
        int pos = vuelo.searchPassenger(nombre, 0);
        if (pos == -1) {
            mostrar("No se encontró a " + nombre + ".");
        } else {
            mostrar(nombre + " está en el asiento " + codigo(pos) + ".");
        }
    }

    private void imprimir() {
        String lista = vuelo.printPassengers(0);
        if (lista.isEmpty()) {
            mostrar("Sin pasajeros registrados.");
        } else {
            mostrar("Pasajeros del vuelo:\n" + lista);
        }
    }

    private void verIngresos() {
        mostrar("Ingresos actuales: " + dinero(vuelo.income(0)));
    }

    private void despachar() {
        int respuesta = JOptionPane.showConfirmDialog(this, "¿Desea despachar el vuelo?",
                "Dispatch", JOptionPane.YES_NO_OPTION);
        if (respuesta == JOptionPane.YES_OPTION) {
            mostrar(vuelo.dispatch());
            actualizar();
        }
    }

    private void verDetalle(int i) {
        Ticket t = vuelo.getSeat(i);
        if (t == null) {
            mostrar("El asiento " + codigo(i) + " está libre.");
        } else {
            JOptionPane.showMessageDialog(this, "Asiento: " + codigo(i) + "\n" + t.print());
        }
    }

    private void mostrar(String texto) {
        consola.append("> " + texto + "\n");
        consola.setCaretPosition(consola.getDocument().getLength());
    }

    private String codigo(int i) {
        return (char) ('A' + i / COLUMNAS) + "" + (i % COLUMNAS + 1);
    }

    private String dinero(double monto) {
        return String.format("$%.2f", monto);
    }

    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }
}