package grafica;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.dussan.vaadin.dcharts.DCharts;
import org.dussan.vaadin.dcharts.data.DataSeries;
import org.dussan.vaadin.dcharts.metadata.LegendPlacements;
import org.dussan.vaadin.dcharts.metadata.locations.LegendLocations;
import org.dussan.vaadin.dcharts.metadata.renderers.SeriesRenderers;
import org.dussan.vaadin.dcharts.options.Highlighter;
import org.dussan.vaadin.dcharts.options.Legend;
import org.dussan.vaadin.dcharts.options.Options;
import org.dussan.vaadin.dcharts.options.SeriesDefaults;
import org.dussan.vaadin.dcharts.renderers.series.PieRenderer;

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
import valueObject.VODesgloce;
import valueObject.VOMoneda;
import valueObject.VORankingVentas;

public class RankingDeVentasPorFecha extends CustomComponent implements View {

	private static final long serialVersionUID = 1L;
	@AutoGenerated
	private VerticalLayout mainLayout;
	private HorizontalLayout startDateLayout;
	private HorizontalLayout endDateLayout;
	private PopupDateField startDate;
	private PopupDateField endDate;
	private Label lblTitle;	
	private Table tblRanking;
	private BeanItemContainer<VORankingVentas> ds;	
	private Logica logica;
	private Label lblMessage;	
	private Button btnExcelExport;
	private Button btnStartAddOneDay;
	private Button btnStartSubstractOneDay;
	private Button btnEndAddOneDay;
	private Button btnEndSubstractOneDay;
	private ComboBox cboTop;

	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	public RankingDeVentasPorFecha() {
		buildMainLayout();
		setCompositionRoot(mainLayout);
		logica = new Logica();
		
		btnStartAddOneDay.addClickListener(new ClickListener() {
			
			private static final long serialVersionUID = 1L;
	
			@Override
			public void buttonClick(ClickEvent event) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(startDate.getValue());
				cal.add(Calendar.DATE, 1);
				startDate.setValue(cal.getTime());		            
			}
		});	
		
		btnStartSubstractOneDay.addClickListener(new ClickListener() {
			
			private static final long serialVersionUID = 1L;
	
			@Override
			public void buttonClick(ClickEvent event) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(startDate.getValue());
				cal.add(Calendar.DATE, -1);
				startDate.setValue(cal.getTime());		            
			}
		});	
		
		btnEndAddOneDay.addClickListener(new ClickListener() {
			
			private static final long serialVersionUID = 1L;
	
			@Override
			public void buttonClick(ClickEvent event) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(endDate.getValue());
				cal.add(Calendar.DATE, 1);
				endDate.setValue(cal.getTime());		            
			}
		});	
		
		btnEndSubstractOneDay.addClickListener(new ClickListener() {
			
			private static final long serialVersionUID = 1L;
	
			@Override
			public void buttonClick(ClickEvent event) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(endDate.getValue());
				cal.add(Calendar.DATE, -1);
				endDate.setValue(cal.getTime());		            
			}
		});	
		
		 btnExcelExport.addClickListener(new ClickListener() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				Utils.exportTableAsExcel(tblRanking,"Ranking de Ventas Por Fecha",startDate.getValue().toString() + "-" + endDate.getValue().toString());
			}
		});	
		 
		 startDate.addValueChangeListener(new ValueChangeListener() {
				
				private static final long serialVersionUID = 1L;

				@Override
				public void valueChange(ValueChangeEvent event) {
				
					if(tblRanking != null){
						mainLayout.removeComponent(tblRanking);			
					}				
					
					if(btnExcelExport != null){
						mainLayout.removeComponent(btnExcelExport);
					}
					endDate.setRangeStart(startDate.getValue());
				}
			});

		endDate.addValueChangeListener(new ValueChangeListener() {
		
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
		
		lblTitle = new Label("Ranking de ventas por fecha");
		lblTitle.setSizeUndefined();
		lblTitle.setStyleName("h2");
		mainLayout.addComponent(lblTitle);
		
		cboTop = new ComboBox();
		cboTop.setCaption("Mostrar");	
		cboTop.setNullSelectionAllowed(false);
		cboTop.setImmediate(true);
		mainLayout.addComponent(cboTop);
		
		startDateLayout = new HorizontalLayout();
		startDateLayout.setSpacing(false);
		startDateLayout.setImmediate(true);
		mainLayout.addComponent(startDateLayout);
		
		btnStartSubstractOneDay = new Button(new ThemeResource("icons/angle-left.png"));
		btnStartSubstractOneDay.setStyleName(ValoTheme.BUTTON_BORDERLESS);		
		startDateLayout.addComponent(btnStartSubstractOneDay);
		startDateLayout.setComponentAlignment(btnStartSubstractOneDay, Alignment.BOTTOM_CENTER);
		
		startDate = new PopupDateField();				
		startDate.setCaption("Fecha inicial");
		startDate.setImmediate(true);		
		startDateLayout.addComponent(startDate);
		
		btnStartAddOneDay = new Button(new ThemeResource("icons/angle-right.png"));
		btnStartAddOneDay.setStyleName(ValoTheme.BUTTON_BORDERLESS);
		startDateLayout.addComponent(btnStartAddOneDay);
		startDateLayout.setComponentAlignment(btnStartAddOneDay, Alignment.BOTTOM_CENTER);
		
		endDateLayout = new HorizontalLayout();
		endDateLayout.setSpacing(false);
		endDateLayout.setImmediate(true);
		mainLayout.addComponent(endDateLayout);
		
		btnEndSubstractOneDay = new Button(new ThemeResource("icons/angle-left.png"));
		btnEndSubstractOneDay.setStyleName(ValoTheme.BUTTON_BORDERLESS);		
		endDateLayout.addComponent(btnEndSubstractOneDay);
		endDateLayout.setComponentAlignment(btnEndSubstractOneDay, Alignment.BOTTOM_CENTER);
		
		endDate = new PopupDateField();				
		endDate.setCaption("Fecha final");
		endDate.setImmediate(true);		
		endDateLayout.addComponent(endDate);
		
		btnEndAddOneDay = new Button(new ThemeResource("icons/angle-right.png"));
		btnEndAddOneDay.setStyleName(ValoTheme.BUTTON_BORDERLESS);
		endDateLayout.addComponent(btnEndAddOneDay);
		endDateLayout.setComponentAlignment(btnEndAddOneDay, Alignment.BOTTOM_CENTER);
		
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
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, -7);
		startDate.setValue(cal.getTime());	
		endDate.setRangeStart(startDate.getValue());
		endDate.setValue(new Date());
	}
	
	private void getRankingDiario(){
		if(tblRanking != null){
			mainLayout.removeComponent(tblRanking);			
		}
				
		if(btnExcelExport != null){
			mainLayout.removeComponent(btnExcelExport);
		}
		
		if(startDate.isValid() && endDate.isValid()){	
			ArrayList<VORankingVentas> array =  logica.RankingVentasPorFecha((Integer)cboTop.getValue(), startDate.getValue(), endDate.getValue());
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
