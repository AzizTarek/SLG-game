package uos.Game;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class Gas extends GameObject {
	@SuppressWarnings("exports")
	public Gas(double x, double y, GraphicsContext gc,Image img,Pane pane,String itemName) {
		super(x, y, gc,img,pane,itemName);
	}
	
	public void display() 
	{
		super.display();
	}
}
