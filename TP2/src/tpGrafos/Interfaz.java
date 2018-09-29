package tpGrafos;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Point;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.LinkedList;

public class Interfaz 
{

	private JFrame frame;
	private String []  iconos= {"productor.png","consumidor.png","paso.png"};
	private String []  nombres= {"productor","consumidor","paso"};
	private String selActual="";
	LinkedList <JLabel> select=new LinkedList<JLabel>();
	ArrayList <Integer> relaciones=new ArrayList <Integer>();
	private Integer ub=0;
	JLabel rel=new JLabel(0+" A "+0);
	
	
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Interfaz window = new Interfaz();
					
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
	public Interfaz() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	
	public JLabel generarIcono(int id,Point p) 
	{
	 
		JLabel label=new JLabel(id+"");
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) 
			{
				String name=label.getText();
				Integer num=Integer.parseInt(name);
				selActual=nombres[num];
				
				JOptionPane.showConfirmDialog(frame,selActual,
						selActual,JOptionPane.YES_NO_OPTION,JOptionPane.ERROR_MESSAGE);
			}
		});
	 
	
		label.setSize(30, 30);
		label.setLocation(p);

		Image im=new ImageIcon(iconos[id]).getImage();
		label.setIcon(new ImageIcon( im.getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
		
		return label;
		
	}
	
	public JLabel generarNodo(int id,int ub, Point p) 
	{
	 
		JLabel label=new JLabel(ub+"");
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) 
			{
				String name=label.getText();
				Integer num=Integer.parseInt(name);

				if (relaciones.size()==0)
					relaciones.clear();
				else 
				relaciones.add(num);
				
				
				cambiarLabel(rel,relaciones.get(0),relaciones.get(1));
				
			}

			
		});
	 
	
		label.setSize(30, 30);
		label.setLocation(p);

		Image im=new ImageIcon(iconos[id]).getImage();
		label.setIcon(new ImageIcon( im.getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
		
		return label;
		
	}
	private void cambiarLabel(JLabel label,Integer desde,Integer hasta) 
	{
		label.setText(desde+" A "+ hasta);
		
	}
	private void initialize() 
	{
		frame = new JFrame();
		frame.setBounds(100, 100, 1600, 900);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		frame.setFocusable(true);
		rel.setLocation(1000, 36);
		rel.setVisible(true);
		frame.getContentPane().add(rel);
	
		
		Point p1=new Point(71, 36);
		Point p2=new Point(586, 36);
		Point p3=new Point(977, 36);
		
		
		JLabel productor = generarIcono(0, p1);
		JLabel consumidor = generarIcono(1, p2);
		JLabel paso = generarIcono(2, p3);
		
		frame.add(paso);
		frame.add(consumidor);
		frame.add(productor);
		select.add(paso);
		select.add(consumidor);
		select.add(productor);
		
		
		
		
			frame.addMouseListener(new MouseAdapter() 
		{

			public void mouseReleased(MouseEvent e)
			{
				Point p=new Point();
				p.setLocation(e.getX()-20, e.getY()-40);
				
				int i=-1;
				if (selActual.equals("productor"))
					i=0;
				if (selActual.equals("consumidor"))
					i=1;
				if (selActual.equals("paso"))
				 
					i=2;
				
				if (i!=-1)
				{
				JLabel l=generarNodo(i,ub,p);
				frame.add(l);
				frame.repaint();
					ub++;
				}
				
				
			}
			
		});
		
		
		
		
		
	}
}
