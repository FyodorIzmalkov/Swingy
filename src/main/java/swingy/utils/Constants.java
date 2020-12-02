package swingy.utils;

import com.google.common.collect.ImmutableMap;
import swingy.mvc.models.ArtifactType;

import java.awt.*;

public class Constants {
    public static final String CONSOLE_CONTROLS = "\nType: \n   0 - to exit\n\n           1 go North\n<- 2 go West      3 go East ->\n           4 go South\n type \"gui\" - to go to GUI mode";
    public static final String[] races = {"Human", "Orc", "Elf"};
    public static final ArtifactType[] artifactTypes = {ArtifactType.WEAPON, ArtifactType.ARMOR, ArtifactType.HELM};
    public static final ImmutableMap<ArtifactType, String> artifactsMap = ImmutableMap.<ArtifactType, String>builder()
            .put(ArtifactType.WEAPON, "weapon_artifact.png")
            .put(ArtifactType.ARMOR, "armor_artifact.png")
            .put(ArtifactType.HELM, "helm_artifact.png")
            .build();
    public static final int HUMAN = 1;
    public static final int ORC = 2;
    public static final int ELF = 3;
    public static final int CHANGE_VIEW_CODE = -2;
    public static final int SQUARE_SIZE = 150;
    public static final int MAIN_WIDTH = 1920;
    public static final int MAIN_HEIGHT = 1080;

    public static final int MAP_WITH_SCROLL_WIDTH = 1400;
    public static final int MAP_WITH_SCROLL_HEIGHT = 900;

    public static final char HERO_SYMBOL = 'H';
    public static final char ENEMY_SYMBOL = 'E';
    public static final String LEFT_INDENT = "      ";
    public static final String STAT_INDENT = "        ";
    public static final String PATH_TO_ICONS = Utils.getPathToResources().concat("icons/");

    public static final Color HERO_COLOR = new Color(197, 226, 234, 255);
    public static final Color LOG_COLOR = new Color(0, 0, 0);
    public static final Font BIG_FONT = new Font("serif", Font.PLAIN, 30);
    public static final Font SMALL_FONT = new Font("serif", Font.PLAIN, 18);
    public static final Font HERO_NAME_FONT = new Font("serif", Font.PLAIN, 20);
}
