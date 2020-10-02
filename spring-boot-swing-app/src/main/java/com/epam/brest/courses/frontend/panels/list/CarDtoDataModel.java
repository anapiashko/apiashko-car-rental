package com.epam.brest.courses.frontend.panels.list;

import com.epam.brest.courses.frontend.services.Services;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class CarDtoDataModel extends ListDataModel {

    public CarDtoDataModel() {
        super(new String[]{"Id","Brand", "RegisterNumber", "NumberOrders"}, 0);
    }

    @Override
    public int getObjectType() {
        return Services.TYPE_CAR_DTO;
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
                    String.valueOf(linkedHashMap.get("numberOrders"))
            };
            sampleData.add(rowData);
        }
        return sampleData;
    }
}
