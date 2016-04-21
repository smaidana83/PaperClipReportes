package valueObject;

import java.util.Date;

public class VOFlujoCaja {
	
	String fecha;
	double debito;
	double credito;
	double saldo;
	String descripcion;	
	double importeDeduccionIVA;
	
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
