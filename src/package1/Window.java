package package1;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridLayout;
import javafx.*;

public class Window
{

	/*COLORS TO USE : 
	*background : 10,125,165;
	*floo : 102 189 227;
	*elevator color : 8,76,121;
	*some white : 237,245,247;

	*/
	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try 
				{
					Window window = new Window();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Window()
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		frame = new JFrame();
		frame.setBounds(100, 100, 903, 689);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new GridLayout(4,1));
	}

}
