package com.example.paperclipreportes;

import java.io.File;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.Navigator.ComponentContainerViewDisplay;
import com.vaadin.server.FileResource;
import com.vaadin.server.Responsive;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import grafica.FlujoDeCaja;
import grafica.TotalEnCaja;
import utils.MyConverterFactory;

@SuppressWarnings("serial")
@Theme("paperclipreportes")
public class PaperclipreportesUI extends UI {
	private GridLayout mainLayout;
	private VerticalLayout contentLayout;  
	private Navigator contentNavigator;	
	//protected static final String TOTALENCAJA = "totalEnCaja";
	protected static final String TOTALENCAJA = "totalEnCaja";
	//protected static final String FLUJODECAJA = "flujoDeCaja";
	protected static final String FLUJODECAJA = "";
	protected static final String DEUDORES = "deudores";
	protected static final String DESGLOCEDEVENTAS = "desgloceDeVentas";
	

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = PaperclipreportesUI.class)
	public static class Servlet extends VaadinServlet {		
        protected void servletInitialized() throws ServletException {
            super.servletInitialized();
            getService().addSessionInitListener(
                    new ValoThemeSessionInitListener());
        }
	}

	@Override
	protected void init(VaadinRequest request) {
		mainLayout = new GridLayout(3,3);
		contentLayout = new VerticalLayout();
					
		VaadinSession.getCurrent().setConverterFactory(new MyConverterFactory());
		setLocale(new Locale("es", "UY"));
		mainLayout.setMargin(true);
		setContent(mainLayout);		
		mainLayout.setSizeFull();		
		Responsive.makeResponsive(mainLayout);		
				
		
		//Ruta base de la aplicacion
		String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
				
		//Logo
		FileResource paperClipLogoResource = new FileResource(new File(basepath +"/WEB-INF/images/logo.png"));		
		Image paperClipLogo = new Image(null, paperClipLogoResource);
		paperClipLogo.setHeight("80%");
		//paperClipLogo.setWidth("80%");
		
		mainLayout.addComponent(paperClipLogo, 0,0);
		
		//MenuBar
		MenuBar menuPrincipal = new MenuBar();
		mainLayout.addComponent(menuPrincipal,0,1);
		
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
				System.out.println("Deudores");
				break;
			case "Desgloce de ventas":
				System.out.println("Desgloce de ventas");
				break;
			default:
				System.out.println("Selecciono uno no valido");
				break;
				
			}
			}
			};
		
				
		MenuItem reportes = menuPrincipal.addItem("", new ThemeResource("icons/menu.png"), null);
		reportes.setStyleName("menuPrincipal");
		
		MenuItem totalEnCaja = reportes.addItem("Total en caja", menuCommand);
		MenuItem flujoDeCaja = reportes.addItem("Flujo de caja", menuCommand);
		MenuItem deudores = reportes.addItem("Deudores", menuCommand);
		MenuItem desgloceDeVentas = reportes.addItem("Desgloce de ventas", menuCommand);
		
		
				
				
		

		//Navigator
		ComponentContainerViewDisplay viewDisplay = new ComponentContainerViewDisplay(contentLayout);
		contentNavigator = new Navigator(this, viewDisplay);
		mainLayout.addComponent(contentLayout,0,2,2,2);
		
		//Carga de las paginas		
		contentNavigator.addView(TOTALENCAJA, new TotalEnCaja());
		contentNavigator.addView(FLUJODECAJA, new FlujoDeCaja());
		
//		contentNavigator.addView(DEUDORES, new Deudores());
//		contentNavigator.addView(DESGLOCEDEVENTAS, new DesgloceDeVentas());

//		Button btnFlujoCaja = new Button("Flujo de Caja");
//		headerLayout.addComponent(btnFlujoCaja);		
//		btnFlujoCaja.addClickListener(new Button.ClickListener() {
//			public void buttonClick(ClickEvent event) {				
//				Datos datos = new Datos();				
//				final BeanItemContainer<VOFlujoCaja> ds = new BeanItemContainer<VOFlujoCaja>(VOFlujoCaja.class, datos.FlujoDeCaja("20140822"));
//				Grid grid = new Grid("Flujo de Caja", ds);
//				grid.setSizeFull();
//				mainLayout.addComponent(grid);				
//			}
//		});
//		
//		Button btnDeudores = new Button("Deudores");
//		headerLayout.addComponent(btnDeudores);
//		btnDeudores.setCaption("Deudores");
//		btnDeudores.addClickListener(new Button.ClickListener() {
//			public void buttonClick(ClickEvent event) {
//				Datos datos = new Datos();				
//				final BeanItemContainer<VODeudores> ds = new BeanItemContainer<VODeudores>(VODeudores.class, datos.Deudores(1));
//				Grid grid = new Grid("Deudores", ds);
//				grid.setSizeFull();
//				mainLayout.addComponent(grid);		
//			}
//		});
//		
//		Button btnDegloceVentas = new Button("Desgloce Ventas");
//		headerLayout.addComponent(btnDegloceVentas);		
//		btnDegloceVentas.addClickListener(new Button.ClickListener() {
//			public void buttonClick(ClickEvent event) {
//				Datos datos = new Datos();				
//				final BeanItemContainer<VODesgloce> ds = new BeanItemContainer<VODesgloce>(VODesgloce.class, datos.DegloceVentas("20140822"));
//				Grid grid = new Grid("Desgloce de Ventas", ds);
//				grid.setSizeFull();
//				mainLayout.addComponent(grid);	
//			}
//		});
		
	}

}


	