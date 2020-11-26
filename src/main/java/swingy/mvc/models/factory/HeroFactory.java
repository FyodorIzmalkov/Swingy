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
            case "Ork":
                return setDefaultOrkStats(newHero);
            case "Elf":
                return setDefaultElfStats(newHero);
        }
        System.err.println("Пытаемся создать героя неизвестной расы");
        return newHero;
    }

    private static Hero setDefaultHumanStats(Hero hero) {
        hero.setRace("human");
        hero.setLevel(1);
        hero.setExp(0);
        hero.setAttack(7);
        hero.setDefense(7);
        hero.setMaxHp(150);
        hero.setHP(150);
        return hero;
    }

    private static Hero setDefaultOrkStats(Hero hero) {
        hero.setRace("ork");
        hero.setLevel(1);
        hero.setExp(0);
        hero.setAttack(12);
        hero.setDefense(2);
        hero.setMaxHp(200);
        hero.setHP(200);
        return hero;
    }

    private static Hero setDefaultElfStats(Hero hero) {
        hero.setRace("elf");
        hero.setLevel(1);
        hero.setExp(0);
        hero.setAttack(5);
        hero.setDefense(12);
        hero.setMaxHp(130);
        hero.setHP(130);
        return hero;
    }
}