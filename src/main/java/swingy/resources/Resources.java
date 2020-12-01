package swingy.resources;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Resources {
    public static Font font2;
    public static Font font3; // TODO DODELAT FONTI

    private Resources() {
    }

    static {
        String pathToResources = getPathToResources();

        try {
            font2 = Font.createFont(Font.TRUETYPE_FONT, new File(pathToResources.concat("fonts/font2.ttf"))).deriveFont(20f);
            font3 = Font.createFont(Font.TRUETYPE_FONT, new File(pathToResources.concat("fonts/font3.ttf"))).deriveFont(20f);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
    }

    public static String getPathToResources() {
        return System.getProperty("user.dir").concat("/src/main/resources/");
    }
}
