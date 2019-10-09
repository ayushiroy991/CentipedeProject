
import processing.core.PApplet;

public class Spider extends Sprite { ///SPIDERS
	
	private int lives = SPIDER_LIVES;

	public Spider(PApplet display, Engine subject, int x, int y, int dx, int dy) {
		super(display, subject, x, y, dx, dy);
        this.setImage(display.loadImage("src/images/spider.png"));
		this.update();
		//this.destroy();
	}

	public void update() {
	    if (!this.isDestroyed()) {
            updatePosition();
            try {                
                this.getDisplay().image(getImage(), getX(), getY());
                this.getDisplay().redraw();
            } catch (ClassCastException e) {
                System.out.println(e.getMessage());
            }
         
        }
	}

	@Override
    public void updatePosition() { //should honestly move more similar to centipede
		//move in x direction back and forth until it comes across something 
        this.setY(this.getY() + this.getSpeedY());
        this.setX(this.getX() + this.getSpeedX());
        if (this.getY() >= BOARD_HEIGHT) {
            //this.destroy();
        	this.reverseSpeedY();
        }
        if (this.getY() <= GROUND-150) {
        	this.setY(GROUND-150);
        	this.reverseSpeedY();
        }
        if (this.getX() >= BOARD_WIDTH) {
            this.reverseSpeedX();
        }
        if (this.getX() <= BOARD_PADDING) {
        	this.reverseSpeedX();
        }
        
        //this.changeSpeedY(((int)Math.random()*(1))-1);
        //this.changeSpeedX(((int)Math.random()*(1))-1);
    }
	
	public int getLives() {
		return this.lives;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}

    @Override
    public void resurrect() {
        this.setDestroyed(false);
    }
}