package logica;

import java.util.ArrayList;
import java.util.Date;

import datos.Datos;
import utils.Utils;
import valueObject.VODesgloce;
import valueObject.VODeudores;
import valueObject.VOFlujoCaja;
import valueObject.VOMoneda;

public class Logica {
	private Datos datos = new Datos();
	
	public double TotalEnCaja(Date fecha){
		return datos.TotalEnCaja(Utils.convertDateToString(fecha));
	}
	
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
	
	public ArrayList<VODesgloce> DesgloceVentasMensual(Date fecha){
		return null;
	}

}
