package logica;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import datos.Datos;
import utils.Utils;
import valueObject.VOAcreedores;
import valueObject.VODesgloce;
import valueObject.VODeudores;
import valueObject.VOFlujoCaja;
import valueObject.VOMoneda;
import valueObject.VOPresupuesto;
import valueObject.VOTotalEnCajaDesgloce;
import valueObject.VOTotalEnCajaDesglocePorFecha;

public class Logica implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Datos datos = new Datos();
	
//	public double TotalEnCaja(Date fecha){
//		return datos.TotalEnCaja(Utils.convertDateToString(fecha));		
//	}
	
	public ArrayList<VOFlujoCaja> FlujoDeCaja(Date fecha, int idMoneda){
		return datos.FlujoDeCaja(Utils.convertDateToString(fecha),idMoneda);		
	}
	
	public ArrayList<VODeudores> Deudores(int idMoneda){
		return datos.Deudores(idMoneda);
	}
	
	public ArrayList<VODesgloce> DesgloceVentasDiario(Date fecha){
		return datos.DesgloceVentasDiario(Utils.convertDateToString(fecha));
	}
	
	public ArrayList<VOMoneda> ObtenerMonedas(){
		return datos.ObtenerMonedas();
	}
	
	public ArrayList<VODesgloce> DesgloceVentasMensual(Date fechaInicial, Date fechaFinal){
		if(fechaFinal.after(fechaInicial)){
			return datos.DesgloceVentasMensual(Utils.convertDateToString(fechaInicial), Utils.convertDateToString(fechaFinal));
		}else{
			return null;
		}
	}
	
	public ArrayList<VOTotalEnCajaDesgloce> TotalEnCajaDesgloce(Date fecha){
		return datos.TotalEnCajaDesgloce(Utils.convertDateToString(fecha));
	}
	
	public ArrayList<VOPresupuesto> Presupuesto(){
		return datos.Presupuesto();
	}
	
	public ArrayList<VOAcreedores> Acreedores(int idMoneda){
		return datos.Acreedores(idMoneda);
	}
	
	public ArrayList<VOTotalEnCajaDesglocePorFecha> TotalEnCajaDesglocePorFecha(Date fechaInicial, Date fechaFinal){
		ArrayList<VOTotalEnCajaDesglocePorFecha> array = new ArrayList<VOTotalEnCajaDesglocePorFecha>();
		ArrayList<VOTotalEnCajaDesglocePorFecha> haber = datos.TotalEnCajaDesglocePorFechaHaber(Utils.convertDateToString(fechaInicial), Utils.convertDateToString(fechaFinal));
		ArrayList<VOTotalEnCajaDesglocePorFecha> debe = datos.TotalEnCajaDesglocePorFechaDeber(Utils.convertDateToString(fechaInicial), Utils.convertDateToString(fechaFinal));
		
		for (VOTotalEnCajaDesglocePorFecha VOhaber : haber) {
			VOTotalEnCajaDesglocePorFecha add = new VOTotalEnCajaDesglocePorFecha();
			for (VOTotalEnCajaDesglocePorFecha VOdebe : debe) {
				if(VOhaber.getFecha().equals(VOdebe.getFecha())){					
					add.setTotal(VOdebe.getTotal());					
				}			
			}
			add.setTotal(add.getTotal() + VOhaber.getTotal());
			add.setFecha(VOhaber.getFecha());
			array.add(add);			
		}
		return array;
	}

}
