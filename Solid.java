package uos.Game;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class Solid extends GameObject {
	@SuppressWarnings("exports")
	public Solid(double x, double y, GraphicsContext gc,Image img,Pane pane,String itemName) {
		super(x, y, gc,img,pane,itemName);
	}	
	public void display() 
	{
		super.display();
	}
}
