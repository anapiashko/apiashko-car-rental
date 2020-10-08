package com.epam.brest.courses.frontend.panels;

import com.epam.brest.courses.frontend.panels.edit.EditOrder;
import com.epam.brest.courses.frontend.panels.edit.EditCar;
import com.epam.brest.courses.frontend.panels.list.OrderDataModel;
import com.epam.brest.courses.frontend.panels.list.CarDataModel;

import javax.swing.*;
import javax.swing.text.DateFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class MakeOrderPanel extends JPanel {

    private PanelSwitcher cardSwitcher;
    public static LocalDate localDate;

    public MakeOrderPanel(PanelSwitcher cardSwitcher1) {
        this.cardSwitcher = cardSwitcher1;

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

        // Добавляем поле ввода даты на панель
        add(ftfDate);

        // перевод строки из поля в LocalDate
        Date dateStr = (Date)ftfDate.getValue();
        localDate = dateStr.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        JButton okBtn = new JButton("OK");
        add(okBtn);
        okBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Date dateStr = (Date)ftfDate.getValue();
                localDate = dateStr.toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();
                cardSwitcher.switchTo(CarDataModel.class.getName());
            }
        });

        JButton btnListProducts = new JButton("List Cars");
        add(btnListProducts);
        btnListProducts.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardSwitcher.switchTo(CarDataModel.class.getName());
            }
        });

    }
}
