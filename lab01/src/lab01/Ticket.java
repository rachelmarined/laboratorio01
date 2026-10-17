package lab01;

public class Ticket {
    private String nombre;
    private double montoog;
    private double montop;
    private boolean palindromo;

    public Ticket(String nombre, double montoog, double montop, boolean palindromo) {
        this.nombre = nombre;
        this.montoog = montoog;
        this.montop = montop;
        this.palindromo = palindromo;
    }

    public String getNombre() {
        return nombre;
    }

    public double getmontoog() {
        return montoog;
    }

    public double getmontop() {
        return montop;
    }

    public boolean palindromo() {
        return palindromo;
    }

    public String print() {
        String texto = "Pasajero: " + nombre + "\n";
        texto += "Monto original: " + montoog + "\n";
        texto += "Monto pagado: " + montop + "\n";
        if (palindromo) {
            texto += "Si aplica descuento!";
        } else {
            texto += "No aplica descuento";
        }
        return texto;
    }
}