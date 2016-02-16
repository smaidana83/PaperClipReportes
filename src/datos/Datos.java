package datos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import valueObject.VODesgloce;
import valueObject.VODeudores;
import valueObject.VOFlujoCaja;

public class Datos {
	
	public Datos(){
		
	}
	
	/**
	 * Establece la conexion con el driver jdbc
	 */
	private Connection getConnection() throws ClassNotFoundException, SQLException{
		String username = "ro";
		String password = "ro";	
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); 
		String url = "jdbc:sqlserver://localhost\\MSSQLEXPRESS:51590;databaseName=GestionIntegral20;";		
		Connection conn = DriverManager.getConnection(url,username,password);
		return conn;
	}
	
	/**
	 * Devuelve el total de la caja dada una fecha	
	 */
	public double TotalEnCaja (String fecha){		
	    ResultSet rs = null;
	    Connection conn = null;
	    PreparedStatement preparedStatement = null;
	    
	    double total=0;
	    try{		
	    	String sql = "select( " +
						/*obtengo el ultimo movimiento*/
						"select mc1.dob_saldo " +
							"from Movimiento_Caja mc1 "+
							"where mc1.id in( "+
								"select max(mc2.id) "+
									"from dbo.Movimiento_Caja mc2 "+
									"where mc2.str_fecha like ? "+
										"and mc2.id_tipo_movimiento != 10 "+
										"and mc2.id_tipo_movimiento != 9 " +
							") " +
						") - ( "+
						/*obtengo la apertura de caja*/
						"select mc3.dob_saldo "+
							"FROM dbo.Movimiento_Caja mc3 " +
							"where mc3.str_fecha like ? "+
								"and mc3.id_tipo_movimiento = 13 "+
						") as 'Total en caja' ";
		
	    	conn = this.getConnection();	
	    	preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, fecha);
			preparedStatement.setString(2, fecha);
			rs = preparedStatement.executeQuery();        
	        
	        if(rs.next()){
	        	total = rs.getDouble(1);
	        }
	        
		} catch (Exception e) {
	         e.printStackTrace();
	      }
	      finally {
	         if (rs != null) {
	        	 try { 
	        		 rs.close(); 
	        	 } catch(Exception e) {
	        		 
	        	 }
	         }
	         if (preparedStatement != null){
	        	 try { 
	        		 preparedStatement.close(); 
        		 } catch(Exception e) {
        			 
        		 }
	         }
	         if (conn != null) {
	        	 try { 
	        		 conn.close(); 
	        	 } catch(Exception e) {
	        		 
	        	 }
	         }
	      }
	    return total;
	}
	
	/***
	 * Devuelve un array con el flujo de la caja segun una fecha dada	
	 */
	public ArrayList<VOFlujoCaja> FlujoDeCaja(String fecha){		
		PreparedStatement stmt = null;
	    ResultSet rs = null;
	    Connection conn = null;
	    ArrayList<VOFlujoCaja> arrayFlujoCaja = new ArrayList<VOFlujoCaja>();
	    
	    try{
	    	String sql = "select mc.str_fecha as 'Fecha', mc.dob_debe as 'Debito', mc.dob_haber as 'Credito', mc.dob_saldo 'Saldo', mov.str_descrip 'Movimiento', mc.str_descrip 'Descripcion', mon.str_simbolo 'Moneda', mc.importe_deduccion_iva 'Importe deduccion IVA' "+
						"from dbo.movimiento_caja mc " +
						"join dbo.monedas mon "+ 
							"on  mc.id_moneda = mon.id "+
							"join dbo.Tipo_Movimiento_Caja mov "+
							"on mc.id_tipo_movimiento = mov.id "+	
						"where mc.str_fecha like ? "+ 
							"and mc.id_tipo_movimiento != 10 "+
							"and mc.id_tipo_movimiento != 9 ";
	    	conn = this.getConnection();
	    	stmt = conn.prepareStatement(sql);
	    	stmt.setString(1, fecha);			
	    	rs = stmt.executeQuery(); 
	        
	        while(rs.next()){
	        	VOFlujoCaja voFlujoCaja = new VOFlujoCaja();        	
	        	
	        	DateFormat format = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
	        	Date date = format.parse(rs.getString("Fecha"));
	        	        	
	        	voFlujoCaja.setFecha(date);
	        	voFlujoCaja.setDebito(rs.getDouble("Debito"));
	        	voFlujoCaja.setCredito(rs.getDouble("Credito"));
	        	voFlujoCaja.setSaldo(rs.getDouble("Saldo"));
	        	voFlujoCaja.setMovimiento(rs.getString("Movimiento"));
	        	voFlujoCaja.setDescripcion(rs.getString("Descripcion"));
	        	voFlujoCaja.setMoneda(rs.getString("Moneda"));
	        	voFlujoCaja.setImporteDeduccionIVA(rs.getDouble("Importe deduccion IVA"));
	        	arrayFlujoCaja.add(voFlujoCaja);
	        }	        
	        
	    } catch (Exception e) {
	         e.printStackTrace();
	      }
	      finally {
	         if (rs != null) {
	        	 try { 
	        		 rs.close(); 
	        	 } catch(Exception e) {
	        		 
	        	 }
	         }
	         if (stmt != null){
	        	 try { 
	        		 stmt.close(); 
       		 } catch(Exception e) {
       			 
       		 }
	         }
	         if (conn != null) {
	        	 try { 
	        		 conn.close(); 
	        	 } catch(Exception e) {
	        		 
	        	 }
	         }
	      }
	    return arrayFlujoCaja;
	}
	
	/**
	 * Devuelve los deudores segun una moneda 	 
	 */
	public ArrayList<VODeudores> Deudores(int idMoneda){		
		PreparedStatement stmt = null;
	    ResultSet rs = null;
	    Connection conn = null;
	    ArrayList<VODeudores> arrayDeudores = new ArrayList<VODeudores>();
	    
	    try{
	    	String sql =  "select cli.str_nombre as 'Nombre empresa', cli.id as 'Id cliente', cli.empresa_particular as 'Tipo', cli.str_direccion as 'Direccion', (cc.saldo * -1) as 'Saldo deuda', mon.str_simbolo as 'Moneda' "+
	    			 		"from dbo.Clientes cli "+
    			 			"join CC_Cliente cc "+
    			 				"on cli.id = cc.id_cliente "+
    			 				"join dbo.monedas mon "+
    			 				"on cc.id_moneda = mon.id "+
			 				"where cli.id in ( "+
			 					"SELECT cc2.id_cliente "+
			 						"FROM CC_Cliente cc2 "+
			 						"where cc2.saldo != 0 and cc2.id_moneda = ? "+
	 						") and cc.id_moneda = ? ";
	    	conn = this.getConnection();
	    	stmt = conn.prepareStatement(sql);
	    	stmt.setInt(1, idMoneda);
	    	stmt.setInt(2, idMoneda);
	    	rs = stmt.executeQuery(); 
	        
	        while(rs.next()){
	        	VODeudores voDeudores = new VODeudores();        	
	        	
	        	voDeudores.setDireccion(rs.getString("Direccion"));
	        	voDeudores.setIdCliente(rs.getInt("Id cliente"));
	        	voDeudores.setMoneda(rs.getString("Moneda"));
	        	voDeudores.setNombre(rs.getString("Nombre empresa"));
	        	voDeudores.setSaldo(rs.getDouble("Saldo deuda"));
	        	voDeudores.setTipo(rs.getString("Tipo"));	        	
	        	arrayDeudores.add(voDeudores);
	        }	        
	        
	    } catch (Exception e) {
	         e.printStackTrace();
	      }
	      finally {
	         if (rs != null) {
	        	 try { 
	        		 rs.close(); 
	        	 } catch(Exception e) {
	        		 
	        	 }
	         }
	         if (stmt != null){
	        	 try { 
	        		 stmt.close(); 
       		 } catch(Exception e) {
       			 
       		 }
	         }
	         if (conn != null) {
	        	 try { 
	        		 conn.close(); 
	        	 } catch(Exception e) {
	        		 
	        	 }
	         }
	      }
	    return arrayDeudores;
	}
	
	/**
	 * Devuelve los deudores segun una moneda 	 
	 */
	public ArrayList<VODesgloce> DegloceVentas(String fecha){		
		PreparedStatement stmt = null;
	    ResultSet rs = null;
	    Connection conn = null;
	    ArrayList<VODesgloce> arrayDesgloce = new ArrayList<VODesgloce>();
	    
	    try{
	    	String sql =  "select sum(lde.cantidad) as 'Cantidad', ti.str_descrip as 'Rubro' "+
	    			"from Linea_Documento_Emitido lde "+
	    			"join Item_CompraVenta icv "+
	    				"on lde.id_item = icv.id "+
	    			"join Tipo_Item ti "+
	    				"on icv.id_tipo = ti.id "+
	    			"where lde.id_documento in ( "+
	    				"select id_documento_emision from Movimiento_Caja mc "+
	    					"where mc.id_documento_emision in ( "+		
	    						"select de1.id "+ 
	    							"from dbo.Documentos_Emitidos de1 "+ 
	    							"where de1.anulado != 1 "+
	    								"and de1.str_fecha like ? "+
	    					") "+
	    			") "+
	    			"group by ti.str_descrip ";	
	    	conn = this.getConnection();
	    	stmt = conn.prepareStatement(sql);
	    	stmt.setString(1, fecha);	    	
	    	rs = stmt.executeQuery(); 
	        
	        while(rs.next()){
	        	VODesgloce voDesgloce = new VODesgloce();        	
	        	
	        	voDesgloce.setCantidad(rs.getDouble("Cantidad"));
	        	voDesgloce.setRubro(rs.getString("Rubro"));	        		        	
	        	arrayDesgloce.add(voDesgloce);
	        }	        
	        
	    } catch (Exception e) {
	         e.printStackTrace();
	      }
	      finally {
	         if (rs != null) {
	        	 try { 
	        		 rs.close(); 
	        	 } catch(Exception e) {
	        		 
	        	 }
	         }
	         if (stmt != null){
	        	 try { 
	        		 stmt.close(); 
       		 } catch(Exception e) {
       			 
       		 }
	         }
	         if (conn != null) {
	        	 try { 
	        		 conn.close(); 
	        	 } catch(Exception e) {
	        		 
	        	 }
	         }
	      }
	    return arrayDesgloce;
	}
}
