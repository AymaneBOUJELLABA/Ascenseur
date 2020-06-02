package package1;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;


public class Simulation extends JPanel
{
	private Floor sp = new Floor(); //where the elevator will go up and down
	private ArrayList<Floor> Floors;
	private ArrayList<People> Peoples;
	private Ascenseur a1 = new Ascenseur();
	
	public Simulation()
	{
		 Peoples = new ArrayList<People>();
		 Floors = new ArrayList<Floor>();
	}
	@Override
	protected void paintComponent(Graphics g)
	{	
		super.paintComponent(g);
		//background
		this.setBackground(new Color(78,129,158));
		//couleur du batiment
		g.setColor(new Color(99,181,227));
		//3 premier etage
		g.fillRect(150,0,440,300);
		//2 dernier etage
		g.fillRect(50,300,645,300);
		
		//draw all floors
		this.drawFloors(g);
		this.drawSpace(g);
		for(Floor f: Floors)
		{
			System.out.println(f);
		}
		
		this.genPeoples(5, g);
		
		a1.drawElevator(g);
		
		a1.goTo(Floors.get(0));
		//MoveAsc(a1);
		
		for(Floor f : Floors)
		{
			System.out.println("Floor : "+f);
			System.out.println(f.getPeoples());
		}
	}
	
	//goTo function still work in progress
	public void MoveAsc(Ascenseur asc)
	{
		boolean stop=false;
		while(!stop)
		{
			
		}
	}
	//genérer un nombre spécifique de personnes
	public void genPeoples(int n, Graphics g)
	{
		
		for(int i=0; i<n;i++)
		{
			//random number generation from 0 to (n-1)
			int min=0, max=n;
			int DFn = (int)(Math.random() * (max - min + 1) + min);
			//la nouvelle personne
			People p;
			//affecter selectionner l'étage
			int Fn=i;
			System.out.println(Fn);
			if(i<3)
			{
				//creer la personne et affecter l'étage sélectionner
				p = new People(90,Floors.get(Fn).getAy()+48,Floors.get(Fn));
				//affecter une destination au hasard
				p.setDestinationF(Floors.get(DFn));
				p.drawPerson(g);
			}else
			{
				//same thing but for the bottom floors
				p = new People(40,Floors.get(Fn).getAy()+48,Floors.get(Fn));
				p.setDestinationF(Floors.get(DFn));
				p.drawPerson(g);
			}
			
			Floors.get(Fn).addPerson(p);
			Peoples.add(p);
			System.out.println(p.getCurrentF());
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
		//400 300 200 100
	}
	// to draw all floors
	public void drawFloors(Graphics g) 
	{
		//draw floor lines
		Graphics2D g2 = (Graphics2D) g ;
		g2.setColor(new Color(0,66,112)); // white smock 245,245,245 
		g2.setStroke(new BasicStroke(3));
		
		
		//drawlines for each floor
		for(int i=0,j=5;i<500 && j>0 ; i+=100,j--)
		{
			Floor newF= new Floor();
			newF.setAy(i);
			newF.setNumber(j);
			System.out.println("newF : "+newF);
			if(newF.getAy()<300)
			{
				g.drawLine(150,newF.getAy(),588,newF.getAy());
				g.drawString(" Etage : "+newF.getNumber(), 50, i+30);
			}
			else
			{
				g.drawLine(50,newF.getAy(),693,newF.getAy());
				g.drawString(" Etage : "+newF.getNumber() , 50, i+30);
			}	
			Floors.add(newF);
		}
	}
	public static void main(String[] args)
	{
		final JPanel panel;
		final JFrame frame;
		
		Simulation s1 = new Simulation();
		s1.setBounds(0, 0, 750, 500);
		panel = new JPanel(null);
		panel.setBackground(UIManager.getColor("Focus.color"));
        panel.setPreferredSize(new Dimension(750, 500));
        panel.add(s1);
        
        frame = new JFrame("Simulation d'Ascenceurs");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
        frame.setAlwaysOnTop(true);
        frame.setResizable(false);
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
	}
}
