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
import javax.swing.Timer;
import javax.swing.UIManager;

import package1.Ascenseur.Mode;
import package1.People.PMode;


public class Simulation extends JPanel
{
	
	private ArrayList<Floor> Floors;
	//private ArrayList<People> Peoples;
	private CopyOnWriteArrayList<People> Peoples;
	private Ascenseur a;
	//booleans for ascenseur
	
	
	private Timer timer;
    private boolean isRunning = true;
    private int counter;
    private int timeElapsedInSecs;
	public Simulation(int refreshRate)
	{
		Floors = Floor.genFloors();
		Peoples = new CopyOnWriteArrayList<People>();
		a = new Ascenseur(Floors.get(4));
		timer = new Timer(refreshRate, (e) -> {
            
            counter += refreshRate;
            
            if (counter / 500 == 1)
            {
                counter = 0;
                ++timeElapsedInSecs;
                
                if (timeElapsedInSecs % 5 == 0)
                {
                	People.genPerson(Peoples, Floors);
                }
            }
            
            if (isRunning)
            {
                repaint();
            }
        });
        
        timer.start();
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
		if(!Peoples.isEmpty())
		{
			People.drawPeople(Peoples, gg);
		}

		manouver(a);
		
	}
	public void manouver(Ascenseur a)
	{
		//si l'ascenseur est vide et aucune personne n'existe
		if(a.standby)
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
					
					a.call = true;
					a.standby = false;
					
					break;
				}
			}
		}
		//Move elevator to targeted passengers
		if(a.call)
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
                	a.call = false;
                	a.boarding = true;	
                }
			}
		}
		
		if(a.getState() == Mode.WAIT && a.boarding)
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
				a.boarding = false;
				a.prepare = true;
			}
			else
			{
				a.boarding = false;
				a.finished = true;
			}
		}
		
		
		if(a.finished)
		{

			a.setState(Mode.WAIT);
			
			if(a.getState() == Mode.WAIT)
			{
				a.finished = false;
				a.standby = true;
			}
		}
		
		if(a.prepare)
		{
			CopyOnWriteArrayList<People> temp = a.getPeople();
			System.out.println(temp);
			if(!temp.isEmpty())
			{
				if(temp.get(temp.size()-1).getAx() == temp.get(temp.size()-1).getDestX())
				{
					a.setState(Mode.WAIT);
					a.prepare = false;
					a.launch = true;
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
				a.boarding = false;
				a.finished = true;
			}
		}
		
		if(a.launch)
		{
			a.setState( a.getDirection() == Mode.UP ? Mode.UP : Mode.DOWN);
			
			a.launch = false;
			
			a.call = true;
		}
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
        JFrame frame = new JFrame("Asceneur Demo");
        
        Simulation s1 = new Simulation(10);
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
