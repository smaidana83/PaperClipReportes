package com.example.paperclipreportes;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import datos.Datos;
import valueObject.VODesgloce;
import valueObject.VODeudores;
import valueObject.VOFlujoCaja;

@SuppressWarnings("serial")
@Theme("paperclipreportes")
public class PaperclipreportesUI extends UI {

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = PaperclipreportesUI.class)
	public static class Servlet extends VaadinServlet {
	}

	@Override
	protected void init(VaadinRequest request) {
		final GridLayout layout = new GridLayout(3,2);
		layout.setMargin(true);
		setContent(layout);
		Responsive.makeResponsive(layout);
		layout.setStyleName("flexwrap");
		layout.setSizeFull();
		
		Button btnTotalCaja = new Button("Total en Caja");
		layout.addComponent(btnTotalCaja,0,0);				
		btnTotalCaja.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				Datos datos = new Datos();
				layout.addComponent(new Label("Total en Caja: " + datos.TotalEnCaja("20140822")));				
			}
		});

		Button btnFlujoCaja = new Button("Flujo de Caja");
		layout.addComponent(btnFlujoCaja,0,1);		
		btnFlujoCaja.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {				
				Datos datos = new Datos();				
				final BeanItemContainer<VOFlujoCaja> ds = new BeanItemContainer<VOFlujoCaja>(VOFlujoCaja.class, datos.FlujoDeCaja("20140822"));
				Grid grid = new Grid("Flujo de Caja", ds);
				grid.setSizeFull();
				layout.addComponent(grid);				
			}
		});
		
		Button btnDeudores = new Button("Deudores");
		layout.addComponent(btnDeudores,1,0);
		btnDeudores.setCaption("Deudores");
		btnDeudores.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				Datos datos = new Datos();				
				final BeanItemContainer<VODeudores> ds = new BeanItemContainer<VODeudores>(VODeudores.class, datos.Deudores(1));
				Grid grid = new Grid("Deudores", ds);
				grid.setSizeFull();
				layout.addComponent(grid);		
			}
		});
		
		Button btnDegloceVentas = new Button("Desgloce Ventas");
		layout.addComponent(btnDegloceVentas,1,1);		
		btnDegloceVentas.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				Datos datos = new Datos();				
				final BeanItemContainer<VODesgloce> ds = new BeanItemContainer<VODesgloce>(VODesgloce.class, datos.DegloceVentas("20140822"));
				Grid grid = new Grid("Desgloce de Ventas", ds);
				grid.setSizeFull();
				layout.addComponent(grid);	
			}
		});
		
	}

}