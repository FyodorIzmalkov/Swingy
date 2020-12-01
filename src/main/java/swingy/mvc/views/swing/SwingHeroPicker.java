package swingy.mvc.views.swing;

import org.springframework.stereotype.Component;
import swingy.database.DataManager;
import swingy.mvc.models.Hero;
import swingy.resources.Resources;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static swingy.mvc.models.factory.HeroFactory.createNewHero;
import static swingy.utils.Constants.HERO_NAME_FONT;
import static swingy.utils.Constants.races;
import static swingy.utils.Utils.validateHeroAndReturnErrorMessages;

@Component
public class SwingHeroPicker {
    private final MainPanel panel;
    private final JTextField inputForHeroName = new JTextField("", 5);
    private final JComboBox<String> heroRaces = new JComboBox<>(races);
    private final JComboBox<String> createdHeroes = new JComboBox<>();
    private final Map<String, JLabel> labels;
    private final Map<String, JButton> buttons;
    private final DataManager dataManager;
    private GuiHeroStats stats;
    private Hero selectedHero;

    public SwingHeroPicker(MainPanel panel, DataManager dataManager) {
        this.panel = panel;
        this.dataManager = dataManager;

        labels = new HashMap<>();
        labels.put("Name", new JLabel("Name:"));
        labels.put("Old", new JLabel("Previous saved heroes:"));

        buttons = new HashMap<>();
        buttons.put("create", new JButton("Create new hero"));
        buttons.put("select", new JButton("Select"));
        buttons.put("remove", new JButton("Remove"));
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
        labels.get("Name").setLocation(950, 50);
        labels.get("Name").setSize(150, 100);
        labels.get("Name").setFont(HERO_NAME_FONT);

        labels.get("Old").setLocation(85, 100);
        labels.get("Old").setSize(200, 100);
        labels.get("Old").setFont(HERO_NAME_FONT);
    }

    private void initNameInputs() {
        inputForHeroName.setLocation(900, 125);
        inputForHeroName.setSize(150, 35);
        inputForHeroName.setFont(Resources.font3);
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
            createdHeroes.addItem(name);
        }

        createdHeroes.setLocation(100, 175);
        createdHeroes.setSize(160, 50);
        createdHeroes.addItemListener((ItemEvent e) -> {
            try {
                Hero pickedHero = dataManager.getHero((String) createdHeroes.getSelectedItem());
                if (pickedHero == null) {
                    pickedHero = createNewHero(heroRaces.getSelectedItem().toString());
                }

                stats.setHero(pickedHero);
                stats.updateStats();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        stats = new GuiHeroStats(!names.isEmpty() ? dataManager.getHero(createdHeroes.getSelectedItem().toString()) :
                createNewHero(heroRaces.getSelectedItem().toString()));
        stats.setLocation(400, 400);
    }

    private void initButtons() {
        buttons.get("select").setLocation(75, 250);
        buttons.get("select").setSize(100, 25);
        buttons.get("select").addActionListener((ActionEvent e) -> {
            if (createdHeroes.getItemCount() == 0) {
                JOptionPane.showMessageDialog(panel, "You do not have any hero, create one!");
            } else {
                selectHero();
                panel.removeAll();
            }
        });
        buttons.get("select").setFont(Resources.font3);

        buttons.get("create").setLocation(875, 250);
        buttons.get("create").setSize(210, 25);
        buttons.get("create").addActionListener((ActionEvent e) -> tryToCreateNewHero());
        buttons.get("create").setFont(Resources.font3);

        buttons.get("remove").setLocation(185, 250);
        buttons.get("remove").setSize(100, 25);
        buttons.get("remove").addActionListener((ActionEvent e) -> tryToRemoveHero());
        buttons.get("remove").setFont(Resources.font3);
    }

    private void putObjectsOnPanel() {
        panel.add(labels.get("Name"));
        panel.add(labels.get("Old"));
        panel.add(inputForHeroName);
        panel.add(heroRaces);
        panel.add(createdHeroes);
        panel.add(stats);
        panel.add(buttons.get("create"));
        panel.add(buttons.get("select"));
        panel.add(buttons.get("remove"));


        labels.get("Name").repaint();
        labels.get("Old").repaint();

        inputForHeroName.repaint();
        heroRaces.repaint();
        createdHeroes.repaint();
        stats.repaint();
        stats.updateStats();

        buttons.get("create").repaint();
        buttons.get("select").repaint();
        buttons.get("remove").repaint();
    }

    private void tryToCreateNewHero() {
        Hero newHero = createNewHero(heroRaces.getSelectedItem().toString());


        String errorMessage = validateHeroAndReturnErrorMessages(newHero);
        for (int i = 0; i < createdHeroes.getItemCount(); ++i) {
            if (createdHeroes.getItemAt(i).equals(inputForHeroName.getText())) {
                errorMessage = "Hero with such name already created";
            }
        }

        if (!errorMessage.isEmpty()) {
            JOptionPane.showMessageDialog(panel, errorMessage);
        } else {
            newHero.setName(inputForHeroName.getText());
            dataManager.addNewHero(newHero);
            createdHeroes.addItem(inputForHeroName.getText());
            inputForHeroName.setText("");
        }
    }

    private void tryToRemoveHero() {
        String heroToRemove = (String) createdHeroes.getSelectedItem();

        dataManager.removeHero(heroToRemove);
        createdHeroes.removeItem(heroToRemove);
    }

    private void selectHero() {
        this.selectedHero = dataManager.getHero(createdHeroes.getSelectedItem().toString());
    }
}