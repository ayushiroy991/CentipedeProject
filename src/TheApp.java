
import processing.core.PApplet;
import java.awt.*;

//mport processing.sound.*;

import sun.audio.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;

public class TheApp extends PApplet implements Commons
{
    /**
     * GamerController contains the game logic.
     */
	private GamerController gamerController;

    /**
     * Engine is mainly the Sprite collection.
     */
	private Engine engine;
	
	//private SoundFile laser;

	@Override
	public void settings() {
	    size(BOARD_WIDTH, BOARD_HEIGHT);
	}

	@Override
	public void setup() {
	    // Set the frame rate.
		frameRate(30);

		// Initialize the Engine and GamerController.
		engine = new Engine(this);
		gamerController = new GamerController( this, engine);

		// Initialize the game start screen.
		engine.setGround(new Ground(this, engine, 0, 0, 0, 0));
		engine.setLogo(new Logo(this, engine, 0, 0, 0, 0));
		
		//initialize sound
		
	}

	@Override
	public void draw() { // draw() loops forever, until stopped
        gamerController.run();
	}
	
	
	
    /**
     * This function listens to the user input.
     */
	//@Override
	/**public void keyPressed() {
	    // If any key is pressed and the game isn't running, start it.
	    if (!engine.isGameRunning()) {
	        engine.startGame();
        }
        // If the game is running, check for any pressed control key.
        else {
            // Key A moves the spaceship to the left.
            if (keyCode == 'A') {
            	engine.getSpaceship().changeSpeedX(-10);
            }
            // Key D moves the spaceship to the right.
            else if (keyCode == 'D') {
                engine.getSpaceship().changeSpeedX(10);
            }
            // Key S makes the spaeship fire a bullet.
            else if (keyCode == 'S') {
            	// There can only be that many bullets visible at the same time.
                if (engine.activeBullets() < BULLET_COUNT)
            	{
            		Sprite bullet = new Bullet(this, engine, engine.getSpaceship().getX() + 10, engine.getSpaceship().getY() - 10, 0, -5);
                    engine.addBullet(bullet);                      
            	}            	         		
            }  
        }   
	}**/

    /**
     * This function listens to the key release action and stops the spaceship's movement.
     */
	//@Override
	/**public void keyReleased() {
	    if (engine.isGameRunning()) {
            if (keyCode == 'A') {
                engine.getSpaceship().resetSpeedX();
            }
            else if (keyCode == 'D') {
                engine.getSpaceship().resetSpeedX();
            }
        }
	}**/
	
	/**
	 * user mouse input - ayushi 
	 */
	// private int mX, mY;
	@Override
	public void mouseMoved() {
		if (engine.isGameRunning()) {
			engine.getPlayer().setX((int) mouseX);
			engine.getPlayer().setY((int) mouseY);
		} 
	}


	@Override
	public void mouseClicked() {
		// TODO Auto-generated method stub
		if (!engine.isGameRunning()) {
	        engine.startGame();
        }
		
	}

	@Override
	public void mousePressed() {
		// TODO Auto-generated method stub
		if (!engine.isGameRunning()) {
	        engine.startGame();
        }
		else if (mouseButton == LEFT) {
			// There can only be that many bullets visible at the same time.
			if (engine.activeBullets() < BULLET_COUNT) {
				Sprite bullet = new Bullet(this, engine, engine.getPlayer().getX() + 10,
						engine.getPlayer().getY() - 10, 0, -5);
				engine.addBullet(bullet);
				/**InputStream test = new FileInputStream("C:\\Music1.wmv");
	            AudioStream BGM = new AudioStream(test);
	            AudioPlayer.player.start(BGM);**/
			
				
				//laser.play();
			}
		}

	}
	
	
  
}
