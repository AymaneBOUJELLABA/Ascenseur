package package1;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javax.imageio.ImageIO;

public class People
{
	//image graphic
	private static Image icon;
	//position du personne
	private int Ax,Ay;
	//étage originale 
	//I don't think we need it to be floor just a simple int 
	private Floor CurrentF;
	//destination
	//I don't think we need it to be floor just a simple int 
	private Floor DestinationF;
	//si la personne est dans l'ascenseur
	//we could use enum for that 
	private boolean state;
	//we need to know if the person is up, down, right or left
	private int cFloor;

	public People(int Ax,int Ay)
	{
		this.Ax=Ax;
		this.Ay=Ay;
	}
	public People(int Ax,int Ay,Floor CurrentF)
	{
		this.Ax=Ax;
		this.Ay=Ay;
		this.CurrentF = CurrentF;
	}
	public void drawPerson(Graphics g)
	{
		Graphics2D g2D = (Graphics2D) g;
		File pathToFile = new File("student.png");
        try
        {
        	g2D.setColor(new Color(237,245,247)); // white smock 245,245,245 
    		g2D.setStroke(new BasicStroke(3));
    		
			Image img = ImageIO.read(pathToFile);
			g.drawImage(img, Ax+50, Ay,60,50, null);
			
			g.drawString(" DestFloor : " + DestinationF.getNumber(),Ax, Ay+10);
			
			System.out.println("DestF : " +DestinationF.getNumber());
		} catch (IOException e)
        {
			e.printStackTrace();
		}	
	}
	public void drawKiller(Graphics g)
	{
		Graphics2D g2D = (Graphics2D) g;
		File pathToFile = new File("knife.png");
        try
        {
        	g2D.setColor(new Color(237,245,247)); // white smock 245,245,245 
    		g2D.setStroke(new BasicStroke(3));
			Image img = ImageIO.read(pathToFile);
			g.drawImage(img, Ax, Ay,60,50, null);
			g.drawString(" DestFloor : " + DestinationF.getNumber(),Ax, Ay+10);
			
			System.out.println("DestF : " +DestinationF.getNumber());
        } catch (IOException e)
        {
			e.printStackTrace();
		}

		
	}
	
	public static Image getIcon()
	{
		return icon;
	}
	public static void setIcon(Image icon)
	{
		People.icon = icon;
	}
	public Floor getCurrentF()
	{
		return CurrentF;
	}
	public void setCurrentF(Floor currentF)
	{
		CurrentF = currentF;
	}
	public Floor getDestinationF()
	{
		return DestinationF;
	}
	public void setDestinationF(Floor destinationF)
	{
		DestinationF = destinationF;
	}
	public boolean isState()
	{
		return state;
	}
	public void setState(boolean state)
	{
		this.state = state;
	}
	public int getAx()
	{
		return Ax;
	}
	public void setAx(int ax)
	{
		Ax = ax;
	}
	public int getAy()
	{
		return Ay;
	}
	public void setAy(int ay)
	{
		Ay = ay;
	}
	@Override
	public String toString()
	{
		return "People [Ax=" + Ax + ", Ay=" + Ay + ", CurrentF=" + CurrentF + ", DestinationF=" + DestinationF + "]";
	}
	
	
	//we need constrator to add the values
	//we need a function to draw in the panel 
	//we need a function to know the stat of the people (up down right left)
	
	

}
