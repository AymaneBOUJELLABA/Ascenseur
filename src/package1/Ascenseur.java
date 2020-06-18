package package1;
import java.awt.*;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.CopyOnWriteArrayList;

import package1.People.PMode;

import java.util.LinkedList;
public class Ascenseur
{
	//l'étage courrant de l'ascenseur 
	// we could simply use an int 
	private Floor CurrentF;
	//la file des étages selectionner
	// we could simply use an int 
	private LinkedList<Floor> Calls;
	//la capacité maximale de l'ascenseur
	private final static int max_capacity = 4;
	//arraylist des personne au bord de l'ascenseur
	private CopyOnWriteArrayList<People> People; 
	//position de l'ascenseur
	private int x,y;
	//l'état de l'ascenseur (en mouvement, en arret )
	private Mode State;
	private Mode Direction;
	//vitesse de l'assenceur
	private int ESpeed = 1;
	public Ascenseur(int y) 
	{
		Calls = new LinkedList<Floor>();
		People = new CopyOnWriteArrayList<People>();
		State = Mode.WAIT;
		Direction=Mode.UP;
		x=327;
		this.y=y;
	}
	public Ascenseur(Floor currentF) 
	{
		Calls = new LinkedList<Floor>();
		People = new CopyOnWriteArrayList<People>();
		State = Mode.WAIT;
		Direction=Mode.UP;
		x=327;
		this.y=currentF.getAy()+5;
		this.setCurrentF(currentF);
	}
	
	//ajouter un nouveau personne a l'ascenseur
	public int addPeople(People p)
	{
		if(People.contains(p))
			return -2;
		if(People.size() < max_capacity)
		{
			People.add(p);
			return 1;
		}
		return -1;
	}
	public void Depart(ArrayList<People> Departing)
	{
		Iterator<People> it = this.getPeople().iterator();
		while(it.hasNext())
		{
			People p = it.next();
			if(p.getDestinationF().equals(this.getCurrentF()))
			{
				p.setDestX(p.getDestX()+150);
				p.setState(PMode.RIGHT);
				Departing.add(p);
				
				this.People.remove(p);
				
			}
		}
	}
	public void MoveAsc()
	{
		switch(State)
		{
			case WAIT : break;
			
			case UP : 	this.setY(this.getY() - ESpeed);
						
						if(!this.getPeople().isEmpty())
						{
							for(People p : this.getPeople())
							{
								p.setAy(p.getAy() - ESpeed);
							}
						}
						
						if(getY() == 100)
						{
							Direction = Mode.DOWN;
						}
						break;
						
			case DOWN : this.setY(this.getY() + ESpeed);
						
						if(!this.getPeople().isEmpty())
						{
							for(People p : this.getPeople())
							{
								p.setAy(p.getAy() + ESpeed);
							}
						}
						
						if(getY() == 400)
						{
							Direction = Mode.UP;
						}
						break;
						
			default : 	System.err.println("WRONG STATE!");
						break;
		}
	}

	//dessiner l'ascenseur
	public void drawElevator(Graphics g)
	{
		g.setColor(new Color(237,245,247));
		g.drawRect(x,y,85,90);
		MoveAsc();
	}
	
	public Floor getCurrentF()
	{
		return CurrentF;
	}

	public void setCurrentF(Floor currentF)
	{
		System.out.println("CFloor = " + CurrentF);
		CurrentF = currentF;
	}

	public LinkedList<Floor> getCalls()
	{
		return Calls;
	}

	public void setCalls(LinkedList<Floor> Calls)
	{
		this.Calls = Calls;
	}

	public CopyOnWriteArrayList<People> getPeople()
	{
		return People;
	}

	public void setPeople(CopyOnWriteArrayList<People> people)
	{
		People = people;
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
	public Mode getDirection()
	{
		return Direction;
	}

	public void setDirection(Mode Direction)
	{
		this.Direction = Direction;
	}
	public Mode getState()
	{
		return State;
	}

	public void setState(Mode state)
	{
		this.State = state;
	}

	public int getMaxCapacity()
	{
		return max_capacity;
	}
	
	public enum Mode {UP, DOWN, WAIT};
	
}
