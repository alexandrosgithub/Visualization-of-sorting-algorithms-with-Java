import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Random;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;


 
public class SortingApplication extends JFrame implements ActionListener  {

	/* numbers to sort*/
	int numbers[];
	
	/*columns to be sorted*/
	int numColumns = 50;
	
	ButtonGroup[] buttonGroups;
	int numButtonGroups = 4;
	
	JButton[] buttons;
	int numButtons = 4;
	
	static JRadioButton[][] radioButtons;
	int numRadioButtons = 15;
	
	JLabel[] labels;
	int numLabels = 4;
	
	JPanel[] panels;
	int numPanels = 5;
	
	MyJPanel drawingArea;
	JTextArea commentArea= new JTextArea(5,10);
	
	// flags for painting the bars different than black
	boolean radix=false;
	boolean count=false;
	boolean insertion=false;
	boolean merge=false;
	boolean bitonic=false;
	boolean bubble=false;
	boolean selection =false;
	int redBar1,redBar2,green,redBar3,redBar4;
	int delay=10;
	
	int drawingAreaWidth;
	
	int drawingAreaHeight;
	
	int heightDivisor = 300;
	
	int heightDifference = 90;
	
	//if true, when paint is called numbers will be sorted using the preferred parameters
	boolean sort = false;
	
	boolean rectangles = false;
	
	boolean paused = false;
	
	boolean stop = false;
	
	boolean stepping = false;
	
	
	//labels for insertion sort
	static JLabel l=new JLabel("INSERTION SORT");
	static JLabel l1=new JLabel("1.   for j = 2 to length[A]");
	static JLabel l2=new JLabel("2.       do key = A[j]\n3           Insert A[j] into the sorted sequence A[1 . . j - 1]");
	static JLabel l3=new JLabel("3.           Insert A[j] into the sorted sequence A[1 . . j - 1]");
	static JLabel l4=new JLabel("4.           i = j - 1");
	static JLabel l5=new JLabel("5.       while i > 0 and A[i] > key");
	static JLabel l6=new JLabel("6.             do A[i + 1] = A[i]");
	static JLabel l7=new JLabel("7.                i = i - 1");
	static JLabel l8=new JLabel("8.        A[i + 1] = key");
	//labels for count sort
	static JLabel l9=new JLabel("COUNT SORT");
	static JLabel l10=new JLabel("1. for i=1 to n");
	static JLabel l11=new JLabel("2.		     C(S(i))= C(S(i))+1");
	static JLabel l12=new JLabel("3. for i=2 to m");
	static JLabel l13=new JLabel("4.		     C(i)= C(i) + C(i-1)");
	static JLabel l14=new JLabel("5. for i=n down to 1");
	static JLabel l15=new JLabel("6.		     T(C(S(i)))=S(i)");
	static JLabel l16=new JLabel("7.		     C(S(i))= C(S(i))-1");
	//labels for radix sort
	static JLabel l17=new JLabel("RADIX SORT");
	static JLabel l18=new JLabel("1.  for i=1 to d(d is the number of digits of the maximum number)");
	static JLabel l19=new JLabel("2.         floor(mod(T,10^i)/10^i-1)");
	static JLabel l20=new JLabel("3.         [x,theseis]=countsort(x+1,n,10)");
	static JLabel l21=new JLabel("4.         T=T(theseis)");
	
	// labels for bubble sort
	static JLabel l22=new JLabel("BUBBLE SORT");
	static JLabel l23=new JLabel("1.	i = N, t = -1");
	static JLabel l24=new JLabel("2.	while i>=2 and t!=0");
	static JLabel l25=new JLabel("3.	        t = 0");
	static JLabel l26=new JLabel("4.            for j from 1 to i-1");
	static JLabel l27=new JLabel("5.                  if A[j]>A[j+1]");
	static JLabel l28=new JLabel("6.                  Exchange A[j] = A[j+1]");
	static JLabel l29=new JLabel("7.                  t = t+1");
	static JLabel l30=new JLabel("8.            i = i-1      ");						
								
