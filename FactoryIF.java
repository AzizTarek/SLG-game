package uos.Game;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
/*Create item when the type of matter,image,x and y are given*/
public interface FactoryIF {
	@SuppressWarnings("exports")
	GameObject createItem(String type, double x, double y,Image img,Pane pane,String itemName);
}
