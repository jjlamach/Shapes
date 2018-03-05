package ShapeComposer;

import javafx.geometry.Point2D;

/**
 * 
 * @author Julio Lama
 *
 */
/*
 * The interface for the composites and component.
 */
public interface ShapePopperComponent {
	public boolean containsCoordinates(Point2D coordinates); // checks if some other component has this component.

	public void move(double deltaX, double deltaY); // moves the component

	public void setLocation(Point2D coordinates); // sets the location for the component

	public Point2D getLocation(); // gets location of the component
}
