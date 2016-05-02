package valueObject;

import java.io.Serializable;

public class VODesgloce implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private double cantidad;
	private String rubro;
	
	public double getCantidad() {
		return cantidad;
	}
	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}
	public String getRubro() {
		return rubro;
	}
	public void setRubro(String rubro) {
		this.rubro = rubro;
	}
}