	//labels for selection sort
	static JLabel l31=new JLabel("SELECTION SORT");
	static JLabel l32=new JLabel("1.    for j = 1 to n-1");	
	static JLabel l33=new JLabel("2.         smallest = j");				
	static JLabel l34=new JLabel("3.         for i = j + 1 to n");			
	static JLabel l35=new JLabel("4                  if A[ i ] < A[ smallest ]");			
    static JLabel l36=new JLabel("5.                      smallest = i");	
    static JLabel l37=new JLabel("6.      				                Exchange A[j] = A[smallest]");	
    //labels for merge sort
    static JLabel l38=new JLabel("MERGE SORT");
	static JLabel l39=new JLabel("1.m = 1");	
	static JLabel l40=new JLabel("2.while m < n do");				
	static JLabel l41=new JLabel("3.       i = 0");			
	static JLabel l42=new JLabel("4.       while i < n-m do");			
    static JLabel l43=new JLabel("5.           merge subarrays A[i..i+m-1] and A[i+m  .. min(i+2*m-1,n-1)] in-place.");	
    static JLabel l44=new JLabel("6.           i = i + 2 * m");
    static JLabel l45=new JLabel("7.       m = m * 2");	
    //labes for bitonic sort
    static JLabel l46=new JLabel("BITONIC SORT");	
    static JLabel l47=new JLabel("1.if n>1");	
    static JLabel l48=new JLabel("2.     m=n/2");	
    static JLabel l49=new JLabel("3.     bitonicSort(lowest,middle)");	
    static JLabel l50=new JLabel("4.     bitonicSort(lowest+middle,n-middle)");	
    static JLabel l51=new JLabel("5.     bitonicMerge(lowest,n)");	
	public SortingApplication()
	{
		super();
		
		this.setTitle("Visualization of Sorting Algorithms");
		this.setVisible(true);
		this.setBackground(Color.white);
		
		setName("Visualization application");
		
		setSize(800, 500);
		
		createButtons();
		
		createLabels();
		
		createPanels();
		
		createArray();
		
		shuffle();
		
	}
	
	
	/*
	   create all the button objects
	 */
	private void createButtons()
	{
		buttonGroups = new ButtonGroup[numButtonGroups];
		radioButtons = new JRadioButton[numButtonGroups][];
		
		radioButtons[0] = new JRadioButton[4];
		             
		radioButtons[0][0] = new JRadioButton("25");
		radioButtons[0][1] = new JRadioButton("50");
		radioButtons[0][1].setSelected(true);
		radioButtons[0][2] = new JRadioButton("100");
		radioButtons[0][3] = new JRadioButton("150");
		
		buttonGroups[0] = new ButtonGroup();
		
		for(int i=0;i<4;i++)
		{
			buttonGroups[0].add(radioButtons[0][i]);
		}
		
		radioButtons[1] = new JRadioButton[7];
        
		radioButtons[1][0] = new JRadioButton("Count");
		radioButtons[1][1] = new JRadioButton("Bubble");
		radioButtons[1][1].setSelected(true);
		radioButtons[1][2] = new JRadioButton("Selection");
		radioButtons[1][3] = new JRadioButton("Insertion");
		radioButtons[1][4] = new JRadioButton("Merge");
		radioButtons[1][5] = new JRadioButton("Radix");
		radioButtons[1][6] = new JRadioButton("Bitonic");
		buttonGroups[1] = new ButtonGroup();
		
		for(int i=0;i<7;i++)
		{
			buttonGroups[1].add(radioButtons[1][i]);
		}
		
		radioButtons[2] = new JRadioButton[3];
        
		radioButtons[2][0] = new JRadioButton("Fast");
		radioButtons[2][1] = new JRadioButton("Medium");
		radioButtons[2][1].setSelected(true);
		radioButtons[2][2] = new JRadioButton("Slow");
		
		buttonGroups[2] = new ButtonGroup();
		
		for(int i=0;i<3;i++)
		{
			buttonGroups[2].add(radioButtons[2][i]);
		}

		buttons = new JButton[numButtons];
		buttons[0] = new JButton("Sort!");
		buttons[1] = new JButton("Shuffle!");
		buttons[2] = new JButton("Step");
		buttons[2].setEnabled(false);
		buttons[3] = new JButton("Pseudocode");
		
		for(int i=0;i<buttons.length;i++)
		{
			buttons[i].addActionListener(this);
		}
	}
	//create all the labels
	
	private void createLabels()
	{
		labels = new JLabel[numLabels];
		
		for(int i=0;i<numLabels;i++)
		{
			labels[i] = new JLabel();
		}
		
		labels[0].setText("Number of Columns");
		labels[1].setText("Algorithm");
		labels[2].setText("Speed");
		labels[3].setText("Options");
		
	
	}
	
	//create all the panels and add radiobuttons
	private void createPanels()
	{
		panels = new JPanel[numPanels];
		
		for(int i=0;i<numPanels;i++)
			panels[i] = new JPanel();
		

		panels[1].setLayout(new GridLayout(buttonGroups[0].getButtonCount()+2, 1));
		panels[2].setLayout(new GridLayout(buttonGroups[1].getButtonCount()+1, 1));
		panels[3].setLayout(new GridLayout(buttonGroups[2].getButtonCount()+1, 1));
		
		for(int i=1;i<panels.length-1;i++)
		{
				panels[i].add(labels[i-1]);
				
				for(int j=0;j<radioButtons[i-1].length;j++)
					panels[i].add(radioButtons[i-1][j]);
		}
		//add shuffle button
		panels[1].add(buttons[1]);
		
		panels[4].add(labels[3]);
		panels[4].add(buttons[3]);

		
		//add everything to right panel
		panels[0].setLayout(new GridLayout(numButtonGroups,1));

		panels[0].add(panels[4]);
				
		for(int i=1;i<numPanels-1;i++)
		{
			panels[0].add(panels[i]);
		}
				
		//add everything 
		setLayout(new BorderLayout());
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(1,2));
		bottomPanel.add(buttons[0]);
		bottomPanel.add(buttons[2]);
		add(bottomPanel, BorderLayout.SOUTH);
		add(panels[0], BorderLayout.EAST);
		
		add(commentArea, BorderLayout.NORTH);

		
		drawingArea = new MyJPanel();
		
		add(drawingArea, BorderLayout.CENTER);
	}

