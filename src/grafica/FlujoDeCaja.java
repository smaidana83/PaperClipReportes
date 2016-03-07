package grafica;

import java.util.Date;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;

import logica.Logica;
import valueObject.VOFlujoCaja;
import valueObject.VOMoneda;

public class FlujoDeCaja extends CustomComponent implements View {

	@AutoGenerated
	private VerticalLayout mainLayout;
	private HorizontalLayout firstRow;
	private PopupDateField date;
	private Label lblTitle;
	private ComboBox cboMoneda;
	private Grid grdFlujo;
	private BeanItemContainer<VOFlujoCaja> ds;	
	private Logica logica;

	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	public FlujoDeCaja() {
		buildMainLayout();
		setCompositionRoot(mainLayout);
		logica = new Logica();
		
		
		date.addValueChangeListener(new ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				// TODO Auto-generated method stub		
				getFlujoDeCaja();							
			}
		});
		
		cboMoneda.addValueChangeListener(new ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				// TODO Auto-generated method stub
				getFlujoDeCaja();				
			}
		});
	}

	@AutoGenerated
	private void buildMainLayout() {
		// the main layout and components will be created here
		mainLayout = new VerticalLayout();		
		
		lblTitle = new Label("Flujo de caja");
		mainLayout.addComponent(lblTitle);
		
		firstRow = new HorizontalLayout();	
		firstRow.setSpacing(true);
		firstRow.setImmediate(true);
		mainLayout.addComponent(firstRow);		
		
		date = new PopupDateField();				
		date.setCaption("Fecha a consultar");
		firstRow.addComponent(date);
		
		cboMoneda = new ComboBox();
		cboMoneda.setCaption("Moneda a consultar");
		firstRow.addComponent(cboMoneda);
		
		BeanItemContainer<VOFlujoCaja> ds = new BeanItemContainer<VOFlujoCaja>(VOFlujoCaja.class);
		
		grdFlujo = new Grid();
		grdFlujo.setCaption("Flujo de caja");
		grdFlujo.setSizeFull();
		
		
				
		
		
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		date.setValue(new Date());
		
		cargarComboMonedas();
		
	}
	
	private void getFlujoDeCaja(){
		//Comprobar que la fecha sea valida y el combo tenga algo seleccionado
		//Obtener el id de la seleccion del combo
		//invocar el metodo flujoDeCaja con la fecha y moneda seleccionada
		//si existe un grid eliminarlo
		//si no es nulo armar el grid y agregarlo al componente
		
		
			
		//ds.addAll(logica.FlujoDeCaja(date.getValue(),1));
		
	}
	
	private void cargarComboMonedas(){
		cboMoneda.clear();	
		
		for (VOMoneda moneda : logica.ObtenerMonedas()) {
			cboMoneda.addItem(moneda.getId());
			cboMoneda.setItemCaption(moneda.getId(), moneda.getSimbolo());			
		}
		
		
		
	}

}
