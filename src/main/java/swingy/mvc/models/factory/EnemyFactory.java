package swingy.mvc.models.factory;

import swingy.mvc.models.Enemy;
import swingy.mvc.models.Hero;

import java.awt.*;
import java.util.List;
import java.util.Random;

public class EnemyFactory {
    private EnemyFactory() {
    }

    private static final Random random = new Random();

    public static Enemy createNewEnemy(int sizeMap, List<Enemy> enemies, Hero hero) {
        Enemy newEnemy = new Enemy();
        Point position = new Point();

        do {
            position.setLocation(random.nextInt(sizeMap), random.nextInt(sizeMap));
        } while (!checkIfPositionFree(position, hero.getPosition(), enemies));

        int multiplicator = hero.getLevel();
        newEnemy.setAttack((random.nextInt(10) + 1) + multiplicator);
        newEnemy.setDefense(random.nextInt(5) + multiplicator);
        newEnemy.setHp(random.nextInt(90) + 10 * multiplicator);
        newEnemy.setImageNumber(random.nextInt(7));
        newEnemy.setPosition(position);

        return newEnemy;
    }

    private static boolean checkIfPositionFree(Point positionToCheck, Point heroPosition, List<Enemy> enemies) {
        if (heroPosition.equals(positionToCheck)) {
            return false;
        }

        for (Enemy enemy : enemies) {
            if (enemy.getPosition().equals(positionToCheck)) {
                return false;
            }
        }

        return true;
    }
}
