import lombok.AllArgsConstructor;
import lombok.Data;

import java.awt.*;
import java.util.UUID;

@Data
@AllArgsConstructor
public class Brick implements Collidable{
    private final int x, y;
    private final int width,height;
    private boolean isVisible=true;
    private final boolean isDestructible;
    private int hitPoints; // Livelli di resistenza del mattone
    private UUID id;
    public Brick(int x,int y,int width, int height, boolean isDestructible, int hitpoints){
        id= UUID.randomUUID();
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
        this.isDestructible=isDestructible;
        this.hitPoints=hitpoints;
    }
    public void hit() {
        if (hitPoints == 0)
            isVisible = false;
        if (hitPoints > 0)
            hitPoints--;
    }
    public Color getColor() {
        return switch (hitPoints) {
            case 0 -> Color.GREEN; // Colore per mattoni che si rompono subito
            case 1 -> Color.ORANGE; // Colore per mattoni che richiedono 1 colpo
            case 2 -> Color.RED; // Colore per mattoni che richiedono 2 colpi
            default -> Color.gray; //colore per mattoni che non si distruggono
        };
    }
}
