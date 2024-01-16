import javax.swing.JFrame;
import java.awt.EventQueue;

public class Main {
    public static void main(String[] args) {
        // Ensure that all UI updates are done on the Event Dispatch Thread
        EventQueue.invokeLater(Main::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        // Create the main window (JFrame)
        JFrame frame = new JFrame("Breakout Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the game panel and add it to the frame
        GamePanel gamePanel = new GamePanel();
        frame.add(gamePanel);

        // Pack the frame to fit the preferred size and layouts of its subcomponents
        frame.pack();

        // Optionally, you can set the frame size manually
        frame.setSize(800, 600);

        // Center the frame on the screen
        frame.setLocationRelativeTo(null);

        // Make the frame visible
        frame.setVisible(true);
    }
}
