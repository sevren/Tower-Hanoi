/***
 * Towers of Hanoi game Applet
 * Author: Kiril Tzvetanov Goguev
 * Object of the game is to get the disks from the far left to the far right
 * 
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

import javax.swing.JApplet;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.io.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class TowersApplet extends JApplet implements ActionListener {
	public TowersApplet() {
	}
	private static PrintStream ps = null;
    private JTextArea textArea = new JTextArea();
	private Container container;
	Stack<Disk>[] posts;
	Disk[] disks;
	boolean temp = true;
	int numMoves=0;
	boolean solved=false;
	private JTextField t1;
	private JTextField t2;
    
    public static void printDisk(Disk d)
	{
		switch (d.getSize())
		{
			case 0:
				//System.out.print("       *");
				ps.print("       *");
				break;
			case 1:
				//System.out.print("      **");
				ps.print("      **");
				break;
			case 2:
				//System.out.print("     ***");
				ps.print("     ***");
				break;
			case 3:
				//System.out.print("    ****");
				ps.print("    ****");
				break;
				
			default: 
				//System.out.print("SOMETHING IS HORRIBLY WRONG");
				ps.print("SOMETHING IS HORRIBLY WRONG");
				break;
		}
	}

	public static void printTowers(Stack<Disk>[] posts)
	{
		Stack<Disk>[] temp = new Stack[3];
		temp=new Stack[3];
		for(int x=0;x<3;x++)
		{
			temp[x]=new Stack<Disk>();
		}

		for(int x=0;x<4;x++)
		{	
			for(int y=0; y<3;y++)
			{
				if(posts[y].empty()!=true)
				{
		
					printDisk(posts[y].peek());
					temp[y].push(posts[y].pop());
		
				}
				else
				{
					
					ps.print("        ");
				}
			}
			
			
			ps.println("");
		}	

		for(int x=0;x<3;x++)
		{
			while(temp[x].empty()!=true)
			{
				posts[x].push(temp[x].pop());
			}
		}
		
		
		ps.println("  ------    ------    ------");
		ps.println("    1         2         3   ");
		ps.println("");
		ps.println("");
	}
	
	public static boolean move(Stack<Disk>[] posts,int mF,int mT)
	{
		
		try
		{
			
		
			int moveFrom = mF; 
			int moveTo =  mT;
			
			

			if((moveFrom == 0)&&(moveTo==0))
			{
				System.exit(0);
			}
			
			if((moveFrom<1)||(moveFrom >3)||(moveTo<1)||(moveTo>3))
			{
				ps.print("invalid move");
				return false;
			}
			moveFrom--;
			moveTo--;
			if (posts[moveFrom].empty())
			{
				ps.print("invalid move");
				
				return false;
			}
			
			if (posts[moveTo].empty())
			{
				posts[moveTo].push(posts[moveFrom].pop());
				return true;
			}
			else if (posts[moveFrom].peek().getSize()>posts[moveTo].peek().getSize())
			{
				ps.print("invalid move");
				return false;
			}
			else
			{
				posts[moveTo].push(posts[moveFrom].pop());
				return true;
			}
			
			
		}
		catch (Exception e)
		{
			return false;
		}
	}

	public static boolean checkWin(Stack<Disk>[] posts)
	{
	
		Stack<Disk>temp = new Stack<Disk> ();
		int numPops=0;
		
		if(posts[2].empty()!=true)
		{
			for(int x=0;x<4;x++)
			{
				if(posts[2].empty()==true)
				{
					while(temp.empty()!=true)
					{
						posts[2].push(temp.pop());
					}
					return false;
					
				}
				temp.push(posts[2].pop());
				
			}
			return true;
		}
		return false;

	}
	
    
    
	
	public void init() {
		
		this.setSize(300, 300);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{600, 0};
		gridBagLayout.rowHeights = new int[] {100, 20, 100, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, 1.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		//JFrame frame=new JFrame();
		//frame.setSize(250, 250);
		textArea = new JTextArea();
		textArea.setSize(310, 180);
		textArea.setEditable(false);
		//this.add(textArea);
		//frame.getContentPane().setLayout(new BorderLayout());
		//frame.getContentPane().add(new JScrollPane(textArea),BorderLayout.CENTER);
		//frame.getContentPane().add(button,BorderLayout.SOUTH);
		//frame.setVisible(true);	

        GridBagConstraints gbc_textArea = new GridBagConstraints();
        gbc_textArea.fill = GridBagConstraints.BOTH;
        gbc_textArea.insets = new Insets(0, 0, 5, 0);
        gbc_textArea.gridx = 0;
        gbc_textArea.gridy = 0;
        getContentPane().add(textArea, gbc_textArea);
        
        JSplitPane splitPane = new JSplitPane();
        GridBagConstraints gbc_splitPane = new GridBagConstraints();
        gbc_splitPane.insets = new Insets(0, 0, 5, 0);
        gbc_splitPane.fill = GridBagConstraints.BOTH;
        gbc_splitPane.gridx = 0;
        gbc_splitPane.gridy = 1;
        getContentPane().add(splitPane, gbc_splitPane);
        
        JLabel lbl = new JLabel("Move:");
        splitPane.setLeftComponent(lbl);
        
        JSplitPane splitPane_1 = new JSplitPane();
        splitPane.setRightComponent(splitPane_1);
        
        t1 = new JTextField();
        splitPane_1.setLeftComponent(t1);
        t1.setColumns(10);
        
        t2 = new JTextField();
        splitPane_1.setRightComponent(t2);
        t2.setColumns(10);
        
        JTextPane txtpnTestnTest = new JTextPane();
        txtpnTestnTest.setEditable(false);
        txtpnTestnTest.setText("To move the disks: type numbers (1-3) in both the left and right boxes above. Hit enter to execute the action  \r\n Example: 1 3 will attempt to move the disk from column 1 to 3 \r\n invalid moves will not change the configuration!");
        GridBagConstraints gbc_txtpnTestnTest = new GridBagConstraints();
        gbc_txtpnTestnTest.fill = GridBagConstraints.BOTH;
        gbc_txtpnTestnTest.gridx = 0;
        gbc_txtpnTestnTest.gridy = 2;
        getContentPane().add(txtpnTestnTest, gbc_txtpnTestnTest);
        
        t1.addActionListener(this);
        t2.addActionListener(this);
		//container.add(textArea);
		//this is the trick: overload the println(String) 
	    //method of the PrintStream
	    //and redirect anything sent to this to the text box
		ps =  new PrintStream(System.out)
		{
			public void print(String x) 
			{
				textArea.append(x);
			}
			
			public void println(String x)
			{
				textArea.append(x+"\n");
			}
		};
		
		

		
		int numMoves=0;
		boolean solved=false;
		
		disks = new Disk[4];
		for (int x=0; x<4; x++)
		{
			disks[x] = new Disk(x);
		}
		
		posts=new Stack[3];
		for(int x=0;x<3;x++)
		{
			posts[x]=new Stack<Disk>();
		}
		for (int x=3; x>=0; x--)
		{
			posts[0].push(disks[x]);
		}
		
		
		printTowers(posts);
		
	
		
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		int mF = Integer.parseInt(t1.getText());
		int mT = Integer.parseInt(t2.getText());
		
		temp = move(posts,mF,mT);
		if (temp)
		{
			textArea.setText("");
			printTowers(posts);
			numMoves++;
			
		}
		
		
		
		if (checkWin(posts))
		{
			ps.println("Congratulations!");
			ps.println("You solved the Towers of Hanoi in "+numMoves+" moves");
			//System.exit(1);
		}
		
	}
		
		
	
		
		
	}
	
