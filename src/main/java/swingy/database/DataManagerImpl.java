package swingy.database;

import org.springframework.stereotype.Repository;
import swingy.mvc.models.Hero;

import javax.annotation.PostConstruct;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static swingy.utils.Utils.buildHeroByResultSet;

@Repository
public class DataManagerImpl implements DataManager {

    private static Statement statement;
    private static final String connectionString = "jdbc:sqlite:".concat(System.getProperty("user.dir")).concat("/saved_heroes.db");

    @PostConstruct
    private void establishConnection() {
        try {
            Connection connection = DriverManager.getConnection(connectionString);
            statement = connection.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS " +
                    "'heroes' ('name' text, 'race' text, 'level' INT, 'exp' INT," +
                    "'attack' INT, 'defense' INT, 'hp' INT, 'maxHp' INT, 'artifactT' text, 'artifactV' INT);");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String> getHeroesNames() {
        List<String> names = new ArrayList<>();
        ResultSet resultSet;
        try {
            resultSet = statement.executeQuery("SELECT * FROM heroes");
            while (resultSet.next()) {
                names.add(resultSet.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return names;
    }

    @Override
    public void addNewHero(Hero heroToAdd) {
        String artifactType = heroToAdd.getArtifact() == null ? "" : heroToAdd.getArtifact().getType();
        int artifactValue = artifactType.equals("") ? 0 : heroToAdd.getArtifact().getValue();

        String requestAdd = " VALUES ('" +
                heroToAdd.getName() + "', '" +
                heroToAdd.getRace() + "', " +
                heroToAdd.getLevel() + "," +
                heroToAdd.getExp() + "," +
                heroToAdd.getAttack() + "," +
                heroToAdd.getDefense() + "," +
                heroToAdd.getHp() + "," +
                heroToAdd.getMaxHp() + ",'" +
                artifactType + "'," +
                artifactValue + ");";
        try {
            statement.execute("INSERT INTO 'heroes' ('name', 'race', 'level', 'exp', 'attack', 'defense', 'hp', 'maxHP', 'artifactT', 'artifactV')" + requestAdd);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeHero(String nameOfHeroToRemove) {
        try {
            statement.execute("DELETE FROM heroes WHERE name = '" + nameOfHeroToRemove + "';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Hero getHero(String nameOfHeroToGet) {
        ResultSet resultSet;
        try {
            resultSet = statement.executeQuery("SELECT * FROM heroes where name = '" + nameOfHeroToGet + "';");
            if (resultSet.next()) {
                return buildHeroByResultSet(resultSet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void updateHero(Hero hero) {
        String request = "UPDATE heroes SET level = "
                + hero.getLevel() +
                ", exp = " + hero.getExp() +
                ", attack = " + hero.getAttack() +
                ", defense = " + hero.getDefense() +
                ", hp = " + hero.getMaxHp() +
                ", maxHp = " + hero.getMaxHp() +
                ", artifactT = '" + (hero.getArtifact() == null ? "" : hero.getArtifact().getType()) +
                "' , artifactV = " + hero.getArtifact().getValue() +
                " WHERE name = '" + hero.getName() + "';";

        try {
            statement.execute(request);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}