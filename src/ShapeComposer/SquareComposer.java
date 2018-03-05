package ShapeComposer;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

/*
 * This is the Composite(the Square) where all the circles will land and change color from within.
 */
/**
 * 
 * @author Julio Lama
 *
 */
public class SquareComposer implements ShapePopperComponent {
	List<ShapePopperComponent> allComponents = new ArrayList<ShapePopperComponent>();
	Point2D coordinates; // location of the composite.
	int height;
	int width;
	Color color; // color of the rectangle.
	Rectangle xRectangle = new Rectangle(); // the "square"

	/*
	 * Constructs a rectangle that will contain circles.
	 */
	public SquareComposer(Point2D coordinates, int height, int width, Color color) {
		this.coordinates = coordinates;
		this.height = height;
		this.width = width;
		this.color = color;
		xRectangle.setHeight(height);
		xRectangle.setWidth(width);
		xRectangle.setX(coordinates.getX());
		xRectangle.setY(coordinates.getY());
		xRectangle.setFill(Color.WHITE);
		xRectangle.setStroke(Color.RED);
		xRectangle.setStroke(color);
		xRectangle.setStrokeWidth(5);

	}

	/*
	 * Adds a circle, a component.
	 */
	public void addComponent(ShapePopperComponent shape) {
		allComponents.add(shape);
	}

	/*
	 * Removes a component if it is in the list of components.
	 */
	public void removeComponent(ShapePopperComponent shape) {
		if (allComponents.contains(shape))
			allComponents.remove(shape);
	}

	/*
	 * Checks if the shape is in the composite.
	 */
	public boolean containsCoordinates(Point2D coordinates) {
		boolean inRectangle = (coordinates.getX() >= xRectangle.getX()
				&& coordinates.getX() <= xRectangle.getX() + xRectangle.getWidth()
				&& coordinates.getY() >= xRectangle.getY()
				&& coordinates.getY() <= xRectangle.getY() + xRectangle.getHeight());
		return inRectangle;
	}

	/*
	 * Makes all the components inside the composite to move.
	 */
	public void move(double deltaX, double deltaY) {
		xRectangle.setX(xRectangle.getX() + deltaX);
		xRectangle.setY(xRectangle.getY() + deltaY);
		for (ShapePopperComponent component : allComponents) {
			component.move(deltaX, deltaY);
		}
		xRectangle.toBack();
	}

	/*
	 * Sets the location of the composite.
	 */
	public void setLocation(Point2D coordinates) {
		xRectangle.setX(coordinates.getX());
		xRectangle.setY(coordinates.getY());
	}

	/*
	 * Returns the current location of the composite.
	 */
	public Point2D getLocation() {
		return coordinates;
	}

	/*
	 * Returns the composite.
	 */
	public Rectangle getRectangle() {
		return xRectangle;
	}

	/*
	 * Returns the Paint color of the square.
	 */
	public Paint getColor() {
		return xRectangle.getFill();
	}

	/*
	 * Reset the list of the components that are in the composite.
	 * We instantiate a new ArrayList of ShapePopperComponent.
	 */
	public void resetComposite() {
		allComponents = new ArrayList<ShapePopperComponent>();
	}
}
