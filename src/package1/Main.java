package package1;

import java.awt.Dimension;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.UIManager;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;


public class Main
{
    private final JFrame frame;
    private final JPanel panel;

    private Simulation simulation;
   
    
    public Main()
    {

		simulation = new Simulation(10);
		simulation.setBounds(0, 0, 750, 500);
		
        panel = new JPanel(null);
        panel.setBackground(UIManager.getColor("Focus.color"));
        panel.setPreferredSize(new Dimension(750, 550));
        panel.add(simulation);
     
        frame = new JFrame("Simulation de 2 Ascenceurs");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
        frame.setAlwaysOnTop(true);
        frame.setResizable(false);
        frame.getContentPane().add(panel);
        
        JPanel panel_1 = new JPanel();
        panel_1.setBorder(new LineBorder(Color.WHITE));
        panel_1.setBackground(new Color(99,181,227));
        panel_1.setBounds(0, 501, 750, 49);
        panel.add(panel_1);
        JLabel desc = new JLabel("Réalisé par : BOUJELLABA Aymane");
        panel_1.add(desc);
        
        desc.setHorizontalAlignment(SwingConstants.CENTER);
        desc.setFont(new Font("Tahoma", Font.PLAIN, 22));
        frame.pack();
        frame.setVisible(true);
    }
    
    public static void main(String[] args)
    {
    	// TODO Auto-generated method stub
        Main main = new Main();
    }
}

		
