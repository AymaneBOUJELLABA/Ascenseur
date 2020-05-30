package package1;

import java.util.ArrayList;
import java.util.Queue;

public class Ascenseur
{
	//l'étage courrant de l'ascenseur 
	// we could simply use an int 
	private Floor CurrentF;
	//la file des étages selectionner
	// we could simply use an int 
	private Queue<Floor> DestinationF;
	//la capacité maximale de l'ascenseur
	private final static int max_capacity = 4;
	//arraylist des personne au bord de l'ascenseur
	private ArrayList<People> People; 
	//l'état des ports de l'ascenseur true = overt, false = fermé
	private boolean doorsopen;
	//position de l'ascenseur
	private double x,y;
	//l'état de l'ascenseur (en mouvement, en arret )
	private boolean state;
	// we need two val boolean one is closed and one is opened

	public Ascenseur() 
	{
		
	}
	//constructor
	//draw 
	//function to know the stat (up down  open close wait)
	//function for people on board
	//function to add people 

	public Floor getCurrentF()
	{
		return CurrentF;
	}

	public void setCurrentF(Floor currentF)
	{
		CurrentF = currentF;
	}

	public Queue<Floor> getDestinationF()
	{
		return DestinationF;
	}

	public void setDestinationF(Queue<Floor> destinationF)
	{
		DestinationF = destinationF;
	}

	public ArrayList<People> getPeople()
	{
		return People;
	}

	public void setPeople(ArrayList<People> people)
	{
		People = people;
	}

	public boolean isDoorsopen()
	{
		return doorsopen;
	}

	public void setDoorsopen(boolean doorsopen)
	{
		this.doorsopen = doorsopen;
	}

	public double getX()
	{
		return x;
	}

	public void setX(double x)
	{
		this.x = x;
	}

	public double getY()
	{
		return y;
	}

	public void setY(double y)
	{
		this.y = y;
	}

	public boolean isState()
	{
		return state;
	}

	public void setState(boolean state)
	{
		this.state = state;
	}

	public static int getMaxCapacity()
	{
		return max_capacity;
	}

	
}
