package package1;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class Floor
{
	//numero de l'étage
	private int number;
	//list des personne dans ce étage
	private ArrayList<People> Peoples;
	//l'etat de l'ascenseur dans l'étage
	private boolean AscenseurState;
	//we need an y for the drawing 
	private int Ay;
	
	public Floor(int Ay,int number)
	{
		this.Ay=Ay;
		this.number=number;
	}
	public Floor() 
	{
		
	}
	
	public void drawSpace(Graphics g) 
	{
		Graphics2D g2 = (Graphics2D) g ;
		g2.setStroke(new BasicStroke(3));
		

		Graphics2D g3 = (Graphics2D) g ;
		g3.setColor(new Color(0,66,112));
		for(int i = 0 ; i<500;i+=100) {
			g3.fillRect(320, i, 100, 100);
		}
		//400 300 200 100
	}
	public void drawFloor(Graphics g) 
	{
		Graphics2D g2 = (Graphics2D) g ;
		g2.setColor(new Color(0,66,112)); // white smock 245,245,245 
		
		for(int i=0,j=5;i<500 && j>0 ; i+=100,j--)
		{
			this.Ay =i;
			this.number = j;
			if(Ay<300)
			{
				g.drawLine(150,Ay,588,Ay);
				g.drawString(" Etage = "+number , 50, i+30);
			}
			else
			{
				g.drawLine(50,Ay,693,Ay);
				g.drawString(" Etage = "+number , 50, i+30);
			}
			
		}
		
//		g.drawLine();
		
	}
	public int getNumber() 
	{
		return this.number;
	}
	public void getNumberFromY(int Ay) 
	{
		switch (Ay) 
		{
		case 0:
			this.number =5;
			break;
		case 100:
			this.number =4;
			break;
		case 200:
			this.number =3;
			break;
		case 300:
			this.number =2;
			break;
		case 400:
			this.number=1;
			break;
		default:
			break;
		}
		
	}
	
	public void goTo(int number) 
	{
		// a switch  to go the floor  
		
	}
	//constractor
	//add people to the floor
	//draw the lines
}
