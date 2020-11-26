package swingy.database;

import swingy.mvc.models.Hero;

import java.util.List;

public interface DataManager {

    List<String> getHeroesNames();

    void addNewHero(Hero newHero);

    void removeHero(String name);

    Hero getHero(String name);

    void updateHero(Hero hero);
}
