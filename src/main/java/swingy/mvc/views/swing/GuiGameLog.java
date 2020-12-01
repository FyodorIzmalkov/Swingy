package swingy.mvc.views.swing;

import swingy.resources.Resources;

import javax.swing.*;
import java.awt.*;

import static swingy.utils.Constants.LOG_COLOR;
import static swingy.utils.Constants.SMALL_FONT;

public class GuiGameLog extends JTextArea {
    public GuiGameLog() {
        setLayout(null);
        setAutoscrolls(true);
        setBackground(Color.gray);
        setForeground(LOG_COLOR);

        setEditable(false);
        setFocusable(false);
        setFont(SMALL_FONT);
    }

    @Override
    public void append(String string) {
        super.append(string);
        setRows(getRows() + 1);
    }
}