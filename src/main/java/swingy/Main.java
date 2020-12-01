package swingy;

import org.springframework.context.annotation.*;
import swingy.mvc.Controller;
import swingy.mvc.views.swing.MainPanel;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan(basePackages = "swingy")
public class Main {
    private static final Set<String> consoleVariants = new HashSet<>(Arrays.asList("gui", "console"));

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Correct usage: java -jar swingy.jar console or gui");
        } else {
            try {
                AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
                Controller controller = context.getBean(Controller.class);

                if (!consoleVariants.contains(args[0])) {
                    System.err.println("You can use with only one of those arguments: gui or console.");
                    System.exit(1);
                }

                controller.startGame(args[0]);
            } catch (Exception e) {
                System.err.println("Error occurred :( ");
                e.printStackTrace();
                System.exit(1);
            }
        }
    }

    @Bean
    Scanner getScanner() {
        return new Scanner(System.in);
    }

    @Bean
    MainPanel getMainPanel(){
        return new MainPanel();
    }

}
