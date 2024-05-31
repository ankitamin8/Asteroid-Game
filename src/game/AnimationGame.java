
package game;

/**
 * Represents an animation game that extends the Game class. This class controls
 * the main components of the game such as the spaceship, asteroids, and
 * rectangles. It serves as a control center for the game and performs the
 * execution of the game.
 * 
 * @author Ankit Amin, FNU Mahek
 */

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

class AnimationGame extends Game {
	static int counter = 0;
	private Spaceship spaceship;
	private ArrayList<Asteroid> asteroids;
	private ArrayList<Rectangle> rectangles;
	private GameSettings gameSetting;
	private int currentRectangles = 0;
	private Random random;

	/**
	 * It creates a Game using the Game class constructor.
	 * 
	 * Additionally, it creates a Key listener, sets focusable to true, and
	 * requests focus. It also creates a spaceship which is one of the main
	 * components of the Game.
	 * 
	 * Further, it performs any required initialization.
	 * 
	 */
	public AnimationGame() {
		super("AnimationGame", 800, 600);
		addKeyListener(new GameAdapter());
		this.setFocusable(true);
		this.requestFocus();

		Point[] spaceshipShape = { new Point(0, 0), // Top point
				new Point(25, 25), // Bottom right point
				new Point(-25, 25), // Bottom left point
		};

		spaceship = new Spaceship(spaceshipShape, new Point(10, 10), 90);

		asteroids = new ArrayList<>();
		rectangles = new ArrayList<>();
		gameSetting = new GameSettings(1, 15);
		random = new Random();
	}

