package com.epam.brest.courses.swing;

import com.epam.brest.courses.model.Car;
import com.epam.brest.courses.service_api.CarService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import javax.swing.text.DateFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class MakeOrder {

    private final CarService carService;
    private Car[] fonts = new Car[1];
    private LocalDate localDate;

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
        DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        // Форматирующий объект даты
        DateFormatter dateFormatter = new DateFormatter(date);
        dateFormatter.setAllowsInvalid(false);
        dateFormatter.setOverwriteMode(true);

        // Создание форматированного текстового поля даты
        JFormattedTextField ftfDate = new JFormattedTextField(dateFormatter);
        ftfDate.setColumns(32);
        ftfDate.setValue(new Date());

        // перевод строки из поля в LocalDate
        Date dateStr = (Date)ftfDate.getValue();
        localDate = dateStr.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        JButton okBtn = new JButton("OK");
        okBtn.addActionListener(new ListenerAction());

        // создание списка
        //fonts = carService.findAllByDate(localDate).toArray(new Car[0]);
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

    class ListenerAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            fonts = carService.findAllByDate(localDate).toArray(new Car[0]);
        }

    }
}
