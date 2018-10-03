package tpGrafos;

public class Tupla 
{

	private Integer a;
	private Integer b;
	
	public Tupla(Integer a, Integer b)
	{
		this.a=a;
		this.b=b;
	}
	
	public boolean equals (Tupla tupla) 
	{
		return (this.a==tupla.a && this.b==tupla.b);
	}
	
	
}
