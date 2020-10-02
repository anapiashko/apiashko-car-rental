package com.epam.brest.courses.frontend.panels;

import com.epam.brest.courses.frontend.panels.list.CarDtoDataModel;

import javax.swing.*;
import javax.swing.text.DateFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class StatisticsPanel extends JPanel {

    private PanelSwitcher cardSwitcher;
    public static LocalDate localDateFrom;
    public static LocalDate localDateTo;

    public StatisticsPanel(PanelSwitcher cardSwitcher1) {
        this.cardSwitcher = cardSwitcher1;

        // Определение маски и поля ввода даты
        DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        // Форматирующий объект даты
        DateFormatter dateFormatter = new DateFormatter(date);
        dateFormatter.setAllowsInvalid(false);
        dateFormatter.setOverwriteMode(true);

        // Создание форматированного текстового поля даты (от какого числа)
        JFormattedTextField ftfDateFrom = new JFormattedTextField(dateFormatter);
        ftfDateFrom.setColumns(32);
        ftfDateFrom.setValue(new Date());

        // Создание форматированного текстового поля даты (до какого числа)
        JFormattedTextField ftfDateTo = new JFormattedTextField(dateFormatter);
        ftfDateTo.setColumns(32);
        ftfDateTo.setValue(new Date());

        // Добавляем поле ввода даты на панель
        add(ftfDateFrom);
        add(ftfDateTo);

        JButton okBtn = new JButton("OK");
        add(okBtn);
        okBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Date dateFrom = (Date) ftfDateFrom.getValue();
                localDateFrom = dateFrom.toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();
                Date dateTo = (Date) ftfDateTo.getValue();
                localDateTo = dateTo.toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();
                cardSwitcher.switchTo(CarDtoDataModel.class.getName());
            }
        });
    }
}
