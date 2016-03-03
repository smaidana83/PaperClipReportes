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
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import grafica.TotalEnCaja;
import utils.MyConverterFactory;

@SuppressWarnings("serial")
@Theme("paperclipreportes")
public class PaperclipreportesUI extends UI {
	private GridLayout mainLayout;
	private VerticalLayout contentLayout;  
	private Navigator contentNavigator;	
	protected static final String TOTALENCAJA = "totalEnCaja";
	

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
		mainLayout.addComponent(paperClipLogo, 0,0);

		//Navigator
		ComponentContainerViewDisplay viewDisplay = new ComponentContainerViewDisplay(contentLayout);
		contentNavigator = new Navigator(this, viewDisplay);
		mainLayout.addComponent(contentLayout,0,1,2,1);
		
		contentNavigator.addView("", new TotalEnCaja());	
		
		
//		Button btnTotalCaja = new Button("Total en Caja");
//		headerLayout.addComponent(btnTotalCaja);				
//		btnTotalCaja.addClickListener(new Button.ClickListener() {
//			public void buttonClick(ClickEvent event) {
//				Datos datos = new Datos();
//				mainLayout.addComponent(new Label("Total en Caja: " + datos.TotalEnCaja("20140822")));				
//			}
//		});
//
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


	