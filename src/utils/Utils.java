package utils;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.vaadin.addon.tableexport.ExcelExport;
import com.vaadin.ui.Table;

public class Utils implements Serializable {

	private static final long serialVersionUID = 1L;

	public static String convertDateToString(Date date){		
		SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd");
		String formatted = format1.format(date.getTime());
		return formatted;				
	}
	
	public static void exportTableAsExcel(Table tblExport, String title, String date){
		ExcelExport excelExport;	           
        excelExport = new ExcelExport(tblExport);
        excelExport.excludeCollapsedColumns();
        excelExport.setReportTitle(title + " " + date);        
        excelExport.setExportFileName(title.replaceAll(" ", "_"));	                
        excelExport.export();			
	}

}
