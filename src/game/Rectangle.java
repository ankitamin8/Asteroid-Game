package game;

/**
 * Represents an Rectangle element in the game.
 */
public class Rectangle extends Element {

	/**
	 * Constructs a rectangle with the specified shape, position, and rotation.
	 * Calls Element class constructor to create rectangle object.
	 * 
	 * @param inShape    The shape of the spaceship.
	 * @param inPosition The position of the spaceship.
	 * @param inRotation The rotation of the spaceship.
	 */
	public Rectangle(Point[] inShape, Point inPosition, double inRotation) {
		super(inShape, inPosition, inRotation);
	}

	/**
	 * Moves the rectangle. This method is not implemented for rectangles, as
	 * they are fixed in the game and do not move.
	 * 
	 * @param position The position of the rectangle (not used).
	 */
	@Override
	public void move(Point position) {

	}

}
