package com.epam.brest.courses.frontend.panels.list;

import com.epam.brest.courses.frontend.services.Services;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;


public class ProductDataModel extends ListDataModel
{
	private static final long serialVersionUID = 7526529951747614655L;

	public ProductDataModel() 
	{
		super(new String[]{"Id","Brand","Price","RegisterNumber"}, 0);
	}

	@Override
	public int getObjectType() {
		return Services.TYPE_CAR;
	}

	@Override
	public List<String[]> convertRecordsListToTableModel(List<Object> list)
	{
		//TODO by the candidate
		/*
		 * This method use list returned by Services.listCurrentRecords and should convert it to array of rows
		 * each row is another array of columns of the row
		 */
//		String[][] sampleData = new String [][]{
//				{"01","Product 1","12.5","25"},
//				{"02","Product 2","10","8"}
//		};
		List<String []> sampleData = new ArrayList<>();

		for (Object o : list) {
			LinkedHashMap linkedHashMap = (LinkedHashMap) o;

			String[] rowData = new String []{
					String.valueOf(linkedHashMap.get("id")),
					String.valueOf(linkedHashMap.get("brand")),
					String.valueOf(linkedHashMap.get("registerNumber")),
					String.valueOf(linkedHashMap.get("price"))
			};
			sampleData.add(rowData);
		}


		return sampleData;
	}
}
