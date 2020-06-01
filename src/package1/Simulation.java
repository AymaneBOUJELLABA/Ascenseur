package package1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;


public class Simulation extends JPanel
{
	private Floor sp = new Floor(); //where the elevator will go up and downs
	private People p1 = new People(60,450);
	
	public Simulation() 
	{
			
	}
	@Override
	protected void paintComponent(Graphics g)
	{	
		super.paintComponent(g);
		this.setBackground(new Color(78,129,158));
		g.setColor(new Color(99,181,227));
		g.fillRect(150,0,440,300);
		g.fillRect(50,300,645,300);
		sp.drawSpace(g);
		sp.drawFloor(g);
		p1.drawPerson(g);
		p1.drawKiller(g);
			//repaint();
			
	}
	
	public static void main(String[] args)
	{
		final JPanel panel;
		final JFrame frame;
		
		Simulation s1 = new Simulation();
		s1.setBounds(0, 0, 750, 500);
		panel = new JPanel(null);
		panel.setBackground(UIManager.getColor("Focus.color"));
        panel.setPreferredSize(new Dimension(750, 550));
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
