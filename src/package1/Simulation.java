package package1;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

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
	private ArrayList<People> Peoples;
	private Ascenseur a;
	//booleans for simulation
	private boolean start = false;
	private boolean stop = true;
	//booleans for ascenseur
	private boolean arrive = false;
	private boolean arriveDest = false;
	private boolean stopped = true;
	private boolean empty = true;
	//booleans for people
	private boolean call = false;
	private boolean onboard = false;
	private boolean PersonArrived = false;
	
	private People nextP = null;

	private int counter = 0;
	private int time = 0;
	public Simulation(int n)
	{
		Floors = Floor.genFloors();
		Peoples = new ArrayList<People>();
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
			Floor f=null;
			if(!call && nextP==null)
			{
				for(Floor fs : Floors)
				{
					if(!fs.getPeoples().isEmpty())
					{
						nextP = fs.getPeoples().get(0);
						System.out.println(nextP);
						call = true;
						break;
					}
				}
			}
			if(call)
			{
				if(stopped && !arrive && a.getPeople().isEmpty())
				{
					MoveAsc(nextP.getCurrentF(), a);
				}
				if(a.getCurrentF().equals(nextP.getCurrentF()))
				{
					arrive=true;
					stopped = true;
				}
				if(arrive && !onboard)
				{
					MovePersonToAsc(nextP,a);
					empty = false;
					arrive = false;
				}
				if(!arriveDest && !a.getPeople().isEmpty())
				{
					f = a.getPeople().get(0).getDestinationF();
					MoveAsc(f, a);
				}
				if(a.getCurrentF().equals(nextP.getDestinationF()))
				{
					arriveDest = true;
				}
				if(arriveDest)
				{
					onboard = false;
					exitAsceneur(nextP, a);
					stopped = true;
				}
			}
		}
	}
	public void MoveAsc(Floor F,Ascenseur a)
	{
		System.out.println("Going from floor "+a.getCurrentF().getNumber()+" To floor :"+F.getNumber());
		ArrayList<People> ps = a.getPeople();
		boolean stop=false;
		if(!stop)
		{
			//si l'ascenseur est arriv� a l'etage F
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
		if(!end)
			p.setAx(p.getAx()+pSpeed);
		if(p.getAx()==500)
			end = true;
		
		if(end)
		{
			a.getPeople().remove(p);
			Peoples.remove(p);
			call = false;
			nextP = null;
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
