package valueObject;

import java.io.Serializable;

public class VOTotalEnCajaDesgloce implements Serializable {
	private static final long serialVersionUID = 1L;
	String descripcion;
	double credito;
	double debito;
	
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public double getCredito() {
		return credito;
	}
	public void setCredito(double credito) {
		this.credito = credito;
	}
	public double getDebito() {
		return debito;
	}
	public void setDebito(double debito) {
		this.debito = debito;
	}

}
