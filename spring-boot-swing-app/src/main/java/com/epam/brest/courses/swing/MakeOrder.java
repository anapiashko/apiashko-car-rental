package com.epam.brest.courses.swing;

import com.epam.brest.courses.model.Car;
import com.epam.brest.courses.service_api.CarService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import javax.swing.text.DateFormatter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MakeOrder {

    private CarService carService;

    @Autowired
    public MakeOrder(CarService carService) {
        this.carService = carService;
    }

    public JPanel createPanel(String text) {

        JPanel panel = new JPanel();

        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);

        // Добавляем labels
        JLabel mainLabel = new JLabel(text);
        JLabel carListLabel = new JLabel("Car List");

        // Определение маски и поля ввода даты
        DateFormat date = new SimpleDateFormat("yyyy-MM-dd, EEEE");
        // Форматирующий объект даты
        DateFormatter dateFormatter = new DateFormatter(date);
        dateFormatter.setAllowsInvalid(false);
        dateFormatter.setOverwriteMode(true);

        // Создание форматированного текстового поля даты
        JFormattedTextField ftfDate = new JFormattedTextField(dateFormatter);
        ftfDate.setColumns(32);
        ftfDate.setValue(new Date());

        // panel.add(ftfDate,new GridLayout(1,1));
        JButton okBtn = new JButton("OK");

        // создание списка
        Car[] fonts = carService.findAll().toArray(new Car[0]);
        JList list = new JList(fonts);

        JScrollPane scrollPane = new JScrollPane(list);

        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addComponent(mainLabel)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(ftfDate)
                                .addComponent(carListLabel)
                                .addComponent(scrollPane))
                        .addComponent(okBtn)

        );
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addComponent(mainLabel)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(ftfDate)
                                .addComponent(okBtn))
                        .addComponent(carListLabel)
                        .addComponent(scrollPane)

        );
        return panel;
    }
}
