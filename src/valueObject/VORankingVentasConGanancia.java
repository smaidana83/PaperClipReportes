package valueObject;

import java.io.Serializable;

public class VORankingVentasConGanancia implements Serializable{

	private static final long serialVersionUID = 1L;
	private String item;	
	private double cantidad;
	private double costo;
	private double venta;
	private double ganancia;
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
	public double getCosto() {
		return costo;
	}
	public void setCosto(double costo) {
		this.costo = costo;
	}
	public double getVenta() {
		return venta;
	}
	public void setVenta(double venta) {
		this.venta = venta;
	}
	public double getGanancia() {
		return ganancia;
	}
	public void setGanancia(double ganancia) {
		this.ganancia = ganancia;
	}
		
}
