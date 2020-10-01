package com.epam.brest.courses.frontend.panels.edit;

import com.epam.brest.courses.frontend.services.Services;
import com.epam.brest.courses.model.Car;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;

public class EditProduct extends EditContentPanel
{
	private static final long serialVersionUID = -8971249970444644844L;
	private JTextField txtId = new JTextField();
	private JTextField txtBrand = new JTextField();
	private JTextField txtRegisterNumber = new JTextField();
	private JTextField txtPrice = new JTextField();

	public EditProduct()
	{
		GridBagLayout gridBagLayout = new GridBagLayout();
		setLayout(gridBagLayout);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 0;
		gbc.gridy = 0;
		add(new JLabel("Id"), gbc);

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
		add(new JLabel("Brand"), gbc);

		gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridwidth = 3;
		gbc.anchor = GridBagConstraints.LAST_LINE_START;
		add(txtBrand, gbc);
		txtBrand.setColumns(28);
		gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 0;
		gbc.gridy = 2;
		add(new JLabel("Price"), gbc);

		gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.LAST_LINE_START;
		add(txtPrice, gbc);
		txtPrice.setColumns(10);

		gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 2;
		gbc.gridy = 2;
		add(new JLabel("RegisterNumber"), gbc);

		gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 15);
		gbc.gridx = 3;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.LAST_LINE_START;
		add(txtRegisterNumber, gbc);
		txtRegisterNumber.setColumns(10);
	}

	public boolean bindToGUI(Object o) 
	{
		// TODO by the candidate
		/*
		 * This method use the object returned by Services.readRecordByCode and should map it to screen widgets 
		 */
		Car car = (Car)o;
		txtId.setText(String.valueOf(car.getId()));
		txtBrand.setText(car.getBrand());
		txtPrice.setText(car.getPrice().toString());
		txtRegisterNumber.setText(car.getRegisterNumber());
		return true;
	}

	public Object guiToObject() 
	{
		// TODO by the candidate
		/*
		 * This method collect values from screen widgets and convert them to object of your type
		 * This object will be used as a parameter of method Services.save
		 */
		System.out.println("id : " + txtId.getText());
		Car car = new Car();
		car.setId(Integer.parseInt(txtId.getText()));
		car.setBrand(txtBrand.getText());
		car.setRegisterNumber(txtRegisterNumber.getText());
		car.setPrice(BigDecimal.valueOf(Double.parseDouble(txtPrice.getText())));
		return car;
	}

	public int getObjectType()
	{
		return Services.TYPE_CAR;
	}

	@Override
	public String getCurrentCode()
	{
		return txtId.getText();
	}

	public void clear()
	{
		txtId.setText("");
		txtBrand.setText("");
		txtPrice.setText("");
		txtRegisterNumber.setText("");
	}

	public void onInit()
	{

	}
}
