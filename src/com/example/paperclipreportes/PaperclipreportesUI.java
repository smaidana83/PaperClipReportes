package com.example.paperclipreportes;

import java.io.File;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.Navigator.ComponentContainerViewDisplay;
import com.vaadin.server.FileResource;
import com.vaadin.server.Responsive;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import grafica.Acreedores;
import grafica.DesgloceDeVentasDiario;
import grafica.DesgloceDeVentasMensual;
import grafica.Deudores;
import grafica.FlujoDeCaja;
import grafica.Presupuesto;
import grafica.TotalEnCaja;
import grafica.TotalEnCajaPorFecha;
import utils.MyConverterFactory;

@SuppressWarnings("serial")
@Theme("paperclipreportes")
public class PaperclipreportesUI extends UI {
	private VerticalLayout mainLayout;
	private VerticalLayout contentLayout;  
	private HorizontalLayout headerLayout;
	private Navigator contentNavigator;	
	protected static final String TOTALENCAJA = "";
//	protected static final String TOTALENCAJA = "totalEnCaja";
	protected static final String FLUJODECAJA = "flujoDeCaja";	
	protected static final String DEUDORES = "deudores";
	protected static final String DESGLOCEDEVENTASDIARIO = "desgloceDeVentasDiario";
	protected static final String DESGLOCEDEVENTASMENSUAL = "desgloceDeVentasMensual";
	protected static final String PRESUPUESTO = "presupuesto";
	protected static final String ACREEDORES = "acreedores";
	protected static final String TOTALENCAJAPORFECHA = "totalEnCajaPorFecha";
	

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = true, ui = PaperclipreportesUI.class, widgetset = "com.example.paperclipreportes.widgetset.PaperclipreportesWidgetset")
	public static class Servlet extends VaadinServlet {		
        protected void servletInitialized() throws ServletException {
            super.servletInitialized();
            getService().addSessionInitListener(
                    new ValoThemeSessionInitListener());
        }
	}

	@Override
	protected void init(VaadinRequest request) {
		//mainLayout = new GridLayout(3,3);
		mainLayout = new VerticalLayout();
		headerLayout = new HorizontalLayout();
		contentLayout = new VerticalLayout();
		
		VaadinSession.getCurrent().setConverterFactory(new MyConverterFactory());
		setLocale(new Locale("es", "UY"));
		mainLayout.setMargin(false);
		mainLayout.setSpacing(true);
		setContent(mainLayout);				
		mainLayout.setWidth("100%");		
		Responsive.makeResponsive(this);
		
		//Header
		//headerLayout.setSizeUndefined();
		headerLayout.setWidth("100%");
		headerLayout.setHeight("90px");
		headerLayout.setMargin(true);
		headerLayout.setSpacing(true);
		headerLayout.setStyleName("headerStyle");		
		//headerLayout.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
		mainLayout.addComponent(headerLayout);
		
		
		//Ruta base de la aplicacion
		String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
					
		//MenuBar	
		MenuBar menuPrincipal = new MenuBar();
		menuPrincipal.setStyleName(ValoTheme.MENUBAR_BORDERLESS);
		//mainLayout.addComponent(menuPrincipal,0,1);		
		headerLayout.addComponent(menuPrincipal);			
		
		MenuBar.Command menuCommand = new MenuBar.Command() {
			public void menuSelected(MenuItem selectedItem) {
				switch(selectedItem.getText()){
				case "Total en caja":
					contentNavigator.navigateTo(TOTALENCAJA);
					break;
				case "Flujo de caja":
					contentNavigator.navigateTo(FLUJODECAJA);
					break;
				case "Deudores":
					contentNavigator.navigateTo(DEUDORES);
					break;
				case "Diario":
					contentNavigator.navigateTo(DESGLOCEDEVENTASDIARIO);
					break;
				case "Mensual":
					contentNavigator.navigateTo(DESGLOCEDEVENTASMENSUAL);
					break;
				case "Presupuesto":
					contentNavigator.navigateTo(PRESUPUESTO);
					break;
				case "Acreedores":
					contentNavigator.navigateTo(ACREEDORES);
					break;
				case "Total en caja por fecha":
					contentNavigator.navigateTo(TOTALENCAJAPORFECHA);
					break;
				default:
					System.out.println("Selecciono uno no valido");
					break;
					
				}
			}
		};
		
		MenuBar.Command totalEnCajaCommand = new MenuBar.Command() {
			public void menuSelected(MenuItem selectedItem) {
				switch(selectedItem.getText()){				
				case "Diario":
					contentNavigator.navigateTo(TOTALENCAJA);
					break;
				case "Mensual":
					contentNavigator.navigateTo(TOTALENCAJAPORFECHA);
					break;				
				default:
					System.out.println("Selecciono uno no valido");
					break;
					
				}
			}
		};
		
		MenuBar.Command desgloceCommand = new MenuBar.Command() {
			public void menuSelected(MenuItem selectedItem) {
				switch(selectedItem.getText()){				
				case "Diario":
					contentNavigator.navigateTo(DESGLOCEDEVENTASDIARIO);
					break;
				case "Mensual":
					contentNavigator.navigateTo(DESGLOCEDEVENTASMENSUAL);
					break;				
				default:
					System.out.println("Selecciono uno no valido");
					break;
					
				}
			}
		};
		
				
		MenuItem reportes = menuPrincipal.addItem("", new ThemeResource("icons/menu.png"), null);
		reportes.setStyleName("menuPrincipal");
		
		MenuItem totalEnCaja = reportes.addItem("Total en caja",null);		
		totalEnCaja.addItem("Diario", totalEnCajaCommand);
		totalEnCaja.addItem("Mensual", totalEnCajaCommand);
		reportes.addItem("Flujo de caja", menuCommand);
		reportes.addItem("Deudores", menuCommand);		
		MenuItem desgloce = reportes.addItem("Desgloce de ventas", null);
		desgloce.addItem("Diario", desgloceCommand);
		desgloce.addItem("Mensual", desgloceCommand);
		reportes.addItem("Presupuesto", menuCommand);
		reportes.addItem("Acreedores", menuCommand);
	
		
		
		//Logo
		FileResource paperClipLogoResource = new FileResource(new File(basepath +"/WEB-INF/images/logo.png"));		
		Image paperClipLogo = new Image(null, paperClipLogoResource);
		//paperClipLogo.setHeight("63px");
		//paperClipLogo.setWidth("184.2px");		
		headerLayout.addComponent(paperClipLogo);
		headerLayout.setExpandRatio(paperClipLogo, 1);
	
		
		paperClipLogo.addClickListener(new ClickListener() {
			
			@Override
			public void click(ClickEvent event) {
				// TODO Auto-generated method stub
				contentNavigator.navigateTo("");
			}
		});
						
		

		//Navigator
		
		contentLayout.setMargin(true);
		//contentLayout.setSizeFull();
		ComponentContainerViewDisplay viewDisplay = new ComponentContainerViewDisplay(contentLayout);
		contentNavigator = new Navigator(this, viewDisplay);
		//mainLayout.addComponent(contentLayout,0,2,2,2);		
		mainLayout.addComponent(contentLayout);
		
		
		//Carga de las paginas		
		contentNavigator.addView(TOTALENCAJA, new TotalEnCaja());
		contentNavigator.addView(FLUJODECAJA, new FlujoDeCaja());		
		contentNavigator.addView(DEUDORES, new Deudores());
		contentNavigator.addView(DESGLOCEDEVENTASDIARIO, new DesgloceDeVentasDiario());	
		contentNavigator.addView(DESGLOCEDEVENTASMENSUAL, new DesgloceDeVentasMensual());
		contentNavigator.addView(PRESUPUESTO, new Presupuesto());
		contentNavigator.addView(ACREEDORES, new Acreedores());
		contentNavigator.addView(TOTALENCAJAPORFECHA, new TotalEnCajaPorFecha());
	}

}


	