import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

class BackgroundPanel extends JPanel {
    private final BufferedImage background;

    public BackgroundPanel(ResourceManager resourceManager) {
        background=resourceManager.getImage("Background");
        setLayout(new GridBagLayout());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(),this);
        // Configura il font e il colore per la scritta
        g.setFont(new Font("Serif", Font.BOLD, 48)); // Modifica il font e la dimensione come desiderato
        Graphics2D g2d = (Graphics2D) g.create();
        String text = "The Breakout Game";

        // Calcola la posizione della scritta per centrarla nel panel
        FontMetrics fm = g2d.getFontMetrics();
        int x = (getWidth() - fm.stringWidth(text)) / 2;
        int y = ((getHeight() - fm.getHeight()) / 6) + fm.getAscent();

        // Configura l'effetto bordo
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setColor(Color.BLACK); // Colore del bordo
        g2d.drawString(text, x + 2, y + 2); // Leggermente spostata per creare l'effetto bordo

        g2d.setColor(Color.ORANGE); // Colore dell'oro per la scritta
        g2d.drawString(text, x, y); // Disegna la scritta

        g2d.dispose(); // Rilascia la copia di Graphics2D
    }

}
