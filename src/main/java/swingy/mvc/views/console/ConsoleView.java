package swingy.mvc.views.console;

import org.springframework.stereotype.Component;
import swingy.mvc.Controller;
import swingy.mvc.models.Enemy;
import swingy.mvc.models.Hero;
import swingy.mvc.views.InterfaceView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static swingy.utils.Constants.*;
import static swingy.utils.Utils.getIntegerFromInputWithMappingForUI;

@Component
public class ConsoleView implements InterfaceView {

    private final Controller controller;
    private final Scanner scanner;
    private final ConsoleHeroPicker consoleHeroPicker;
    private final List<char[]> currentMap;

    public ConsoleView(Controller controller, Scanner scanner, ConsoleHeroPicker consoleHeroPicker) {
        this.controller = controller;
        this.scanner = scanner;
        this.consoleHeroPicker = consoleHeroPicker;
        currentMap = new ArrayList<>();
    }

    @Override
    public void pickHero() {
        try {
            controller.setHero(consoleHeroPicker.getHero());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw() {
        fillAndPrintMap();
        System.out.println(CONSOLE_CONTROLS);
        controller.keyPressed(getIntegerFromInputWithMappingForUI(scanner, controller));
    }

    @Override
    public void viewRepaint() {
        this.draw();
    }

    @Override
    public void scrollPositionManager() {
        //do nothing
    }

    @Override
    public void updateData() {
        //do nothing
    }

    @Override
    public boolean askYesOrNoQuestion(String message) {
        System.out.println(message + "\n 1 - Yes     2 - No");

        int pressedKey = getIntegerFromInputWithMappingForUI(scanner, controller);
        while (pressedKey != 38 && pressedKey != 37) {
            System.err.println("Bad value typed.");
            pressedKey = getIntegerFromInputWithMappingForUI(scanner, controller);
        }

        return pressedKey == 38;
    }

    @Override
    public String getCurrentViewType() {
        return "console";
    }

    @Override
    public void printTextToLog(String textToPrint) {
        System.out.println(textToPrint);
    }

    @Override
    public void close() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private void fillAndPrintMap() {
        currentMap.clear();

        char[] buffer = new char[controller.getMapSize()];
        Arrays.fill(buffer, '.');
        for (int i = 0; i < controller.getMapSize(); ++i) {
            currentMap.add(buffer.clone());
        }

        Hero hero = consoleHeroPicker.getHero();
        currentMap.get(hero.getPosition().y)[hero.getPosition().x] = HERO_SYMBOL;
        for (Enemy enemy : controller.getEnemies()) {
            currentMap.get(enemy.getPosition().y)[enemy.getPosition().x] = ENEMY_SYMBOL;
        }

        int lineNumber = 0;
        for (char[] line : currentMap) {
            System.out.println(LEFT_INDENT + String.valueOf(line) + getLineWithStat(lineNumber++));
        }
    }

    private String getLineWithStat(int lineNumber) {
        if (lineNumber > 9) {
            return "";
        }

        Hero hero = controller.getHero();
        StringBuilder stat = new StringBuilder(STAT_INDENT);
        switch (lineNumber) {
            case 0:
                stat.append("Name: ").append(hero.getName());
                break;
            case 1:
                stat.append("Race: ").append(hero.getRace());
                break;
            case 2:
                stat.append("Level: ").append(hero.getLevel());
                break;
            case 3:
                stat.append("Exp: ").append(hero.getExp()).append("/")
                        .append(hero.getExpForLevelUp());
                break;
            case 4:
                stat.append("Position [").append(hero.getPosition().x).append(", ")
                        .append(hero.getPosition().y).append("]");
                break;
            case 5:
                stat.append("Attack: ").append(hero.getAttack());
                break;
            case 6:
                stat.append("Defense: ").append(hero.getDefense());
                break;
            case 7:
                stat.append("Hp: ").append(hero.getHp()).append("/")
                        .append(hero.getMaxHp());
                break;
            case 8:
                if (hero.getArtifact() != null && !hero.getArtifact().getType().isEmpty()) {
                    stat.append("Artifact-").append(hero.getArtifact().getType()).append(": ")
                            .append(hero.getArtifact().getValue());
                }
                break;
        }

        return stat.toString();
    }
}
