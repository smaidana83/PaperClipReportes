package datos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import valueObject.VOFlujoCaja;

public class Datos {
	
	public Datos(){
		
	}
	
	private Connection getConnection() throws ClassNotFoundException, SQLException{
		String username = "ro";
		String password = "ro";	
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); 
		String url = "jdbc:sqlserver://localhost\\MSSQLEXPRESS:51590;databaseName=GestionIntegral20;";		
		Connection conn = DriverManager.getConnection(url,username,password);
		return conn;
	}
	
	
	public double TotalEnCaja (){
		Statement stmt = null;
	    ResultSet rs = null;
	    Connection conn = null;
	    double total=0;
	    try{		
	    	String sql = "select( " +
						/*obtengo el ultimo movimiento*/
						"select mc1.dob_saldo " +
							"from Movimiento_Caja mc1 "+
							"where mc1.id in( "+
								"select max(mc2.id) "+
									"from dbo.Movimiento_Caja mc2 "+
									"where mc2.str_fecha like '20140822' "+
										"and mc2.id_tipo_movimiento != 10 "+
										"and mc2.id_tipo_movimiento != 9 " +
							") " +
						") - ( "+
						/*obtengo la apertura de caja*/
						"select mc3.dob_saldo "+
							"FROM dbo.Movimiento_Caja mc3 " +
							"where mc3.str_fecha like '20140822' "+
								"and mc3.id_tipo_movimiento = 13 "+
						") as 'Total en caja' ";
		
		
			conn = this.getConnection();
			stmt = conn.createStatement();
	        rs = stmt.executeQuery(sql);
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
	    return total;
	}
	
	
	public ArrayList<VOFlujoCaja> FlujoDeCaja(){		
		Statement stmt = null;
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
						"where mc.str_fecha like '20140901%' "+ 
							"and mc.id_tipo_movimiento != 10 "+
							"and mc.id_tipo_movimiento != 9 ";
	    	conn = this.getConnection();
			stmt = conn.createStatement();
	        rs = stmt.executeQuery(sql);
	        
	        if(rs.next()){
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
}
