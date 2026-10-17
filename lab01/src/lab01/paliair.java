
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
}
