package swingy.mvc.views.picker;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import swingy.database.DataManager;
import swingy.mvc.Controller;
import swingy.mvc.views.InterfaceView;
import swingy.mvc.views.console.ConsoleHeroPicker;
import swingy.mvc.views.console.ConsoleView;
import swingy.mvc.views.gui.GuiHeroPicker;
import swingy.mvc.views.gui.GuiView;
import swingy.mvc.views.gui.MainPanel;

import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class ViewPicker {
    private final Scanner scanner;
    private final ConsoleHeroPicker consoleHeroPicker;
    private final DataManager dataManager;


    public InterfaceView getInterfaceView(String viewName, Controller controller) {
        MainPanel mainPanel = getMainPanel();
        GuiHeroPicker guiHeroPicker = new GuiHeroPicker(mainPanel, dataManager);
        return "gui".equals(viewName) ? new GuiView(controller, guiHeroPicker, mainPanel) : new ConsoleView(controller, scanner, consoleHeroPicker);
    }

    private static MainPanel getMainPanel() {
        return new MainPanel();
    }
}
