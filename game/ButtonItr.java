package game;

import java.util.ArrayList;
import java.util.Iterator;

public class ButtonItr extends ArrayList<ColorButton>{
	public ArrayList<ColorButton> a = this;
	
	public Iterator<ColorButton> butitr(){
		return new ButtonIterator();
	}
	
	private class ButtonIterator implements Iterator<ColorButton>{

		int index;
		@Override
		public boolean hasNext() {
			if(a.size() > index)
				return true;
			
			return false;
		}

		@Override
		public ColorButton next() {
			if(this.hasNext())
			{
				
				return (ColorButton) a.get(index++);
			}
			
			return null;
		}
		
	}
}
