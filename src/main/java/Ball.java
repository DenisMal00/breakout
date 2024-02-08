import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ball {
    private float x, y;
    private final float radius = GameConstants.BALL_RADIUS;
    private float dx = GameConstants.INITIAL_BALL_DX;
    private float dy = GameConstants.INITIAL_BALL_DY;
    private float maxSpeedChange = GameConstants.INITIAL_BALL_MAX_SPEED_CHANGE;
    private boolean isAboutToExpire = false;
    public void setInitialPosition(int paddleX, int paddleY, int paddleWidth) {
        // Posiziona la pallina appena sopra il centro del paddle
        this.x = paddleX + (float) paddleWidth / 2;
        this.y = paddleY - radius - 30; // Sposta la pallina un po' sopra il paddle
    }

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
