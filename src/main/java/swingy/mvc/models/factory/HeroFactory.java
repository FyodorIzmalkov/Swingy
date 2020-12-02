package swingy.mvc.models.factory;

import swingy.mvc.models.Hero;

public class HeroFactory {
    private HeroFactory() {
    }

    public static Hero createNewHero(String race) {
        Hero newHero = new Hero();

        switch (race) {
            case "Human":
                return setDefaultHumanStats(newHero);
            case "Orc":
                return setDefaultOrcStats(newHero);
            case "Elf":
                return setDefaultElfStats(newHero);
        }
        System.err.println("Пытаемся создать героя неизвестной расы");
        return newHero;
    }

    private static Hero setDefaultHumanStats(Hero hero) {
        hero.setRace("Human");
        hero.setLevel(1);
        hero.setExp(0);
        hero.setAttack(17);
        hero.setDefense(7);
        hero.setMaxHp(150);
        hero.setHP(150);
        return hero;
    }

    private static Hero setDefaultOrcStats(Hero hero) {
        hero.setRace("Orc");
        hero.setLevel(1);
        hero.setExp(0);
        hero.setAttack(24);
        hero.setDefense(2);
        hero.setMaxHp(200);
        hero.setHP(200);
        return hero;
    }

    private static Hero setDefaultElfStats(Hero hero) {
        hero.setRace("Elf");
        hero.setLevel(1);
        hero.setExp(0);
        hero.setAttack(12);
        hero.setDefense(12);
        hero.setMaxHp(130);
        hero.setHP(130);
        return hero;
    }
}
