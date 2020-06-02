package package1;
import java.awt.*;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;
public class Ascenseur
{
	//l'étage courrant de l'ascenseur 
	// we could simply use an int 
	private Floor CurrentF;
	//la file des étages selectionner
	// we could simply use an int 
	private LinkedList<Floor> DestinationF;
	//la capacité maximale de l'ascenseur
	private final static int max_capacity = 4;
	//arraylist des personne au bord de l'ascenseur
	private ArrayList<People> People; 
	//l'état des ports de l'ascenseur true = overt, false = fermé
	private boolean doorsopen;
	//position de l'ascenseur
	private int x,y;
	//l'état de l'ascenseur (en mouvement, en arret )
	private int state;
	
	//states:
	private final static int Moving = 1;
	private final static int Stopped = 0;
	
	// we need two val boolean one is closed and one is opened

	public Ascenseur() 
	{
		DestinationF = new LinkedList<Floor>();
		state = Stopped;
		x=327;
		y=410;
	}
	//constructor
	//draw : moved draw to simulation
	//function to know the state (up down wait)
	//function for people on board
	//function to add people 
	public int addPeople(People p)
	{
		if(People.size() < max_capacity-1)
		{
			People.add(p);
			return 1;
		}
		return -1;
	}
	//work in progress
		public void goTo(Floor Dest)
		{
			if(state == Stopped)
			{
				DestinationF.addFirst(Dest);
			}
			else
			{
				DestinationF.addLast(Dest);
			}
		}
	//call function work in progress
	public void CallElevator(Floor source)
	{
		//si l'ascenseur n'est pas plein
		if(People.size() < max_capacity-1)
		{
			//si l'ascenseur est en arret
			if(state == Stopped)
			{
				//ajouter la source de l'appel à la file
				DestinationF.add(source);
				//on deplace l'asceneur vers la source
				goTo(DestinationF.removeFirst());
			}
			else
			{
				DestinationF.addLast(source);
			}
		}
	}
	
	public void drawElevator(Graphics g)
	{
		g.setColor(new Color(237,245,247));
		g.drawRect(x,y,85,90);
	}
	
	
	
	public int getState()
	{
		return state;
	}

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

	public void setDestinationF(LinkedList<Floor> destinationF)
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

	public int getX()
	{
		return x;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public int getY()
	{
		return y;
	}

	public void setY(int y)
	{
		this.y = y;
	}

	public int isState()
	{
		return state;
	}

	public void setState(int state)
	{
		this.state = state;
	}

	public static int getMaxCapacity()
	{
		return max_capacity;
	}

	
}
