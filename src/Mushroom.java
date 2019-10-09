
import processing.core.PApplet;


public class Mushroom extends Sprite { //actuallly a mushroom

	private Spider spider;
	
	private int lives = MUSHROOM_LIVES;

	public Mushroom(PApplet display, Engine subject, int x, int y, int dx, int dy) {
		super(display, subject, x, y, dx, dy);
		this.setImage(display.loadImage("src/images/mushroom.png"));
		//this.spider = new Spider(display, subject, 0, 0,((int)Math.random()*(BOMB_SPEED)+1), ((int)Math.random()*(BOMB_SPEED)+1));
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

	public Spider getSpider() {
		return spider;
	}
	
	public int getLives() {
		return this.lives;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}
}