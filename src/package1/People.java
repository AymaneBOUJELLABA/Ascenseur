package package1;

import java.awt.Graphics;
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
	public People(Floor CurrentF)
	{
		this.CurrentF = CurrentF;
	}
	public void drawPerson(Graphics g)
	{
		File pathToFile = new File("C:\\Users\\HP i5\\Downloads\\student.png");
        try {
			Image img = ImageIO.read(pathToFile);
			g.drawImage(img, Ax, Ay,60,50, null);
			
		} catch (IOException e) {
			e.printStackTrace();
		}

		
	}
	public void drawKiller(Graphics g)
	{
		File pathToFile = new File("C:\\Users\\HP i5\\Downloads\\knife.png");
        try {
			Image img = ImageIO.read(pathToFile);
			g.drawImage(img, Ax+50, Ay,60,50, null);
			
		} catch (IOException e) {
			e.printStackTrace();
		}

		
	}
	//we need constrator to add the values
	//we need a function to draw in the panel 
	//we need a function to know the stat of the people (up down right left)
	
	

}
