package com.epam.brest.courses.frontend.panels.edit;

import com.epam.brest.courses.frontend.services.Services;
import com.epam.brest.courses.model.Order;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EditCustomer extends EditContentPanel 
{
	private static final long serialVersionUID = -8971249970444644844L;
	private JTextField txtId = new JTextField();
	private JTextField txtDate = new JTextField();
	private JTextField txtCarId = new JTextField();

	public EditCustomer() 
	{
		GridBagLayout gridBagLayout = new GridBagLayout();
		setLayout(gridBagLayout);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 0;
		gbc.gridy = 0;
		add(new JLabel("Code"), gbc);

		gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		add(txtId, gbc);
		gbc.anchor = GridBagConstraints.LAST_LINE_START;
		txtId.setColumns(10);
		gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 0;
		gbc.gridy = 1;
		add(new JLabel("Name"), gbc);

		gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridwidth = 3;
		gbc.anchor = GridBagConstraints.LAST_LINE_START;
		add(txtDate, gbc);
		txtDate.setColumns(28);
		
		
		gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 0;
		gbc.gridy = 2;
		add(new JLabel("Address"), gbc);

		gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.gridwidth = 3;
		gbc.anchor = GridBagConstraints.LAST_LINE_START;
		add(txtCarId, gbc);
		txtCarId.setColumns(28);
	}

	public boolean bindToGUI(Object o) 
	{
		// TODO by the candidate
		/*
		 * This method use the object returned by Services.readRecordByCode and should map it to screen widgets 
		 */
		Order customer = (Order)o;
		txtId.setText(String.valueOf(customer.getId()));
		txtDate.setText(String.valueOf(customer.getDate()));
		txtCarId.setText(String.valueOf(customer.getCarId()));
		return true;
	}

	public Object guiToObject() 
	{
		// TODO by the candidate
		/*
		 * This method collect values from screen widgets and convert them to object of your type
		 * This object will be used as a parameter of method Services.save
		 */

		Order order = new Order();
		order.setId(Integer.parseInt((txtId.getText())));
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd");
		order.setDate(LocalDate.parse(txtDate.getText(), formatter));
		order.setCarId(Integer.parseInt(txtCarId.getText()));
		return order;
	}

	@Override
	public int getObjectType() 
	{
		return Services.TYPE_ORDER;
	}

	@Override
	public String getCurrentId()
	{
		return txtId.getText();
	}

	public void clear() 
	{
		txtId.setText("");
		txtDate.setText("");
		txtCarId.setText("");
	}

	public void onInit() 
	{
		
	}
}
