package tpGrafos;


import java.awt.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import org.openstreetmap.gui.jmapviewer.*;

import Animacion.Animacion;

import java.awt.event.*;
import java.util.ArrayList;


public class Interfaz 
{

	private JFrame frame;
	private String []  iconos= {"productor.png","consumidor.png","paso.png"};
	private String []  nombres= {"productor","consumidor","paso"};
	private String selActual="";
	private ArrayList <Integer> relaciones=new ArrayList <Integer>();
	private ArrayList <JLabel> nodos=new ArrayList <JLabel>();
	private ArrayList <JLabel> aristas=new ArrayList<JLabel>();
	private ArrayList <JLabel> consumidores=new ArrayList<JLabel>();
	private ArrayList <JLabel> productores=new ArrayList<JLabel>();
	private Arista mapArista=new Arista();
	private Integer ub=1;
	private JLabel rel=new JLabel("Crear Arista entre: \n "+"X"+" A "+"X");
	private Color color=new Color (151, 15, 207);
	private JMapViewer map=new JMapViewer();
    private StringBuilder consoleOut=new StringBuilder("Bienvenido al sistema de planificacion de gasoductos: \n");
    private JTextArea ta = new JTextArea("",33,42);
    private boolean finNodos=false;
    private boolean finAristas=false;
    private Red gasoducto=new Red();
 
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

	public Interfaz() {
		initialize();
	}

