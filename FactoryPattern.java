package uos.Game;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class FactoryPattern implements FactoryIF{
	GraphicsContext gc;
	Pane pane;
	@SuppressWarnings("exports")
	@Override
	public GameObject createItem(String type, double x, double y,Image img,Pane pane,String itemName) {
		if(type.equals("solid"))
			return new Solid(x,y,gc,img,pane,itemName);// instantiate a solid
		else if(type.equals("liquid")) 
			return new Liquid(x,y,gc,img,pane,itemName);// instantiate a liquid
		else if(type.equals("gas"))
			return new Gas(x,y,gc,img,pane,itemName);// instantiate a gas
		
		return null;
	}

	@SuppressWarnings("exports")
	public FactoryPattern(GraphicsContext gc) {
		super();
		this.gc = gc;
	}
}
