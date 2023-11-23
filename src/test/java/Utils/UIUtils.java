package Utils;

import java.awt.*;
import java.util.HashMap;

public class UIUtils {
    public static final Font FONT_GENERAL_UI = new Font("Segoe UI", Font.PLAIN, 20);
    public static final Font FONT_LINK = new Font("Segoe UI", Font.PLAIN, 18);

    public static final Color COLOR_OUTLINE = new Color(103, 112, 120);
    public static final Color COLOR_BACKGROUND = new Color(37, 51, 61);
    public static final Color COLOR_INTERACTIVE = new Color(4, 255, 247);
    public static final Color COLOR_INTERACTIVE_DARKER = new Color(77,77,255);
    public static final Color OFFWHITE = new Color(229, 229, 229);

    public static final String BUTTON_TEXT_COLLECT = "Collect Comments";
    public static final String BUTTON_TEXT_GITHUB = "My GitHub";
    public static final String BUTTON_TEXT_WEBSITE = "My Website";

    public static final String PLACEHOLDER_TEXT_URL = "Website URL";

    public static final int ROUNDNESS = 8;

    public static Graphics2D get2dGraphics(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.addRenderingHints(new HashMap<RenderingHints.Key, Object>() {{
            put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        }});
        return g2;
    }
}