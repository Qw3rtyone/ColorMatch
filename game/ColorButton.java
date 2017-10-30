package game;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class ColorButton extends JButton
{
	Psyam15Main mainObj;
	int butnum;
	Color butColor;
	ColorButton button;
	Boolean flipped;
	Boolean out;

	ColorButton(int butNum, int height, int width, Psyam15Main mainClassObject)
	{
		butnum = butNum;
		mainObj = mainClassObject;
		setMinimumSize(new Dimension(width, height));
		setPreferredSize(new Dimension(width, height));
		butColor = null;
		this.addActionListener(new click());
		
		button = this;
		
		flipped = false;
		out = false;
	}
	
	public void setColor(Color color)
	{
		if(butColor == null)
			butColor = color;
		
		this.setBackground(color);
		this.setForeground(color);
	}
	
	public void drawButton()
	{
		if(out)
		{
			setColor(Color.BLACK);
		}
		else
		{
			if(flipped)
				setColor(butColor);
			else
				setColor(Color.GRAY);
		}
	}
	
	void flip(boolean flip)
	{
		if(flip)
			flipped = true;
		else
			flipped = false;
		
		this.drawButton();
	}
	
	class click implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent arg0) 
		{
			mainObj.buttonClicked(butnum);
		}
		
	}
		
	public static void main(String[] args) 
	{

	}

}
