package game;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Psyam15Main
{													//Ahoy, look at me!!
	final static int Width = 4;
	final static int Height = 4;
	final static int COLOURS = 8;
	final static int MAX_PER_COLOUR = 2;
	final static int BUT_SIZE = 100;
	
/*<----------------------------------IMPORTANT...maybe
 * Right, this is important. Don't mess with the above, the game will do strange things... but if you have to:
 * 1. 'MAX_PER_COLOUR' MUST be EVEN!! You won't be able to match up odd numbers...
 *	     Since the game rewards 2 points per match AND picks a winner when the sum of the scores equals COLOURS * MAX_PER_COLOUR, the game will just not pick a winner... 
 * 2. 'COLOURS' can be any number BUT "'COLOURS' multiplied by 'MAX_PER_COLOUR'" MUST BE equal to 'Width * Height'
 * 3. 'Width' and 'Height' can be anything BUT make sure to adjust the 'COLOURS' and 'MAX_PER_COLOUR' as needed...
 * 4. 'BUT_SIZE'... umm not really important since the window will adjust them on its own... just... leave it alone...    
 *
*/
	int scoreP1 = 0;
	int scoreP2 = 0;
	int clicks = 0;
	Boolean turnP1 = true;														//All these be here to keep track 'o thin's that change each turn.
	Color color1 = null;
	Color color2 = null;
	int but1 = -1;
	int but2 = -1;
	Color[] colorArray = new Color[COLOURS*MAX_PER_COLOUR];						//This be the array of all the colours that are used each game
	ColorButton[] buttonArray = new ColorButton[Width*Height];					//This be the array of all the butt-ons that are made... heh *butt*
	Random rand = new Random();
	
	ButtonItr a = new ButtonItr();												//Iterator thing
	
	
	JFrame guiFrame = new JFrame();
	JLabel label1, label2;
	JPanel panel1;
	public void createGUI()
	{
		guiFrame.setLayout(new BorderLayout(20,20));
		guiFrame.setVisible(true);
		guiFrame.setTitle("PGP-OO-CW4");										//"Who? TheRapist John? You looking for him?" - someone who didn't like John the therapist.
		
		label1 = new JLabel();
		label2 = new JLabel();
		panel1 = new JPanel();
		
		label1.setBackground(Color.BLACK);
		label1.setFont(new Font("serif",Font.BOLD,25));							//"To boldly go where no label has gone before" - Me in my head 
		label1.setForeground(Color.white);
		label1.setOpaque(true);
		
		panel1.setLayout(new GridLayout(Height, Width));

		
		label2.setBackground(Color.BLACK);
		label2.setFont(new Font("serif",Font.BOLD,25));
		label2.setForeground(Color.white);
		label2.setOpaque(true);
		
		guiFrame.add(label1, BorderLayout.NORTH);
		guiFrame.add(label2, BorderLayout.SOUTH);
		guiFrame.add(panel1, BorderLayout.CENTER);
		
		text();
		createColours();
		createColourButtons();

		guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		guiFrame.pack();
		guiFrame.repaint();
	}
	
	public void text()															//"Those that incorrectly use grammar will be killed." -seen on the side of a bus
	{
		if(turnP1)
		{
			label1.setText("Player 1 has "+ scoreP1 +" points. It is your turn!!");
			label1.setBackground(Color.GREEN);
			label1.setForeground(Color.BLACK);
			
			if(clicks == 0)
				label1.setText(label1.getText()+" Make your first choice!");
			else if(clicks == 1)
				label1.setText(label1.getText()+" Make your match!");
			else if(clicks == 2)
				label1.setText("Click a square to continue...");
			
			label2.setText("Player 2 has "+ scoreP2 +" points. Wait...");
			label2.setBackground(Color.RED);
			label2.setForeground(Color.DARK_GRAY);
		}
		else
		{
			label1.setText("Player 1 has "+ scoreP1 +" points. Wait...");
			label1.setBackground(Color.RED);
			label1.setForeground(Color.DARK_GRAY);
			label2.setText("Player 2 has "+ scoreP2 +" points. It is your turn...");
			label2.setBackground(Color.GREEN);
			label2.setForeground(Color.BLACK);
			
			if(clicks == 0)
				label2.setText(label2.getText()+" Make your first choice!");
			else if(clicks == 1)
				label2.setText(label2.getText()+" Make your match!");
			else if(clicks == 2)
				label2.setText("Click a square to continue...");
		}
	}
	
	public void buttonClicked(int iButton)
	{
		if(buttonArray[iButton].flipped == false && clicks < 2)
		{
			buttonArray[iButton].flip(true);	
			
			if(color1 == null)
			{
				color1 = buttonArray[iButton].butColor;
				but1 = iButton;
			}
			else
			{
				color2 = buttonArray[iButton].butColor;
				but2 = iButton;
			}
			
			clicks++;
		}
		else if(clicks >= 2)
		{
			if(color1 != null && color2 != null)
				match(color1, color2);
			
		}

		Iterator<ColorButton> iter = a.butitr();
		iter = a.butitr();
		int outcnt = 0;
		while(iter.hasNext())
		{
			ColorButton currBut = iter.next();
			
			if(currBut.out)
				outcnt++;
		}
		
		if(outcnt == Width*Height)
			win();
		
		label2.setText(label2.getText() + outcnt);
		text();
	}
	
	public void match(Color firstCol , Color secCol)
	{
		if(firstCol == secCol)
		{
			if(turnP1)
				scoreP1 = scoreP1 + 2;
			else
				scoreP2 = scoreP2 + 2;
			
			removeButton();
		}
		else
		{
			if(turnP1)
				turnP1 = false;
			else
				turnP1 = true;
			
			buttonArray[but1].flip(false);
			buttonArray[but2].flip(false);
		}
		clicks = 0;
		color1 = null;
		color2 = null;
	}
	
	public void removeButton()
	{
		buttonArray[but1].out = true;										//Take it out and flip it 
		buttonArray[but1].flip(true);										//flip to true so things
																			//This is the bit that also ensures a clicked square can't be clicked again
		buttonArray[but2].out = true;
		buttonArray[but2].flip(true);
		
	}
	
	public void win()
	{
		if( scoreP1 > scoreP2)
		{
			JOptionPane.showMessageDialog(guiFrame,"Player 1 wins!!!");
			//reset();
		}else if(scoreP1 < scoreP2)
		{
			JOptionPane.showMessageDialog(guiFrame,"Player 2 wins!!!");
			//reset();
		}
		reset();
	}
	
	public void reset()
	{
		createColours();																			//make new random colours
		scoreP1 = 0;
		scoreP2 = 0;
		text();
		for(int i = 0; i < Width*Height; i++)
			assignColour(i);																		//reassign new random colours
		
	}
	
	public void createColours()
	{
		for(int a = 0; a < COLOURS*MAX_PER_COLOUR; a++)													//clear the array... necessary... definitely not added to trick myself into thinking i'm doing work
			colorArray[a] = null;
		
		int a = 0;
		for(int j = 0; j < COLOURS; j++)
		{
			Color col = new Color(rand.nextInt(25)*10,rand.nextInt(25)*10,rand.nextInt(25)*10);			//It's random, I swear...
			
			int assignedCol =  0;
			while(assignedCol < MAX_PER_COLOUR)
			{
					colorArray[a] = col;																//put oddly similar but still totally random colours into the array
					assignedCol++;
					a++;
			}
		}

		Collections.shuffle(Arrays.asList(colorArray));											//shuffle the colours into random positions in the array
		
	}
	
	public void createColourButtons()
	{
		for(int i =0; i<Width*Height; i++)
		{
			buttonArray[i] = new ColorButton(i, BUT_SIZE, BUT_SIZE, this); 						//make a new button
			panel1.add(buttonArray[i]);
			
			assignColour(i);																	//then give it a colour
			
			a.add(buttonArray[i]);
		}
	}
	
	public void assignColour(int i)
	{
		buttonArray[i].butColor = null;											//need to set initial colour to 'null' for the way my code for ColorButton was written to work properly...
		buttonArray[i].setColor(colorArray[i]);									//assign the colours inorder.(they're already randomly shuffled)
		buttonArray[i].out = false;												//helps when called from reset... makes sure the buttons are still in play
		buttonArray[i].flip(false);												//"Grey, the colour of... grey things."
		guiFrame.repaint();
		buttonArray[i].repaint();
	}


	public static void main(String[] args) 
	{
		new Psyam15Main().createGUI();
	}
}



