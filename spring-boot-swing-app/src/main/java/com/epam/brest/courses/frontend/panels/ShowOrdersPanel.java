package com.epam.brest.courses.frontend.panels;

import com.epam.brest.courses.frontend.panels.list.OrderDtoDataModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShowOrdersPanel extends JPanel {

    private static final long serialVersionUID = -2976633621616925350L;
    private PanelSwitcher cardSwitcher;

    public ShowOrdersPanel(PanelSwitcher cardSwitcher1) {
        this.cardSwitcher = cardSwitcher1;

        JButton btnListOrderDtos = new JButton("List OrderDtos");
        add(btnListOrderDtos);
        btnListOrderDtos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardSwitcher.switchTo(OrderDtoDataModel.class.getName());
            }
        });
    }
}
