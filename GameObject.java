package uos.Game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class GameObject {
	protected Image img;
	protected double x, y;
	protected GraphicsContext gc;
	protected Pane pane;
	protected String itemName;
	protected ImageView imageView;
	protected Label label;
	protected double width=100, height=100;
	
//	Superclass of solid,liquid and gas
	@SuppressWarnings("exports")
	public GameObject(double y, double x, GraphicsContext gc,Image img,Pane pane,String itemName)
	{
	super();
	this.gc=gc;
	this.x=x;
	this.y=y;
	this.img=img;
	this.pane=pane;
	this.itemName=itemName;
	}

	public void display() 
	{   // Create an imageview and add it to the pane 
		imageView =  new ImageView(img);
		imageView.setFitHeight(100);
		imageView.setFitWidth(100);
		imageView.setX(x);
		imageView.setY(y);
		pane.getChildren().add(imageView);
//		Label 
		label = new Label(itemName);
		label.setGraphic(imageView);
		label.setLayoutX(x);
		label.setLayoutY(y);
		label.setFont(Font.font("Arial",18));
		label.setTextFill(Color.WHITE);
		pane.getChildren().add(label);
//		updateRectangle();
	}
	
}