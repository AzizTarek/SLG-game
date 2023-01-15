package uos.Game;

import javafx.scene.control.Label;

public class DragCommand implements CommandIF{
	double x,y;
	Label label;
	GameObject obj;
	public DragCommand(double x,double y,GameObject obj) {
		super();
		this.x =x;
		this.y =y;
		this.label = obj.label;
		this.obj = obj;
	}
	@Override
	/*do command to drag */
	public void _do() {
		// TODO Auto-generated method stub
		obj.label.setLayoutX(x-20);
		obj.label.setLayoutY(y-40);
	}

	@Override
   /*undo command to undo drag*/
	public void _undo() {
		// TODO Auto-generated method stubs
		obj.label.setLayoutX(x-20);
		obj.label.setLayoutY(y-40);
	}
 
}
