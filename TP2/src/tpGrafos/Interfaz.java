package tpGrafos;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.MapPolygonImpl;
import org.openstreetmap.gui.jmapviewer.interfaces.ICoordinate;
import org.openstreetmap.gui.jmapviewer.interfaces.MapPolygon;

import Animacion.Animacion;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
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
	ArrayList <JLabel> nodos=new ArrayList <JLabel>();
	private Integer ub=0;
	JLabel rel=new JLabel("X"+" A "+"X");
	Color color=new Color (151, 15, 207  );
	JMapViewer map=new JMapViewer();
			
	
	
	

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
	 
	
		label.setSize(70, 70);
		label.setLocation(p);

		Image im=new ImageIcon(iconos[id]).getImage();
		label.setIcon(new ImageIcon( im.getScaledInstance(70, 70, Image.SCALE_SMOOTH)));
		label.setText(id+"");
		
		return label;
		
	}
	
	private void addArista(Point desde,Point hasta)
	{
		desde=new Point((int )desde.getX()+10,(int)desde.getY()+10);
		hasta=new Point((int )hasta.getX()+10,(int)hasta.getY()+10);
		
		Coordinate cordinada1=map.getPosition(desde.getLocation());
		Coordinate cordinada2=map.getPosition(hasta.getLocation());
		map.addMapPolygon(new MapPolygonImpl(cordinada1,cordinada2,cordinada1));
		map.repaint();
	}
	
	private void addDot(JMapViewer map,MouseEvent e) 
	{
		
		
		
			    Coordinate markeradd = map.getPosition(e.getPoint());
			    
				//String nombre = JOptionPane.showInputDialog("Nombre: ");
			    map.addMapMarker(new MapMarkerDot("", markeradd));
			
	
		
	}
	
	public JLabel generarNodo(int id,int ub, Point p) 
	{
		
		JLabel label=new JLabel(ub+"");
	
		
		label.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseReleased(MouseEvent e) 
			{
				String name=label.getText();
				Integer num=Integer.parseInt(name);

				
				
				if (relaciones.size()==2) 
				{
					relaciones.clear();
					relaciones.add(num);
					label.repaint();
					//cambiarLabel(rel,relaciones.get(0),0);
				}
					
				else 
				{
					if (relaciones.isEmpty() || relaciones.get(0)!=num)
		
						relaciones.add(num);
				label.repaint();
				//nodos.add(label);
				}
				
				
				if (relaciones.size()==1) 
				{
					
					cambiarLabel(rel,relaciones.get(0).toString(),"X");
					
					label.repaint();
				}
					

				 
				 if (relaciones.size()==2) 
				 {
					
				 
				 cambiarLabel(rel,relaciones.get(0).toString(),relaciones.get(1).toString());
				 label.repaint();
				 
				 String option=JOptionPane.showInputDialog(null,"Crear arista entre: "+nodos.get(relaciones.get(0)).getText()+ " y "+nodos.get(relaciones.get(1)).getText());
				
				 boolean continuar=true;
				 if (option!=null)
				 {
					
					 Integer c=null;
				 try 
				 {
				 c=Integer.parseInt(option);
				 }catch (Exception err)
				 {
					 JOptionPane.showConfirmDialog(map,"Error al parsear datos del tipo String",
								"Error",JOptionPane.YES_NO_OPTION,JOptionPane.ERROR_MESSAGE); 
					 continuar=false;
					 
				 } finally 
				 {
					 if (continuar)
					 {
						 JLabel desde=nodos.get(relaciones.get(0));
					JLabel hasta=nodos.get(relaciones.get(1));
				 addArista(hasta.getLocation(),desde.getLocation());
				 map.repaint();	 
					 }
					 
				 }
					
				 
				
				 
				
				 }
				
				
				
				 }
					
				
			}

			
		});
		
		label.setSize(23, 23);
		label.setLocation(p);
		//label.setBorder(new LineBorder(Color.BLACK));
		

		Image im=new ImageIcon(iconos[id]).getImage();
		label.setIcon(new ImageIcon( im.getScaledInstance(23, 23, Image.SCALE_SMOOTH)));
		JLabel numero=new JLabel(ub+"");
		numero.setFont(new Font ("Tahoma", Font.PLAIN, 13));
	
		label.setLayout(new FlowLayout(FlowLayout.CENTER));
		label.add(numero);
		label.repaint();
		numero.repaint();
		
		return label;
		
	}
	
	private void cambiarLabel(JLabel labe)
	{
		labe.setText(0+" A "+ 0);
	}
	private void cambiarLabel(JLabel label,String desde,String hasta) 
	{
		label.setText(desde+" A "+ hasta);
		
	}
	
	//Utilizada como recurso grafico para cambiar imagen de un drop menu
	public void im_up(JLabel icono)
	{
icono.setIcon(new ImageIcon("subir.png"));
	}
	
	//Utilizada como recurso grafico para cambiar imagen de un drop menu
	public void im_down(JLabel icono)
	{
icono.setIcon(new ImageIcon("bajar.png"));
	}

	private void initialize() 
	{
		frame = new JFrame();
		frame.setBounds(100, 100, 1600, 900);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		frame.setFocusable(true);
		rel.setLocation(0, 200);
		rel.setFont(new Font("Tahoma", Font.PLAIN, 26));
		rel.setSize(250,100);
		rel.setVisible(true);
		frame.getContentPane().add(rel);
	
		JPanel contenedormapa=new JPanel();
		//contenedormapa.setLayout(new FlowLayout(FlowLayout.LEADING));
		contenedormapa.setLayout(null);
		contenedormapa.setBounds(500,0,1100,1000);
		contenedormapa.setBorder(new LineBorder(Color.blue));
		
		//JMapViewer map=new JMapViewer();
		map.setBounds(0,0,1300,1000);
		map.setDisplayPositionByLatLon(-40, -59, 5);
		map.setZoomContolsVisible(false);
		
		contenedormapa.add(map);
		frame.add(contenedormapa);

		JPanel dropmenu= new JPanel();
		dropmenu.setLayout(null);
		
		dropmenu.setLocation(0,0);
		dropmenu.setBorder( new RoundedBorder(10,color));
		dropmenu.setSize(350,130);
		
		JLabel icono = new JLabel("");
		icono.setBounds(165, 114, 30, 15);
		icono.setBackground(Color.WHITE);
		icono.setIcon(new ImageIcon("subir.png"));
		icono.setToolTipText("Desplegar drop menu");
		
		
		dropmenu.add(icono);
		
		frame.add(dropmenu);
		
		Point p1=new Point(10, 36);
		Point p11=new Point(10,18);
		Point p2=new Point(130, 36);
		Point p22=new Point (03,18);
		Point p3=new Point(250, 36);
		Point p33=new Point (22,18);
		
		JLabel productor = generarIcono(0, p1);
		
		JLabel consumidor = generarIcono(1, p2);
	
		JLabel paso = generarIcono(2, p3);
		
		
		productor.setLayout(null);
		consumidor.setLayout(null);
		paso.setLayout(null);
		
		
		JLabel productorTitle = new JLabel("Productor");
		productorTitle.setFont(new Font("Tahoma", Font.PLAIN, 12));
		productorTitle.setSize(100,30);
		productorTitle.setLocation(p11);
		
		productor.add(productorTitle);
		
		JLabel consumidorTitle = new JLabel("Consumidor");
		consumidorTitle.setFont(new Font("Tahoma", Font.PLAIN, 12));
		consumidorTitle.setSize(100,30);
		consumidorTitle.setLocation(p22);
		consumidor.add(consumidorTitle);
		
		JLabel pasoTitle = new JLabel("Paso");
		pasoTitle.setFont(new Font("Tahoma", Font.PLAIN, 12));
		pasoTitle.setSize(100,30);
		pasoTitle.setLocation(p33);
		paso.add(pasoTitle);
		
		dropmenu.add(paso);
		dropmenu.add(consumidor);
		dropmenu.add(productor);
		
	
		
		//JLabel consumidor = generarIcono(1, p2);
		//JLabel paso = generarIcono(2, p3);
		
		
		select.add(paso);
		select.add(consumidor);
		select.add(productor);
		
		
		icono.addMouseListener(new MouseAdapter() 
		{

			public void mouseReleased(MouseEvent e)
			{
		int posicion=dropmenu.getY();
		if (posicion>=0)
		{
			
			
			Animacion.subir(0, -115, 2,1,dropmenu);
			dropmenu.setBorder(null);
			icono.setToolTipText("Desplegar drop menu");
			im_down(icono);
		}else
		{
			
			Animacion.bajar(-100, 0, 2,1, dropmenu);
			dropmenu.setBorder(new RoundedBorder(10, color));
			im_up(icono);
			icono.setToolTipText("Retraer drop menu");
			
		}

	}
		});

			map.addMouseListener(new MouseAdapter() 
			{

		
			public void mouseReleased(MouseEvent e)
			{
				
				
				if (e.getButton()==MouseEvent.BUTTON1)
				{
					
				Point p=new Point();
				p.setLocation(e.getX()-10, e.getY()-10);
				
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
				map.add(l);
				nodos.add(l);
				map.repaint();
				addDot(map,e);
				addDot(map,e);
					ub++;
					
					for (JLabel lab:nodos) 
					{
						System.out.print(lab.getText()+" ");
					}
					System.out.println("");
				}
				
				
			}
				}

			
		
			
		});
		
		
		
		
		
	}
}
