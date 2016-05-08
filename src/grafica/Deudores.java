package grafica;

import java.util.ArrayList;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import logica.Logica;
import valueObject.VODeudores;
import valueObject.VOMoneda;

public class Deudores extends CustomComponent implements View {

	
	private static final long serialVersionUID = 1L;
	@AutoGenerated
	private VerticalLayout mainLayout;
	private Label lblTitle;
	private ComboBox cboMoneda;
	private Table tblDeudores;
	private BeanItemContainer<VODeudores> ds;	
	private Logica logica;
	private Label lblMessage;
	private Button btnExcelExport;

	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	public Deudores() {
		buildMainLayout();
		setCompositionRoot(mainLayout);
		logica = new Logica();

		cboMoneda.addValueChangeListener(new ValueChangeListener() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				// TODO Auto-generated method stub
				getDeudores();					
			}			
		});
	}

	@AutoGenerated
	private void buildMainLayout() {
		// the main layout and components will be created here
		mainLayout = new VerticalLayout();
		mainLayout.setSpacing(true);		
		mainLayout.setImmediate(true);
		mainLayout.setWidth("100%");
		mainLayout.setDefaultComponentAlignment(Alignment.TOP_CENTER);
		
		lblTitle = new Label("Deudores");
		lblTitle.setSizeUndefined();
		lblTitle.setStyleName("h2");
		mainLayout.addComponent(lblTitle);
		
		cboMoneda = new ComboBox();
		cboMoneda.setCaption("Moneda");	
		cboMoneda.setNullSelectionAllowed(false);
		cboMoneda.setImmediate(true);
		mainLayout.addComponent(cboMoneda);
		
		lblMessage = new Label("");
		lblMessage.setStyleName("messageAlert");
		lblMessage.setSizeUndefined();
		mainLayout.addComponent(lblMessage);
		
		btnExcelExport = new Button();
		btnExcelExport.setIcon(new ThemeResource("icons/download.png"));
		btnExcelExport.setStyleName(ValoTheme.BUTTON_BORDERLESS);
		
		tblDeudores = new Table();		
		tblDeudores.setSizeFull();
		
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
			
		if(tblDeudores != null){
			mainLayout.removeComponent(tblDeudores);		
		}
		
		if(btnExcelExport != null){
			mainLayout.removeComponent(btnExcelExport);
		}
		
		lblMessage.setValue("");
		
		cargarComboMonedas();		
	}
	
	private void getDeudores(){
		if(tblDeudores != null){
			mainLayout.removeComponent(tblDeudores);			
		}
		
		if(btnExcelExport != null){
			mainLayout.removeComponent(btnExcelExport);
		}
		
		if(cboMoneda.getValue() != null){			
			ArrayList<VODeudores> array =  logica.Deudores((Integer)cboMoneda.getValue());
			if(array != null && !array.isEmpty()){
				ds = new BeanItemContainer<VODeudores>(VODeudores.class, array);
				tblDeudores.removeAllItems();
				tblDeudores.setContainerDataSource(ds);
				tblDeudores.setPageLength(ds.size());
				
				mainLayout.addComponent(btnExcelExport);
				mainLayout.addComponent(tblDeudores);	
				
				lblMessage.setValue("");				
			}else{
				lblMessage.setValue("No hay registros para mostrar");
			}
		}		
	}
	
	private void cargarComboMonedas(){
		cboMoneda.clear();	
		
		for (VOMoneda moneda : logica.ObtenerMonedas()) {
			cboMoneda.addItem(moneda.getId());
			cboMoneda.setItemCaption(moneda.getId(), moneda.getSimbolo());			
		}	
		cboMoneda.setValue(cboMoneda.getItemIds().iterator().next());
	}

}
