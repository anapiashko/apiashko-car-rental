package com.epam.brest.courses;

import com.epam.brest.courses.frontend.panels.Main;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by Shittu on 29/05/2016.
 */
@SpringBootApplication
public class Application {
    static {
        System.setProperty("java.awt.headless", "false");
    }

    public static void main(String[] args) {
        Main.main(args);
    }
}
