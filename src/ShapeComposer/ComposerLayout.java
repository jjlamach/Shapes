package ShapeComposer;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * @author Julio Lama
 *
 */
public class ComposerLayout extends Application {
	Point2D clickPoint;
	Point2D oldPoint = null;
	// a List of type ShapePopperComponent.
	List<ShapePopperComponent> components = new ArrayList<ShapePopperComponent>();
	ShapePopperComponent currComp = null; // the current component being dragged.

	boolean isDragged = false; // forcing to enter a condition.
	SquareComposer square; // first square. The composite.
	SquareComposer square2; // second square. The composite.

	Scene myScene;
	AnchorPane root;

	@Override
	public void start(Stage composerStage) throws Exception {
		root = new AnchorPane();
		myScene = new Scene(root, 800, 750);

		setSquare();
		setSquareTwo();
		instructionLabel();

		/* Mouse events. */
		myScene.setOnMouseClicked(mouseHandler);
		myScene.setOnMouseDragged(mouseHandler);
		myScene.setOnMouseEntered(mouseHandler);
		myScene.setOnMouseExited(mouseHandler);
		myScene.setOnMouseMoved(mouseHandler);
		myScene.setOnMousePressed(mouseHandler);
		myScene.setOnMouseReleased(mouseHandler);

		composerStage.setScene(myScene);
		composerStage.setTitle("Shape Popper");
		composerStage.show();
	}

	/*
	 * Gets the current component being dragged by the mouse.
	 */
	public ShapePopperComponent getCurrentComponent(Point2D clickPoint) {
		ShapePopperComponent theComp = null;	// we want to store the component here
		/* We iterate over the list of components.*/
		for (ShapePopperComponent component : components) {
			if (component.containsCoordinates(clickPoint)) {
				theComp = component;
			}
		}
		return theComp;
	}

	/*
	 * Constructs a square.
	 */
	private void setSquare() {
		// i played here with x,y coordinates.
		SquareComposer square = new SquareComposer(new Point2D(390, 120), 300, 300, Color.RED);
		square.getRectangle().setStrokeWidth(7);	// how big the stroke for painting will be.
		this.square = square;
		components.add(square); // we add this to the list of components of type ShapePopperComponent.
		root.getChildren().add(square.getRectangle());	// .getRectangle because the root needs to know about what node is it.
	}

	/*
	 * Constructs a second square.
	 */
	private void setSquareTwo() {
		SquareComposer square = new SquareComposer(new Point2D(70, 410), 300, 300, Color.BLUE);
		square.getRectangle().setStrokeWidth(7);
		this.square2 = square;
		components.add(square);
		root.getChildren().add(square.getRectangle());
	}

	/*
	 * Constructs 2 circles with the right click of the mouse.
	 */
	private void createCircles() {
		for (int i = 0; i < 2; i++) {
			// here i also played a little bit with the x,y coordinates to fit the Scene.
			xCircle circle = new xCircle(new Point2D((Math.random() * 289), Math.random() * 380));
			/* We add the new circles to the List of all SquareComposer types. */
			components.add(circle);
			root.getChildren().add(circle.getCircle());
		}
	}

	/*
	 * Mouse handler events.
	 */
	EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {

		@Override
		public void handle(MouseEvent mouseEvent) {
			clickPoint = new Point2D(mouseEvent.getX(), mouseEvent.getY());
			/* We need to get the eventName for the switch statement. */
			String eventName = mouseEvent.getEventType().getName();

			/* We force this condition and we know have a variable that is holding the component
			 * that the mouse is clicking on.
			 */
			if (!isDragged) {
				currComp = getCurrentComponent(clickPoint);
			}

			switch (eventName) {
			case ("MOUSE_PRESSED"):
				/* Place on the variable "currComp" what the mouse is pressing. */
				currComp = getCurrentComponent(clickPoint);
				/* If it is a circle then we add it to the list of components. */
				if (currComp != null && currComp instanceof xCircle) {
					components.add(currComp);
				}
				/* If right click is clicked then create circles. */
				if (mouseEvent.isSecondaryButtonDown()) {
					createCircles();
				}
				break;

			
			case ("MOUSE_RELEASED"):
				/* If what the mouse is dragging is on the Square and is a circle */
				if (currComp != null && currComp instanceof xCircle && square.containsCoordinates(clickPoint)) {
					for (ShapePopperComponent comp : components) {
						if(comp instanceof SquareComposer) {
							// if it does not contain the circle, add it.
							if(!((SquareComposer) square).allComponents.contains(currComp)) {
								((SquareComposer) square).addComponent(currComp);
							}
							break;
						}
					}
				}
				/* Case for the second square. */
				if (currComp != null && currComp instanceof xCircle && square2.containsCoordinates(clickPoint)) {
					for (ShapePopperComponent comp : components) {
						if (comp instanceof SquareComposer) {
							// if it does not contain the circle, add it.
							if (!((SquareComposer) square2).allComponents.contains(currComp)) {
								((SquareComposer) square2).addComponent(currComp);
							}
							break;
						}
					}
				}

				/*
				 * If the square does not contain a circle anymore then it should remove it so
				 * that it does not move along with it.
				 */
				if (currComp != null && currComp instanceof xCircle && !square.containsCoordinates(clickPoint)) {
					square.allComponents.remove(currComp);
				}
				if (currComp != null && currComp instanceof xCircle && !square2.containsCoordinates(clickPoint)) {
					square2.allComponents.remove(currComp);
				}

				/*
				 * If the current component is not in the square, then it should set back to
				 * black the default color of the circle.
				 */
				if (currComp != null && currComp instanceof xCircle && !square.containsCoordinates(clickPoint)) {
					((xCircle) currComp).getCircle().setFill(Color.BLACK);
				}
				if (currComp != null && currComp instanceof xCircle && !square2.containsCoordinates(clickPoint)) {
					((xCircle) currComp).getCircle().setFill(Color.BLACK);
				}

				/*
				 * If the current component is on the square then it should set its color to the
				 * color of the stroke of the square.
				 */
				if (currComp != null && currComp instanceof xCircle && square.containsCoordinates(clickPoint)) {

					((xCircle) currComp).getCircle().setFill(square.xRectangle.getStroke());
				}
				if (currComp != null && currComp instanceof xCircle && square2.containsCoordinates(clickPoint)) {
					((xCircle) currComp).getCircle().setFill(square2.xRectangle.getStroke());
				}

				break;

			case ("MOUSE_DRAGGED"):
				isDragged = true; // since we are dragging something we set this to true.
				if (currComp != null && oldPoint != null) {
					currComp.move(clickPoint.getX() - oldPoint.getX(), clickPoint.getY() - oldPoint.getY());
					if (currComp instanceof xCircle) {
						((xCircle) currComp).getCircle().toFront();
					}
				}
				break;
			}
			oldPoint = clickPoint;	// we update the old location of the mouse with the new one.
		}
	};

	/* Instruction label. */
	private void instructionLabel() {
		Label instruction = new Label();
		instruction.setText("Right click to create circles.\nDrag them to the squares to change their colors.");
		instruction.setLayoutX(400);
		instruction.setLayoutY(450);
		instruction.toBack();
		root.getChildren().add(instruction);
	}

	// main
	public static void main(String[] args) {
		launch(args);
	}
}
	
