package swingy.mvc;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import swingy.database.DataManager;
import swingy.mvc.models.Artifact;
import swingy.mvc.models.ArtifactType;
import swingy.mvc.models.Enemy;
import swingy.mvc.models.Hero;
import swingy.mvc.views.InterfaceView;
import swingy.mvc.views.picker.ViewPicker;
import swingy.utils.Utils;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import static swingy.mvc.models.factory.EnemyFactory.createNewEnemy;
import static swingy.utils.Constants.CHANGE_VIEW_CODE;
import static swingy.utils.Constants.artifactTypes;
import static swingy.utils.Utils.getArtifactValue;

@Component
public class Controller {
    private final Random random = new Random();
    private InterfaceView currentView;
    private final ViewPicker viewPicker;
    private Hero hero;
    @Getter
    private int mapSize;
    private ArrayList<Enemy> enemies = new ArrayList<>();
    @Getter
    private final DataManager dataManager;

    @Autowired
    public Controller(DataManager dataManager, ViewPicker viewPicker) {
        this.dataManager = dataManager;
        this.viewPicker = viewPicker;
    }

    public void startGame(String viewType) throws Exception {
        setGUI(viewType);

        if (hero == null) {
            currentView.pickHero();
            mapSize = Utils.getMapSize(hero.getLevel());
            initGame();
        }
        currentView.draw();
    }

    public void keyPressed(int ketPressed) {
        handlePressedKey(ketPressed);
        handleCollisionsWithEnemies();

        this.currentView.updateData();
        this.currentView.viewRepaint();

        if (this.mapSize > 9)
            this.currentView.scrollPositionManager();
    }

    public void saveHeroProgress() {
        if (currentView.askYesOrNoQuestion("Save your hero ?")) {
            dataManager.updateHero(this.hero);
        }
    }

