package uos.Game;

import java.util.ArrayList;

public class CommandPattern {
	private ArrayList<CommandIF> queue = new ArrayList<CommandIF>();
	int count=0;
	
	public void addCommand(CommandIF c)
	{
		queue.add(c);
		c._do();
		count++;
	}

	public void redo()
	{
		if(count<queue.size())
			count++;
		queue.get(count-1)._do();
	}
	
	public void undo()
	{
		if(count>0)
			count--;
		queue.get(count)._undo();
	}
}