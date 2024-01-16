import java.awt.*;

public class Ball {
    private int x, y;
    private final int radius = 10; // Scegli un raggio appropriato per la pallina
    private float dx = 1 ; // Velocità orizzontale
    private int dy = -1; // Velocità verticale (negativa per muoversi verso l'alto)


    // Costruttore
    public Ball() {
        // La posizione iniziale verrà impostata separatamente
    }

    // Metodo per impostare la posizione iniziale della pallina
    public void setInitialPosition(int paddleX, int paddleY, int paddleWidth, int paddleHeight) {
        // Posiziona la pallina appena sopra il centro del paddle
        this.x = paddleX + paddleWidth / 2;
        this.y = paddleY - radius - 30; // Sposta la pallina un po' sopra il paddle
    }

    // Metodo per disegnare la pallina
    public void draw(Graphics2D g) {
        g.setColor(Color.RED); // Scegli un colore per la pallina
        g.fillOval(x - radius, y - radius, radius * 2, radius * 2);
    }

    public void move() {
        x += dx;
        y += dy;
        // Gestisci le collisioni con le pareti
        if (x < 0 || x > /* Larghezza del pannello */ - radius * 2) {
            reverseDirectionX(); // Inverti la direzione orizzontale
        }
        if (y < 0) {
            reverseDirectionY(); // Inverti la direzione verticale
        }

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getRadius() {
        return radius;
    }

    public void reverseDirectionX(){
        dx = -dx;
    }
    public void reverseDirectionY(){
        dy = -dy;
    }
    // Aggiungi qui altri metodi, come per il movimento della pallina
}
