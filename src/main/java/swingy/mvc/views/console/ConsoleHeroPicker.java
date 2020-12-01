package swingy.mvc.views.console;

import org.springframework.stereotype.Component;
import swingy.database.DataManager;
import swingy.mvc.models.Hero;
import swingy.mvc.models.factory.HeroFactory;
import swingy.utils.ConsolePrinter;

import java.util.List;
import java.util.Scanner;

import static swingy.utils.Constants.*;
import static swingy.utils.Utils.getIntegerFromInput;
import static swingy.utils.Utils.validateHero;


@Component
public class ConsoleHeroPicker {
    private List<String> oldHeroesNames;
    private Hero hero;
    private final Scanner scanner;
    private final DataManager dataManager;

    public ConsoleHeroPicker(Scanner scanner, DataManager dataManager) {
        this.scanner = scanner;
        this.dataManager = dataManager;
    }

    public Hero getHero() {
        oldHeroesNames = dataManager.getHeroesNames();

        while (hero == null) {
            ConsolePrinter.getPrinter()
                    .setNumber(0).setMessage("exit")
                    .setNumber(1).setMessage("select your old hero")
                    .setNumber(2).setMessage("create new hero")
                    .printMessage();

            int value = getIntegerFromInput(scanner);
            switch (value) {
                case 0:
                    System.exit(0);
                case 1:
                    this.getOldHeroes();
                    break;
                case 2:
                    this.createNewHero();
                    break;
            }
        }

        return hero;
    }

    private void getOldHeroes() {
        while (true) {

            if (oldHeroesNames.size() == 0) {
                System.out.println("It looks like you have no heroes, feel free to create one!");
                return;
            }

            int index = 0;
            ConsolePrinter consolePrinter = ConsolePrinter.getPrinter()
                    .setNumber(0).setMessage("go back");
            for (String heroName : oldHeroesNames) {
                consolePrinter.setNumber(++index).setMessage("play as ").setMessage(heroName);
            }
            consolePrinter.printMessage();

            int value;
            if ((value = getIntegerFromInput(scanner)) == 0) {
                return;
            } else if (value <= index) {
                Hero possiblyPickedHero = dataManager.getHero(oldHeroesNames.get(value - 1));
                System.out.println(possiblyPickedHero.toString());
                ConsolePrinter.getPrinter()
                        .setNumber(1).setMessage("play this hero")
                        .setNumber(2).setMessage("remove this hero")
                        .setNumber(3).setMessage("go back")
                        .printMessage();

                int typedInteger = getIntegerFromInput(scanner);
                if (typedInteger == 1) {
                    hero = possiblyPickedHero;
                } else if (typedInteger == 2) {
                    try {
                        dataManager.removeHero(possiblyPickedHero.getName());
                        oldHeroesNames.remove(value - 1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (typedInteger == 1 || typedInteger == 3)
                    return;
            }
        }
    }

    private void createNewHero() {
        while (true) {
            ConsolePrinter.getPrinter()
                    .setNumber(0).setMessage("go back")
                    .setNumber(1).setMessage("create Human")
                    .setNumber(2).setMessage("create Orc")
                    .setNumber(3).setMessage("create Elf")
                    .printMessage();

            int typedInteger;
            if ((typedInteger = getIntegerFromInput(scanner)) == 0) {
                return;
            }

            if ((typedInteger > 0 && typedInteger < 4)) {
                switch (typedInteger) {
                    case HUMAN:
                        this.createNewHero(races[HUMAN - 1]); // -1 to match array index from 0
                        break;
                    case ORC:
                        this.createNewHero(races[ORC - 1]);
                        break;
                    case ELF:
                        this.createNewHero(races[ELF - 1]);
                        break;
                }
            }
        }
    }

    private void createNewHero(String heroRace) {
        Hero newHero = HeroFactory.createNewHero(heroRace);
        ConsolePrinter.getPrinter()
                .setNumber(1).setMessage("create this hero")
                .setNumber(2).setMessage("cancel creation")
                .setMessage(newHero.toString())
                .printMessage();

        int typedInteger = getIntegerFromInput(scanner);
        if (typedInteger == 1) {
            while (true) {
                System.out.print("Enter a name for your hero: ");
                String heroName = scanner.nextLine();
                newHero.setName(heroName);

                if (!validateHero(newHero)) {
                    continue;
                }

                boolean nameIsNotUsed = true;
                for (String name : oldHeroesNames) {
                    if (name.equals(heroName)) {
                        System.err.println("Use another name, this name is already used.");
                        nameIsNotUsed = false;
                        break;
                    }
                }

                if (nameIsNotUsed) {
                    break;
                }
            }

            try {
                dataManager.addNewHero(newHero);
                this.oldHeroesNames.add(newHero.getName());
                System.out.println("Your hero was created! You can go back and play it.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}