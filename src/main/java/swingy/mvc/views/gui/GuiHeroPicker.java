package swingy.mvc.views.gui;

import org.springframework.stereotype.Component;
import swingy.database.DataManager;
import swingy.mvc.models.Hero;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static swingy.mvc.models.factory.HeroFactory.createNewHero;
import static swingy.utils.Constants.*;
import static swingy.utils.Utils.validateHeroAndReturnErrorMessages;

@Component
public class GuiHeroPicker {
    private final MainPanel panel;
    private final JTextField inputForHeroName = new JTextField("", 5);
    private final JComboBox<String> heroRaces = new JComboBox<>(races);
    private final JComboBox<String> savedHeroes = new JComboBox<>();
    private final Map<String, JLabel> labels;
    private final Map<String, JButton> buttons;
    private final DataManager dataManager;
    private GuiHeroStats stats;
    private Hero selectedHero;

    public GuiHeroPicker(MainPanel mainPanel, DataManager dataManager) {
        this.panel = mainPanel;
        this.dataManager = dataManager;

        labels = new HashMap<>();
        labels.put("Name", new JLabel("New hero name:"));
        labels.put("saved_heroes", new JLabel("Saved heroes:"));

        buttons = new HashMap<>();
        buttons.put("create_new_hero", new JButton("Create new hero"));
        buttons.put("select", new JButton("Select"));
        buttons.put("delete", new JButton("Delete"));
    }

    public Hero pickHero() {
        initInterfaceObjects();

        panel.revalidate();
        panel.repaint();

        while (true) {
            if (panel.getComponents().length == 0) {
                break;
            }
        }
        panel.revalidate();
        panel.repaint();

        return selectedHero;
    }

    private void initInterfaceObjects() {
        initLabels();
        initNameInputs();
        initBoxes();
        initButtons();
        putObjectsOnPanel();
    }

    private void initLabels() {
        JLabel nameLabel = labels.get("Name");
        nameLabel.setLocation(900, 50);
        nameLabel.setSize(200, 100);
        nameLabel.setFont(HERO_NAME_FONT);

        JLabel savedHeroesLabel = labels.get("saved_heroes");
        savedHeroesLabel.setLocation(100, 50);
        savedHeroesLabel.setSize(160, 100);
        savedHeroesLabel.setFont(HERO_NAME_FONT);
    }

    private void initNameInputs() {
        inputForHeroName.setLocation(900, 125);
        inputForHeroName.setSize(150, 35);
        inputForHeroName.setFont(HERO_NAME_FONT);
    }

    private void initBoxes() {
        heroRaces.setLocation(898, 175);
        heroRaces.setSize(160, 50);
        heroRaces.addItemListener((ItemEvent e) -> {
                    stats.setHero(createNewHero(heroRaces.getSelectedItem().toString()));
                    stats.updateStats();
                }
        );

        List<String> names = dataManager.getHeroesNames();
        for (String name : names) {
            savedHeroes.addItem(name);
        }

        savedHeroes.setLocation(100, 125);
        savedHeroes.setSize(160, 50);
        savedHeroes.addItemListener((ItemEvent e) -> {
            try {
                Hero pickedHero = dataManager.getHero((String) savedHeroes.getSelectedItem());
                if (pickedHero == null) {
                    pickedHero = createNewHero(heroRaces.getSelectedItem().toString());
                }

                stats.setHero(pickedHero);
                stats.updateStats();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        stats = new GuiHeroStats(!names.isEmpty() ? dataManager.getHero(savedHeroes.getSelectedItem().toString()) :
                createNewHero(races[HUMAN - 1]));
        stats.setLocation(400, 50);
    }

    private void initButtons() {
        JButton selectButton = buttons.get("select");
        selectButton.setLocation(75, 195);
        selectButton.setSize(100, 25);
        selectButton.addActionListener((ActionEvent e) -> {
            if (savedHeroes.getItemCount() == 0) {
                JOptionPane.showMessageDialog(panel, "You do not have any hero, create one!");
            } else {
                selectHero();
                panel.removeAll();
                panel.setPaintLogo(false);
            }
        });
        selectButton.setFont(HERO_NAME_FONT);

        JButton deleteButton = buttons.get("delete");
        deleteButton.setLocation(185, 195);
        deleteButton.setSize(100, 25);
        deleteButton.addActionListener((ActionEvent e) -> tryToRemoveHero());
        deleteButton.setFont(HERO_NAME_FONT);

        JButton createButton = buttons.get("create_new_hero");
        createButton.setLocation(875, 245);
        createButton.setSize(210, 25);
        createButton.addActionListener((ActionEvent e) -> tryToCreateNewHero());
        createButton.setFont(HERO_NAME_FONT);
    }

    private void putObjectsOnPanel() {
        panel.add(labels.get("Name"));
        panel.add(labels.get("saved_heroes"));
        panel.add(inputForHeroName);
        panel.add(heroRaces);
        panel.add(savedHeroes);
        panel.add(stats);
        panel.add(buttons.get("create_new_hero"));
        panel.add(buttons.get("select"));
        panel.add(buttons.get("delete"));
        panel.setPaintLogo(true);


        labels.get("Name").repaint();
        labels.get("saved_heroes").repaint();

        inputForHeroName.repaint();
        heroRaces.repaint();
        savedHeroes.repaint();
        stats.repaint();
        stats.updateStats();

        buttons.get("create_new_hero").repaint();
        buttons.get("select").repaint();
        buttons.get("delete").repaint();
    }

    private void tryToCreateNewHero() {
        Hero newHero = createNewHero(heroRaces.getSelectedItem().toString());
        newHero.setName(inputForHeroName.getText());


        String errorMessage = validateHeroAndReturnErrorMessages(newHero);
        for (int i = 0; i < savedHeroes.getItemCount(); ++i) {
            if (savedHeroes.getItemAt(i).equals(inputForHeroName.getText())) {
                errorMessage = "Hero with such name already created";
            }
        }

        if (!errorMessage.isEmpty()) {
            JOptionPane.showMessageDialog(panel, errorMessage);
        } else {
            dataManager.addNewHero(newHero);
            savedHeroes.addItem(inputForHeroName.getText());
            inputForHeroName.setText("");
        }
    }

    private void tryToRemoveHero() {
        String heroToRemove = (String) savedHeroes.getSelectedItem();

        dataManager.removeHero(heroToRemove);
        savedHeroes.removeItem(heroToRemove);
    }

    private void selectHero() {
        this.selectedHero = dataManager.getHero(savedHeroes.getSelectedItem().toString());
    }
}