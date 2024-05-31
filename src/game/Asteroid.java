package game;

/**
 * Represents an asteroid element in the game.
 */
public class Asteroid extends Element {

	/**
	 * Constructs an asteroid with the specified shape, position, and rotation.
	 * Calls Element class constructor to create asteroid object.
	 * 
	 * @param inShape    The shape of the spaceship.
	 * @param inPosition The position of the spaceship.
	 * @param inRotation The rotation of the spaceship.
	 */
	public Asteroid(Point[] inShape, Point inPosition, double inRotation) {
		super(inShape, inPosition, inRotation);

	}

	/**
	 * Moves the asteroid to the left by a fixed distance. Overrides the super
	 * class move method.
	 * 
	 * @param position The current position of the asteroid.
	 */
	@Override
	public void move(Point position) {
		position.setX((int) position.getX() - 4);
	}

}
