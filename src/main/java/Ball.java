import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ball {
    private float x, y;
    private final int radius = 10; // Scegli un raggio appropriato per la pallina
    private float dx = 1f ; // Velocità orizzontale
    private float dy = -1f; // Velocità verticale (negativa per muoversi verso l'alto)
    private float maxSpeedChange = 0.7f; // Adjust this based on your game's requirements
    private boolean isAboutToExpire = false;


    // Metodo per impostare la posizione iniziale della pallina
    public void setInitialPosition(int paddleX, int paddleY, int paddleWidth) {
        // Posiziona la pallina appena sopra il centro del paddle
        this.x = paddleX + paddleWidth / 2;
        this.y = paddleY - radius - 30; // Sposta la pallina un po' sopra il paddle
    }

    // Metodo per disegnare la pallina

    public void move() {
        x += dx;
        y += dy;
    }

    public void reverseDirectionX(){
        dx = -dx;
    }
    public void reverseDirectionY(){
        dy = -dy;
    }
    public void reversePaddleCollision(float impactPoint) {
        reverseDirectionY();
        dx=dx + impactPoint * maxSpeedChange;
        // Clamp the X speed to avoid excessive values
        dx=(Math.min(Math.max(dx, -maxSpeedChange), maxSpeedChange));
    }

    public void increaseSpeed(float speedBoost) {
        dx*=speedBoost;
        dy*=speedBoost;
        maxSpeedChange*=speedBoost;
    }

    public void decreaseSpeed(float speedBoost) {
        dx/=speedBoost;
        dy/=speedBoost;
        maxSpeedChange/=speedBoost;
    }
}
