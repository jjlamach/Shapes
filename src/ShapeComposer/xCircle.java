package ShapeComposer;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

/**
 * @author Julio Lama
 */
/*
 * A component for the composite.
 */
public class xCircle implements ShapePopperComponent {
	Circle circle;
	Point2D coordinates;

	public xCircle(Point2D coordinates) {
		circle = new Circle();
		this.coordinates = coordinates;
		circle.setCenterX(coordinates.getX());
		circle.setCenterY(coordinates.getY());
		circle.setRadius(30.0f);

	}

	/*
	 * Checks if the circle contains what the mouse contains.
	 */
	public boolean containsCoordinates(Point2D coordinates) {
		return circle.contains(coordinates);
	}

	/*
	 * Sets the color of the circle.
	 */
	public void setFill(Color color) {
		circle.setFill(color);
	}

	/*
	 * Moves the circle.
	 */
	public void move(double deltaX, double deltaY) {
		circle.setCenterX(circle.getCenterX() + deltaX);
		circle.setCenterY(circle.getCenterY() + deltaY);
		circle.toFront();	// after moving they move to the front.
	}

	/*
	 * Sets the location of the circle.
	 */
	public void setLocation(Point2D coordinates) {
		circle.setCenterX(coordinates.getX());
		circle.setCenterY(coordinates.getY());

	}

	/*
	 * Returns the location of the circle.
	 */
	public Point2D getLocation() {
		return coordinates;
	}

	/*
	 * Returns the circle.
	 */
	public Circle getCircle() {
		return circle;
	}

	/*
	 * Returns the color of the circle.
	 */
	public Paint getColor() {
		return circle.getFill();
	}

}
