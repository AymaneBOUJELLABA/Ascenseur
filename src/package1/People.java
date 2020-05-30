package package1;

import java.awt.Image;

public class People
{
	//image graphic
	private static Image icon;
	//position du personne
	private double x,y;
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

	public People()
	{
		
	}
	//we need constrator to add the values
	//we need a function to draw in the panel 
	//we need a function to know the stat of the people (up down right left)
	
	

}
