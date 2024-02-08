import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class Brick implements Collidable{
    private final int x, y;
    private final int width,height;
    private final boolean isDestructible;
    private int hitPoints;
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
    public boolean isVisible() {
        return !isDestructible || hitPoints >= 0;
    }
    public boolean hit() {
        if (isDestructible && hitPoints >= 0) {
            hitPoints--;
            return hitPoints == -1;
        }
        return false;
    }

}
