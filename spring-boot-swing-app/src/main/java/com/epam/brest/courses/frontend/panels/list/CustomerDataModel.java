package com.epam.brest.courses.frontend.panels.list;

import com.epam.brest.courses.frontend.services.Services;
import com.epam.brest.courses.model.Order;

import java.util.ArrayList;
import java.util.List;

public class CustomerDataModel extends ListDataModel {

	public CustomerDataModel()
	{
		super(new String[] { "Id", "Date", "CarId" }, 0);
	}

	@Override
	public int getObjectType()
	{
		return Services.TYPE_ORDER;
	}

	@Override
	public List<String[]> convertRecordsListToTableModel(List<Object> list)
	{
		//TODO by the candidate
		/*
		 * This method use list returned by Services.listCurrentRecords and should convert it to array of rows
		 * each row is another array of columns of the row
		 */
//		String[][] sampleData = new String [][]{{"01","Customer 1","+201011121314","23.4"},{"02","Customer 2","+201112131415","1.4"}};
		List<String []> sampleData = new ArrayList<>();

		for (Object o : list) {
			Order order = (Order)o;
			String[] rowData = new String []{
					String.valueOf(order.getId()),
					String.valueOf(order.getDate()),
					String.valueOf(order.getCarId())
			};
			sampleData.add(rowData);
		}

		return sampleData;
	}
}
