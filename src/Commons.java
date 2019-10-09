

public interface Commons {
	//say each row is 30 (invaders height)
	//player field is 3 rows
	//centipede gets 13+1;
    public static final int BOARD_WIDTH = 850;      // Width of the game board.
    public static final int BOARD_HEIGHT = 540; //450/30 = 9     // Height of the game board.
    public static final int BOARD_PADDING = 15;     // Inner padding of the game board.
    public static final int GROUND = 435;   //3 rows  + padding      // Position of the ground.
    public static final int BOMB_GROUND = 435;      // Threshold for the bombs' impact, may vary from GROUND for visual reasons.
    public static final int FLEET_WIDTH = 360;      // Total width of the invaders' fleet.
    public static final int MUSHROOMS = 10;          // Total count of invaders.
    public static final int MUSHROOM_HEIGHT = 29;    // Height of a single invader.
    public static final int MUSHROOM_WIDTH = 43;     // Width of a single invader.
    public static final int MUSHROOM_SPEED = 15;    // Speed of the down movement when edge of game board is reached by fleet.
    public static final int BOMB_CHANCE = 99;       // Chance for an invader to drop a bomb, 1 in X.
    public static final int BOMB_COUNT = 2;         // Maximum number of bombs visible at a time.
    public static final int BOMB_HEIGHT = 5;        // Height of a bomb.
    public static final int BOMB_WIDTH = 5;         // Width of a bomb.
    public static final int BOMB_SPEED = 5;         // max speed of a bomb.
    public static final int PLAYER_WIDTH = 33;      // Width of the player
    public static final int PLAYER_HEIGHT = 23;     // Height of the player
    public static final int PLAYER_LIVES = 3;       // Initial number of lives for the player.
    public static final int BULLET_HEIGHT = 23;     // Height of a bullet.
    public static final int BULLET_WIDTH = 12;      // Width of a bullet.
    public static final int BULLET_COUNT = 50;       // Maximum number of bullets visible at a time.
    public static final int MUSHROOM_LIVES = 3;     // Max lives for each mushroom
    public static final int SPIDER_LIVES = 2;     // Max lives for each spider
    public static final int CENTIPEDE_LENGTH = 10; //length of centipede
    public static final int CENTIPEDE_SPEED = 5; //speed of centipede
    public static final int CENTIPEDE_LIVES = 2; //each segment has 2 lives
    
}
