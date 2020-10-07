package com.epam.brest.courses.frontend.panels;

import com.epam.brest.courses.frontend.panels.edit.EditOrder;
import com.epam.brest.courses.frontend.panels.edit.EditCar;
import com.epam.brest.courses.frontend.panels.list.OrderDataModel;
import com.epam.brest.courses.frontend.panels.list.CarDataModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPanel extends JPanel {

	private PanelSwitcher cardSwitcher;

	public MenuPanel(PanelSwitcher cardSwitcher1) {
		this.cardSwitcher = cardSwitcher1;
		JButton btnAddCustomer = new JButton("Add Order");
		btnAddCustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardSwitcher.switchTo(EditOrder.class.getName());
			}
		});
		add(btnAddCustomer);
		btnAddCustomer.setBounds(new Rectangle(50, 50, 30, 20));

		JButton btnAddProduct = new JButton("Add Car");
		btnAddProduct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				cardSwitcher.switchTo(EditCar.class.getName());
			}
		});

//		JButton btnAddSalesOrder = new JButton("Add Sales order");
//		btnAddSalesOrder.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e)
//			{
//				cardSwitcher.switchTo(EditSalesOrder.class.getName());
//			}
//		});

		add(btnAddProduct);
//		add(btnAddSalesOrder);

		JButton btnListCustomers = new JButton("List Orders");
		add(btnListCustomers);
		btnListCustomers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardSwitcher.switchTo(OrderDataModel.class.getName());
			}
		});

		JButton btnListProducts = new JButton("List Cars");

		add(btnListProducts);

//		JButton btnListOrders = new JButton("List Sales Order");
//		add(btnListOrders);

		btnListProducts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardSwitcher.switchTo(CarDataModel.class.getName());
			}
		});
//		btnListOrders.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				cardSwitcher.switchTo(SalesOrderDataModel.class.getName());
//			}
//		});

	}
}
