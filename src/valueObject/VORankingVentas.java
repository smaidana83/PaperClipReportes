package valueObject;

import java.io.Serializable;

public class VORankingVentas implements Serializable{

	private static final long serialVersionUID = 1L;
	private String item;	
	private double cantidad;
	private double total;
	
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public double getCantidad() {
		return cantidad;
	}
	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
}
