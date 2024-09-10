package com.example;

import com.example.config.Config;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        ConsoleApp app = context.getBean(ConsoleApp.class);
        app.run();
        context.close();
    }

}
