package com.epam.brest.courses.frontend.panels;


import com.epam.brest.courses.Application;
import com.epam.brest.courses.frontend.panels.edit.EditContainer;
import com.epam.brest.courses.frontend.panels.edit.EditOrder;
import com.epam.brest.courses.frontend.panels.edit.EditCar;
import com.epam.brest.courses.frontend.panels.list.*;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

@org.springframework.stereotype.Component
public class Main implements PanelSwitcher {
	private JFrame frame;
	private JPanel panel;
	private HashMap<String, HasBusinessPresenter> containersMap = new HashMap<String, HasBusinessPresenter>();

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {

			public void run() {
				try {
					ConfigurableApplicationContext context = new SpringApplicationBuilder(Application.class)
							.run(args);

					Main window = context.getBean(Main.class);

					JTabbedPane tabbedPane = new JTabbedPane();

					MakeOrderPanel makeOrderPanel = new MakeOrderPanel(window);
					StatisticsPanel statisticsPanel = new StatisticsPanel(window);

					tabbedPane.addTab("Make order", makeOrderPanel);
					tabbedPane.add(new ListContentPanel(window, new OrderDtoDataModel()), "Show Orders");
					tabbedPane.addTab("Statistics", statisticsPanel);
					window.panel.add(tabbedPane, JTabbedPane.class.getName());

//					MenuPanel menuPanel = new MenuPanel(window);
//					window.panel.add(menuPanel, MenuPanel.class.getName());
//					MainPanel mainPanel = new MainPanel(window);
//					window.panel.add(mainPanel, MainPanel.class.getName());

				//	window.panel.add(makeOrderPanel, MakeOrderPanel.class.getName());

					EditContainer carContainer = new EditContainer(new EditCar(),
							window);
					EditContainer orderContainer = new EditContainer(new EditOrder(),
							window);
					
					window.addPanel(carContainer, EditCar.class.getName());
					window.addPanel(orderContainer, EditOrder.class.getName());

					window.addPanel(new ListContentPanel(window,new OrderDataModel()), OrderDataModel.class.getName());
					window.addPanel(new ListContentPanel(window,new CarDataModel()), CarDataModel.class.getName());
//					window.addPanel(new ListContentPanel(window, new OrderDtoDataModel()), OrderDtoDataModel.class.getName());
					window.addPanel(new ListContentPanel(window, new CarDtoDataModel()), CarDtoDataModel.class.getName());

					window.frame.setTitle("Car-Rental-App");
					window.frame.setSize(860, 750);
					window.frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	void addPanel(HasBusinessPresenter container,String name)
	{
		containersMap.put(name, container);
		panel.add((Component) container,name);
	}
	public Main() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel = new JPanel(new CardLayout());
		frame.add(panel);
	}

	public void switchTo(String name) {
		CardLayout layout = (CardLayout) panel.getLayout();
		HasBusinessPresenter container = getPanelOfClass(name);
		if(container!=null) {
			container.getBusinessPresenter().clear();
			container.getBusinessPresenter().onInit();
		}
		layout.show(panel, name);
	}

	public HasBusinessPresenter getPanelOfClass(String name) {
		return containersMap.get(name);
	}
}