// create the array with the numbers and shuffle it

	private void createArray()
	{
		numbers = new int[numColumns];
		 
		for (int i = 0; i<numColumns; i++)
	    {
			numbers[i] = (i+1);     
	    }
	}
	
	
	


	/*
	 Paints the graph every time a change is made or repaint() is called
	 
	 */
	public void paint( Graphics g ) 
	{
		if(drawingArea!=null)
			drawingArea.repaint();
		
		super.paint(g);

	}
	
	
	//shuffle the numbers
	private void shuffle()
	{
		Random rand = new Random();
	     
		for (int i = 0; i<numColumns; i++)
			swap(i,rand.nextInt(numColumns-1));	
	}
	
	
	/*
	  swaps values in the array
	 
	 */
	private void swap(int j, int k)
	{
		
		int temp = numbers[j];
		numbers[j] = numbers[k];
		numbers[k] = temp;
	}
	
	

	/*
	  draws the  rectangle 
	 
	 */
	private void drawPoint(int index, int height, Graphics g)
	{
		clearColumn(index,g);
		
    	g.setColor(Color.blue);
    	
    	int rectHeight = (getHeight()-heightDifference*2)/numColumns*height;
    	
			g.fillRect( (index*(drawingAreaWidth-50)/numColumns)+50, 
					getHeight()-rectHeight-heightDifference, 
					(drawingAreaWidth-50)/numColumns, 
					rectHeight);	
	
	
	}
	//turns the column into a white bar so it can be replaced with a new value
	
	private void clearColumn(int index, Graphics g)
	{
		g.setColor(Color.white);
		g.fillRect((index*(drawingAreaWidth-50)/numColumns)+50, heightDifference, 
				(drawingAreaWidth-50)/numColumns, 
				(getHeight())-heightDifference*2+5);
	}

	
	// compare j and k. if j<k swaps the values and animates that swap
	
	private boolean compareExchange(int j, int k)
	{
		if(numbers[j]<numbers[k])
		{
			swap(j,k);
			drawingArea.repaint();
			return true;
		}		
		
		else
			return false;
	}
	private boolean onlyRepaint(){
		drawingArea.repaint();
		return true;
	}
	
	/*
	  sets the delay value based on which radio button is selected
	 */
	private void getDelay()
	{
		String speed = "";
		for(int i = 0; i<radioButtons[2].length;i++)		
			if(radioButtons[2][i].isSelected())
				speed = radioButtons[2][i].getText();
		
		if(speed.equals("Fast"))
			delay = 10;
		else if(speed.equals("Medium"))
			delay = 50;
		else
			delay = 200;
	}
	/*
	  slows down animation so each swap can be seen
	 
	 */
	private void delay()
	{	
		long oldTime = System.currentTimeMillis();
		while(System.currentTimeMillis()-oldTime<delay)
		{}	
	}
	
	public int findMax(int[] a){
		int max = a[0];
		for (int i = 0;i<a.length;i++){
			if(a[i]>max)
			max=a[i];
		}
		return max;
	
	}
	
	public int findMin(int[] a){
		int min = a[0];
		for (int i = 0;i<a.length;i++){
			if(a[i]<min)
			min=a[i];
		}
		return min;
	
	}
	//create pseudo code frame
	public static void createFrame()
    {			
		
       
        EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                JFrame frame = new JFrame("Pseudocode panel");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                try 
                {
                   UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                   e.printStackTrace();
                }
                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                panel.setOpaque(true);
    			
    			
    				String whichSort = "";
    				for(int i = 0; i<radioButtons[1].length;i++)		
    					if(radioButtons[1][i].isSelected())
    						whichSort = radioButtons[1][i].getText();
    				
    				
    				if(whichSort.equals("Selection"))
    				{
    					panel.add(l31);
    	                panel.add(l32);
    	                panel.add(l33);
    	                panel.add(l34);
    	                panel.add(l35);
    	                panel.add(l36);
    	                panel.add(l37);
    				}
    				
    				else if(whichSort.equals("Bubble"))
    				{
    					panel.add(l22);
    	                panel.add(l23);
    	                panel.add(l24);
    	                panel.add(l25);
    	                panel.add(l26);
    	                panel.add(l27);
    	                panel.add(l28);
    	                panel.add(l29);
    	                panel.add(l30);
    				}
    				
    				else if(whichSort.equals("Merge"))
    				{panel.add(l38);
	                panel.add(l39);
	                panel.add(l40);
	                panel.add(l41);
	                panel.add(l42);
	                panel.add(l43);
	                panel.add(l44);
	                panel.add(l45);
    					    				}
    				
    				else if(whichSort.equals("Radix"))
    				{
    					panel.add(l17);
    	                panel.add(l18);
    	                panel.add(l19);
    	                panel.add(l20);
    	                panel.add(l21);
    					
    				}
    				
    				else if(whichSort.equals("Insertion"))
    				{
    				
    					panel.add(l);
    	                panel.add(l1);
    	                panel.add(l2);
    	                panel.add(l3);
    	                panel.add(l4);
    	                panel.add(l5);
    	                panel.add(l6);
    	                panel.add(l7);
    	                panel.add(l8);
    					
    					
    					
    					
    				}
    				
    				else if(whichSort.equals("Count"))
    				{
    					panel.add(l9);
    					panel.add(l10);
    	                panel.add(l11);
    	                panel.add(l12);
    	                panel.add(l13);
    	                panel.add(l14);
    	                panel.add(l15);
    	                panel.add(l16);
    				}
    				
    				else if (whichSort.equals("Bitonic"))
    				{
    					panel.add(l46);
    					panel.add(l47);
    	                panel.add(l48);
    	                panel.add(l49);
    	                panel.add(l50);
    	                panel.add(l51);
    					
    				}
                
                frame.getContentPane().add(BorderLayout.CENTER, panel);
                frame.pack();
                frame.setLocationByPlatform(true);
                frame.setVisible(true);
                frame.setSize(500,300);
                frame.setResizable(false);
               
            }
        });
    }
	
	// what happens when buttons are pressed
	
	public void actionPerformed(ActionEvent arg0) 
	{
		if (arg0.getActionCommand().equals("Sort!"))
		{
			buttons[0].setText("Pause");
			buttons[1].setText("Stop");
	
			getDelay();
			sort=true;
			stop = false;
			stepping = false;
			paused=false;
			drawingArea.repaint();
		}
		else if (arg0.getActionCommand().equals("Step"))
		{
			stepping = true;
			paused = false;
			buttons[0].setText("Continue");
		}
		else if (arg0.getActionCommand().equals("Shuffle!"))
		{
			stop = false;
			
			buttons[1].setText("Shuffle!");
			buttons[0].setText("Sort!");
			
			for(int i = 0; i<radioButtons[0].length;i++)
			{
				if(radioButtons[0][i].isSelected())
					numColumns = Integer.parseInt(radioButtons[0][i].getText());
			}

			
			createArray();
			shuffle();
			drawingArea.repaint();
		}
		else if (arg0.getActionCommand().equals("Pause"))
		{
			buttons[0].setText("Continue");
			buttons[2].setEnabled(true);
			paused = true;
			
		}
		else if (arg0.getActionCommand().equals("Continue"))
		{
			buttons[0].setText("Pause");
			buttons[1].setText("Stop");
			buttons[2].setEnabled(false);
			stop = false;
			stepping = false;
			paused = false;
		}
		else if (arg0.getActionCommand().equals("Stop"))
		{
			buttons[1].setText("Shuffle!");
			buttons[2].setEnabled(false);
			paused = false;
			stop = true;
			stepping=false;
			buttons[0].setText("Sort!");
			drawingArea.repaint();
		}
		else if (arg0.getActionCommand().equals("Pseudocode")){
			createFrame();
			
			
		}
		

	}
	
	
	class bitonicSortThread extends Thread implements Runnable {
		public void run(){
			bitonic=true;
			commentArea.setText("Bitonic Sort started!");
			 sort(numbers);
			 l47.setForeground(Color.BLACK);
			 l49.setForeground(Color.BLACK);
			 l50.setForeground(Color.BLACK);
			 l51.setForeground(Color.BLACK);
			 commentArea.setText("Bitonic Sort finished!");
			 bitonic=false;
			 
		}
		

		 private int[] a;
		private final static boolean ASCENDING=true, DESCENDING=false;

		public void sort(int[] a)
	    {
	        this.a=a;
	        
	        bitonicSort(0, a.length, ASCENDING);
	        
	        buttons[1].setText("Shuffle!");
			buttons[0].setText("Sort!");
			buttons[2].setEnabled(false);
	    }
		
		

	   private void bitonicSort(int lo, int n, boolean dir)
	    {	
	        if (n>1)
	        {l47.setForeground(Color.RED);
	        	l49.setForeground(Color.RED);
        	l50.setForeground(Color.RED);
        	l51.setForeground(Color.BLACK);
	        delay();
	            int m=n/2;
	            commentArea.setText("Current chosen numbers "+lo+" "+(lo+m)+"\n");
	            delay();
	            bitonicSort(lo, m, !dir);
	            
	            bitonicSort(lo+m, n-m, dir);
	            
	            bitonicMerge(lo, n, dir);
	        }
	    }

	    private void bitonicMerge(int lo, int n, boolean dir)
	    {
	        if (n>1)
	        {commentArea.setText("Creating bitonic sequences and then sorting them");
	        l47.setForeground(Color.RED);
	        l51.setForeground(Color.RED);
	        l49.setForeground(Color.BLACK);
        	l50.setForeground(Color.BLACK);
	            int m=greatestPowerOfTwoLessThan(n);
	            for (int i=lo; i<lo+n-m; i++){
	                compare(i, i+m, dir);
	                redBar1=i;
	    	        redBar2=i+m;
	            while(paused)
					try{sleep(1);}catch(Exception e){};
					if(stepping)
						paused=true;}
	            bitonicMerge(lo, m, dir);
	            bitonicMerge(lo+m, n-m, dir);
	            delay();
	            onlyRepaint();
	        }
	    }

	    private void compare(int i, int j, boolean dir)
	    {
	        if (dir==(a[i]>a[j]))
	            exchange(i, j);
	        
	    }

	    private void exchange(int i, int j)
	    {
	        int t=a[i];
	        a[i]=a[j];
	        a[j]=t;
	    }

	    private int greatestPowerOfTwoLessThan(int n)
	    {
	        int k=1;
	        while (k<n)
	            k=k<<1;
	        return k>>1;
	    }

		
	}
	
	class countSortThread extends Thread implements Runnable{
		public void run(){
			
			count=true;
			l10.setForeground(Color.RED);
			l11.setForeground(Color.RED);
			commentArea.setText("Count Sort started!\n");
			delay();
			delay();
			
			int high = findMax(numbers);
			int low = findMin(numbers);
			int range = high - low +1;
			int[] counts = new int[range]; // this will hold all possible values, from low to high
			
			
			for (int x=0;x<numbers.length;x++){
				counts[numbers[x]-low]++;
				commentArea.setText("The range between lowest and highest numbers is "+range+"\n Counting the number of times a number appears and adding up the numbers");
				delay();
				l10.setForeground(Color.BLACK);
					l11.setForeground(Color.BLACK);
		            l12.setForeground(Color.RED);
					l13.setForeground(Color.RED);
				delay();
				
			}
			
			for (int i = 1; i < range; i++){
	            counts[i] += counts[i - 1];
	            commentArea.setText("Final sort");
	            l10.setForeground(Color.BLACK);
				l11.setForeground(Color.BLACK);
				l12.setForeground(Color.BLACK);
				l13.setForeground(Color.BLACK);
				l14.setForeground(Color.RED);
				l15.setForeground(Color.RED);
				l16.setForeground(Color.RED);
				delay();
				
	            
			}
			int j = 0;
	        for (int i = 0; i < range; i++){
	            while (j < counts[i]){
	            	redBar1=i;
	                numbers[j++] = i + low;
	            }
	            while(paused)
					try{sleep(1);}catch(Exception e){};
					
				if(stop)
					return;
				
				delay();
				onlyRepaint();
				if(stepping)
					paused=true;
	        }
	        buttons[1].setText("Shuffle!");
			buttons[0].setText("Sort!");
			buttons[2].setEnabled(false); 
			count=false;
			commentArea.setText("Count Sort finished!\n");
			l10.setForeground(Color.BLACK);
			l11.setForeground(Color.BLACK);
			l12.setForeground(Color.BLACK);
			l13.setForeground(Color.BLACK);
			l14.setForeground(Color.BLACK);
			l15.setForeground(Color.BLACK);
			l16.setForeground(Color.BLACK);
		}
	}
	//insertion sort
	class insertionSortThread extends Thread implements Runnable
	{
		public void run() {
			
			
			insertion=true;
			commentArea.append("Insertion Sort started!\n");
			for(int i=0;i<numColumns;i++)
			{	commentArea.setText("Number "+numbers[i]+" is chosen to be inserted in the already sorted sequence\n");
				l1.setForeground(Color.RED);
				l2.setForeground(Color.RED);
			l3.setForeground(Color.RED);
			l4.setForeground(Color.RED);
			l8.setForeground(Color.RED);
			
				for(int j=i-1;j>-1;j--)
				{	
				
					while(paused)
						try{sleep(1);}catch(Exception e){};
						
					if(stop)
						return;
					redBar1=j+1;
					redBar2=j;
					delay();
					l5.setForeground(Color.red);
					l6.setForeground(Color.red);
					l7.setForeground(Color.red);
					commentArea.setText("Number "+numbers[i]+" is chosen to be inserted in the already sorted sequence\n");
					commentArea.append("Swapping "+numbers[j+1]+" with "+numbers[j]+"\n");
					if(!compareExchange(j+1, j))
						break;
					
					if(stepping)
						paused=true;
					l2.setForeground(Color.BLACK);
					l3.setForeground(Color.BLACK);
					l4.setForeground(Color.BLACK);
					l8.setForeground(Color.BLACK);
					
				}
				commentArea.setText(null);
				l5.setForeground(Color.BLACK);
				l6.setForeground(Color.BLACK);
				l7.setForeground(Color.BLACK);
				
			}	
			commentArea.append("Insertion Sort completed!\n");
			l1.setForeground(Color.BLACK);
			l2.setForeground(Color.BLACK);
			l3.setForeground(Color.BLACK);
			l4.setForeground(Color.BLACK);
			l5.setForeground(Color.BLACK);
			l6.setForeground(Color.BLACK);
			l7.setForeground(Color.BLACK);
			l8.setForeground(Color.BLACK);
			buttons[1].setText("Shuffle!");
			buttons[0].setText("Sort!");
			buttons[2].setEnabled(false);
		insertion=false;
		}
	}
	
	
	//bubble sort
	class bubbleSortThread extends Thread implements Runnable
	{
		public void run() {
			bubble=true;
			commentArea.setText("Bubble sort started!");
			for(int i=0;i<numColumns;i++)
				for(int j=numColumns-1;j>i;j--)
				{	l24.setForeground(Color.RED);
				l29.setForeground(Color.BLACK);
				l30.setForeground(Color.BLACK);
					while(paused)
						try{sleep(1);}catch(Exception e){};
						
					if(stop)
						return;
					commentArea.setText("Is "+ numbers[j]+" smaller than "+numbers[j-1]+"\n");
					l25.setForeground(Color.RED);
					l26.setForeground(Color.RED);
					if(numbers[j]<numbers[j-1])
					{	commentArea.append("Yes\n");
					commentArea.append("Swap "+numbers[j]+" with "+numbers[j-1]+"\n");
						l27.setForeground(Color.RED);
						l28.setForeground(Color.RED);
						l29.setForeground(Color.RED);
						l30.setForeground(Color.RED);
						delay();
					}
					else
						{commentArea.append("No\n");
						l29.setForeground(Color.RED);
						l30.setForeground(Color.RED);
						delay();
						}
					l26.setForeground(Color.BLACK);
					l27.setForeground(Color.BLACK);
					l28.setForeground(Color.BLACK);
					l29.setForeground(Color.BLACK);
					l30.setForeground(Color.BLACK);
					
					delay();
					compareExchange(j,j-1);
					
					redBar1=j-1;
					if(stepping)
						paused=true;
					
				}
			
			l23.setForeground(Color.BLACK);
			l24.setForeground(Color.BLACK);
			l25.setForeground(Color.BLACK);
			l26.setForeground(Color.BLACK);
			l27.setForeground(Color.BLACK);
			l28.setForeground(Color.BLACK);
			l29.setForeground(Color.BLACK);
			l30.setForeground(Color.BLACK);
			commentArea.setText("Bubble sort completed!");
			buttons[1].setText("Shuffle!");
			buttons[0].setText("Sort!");
			buttons[2].setEnabled(false);
			bubble=false;
		}
	}
	
	//radix sort
	class radixSortThread extends Thread implements Runnable{
		
		public void run(){
			radix=true;
			commentArea.setText("Radix Sort started!\n");
			sort(numbers);
			l18.setForeground(Color.BLACK);
	        l19.setForeground(Color.BLACK);
	        l20.setForeground(Color.BLACK);
	        l21.setForeground(Color.BLACK);
	        commentArea.setText("Radix Sort completed!\n");  
			buttons[1].setText("Shuffle!");
			buttons[0].setText("Sort!");
			buttons[2].setEnabled(false);
			
		
			
		}
		public void sort( int[] a)
	    {	l18.setForeground(Color.RED);
	        int i, m = a[0], exp = 1, n = a.length;
	        int[] b = new int[250];
	        for (i = 1; i < n; i++)
	            if (a[i] > m)
	                m = a[i];
	            
	          
	       
	        while (m / exp > 0)
	        { 	 l19.setForeground(Color.BLACK);
        	l20.setForeground(Color.BLACK);
        	l21.setForeground(Color.BLACK);
        	
        	
        	delay();
	        	for(int p=3;p>0;p=p-1){
	        	commentArea.append("Sorting numbers using their digit number "+p+"\n");
	        	
	        	l19.setForeground(Color.RED);
	        	l20.setForeground(Color.RED);
	        	l21.setForeground(Color.RED);
	            int[] bucket = new int[10];
	 
	            for (i = 0; i < n; i++){
	                bucket[(a[i] / exp) % 10]++;
	               
	            }
	            for (i = 1; i < 10; i++){
	                bucket[i] += bucket[i - 1];
	                }
	            for (i = n - 1; i >= 0; i--){
	                b[--bucket[(a[i] / exp) % 10]] = a[i];
	                
	               }
	            for (i = 0; i < n; i++){
	                a[i] = b[i];
	                delay();
					onlyRepaint();
					while(paused)
						try{sleep(1);}catch(Exception e){};
						
					if(stop)
						return;
					if(stepping)
						paused=true;}
	            exp *= 10;        
	        } 
	        }
	        
	    }    
		
	}
	
	
	
	
	
	//selection sort
	class selectionSortThread extends Thread implements Runnable
	{
		public void run() {
			selection=true;
			commentArea.append("Selection Sort started!\n");
			
			for(int i=0;i<numColumns-1;i++)
			{	l32.setForeground(Color.RED);
				l33.setForeground(Color.RED);
				int min = i;
				
				for(int j=i+1;j<numColumns;j++)
				{	l33.setForeground(Color.BLACK);
					l34.setForeground(Color.RED);
					l35.setForeground(Color.BLACK);
					l36.setForeground(Color.BLACK);
					l37.setForeground(Color.BLACK);
					
					while(paused)
						try{sleep(1);}catch(Exception e){};
						
					if(stop)
						return;
					if(stepping)
						paused=true;
					commentArea.setText("Is "+ numbers[j]+" smaller than "+numbers[min]+"\n");	
					delay();
					
					if(numbers[j]<numbers[min])
					{	l35.setForeground(Color.RED);
					l36.setForeground(Color.RED);
					l37.setForeground(Color.RED);
					delay();
						commentArea.append("Yes\n");
						commentArea.append("The current minimum is "+numbers[j]+"\n");
						
						green=j;
						min=j;
						l35.setForeground(Color.BLACK);
						l36.setForeground(Color.BLACK);
						l37.setForeground(Color.BLACK);
					}
					else
						{commentArea.append("No\n");
						commentArea.append("The current minimum is "+numbers[min]+"\n");
						}
					
				
				}
				commentArea.append("Swap "+numbers[min]+" with "+numbers[i]+"\n");
				redBar1=min;
				redBar2=i;
				compareExchange(min, i);
				
				if(stepping)
					paused=true;
				
			}
			l32.setForeground(Color.BLACK);
			l33.setForeground(Color.BLACK);
			l34.setForeground(Color.BLACK);
			l35.setForeground(Color.BLACK);
			l36.setForeground(Color.BLACK);
			l37.setForeground(Color.BLACK);
			commentArea.append("Selection Sort completed\n");
			buttons[1].setText("Shuffle!");
			buttons[0].setText("Sort!");
			buttons[2].setEnabled(false);
		}
	}
	
	/*iterative merge sort
	 */
	class mergeSortThread extends Thread implements Runnable
	{
		public void run() {
			merge=true;
			commentArea.setText("Merge sort started!");
			
			mergeSort(numbers);
			drawingArea.repaint();
			buttons[1].setText("Shuffle!");
			buttons[0].setText("Sort!");
			buttons[2].setEnabled(false);
			commentArea.setText("Merge sort completed!");
			l40.setForeground(Color.BLACK);
			l42.setForeground(Color.BLACK);
			l43.setForeground(Color.BLACK);
			l44.setForeground(Color.BLACK);
			l45.setForeground(Color.BLACK);
			merge=false;
		}
		
		
		
		
		
		public  void mergeSort(int[] array) {
			if(array.length < 2) {
				// We consider the array already sorted, no change is done
				return;
			}
			// The size of the sub-arrays . Constantly changing .
			int step = 1;
			// startL - start index for left sub-array
			// startR - start index for the right sub-array
			int startL, startR;

			while(step < array.length) {
				l40.setForeground(Color.RED);
				if(step==1){
				commentArea.setText("Merging arrays of size 1 to create sorted arrays of size 2\n");
				commentArea.append("Merging the two sorted arrays into the initial one\n");}
				if(step==2){
					commentArea.setText("Merging arrays of size 2 to create sorted arrays of size 4\n");
					commentArea.append("Merging the two sorted arrays into the initial one\n");}
				if(step==4){
					commentArea.setText("Merging arrays of size 4 to create sorted arrays of size 8\n");
					commentArea.append("Merging the two sorted arrays into the initial one\n");}
				if(step==8){
					commentArea.setText("Merging arrays of size 8 to create sorted arrays of size 16\n");
					commentArea.append("Merging the two sorted arrays into the initial one\n");}
				if(step==16){
					commentArea.setText("Merging arrays of size 16 to create sorted arrays of size 32\n");
					commentArea.append("Merging the two sorted arrays into the initial one\n");}
				if(step==32){
					commentArea.setText("Merging arrays of size 32 to create sorted arrays of size 64\n");
					commentArea.append("Merging the two sorted arrays into the initial one\n");}
				if(step==64){
					commentArea.setText("Merging arrays of size 64 to create sorted arrays of size 128\n");
					commentArea.append("Merging the two sorted arrays into the initial one\n");}
				if(step==128){
					commentArea.setText("Merging arrays of size 128 to create sorted arrays of size 256\n");
					commentArea.append("Merging the two sorted arrays into the initial one\n");}
				startL = 0;
				startR = step;
				
				while(startR + step <= array.length) {
					l42.setForeground(Color.RED);
					l43.setForeground(Color.BLACK);
					l44.setForeground(Color.BLACK);
					l45.setForeground(Color.BLACK);
					mergeArrays(array, startL, startL + step, startR, startR + step);
					
					startL = startR + step;
					startR = startL + step;
					redBar1=startL;
					
				}
				
				if(startR < array.length) {
					
					
					mergeArrays(array, startL, startL + step, startR, array.length);
					
				}
				step *= 2;
				l44.setForeground(Color.RED);
				
				delay();
			}
		}

		// Merge to already sorted blocks
		public void mergeArrays(int[] array, int startL, int stopL,
			int startR, int stopR) {
			l43.setForeground(Color.RED);
			delay();
			// Additional arrays needed for merging
			int[] right = new int[stopR - startR + 1];
			int[] left = new int[stopL - startL + 1];
			
			
		
			
			// Copy the elements to the additional arrays
			for(int i = 0, k = startR; i < (right.length - 1); ++i, ++k) {
				
				
				right[i] = array[k];
			}
			for(int i = 0, k = startL; i < (left.length - 1); ++i, ++k) {
				left[i] = array[k];
				
			}

			// Adding sentinel values
			right[right.length-1] = Integer.MAX_VALUE;
			left[left.length-1] = Integer.MAX_VALUE;
			
			// Merging the two sorted arrays into the initial one
			for(int k = startL, m = 0, n = 0; k < stopR; ++k) {
				redBar1=k;
				
				
				
				if(left[m] <= right[n]) {
					array[k] = left[m];
					
					delay();
					onlyRepaint();
					while(paused)
						try{sleep(1);}catch(Exception e){};
						
					if(stop)
						return;
					if(stepping)
						paused=true;
					
					m++;
				}
				else {
					
					
					
					array[k] = right[n];
					
					delay();
					onlyRepaint();
					while(paused)
						try{sleep(1);}catch(Exception e){};
						
					if(stop)
						return;
					if(stepping)
						paused=true;
					
					n++;
				}
			}
			
		}
		
		
		
		
	}
	
	
	
	
	class MyJPanel extends JPanel
	{
		public MyJPanel()
		{
			super();
		}
		
		
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			
			g.setColor(Color.white);
			
			g.clearRect(5,5,this.getWidth()-10, this.getHeight()-10);

			g.setColor(Color.black);
			//draws border
			g.drawRect(5, 5, this.getWidth()-10, this.getHeight()-10);
			
			if(numbers!=null)
			{
				int barWidth = (getWidth()-20)/numColumns;
				
				for(int j=0;j<numColumns;j++)
				{
					g.setColor(Color.white);
					g.fillRect(j*barWidth + 15, 10, 
							barWidth, 
							getHeight()-20);
					
			    	g.setColor(Color.black);
			    	
			    	int rectHeight = (int)((double)getHeight()-20)/numColumns*numbers[j];
			    	
				
						g.fillRect( j*barWidth + 15, 
								getHeight()-rectHeight-20, 
								barWidth, 
								rectHeight);	
				
	
				}
				//trying to paint the compared numbers with different color
				if(selection){
				int rectHeight = (int)((double)getHeight()-20)/numColumns*numbers[redBar1];
				g.setColor(Color.red);
				g.fillRect( redBar1*barWidth + 15, 
						getHeight()-rectHeight-20, 
						barWidth, 
						rectHeight);	
				int rectHeight2 = (int)((double)getHeight()-20)/numColumns*numbers[redBar2];
				g.setColor(Color.red);
				g.fillRect( redBar2*barWidth + 15, 
						getHeight()-rectHeight2-20, 
						barWidth, 
						rectHeight2);	
				
				}
				if(insertion){
					int rectHeight = (int)((double)getHeight()-20)/numColumns*numbers[redBar1];
					g.setColor(Color.red);
					g.fillRect( redBar1*barWidth + 15, 
							getHeight()-rectHeight-20, 
							barWidth, 
							rectHeight);	
					int rectHeight2 = (int)((double)getHeight()-20)/numColumns*numbers[redBar2];
					g.setColor(Color.red);
					g.fillRect( redBar2*barWidth + 15, 
							getHeight()-rectHeight2-20, 
							barWidth, 
							rectHeight2);	
					for(int t=0;t<redBar1;t++){
						int rectHeight3 = (int)((double)getHeight()-20)/numColumns*numbers[t];
						g.setColor(Color.green);
						g.fillRect( t*barWidth + 15, 
								getHeight()-rectHeight3-20, 
								barWidth, 
								rectHeight3);	
					}
				}
				if(bubble){
					int rectHeight = (int)((double)getHeight()-20)/numColumns*numbers[redBar1];
					g.setColor(Color.red);
					g.fillRect( redBar1*barWidth + 15, 
							getHeight()-rectHeight-20, 
							barWidth, 
							rectHeight);	
					}
				if(merge){
					
					
					
					int rectHeight = (int)((double)getHeight()-20)/numColumns*numbers[redBar1];
					g.setColor(Color.red);
					g.fillRect( redBar1*barWidth + 15, 
							getHeight()-rectHeight-20, 
							barWidth, 
							rectHeight);	
						
					
					
				}
				if(bitonic){
					int rectHeight = (int)((double)getHeight()-20)/numColumns*numbers[redBar1];
					g.setColor(Color.red);
					g.fillRect( redBar1*barWidth + 15, 
							getHeight()-rectHeight-20, 
							barWidth, 
							rectHeight);	
					int rectHeight2 = (int)((double)getHeight()-20)/numColumns*numbers[redBar2];
					g.setColor(Color.red);
					g.fillRect( redBar2*barWidth + 15, 
							getHeight()-rectHeight2-20, 
							barWidth, 
							rectHeight2);	
				}
				if(radix){
					
				}
				if(count){
					for(int t=0;t<redBar1;t++){
						int rectHeight3 = (int)((double)getHeight()-20)/numColumns*numbers[t];
						g.setColor(Color.red);
						g.fillRect( t*barWidth + 15, 
								getHeight()-rectHeight3-20, 
								barWidth, 
								rectHeight3);	
					}
				}
			}
			
			if(sort)
			{
				String whichSort = "";
				for(int i = 0; i<radioButtons[1].length;i++)		
					if(radioButtons[1][i].isSelected())
						whichSort = radioButtons[1][i].getText();
				
				//if selection sort has been chosen call it
				if(whichSort.equals("Selection"))
				{
					Thread t = new selectionSortThread();
					t.start();
					
				}
				//if bubble sort has been chosen call it
				else if(whichSort.equals("Bubble"))
				{
					Thread t = new bubbleSortThread();
					t.start();
					
				}
				//if merge sort has been chose call it
				else if(whichSort.equals("Merge"))
				{
					Thread t = new mergeSortThread();
					t.start();
					
				}
				//if radixsort has been chosen call it
				else if(whichSort.equals("Radix"))
				{
					Thread t = new radixSortThread();
					t.start();
					
				}
				//if insertion sort has been chosen call it
				else if(whichSort.equals("Insertion"))
				{
				
					
					Thread t = new insertionSortThread();
					t.start();
					
					
				}
				//if count sort has been chosen call it
				else if(whichSort.equals("Count"))
				{
					Thread t = new countSortThread();
					t.start();
					
					
				}
				//if bitonic sort has been chosen call it
				else if (whichSort.equals("Bitonic"))
				{
					Thread t = new bitonicSortThread();
					t.start();
				}
				sort = false;
			}	
		}
	}
}

