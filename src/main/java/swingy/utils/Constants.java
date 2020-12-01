package swingy.utils;

import swingy.resources.Resources;

import java.awt.*;

public class Constants {
    public static final String CONSOLE_CONTROLS = "\nType: \n   0 - to exit\n\n           1 go North\n<- 2 go West      3 go East ->\n           4 go South\n type \"gui\" - to go to GUI mode";
    public static final String[] races = {"Human", "Orc", "Elf"};
    public static final int HUMAN = 1;
    public static final int ORC = 2;
    public static final int ELF = 3;
    public static final int GUI_CODE = -2;
    public static final int SQUARE_SIZE = 150;
    public static final int MAIN_WIDTH = 1920;
    public static final int MAIN_HEIGHT = 1080;

    public static final int MAP_WITH_SCROLL_WIDTH = 1400;
    public static final int MAP_WITH_SCROLL_HEIGHT = 900;

    public static final char HERO_SYMBOL = 'H';
    public static final char ENEMY_SYMBOL = 'E';
    public static final String LEFT_INDENT = "      ";
    public static final String STAT_INDENT = "        ";
    public static final String PATH_TO_ICONS = Resources.getPathToResources().concat("icons/");

    public static final Color HERO_COLOR = new Color(197, 226, 234, 255);
    public static final Color LOG_COLOR = new Color(56, 178, 229);
    public static final Font BIG_FONT = new Font("serif", Font.PLAIN, 30);
    public static final Font SMALL_FONT = new Font("serif", Font.PLAIN, 18);
    public static final Font HERO_NAME_FONT = new Font("SansSerif", Font.PLAIN, 20);
}
