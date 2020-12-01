package swingy.mvc.views.swing;

import swingy.mvc.Controller;
import swingy.mvc.models.Enemy;
import swingy.resources.Resources;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static swingy.utils.Constants.HERO_COLOR;
import static swingy.utils.Constants.SQUARE_SIZE;

public class GuiMap extends JPanel {
    private final Controller controller;

    public GuiMap(Controller controller) {
        this.controller = controller;
        setLayout(null);
        setDoubleBuffered(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;

        this.drawMap(g2);
        this.drawHero(g2);
        this.drawEnemies(g2);
    }

    private void drawMap(Graphics2D g2) {
        setPreferredSize(new Dimension(SQUARE_SIZE * controller.getMapSize(), SQUARE_SIZE * controller.getMapSize()));

        for (int i = 0; i < controller.getMapSize(); ++i) {
            for (int j = 0; j < controller.getMapSize(); ++j) {
                g2.drawRect(SQUARE_SIZE * j, SQUARE_SIZE * i, SQUARE_SIZE, SQUARE_SIZE);
            }
        }
    }

    private void drawHero(Graphics2D g2) {
        Image image = getToolkit().getImage(Resources.getPathToResources().concat("chars/")
                .concat(controller.getHero().getRace()).concat(".png"));

        prepareImage(image, this);

        g2.setColor(HERO_COLOR);
        g2.fillRect(controller.getHero().getPosition().x * SQUARE_SIZE, controller.getHero().getPosition().y * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
        g2.drawImage(image, controller.getHero().getPosition().x * SQUARE_SIZE + (SQUARE_SIZE >> 2), controller.getHero().getPosition().y * SQUARE_SIZE, this);
    }

    private void drawEnemies(Graphics2D g2) {
        ArrayList<Enemy> enemies = controller.getEnemies();
        String pathToResources = Resources.getPathToResources();

        for (Enemy enemy : enemies) {
            Image image = getToolkit().getImage(pathToResources.concat("chars/enemy_")
                    .concat(String.valueOf(enemy.getImageNumber())).concat(".png"));

            prepareImage(image, this);
            g2.drawImage(image, enemy.getPosition().x * SQUARE_SIZE + (SQUARE_SIZE >> 2), enemy.getPosition().y * SQUARE_SIZE, this);
        }
    }
}