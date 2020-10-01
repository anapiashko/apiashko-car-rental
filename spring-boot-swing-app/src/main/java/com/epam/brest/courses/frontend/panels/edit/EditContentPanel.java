package com.epam.brest.courses.frontend.panels.edit;

import com.epam.brest.courses.frontend.panels.BusinessPresenter;

import javax.swing.*;

public abstract class EditContentPanel extends JPanel implements BusinessPresenter {
	private static final long serialVersionUID = 4495835331993612718L;
	public abstract int getObjectType();
	public abstract String getCurrentCode();
}
