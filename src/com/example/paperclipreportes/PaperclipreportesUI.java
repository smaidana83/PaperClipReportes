package com.example.paperclipreportes;

import java.io.File;
import java.util.Locale;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.FileResource;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import grafica.TotalEnCaja;
import utils.MyConverterFactory;

@SuppressWarnings("serial")
@Theme("paperclipreportes")
public class PaperclipreportesUI extends UI {
	private VerticalLayout mainLayout;
	private HorizontalLayout headerLayout;
	private VerticalLayout contentLayout;  
	private Navigator contentNavigator;
	protected static final String TOTALENCAJA = "totalEnCaja";
	

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = PaperclipreportesUI.class)
	public static class Servlet extends VaadinServlet {
	}

	@Override
	protected void init(VaadinRequest request) {
		mainLayout = new VerticalLayout();
		headerLayout = new HorizontalLayout();
		contentLayout = new VerticalLayout();
					
		VaadinSession.getCurrent().setConverterFactory(new MyConverterFactory());
		setLocale(new Locale("es", "UY"));
		mainLayout.setMargin(true);
		setContent(mainLayout);
		Responsive.makeResponsive(mainLayout);		
		mainLayout.setSizeFull();
		
		headerLayout.setSpacing(true);
		headerLayout.setSizeUndefined();
		mainLayout.addComponent(headerLayout);
		
		contentLayout.setSpacing(true);
		mainLayout.addComponent(contentLayout);
		contentNavigator = new Navigator(this, contentLayout);		
		contentNavigator.addView("", new TotalEnCaja());
		
		//Ruta base de la aplicacion
		String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
		
		//Logo
		FileResource paperClipLogoResource = new FileResource(new File(basepath +"/WEB-INF/images/logo.png"));		
		Image paperClipLogo = new Image(null, paperClipLogoResource);
		headerLayout.addComponentAsFirst(paperClipLogo);
		
		
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