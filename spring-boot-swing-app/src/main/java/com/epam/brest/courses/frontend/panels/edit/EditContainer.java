package com.epam.brest.courses.frontend.panels.edit;

import com.epam.brest.courses.frontend.panels.BusinessPresenter;
import com.epam.brest.courses.frontend.panels.HasBusinessPresenter;
import com.epam.brest.courses.frontend.panels.MakeOrderPanel;
import com.epam.brest.courses.frontend.panels.PanelSwitcher;
import com.epam.brest.courses.frontend.services.Services;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.Objects;

public class EditContainer extends JPanel implements ActionListener, HasBusinessPresenter {

	private static final long serialVersionUID = -7388350255798160262L;
	private JToolBar toolbar;
	private String SAVE_ACTION = "save";
	private String DELETE_ACTION = "delete";
	private String CLOSE_ACTION = "close";
	private String ORDER_ACTION = "order";
	private int objectType;
	private EditContentPanel editPanel;
	private PanelSwitcher cardSwitcher;

	public EditContainer(EditContentPanel editPanel,PanelSwitcher cardSwitcher) 
	{
		setLayout(new BorderLayout());
		this.cardSwitcher = cardSwitcher;
		toolbar = new JToolBar();
		add(toolbar, BorderLayout.PAGE_START);
		addToolBarButton(SAVE_ACTION, "Save");
		addToolBarButton(DELETE_ACTION, "Delete");
		addToolBarButton(CLOSE_ACTION, "Close");
		addToolBarButton(ORDER_ACTION, "Order this car");
		this.editPanel = editPanel;
		this.objectType = editPanel.getObjectType();
		add(editPanel, BorderLayout.CENTER);
	}

	void addToolBarButton(String name, String text) {
		String imgLocation = name + ".png";
		URL imageURL = EditContainer.class.getResource(imgLocation);

		JButton button = new JButton();
		button.setActionCommand(name);
		button.setToolTipText(text);
		button.addActionListener(this);

		if (imageURL != null) {
			ImageIcon defaultIcon = new ImageIcon(imageURL, text);
			button.setIcon(defaultIcon);
		} else {
			button.setText(text);
		}
		toolbar.add(button);
	}

	public void actionPerformed(ActionEvent e) 
	{
		String actionCommand = e.getActionCommand();
		if(actionCommand.equals(SAVE_ACTION))
		{
			Object currentObject = editPanel.guiToObject();
			try
			{
				if(currentObject != null) {
					currentObject = Services.save(currentObject, MakeOrderPanel.localDate, objectType);
					boolean retValue = editPanel.bindToGUI(currentObject);
					if (!retValue) {
						if (Objects.equals(objectType, 3)) {
							JOptionPane.showMessageDialog(this, "Invalid Order Number, Inventory Level or Customer Credit Limit");
						} else {
							JOptionPane.showMessageDialog(this, "Invalid Data Provided.");
						}
					} else {
						JOptionPane.showMessageDialog(this, "Record Saved");
					}
				}
			}
			catch(Exception ee)
			{
				ee.printStackTrace();
				JOptionPane.showMessageDialog(this, "Record Not Saved");
			}
		}
		else if (actionCommand.equals(ORDER_ACTION)){
			Object currentObject = editPanel.guiToObject();
			try
			{
				if(currentObject != null) {
					// send a request to save an order ( 2 is an objectType - order)
					Services.save(currentObject, MakeOrderPanel.localDate, 2);
					JOptionPane.showMessageDialog(this, "Record Saved");
					goToHome();
				}
			}
			catch(Exception ee)
			{
				ee.printStackTrace();
				JOptionPane.showMessageDialog(this, "Record Not Saved");
			}
		}
		else if(actionCommand.equals(DELETE_ACTION))
		{
			boolean retValue = Services.deleteRecordById(editPanel.getCurrentId(), objectType);
			if(retValue)
			{
				goToHome();
			}
			else
			{
				JOptionPane.showMessageDialog(this, "Couldn't Delete Record");
			}
		}
		else if(actionCommand.equals(CLOSE_ACTION))
		{
			goToHome();
		}
	}

	private void goToHome() 
	{
		cardSwitcher.switchTo(JTabbedPane.class.getName());
	}

	public BusinessPresenter getBusinessPresenter()
	{
		return editPanel;
	}
}
