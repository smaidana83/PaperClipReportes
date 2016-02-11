package com.example.paperclipreportes;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

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
		
		Button reporte1 = new Button("Reporte 1");
		layout.addComponent(reporte1,0,0);
		//reporte1.addStyleName("myresponsive");	
		reporte1.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				layout.addComponent(new Label("Seleccionaste reporte1"));
			}
		});

		Button reporte2 = new Button("Reporte 2");
		layout.addComponent(reporte2,0,1);
		reporte2.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				layout.addComponent(new Label("Seleccionaste reporte2"));
			}
		});
		
		Button reporte3 = new Button("Reporte 3");
		layout.addComponent(reporte3,1,0);
		reporte3.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				layout.addComponent(new Label("Seleccionaste reporte3"));
			}
		});
		Button reporte4 = new Button("Reporte 4");
		layout.addComponent(reporte4,1,1);		
		reporte4.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				layout.addComponent(new Label("Seleccionaste reporte4"));
			}
		});
		
	}

}