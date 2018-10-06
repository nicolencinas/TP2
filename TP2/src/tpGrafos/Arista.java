package tpGrafos;

import javax.swing.JLabel;

public class Arista extends JLabel
{

	private static final long serialVersionUID = 1L;
	private Integer a;
	private Integer b;
	
	public Arista(Integer a, Integer b)
	{
		this.a=a;
		this.b=b;
	}
	
	public boolean equals (Arista arista) 
	{
		return ((this.a==arista.a && this.b==arista.b) );
		
	}
	
	public boolean existeReciproca(Arista arista)
	{
		return ((this.a==arista.b && this.b==arista.a));
	}
	
	public Integer getDesde()
	{
		return a;
	}
	
	public Integer getHasta()
	{
		return b;
	}
	
	
	
	
	
}