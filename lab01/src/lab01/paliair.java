
package lab01;


public class paliair {
    private Ticket asientos [] = new Ticket[30];
    public Ticket getSeat(int index) {
        return asientos[index];
    }
    public int firstAvailable(int index) {
        if (index>=asientos.length) {
            return -1;
        }
        if (asientos[index]==null) {
            return index;
        }
        return firstAvailable(index+1);
    }
    public int searchPassenger(String name, int index) {
        if (index >= asientos.length) {
            return -1;
        }
        if (asientos[index] != null) {
            String nombrePasajero = asientos[index].getNombre();
            if (nombrePasajero.equalsIgnoreCase(name)) {
                return index; 
            }
    }
    int siguientePosicion = searchPassenger(name, index + 1);
    return siguientePosicion;
    }   
    public boolean isPalindromo(String name) {
        String limpio = name.toLowerCase().replace(" ", "");
        if (limpio.isEmpty()) {
            return false;
        }
        return isPalindromo(limpio, 0, limpio.length()-1);
    }
    public boolean isPalindromo(String name,int inicio, int fin) {
        if (inicio>=fin) {
            return true;
        }
        if (name.charAt(inicio)!=name.charAt(fin)) {
            return false;
        }
        return isPalindromo(name, inicio+1,fin-1);
    }
    public String printPassengers(int index) {
        if (index>=asientos.length) {
            return " ";
        }
        String texto = "";
        if (asientos[index]!=null) {
            texto = "--- asiento"+(index + 1)+" ---\n"+asientos[index].print()+"\n";
        }
        return texto + printPassengers(index + 1);
    }
    public double income(int index) {
    if (index >= asientos.length) {
        return 0.0;
    }
    
    double montoAsiento = 0.0;
    
    if (asientos[index] != null) {
        montoAsiento = asientos[index].getmontop(); 
    }
    
    return montoAsiento + income(index + 1);    }

    public void reset(int index) {
    if (index >= asientos.length) {
        return;
    }
    
    asientos[index] = null;
    
    reset(index + 1);
    }

    public String sellTicket(String name) {
        double monto = 1000;
        double descuento;
        double montof;
        boolean palindromo = isPalindromo(name);
        int asiento=firstAvailable(0);
        if (asiento==-1) {
            return "El avion esta lleno, no se puede vender el ticket";
        }
        if (palindromo) {
            descuento = monto*0.20;
        } else {
            descuento =0;
        }
        montof=monto-descuento;
        Ticket pasajero = new Ticket(name,montof,monto,palindromo);
        asientos[asiento] = pasajero;
        return "Ticket vendido en el asiento " + (asiento + 1) + "\n\n" + pasajero.print();
    }

    public boolean cancelTicket(String name) {
        int asiento = searchPassenger(name, 0);

        if (asiento ==-1) {
            return false;
        }


        asientos[asiento] = null;
        return true;
    }

    public String dispatch() {
    double total = income(0);

    reset(0);

    return "Ingresos totales del vuelo: " + total + "\nEl avion ha sido vaciado exitosamente.";
    }
}
   

