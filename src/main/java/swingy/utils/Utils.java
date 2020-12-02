package swingy.utils;

import swingy.mvc.Controller;
import swingy.mvc.models.Artifact;
import swingy.mvc.models.ArtifactType;
import swingy.mvc.models.Enemy;
import swingy.mvc.models.Hero;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.awt.*;
import java.sql.ResultSet;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import static swingy.utils.Constants.CHANGE_VIEW_CODE;

public class Utils {

    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    private Utils() {
    }

    public static Hero buildHeroByResultSet(ResultSet resultSet) throws Exception {
        return Hero.builder()
                .name(resultSet.getString("name"))
                .race(resultSet.getString("race"))
                .artifact(new Artifact(ArtifactType.valueOf(resultSet.getString("artifactType")), resultSet.getInt("artifactValue")))
                .attack(resultSet.getInt("attack"))
                .defense(resultSet.getInt("defense"))
                .exp(resultSet.getInt("exp"))
                .level(resultSet.getInt("level"))
                .maxHp(resultSet.getInt("maxHp"))
                .hp(resultSet.getInt("hp"))
                .position(new Point())
                .oldPosition(new Point())
                .build();
    }

    public static boolean validateHero(Hero hero) {
        Set<ConstraintViolation<Hero>> violations = validator.validate(hero);

        if (violations == null || violations.isEmpty()) {
            return true;
        } else {
            violations.forEach(violation -> System.err.println(violation.getMessage()));
            return false;
        }
    }

    public static String validateHeroAndReturnErrorMessages(Hero hero) {
        Set<ConstraintViolation<Hero>> violations = validator.validate(hero);

        if (violations == null || violations.isEmpty()) {
            return "";
        } else {
            return violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining("\n"));
        }
    }

    public static int getIntegerFromInput(Scanner scanner) {
        while (true) {
            String scannedString = scanner.nextLine();

            if (!scannedString.matches("^[0-9]+")) {
                System.err.println("Enter only number 0 - 9");
            } else {
                try {
                    return Integer.parseInt(scannedString);
                } catch (Exception e) {
                    System.err.println("Wow, stop doing such bad things, you are a bad one! Type a correct number.");
                }
            }
        }
    }

    public static int getIntegerFromInputWithMappingForUI(Scanner scanner, Controller controller) {
        int parsedInt;
        while (true) {
            String scannedString = scanner.nextLine();

            if ("gui".equals(scannedString)) {
                return CHANGE_VIEW_CODE;
            } else if (!scannedString.matches("^[0-9]+")) {
                System.err.println("Enter only number 0 - 9");
            } else {
                try {
                    parsedInt = Integer.parseInt(scannedString);
                    break;
                } catch (Exception e) {
                    System.err.println("Wow, stop doing such bad things, you are a bad one! Type a correct number.");
                }
            }
        }

        switch (parsedInt) {
            case 1:
                parsedInt = 38;
                break;
            case 2:
                parsedInt = 37;
                break;
            case 3:
                parsedInt = 39;
                break;
            case 4:
                parsedInt = 40;
                break;
            case 0:
                controller.saveHeroProgress();
                System.exit(0);
        }
        return parsedInt;
    }

    public static int getMapSize(int heroLevel) {
        return (heroLevel - 1) * 5 + 10 - (heroLevel % 2);
    }

    public static String getPathToResources() {
        return System.getProperty("user.dir").concat("/src/main/resources/");
    }

    public static int getArtifactValue(ArtifactType artifactType, Enemy enemy) {
        if (ArtifactType.WEAPON == artifactType) {
            return enemy.getAttack() + 1;
        }

        if (ArtifactType.ARMOR == artifactType) {
            return (enemy.getDefense() * 2) + 1;
        }

        return (enemy.getAttack() + enemy.getDefense()) * 3;
    }
}
