package com.epam.brest.courses;

import com.epam.brest.courses.service_api.CarService;
import com.epam.brest.courses.swing.MakeOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.*;
import java.awt.*;

@SpringBootApplication
public class ApplicationSwing extends JFrame {

    private final CarService carService;

    @Autowired
    public ApplicationSwing(CarService carService) {
        this.carService = carService;
        initUI();

    }

    private void initUI() {

        JTabbedPane tabbedPane = new JTabbedPane();
        MakeOrder makeOrder = new MakeOrder(carService);

        tabbedPane.addTab("Make order", makeOrder.createPanel("First panel 1"));
//        tabbedPane.addTab("Show orders", makeOrder.createPanel("Second panel"));
//        tabbedPane.addTab("Statistics", makeOrder.createPanel("Third panel"));

        createLayout(tabbedPane);

        setTitle("Car-Rental-App");
        setSize(860, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void createLayout(JComponent... arg) {

        Container pane = getContentPane();
        GroupLayout gl = new GroupLayout(pane);
        pane.setLayout(gl);

        gl.setAutoCreateContainerGaps(true);
        gl.setAutoCreateGaps(true);

        gl.setHorizontalGroup(gl.createSequentialGroup()
                .addComponent(arg[0])
        );

        gl.setVerticalGroup(gl.createParallelGroup()
                .addComponent(arg[0])
        );

        pack();
    }

    public static void main(String[] args) {

        ConfigurableApplicationContext ctx = new SpringApplicationBuilder(ApplicationSwing.class)
                .headless(false).run(args);

        EventQueue.invokeLater(() -> {

            ApplicationSwing ex = ctx.getBean(ApplicationSwing.class);
            ex.setVisible(true);
        });
    }
}