    private void handlePressedKey(int keyPressed) {
        switch (keyPressed) {
            case 37:
                if (hero.getPosition().x - 1 >= 0) {
                    hero.move(-1, 0);
                } else {
                    keyPressed = -1;
                }
                break;

            case 38:
                if (hero.getPosition().y - 1 >= 0) {
                    hero.move(0, -1);
                } else {
                    keyPressed = -1;
                }
                break;

            case 39:
                if (hero.getPosition().x + 1 < mapSize) {
                    hero.move(1, 0);
                } else {
                    keyPressed = -1;
                }
                break;

            case 40:
                if (hero.getPosition().y + 1 < mapSize) {
                    hero.move(0, 1);
                } else {
                    keyPressed = -1;
                }
                break;

            case CHANGE_VIEW_CODE:
                try {
                    String newType = currentView.getCurrentViewType().equals("gui") ? "console" : "gui";
                    currentView.close();
                    startGame(newType);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }

        if (keyPressed == -1) {
            if (currentView.askYesOrNoQuestion("This map ended,do you want to start a new map?")) {
                initGame();
            } else {
                saveHeroProgress();
                System.exit(0);
            }
        }
    }

    private void handleCollisionsWithEnemies() {
        enemies.removeIf(enemy -> enemy.getPosition().equals(hero.getPosition()) && simulateFight(enemy));
    }

    private void initGame() {
        hero.getPosition().setLocation(mapSize >> 1, mapSize >> 1);
        hero.setHP(hero.getMaxHp());
        deployEnemies();
    }

    private boolean simulateFight(Enemy enemy) {
        Point enemyPosition = enemy.getPosition();
        currentView.printTextToLog("You met an enemy:\n    hp: " + enemy.getHp() + "\n    attack: "
                + enemy.getAttack() + "\n    defense: " + enemy.getDefense());

        if (currentView.askYesOrNoQuestion("Do you want to battle it?")) {
            simulateBattleWithEnemy(enemy);
        } else {
            if (random.nextInt(2) % 2 == 0) {
                hero.setPosition(new Point(hero.getOldPosition()));
                currentView.printTextToLog("You were lucky to escape unharmed.");
            } else {
                currentView.printTextToLog("You try to run from enemy failed.");
                simulateBattleWithEnemy(enemy);
            }
        }

        if (!enemyPosition.equals(hero.getPosition())) {
            return false;
        }

        if (enemy.getHp() <= 0) {
            int earnedExp = (enemy.getAttack() + enemy.getDefense()) << 4;
            hero.setExp(hero.getExp() + earnedExp);
            currentView.printTextToLog("Enemy killed ! You earned " + earnedExp + " experience !");
            checkForLevelUpAndLoot(enemy);
            return true;
        } else {
            hero.setPosition(new Point(hero.getOldPosition()));
            return false;
        }
    }

    private void simulateBattleWithEnemy(Enemy enemy) {
        if (random.nextInt(8) == 7) {
            enemy.setHp(0);
            currentView.printTextToLog("Nice! You made a critical hit!");
        } else {
            hero.setHP(hero.getHp() - (enemy.getAttack() << 2) + hero.getTotalDefense());
            if (checkIfHeroDied()) {
                return;
            }

            enemy.setHp(enemy.getHp() - Math.max(1, hero.getTotalAttack() - enemy.getDefense()));

            int receivedDamage = (enemy.getAttack() << 2) - hero.getTotalDefense();
            currentView.printTextToLog("You caused " + (hero.getTotalAttack() - enemy.getDefense())
                    + " damage to the enemy !\n" + (receivedDamage < 0 ? " Blocked all incoming damage" : " Received " + receivedDamage) + " damage.");
        }
    }

    private void checkForLevelUpAndLoot(Enemy enemy) {
        if (hero.getExp() >= hero.getExpForLevelUp()) {
            currentView.printTextToLog("You have reached new level ! Your attributes have increased !");
            hero.setMaxHp(hero.getMaxHp() + (5 * hero.getLevel()));
            hero.setHP(hero.getMaxHp());
            hero.setAttack(hero.getAttack() + (hero.getLevel() * 2 + 1));
            hero.setDefense(hero.getDefense() + (hero.getLevel() + 1));
            hero.setLevel(hero.getLevel() + 1);
            mapSize = Utils.getMapSize(hero.getLevel());
        }
        randomForLoot(enemy);
    }

    private boolean checkIfHeroDied() {
        if (hero.getHp() <= 0) {
            currentView.updateData();

            if (currentView.askYesOrNoQuestion("You have died,do you want to respawn at center of the map ?")) {
                initGame();
            } else {
                this.saveHeroProgress();
                System.exit(0);
            }
            return true;
        }
        return false;
    }

    private void randomForLoot(Enemy enemy) {
        if (random.nextInt(4) == 2) {
            if (random.nextInt(2) == 0) {
                int healthIncrease = random.nextInt(40) + 5;
                hero.setHP(Math.min((hero.getHp() + healthIncrease), hero.getMaxHp()));
                currentView.printTextToLog("You have found a health elixir, health increases by + " + healthIncrease + " hp !");
            } else {
                getRandomArtifact(enemy);
            }
        }
    }

    private void deployEnemies() {
        enemies = new ArrayList<>();

        for (int i = Math.max((7 + hero.getLevel() * 2), random.nextInt(mapSize)); i > 0; i--) {
            enemies.add(createNewEnemy(mapSize, enemies, hero));
        }
    }

    private void getRandomArtifact(Enemy enemy) {
        ArtifactType artifactType = artifactTypes[random.nextInt(3)];
        int artifactValue = getArtifactValue(artifactType, enemy);

        if (currentView.askYesOrNoQuestion("Found an " + artifactType + " artifact (" + artifactValue + ") do you want to pick it up ?")) {
            hero.setArtifact(new Artifact(artifactType, artifactValue));
            currentView.printTextToLog("New artifact has been equipped");
        }
    }

    private void setGUI(String viewName) {
        this.currentView = viewPicker.getInterfaceView(viewName, this);
    }

    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }
}