package com.epam.brest.courses.frontend.panels.list;

import com.epam.brest.courses.frontend.services.Services;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class OrderDtoDataModel extends ListDataModel {

    public OrderDtoDataModel() {
        super(new String[]{"Id","Brand","RegisterNumber", "Date"}, 0);
    }

    @Override
    public int getObjectType() {
        return Services.TYPE_ORDER_DTO;
    }

    @Override
    public List<String[]> convertRecordsListToTableModel(List<Object> list) {
        List<String []> sampleData = new ArrayList<>();

        for (Object o : list) {
            LinkedHashMap linkedHashMap = (LinkedHashMap) o;

            String[] rowData = new String []{
                    String.valueOf(linkedHashMap.get("id")),
                    String.valueOf(linkedHashMap.get("brand")),
                    String.valueOf(linkedHashMap.get("registerNumber")),
                    String.valueOf(linkedHashMap.get("date"))
            };
            sampleData.add(rowData);
        }
        return sampleData;
    }
}
