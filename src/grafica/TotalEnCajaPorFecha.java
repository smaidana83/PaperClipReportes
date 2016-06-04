package grafica;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.dussan.vaadin.dcharts.DCharts;
import org.dussan.vaadin.dcharts.data.DataSeries;
import org.dussan.vaadin.dcharts.metadata.renderers.SeriesRenderers;
import org.dussan.vaadin.dcharts.options.Highlighter;
import org.dussan.vaadin.dcharts.options.Options;
import org.dussan.vaadin.dcharts.options.SeriesDefaults;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
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
import valueObject.VOTotalEnCajaDesglocePorFecha;

public class TotalEnCajaPorFecha extends CustomComponent implements View{

	private static final long serialVersionUID = 1L;
	@AutoGenerated
	private VerticalLayout mainLayout;
	private HorizontalLayout startDateLayout;
	private HorizontalLayout endDateLayout;
	private PopupDateField startDate;
	private PopupDateField endDate;
	private Label lblTitle;	
	private Table tblDesgloce;
	private BeanItemContainer<VOTotalEnCajaDesglocePorFecha> ds;	
	private Logica logica;
	private Label lblMessage;
	private DCharts lineChart;
	private Button btnExcelExport;
	private Button btnStartAddOneDay;
	private Button btnStartSubstractOneDay;
	private Button btnEndAddOneDay;
	private Button btnEndSubstractOneDay;

	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	public TotalEnCajaPorFecha() {
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
				Utils.exportTableAsExcel(tblDesgloce,"Total en caja por fecha",startDate.getValue().toString() + "-" + endDate.getValue().toString());
			}
		});	

		startDate.addValueChangeListener(new ValueChangeListener() {
		
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				// TODO Auto-generated method stub		
				//getDesgloceMensual();	
				if(tblDesgloce != null){
					mainLayout.removeComponent(tblDesgloce);			
				}
				
				if(lineChart != null){
					mainLayout.removeComponent(lineChart);
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
				getDesgloceMensual();					
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
		
		lblTitle = new Label("Total en caja por fecha");
		lblTitle.setSizeUndefined();
		lblTitle.setStyleName("h2");
		mainLayout.addComponent(lblTitle);
		
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
		
		tblDesgloce = new Table();		
		tblDesgloce.setSizeFull();
		
		btnExcelExport = new Button();
		btnExcelExport.setIcon(new ThemeResource("icons/download.png"));
		btnExcelExport.setStyleName(ValoTheme.BUTTON_BORDERLESS);
		
		lineChart = new DCharts();	
		lineChart.setSizeUndefined();
		mainLayout.addComponent(lineChart);		
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		lblMessage.setValue("");
					
		if(tblDesgloce != null){
			mainLayout.removeComponent(tblDesgloce);		
		}	
		
		if(lineChart != null){
			mainLayout.removeComponent(lineChart);
		}
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, -7);
		startDate.setValue(cal.getTime());	
		endDate.setRangeStart(startDate.getValue());
		endDate.setValue(new Date());
	}
	
	private void getDesgloceMensual(){
		if(tblDesgloce != null){
			mainLayout.removeComponent(tblDesgloce);			
		}
		
		if(lineChart != null){
			mainLayout.removeComponent(lineChart);
		}
		
		if(btnExcelExport != null){
			mainLayout.removeComponent(btnExcelExport);
		}
		
		if(startDate.isValid() && endDate.isValid()){			
			ArrayList<VOTotalEnCajaDesglocePorFecha> array =  logica.TotalEnCajaDesglocePorFecha(startDate.getValue(), endDate.getValue());
			if(array != null && !array.isEmpty()){
				lblMessage.setValue("");
				buildChart(array);
				ds = new BeanItemContainer<VOTotalEnCajaDesglocePorFecha>(VOTotalEnCajaDesglocePorFecha.class, array);
				tblDesgloce.removeAllItems();
				tblDesgloce.setContainerDataSource(ds);
				tblDesgloce.setVisibleColumns("fecha","total");
				tblDesgloce.setPageLength(ds.size());
				
				mainLayout.addComponent(btnExcelExport);
				mainLayout.addComponent(tblDesgloce);	
				
			}else{
				lblMessage.setValue("No hay registros para mostrar");
			}
		}		
	}
	
	private void buildChart(ArrayList<VOTotalEnCajaDesglocePorFecha> array){
		DataSeries dataSeries = new DataSeries();	
		Double num[] = new Double[array.size()];
		int i = 0;
		for (VOTotalEnCajaDesglocePorFecha aux : array) {
			num[i++] = aux.getTotal();			
		}
		dataSeries.add((Object[])num);

		SeriesDefaults seriesDefaults = new SeriesDefaults();
		seriesDefaults.setRenderer(SeriesRenderers.LINE);
		//seriesDefaults.setRendererOptions(new LineRenderer());		
		
		Highlighter highlighter = new Highlighter();
		highlighter.setShow(true);
		highlighter.setShowTooltip(true);
		highlighter.setTooltipAlwaysVisible(true);
		highlighter.setKeepTooltipInsideChart(true);

		Options options = new Options();
		options.setSeriesDefaults(seriesDefaults);
		options.setHighlighter(highlighter);
				
		lineChart.setDataSeries(dataSeries);
		lineChart.setOptions(options);					
		lineChart.show();		
		
		mainLayout.addComponent(lineChart);
		
//		DCharts chart = new DCharts();
//		chart.setDataSeries(
//			new DataSeries()
//				.add(3, 7, 9, 1, 4, 6, 8, 2, 5))
//			.show();
//		mainLayout.addComponent(chart);

	}

}
