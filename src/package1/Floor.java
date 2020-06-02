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
		Peoples = new ArrayList<People>();
	}
	public Floor()
	{
		Peoples = new ArrayList<People>();
	}
	
	//removed draw to simulation
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
	public void addPerson(People p)
	{
		Peoples.add(p);
	}
	public ArrayList<People> getPeoples()
	{
		return Peoples;
	}
	public void setPeoples(ArrayList<People> peoples)
	{
		Peoples = peoples;
	}
	public int getAy()
	{
		return Ay;
	}
	public void setAy(int ay)
	{
		Ay = ay;
	}
	public void setNumber(int number)
	{
		this.number = number;
	}
	public void goTo(int number) 
	{
		// a switch  to go the floor  
		
	}
	@Override
	public String toString()
	{
		return "Floor [number=" + number + ", Ay=" + Ay + "]";
	}
	
	
	//constractor
	//add people to the floor
	//draw the lines
}
