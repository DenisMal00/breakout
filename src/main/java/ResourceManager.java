import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ResourceManager {
    private Map<String, BufferedImage> images = new HashMap<>();

    public ResourceManager() {
        loadResources();
    }

    private void loadResources() {
        loadResource("brick_0", "/hitpoints_0.jpg",new Color(255, 0, 0, 128));
        loadResource("brick_1", "/hitpoints_1.jpg",Color.RED);
        loadResource("brick_2", "/hitpoints_2.jpeg",new Color(128, 0, 0));
        loadResource("DOUBLE_BALL", "/double_ball.png", Color.YELLOW);
        loadResource("EXTRA_LIFE", "/extra_life.png", Color.ORANGE);
        loadResource("FORCE_FIELD", "/force_field.png", Color.GREEN);
        loadResource("REVERSE_CONTROLS", "/reverse_command.png", Color.MAGENTA);
        loadResource("SPEED_BOOST", "/speed_boost.png", Color.RED);
    }

    private void loadResource(String name, String path, Color color) {
        try {
            URL resourceUrl = getClass().getResource(path);
            if (resourceUrl != null) {
                BufferedImage image = ImageIO.read(resourceUrl);
                images.put(name, image);
            } else {
                System.err.println("Risorsa non trovata: " + path);
                images.put(name, createPlaceholderImage(color)); // Usare un'immagine di segnaposto
            }
        } catch (IOException e) {
            System.err.println("Errore nel caricamento della risorsa: " + path);
            images.put(name, createPlaceholderImage(color)); // Usare un'immagine di segnaposto
        }
    }

    public BufferedImage getImage(String name) {
        return images.getOrDefault(name, createPlaceholderImage(Color.GRAY)); // Ritorna un'immagine di segnaposto se non trovata
    }

    private BufferedImage createPlaceholderImage(Color color) {
        // Crea un'immagine con trasparenza (ARGB)
        BufferedImage placeholderImage = new BufferedImage(50,50, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = placeholderImage.createGraphics();

        // Imposta un colore di fondo, per esempio grigio chiaro
        g2d.setColor(color);
        g2d.fillRect(0, 0, 50,50);

        // Aggiungi un bordo e del testo per indicare che è un'immagine di segnaposto
        g2d.setColor(Color.BLACK);
        g2d.drawRect(0, 0, 50, 50); // -1 per non uscire dall'immagine
        // Libera le risorse del contesto grafico
        g2d.dispose();

        return placeholderImage;
    }
}
