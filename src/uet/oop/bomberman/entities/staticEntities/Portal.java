package uet.oop.bomberman.entities.staticEntities;

import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.dynamicEntities.Bomber;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.Sound.Audio;

import java.io.File;


public class Portal extends Tile {
    private Audio portalSound;


    public Portal(int x, int y, Sprite sprite) {
        super(x, y, sprite);

        portalSound = new Audio(new File("res/Sound/PortalSound.wav"));
    }

    @Override
    public boolean collide(Entity e) {
        // TODO: xử lý khi Bomber đi vào
        if(e instanceof Bomber) {

            if(!Game.getBoard().detectNoEnemies()) //Đi vào khi tiêu diệt hết Enemy
                return false;

            //if(Game.getBoard().detectNoEnemies() == true) //Đi vào thoải mái, không cần quan tâm hết hay chưa
            //return true;

            if(e.getXTile() == getX() && e.getYTile() == getY()) {
                if(Game.getBoard().detectNoEnemies()) {
                    portalSound.play();
                    Game.getBoard().nextLevel();
                }
            }

            return true;
        }
        return false;
    }

}
