package package1;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

import package1.Ascenseur.Mode;
import package1.People.PMode;


public class Simulation extends JPanel
{
	private int Elspeed = 5;
	private int pSpeed = 1;
	private Graphics g;
	private Floor sp = new Floor(); //where the elevator will go up and down
	private ArrayList<Floor> Floors;
	//private ArrayList<People> Peoples;
	private CopyOnWriteArrayList<People> Peoples;
	private Ascenseur a;
	//booleans for simulation
	private boolean start = false;
	private boolean stop = true;
	private boolean exists = false;
	//booleans for ascenseur
	private boolean arrive = false;
	private boolean arriveDest = false;
	private boolean stopped = true;
	private boolean empty = true;
	private boolean goNext = true;
	private boolean standby = true;
	private boolean boarding = false;
	private boolean prepare = false;
	private boolean launch = false;
	//booleans for people
	private boolean call = false;
	private boolean onboard = false;
	private boolean PersonArrived = false;
	private boolean finished = false;
	
	private int counter = 0;
	private int time = 0;
	public Simulation(int n)
	{
		Floors = Floor.genFloors();
		Peoples = new CopyOnWriteArrayList<People>();
		a = new Ascenseur(Floors.get(4));
		Thread animationThread = new Thread(new Runnable()
        {
            public void run()
            {
                while (true)
                {
                	repaint();
                    try
                    {
                    	Thread.sleep(10);
                    } catch (Exception ex)
                    {
                    	
                    }
                }
            }
        });
        animationThread.start();
	}
	@Override
	protected void paintComponent(Graphics g)
	{	
		Graphics2D gg = (Graphics2D) g;
		super.paintComponent(g);
		//background
		this.setBackground(new Color(78,129,158));
		//couleur du batiment
		gg.setColor(new Color(99,181,227));
		//3 premier etage
		gg.fillRect(150,0,440,300);
		//2 dernier etage
		gg.fillRect(50,300,645,300);
		//draw all floors
		Floor.drawFloors(gg);
		this.drawSpace(gg);
		//draw the elevator
		a.drawElevator(gg);
		//draw all the people
		counter++;
		if(counter == 200)
		{
			counter = 0;
			People.genPerson(Peoples, Floors);
			start=true;
		}
		if(!Peoples.isEmpty())
		{
			People.drawPeople(Peoples, gg);
		}
		
		for(Floor f : Floors)
		{
			System.out.println("f has : " + f.getPeoples().size() + " person");
		}
		System.out.println("A has : "+ a.getPeople().size() + " person");
		manouver(a);
		
	}
	public void manouver(Ascenseur a)
	{
		//si l'ascenseur est vide et aucune personne n'existe
		if(standby)
		{
			//chercher ou se trouve les personnes
			for(Floor f: Floors)
			{
				if(f.getPeoples().isEmpty())
					continue;
				else
				{
					//Move elevator to upper floor
					a.setState(Mode.UP);
					
					call = true;
					standby = false;
					
					break;
				}
			}
		}
		//Move elevator to targeted passengers
		if(call)
		{
			if ( (a.getY()-5) % 100 == 0)
			{
				//affecter l'etage a l'ascenseur
				a.setCurrentF(Floors.get( a.getY()/100 ));
				boolean arrivedD = false;
                
                for (People p  : a.getPeople())
                {
                    if (p.getDestinationF().equals(a.getCurrentF()))
                    {
                        arrivedD = true;
                    }
                }
                
                if(a.getCurrentF().getPeoples().isEmpty() && !arrivedD)
                {
                	a.setState( a.getDirection() == Mode.UP ? Mode.UP : Mode.DOWN);
                }
                else
                {
                	a.setState(Mode.WAIT);
                	for(People p: a.getCurrentF().getPeoples())
                	{
                		p.setState(PMode.RIGHT);
                	}
                	call = false;
                	boarding = true;	
                }
			}
		}
		
		if(a.getState() == Mode.WAIT && boarding)
		{
			//unload the passengers
			
			a.Depart(a.getCurrentF().getDeparting());
			
			//load the passengers
			Iterator<People> it = a.getCurrentF().getPeoples().iterator();
			while(it.hasNext())
			{
				People P = it.next();
				a.addPeople(P);
				it.remove();
			}
			int size = a.getPeople().size();
			
			for(int i=0; i < size ;i++)
			{
				a.getPeople().get(i).setDestX(327-i*20);
			}

			if(!a.getPeople().isEmpty())
			{
				boarding = false;
				prepare = true;
			}
			else
			{
				boarding = false;
				finished = true;
			}
		}
		
		
		if(finished)
		{

			a.setState(Mode.WAIT);
			
			if(a.getState() == Mode.WAIT)
			{
				finished = false;
				standby = true;
			}
		}
		
		if(prepare)
		{
			CopyOnWriteArrayList<People> temp = a.getPeople();
			System.out.println(temp);
			if(!temp.isEmpty())
			{
				if(temp.get(temp.size()-1).getAx() == temp.get(temp.size()-1).getDestX())
				{
					System.out.println("State changed");
					a.setState(Mode.WAIT);
					prepare = false;
					launch = true;
				}
				else
				{
					Iterator<People> it = temp.iterator();
					
					while(it.hasNext())
					{
						People p = it.next();
						if(p.getAx() == a.getX())
						{
							break;
						}
						if(p.getAx() > a.getX())
						{
							it.remove();
							break;
						}
					}
				}
			}
			else
			{
				boarding = false;
				finished = true;
			}
		}
		
		if(launch)
		{
			System.out.println("a.getDirect = " +a.getDirection());
			a.setState( a.getDirection() == Mode.UP ? Mode.UP : Mode.DOWN);
			
			launch = false;
			
			call = true;
		}
	}
	public void manouver(Ascenseur a, People nextP)
	{
		if(!stopped && !arrive && a.getPeople().isEmpty())
		{
			MoveAsc(nextP.getCurrentF(), a);
		}
		if(!onboard && a.getCurrentF().equals(nextP.getCurrentF()))
		{
			arrive=true;
			stopped = true;
		}
		if(arrive && stopped && !onboard)
		{
			MovePersonToAsc(nextP,a);
			empty = false;
			arrive = false;
		}
		if(onboard && !stopped && !arriveDest && !a.getPeople().isEmpty())
		{
			Floor f = a.getPeople().get(0).getDestinationF();
			if(f!=null)
				MoveAsc(f, a);
		}
		if(a.getCurrentF().equals(nextP.getDestinationF()))
		{
			arriveDest = true;
			stopped = true;
		}
		if(arriveDest && stopped)
		{
			onboard = false;
			exitAsceneur(nextP, a);
		}
	}
	public void MoveAsc(Floor F,Ascenseur a)
	{
		System.out.println(a.getCurrentF().getNumber()+" -> "+F.getNumber());
		CopyOnWriteArrayList<People> ps = a.getPeople();
		boolean stop=false;
		if(!stop)
		{
			//si l'ascenseur est arrivé a l'etage F
			if( F.getAy()+5 == a.getY())
			{
				stop = true;
				a.setCurrentF(F);
			}
			else
			{
				//si l'etage est en bas
				if(F.getAy()+5 > a.getY())
				{
					a.setY(a.getY()+Elspeed);
					if(!ps.isEmpty())
					{
						for(People p : ps)
							p.setAy(p.getAy()+Elspeed);
					}
							
				}
				//si l'etage est en haut
				if(F.getAy()+5 < a.getY())
				{
					a.setY(a.getY()-Elspeed);
					if(!ps.isEmpty())
					{
						for(People p : ps)
							p.setAy(p.getAy()-Elspeed);
					}
				}
			}
		}
	}
	public void MovePersonToAsc(People p,Ascenseur a)
	{
		boolean isIn = false;
		if(!isIn)
		{
			if(p.getAx()==a.getX())
			{
				
				onboard = true;
				isIn=true;
				a.addPeople(p);
				stopped = false;
				System.out.println("person added");
			}
			if(!isIn)
			{
				p.setAx(p.getAx()+pSpeed);
			}
		}
	}
	
	public void exitAsceneur(People p,Ascenseur a)
	{
		boolean end = false;
		p.setCurrentF(a.getCurrentF());
		
		if(!end)
			p.setAx(p.getAx()+pSpeed);
		if(p.getAx()==500)
			end = true;
		
		if(end)
		{
			a.getPeople().remove(p);
			Peoples.remove(p);
			call = false;
			goNext = true;
			finished = true;
		}
	}
	public int getFloorindex(int i)
	{
		return Floors.size()-i-1;
	}
	//to draw the spaces between floors
	public void drawSpace(Graphics g) 
	{
		
		//Elevator space
		Graphics2D g3 = (Graphics2D) g ;
		g3.setColor(new Color(0,66,112));
		for(int i = 0 ; i<500;i+=100)
		{
			g3.fillRect(320, i, 100, 100);
		}
	}
	
	public static void main(String[] args)
	{
        JFrame frame = new JFrame("Train Demo");
        
        Simulation s1 = new Simulation(5);
		s1.setBounds(0, 0, 750, 500);
		s1.setPreferredSize(new Dimension(750, 500));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setAlwaysOnTop(true);
        frame.setSize(750, 535);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.add(s1);
        frame.setVisible(true);
	}
}
