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

public class DesgloceDeVentasDiario extends CustomComponent implements View {

	private static final long serialVersionUID = 1L;
	@AutoGenerated
	private VerticalLayout mainLayout;
	private HorizontalLayout dateLayout;
	private PopupDateField date;
	private Label lblTitle;	
	private Table tblDesgloce;
	private BeanItemContainer<VODesgloce> ds;	
	private Logica logica;
	private Label lblMessage;
	private DCharts pieChart;
	private Button btnExcelExport;
	private Button btnAddOneDay;
	private Button btnSubstractOneDay;

	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	public DesgloceDeVentasDiario() {
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
				Utils.exportTableAsExcel(tblDesgloce,"Desgloce de Ventas Diario",date.getValue().toString());
			}
		});	

		date.addValueChangeListener(new ValueChangeListener() {
		
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				// TODO Auto-generated method stub		
				getDesgloceDiario();					
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
		
		lblTitle = new Label("Desgloce de ventas diario");
		lblTitle.setSizeUndefined();
		lblTitle.setStyleName("h2");
		mainLayout.addComponent(lblTitle);
		
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
		
		tblDesgloce = new Table();		
		tblDesgloce.setSizeFull();
		
		btnExcelExport = new Button();
		btnExcelExport.setIcon(new ThemeResource("icons/download.png"));
		btnExcelExport.setStyleName(ValoTheme.BUTTON_BORDERLESS);
		
		pieChart = new DCharts();
		pieChart.setSizeUndefined();		
		mainLayout.addComponent(pieChart);		
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		lblMessage.setValue("");
					
		if(tblDesgloce != null){
			mainLayout.removeComponent(tblDesgloce);		
		}	
		
		if(pieChart != null){
			mainLayout.removeComponent(pieChart);
		}
		
		date.setValue(new Date());	
	}
	
	private void getDesgloceDiario(){
		if(tblDesgloce != null){
			mainLayout.removeComponent(tblDesgloce);			
		}
		
		if(pieChart != null){
			mainLayout.removeComponent(pieChart);
		}
		
		if(btnExcelExport != null){
			mainLayout.removeComponent(btnExcelExport);
		}
		
		if(date.isValid()){			
			ArrayList<VODesgloce> array =  logica.DesgloceVentasDiario(date.getValue());
			if(array != null && !array.isEmpty()){
				lblMessage.setValue("");
				buildChart(array);
				ds = new BeanItemContainer<VODesgloce>(VODesgloce.class, array);
				tblDesgloce.removeAllItems();
				tblDesgloce.setContainerDataSource(ds);
				tblDesgloce.setVisibleColumns("rubro","cantidad","total");
				tblDesgloce.setPageLength(ds.size());
				
				mainLayout.addComponent(btnExcelExport);
				mainLayout.addComponent(tblDesgloce);	
				
			}else{
				lblMessage.setValue("No hay registros para mostrar");
			}
		}		
	}
	
	private void buildChart(ArrayList<VODesgloce> array){
		DataSeries dataSeries = new DataSeries();		
		for (VODesgloce aux : array) {
			dataSeries.newSeries();
			dataSeries.add(aux.getRubro(),aux.getCantidad());	
		}

		SeriesDefaults seriesDefaults = new SeriesDefaults();
		seriesDefaults.setRenderer(SeriesRenderers.PIE);
		seriesDefaults.setRendererOptions(new PieRenderer().setShowDataLabels(true));
		
		Legend legend = new Legend();
		legend.setPlacement(LegendPlacements.OUTSIDE_GRID);
		legend.setLocation(LegendLocations.EAST);
		legend.setShow(true);		
		
		Highlighter highlighter = new Highlighter();
		highlighter.setShow(true);
		highlighter.setShowTooltip(true);
		highlighter.setTooltipAlwaysVisible(true);
		highlighter.setKeepTooltipInsideChart(true);

		Options options = new Options();
		options.setSeriesDefaults(seriesDefaults);
		options.setLegend(legend);
		options.setHighlighter(highlighter);
				
		pieChart.setDataSeries(dataSeries);
		pieChart.setOptions(options);					
		pieChart.show();		
		mainLayout.addComponent(pieChart);
	}

}
