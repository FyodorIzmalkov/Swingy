package swingy.mvc.views.gui;

import javax.swing.*;
import java.awt.*;

import static swingy.utils.Constants.LOG_COLOR;
import static swingy.utils.Constants.SMALL_FONT;

public class GuiGameLog extends JTextArea {
    public GuiGameLog() {
        setLayout(null);
        setAutoscrolls(true);
        setBackground(Color.lightGray);
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