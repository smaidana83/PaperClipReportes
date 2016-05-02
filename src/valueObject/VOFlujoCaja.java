package valueObject;

import java.io.Serializable;

public class VOFlujoCaja implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String fecha;
	private double debito;
	private double credito;
	private double saldo;
	private String descripcion;	
	private double importeDeduccionIVA;
	
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public double getDebito() {
		return debito;
	}
	public void setDebito(double debito) {
		this.debito = debito;
	}
	public double getCredito() {
		return credito;
	}
	public void setCredito(double credito) {
		this.credito = credito;
	}
	public double getSaldo() {
		return saldo;
	}
	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}	
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}	
	public double getImporteDeduccionIVA() {
		return importeDeduccionIVA;
	}
	public void setImporteDeduccionIVA(double importeDeduccionIVA) {
		this.importeDeduccionIVA = importeDeduccionIVA;
	}
	
	

}
