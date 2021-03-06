package grafica;

import java.util.ArrayList;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.ValoTheme;

import logica.Logica;
import utils.Utils;
import valueObject.VOPresupuesto;

public class Presupuesto extends CustomComponent implements View {
	
/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	
	private static final long serialVersionUID = 1L;

	@AutoGenerated
	private VerticalLayout mainLayout;	
	private Label lblTitle;
	private Label lblMessage;	
	private Table tblPresupuesto;
	private BeanItemContainer<VOPresupuesto> ds;
	private Logica logica;
	private Button btnExcelExport;
	
	
	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	public Presupuesto() {
		buildMainLayout();
		setCompositionRoot(mainLayout);
		logica = new Logica();
		

		// TODO add user code here		
		
		 btnExcelExport.addClickListener(new ClickListener() {
				
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				Utils.exportTableAsExcel(tblPresupuesto,"Productos para presupuesto","");
			}
		});	
	}

	@AutoGenerated
	private VerticalLayout buildMainLayout() {
		// common part: create layout
		
		mainLayout = new VerticalLayout();
		mainLayout.setSpacing(true);		
		mainLayout.setImmediate(true);
		mainLayout.setWidth("100%");
		mainLayout.setDefaultComponentAlignment(Alignment.TOP_CENTER);
		
		lblTitle = new Label("Productos para presupuestar");	
		lblTitle.setSizeUndefined();
		lblTitle.setStyleName("h2");
		mainLayout.addComponent(lblTitle);		
		
		lblMessage = new Label("");
		lblMessage.setSizeUndefined();
		lblMessage.addStyleName("messageAlert");
		mainLayout.addComponent(lblMessage);		
								
		tblPresupuesto = new Table();
		tblPresupuesto.setSizeFull();
		
		btnExcelExport = new Button();
		btnExcelExport.setIcon(new ThemeResource("icons/download.png"));
		btnExcelExport.setStyleName(ValoTheme.BUTTON_BORDERLESS);
		
		return mainLayout;
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub		
		getPresupuesto();				
	}

	private void getPresupuesto() {
		if(tblPresupuesto != null){
			mainLayout.removeComponent(tblPresupuesto);
			mainLayout.removeComponent(btnExcelExport);			
		}
		
		ArrayList<VOPresupuesto> array =  logica.Presupuesto();
		if(array != null && !array.isEmpty()){
			ds = new BeanItemContainer<VOPresupuesto>(VOPresupuesto.class, array);						
			tblPresupuesto.removeAllItems();
			tblPresupuesto.setContainerDataSource(ds);						
			tblPresupuesto.setVisibleColumns("producto","descripcion","categoria","compra","venta");
			tblPresupuesto.setPageLength(ds.size());
			
			mainLayout.addComponent(btnExcelExport);	
			mainLayout.addComponent(tblPresupuesto);					        
			
			lblMessage.setValue("");		
		}else{
			lblMessage.setValue("No hay registros para mostrar");
		}		
		
	}

}
