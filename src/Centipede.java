
import processing.core.PApplet;


public class Centipede extends Sprite {

	private int lives = CENTIPEDE_LIVES;
	
	private Centipede parent;

	public Centipede(PApplet display, Engine subject, int x, int y, int dx, int dy) {
		super(display, subject, x, y, dx, dy);
		this.setImage(display.loadImage("src/images/centipede_body.png"));
		this.update();
	}

	public void update() {
		this.updatePosition();
		if (!this.isDestroyed()) {
			
			try {
				this.getDisplay().image(getImage(), getX(), getY());
				this.getDisplay().redraw();
			} catch (ClassCastException e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	public void updatePosition() { //should honestly move more similar to centipede
		//move in x direction back and forth until it comes across something 
        /**this.setY();
        this.setX(this.parent.getX() + (this.parent.getSpeedX()*(50)));
        if (this.getY() == BOARD_HEIGHT) {
            //this.destroy();
        	this.reverseSpeedY();
        }
        if (this.getY() <= BOARD_PADDING) {
        	this.reverseSpeedY();
        }
        if (this.getX() >= BOARD_WIDTH) {
            this.reverseSpeedX();
        }
        if (this.getX() <= BOARD_PADDING) {
        	this.reverseSpeedX();
        }**/
		
		this.setX(this.getX()+this.getSpeedX());
		this.setY(this.getY()+ this.getSpeedY());
    }

	
	public int getLives() {
		return this.lives;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}
	
	public void setParent(Centipede parent) {
		this.parent = parent;
	}
}