	public void repintarNodos()
	{
		for (JLabel nodo : nodos)
		{
			nodo.updateUI();
		}
	}
	private void removeActionsNodes()
	{
	for (JLabel l:nodos)
	{
		MouseListener[] m=l.getMouseListeners();
		for (MouseListener mo : m)
		l.removeMouseListener(mo);
	}
	
		
	}
	public JLabel generarIcono(int id,Point p) 
	{
	 
		JLabel label=new JLabel(id+"");
		Image im=new ImageIcon(iconos[id]).getImage();
		ImageIcon icon=new ImageIcon( im.getScaledInstance(70, 70, Image.SCALE_SMOOTH));
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) 
			{
				String name=label.getText();
				Integer num=Integer.parseInt(name);
				selActual=nombres[num];
				
				ImageIcon icon2=new ImageIcon( im.getScaledInstance(30, 30, Image.SCALE_SMOOTH));
				JOptionPane.showMessageDialog(null,"Se selecciono el nodo "+selActual.toUpperCase(),
						"Seleccion tipo de Nodo",JOptionPane.CLOSED_OPTION,icon2);
				
				addConsoleLine("Se selecciono el Nodo "+selActual.toUpperCase());
				
			}
		});
	 
	
		label.setSize(70, 70);
		label.setLocation(p);
		label.setIcon(icon);
		label.setText(id+"");
		
		return label;
		
	}
	private boolean parseInteger(String op) 
	 {
		 boolean ret=true;
		 try 
		 {
			 
			 Integer.parseInt(op);
		 }catch(Exception e) 
		 {
			 ret=false;
		 }
		 return ret;
	 }
	

	private void addArista(Integer p,Point desde,Point hasta,Integer d,Integer h)
	{
		
		desde=new Point((int )desde.getX()+10,(int)desde.getY()+10);
		hasta=new Point((int )hasta.getX()+10,(int)hasta.getY()+10);
		
		int mediox=(int) ((desde.getX()+hasta.getX())/2)-12;
		int medioy=(int) ((desde.getY()+hasta.getY())/2)-12;
		Point peso=new Point (mediox,medioy);
		
		JLabel label=new JLabel(p.toString(),SwingConstants.CENTER)
		{

			private static final long serialVersionUID = 1L;
			public Point getToolTipLocation(MouseEvent event)
		      {
				
		        return new Point(-5, -20);
		        
		      }

		 };
		    
		 
		 
		ToolTipManager.sharedInstance().setInitialDelay(0);
		
		label.setLocation(peso);
		label.setSize(label.getText().length()*12+5,20);
		label.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label.setBackground(Color.white);
		label.setOpaque(true);
		label.setBorder(new RoundedBorder(5,Color.BLUE));
		label.setToolTipText(d.toString()+" --> "+h.toString());
		
		Coordinate cordinada1=map.getPosition(desde.getLocation());
		Coordinate cordinada2=map.getPosition(hasta.getLocation());
		MapPolygonImpl polygon=new MapPolygonImpl(cordinada1,cordinada2,cordinada1);
		polygon.setColor(Color.GRAY);
		
		
		map.addMapPolygon(polygon);
		map.add(label);
		map.repaint();
		map.add(label);
		aristas.add(label);
		mapArista.imprimir();
		
		addConsoleLine("Se agrego una arista entre nodo: "+d+ " Y "+h+" con peso de: "+p);
	}
	
	public void changeArista(Integer d,Integer h) 
	{
		Point p=encontrarPuntoMedio(d,h);
		
		 for (JLabel nodo: aristas)
		  {
			  if (nodo.getLocation().equals(p)) 
			  {
				   nodo.setToolTipText(d+"<--->"+h);
				 
			  }

		  }
	}
	
	public void changeArista(String option,Integer d,Integer h) 
	{
		Point p=encontrarPuntoMedio(d,h);
		 for (JLabel nodo: aristas)
		  {
			  if (nodo.getLocation().equals(p)) 
			  {
				  
				  nodo.setText(option);
				  nodo.setSize(option.length()*12+5, 20);
				  nodo.updateUI();
			  }

		  }
	}
	
	public Point encontrarPuntoMedio(Integer d,Integer h) 
	{
		 JLabel desde=nodos.get(d);
		  JLabel hasta=nodos.get(h);
			
			int mediox=(int) ((desde.getX()+hasta.getX())/2)-2;
			int medioy=(int) ((desde.getY()+hasta.getY())/2)-2;
			Point punto=new Point (mediox,medioy);
			return punto;
		
	}
	
	public void NegativeWeightException(JLabel lab) 
	{
		JOptionPane.showMessageDialog(lab, "No se pueden agregar pesos negativos", "Negative weight Exception", JOptionPane.ERROR_MESSAGE);
		addConsoleLine("\n>> Negative weight Exception <<");
		addConsoleLine("No se pueden agregar pesos negativos");
	}
	
	private void ParseIntException(JLabel label) 
	{
		JOptionPane.showMessageDialog(label, "No se pueden agregar valores que no sean numericos", "Parse to Integer Exception", JOptionPane.ERROR_MESSAGE);
		addConsoleLine("\n >> Parse to Integer Exception <<");
		addConsoleLine("No se pueden agregar valores que no sean numericos");
		
	}
	
	public JLabel generarNodo(int id,int ub, Point p) 
	{
		
		JLabel label=new JLabel(ub+"");
	
		
		label.addMouseListener(new MouseAdapter() 
		{
			
			@Override
			public void mouseReleased(MouseEvent e) 
			{
				mapArista.matrizDePesos();
				
				if (selActual!="") 
				{
					
					JOptionPane.showMessageDialog(frame, "Para agregar aristas presione el boton finalizar agregado de nodos", "Illegal action", JOptionPane.INFORMATION_MESSAGE);
				}
				if (selActual=="")
				{
					String name=label.getText();
					Integer num=Integer.parseInt(name);
				
		
					if (relaciones.size()==2) 
					{
						relaciones.clear();
						relaciones.add(num);	
					}
						
					else 
					{
						if (relaciones.isEmpty() || relaciones.get(0)!=num)
							relaciones.add(num);
					}
					
					if (relaciones.size()==1) 
					{
						cambiarLabel(rel,relaciones.get(0).toString(),"X");
					}
					
					 if (relaciones.size()==2) 
					 {
						 
					 Integer d=relaciones.get(0);
					 Integer h=relaciones.get(1);
					 cambiarLabel(rel,d.toString(),h.toString());
					 
					 
					 Image im=new ImageIcon("arista.png").getImage();
					 ImageIcon icon=new ImageIcon(im.getScaledInstance(120, 40, Image.SCALE_SMOOTH));
					 String option="";
					 boolean continuar=true;
					 
					 
					 if (mapArista.existeArista(d, h)) 
					 {
						 
						  option=(String) JOptionPane.showInputDialog(null,"Desea cambiar el valor de la arista "+nodos.get(d).getText()+ " y "+nodos.get(h).getText(),
									"Cambiar Valores",JOptionPane.QUESTION_MESSAGE,icon, null, null);
						  continuar=false;
						  
						  if (option!=null )
						  {
							  if (parseInteger(option))
							  {
								  if(Integer.parseInt(option)>0) 
							  {
								  mapArista.addArista(d, h, Integer.parseInt(option));
								  changeArista(option,d,h);
								  addConsoleLine("Se cambio el peso de la arista entre : "+d+" y "+h+" a "+option);
							  
							  if (mapArista.existeReciproca(d, h)) 
							  {
								  mapArista.addArista(h, d, Integer.parseInt(option));
								  mapArista.imprimir();
							  }
							  
							  }  
							  else 
							  {
									NegativeWeightException(label);
							  }  
								  
							  }
							  else 
								  {
									ParseIntException(label);
								  }
							
								  
							  }
						
					 }
					
					if (!mapArista.existeArista(d, h) && !(mapArista.existeReciproca(d, h))) 
					{
					
					option=(String) JOptionPane.showInputDialog(null,"Crear arista entre: "+nodos.get(d).getText()+" y "+nodos.get(h).getText(),
							"Crear Arista",JOptionPane.QUESTION_MESSAGE,icon, null, null);
					if (option!=null)
					{
						if (parseInteger(option)) 
						{
							if (Integer.parseInt(option)<0)
						{
							NegativeWeightException(label);
							continuar=false;
						}else 
						mapArista.addArista(d, h, Integer.parseInt(option));
						}
						
						else 
						{
							ParseIntException(label);
						}
					}
						
					}
					
					if (!mapArista.existeArista(d, h) && mapArista.existeReciproca(d, h)) 
					{
						continuar=false;
						int i=JOptionPane.showConfirmDialog(label, "Desea agregar una de vuelta entre "+d+" y "+h);
						if (i==0)
						{
							
							Integer r=mapArista.getPeso(h, d);
							
							mapArista.addArista(d,h, r);
							changeArista(d,h);
							addConsoleLine("Se agrego una arista de vuelta entre "+d+" y "+h+" con peso "+r);
						}
						mapArista.imprimir();
						
					}
				
					 if (option!=null )
					 {
						
						 Integer c=null;
					 try 
					 {
					 c=Integer.parseInt(option);
					 
					 
					 }catch (Exception err)
					 {
						 
						 continuar=false;
						 
					 } finally 
					 {
						 if (continuar)
						 {
							 JLabel desde=nodos.get(d);
							 JLabel hasta=nodos.get(h);
							 addArista(c,hasta.getLocation(),desde.getLocation(),d,h);
							 map.repaint();	 
							 selActual="";
						 }
						 
					 }
					 }
					 }
					 
					
						
					
				}

				}

			

				
			
		});
		
		boolean continua=true;
		if (selActual=="productor") 
		{
	String op=null;
			
			op=JOptionPane.showInputDialog(null,"Agregar la oferta del productor: ",
					"Agregar Oferta",JOptionPane.QUESTION_MESSAGE);
			if (op==null ) 
			{
				addConsoleLine("Se cancelo el agregado del nodo productor ");
				continua=false;
			}
			else  
			{
				if (op.equals("")) 
				{
					addConsoleLine("Sin datos: el agregado del nodo no puede continuar.");
				continua=false;
				}
				
			}
			
			if (continua) 
			{
			try 
			{
				Integer e=Integer.parseInt(op);
				
				if (e<0)
				{
					NegativeWeightException(label);
					continua=false;
				}
			}catch (Exception e ) 
			{
				ParseIntException(label);
				continua=false;
			}
			
			label.setName(op);
			label.setToolTipText(op);
			productores.add(label);
			}
			
		}
		
		if (selActual=="consumidor") 
		{
			String op=null;
			
			op=JOptionPane.showInputDialog(null,"Agregar la demanda del consumidor: ",
					"Agregar Oferta",JOptionPane.QUESTION_MESSAGE);
			if (op==null) 
			{
				addConsoleLine("Se cancelo el agregado del nodo consumidor ");
				continua=false;
			}
			else 
			{
				if (op.equals("")) 
				{
					addConsoleLine("Sin datos: el agregado del nodo no puede continuar.");
				continua=false;
				}
				
			}
			
			
			try 
			{
				Integer e=Integer.parseInt(op);
				
				if (e<0)
				{
					NegativeWeightException(label);
					continua=false;
				}
			}catch (Exception e ) 
			{
				ParseIntException(label);
				continua=false;
			}
			
			
			
			
			if (continua) 
			{
			label.setName(op);
			label.setToolTipText(op);
			consumidores.add(label);
			}
			
			
			
		}
		
		label.setSize(23, 23);
		label.setLocation(p);
		Image im=new ImageIcon(iconos[id]).getImage();
		label.setIcon(new ImageIcon( im.getScaledInstance(23, 23, Image.SCALE_SMOOTH)));
		JLabel numero=new JLabel(ub+"");
		numero.setFont(new Font ("Tahoma", Font.PLAIN, 13));
	
		label.setLayout(new FlowLayout(FlowLayout.CENTER));
		label.add(numero);
		
			repintarNodos();
		
			if (continua)
		return label;
			else return null;
		
	}

	
	public void clasificar(JLabel label)
	{
		if (selActual=="productor")
			productores.add(label);
		if (selActual=="consumidor")
			consumidores.add(label);

		
	}
	public void addConsoleLine(String in) 
	{
		consoleOut.append(in);
		consoleOut.append("\n");
		ta.setText(consoleOut.toString());
		
	}
	private void cambiarLabel(JLabel label,String desde,String hasta) 
	{
		label.setText("Crear Arista entre:\n "+desde+" A "+ hasta);	
	}

	public void im_up(JLabel icono)
	{
icono.setIcon(new ImageIcon("subir.png"));
	}
	
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
		mapArista.matrizDePesos();
		frame.setFocusable(true);
		rel.setLocation(0, 200);
		rel.setFont(new Font("Tahoma", Font.PLAIN, 26));
		rel.setSize(350,100);
		rel.setVisible(true);
		
		frame.getContentPane().add(rel);
		
		JLabel s=new JLabel(0+"");
		nodos.add(s);
		map.add(s);
		mapArista.addNodo();

		JPanel consola=new JPanel();
		consola.setLayout(new FlowLayout(FlowLayout.LEFT));
		consola.setBounds(0, 300, 500, 600);
		consola.setBorder(new TitledBorder("Console: "));
		
	  
		ta.setFocusable(false);
	     ta.setLineWrap(true);
		 ta.setSize(500, 500);
		 ta.setLocation(0,300);
		 ta.setText(consoleOut.toString());
		 		
		JScrollPane	scroll = new JScrollPane(ta);
		scroll.setBounds(0, 0, 10, 100);
			scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			
			
		consola.add("East",scroll);
		JButton finalizarNodos=new JButton("Finalizar Agregado de Nodos");
		finalizarNodos.setBounds(10,150 , 200, 30);
		JButton finalizarAristas=new JButton("Finalizar Agregado de Aristas");
		finalizarAristas.setBounds(10,185 , 200, 30);
		JButton flujoMAX=new JButton("Calcular Flujo Maximo");
		flujoMAX.setBounds(-300,167 , 200, 30);
		finalizarNodos.setBorder(new RoundedBorder(10,Color.black));
		finalizarAristas.setBorder(new RoundedBorder(10,Color.black));
		flujoMAX.setBorder(new RoundedBorder(10,Color.black));
		frame.add(flujoMAX);
		
		
		JLabel consoleIcon=new JLabel("");
		consoleIcon.setBounds(55,296,25,25);
		Image im2=new ImageIcon("console.png").getImage();
		consoleIcon.setIcon(new ImageIcon(im2.getScaledInstance(25, 25, Image.SCALE_SMOOTH)));
		consoleIcon.setToolTipText("Consola de informacion");
		frame.add(consoleIcon);
		
		frame.add(finalizarNodos);
		frame.add(finalizarAristas);
		frame.add(consola);
	
		JPanel contenedormapa=new JPanel();
		contenedormapa.setLayout(null);
		contenedormapa.setBounds(500,0,1100,1000);
		contenedormapa.setBorder(new LineBorder(Color.blue));
	
		map.setBounds(0,0,1300,1000);
		map.setDisplayPositionByLatLon(-40, -59, 5);
		map.setZoomContolsVisible(false);
	
		//No Quiero que el mapa se modifique por ende quito los wheel Listener y action Listener y les agrego los que yo quiera
		MouseWheelListener[] wheel=map.getMouseWheelListeners();
		for (MouseWheelListener w: wheel) 
		{
			map.removeMouseWheelListener(w);
		}
		
		MouseListener[] actions=map.getMouseListeners();
		for (MouseListener act:actions)
		{
		 map.removeMouseListener(act);
		}
		
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
		}
		else
		{
			
			Animacion.bajar(-100, 0, 2,1, dropmenu);
			dropmenu.setBorder(new RoundedBorder(10, color));
			im_up(icono);
			icono.setToolTipText("Retraer drop menu");	
		}
	}
		});

		JLabel aux=new JLabel("h");
		aux.setBounds(500,60,20,20);
		
		frame.add(aux);
		
		Image im=new ImageIcon(selActual+".png").getImage();
		
		ImageIcon imicon=new ImageIcon( im.getScaledInstance(40, 40, Image.SCALE_DEFAULT));
		aux.setIcon(imicon);
		aux.updateUI();
		
	
		
		map.addMouseListener(new MouseAdapter() 
		{

		public void mouseReleased(MouseEvent e)
		{
			repintarNodos();
			
			if (e.getButton()==MouseEvent.BUTTON1)
			{
				if (selActual=="") 
				{
					Image im=new ImageIcon("optionpane.gif").getImage();
					ImageIcon imicon=new ImageIcon( im.getScaledInstance(55, 55, Image.SCALE_DEFAULT));
					JOptionPane.showMessageDialog(aux, "No se selecciono ningun tipo de nodo", "Seleccione nodo.", JOptionPane.ERROR_MESSAGE,imicon);
				}
					
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
			
			if (l!=null)
			{
			mapArista.addNodo();
			map.add(l);
			nodos.add(l);	
			addConsoleLine("Se agrego el Nodo "+ub+ ": en el punto- X:"+ p.getX()+ " - Y: "+ p.getY());
			ub++;
			map.repaint();
			}
			
			System.out.println("Lista de productores");
			
			for (JLabel j:productores)
			{
				System.out.println(j.getText()+" "+j.getName()+" ");
			}
			System.out.println();
			
			System.out.println("Lista de consumidores");
			
			for (JLabel j:consumidores)
			{
				System.out.println(j.getText()+" "+j.getName()+" ");
			}
			System.out.println();
			
			
			
		
			}
			
			
		}
			}

	});
			
			finalizarNodos.addMouseListener(new MouseAdapter() 
			{
				
			public void mouseReleased(MouseEvent e)
			{
				if (selActual!="") 
				{
				addConsoleLine("\n___________________________\n Se Finalizo la entrada de  Nodos:\n");
				selActual="";	
				}	
				
			}
			
			});
			
			finalizarAristas.addMouseListener(new MouseAdapter() 
			{
				
			public void mouseReleased(MouseEvent e)
			{
				
				int i=JOptionPane.showConfirmDialog(frame, "¿Esta seguro que quiere finalizar el agregado de aristas. Ya no podra agregar nodos ni aristas nuevas", "Finalizar agregado de aristas", JOptionPane.OK_CANCEL_OPTION);
				
				if (i==0) 
				{
					addConsoleLine("Se finalizo la entrada de aristas. Ya puede analizar el flujo maximo:");
				JLabel t=new JLabel(ub+""); 
				map.add(t);
				mapArista.addNodo();
				
				MouseListener [] eventos=map.getMouseListeners();
				
				for(MouseListener n: eventos)
					map.removeMouseListener(n);
				finalizarAristas.setEnabled(false);
				finalizarNodos.setEnabled(false);
				
				Animacion.mover_izquierda(10, -300, 2,1, finalizarNodos);
			
				Animacion.mover_izquierda(10, -300, 2,1, finalizarAristas);
				
				Animacion.mover_derecha(-300, 10, 2, 1, flujoMAX);
				
				for (JLabel cons :consumidores)
				{
					String text=cons.getText();
				    Integer num=Integer.parseInt(text);
				    String name=cons.getName();
				    Integer peso=Integer.parseInt(name);
					mapArista.addArista(ub, num, peso);
				}	
				
				for (JLabel cons :productores)
				{
					String text=cons.getText();
				    Integer num=Integer.parseInt(text);
				    String name=cons.getName();
				    Integer peso=Integer.parseInt(name);
					mapArista.addArista(0, num, peso);
					
				}
				removeActionsNodes();
			
			}
				
				}
				
				

			
			
			});
			
			flujoMAX.addMouseListener(new MouseAdapter() 
			{
				public void mouseReleased(MouseEvent e) 
				{
					Red gasoducto=new Red(ub);
					
					
					int grafo2[][]=mapArista.matrizDePesos();
					
					Integer produccion=0;
					for (JLabel lab:productores
							) 
					{
						String name=lab.getName();
						Integer num=Integer.parseInt(name);
						produccion+=num;
					}
					
					Integer demanda=0;
					for (JLabel lab:consumidores) 
					{
						String name=lab.getName();
						Integer num=Integer.parseInt(name);
						demanda+=num;
					}
					
					int flujomaximo=gasoducto.flujo_Maximo(grafo2,0,ub-1);
					addConsoleLine("La produccion total de gas es de: "+produccion+" El flujo maximo del grafo es: "+flujomaximo);
					
					if (demanda<flujomaximo) 
					{
						addConsoleLine("Se puede suplir la demanda de los consumidores, la demanda total es de: "+demanda);
					
					}
					if (demanda>flujomaximo) 
					{
						addConsoleLine("NO Se puede suplir la demanda de los consumidores, la demanda total es de: "+demanda);
					
					}
				}

				
				
			});
			
		
		
		
		
		
	}
}
