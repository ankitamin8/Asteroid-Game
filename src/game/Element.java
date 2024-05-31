package game;

import java.awt.Graphics;

/**
 * Abstract class representing an element in the game, such as a spaceship,
 * asteroid, and rectangle.
 */
public abstract class Element extends Polygon {

	/**
	 * Constructs an Element with the specified shape, position, and rotation.
	 * Calls Polygon class constructor to create spaceship object.
	 * 
	 * @param inShape    The shape of the element.
	 * @param inPosition The position of the element.
	 * @param inRotation The rotation of the element.
	 */
	public Element(Point[] inShape, Point inPosition, double inRotation) {
		super(inShape, inPosition, inRotation);
	}

	/**
	 * Paints the element on the graphics context.
	 * 
	 * @param brush The graphics context to paint on.
	 */
	public void paint(Graphics brush) {
		int[] xPoints = new int[getPoints().length];
		int[] yPoints = new int[getPoints().length];

		// Convert Point objects to arrays of x and y coordinates
		for (int i = 0; i < getPoints().length; i++) {
			xPoints[i] = (int) getPoints()[i].getX();
			yPoints[i] = (int) getPoints()[i].getY();
		}

		brush.fillPolygon(xPoints, yPoints, getPoints().length);
	}

	/**
	 * Moves the element to the specified position.
	 * 
	 * @param position.
	 */
	public abstract void move(Point position);
}
