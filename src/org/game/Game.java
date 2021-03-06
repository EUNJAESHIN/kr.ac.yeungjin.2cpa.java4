package org.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import org.game.object.PlayerObject;
import org.game.map.Map;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Game extends CanvasView implements MouseMotionListener, KeyListener {

    public static final boolean DEBUG = true;
              
    private boolean isGameOver = false;  

    public PlayerObject player;  
    public Map map;

    public Game() { 
        canvas.addMouseMotionListener(this);
        canvas.addKeyListener(this);
        
        map = new Map();
        player = new PlayerObject(map);
        
        map.setPlayer(player);
    }
    
    @Override
    protected void draw(Graphics2D g2d) {  
        super.draw(g2d);
        
        if (isGameOver) {
            // TODO 게임오버 처리
            String s = "게임오버";
            
            Font f = new Font("굴림", Font.PLAIN, 70);
            FontMetrics fm = g2d.getFontMetrics(f);
            
            int x = (canvas.getWidth() - fm.stringWidth(s)) / 2;
            int y = ((canvas.getHeight() - fm.getHeight()) / 2) + fm.getAscent();
            
            g2d.setFont(f); 
            g2d.setColor(Color.RED);
            g2d.drawString(s, x, y);
            
            /*
            try {
                Image img = ImageIO.read(new File("res/ghost2.jpg"));
                
                g2d.drawImage(img, 0, 0, canvas.getWidth(), canvas.getHeight(), null);
            }
            catch(IOException e) {
                e.printStackTrace();
            }*/
        } 
        else {
            map.draw(this, g2d);
            player.draw(this, g2d);
        }
    }
    
    public void setGameOver() {
        isGameOver = true;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) 
        {
            case KeyEvent.VK_W:
                player.getVelocity().setY(-4);
                break;
            case KeyEvent.VK_S:
                player.getVelocity().setY(4);
                break;
            case KeyEvent.VK_A:
                player.getVelocity().setX(-4);
                break;
            case KeyEvent.VK_D:
                player.getVelocity().setX(4);
                break;
            case KeyEvent.VK_F:
                player.toggleFlash();
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) 
        {
            case KeyEvent.VK_W:
            case KeyEvent.VK_S:
                player.getVelocity().setY(0);
                break;
            case KeyEvent.VK_A:
            case KeyEvent.VK_D:
                player.getVelocity().setX(0);
                break;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseMoved(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        player.getDirection().setX(e.getX());
        player.getDirection().setY(e.getY());
    }
}  