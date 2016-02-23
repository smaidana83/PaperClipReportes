package utils;

import java.text.NumberFormat;
import java.util.Locale;

import com.vaadin.data.util.converter.StringToDoubleConverter;
import com.vaadin.server.VaadinSession;

public class MyStringToDoubleConverter  extends StringToDoubleConverter{
	
	 @Override
	    protected NumberFormat getFormat(Locale locale) {		
	        NumberFormat format = super.getFormat(locale);
	        format.setGroupingUsed(true);
	        format.setMaximumFractionDigits(2);
	        format.setMinimumFractionDigits(2);	        
	        return format;	        
	    }

}
