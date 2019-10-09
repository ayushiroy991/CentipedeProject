
import processing.core.PConstants;
import processing.core.PApplet;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class GamerController implements Commons {

	Engine engine;
	protected PApplet display;

	public int score = 0;

	public GamerController(PApplet display, Engine engine) {
		this.engine = engine;
		this.display = display;
	}

	public void handleEvent() {
		// engine.updateData();
	}

	public void run() {
		if (!engine.isGameRunning()) {
			display.textAlign(PConstants.CENTER);
			display.textSize(18);
			display.text("Click anywhere to start game", (int) (BOARD_WIDTH / 2),
					(int) (BOARD_HEIGHT / 2 + BOARD_PADDING));
		} else {
			engine.notifyAllSprites();
			this.fleetControl();
			this.collisionControl();
			this.centControl();
		}
	}

	public void fleetControl() {
		List<Mushroom> fleet = engine.getFleet();
		if (!fleet.isEmpty()) {
			Sprite mushroomCaptain = fleet.get(0);
			// System.out.println("IC: " + mushroomCaptain.getX() + " + " +
			// mushroomCaptain.getSpeedX());
			if ((mushroomCaptain.getSpeedX() > 0
					&& mushroomCaptain.getX() >= (BOARD_WIDTH - BOARD_PADDING - FLEET_WIDTH))
					|| (mushroomCaptain.getSpeedX() < 0 && mushroomCaptain.getX() <= (BOARD_PADDING))) {
				for (Mushroom mushroom : fleet) {
					mushroom.reverseSpeedX();
					mushroom.setY(mushroom.getY() + 0);
				}
			}
			int mushroomCount = 0;
			/**
			 * int spiderCount = 0;
			 * 
			 * for (Mushroom mushroom : fleet) { Spider spider = mushroom.getSpider(); if
			 * (!spider.isDestroyed()) { spiderCount++; } }
			 **/

			Random generator = new Random();

			boolean last_row;
			for (Mushroom mushroom : fleet) {
				/**
				 * last_row = true; Spider spider = mushroom.getSpider(); for (Mushroom
				 * mushroom_under : fleet){
				 * 
				 * if (mushroom_under.getY() > mushroom.getY() && mushroom_under.getX() ==
				 * mushroom.getX() && !mushroom_under.isDestroyed()) last_row = false;}
				 **/

				if (!mushroom.isDestroyed()) {
					// TO DO: BOMB = SPIDER?
					mushroomCount++;
					/**
					 * int shot = generator.nextInt(BOMB_CHANCE + 1); if (!mushroom.isDestroyed() &&
					 * spider.isDestroyed() && spiderCount < BOMB_COUNT && shot == BOMB_CHANCE &&
					 * last_row == true) { spider.setX(mushroom.getX() + (MUSHROOM_WIDTH / 2));
					 * spider.setY(mushroom.getY() + MUSHROOM_HEIGHT); spider.resurrect(); }
					 **/
				}
			}

			if (mushroomCount == 0) {
				this.endGame(1);
			} else {
				display.textAlign(PConstants.RIGHT);
				display.textSize(13);
				display.fill(Color.BLUE.getRGB());
				display.text("Score: " + (score), BOARD_WIDTH - BOARD_PADDING, GROUND + 20);
				display.fill(Color.WHITE.getRGB());
			}
		}
	}

	public void centControl() {
		List<List<Centipede>> centipedes = engine.getCentipedes();
		if (!centipedes.isEmpty()) {
			boolean cent = false;
			for (int i = 0; i < centipedes.size(); i++) {
				List<Centipede> centipede = centipedes.get(i);
				if (!centipede.isEmpty()) {
					cent = true;
					int dir = centipede.get(0).getSpeedX() < 0 ? 1 : -1;
					for (int k = centipede.size() - 1; k > 0; k--) {
						if (centipede.get(k - 1).getY() > centipede.get(k).getY()) {
							dir = 0;
						}
						int x = centipede.get(k - 1).getX() + (40 * dir);
						int y = centipede.get(k - 1).getY();
						centipede.get(k).setX(x);
						centipede.get(k).setY(y);
					}

					if (centipede.get(0).getY() >= GROUND) {
						// this.destroy();
						centipede.get(0).setY(GROUND);
					}
					
					if (centipede.get(0).getX() >= BOARD_WIDTH - 50) {
						centipede.get(0).setY(centipede.get(0).getY() + 30);
						centipede.get(0).reverseSpeedX();
					}
					if (centipede.get(0).getX() <= 0) {
						centipede.get(0).setY(centipede.get(0).getY() + 30);
						centipede.get(0).reverseSpeedX();
					}

					// check if it hits mushroom
					List<Mushroom> fleet = engine.getFleet();
					int cx = centipede.get(0).getX();
					int cy = centipede.get(0).getY();
					for (Mushroom mushroom : fleet) {
						int mx = mushroom.getX();
						int my = mushroom.getY();

						if (!mushroom.isDestroyed() && cx + MUSHROOM_WIDTH >= mx && cx <= mx + MUSHROOM_WIDTH
								&& cy >= my && cy <= my + MUSHROOM_HEIGHT) {
							centipede.get(0).setY(centipede.get(0).getY() + 30);
							centipede.get(0).reverseSpeedX();
						}

					}
				}
				
				

			}

		}
	}



	public void collisionControl() {
		// Load sprite lists
		List<Sprite> bullets = engine.getBullets();
		List<Mushroom> fleet = engine.getFleet();
		List<Spider> spiders = engine.getSpiders();
		Sprite player = engine.getPlayer();
		
		// Check spiders hit player
		if (!spiders.isEmpty()) {
			int sX = player.getX();
			int sY = player.getY();

			boolean hit = false;

			for (Spider spider : spiders) {
				int oX = spider.getX();
				int oY = spider.getY();

				if (!spider.isDestroyed() && oX + BOMB_WIDTH >= sX && oX <= sX + PLAYER_WIDTH && oY + BOMB_HEIGHT >= sY
						&& oY <= sY + PLAYER_HEIGHT) {
					spider.destroy();
					engine.setLives((engine.getLives() - 1));
					if (engine.getLives() <= 0) {
						this.endGame(3);
					}

					hit = true;

					for (Mushroom inva : fleet) {
						if ((inva.getLives() < MUSHROOM_LIVES) && (inva.getLives() != 0)) {
							inva.setLives(MUSHROOM_LIVES);
							score += 10; // For each mushroom hit but not destroyed, and restored when the
											// player dies: 10 points?
						}
					}
				}
			}
			if (hit) {
				engine.createNewSpider();
				engine.createNewSpider();
			}
		}

		// Check if centipede hits player
		List<List<Centipede>> cent = engine.getCentipedes();
		if (!cent.isEmpty()) {
			int sX = player.getX();
			int sY = player.getY();

			boolean hit = false;
			for (int i = 0; i < cent.size(); i++) {
				//for each centipede
				List<Centipede> centipede = cent.get(i);
				if (!centipede.isEmpty()) { 
					int cX = centipede.get(0).getX();
					int cY = centipede.get(0).getY();
					
					if (!centipede.get(0).isDestroyed() && cX + BOMB_WIDTH >= sX && cX <= sX + PLAYER_WIDTH && cY + BOMB_HEIGHT >= sY
							&& cY <= sY + PLAYER_HEIGHT) {
						engine.setLives((engine.getLives() - 1));
						if (engine.getLives() <= 0) {
							this.endGame(3);
						}

						for (Mushroom inva : fleet) {
							if ((inva.getLives() < MUSHROOM_LIVES) && (inva.getLives() != 0)) {
								inva.setLives(MUSHROOM_LIVES);
								score += 10; // For each mushroom hit but not destroyed, and restored when the
												// player dies: 10 points?
							}
						}
						
						centipede.get(0).destroy();
						int len = centipede.size()-0-1;
						int dir = centipede.get(0).getSpeedX() < 0? -1 : 1;
						engine.newCentipede(sX+(dir*50), sY, dir, len);
						while (centipede.size()-1 >= 0) {
							centipede.get(centipede.size()-1).destroy();
							centipede.remove(centipede.size()-1);
						}
						
						engine.createNewSpider();
					}
					
				}
			}
			
		}
		
		
		
		// Check bullets
		if (!bullets.isEmpty() && !fleet.isEmpty()) {
			for (Sprite bullet : bullets) {
				int bX = bullet.getX();
				int bY = bullet.getY();

				for (Spider spider : spiders) {
					int oX = spider.getX();
					int oY = spider.getY();

					if (!spider.isDestroyed() && !bullet.isDestroyed() && bX + BULLET_WIDTH >= oX
							&& bX <= oX + MUSHROOM_WIDTH && bY >= oY && bY <= oY + MUSHROOM_HEIGHT) {
						bullet.destroy();
						score += 100;
						spider.setLives((spider.getLives() - 1));
						if (spider.getLives() <= 0) {
							spider.destroy();
							score += 500;
						}
					}

					for (Mushroom mushroom : fleet) {
						int iX = mushroom.getX();
						int iY = mushroom.getY();

						if (!mushroom.isDestroyed() && !bullet.isDestroyed() && bX + BULLET_WIDTH >= iX
								&& bX <= iX + MUSHROOM_WIDTH && bY >= iY && bY <= iY + MUSHROOM_HEIGHT) {

							bullet.destroy();
							score += 1;
							mushroom.setLives((mushroom.getLives() - 1));
							if (mushroom.getLives() <= 0) {
								// spider.destroy();
								mushroom.destroy();
								score += 4;
							}
						}

					}
					// TODO: use this for centipede
					for (Mushroom mush : fleet) {
						if (!mush.isDestroyed() && mush.getY() + MUSHROOM_HEIGHT >= engine.getPlayer().getY()) {
							// this.endGame(2);
						}
					}
				}
				
				
				//check if bullet hit centipede segment
				List<List<Centipede>> centipedes = engine.getCentipedes();
				if (!centipedes.isEmpty()) {
					for (int i = 0; i < centipedes.size(); i++) {
						//for each centipede
						List<Centipede> centipede = centipedes.get(i);
						if (!centipede.isEmpty()) {
							int dir = centipede.get(0).getSpeedX() < 0? 1 : -1;
							for (int k = centipede.size()-1; k >= 0; k--) {
								int sX = centipede.get(k).getX(); //segment x
								int sY = centipede.get(k).getY(); //segment y
								int dx = centipede.get(k).getSpeedX();
										
								if (!centipede.get(k).isDestroyed() && !bullet.isDestroyed() && bX + BULLET_WIDTH +10 >= sX
										&& bX <= sX + MUSHROOM_WIDTH+10 && bY >= sY-10 && bY <= sY + MUSHROOM_HEIGHT +10) {

									bullet.destroy();
									score += 2;
									centipede.get(k).setLives((centipede.get(k).getLives() - 1));
									if (centipede.get(k).getLives() <= 0) {
										// spider.destroy();
										centipede.get(k).destroy();
										int len = centipede.size()-k-1;
										engine.newCentipede(sX-(50*dir), sY, dir, len);
										while (centipede.size()-1 >= k) {
											centipede.get(centipede.size()-1).destroy();
											centipede.remove(centipede.size()-1);
										}
										score += 3;
										
										if (k==0) {
											score +=(600-5);
										}
									}
								}
							}
						}
					}
				}
				
			}
			
			
		}
	}

	public void endGame(int status) {
		engine.endGame();
		engine.getGround().update();
		ArrayList<Mushroom> fleet = engine.getFleet();
		for (Mushroom mushroom : fleet) {
			// Spider spider = mushroom.getSpider();
			// spider.destroy();
			// spider.update();
			mushroom.destroy();
			mushroom.update();
		}
		if (status == 1) {
			// mushrooms destroyed.
			display.textAlign(PConstants.CENTER);
			display.textSize(24);
			display.text("All Mushrooms destroyed. You win!", (int) (BOARD_WIDTH / 2), (int) (BOARD_HEIGHT / 2));
		} else if (status == 2) {
			// Invasion.
			display.textAlign(PConstants.CENTER);
			display.textSize(24);
			display.text("Invasion. You lose!", (int) (BOARD_WIDTH / 2), (int) (BOARD_HEIGHT / 2));
		} else if (status == 3) {
			// Invasion.
			display.textAlign(PConstants.CENTER);
			display.textSize(24);
			display.text("Player destroyed. You lose!", (int) (BOARD_WIDTH / 2), (int) (BOARD_HEIGHT / 2));
		}
		//engine.getLogo().resurrect();
		//engine.getLogo().update();
		display.textSize(18);
		display.fill(Color.RED.getRGB());
		//display.text("Press any key to start game", (int) (BOARD_WIDTH / 2), (int) (BOARD_HEIGHT / 2 + BOARD_PADDING));
		display.fill(Color.WHITE.getRGB());
	}
}
