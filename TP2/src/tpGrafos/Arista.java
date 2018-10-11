package tpGrafos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;


public class Arista 
{

	private ArrayList <HashMap <Integer,Integer>> lista;
	
	public Arista() 
	{
		lista=new ArrayList<HashMap <Integer,Integer>>();
	}
	
	public void addArista(Integer d,Integer h,Integer peso) 
	{
		lista.get(d).put(h, peso);
	}
	
	public void addNodo() 
	{
		HashMap<Integer,Integer> map=new HashMap<Integer,Integer>();
		lista.add(map);
	}
	
	public Integer getPeso(Integer d,Integer h) 
	{
		HashMap<Integer,Integer> map=lista.get(d);
		Integer ret=map.get(h);
		return ret;
		
	}
	
	public boolean existeArista(Integer d,Integer h) 
	{
		return lista.get(d).containsKey(h);
	}
	
	public boolean existeReciproca(Integer d, Integer h) 
	{
		return lista.get(h).containsKey(d);
	}
	
	public void imprimir() 
	{
		int i=0;
		for (HashMap<Integer,Integer> map:lista) 
		{
			System.out.println("Desde: "+i+toString(map));
			i++;
		}
		System.out.println("");
	}

	private String toString(HashMap<Integer,Integer> map) 
	{
		String ret="";
		for (Entry<Integer, Integer> entry : map.entrySet()) 
		{
			ret+=" Hasta "+entry.getKey()+" con peso: "+entry.getValue();
		}
		return ret;
	}
	
	public int [][] matrizDePesos()
	{
		int size=lista.size();
		int[][] ret=new int[size][size];
	
		int fila=0;
		for (HashMap <Integer,Integer> map :lista) 
		{
			for (Entry<Integer, Integer> entry : map.entrySet()) 
			{
				ret[fila][entry.getKey()]=entry.getValue();
			}
			fila++;
		}	
		
		for (int i=0;i<size;i++) 
		{
			for (int j=0;j<size;j++) 
			{
				System.out.print(ret[i][j]+" ");
			}
			System.out.println("");
		}
		System.out.println("");
		
		return ret;
	}
	
	
	
	
	
}
