import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.logging.Level;


public class ResourceManager {
    private final Map<String, BufferedImage> images = new HashMap<>();
    private static final Logger LOGGER = Logger.getLogger(ResourceManager.class.getName());

    public ResourceManager() {
        loadResources();
    }

    private void loadResources() {
        loadResource("brick_-1","/hitpoints_-1.png",Color.DARK_GRAY,true);
        loadResource("brick_0", "/hitpoints_0.jpg",new Color(255, 0, 0, 128),true);
        loadResource("brick_1", "/hitpoints_1.jpg",Color.RED,true);
        loadResource("brick_2", "/hitpoints_2.jpeg",new Color(128, 0, 0),true);
        loadResource("DOUBLE_BALL", "/double_ball.png", Color.YELLOW,true);
        loadResource("EXTRA_LIFE", "/extra_life.png", Color.ORANGE,true);
        loadResource("FORCE_FIELD", "/force_field.png", Color.GREEN,true);
        loadResource("REVERSE_CONTROLS", "/reverse_command.png", Color.MAGENTA,true);
        loadResource("SPEED_BOOST", "/speed_boost.png", Color.RED,true);
        loadResource("Background","/sfondo.jpg",Color.BLUE,false);
    }

    private void loadResource(String name, String path, Color color,boolean includeBorder) {
        try {
            URL resourceUrl = getClass().getResource(path);
            if (resourceUrl != null) {
                BufferedImage image = ImageIO.read(resourceUrl);
                images.put(name, image);
            } else {
                LOGGER.log(Level.SEVERE, "Risorsa non trovata: {0}", path);
                images.put(name, createPlaceholderImage(color,includeBorder));
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Errore nel caricamento della risorsa: " + path, e);
            images.put(name, createPlaceholderImage(color,includeBorder));
        }
    }

    public BufferedImage getImage(String name) {
        return images.get(name);
    }

    private BufferedImage createPlaceholderImage(Color color,boolean includeBorder) {
        BufferedImage placeholderImage = new BufferedImage(50,50, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = placeholderImage.createGraphics();

        g2d.setColor(color);
        g2d.fillRect(0, 0, 50,50);

        if (includeBorder) {
            g2d.setColor(Color.BLACK);
            g2d.drawRect(0, 0, placeholderImage.getWidth() - 1, placeholderImage.getHeight() - 1); // -1 per stare dentro i limiti dell'immagine
        }
        g2d.dispose();
        return placeholderImage;
    }
}
