package valueObject;

import java.util.Date;

public class VOFlujoCaja {
	
	Date fecha;
	double debito;
	double credito;
	double saldo;
	String movimiento;
	String descripcion;
	String moneda;
	double importeDeduccionIVA;
	
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
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
	public String getMovimiento() {
		return movimiento;
	}
	public void setMovimiento(String movimiento) {
		this.movimiento = movimiento;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getMoneda() {
		return moneda;
	}
	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}
	public double getImporteDeduccionIVA() {
		return importeDeduccionIVA;
	}
	public void setImporteDeduccionIVA(double importeDeduccionIVA) {
		this.importeDeduccionIVA = importeDeduccionIVA;
	}
	
	

}
