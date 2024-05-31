package game;

import java.awt.event.KeyEvent;

/**
 * Represents the spaceship element in the game.
 */
public class Spaceship extends Element {
	private int changeInY;
	private int changeInX;

	/**
	 * Constructs a spaceship with the specified shape, position, and rotation.
	 * Calls Element class constructor to create spaceship object.
	 * 
	 * @param inShape    The shape of the spaceship.
	 * @param inPosition The position of the spaceship.
	 * @param inRotation The rotation of the spaceship.
	 */
	public Spaceship(Point[] inShape, Point inPosition, double inRotation) {
		super(inShape, inPosition, inRotation);
	}

	/**
	 * Handles key pressed events to control the spaceship's movement.
	 * 
	 * @param e The KeyEvent object representing the key pressed event.
	 */
	public void keyPressed(KeyEvent e) {

		int key = e.getKeyCode();

		if (key == KeyEvent.VK_LEFT) {
			changeInX = -2;
		}

		if (key == KeyEvent.VK_RIGHT) {
			changeInX = 2;
		}

		if (key == KeyEvent.VK_UP) {
			changeInY = -2;
			this.rotate(-15);
		}

		if (key == KeyEvent.VK_DOWN) {
			changeInY = 2;
			this.rotate(15);
		}
	}

	/**
	 * Handles key released events to stop the spaceship's movement.
	 * 
	 * @param e The KeyEvent object representing the key released event.
	 */
	public void keyReleased(KeyEvent e) {

		int key = e.getKeyCode();

		if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT) {
			changeInX = 0;
		}

		if (key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN) {
			changeInY = 0;
		}

	}

	/**
	 * Moves the spaceship to the specified position based on user input.
	 * Overrides the super class move method.
	 * 
	 * @param position The new position of the spaceship.
	 */
	@Override
	public void move(Point position) {
		// Calculate the new X and Y positions
		int newX = (int) position.getX() + changeInX;
		int newY = (int) position.getY() + changeInY;

		// Define the boundaries considering the spaceship's shape size
		int minX = 0; // The left most boundary
		int maxX = 795 - 25; // The right most boundary minus spaceship width
		int minY = 0; // The top most boundary
		int maxY = 550 - 25; // The bottom most boundary minus spaceship height

		// Check if the new position is within the screen boundaries
		if (newX >= minX && newX <= maxX && newY >= minY && newY <= maxY) {
			position.setX(newX);
			position.setY(newY);
		}
	}

	/**
	 * Decreases the speed of the spaceship.
	 */
	public void decreaseSpeed() {
		changeInY /= 2;
		changeInX /= 2;
	}
}
