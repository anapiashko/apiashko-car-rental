package com.epam.brest.courses.frontend.panels.list;

import com.epam.brest.courses.frontend.panels.BusinessPresenter;
import com.epam.brest.courses.frontend.panels.MakeOrderPanel;
import com.epam.brest.courses.frontend.panels.StatisticsPanel;
import com.epam.brest.courses.frontend.services.Services;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public abstract class ListDataModel extends DefaultTableModel implements
		BusinessPresenter {

	public ListDataModel(String[] columnNames, int rowsCount) {
		super(columnNames, rowsCount);
	}

	public boolean bindToGUI(Object o) 
	{
		clear();
		List<Object> list;

		if( getObjectType() == Services.TYPE_CAR_DTO){
			 list = Services.listCurrentRecords(StatisticsPanel.localDateFrom, StatisticsPanel.localDateTo, getObjectType());
		}else{
			list = Services.listCurrentRecords(MakeOrderPanel.localDate, getObjectType());
		}
		List<String[]> modelData = convertRecordsListToTableModel(list);
		for(String [] row:modelData)
		{
			addRow(row);
		}
		return true;
	}
	public void clear() {
		setRowCount(0);
	}

	public void onInit() 
	{
		bindToGUI(null);
	}

	public Object guiToObject() {
		return getDataVector();
	}
	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
	public abstract int getObjectType();
	public abstract List<String[]> convertRecordsListToTableModel(List<Object> list);
}
