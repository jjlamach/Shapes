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
	
	List<ShapePopperComponent> components = new ArrayList<ShapePopperComponent>();
	ShapePopperComponent currComp = null; 

	boolean isDragged = false; 
	SquareComposer square; 
	SquareComposer square2; 

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
		ShapePopperComponent theComp = null;
		
		
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
		
		SquareComposer square = new SquareComposer(new Point2D(390, 120), 300, 300, Color.RED);
		square.getRectangle().setStrokeWidth(7);
		this.square = square;
		components.add(square);
		root.getChildren().add(square.getRectangle());
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
			xCircle circle = new xCircle(new Point2D((Math.random() * 289), Math.random() * 380));
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
		
			String eventName = mouseEvent.getEventType().getName();

			if (!isDragged) {
				currComp = getCurrentComponent(clickPoint);
			}

			switch (eventName) {
			case ("MOUSE_PRESSED"):
				currComp = getCurrentComponent(clickPoint);
				if (currComp != null && currComp instanceof xCircle) {
					components.add(currComp);
				}
				if (mouseEvent.isSecondaryButtonDown()) {
					createCircles();
				}
				break;

			
			case ("MOUSE_RELEASED"):
				if (currComp != null && currComp instanceof xCircle && square.containsCoordinates(clickPoint)) {
					for (ShapePopperComponent comp : components) {
						if(comp instanceof SquareComposer) {
							if(!((SquareComposer) square).allComponents.contains(currComp)) {
								((SquareComposer) square).addComponent(currComp);
							}
							break;
						}
					}
				}
				if (currComp != null && currComp instanceof xCircle && square2.containsCoordinates(clickPoint)) {
					for (ShapePopperComponent comp : components) {
						if (comp instanceof SquareComposer) {
							if (!((SquareComposer) square2).allComponents.contains(currComp)) {
								((SquareComposer) square2).addComponent(currComp);
							}
							break;
						}
					}
				}

				if (currComp != null && currComp instanceof xCircle && !square.containsCoordinates(clickPoint)) {
					square.allComponents.remove(currComp);
				}
				if (currComp != null && currComp instanceof xCircle && !square2.containsCoordinates(clickPoint)) {
					square2.allComponents.remove(currComp);
				}
					
				if (currComp != null && currComp instanceof xCircle && !square.containsCoordinates(clickPoint)) {
					((xCircle) currComp).getCircle().setFill(Color.BLACK);
				}
				if (currComp != null && currComp instanceof xCircle && !square2.containsCoordinates(clickPoint)) {
					((xCircle) currComp).getCircle().setFill(Color.BLACK);
				}

				if (currComp != null && currComp instanceof xCircle && square.containsCoordinates(clickPoint)) {

					((xCircle) currComp).getCircle().setFill(square.xRectangle.getStroke());
				}
				if (currComp != null && currComp instanceof xCircle && square2.containsCoordinates(clickPoint)) {
					((xCircle) currComp).getCircle().setFill(square2.xRectangle.getStroke());
				}

				break;

			case ("MOUSE_DRAGGED"):
				isDragged = true;
				if (currComp != null && oldPoint != null) {
					currComp.move(clickPoint.getX() - oldPoint.getX(), clickPoint.getY() - oldPoint.getY());
					if (currComp instanceof xCircle) {
						((xCircle) currComp).getCircle().toFront();
					}
				}
				break;
			}
			oldPoint = clickPoint;
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

	public static void main(String[] args) {
		launch(args);
	}
}
	
