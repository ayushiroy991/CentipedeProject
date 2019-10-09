

import processing.core.PApplet;


public class Player extends Sprite {
    
	private Engine engine;

	public Player(PApplet display, Engine subject, int x, int y, int dx, int dy) {
		super(display, subject, x, y, 0, 0);
		this.engine = subject;
		this.setImage(display.loadImage("src/images/ship.gif"));
		update();
	}

	public void update() {
		this.updatePosition();
		try {
            this.getDisplay().image(getImage(), getX(), getY());
            this.getDisplay().redraw();
			for (int l = 0; l < engine.getLives(); l++) {
                this.getDisplay().image(getImage(), BOARD_WIDTH - BOARD_PADDING - ((l + 1) * (20 + PLAYER_WIDTH)), BOARD_HEIGHT - BOARD_PADDING - PLAYER_HEIGHT);
			}
		} catch (ClassCastException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void updatePosition() {
		//made it be able to move right left off screen
		this.setX(getX() + this.getSpeedX());
		if (this.getX() >= BOARD_WIDTH) {
			this.setX(BOARD_WIDTH-PLAYER_WIDTH);
		}
		if (this.getX() <=0) {
			this.setX(0);
		}
		if (this.getY() <= GROUND) {
			this.setY(GROUND);
		}
	}
}