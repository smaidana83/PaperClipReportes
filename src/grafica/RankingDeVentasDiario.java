package grafica;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.ValoTheme;

import logica.Logica;
import utils.Utils;
import valueObject.VORankingVentas;

public class RankingDeVentasDiario extends CustomComponent implements View {

	private static final long serialVersionUID = 1L;
	@AutoGenerated
	private VerticalLayout mainLayout;
	private HorizontalLayout dateLayout;
	private PopupDateField date;
	private Label lblTitle;	
	private Table tblRanking;
	private BeanItemContainer<VORankingVentas> ds;	
	private Logica logica;
	private Label lblMessage;	
	private Button btnExcelExport;
	private Button btnAddOneDay;
	private Button btnSubstractOneDay;
	private ComboBox cboTop;

	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	public RankingDeVentasDiario() {
		buildMainLayout();
		setCompositionRoot(mainLayout);
		logica = new Logica();
		
		btnAddOneDay.addClickListener(new ClickListener() {
			
			private static final long serialVersionUID = 1L;
	
			@Override
			public void buttonClick(ClickEvent event) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(date.getValue());
				cal.add(Calendar.DATE, 1);
				date.setValue(cal.getTime());		            
			}
		});	
		
		btnSubstractOneDay.addClickListener(new ClickListener() {
			
			private static final long serialVersionUID = 1L;
	
			@Override
			public void buttonClick(ClickEvent event) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(date.getValue());
				cal.add(Calendar.DATE, -1);
				date.setValue(cal.getTime());		            
			}
		});	
		
		 btnExcelExport.addClickListener(new ClickListener() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				Utils.exportTableAsExcel(tblRanking,"Ranking de Ventas Diario",date.getValue().toString());
			}
		});	

		date.addValueChangeListener(new ValueChangeListener() {
		
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				// TODO Auto-generated method stub		
				getRankingDiario();					
			}
		});	
		
		cboTop.addValueChangeListener(new ValueChangeListener() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				// TODO Auto-generated method stub		
				if(tblRanking != null){
					mainLayout.removeComponent(tblRanking);		
				}		
				
				if(btnExcelExport != null){
					mainLayout.removeComponent(btnExcelExport);
				}					
			}
		});
		
	}

	@AutoGenerated
	private void buildMainLayout() {
		// the main layout and components will be created here
		mainLayout = new VerticalLayout();
		mainLayout = new VerticalLayout();	
		mainLayout.setSpacing(true);		
		mainLayout.setImmediate(true);
		mainLayout.setWidth("100%");
		mainLayout.setDefaultComponentAlignment(Alignment.TOP_CENTER);
		
		lblTitle = new Label("Ranking de ventas diario");
		lblTitle.setSizeUndefined();
		lblTitle.setStyleName("h2");
		mainLayout.addComponent(lblTitle);
		
		cboTop = new ComboBox();
		cboTop.setCaption("Mostrar");	
		cboTop.setNullSelectionAllowed(false);
		cboTop.setImmediate(true);
		mainLayout.addComponent(cboTop);
		
		dateLayout = new HorizontalLayout();
		dateLayout.setSpacing(false);
		dateLayout.setImmediate(true);
		mainLayout.addComponent(dateLayout);
		
		btnSubstractOneDay = new Button(new ThemeResource("icons/angle-left.png"));
		btnSubstractOneDay.setStyleName(ValoTheme.BUTTON_BORDERLESS);		
		dateLayout.addComponent(btnSubstractOneDay);
		dateLayout.setComponentAlignment(btnSubstractOneDay, Alignment.BOTTOM_CENTER);
		
		date = new PopupDateField();				
		date.setCaption("Fecha");
		date.setImmediate(true);		
		dateLayout.addComponent(date);
		
		btnAddOneDay = new Button(new ThemeResource("icons/angle-right.png"));
		btnAddOneDay.setStyleName(ValoTheme.BUTTON_BORDERLESS);
		dateLayout.addComponent(btnAddOneDay);
		dateLayout.setComponentAlignment(btnAddOneDay, Alignment.BOTTOM_CENTER);
		
		lblMessage = new Label("");
		lblMessage.setStyleName("messageAlert");
		lblMessage.setSizeUndefined();
		mainLayout.addComponent(lblMessage);
		
		tblRanking = new Table();		
		tblRanking.setSizeFull();
		
		btnExcelExport = new Button();
		btnExcelExport.setIcon(new ThemeResource("icons/download.png"));
		btnExcelExport.setStyleName(ValoTheme.BUTTON_BORDERLESS);				
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		lblMessage.setValue("");
					
		if(tblRanking != null){
			mainLayout.removeComponent(tblRanking);		
		}		
		
		if(btnExcelExport != null){
			mainLayout.removeComponent(btnExcelExport);
		}
		
		cboTop.clear();	
		cboTop.addItem(10);
		cboTop.setItemCaption(10,"Top 10");		
		cboTop.addItem(20);
		cboTop.setItemCaption(20,"Top 20");
		cboTop.addItem(50);
		cboTop.setItemCaption(50,"Top 50");
		cboTop.addItem(100);
		cboTop.setItemCaption(100,"Top 100");
		cboTop.addItem(0);
		cboTop.setItemCaption(0,"Todos");
		
		cboTop.setValue(cboTop.getItemIds().iterator().next());
		
		date.setValue(new Date());	
	}
	
	private void getRankingDiario(){
		if(tblRanking != null){
			mainLayout.removeComponent(tblRanking);			
		}
				
		if(btnExcelExport != null){
			mainLayout.removeComponent(btnExcelExport);
		}
		
		if(date.isValid()){	
			ArrayList<VORankingVentas> array =  logica.RankingVentasDiario((Integer)cboTop.getValue(), date.getValue());
			if(array != null && !array.isEmpty()){
				lblMessage.setValue("");
				
				ds = new BeanItemContainer<VORankingVentas>(VORankingVentas.class, array);
				tblRanking.removeAllItems();
				tblRanking.setContainerDataSource(ds);
				tblRanking.setVisibleColumns("item","cantidad","total");
				tblRanking.setPageLength(ds.size());
				
				mainLayout.addComponent(btnExcelExport);
				mainLayout.addComponent(tblRanking);	
				
			}else{
				lblMessage.setValue("No hay registros para mostrar");
			}
		}		
	}
}
