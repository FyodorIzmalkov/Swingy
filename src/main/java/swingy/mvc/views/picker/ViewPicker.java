package swingy.mvc.views.picker;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import swingy.mvc.Controller;
import swingy.mvc.views.InterfaceView;
import swingy.mvc.views.console.ConsoleHeroPicker;
import swingy.mvc.views.console.ConsoleView;
import swingy.mvc.views.swing.MainPanel;
import swingy.mvc.views.swing.SwingHeroPicker;
import swingy.mvc.views.swing.SwingView;

import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class ViewPicker {
    private final Scanner scanner;
    private final ConsoleHeroPicker consoleHeroPicker;
    private final SwingHeroPicker swingHeroPicker;
    private final MainPanel mainPanel;


    public InterfaceView getInterfaceView(String viewName, Controller controller) {
        return "gui".equals(viewName) ? new SwingView(controller, swingHeroPicker, mainPanel) : new ConsoleView(controller, scanner, consoleHeroPicker);
    }
}
