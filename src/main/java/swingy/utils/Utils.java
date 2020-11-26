package swingy.utils;

import swingy.mvc.models.Artifact;
import swingy.mvc.models.Hero;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.awt.*;
import java.sql.ResultSet;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;

public class Utils {

    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    private Utils() {
    }

    public static Hero buildHeroByResultSet(ResultSet resultSet) throws Exception {
        return Hero.builder()
                .name(resultSet.getString("name"))
                .race(resultSet.getString("race"))
                .artifact(new Artifact(resultSet.getString("artifactT"), resultSet.getInt("artifactV")))
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
        java.util.logging.Logger.getLogger("org.hibernate.validator.internal.util.Version").setLevel(Level.OFF);
        Set<ConstraintViolation<Hero>> violations = validator.validate(hero);

        if (violations == null || violations.isEmpty()) {
            return true;
        } else {
            violations.forEach(violation -> System.err.println(violation.getMessage()));
            return false;
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
}
