package package1;
import java.awt.*;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.CopyOnWriteArrayList;
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
	//l'état des ports de l'ascenseur true = overt, false = fermé
	private boolean doorsopen;
	//position de l'ascenseur
	private int x,y;
	//l'état de l'ascenseur (en mouvement, en arret )
	private int state;
	
	//states:
	public final static int Moving = 1;
	public final static int Stopped = 0;
	
	// we need two val boolean one is closed and one is opened

	public Ascenseur(int y) 
	{
		Calls = new LinkedList<Floor>();
		People = new CopyOnWriteArrayList<People>();
		state = Stopped;
		x=327;
		this.y=y;
	}
	public Ascenseur(Floor currentF) 
	{
		Calls = new LinkedList<Floor>();
		People = new CopyOnWriteArrayList<People>();
		state = Stopped;
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
			Calls.add(p.getDestinationF());
			return 1;
		}
		return -1;
	}
	
	public void MoveAsc(Floor F)
	{
		boolean stop=false;
		
		while(!stop)
		{
			//si l'ascenseur est arrivé a l'etage F
			if( F.getAy()+5 == this.getY())
			{
				stop = true;
			}
			else
			{
				//si l'etage est en bas
				if(F.getAy()+5 > this.getY())
				{
					this.setY(this.getY()+1);
				}
				//si l'etage est en haut
				if(F.getAy()+5 < this.getY())
				{
					this.setY(this.getY()-1);
				}
			}
		}
	}
	//Moving Ascenseur
	public void drawMoveAsc(Graphics g)
	{
		
	}
	//dessiner l'ascenseur
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

	public int getMaxCapacity()
	{
		return max_capacity;
	}
	

	
}
