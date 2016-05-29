package valueObject;

import java.io.Serializable;

public class VOAcreedores implements Serializable{

	private static final long serialVersionUID = 1L;
	private String nombre;	
	private double saldo;	
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}	
	public double getSaldo() {
		return saldo;
	}
	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}	
}
