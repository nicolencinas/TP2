package tpGrafos;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class RedTest {


	private int[][] grafo;
	private int[][] grafo2;
	private int[][] grafo3;
	private int[][] grafo4;
	private String[] nombreGrupos;
	private int[] acumulador1;
	private int[] acumulador2;
	private int[] acumulador3;
	@Before
	public void setUp() {
		grafo = new int[][] 	{ { 0, 2, 4, 3, 0, 0 }, 
								  { 0, 0, 0, 0, 0, 3 }, 
								  { 0, 0, 0, 0, 6, 0 }, 
								  { 0, 0, 0, 0, 5, 3 },
								  { 0, 0, 0, 0, 0, 1 }, 
								  { 0, 0, 0, 0, 0, 0 } };
								  
		grafo2 = new int[][] 	{ { 0, 5, 0, 0 }, 
								  { 0, 0, 0, 0 }, 
								  { 0, 0, 0, 3 }, 
								  { 0, 0, 0, 0 } };

		grafo3 = new int[][] 	{ { 0, 0, 0, 0 }, 
								  { 0, 0, 0, 0 }, 
								  { 0, 0, 0, 0 }, 
								  { 0, 0, 0, 0 } };
								  
	    grafo4 = new int[][] 	{ { 0, 5, 0, 0 }, 
	    	                      { 0, 0, 0, 0 }, 
								  { 0, 0, 0, 3 }, 
								  { 0, 0, 0, 0 } };
								  				  
	    nombreGrupos = new String[] { "productor", 
	    							  "paso", 
	    							  "paso", 
	    							  "paso", 
	    							  "paso", 
	    							  "consumidor" };
		acumulador1 = new int[6];
		acumulador2 = new int[4];
		acumulador3 = new int[4];
	}

	@Test
	public void romperConstructor() {
		boolean thrown = false;
		try {
			new Red(0);
		} catch (Exception e) {
			thrown = true;
		}
		assertTrue(thrown);
	}
	
	@Test
	public void grafoValido() {
		boolean thrown = false;
		try {
			Red red1=new Red(-1);
			Red.llenarGrafo(red1.obtenerGrafo(), grafo4);
		} catch (Exception e) {
			thrown = true;
		}
		assertTrue(thrown);
	}

	@Test
	public void llenarGrafoTest() {
		Red red1 = new Red(grafo.length);
		Red.llenarGrafo(red1.obtenerGrafo(),grafo);
		assertArrayEquals(red1.obtenerGrafo(), grafo);
	}

	@Test
	public void llenarGrupoTest() {
		Red red1 = new Red(grafo.length);
		red1.llenarGrupos(nombreGrupos);
		assertArrayEquals(red1.obtenerGrupos(), nombreGrupos);
	}
	
	@Test
	public void BFSValidoTest() {
		Red red1 = new Red(grafo.length);
		Red.llenarGrafo(red1.obtenerGrafo(),grafo);
		assertTrue(red1.bfs(red1.obtenerGrafo(), 0, 5, acumulador1));
		
	}

	@Test
	public void BFSInvalidoTest() {
		Red red2 = new Red(grafo2.length);
		Red.llenarGrafo(red2.obtenerGrafo(),grafo2);
		assertFalse(red2.bfs(red2.obtenerGrafo(), 0, 3, acumulador2));
	}

	@Test
	public void BFSVacioTest() {
		Red red3 = new Red(grafo3.length);
		Red.llenarGrafo(red3.obtenerGrafo(),grafo3);
		assertFalse(red3.bfs(red3.obtenerGrafo(), 0, 3, acumulador3));
	}

	@Test
	public void flujomaximoValido() {
		Red red4 = new Red(grafo.length);
		Red.llenarGrafo(red4.obtenerGrafo(),grafo);
		Integer a = 6;
		assertTrue(a.equals(red4.flujo_Maximo(red4.obtenerGrafo(), 0, 5)));
	}
	
	@Test
	public void flujomaximoInvalido() {
		Red red4 = new Red(grafo.length);
		Red.llenarGrafo(red4.obtenerGrafo(),grafo);
		Integer b = 5;
		assertFalse(b.equals(red4.flujo_Maximo(red4.obtenerGrafo(), 0, 5)));
	}
}


