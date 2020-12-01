package swingy.mvc.views.swing;

import swingy.mvc.Controller;
import swingy.mvc.views.InterfaceView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static swingy.utils.Constants.*;


public class SwingView extends JFrame implements InterfaceView {
    private final SwingHeroPicker swingHeroPicker;
    private final MainPanel mainPanel;
    private final Controller controller;
    private JScrollPane mapWithScroll;
    private JScrollPane scrollGameLog;
    private GuiHeroStats stats;
    private GuiGameLog gameLog;

    public SwingView(Controller controller, SwingHeroPicker swingHeroPicker, MainPanel mainPanel) {
        super("Swingy");

        this.controller = controller;
        this.swingHeroPicker = swingHeroPicker;
        this.mainPanel = mainPanel;

        setBounds(500, 250, MAIN_WIDTH, MAIN_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        setVisible(true);
        setFocusable(true);
        setContentPane(this.mainPanel);
        KeyAdapter keySupporter = new KeySupporter();
        addKeyListener(keySupporter);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                if (controller.getHero() != null) {
                    controller.saveHeroProgress();
                }
            }
        });
    }

    @Override
    public void pickHero() {
        controller.setHero(swingHeroPicker.pickHero());
    }

    @Override
    public void draw() {
        this.initScrolls();
        stats = new GuiHeroStats(controller.getHero());
        stats.updateStats();

        mainPanel.add(scrollGameLog);
        mainPanel.add(stats);
        mainPanel.add(mapWithScroll);

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    @Override
    public void viewRepaint() {
        mainPanel.repaint();
    }

    @Override
    public void printTextToLog(String text) {
        gameLog.append(" " + text + "\n");
        this.scrollGameLog.getViewport().setViewPosition(new Point(scrollGameLog.getViewport().getViewPosition().x,
                scrollGameLog.getViewport().getViewPosition().y + 30));
    }

    @Override
    public void scrollPositionManager() {
        Point newPosition = new Point(controller.getHero().getPosition().x * SQUARE_SIZE - 275,
                controller.getHero().getPosition().y * SQUARE_SIZE - 275);

        newPosition.y = newPosition.y <= 0 ? mapWithScroll.getViewport().getViewPosition().y : newPosition.y;
        newPosition.x = newPosition.x <= 0 ? mapWithScroll.getViewport().getViewPosition().x : newPosition.x;
        this.mapWithScroll.getViewport().setViewPosition(newPosition);
    }

    @Override
    public boolean askYesOrNoQuestion(String message) {
        int dialogResult = JOptionPane.showConfirmDialog(this, message, "You have a choice", JOptionPane.YES_NO_OPTION);

        return dialogResult == 0;
    }

    @Override
    public void updateData() {
        stats.updateStats();
    }

    @Override
    public String getCurrentViewType() {
        return "gui";
    }

    @Override
    public void close() {
        setVisible(false);
        dispose();
    }

    private void initScrolls() {
        GuiMap map = new GuiMap(controller);
        mapWithScroll = new JScrollPane(map);
        mapWithScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        mapWithScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        mapWithScroll.setBounds(500, 50, MAP_WITH_SCROLL_WIDTH, MAP_WITH_SCROLL_HEIGHT);
        mapWithScroll.getViewport().setViewPosition(new Point(controller.getHero().getPosition().x * SQUARE_SIZE, controller.getHero().getPosition().y * SQUARE_SIZE));
        mapWithScroll.repaint();

        gameLog = new GuiGameLog();
        scrollGameLog = new JScrollPane(this.gameLog);
        scrollGameLog.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollGameLog.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollGameLog.setBounds(50, 565, 325, 400);
        scrollGameLog.repaint();
    }

    private class KeySupporter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (controller.getHero() != null) {
                if ((e.getKeyCode() > 36 && e.getKeyCode() < 41))
                    controller.keyPressed(e.getKeyCode());
                else if (e.getKeyCode() == 49)
                    controller.keyPressed(-2);
            }
        }
    }
}