	/**
	 * Main method for animation game which executes all the code and creates a
	 * game.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		AnimationGame a = new AnimationGame();
		a.repaint();
	}

	/**
	 * checks if the asteroid hits the spaceship. If it does, the game is over.
	 * 
	 * @return boolean
	 */
	public boolean checkGameOver() {
		for (Asteroid asteroid : asteroids) {
			Point[] spaceshipPoints = spaceship.getPoints();
			for (Point point : spaceshipPoints) {
				if (asteroid.contains(point)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 
	 * checks if spaceship collides with any element.
	 * 
	 * @param spaceship
	 * @param element
	 * @return boolean
	 */
	private boolean checkCollision(Spaceship spaceship, Element element) {
		Point[] spaceshipPoints = spaceship.getPoints();
		Point[] elementPoints = element.getPoints();

		for (Point elementPoint : elementPoints) {
			for (Point spaceshipPoint : spaceshipPoints) {
				if (spaceship.contains(elementPoint)
						|| element.contains(spaceshipPoint)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Paints the elements and executes the game. If the game is over, it
	 * displays the game over screen. It also updates the game elements'
	 * positions and handles collisions.
	 * 
	 * @param brush The graphics context to paint the elements.
	 */
	public void paint(Graphics brush) {
		// Check if the game is over
		if (checkGameOver()) {
			// Draw the game over screen
			brush.setColor(Color.BLACK);
			brush.fillRect(0, 0, width, height);
			brush.setColor(Color.WHITE);
			brush.drawString("Game Over", width / 2 - 50, height / 2);
			brush.drawString("Level: " + gameSetting.getLevel(), width / 2 - 50,
					height / 2 + 20);
			brush.drawString("You survived for " + counter / 10 + " seconds",
					width / 2 - 50, height / 2 + 40);
			return;
		}

		// Clear the screen
		brush.setColor(Color.BLACK);
		brush.fillRect(0, 0, width, height);

		// Increment the counter
		counter++;

		// Draw game information
		brush.setColor(Color.white);
		brush.drawString("Seconds survided: " + counter / 10, 10, 10);
		brush.drawString("Level: " + gameSetting.getLevel(), 10, 25);

		// Paint and move the spaceship
		spaceship.paint(brush);
		spaceship.move(spaceship.position);

		// Check collisions with rectangles
		for (Rectangle rectangle : rectangles) {
			if (checkCollision(spaceship, rectangle)) {
				Runnable collisionHandler = () -> spaceship.decreaseSpeed();
				collisionHandler.run();
				break; // Only reduce speed once per frame
			}
		}

		// Add new asteroids based on frequency
		if (counter % getAsteroidFrequency() == 0) {
			for (int i = 0; i < 3; i++) {

				// Create new asteroids and add them to the list
				Point[] asteroidShape = { new Point(-10, -8), // Point 1
						new Point(-5, -15), // Point 2
						new Point(5, -15), // Point 3
						new Point(10, -8), // Point 4
						new Point(15, 0), // Point 5
						new Point(10, 8), // Point 6
						new Point(5, 15), // Point 7
						new Point(-5, 15), // Point 8
						new Point(-10, 8), // Point 9
						new Point(-15, 0) // Point 10
				};

				double y = random.nextInt(600); // Random y within the screen
												// height
				Asteroid asteroid = new Asteroid(asteroidShape,
						new Point(850, y), 0);

				asteroids.add(asteroid);
			}
		}

		// Move asteroids based on level
		if (counter % 400 / gameSetting.getLevel() == 0) {
			for (int i = 0; i < 3; i++) {
				Point[] asteroidShape = { new Point(-10, -8), // Point 1
						new Point(-5, -15), // Point 2
						new Point(5, -15), // Point 3
						new Point(10, -8), // Point 4
						new Point(15, 0), // Point 5
						new Point(10, 8), // Point 6
						new Point(5, 15), // Point 7
						new Point(-5, 15), // Point 8
						new Point(-10, 8), // Point 9
						new Point(-15, 0) // Point 10
				};

				double x = random.nextInt(800); // Random x within the screen
				// width
				Point position = new Point(x, 550);
				Asteroid asteroid = new Asteroid(asteroidShape, position, 0) {
					@Override
					public void move(Point position) {
						position.setY((int) position.getY() - 4);
					}
				};
				asteroids.add(asteroid);
			}
		}

		// Paint and move existing asteroids
		for (Asteroid asteroid : asteroids) {
			asteroid.paint(brush);
			asteroid.move(asteroid.position);
		}

		// Increase the game level every 400 seconds
		if (counter % 400 == 0) {
			gameSetting.setLevel(gameSetting.getLevel() + 1);
		}

		// Add new rectangles every 400 seconds
		if (counter % 400 == 0) {
			for (int i = 0; i < 2; i++) {
				Point[] rectangleShape = { new Point(25, 25), // New point 1
						new Point(75, 25), // New point 2
						new Point(75, 75), // New point 3
						new Point(25, 75) // New point 4
				};

				if (currentRectangles < gameSetting.getRectangleCount()) {

					double x = random.nextInt(750); // Random x within the
													// screen width
					double y = random.nextInt(550); // Random y within the
													// screen height

					Rectangle rectangle = new Rectangle(rectangleShape,
							new Point(x, y), 0);
					rectangles.add(rectangle);
					currentRectangles++;
				}
			}
		}

		// Paint and move existing rectangles
		brush.setColor(Color.RED);
		for (Rectangle rectangle : rectangles) {
			rectangle.paint(brush);
		}
	}

	/**
	 * Retrieves the frequency at which asteroids are produced based on the
	 * current game level. Higher levels result in faster asteroid production.
	 * 
	 * @return int
	 */

	private int getAsteroidFrequency() {
		int level = gameSetting.getLevel();
		switch (level) {
		case 1:
			return 250;
		case 2:
			return 200;
		case 3:
			return 200;
		case 4:
			return 150;
		case 5:
			return 100;
		case 6:
			return 50;
		default:
			return 275; // Default production frequency
		}
	}

	/**
	 * An adapter class for handling keyboard input. It prevents input if the
	 * game is over.
	 */
	private class GameAdapter extends KeyAdapter {

		@Override
		public void keyReleased(KeyEvent e) {
			if (!checkGameOver()) {
				spaceship.keyReleased(e);
			}
		}

		@Override
		public void keyPressed(KeyEvent e) {
			if (!checkGameOver()) {
				spaceship.keyPressed(e);
			}
		}
	}

	/**
	 * Represents game settings such as level and rectangle count within an
	 * inner GameSettings class.
	 */
	private class GameSettings {
		private int level;
		private int rectangleCount;

		/**
		 * Constructs a GameSettings object with the specified level and
		 * rectangle count.
		 * 
		 * @param level          The level of the game.
		 * @param rectangleCount The number of rectangles in the game.
		 */
		public GameSettings(int level, int rectangleCount) {
			this.level = level;
			this.rectangleCount = rectangleCount;
		}

		/**
		 * Gets the current level of the game.
		 * 
		 * @return The level of the game.
		 */
		public int getLevel() {
			return level;
		}

		/**
		 * Sets the level of the game.
		 * 
		 * @param level The level to set.
		 */
		public void setLevel(int level) {
			this.level = level;
		}

		/**
		 * Gets the number of rectangles in the game.
		 * 
		 * @return The number of rectangles.
		 */
		public int getRectangleCount() {
			return rectangleCount;
		}

	}

}