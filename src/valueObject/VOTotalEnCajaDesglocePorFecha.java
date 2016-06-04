package valueObject;

import java.io.Serializable;

public class VOTotalEnCajaDesglocePorFecha implements Serializable {
	private static final long serialVersionUID = 1L;
	String fecha;
	double total;
	
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}		
}
