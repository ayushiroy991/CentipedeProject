

import java.util.ArrayList;
import java.util.List;


import processing.core.PApplet;

public class Engine implements Subject, Commons {

	protected PApplet display;
	private static List<Sprite> sprites = new ArrayList<Sprite>();
	private Sprite ground;
	private Sprite logo;
	private Sprite player;
	private ArrayList<Sprite> bullets = new ArrayList<>();
	private ArrayList<Spider> spiders = new ArrayList<>();
	private ArrayList<Mushroom> fleet = new ArrayList<>();
	ArrayList<List<Centipede>> centipedes = new ArrayList<>();
	private boolean ingame = false;
	private int lives = PLAYER_LIVES;

	public Engine(PApplet display) {
		this.display = display;
	}
	
	public void updateData() {
		notifyAllSprites();
	}
	
	@Override
	public void attach(Sprite sprite) {
		sprites.add(sprite);	
	}
	
	@Override
	public void notifyAllSprites() {
		for (Sprite sprite : sprites) {			
			sprite.update();
		}
	}

	public List<Sprite> getSprites() {
		return sprites;
	}

	public Sprite getGround() {
		return ground;
	}

	public void setGround(Sprite ground) {
		this.ground = ground;
	}

	public void setLogo(Sprite logo) {
		this.logo = logo;
	}

	public Sprite getLogo() {
	    return this.logo;
	}

	public Sprite getPlayer() {
		return player;
	}

	public void setPlayer(Sprite player) {
		this.player = player;
	}

	public ArrayList<Sprite> getBullets() {
		return bullets;
	}

	public void setBullets(ArrayList<Sprite> bullets) {
		this.bullets = bullets;
	}
	public void addBullet(Sprite bullet) {
		this.bullets.add(bullet);
	}
	
	public int activeBullets()
	{
		int active_bullets = 0;
		for (Sprite bullet : bullets)
			if (!bullet.isDestroyed())
				active_bullets++;
		return active_bullets;		
	}
		
	public ArrayList<Spider> getSpiders() {
		return spiders;
	}

	public void setSpiders(ArrayList<Spider> spiders) {
		this.spiders = spiders;
	}

	public void addSpider(Spider spider) {
		this.spiders.add(spider);
	}
	
	public ArrayList<Mushroom> getFleet() {
		return fleet;
	}

	public void setFleet(ArrayList<Mushroom> fleet) {
		this.fleet = fleet;
	}

	public void addMushroom(Mushroom mushroom) {
		this.fleet.add(mushroom);
	}
	
	public void createNewSpider() {
		//add in new spider when player dies\
		int a = (int)(Math.random()*(BOARD_WIDTH/50)+1);
    	int b = (int)(Math.random()*(GROUND/30)+1);
    	int x = BOARD_PADDING + (a*50);
        int y = BOARD_PADDING + (b*30);
        int dx = ((int)Math.random()*(BOMB_SPEED))-BOMB_SPEED;
        int dy = ((int)Math.random()*(BOMB_SPEED))-BOMB_SPEED;
		Spider newSpider = new Spider(this.display, this, x, y, dx, dy);
        this.addSpider(newSpider);
	}
	
	public void addCentipede(ArrayList<Centipede> centipede) {
		this.centipedes.add(centipede);
	}
	
	public ArrayList<List<Centipede>> getCentipedes() {
		return centipedes;
	}
	
	public void newCentipede(int x, int y, int dx, int len){
		if (len == 0) {
			return;
		}
		ArrayList<Centipede> centipede = new ArrayList<>();
        
        Centipede head = new Centipede(this.display, this, x, y,(dx*CENTIPEDE_SPEED), 0);
        head.setImage(display.loadImage("src/images/centipede_head.png"));
        centipede.add(head);
        
        for (int i=0; i<len; i++) {
        	Centipede next = new Centipede(this.display, this, head.getX(), head.getY(),0, 0);
            next.setParent(head);
            centipede.add(next);
        }
        
        addCentipede(centipede);
	}

    /**
     * Start the actual.
     */
	public void startGame() {
		this.ingame = true;
        this.lives = PLAYER_LIVES;
        this.logo.destroy();
        if (this.player == null) {
            setPlayer(new Player(this.display, this, BOARD_PADDING, BOARD_HEIGHT - PLAYER_HEIGHT, 0, 0));

            /**for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 6; j++) {
                    int x = BOARD_PADDING + (j * 60);
                    int y = BOARD_PADDING + (i * 40);
                    Mushroom mushroom = new Mushroom(this.display, this, x, y, 2, 0);
                    addMushroom(mushroom);
                }
            }**/
            
            //trying to randomly draw mushrooms
            int[] last = new int[5];
            for (int a : last) {
            	last[a] = 0;
            }
            for (int i=1; i<=13; i++) { //13 ROWS
            	int n = (int)(Math.random()*(5));//max 5 mushrooms in each row
            	for (int j=0; j<n; j++) {
            		int a = (int)(Math.random()*((BOARD_WIDTH-BOARD_PADDING)/50)-1);
            		for (int k=0; k<last.length; k++) {
            			while (a == last[k]+1 || a == last[k]-1) {
            				a = (int)(Math.random()*((BOARD_WIDTH-BOARD_PADDING)/50)-1);
            			}
            		}
            		last[j] = a;
            		int x = 50 + (a*50);
            		int y = (i*30);
            		Mushroom mushroom = new Mushroom(this.display, this, x, y, 0, 0);
                    addMushroom(mushroom);
            	}
            }
            
            //add in spiders
            
            int n = (int)(Math.random()*(1))+1;//max 3 spiders can start
            for (int i=0; i<1; i++) {
            	this.createNewSpider();    	
            }
            
            
            //make initial centipede

        	ArrayList<Centipede> centipede = new ArrayList<>();
            
            int x = BOARD_WIDTH-50;
            int y = 0;
            Centipede head = new Centipede(this.display, this, x, y,-CENTIPEDE_SPEED, 0);
            head.setImage(display.loadImage("src/images/centipede_head.png"));
            centipede.add(head);
            
            for (int i=0; i<CENTIPEDE_LENGTH; i++) {
            	Centipede next = new Centipede(this.display, this, head.getX(), head.getY(),0, 0);
                next.setParent(head);
                centipede.add(next);
            }
            
            addCentipede(centipede);
            
            /**for (int n=0; n<MUSHROOMS; n++) {
            	int a = (int)(Math.random()*(BOARD_WIDTH/50)+1);
            	int b = (int)(Math.random()*(GROUND/30)-1);
            	int x = BOARD_PADDING + (a*50);
                int y = BOARD_PADDING + (b*30);
                Mushroom mushroom = new Mushroom(this.display, this, x, y, 0, 0);
                addMushroom(mushroom);
            } **/
            
        }
        else {
            for (Mushroom mushroom : getFleet()) {
                mushroom.resurrect();
            }
        }
	}

	public void endGame() {
        this.ingame = false;
	}

	public boolean isGameRunning() {
		return this.ingame;
	}

	public int getLives() {
		return this.lives;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}
}
