import java.awt.*;

public class Ball {
    private float x, y;
    private final int radius = 10; // Scegli un raggio appropriato per la pallina
    private float dx = 0.4f ; // Velocità orizzontale
    private float dy = -0.4f; // Velocità verticale (negativa per muoversi verso l'alto)
    private static final float MAX_X_SPEED_CHANGE = 0.1f; // Adjust this based on your game's requirements



    // Costruttore
    public Ball() {
        // La posizione iniziale verrà impostata separatamente
    }

    // Metodo per impostare la posizione iniziale della pallina
    public void setInitialPosition(int paddleX, int paddleY, int paddleWidth) {
        // Posiziona la pallina appena sopra il centro del paddle
        this.x = paddleX + paddleWidth / 2;
        this.y = paddleY - radius - 30; // Sposta la pallina un po' sopra il paddle
    }

    // Metodo per disegnare la pallina
    public void draw(Graphics2D g) {
        /*g.setColor(Color.RED); // Scegli un colore per la pallina
        g.fillOval((int) (x - radius), (int) (y - radius), radius * 2, radius * 2);
        // Disegna il bordo
        g.setColor(Color.BLACK); // Colore del bordo
        g.drawOval((int) (x - radius), (int) (y - radius), radius * 2, radius * 2);*/
        int drawX = Math.round(x - radius);
        int drawY = Math.round(y - radius);
        int diameter = radius * 2;

        g.setColor(Color.RED);
        g.fillOval(drawX, drawY, diameter, diameter);

        g.setColor(Color.BLACK);
        g.drawOval(drawX, drawY, diameter, diameter);
    }

    public boolean move(int panelWidth, int panelHeight) {
        x += dx;
        y += dy;
        // Gestisci le collisioni con le pareti
        if (x < 0 || x > panelWidth - radius * 2) {
            reverseDirectionX(); // Inverti la direzione orizzontale
        }
        if (y < 0) {
            reverseDirectionY(); // Inverti la direzione verticale
        }
        else if(y > panelHeight){
            return true;
        }
        return false;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getRadius() {
        return radius;
    }

    public void reverseDirectionX(){
        dx = -dx;
    }
    public void reverseDirectionY(){
        dy = -dy;
    }

    public void reversePaddleCollision(float impactPoint) {
        reverseDirectionY();
        dx=dx + impactPoint * MAX_X_SPEED_CHANGE;
        // Clamp the X speed to avoid excessive values
        dx=(Math.min(Math.max(dx, -MAX_X_SPEED_CHANGE), MAX_X_SPEED_CHANGE));
    }

    public void reverseBrickCollision() {
        reverseDirectionY();
    }

    public float getDy() {
        return dy;
    }

    public void setX(float x) {
        this.x=x;
    }

    public void setY(float y) {
        this.y=y;
    }
    // Aggiungi qui altri metodi, come per il movimento della pallina
}
