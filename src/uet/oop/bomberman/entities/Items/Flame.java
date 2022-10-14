package uet.oop.bomberman.entities.Items;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

import static uet.oop.bomberman.BombermanGame.fixedEntities;
import static uet.oop.bomberman.BombermanGame.idObjects;

public class Flame extends Items {

    public Flame() {

    }
    public Flame(int x, int y, Image img) {
        super(x, y, img);
    }

    public Flame(boolean receive) {
        super(receive);
    }

    @Override
    public void update() {
        for (Entity entity : fixedEntities)
            if (entity instanceof Speed && !this.receive)
                if (idObjects[getX() * 16][entity.getY() * 16] == 2)
                    entity.setImg(Sprite.powerup_flames.getFxImage());

        if (!this.receive)
            if (getX() == x && getY() == y) {
                this.setImg(Sprite.grass.getFxImage());
                this.receive = true;
            }
    }
}
