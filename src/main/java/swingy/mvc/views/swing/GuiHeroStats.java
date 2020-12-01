package swingy.mvc.views.swing;

import lombok.Getter;
import lombok.Setter;
import swingy.mvc.models.Hero;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import static swingy.utils.Constants.BIG_FONT;
import static swingy.utils.Constants.PATH_TO_ICONS;

public class GuiHeroStats extends JPanel {

    @Getter
    @Setter
    private Hero hero;
    private Map<String, JLabel> stats;

    public GuiHeroStats(Hero hero) {
        this.hero = hero;
        stats = new HashMap<>();

        setLayout(null);
        setSize(325, 500);
        setLocation(50, 50);
    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
        graphics.drawRect(1, 1, 323, 498);
    }

    public void updateStats() {
        if (stats.isEmpty()) {
            prepareInitialInfo();
        }

        stats.get("name").setText("Name: ".concat(hero.getName() == null ? "" : hero.getName()));
        stats.get("race").setText("Race: ".concat(hero.getRace()));

        if (hero.getPosition() != null) {
            stats.get("position").setText("Position: [".concat(String.valueOf(hero.getPosition().x)).concat(", ")
                    .concat(String.valueOf(hero.getPosition().y)).concat("]"));
        }

        stats.get("level").setText("Level: " + hero.getLevel());
        stats.get("exp").setText("Exp: " + hero.getExp() + "/" + hero.getExpForLevelUp());
        stats.get("attack").setText(String.valueOf(hero.getAttack()));
        stats.get("defense").setText(" " + hero.getDefense());
        stats.get("hp").setText(" " + hero.getHp() + "/" + hero.getMaxHp());

        if (hero.getArtifact() != null && !hero.getArtifact().getType().equals("")) {
            stats.get("artifact").setIcon(new ImageIcon(
                    PATH_TO_ICONS.concat(hero.getArtifact().getType().equals("attack") ? "attack_artifact.png" : "protection_artifact.png"))
            );
            stats.get("artifact").setText(" " + hero.getArtifact().getValue());
        } else {
            stats.get("artifact").setText("");
            stats.get("artifact").setIcon(null);
        }
    }

    private void prepareInitialInfo() {
        JLabel heroName = new JLabel("Name: ");
        heroName.setLocation(20, 5);
        heroName.setSize(300, 60);
        heroName.setFont(BIG_FONT);
        if (hero.getName() != null) {
            heroName.setText("Name: " + hero.getName());
        }

        JLabel heroRace = new JLabel("Race: " + hero.getRace());
        heroRace.setLocation(20, 55);
        heroRace.setSize(300, 60);
        heroRace.setFont(BIG_FONT);

        JLabel heroLevel = new JLabel("Level: " + hero.getLevel());
        heroLevel.setLocation(20, 105);
        heroLevel.setSize(300, 60);
        heroLevel.setFont(BIG_FONT);

        JLabel position = new JLabel("Position: [ ]");
        position.setLocation(20, 155);
        position.setSize(300, 60);
        position.setFont(BIG_FONT);
        if (hero.getPosition() != null) {
            position.setText("Position: [" + hero.getPosition().x + ", " + hero.getPosition().y + "]");
        }

        JLabel heroExp = new JLabel("Exp: " + hero.getExp() + "/" + hero.getExpForLevelUp());
        heroExp.setLocation(20, 205);
        heroExp.setSize(400, 60);
        heroExp.setFont(BIG_FONT);

        JLabel heroAttack = new JLabel(String.valueOf(hero.getAttack()), new ImageIcon(PATH_TO_ICONS.concat("sword.png")), JLabel.LEFT);
        heroAttack.setLocation(20, 260);
        heroAttack.setSize(300, 60);
        heroAttack.setFont(BIG_FONT);

        JLabel heroDef = new JLabel(" " + hero.getDefense(), new ImageIcon(PATH_TO_ICONS.concat("protection.png")), JLabel.LEFT);
        heroDef.setLocation(15, 315);
        heroDef.setSize(300, 60);
        heroDef.setFont(BIG_FONT);

        JLabel heroHp = new JLabel(" " + hero.getHp() + "/" + this.hero.getMaxHp(), new ImageIcon(PATH_TO_ICONS.concat("hp.png")), JLabel.LEFT);
        heroHp.setLocation(15, 370);
        heroHp.setSize(350, 60);
        heroHp.setFont(BIG_FONT);
        heroHp.setVerticalTextPosition(JLabel.NORTH);

        JLabel artifact = new JLabel("");
        if (hero.getArtifact() != null) {
            artifact.setIcon(new ImageIcon(PATH_TO_ICONS.concat(hero.getArtifact().getType().equals("attack") ? "attack_artifact.png" : "protection_artifact.png")));
        }
        artifact.setLocation(15, 430);
        artifact.setSize(300, 60);
        artifact.setFont(BIG_FONT);

        stats.put("name", heroName);
        stats.put("race", heroRace);
        stats.put("level", heroLevel);
        stats.put("position", position);
        stats.put("attack", heroAttack);
        stats.put("defense", heroDef);
        stats.put("hp", heroHp);
        stats.put("exp", heroExp);
        stats.put("artifact", artifact);

        add(heroName);
        add(heroRace);
        add(heroLevel);
        add(position);
        add(heroExp);
        add(heroAttack);
        add(heroDef);
        add(heroHp);
        add(artifact);
    }
}