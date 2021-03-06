package datos;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import valueObject.VOMoneda;
import valueObject.VOPresupuesto;
import valueObject.VORankingVentas;
import valueObject.VORankingVentasConGanancia;
import valueObject.VOTotalEnCajaDesgloce;
import valueObject.VOTotalEnCajaDesglocePorFecha;
import valueObject.VOAcreedores;
import valueObject.VODesgloce;
import valueObject.VODeudores;
import valueObject.VOFlujoCaja;

public class Datos implements Serializable{
	
	private static final long serialVersionUID = 1L;

	public Datos(){
		
	}
	
	/**
	 * Establece la conexion con el driver jdbc
	 */
	private Connection getConnection() throws ClassNotFoundException, SQLException{
		String username = "ro";
		String password = "ro";	
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); 
		String url = "jdbc:sqlserver://localhost\\MSSQLEXPRESS:49218;databaseName=GestionIntegral25;";		
		Connection conn = DriverManager.getConnection(url,username,password);
		return conn;
	}
		
	
	/**
	 * Devuelve el desgloce del calculo de plata en caja
	 */
	public ArrayList<VOTotalEnCajaDesgloce> TotalEnCajaDesgloce (String fecha){		
	    ResultSet rs = null;
	    Connection conn = null;
	    PreparedStatement preparedStatement = null;
	    ArrayList<VOTotalEnCajaDesgloce> array = new ArrayList<VOTotalEnCajaDesgloce>();
	    	    
	    try{		
	    	String sql = "SELECT mov.id_tipo_movimiento, tipo.str_descrip as 'Descripcion Movimiento', tipo.str_tipo_cr as 'Tipo Movimiento', sum(mov.dob_haber) as 'Credito', sum(mov.dob_debe) as 'Debito' "
	    			+ "FROM (Movimiento_Caja mov "
	    			+ "join Tipo_Movimiento_Caja tipo "
	    			+ "on  tipo.id = mov.id_tipo_movimiento) "
	    			+ "where mov.str_fecha = ? and mov.id_tipo_movimiento != 9 and mov.id_tipo_movimiento !=10 "
	    			+ "group by mov.id_tipo_movimiento, tipo.str_descrip, tipo.str_tipo_cr "
	    			+ "order by id_tipo_movimiento asc";
		
	    	conn = this.getConnection();	
	    	preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, fecha);			
			rs = preparedStatement.executeQuery();        
	        
	        while(rs.next()){
	        	VOTotalEnCajaDesgloce aux = new VOTotalEnCajaDesgloce();
	        	aux.setDescripcion(rs.getString("Descripcion Movimiento"));
	        	aux.setCredito(rs.getDouble("Credito"));
	        	aux.setDebito(rs.getDouble("Debito"));
	        	aux.setTipo(rs.getString("Tipo Movimiento"));
	        	
	        	array.add(aux);	        	
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
	    return array;
	}
	
	/***
	 * Devuelve un array con el flujo de la caja segun una fecha dada y la moneda
	 */
	public ArrayList<VOFlujoCaja> FlujoDeCaja(String fecha, int idMoneda){		
		PreparedStatement stmt = null;
	    ResultSet rs = null;
	    Connection conn = null;
	    ArrayList<VOFlujoCaja> arrayFlujoCaja = new ArrayList<VOFlujoCaja>();
	    
	    try{
	    	String sql = "select mc.str_fecha as 'Fecha', mc.dob_debe as 'Debito', mc.dob_haber as 'Credito', mc.dob_saldo 'Saldo', mc.str_descrip 'Descripcion', mc.importe_deduccion_iva 'Importe deduccion IVA' "+
						"from dbo.movimiento_caja mc " +
						"join dbo.monedas mon "+ 
							"on  mc.id_moneda = mon.id "+
							"join dbo.Tipo_Movimiento_Caja mov "+
							"on mc.id_tipo_movimiento = mov.id "+	
						"where mc.str_fecha like ? "+ 
							"and mc.id_tipo_movimiento != 10 "+
							"and mc.id_tipo_movimiento != 9 "+
							"and mc.id_moneda = ? ";
	    	conn = this.getConnection();
	    	stmt = conn.prepareStatement(sql);
	    	stmt.setString(1, fecha);		
	    	stmt.setInt(2, idMoneda);
	    	rs = stmt.executeQuery(); 
	        
	        while(rs.next()){
	        	VOFlujoCaja voFlujoCaja = new VOFlujoCaja();        	
	        	
	        	DateFormat format = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());	        	     
	        	DateFormat format2 = new SimpleDateFormat("dd/MM/yyyy");
	        	
	        	voFlujoCaja.setFecha(format2.format(format.parse(rs.getString("Fecha"))));       	
	        	voFlujoCaja.setDebito(rs.getDouble("Debito"));
	        	voFlujoCaja.setCredito(rs.getDouble("Credito"));
	        	voFlujoCaja.setSaldo(rs.getDouble("Saldo"));	        	
	        	voFlujoCaja.setDescripcion(rs.getString("Descripcion"));	        	
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
	    	String sql =  "select cli.str_nombre as 'Nombre empresa', (cc.saldo * -1) as 'Saldo deuda' "+
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
	        	
	        	voDeudores.setNombre(rs.getString("Nombre empresa"));
	        	voDeudores.setSaldo(rs.getDouble("Saldo deuda"));	        	        	
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
	 * Devuelve los deudores en una fecha dada
	 */
	public ArrayList<VODesgloce> DesgloceVentasDiario(String fecha){		
		PreparedStatement stmt = null;
	    ResultSet rs = null;
	    Connection conn = null;
	    ArrayList<VODesgloce> arrayDesgloce = new ArrayList<VODesgloce>();
	    
	    try{
	    	String sql =  "select sum(lde.cantidad) as 'Cantidad', sum(lde.total) as 'Total', ti.str_descrip as 'Rubro' "
	    			+ "from Linea_Documento_Emitido lde "
	    			+ "join Item_CompraVenta icv "
	    			+ "on lde.id_item = icv.id "
	    			+ "join Tipo_Item ti "
	    			+ "on icv.id_tipo = ti.id "
	    			+ "where lde.id_documento in ( "
	    			+ "select id_documento_emision from Movimiento_Caja mc "
	    			+ "where mc.id_documento_emision in ( "
	    			+ "select de1.id "
	    			+ "from dbo.Documentos_Emitidos de1 "
	    			+ "where de1.anulado != 1 "
	    			+ "and de1.str_fecha like ? "
	    			+ ") "
	    			+ ") "
	    			+ "group by ti.str_descrip" ;	
	    	
	    	conn = this.getConnection();
	    	stmt = conn.prepareStatement(sql);
	    	stmt.setString(1, fecha);	    	
	    	rs = stmt.executeQuery(); 
	        
	        while(rs.next()){
	        	VODesgloce voDesgloce = new VODesgloce();        	
	        	
	        	voDesgloce.setCantidad(rs.getDouble("Cantidad"));
	        	voDesgloce.setRubro(rs.getString("Rubro"));
	        	voDesgloce.setTotal(rs.getDouble("Total"));
	        	
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
	
	/**
	 * Devuelve los deudores en un rango de fecha dado 
	 */
	public ArrayList<VODesgloce> DesgloceVentasMensual(String fechaInicial, String fechaFinal){		
		PreparedStatement stmt = null;
	    ResultSet rs = null;
	    Connection conn = null;
	    ArrayList<VODesgloce> arrayDesgloce = new ArrayList<VODesgloce>();
	    
	    try{
	    	String sql =  "select sum(lde.cantidad) as 'Cantidad', sum(lde.total) as 'Total', ti.str_descrip as 'Rubro' "+
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
	    								"and de1.str_fecha between ? and ? "+
	    					") "+
	    			") "+
	    			"group by ti.str_descrip ";	
	    	
	    	conn = this.getConnection();
	    	stmt = conn.prepareStatement(sql);
	    	stmt.setString(1, fechaInicial);	
	    	stmt.setString(2, fechaFinal);
	    	rs = stmt.executeQuery(); 
	        
	        while(rs.next()){
	        	VODesgloce voDesgloce = new VODesgloce();        	
	        	
	        	voDesgloce.setCantidad(rs.getDouble("Cantidad"));
	        	voDesgloce.setRubro(rs.getString("Rubro"));
	        	voDesgloce.setTotal(rs.getDouble("Total"));
	        	
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
	
	/**
	 * Obtener las monedas del sistema
	 */
	public ArrayList<VOMoneda> ObtenerMonedas(){
		PreparedStatement stmt = null;
	    ResultSet rs = null;
	    Connection conn = null;
	    ArrayList<VOMoneda> arrayMoneda = new ArrayList<VOMoneda>();
	    
	    try{
	    	String sql =  "select id as 'Id', str_descrip as 'Descripcion', str_simbolo as 'Simbolo' "+
	    			"from Monedas ";	
	    	conn = this.getConnection();
	    	stmt = conn.prepareStatement(sql);	    		    	
	    	rs = stmt.executeQuery(); 
	        
	        while(rs.next()){
	        	VOMoneda voMoneda = new VOMoneda();        	
	        	
	        	voMoneda.setId(rs.getInt("Id"));
	        	voMoneda.setDescripcion(rs.getString("Descripcion"));
	        	voMoneda.setSimbolo(rs.getString("Simbolo"));
	        	arrayMoneda.add(voMoneda);
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
	    return arrayMoneda;
		
	}
	
	/**
	 * Obtener listado de productos con precio compra y precio venta
	 */
	public ArrayList<VOPresupuesto> Presupuesto(){
		PreparedStatement stmt = null;
	    ResultSet rs = null;
	    Connection conn = null;
	    ArrayList<VOPresupuesto> arrayPresupuesto = new ArrayList<VOPresupuesto>();
	    
	    try{
	    	String sql =  "SELECT icv.str_descrip as 'Producto' "
	    			+ ",icv.str_observaciones as 'Descripcion' "
	    			+ ",ti.str_descrip as 'Categoria' "
	    			+ ",icv.precio_compra_ultimo as 'Compra' "
	    			+ ",icv.precio_venta as 'Venta' "
	    			+ "FROM Item_CompraVenta icv "
	    			+ "join Tipo_Item ti "
	    			+ "on icv.id_tipo = ti.id "
	    			+ "order by icv.str_descrip";	
	    	conn = this.getConnection();
	    	stmt = conn.prepareStatement(sql);	    		    	
	    	rs = stmt.executeQuery(); 
	        
	        while(rs.next()){
	        	VOPresupuesto voPresupuesto = new VOPresupuesto();        	
	        	
	        	voPresupuesto.setProducto(rs.getString("Producto"));
	        	voPresupuesto.setDescripcion(rs.getString("Descripcion"));
	        	voPresupuesto.setCategoria(rs.getString("Categoria"));
	        	voPresupuesto.setCompra(rs.getDouble("Compra"));
	        	voPresupuesto.setVenta(rs.getDouble("Venta"));
	        	arrayPresupuesto.add(voPresupuesto);
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
	    return arrayPresupuesto;
		
	}
	
	/**
	 * Devuelve los acreedores segun una moneda 	 
	 */
	public ArrayList<VOAcreedores> Acreedores(int idMoneda){		
		PreparedStatement stmt = null;
	    ResultSet rs = null;
	    Connection conn = null;
	    ArrayList<VOAcreedores> arrayAcreedores = new ArrayList<VOAcreedores>();
	    
	    try{
	    	String sql =   "select prov.str_nombre as 'Proveedor', sum(mov.dob_debe) + sum(mov.dob_haber) as 'Saldo deuda' "
	    			+ "from Movimiento_CC_Proveedor mov "
	    			+ "join CC_Proveedor cc "
	    			+ "on cc.id = mov.id_cc "
	    			+ "join Proveedores prov "
	    			+ "on prov.id = cc.id_proveedor "
	    			+ "join Monedas mon "
	    			+ "on mon.id = cc.id_moneda "
	    			+ "where cc.id_moneda = ? "
	    			+ "group by prov.str_nombre, mon.str_simbolo "
	    			+ "having sum(mov.dob_debe) + sum(mov.dob_haber) != 0 ";
	    			
	    	conn = this.getConnection();
	    	stmt = conn.prepareStatement(sql);
	    	stmt.setInt(1, idMoneda);	    	
	    	rs = stmt.executeQuery(); 
	        
	        while(rs.next()){
	        	VOAcreedores voAcreedores = new VOAcreedores();        	
	        	
	        	voAcreedores.setNombre(rs.getString("Proveedor"));
	        	voAcreedores.setSaldo(rs.getDouble("Saldo deuda"));	        	        	
	        	arrayAcreedores.add(voAcreedores);
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
	    return arrayAcreedores;
	}
			
	/**
	 * Devuelve el haber en caja detallado por rango de fecha
	 */
	public ArrayList<VOTotalEnCajaDesglocePorFecha> TotalEnCajaDesglocePorFechaHaber (String fechaInicio, String fechaFin){		
	    ResultSet rs = null;
	    Connection conn = null;
	    PreparedStatement preparedStatement = null;
	    ArrayList<VOTotalEnCajaDesglocePorFecha> array = new ArrayList<VOTotalEnCajaDesglocePorFecha>();
	    	    
	    try{		
	    	String sql = "SELECT mov.str_fecha as 'Fecha', sum(mov.dob_haber) as 'Total' "
	    			+ "FROM (Movimiento_Caja mov "
	    			+ "join Tipo_Movimiento_Caja tipo "
	    			+ "on  tipo.id = mov.id_tipo_movimiento) "
	    			+ "where mov.str_fecha between ? and ? and mov.id_tipo_movimiento = 1  "
	    			+ "group by mov.str_fecha "
	    			+ "order by mov.str_fecha asc ";
		
	    	conn = this.getConnection();	
	    	preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, fechaInicio);	
			preparedStatement.setString(2, fechaFin);
			rs = preparedStatement.executeQuery();        
	        
	        while(rs.next()){
	        	VOTotalEnCajaDesglocePorFecha aux = new VOTotalEnCajaDesglocePorFecha();
	        	aux.setFecha(rs.getString("Fecha"));
	        	aux.setTotal(rs.getDouble("Total"));
	        		        	
	        	array.add(aux);	        	
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
	    return array;
	}
	
	/**
	 * Devuelve el debe en caja detallado por rango de fecha
	 */
	public ArrayList<VOTotalEnCajaDesglocePorFecha> TotalEnCajaDesglocePorFechaDeber (String fechaInicio, String fechaFin){		
	    ResultSet rs = null;
	    Connection conn = null;
	    PreparedStatement preparedStatement = null;
	    ArrayList<VOTotalEnCajaDesglocePorFecha> array = new ArrayList<VOTotalEnCajaDesglocePorFecha>();
	    	    
	    try{		
	    	String sql = "SELECT mov.str_fecha as 'Fecha', sum(mov.dob_debe) as 'Total' "
	    			+ "FROM (Movimiento_Caja mov "
	    			+ "join Tipo_Movimiento_Caja tipo "
	    			+ "on  tipo.id = mov.id_tipo_movimiento) "
	    			+ "where mov.str_fecha between ? and ? and mov.id_tipo_movimiento = 12  "
	    			+ "group by mov.str_fecha "
	    			+ "order by mov.str_fecha asc ";
		
	    	conn = this.getConnection();	
	    	preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, fechaInicio);	
			preparedStatement.setString(2, fechaFin);
			rs = preparedStatement.executeQuery();        
	        
	        while(rs.next()){
	        	VOTotalEnCajaDesglocePorFecha aux = new VOTotalEnCajaDesglocePorFecha();
	        	aux.setFecha(rs.getString("Fecha"));
	        	aux.setTotal(rs.getDouble("Total"));
	        		        	
	        	array.add(aux);	        	
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
	    return array;
	}
	
	/**
	 * Devuelve el ranking de items vendidos en un dia
	 */
	public ArrayList<VORankingVentas> RankingVentas (int top, String fecha){		
	    ResultSet rs = null;
	    Connection conn = null;
	    PreparedStatement preparedStatement = null;
	    ArrayList<VORankingVentas> array = new ArrayList<VORankingVentas>();
	    	    
	    try{		
	    	String sql = "select top (?) icv.str_descrip as 'Item' ,sum(lde.cantidad) as 'Cantidad', sum(lde.total) as 'Importe total' "
	    			+ "from Linea_Documento_Emitido lde "
	    			+ "join Item_CompraVenta icv "
	    			+ "on lde.id_item = icv.id "
	    			+ "where lde.id_documento in ( "
	    			+ "select id_documento_emision from Movimiento_Caja mc "
	    			+ "where mc.id_documento_emision in ( "
	    			+ "select de1.id  "
	    			+ "from dbo.Documentos_Emitidos de1  "
	    			+ "where de1.anulado != 1 "
	    			+ "and de1.str_fecha like ? "
	    			+ ") "
	    			+ ") "
	    			+ "group by icv.str_descrip "
	    			+ "order by Cantidad desc ";

		
	    	conn = this.getConnection();	
	    	preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, top);	
			preparedStatement.setString(2, fecha);
			rs = preparedStatement.executeQuery();        
	        
	        while(rs.next()){
	        	VORankingVentas aux = new VORankingVentas();
	        	aux.setItem(rs.getString("Item"));
	        	aux.setCantidad(rs.getDouble("Cantidad"));
	        	aux.setTotal(rs.getDouble("Importe Total"));
	        		        	
	        	array.add(aux);	        	
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
	    return array;
	}
	
	/**
	 * Devuelve el ranking de todos los items vendidos en un dia
	 */
	public ArrayList<VORankingVentas> RankingVentas (String fecha){		
	    ResultSet rs = null;
	    Connection conn = null;
	    PreparedStatement preparedStatement = null;
	    ArrayList<VORankingVentas> array = new ArrayList<VORankingVentas>();
	    	    
	    try{		
	    	String sql = "select icv.str_descrip as 'Item' ,sum(lde.cantidad) as 'Cantidad', sum(lde.total) as 'Importe total' "
	    			+ "from Linea_Documento_Emitido lde "
	    			+ "join Item_CompraVenta icv "
	    			+ "on lde.id_item = icv.id "
	    			+ "where lde.id_documento in ( "
	    			+ "select id_documento_emision from Movimiento_Caja mc "
	    			+ "where mc.id_documento_emision in ( "
	    			+ "select de1.id  "
	    			+ "from dbo.Documentos_Emitidos de1  "
	    			+ "where de1.anulado != 1 "
	    			+ "and de1.str_fecha like ? "
	    			+ ") "
	    			+ ") "
	    			+ "group by icv.str_descrip "
	    			+ "order by Cantidad desc ";

		
	    	conn = this.getConnection();	
	    	preparedStatement = conn.prepareStatement(sql);				
			preparedStatement.setString(1, fecha);
			rs = preparedStatement.executeQuery();        
	        
	        while(rs.next()){
	        	VORankingVentas aux = new VORankingVentas();
	        	aux.setItem(rs.getString("Item"));
	        	aux.setCantidad(rs.getDouble("Cantidad"));
	        	aux.setTotal(rs.getDouble("Importe Total"));
	        		        	
	        	array.add(aux);	        	
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
	    return array;
	}
	
	/**
	 * Devuelve el ranking de items vendidos en un rango de fechas
	 */
	public ArrayList<VORankingVentas> RankingVentasPorFecha (int top, String fechaInicial, String fechaFinal){		
	    ResultSet rs = null;
	    Connection conn = null;
	    PreparedStatement preparedStatement = null;
	    ArrayList<VORankingVentas> array = new ArrayList<VORankingVentas>();
	    	    
	    try{		
	    	String sql = "select top (?) icv.str_descrip as 'Item' ,sum(lde.cantidad) as 'Cantidad', sum(lde.total) as 'Importe total' "
	    			+ "from Linea_Documento_Emitido lde "
	    			+ "join Item_CompraVenta icv "
	    			+ "on lde.id_item = icv.id "
	    			+ "where lde.id_documento in ( "
	    			+ "select id_documento_emision from Movimiento_Caja mc "
	    			+ "where mc.id_documento_emision in ( "
	    			+ "select de1.id  "
	    			+ "from dbo.Documentos_Emitidos de1  "
	    			+ "where de1.anulado != 1 "
	    			+ "and de1.str_fecha between ? and ? "
	    			+ ") "
	    			+ ") "
	    			+ "group by icv.str_descrip "
	    			+ "order by Cantidad desc ";

		
	    	conn = this.getConnection();	
	    	preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, top);	
			preparedStatement.setString(2, fechaInicial);
			preparedStatement.setString(3, fechaFinal);
			rs = preparedStatement.executeQuery();        
	        
	        while(rs.next()){
	        	VORankingVentas aux = new VORankingVentas();
	        	aux.setItem(rs.getString("Item"));
	        	aux.setCantidad(rs.getDouble("Cantidad"));
	        	aux.setTotal(rs.getDouble("Importe Total"));
	        		        	
	        	array.add(aux);	        	
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
	    return array;
	}
	
	/**
	 * Devuelve el ranking de todos los items vendidos en un rango de fechas
	 */
	public ArrayList<VORankingVentas> RankingVentasPorFecha (String fechaInicial, String fechaFinal){		
	    ResultSet rs = null;
	    Connection conn = null;
	    PreparedStatement preparedStatement = null;
	    ArrayList<VORankingVentas> array = new ArrayList<VORankingVentas>();
	    	    
	    try{		
	    	String sql = "select icv.str_descrip as 'Item' ,sum(lde.cantidad) as 'Cantidad', sum(lde.total) as 'Importe total' "
	    			+ "from Linea_Documento_Emitido lde "
	    			+ "join Item_CompraVenta icv "
	    			+ "on lde.id_item = icv.id "
	    			+ "where lde.id_documento in ( "
	    			+ "select id_documento_emision from Movimiento_Caja mc "
	    			+ "where mc.id_documento_emision in ( "
	    			+ "select de1.id  "
	    			+ "from dbo.Documentos_Emitidos de1  "
	    			+ "where de1.anulado != 1 "
	    			+ "and de1.str_fecha between ? and ? "
	    			+ ") "
	    			+ ") "
	    			+ "group by icv.str_descrip "
	    			+ "order by Cantidad desc ";

		
	    	conn = this.getConnection();	
	    	preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, fechaInicial);
			preparedStatement.setString(2, fechaFinal);
			rs = preparedStatement.executeQuery();        
	        
	        while(rs.next()){
	        	VORankingVentas aux = new VORankingVentas();
	        	aux.setItem(rs.getString("Item"));
	        	aux.setCantidad(rs.getDouble("Cantidad"));
	        	aux.setTotal(rs.getDouble("Importe Total"));
	        		        	
	        	array.add(aux);	        	
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
	    return array;
	}
	
	/**
	 * Devuelve el ranking de items vendidos en un rango de fechas con ganancia
	 */
	public ArrayList<VORankingVentasConGanancia> RankingVentasPorFechaConGanancia (int top, String fechaInicial, String fechaFinal){		
	    ResultSet rs = null;
	    Connection conn = null;
	    PreparedStatement preparedStatement = null;
	    ArrayList<VORankingVentasConGanancia> array = new ArrayList<VORankingVentasConGanancia>();
	    	    
	    try{		
	    	String sql = "select top (?) icv.str_descrip as 'Item' , sum(lde.cantidad) as 'Cantidad', "
	    			+ "sum((icv.precio_compra_ultimo/icv.cant_ref_precio_compra)*lde.cantidad) as 'Costo', "
	    			+ "sum((icv.precio_venta/icv.cant_ref_precio_venta)*lde.cantidad) as 'Venta', "
	    			+ "sum(((icv.precio_venta/icv.cant_ref_precio_venta)*lde.cantidad) - ((icv.precio_compra_ultimo/icv.cant_ref_precio_compra)*lde.cantidad)) as  'Ganancia' "
	    			+ "from Linea_Documento_Emitido lde "
	    			+ "join Item_CompraVenta icv "
	    			+ "on lde.id_item = icv.id "
	    			+ "where lde.id_documento in ( "
	    			+ "select id_documento_emision from Movimiento_Caja mc "
	    			+ "where mc.id_documento_emision in ( "
	    			+ "select de1.id "
	    			+ "from dbo.Documentos_Emitidos de1 "
	    			+ "where de1.anulado != 1 "
	    			+ "and de1.str_fecha between ? and ? "
	    			+ ") "
	    			+ ") "
	    			+ "group by icv.str_descrip "
	    			+ "order by Ganancia desc ";

		
	    	conn = this.getConnection();	
	    	preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, top);	
			preparedStatement.setString(2, fechaInicial);
			preparedStatement.setString(3, fechaFinal);
			rs = preparedStatement.executeQuery();        
	        
	        while(rs.next()){
	        	VORankingVentasConGanancia aux = new VORankingVentasConGanancia();
	        	aux.setItem(rs.getString("Item"));
	        	aux.setCantidad(rs.getDouble("Cantidad"));
	        	aux.setCosto(rs.getDouble("Costo"));
	        	aux.setVenta(rs.getDouble("Venta"));
	        	aux.setGanancia(rs.getDouble("Ganancia"));
	        		        	
	        	array.add(aux);	        	
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
	    return array;
	}
	
	/**
	 * Devuelve el ranking de todos los items vendidos en un rango de fechas
	 */
	public ArrayList<VORankingVentasConGanancia> RankingVentasPorFechaConGanancia (String fechaInicial, String fechaFinal){		
	    ResultSet rs = null;
	    Connection conn = null;
	    PreparedStatement preparedStatement = null;
	    ArrayList<VORankingVentasConGanancia> array = new ArrayList<VORankingVentasConGanancia>();
	    	    
	    try{		
	    	String sql = "select icv.str_descrip as 'Item' , sum(lde.cantidad) as 'Cantidad', "
	    			+ "sum((icv.precio_compra_ultimo/icv.cant_ref_precio_compra)*lde.cantidad) as 'Costo', "
	    			+ "sum((icv.precio_venta/icv.cant_ref_precio_venta)*lde.cantidad) as 'Venta', "
	    			+ "sum(((icv.precio_venta/icv.cant_ref_precio_venta)*lde.cantidad) - ((icv.precio_compra_ultimo/icv.cant_ref_precio_compra)*lde.cantidad)) as  'Ganancia' "
	    			+ "from Linea_Documento_Emitido lde "
	    			+ "join Item_CompraVenta icv "
	    			+ "on lde.id_item = icv.id "
	    			+ "where lde.id_documento in ( "
	    			+ "select id_documento_emision from Movimiento_Caja mc "
	    			+ "where mc.id_documento_emision in ( "
	    			+ "select de1.id "
	    			+ "from dbo.Documentos_Emitidos de1 "
	    			+ "where de1.anulado != 1 "
	    			+ "and de1.str_fecha between ? and ? "
	    			+ ") "
	    			+ ") "
	    			+ "group by icv.str_descrip "
	    			+ "order by Ganancia desc ";

		
	    	conn = this.getConnection();	
	    	preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, fechaInicial);
			preparedStatement.setString(2, fechaFinal);
			rs = preparedStatement.executeQuery();        
	        
			while(rs.next()){
	        	VORankingVentasConGanancia aux = new VORankingVentasConGanancia();
	        	aux.setItem(rs.getString("Item"));
	        	aux.setCantidad(rs.getDouble("Cantidad"));
	        	aux.setCosto(rs.getDouble("Costo"));
	        	aux.setVenta(rs.getDouble("Venta"));
	        	aux.setGanancia(rs.getDouble("Ganancia"));
	        		        	
	        	array.add(aux);	        	
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
	    return array;
	}
}
