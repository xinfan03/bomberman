package uet.oop.bomberman;

import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Frame;
import uet.oop.bomberman.act.KeyBoard;
import uet.oop.bomberman.Sound.Audio;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;

/**
 * Tạo vòng lặp cho game, lưu trữ một vài tham số cấu hình toàn cục,
 * Gọi phương thức render(), update() cho tất cả các entity
 */
public class Game extends Canvas {

    public static final int TILES_SIZE = 16,
            WIDTH = TILES_SIZE * 31,
            HEIGHT = 13 * TILES_SIZE;

    public static int SCALE = 2;

    public static final String TITLE = "BombermanGame";

    private static final int BOMBRATE = 1;
    private static final int BOMBRADIUS = 1;
    private static final double BOMBERSPEED = 1.0;

    public static final int TIME = 200;
    public static final int POINTS = 0;

    protected static int SCREENDELAY = 3;

    protected static int bombRate = BOMBRATE;
    protected static int bombRadius = BOMBRADIUS;
    protected static double bomberSpeed = BOMBERSPEED;


    protected int _screenDelay = SCREENDELAY;

    private KeyBoard _input;
    private boolean _paused = true;

    //TODO: thêm âm thanh nền của Game
    private static Audio backgoundSound;
    public static Audio getBackgoundSound() {
        return backgoundSound;
    }

    //TODO: thêm static
    private static Board _board;
    private Screen screen;
    private Frame _frame;

    private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

    public Game(Frame frame) {
        _frame = frame;
        _frame.setTitle(TITLE);

        screen = new Screen(WIDTH, HEIGHT);
        _input = new KeyBoard();

        _board = new Board(this, _input, screen);
        addKeyListener(_input);
        backgoundSound = new Audio(new File("res/Sound/stage_start.wav"));
    }


    private void renderGame() {
        BufferStrategy bs = getBufferStrategy();
        if(bs == null) {
            createBufferStrategy(3);
            return;
        }

        screen.clear();

        _board.render(screen);

        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = screen._pixels[i];
        }

        Graphics g = bs.getDrawGraphics();

        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        _board.renderMessages(g);

        g.dispose();
        bs.show();
    }

    private void renderScreen() {
        BufferStrategy bs = getBufferStrategy();
        if(bs == null) {
            createBufferStrategy(3);
            return;
        }

        screen.clear();

        Graphics g = bs.getDrawGraphics();

        _board.drawScreen(g);

        g.dispose();
        bs.show();
    }

    private void update() {
        _input.update();
        _board.update();
    }

    public void start() {
        boolean _running = true;

        long  lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        final double ns = 1000000000.0 / 60.0; //nanosecond, 60 frames per second
        double delta = 0;
        int frames = 0;
        int updates = 0;
        requestFocus();

        System.out.println("START !");
        backgoundSound.play();

        while(_running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1) {
                update();
                updates++;
                delta--;
            }

            if(_paused) {
                if(_screenDelay <= 0) {
                    _board.setShow(-1);
                    _paused = false;
                }

                renderScreen();
            } else {
                renderGame();
            }


            frames++;
            if(System.currentTimeMillis() - timer > 1000) {
                _frame.setTime(_board.subtractTime());
                _frame.setPoints(_board.getPoints());
                timer += 1000;
                _frame.setTitle(TITLE + " | " + updates + " rate, " + frames + " fps");
                updates = 0;
                frames = 0;

                if(_board.getShow() == 2)
                    --_screenDelay;
            }
        }
    }

    public static double getBomberSpeed() {
        return bomberSpeed;
    }

    public static int getBombRate() {
        return bombRate;
    }

    public static int getBombRadius() {
        return bombRadius;
    }

    public static void addBomberSpeed(double i) {
        bomberSpeed += i;
    }

    public static void addBombRadius(int i) {
        bombRadius += i;
    }

    public static void addBombRate(int i) {
        bombRate += i;
    }

    public void resetScreenDelay() {
        _screenDelay = SCREENDELAY;
    }

    //TODO: Them static
    public static Board getBoard() {
        return _board;
    }

    public boolean isPaused() {
        return _paused;
    }

    public void pause() {
        _paused = true;
    }

}