package package1;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;


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
		if(a.getPeople().isEmpty())
			System.out.println("a is empty");
		else
			System.out.println(a.getPeople());
		counter++;
		if(counter == 500)
		{
			counter = 0;
			People.genPerson(Peoples, Floors);
			start=true;
		}
		if(!Peoples.isEmpty())
		{
			People.drawPeople(Peoples, gg);
		}
		if(start)
		{

			for(Floor fs : Floors)
			{
				if(!fs.getPeoples().isEmpty())
				{
					call = true;
					stopped = false;
					goNext = false;
					exists = true;
					break;
				}
			}
			if(Peoples.isEmpty())
			{
				exists = false;
			}
			if(exists)
			{
				for(People nextP : Peoples)
				{
					manouver(a,nextP);
				}
			}
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
