package swingy;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import swingy.mvc.Controller;

@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan(basePackages = "swingy")
public class Main {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java -jar swingy.jar console or gui");
        } else {
            try {
                AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
                Controller controller = context.getBean(Controller.class);
                controller.startGame(args[0]);
            } catch (Exception e) {
                System.err.println("Error occurred :( ");
                e.printStackTrace();
            }
        }
    }
}
