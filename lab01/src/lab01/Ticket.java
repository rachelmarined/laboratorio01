/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab01;

/**
 *
 * @author XPS 13
 */
public class Ticket {
    private String nombre;
    private double montoog;
    private double montop;
    private boolean palindromo;
    
    public Ticket (String nombre,double montoog,boolean palindromo){
        this.nombre=nombre;
        this.montoog=montoog;
        this.montop=montop;
        this.palindromo=palindromo;
    }
    public String getNombre(){
        return nombre;
        
    }
    public double getmontoog(){
        return montoog;
    }
    public double getmontop(){
        return montop;
    }
    public boolean palindromo(){
        return palindromo;
    }
    
}
