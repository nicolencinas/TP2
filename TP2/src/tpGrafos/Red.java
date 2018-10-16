package tpGrafos;

import java.util.LinkedList;

public class Red {

	private int flujoMaximo = 0;
	private int[][] grafoPesos;
	private String[] grupo;

	// constructor
	Red(int vertice) {

		if (vertice <= 0) {
			throw new RuntimeException("Debe ingresar al menos un vertice");
		} else {

			this.grafoPesos = new int[vertice][vertice];
			this.grupo = new String[vertice];
		}
	}

	// Getters and setters

	public int[][] obtenerGrafo() {
		return this.grafoPesos;
	}

	public int obtenerValor(int a, int b) {
		return obtenerGrafo()[a][b];
	}

	public String obtenerNombreGrupo(int a) {
		return obtenerGrupos()[a];
	}

	public String[] obtenerGrupos() {
		return this.grupo;
	}

	public void setValor(int a, int b, int c) {
		obtenerGrafo()[a][b] = c;
	}

	public void setNombre(int a, String b) {
		obtenerGrupos()[a] = b;
	}

	// Imprimir

	public void imprimirGrafoPesos() {
		for (int i = 0; i < grafoPesos.length; i++) {
			System.out.println("");
			for (int j = 0; j < grafoPesos.length; j++) {
				System.out.print(grafoPesos[i][j]);
			}
		}
	}

	public void imprimirGrupos() {
		for (int i = 0; i < this.obtenerGrafo().length; i++) {
			System.out.print(this.obtenerGrupos()[i] + " ");
		}
	}

	// llenar el grafo/matriz

	public void llenarGrupos(String[] otro) {
		for (int i = 0; i < otro.length; i++) {
			setNombre(i, otro[i]);
		}
	}
	
	public static boolean esValido(int[][] grafo) {
		for (int i = 0; i < grafo.length; i++) {
			for (int j = 0; j < grafo[i].length; j++) {
				if(grafo[i][j]<0) {
					return false;
				}
			}
			}
		return true;
	}

	// llenar arreglo con los nombres de los grupos a los que pertenece cada vertice
	public static void llenarGrafo(int[][] grafo,int[][] otroGrafo) {
		if(esValido(otroGrafo)==false) {
			throw new RuntimeException("No se admiten peos negativo");
		}
		for (int i = 0; i < grafo.length; i++) {
			for (int j = 0; j < grafo[i].length; j++) {
				grafo[i][j] = otroGrafo[i][j];
			}
		}
	}

	// bfs
	public boolean bfs(int grafo[][], int inicio, int fin, int[] acumulador) {
		boolean visitado[] = new boolean[grafo.length];
		for (int i = 0; i < grafo.length; ++i) {
			visitado[i] = false;
		}

		LinkedList<Integer> cola = new LinkedList<Integer>();
		cola.add(inicio);
		visitado[inicio] = true;
		acumulador[inicio] = -1;

		while (cola.size() != 0)
		{
			int aux = cola.poll();
			for (int i = 0; i < grafo.length; i++) 
			{
				if (visitado[i] == false && grafo[aux][i] > 0) {
					cola.add(i);
						acumulador[i] = aux;
					visitado[i] = true;
				}
			}
		}
		return (visitado[fin] == true);
	}

	// Flujo maximo
	public int flujo_Maximo(int grafo[][], int inicio, int fin) 
	{
		int flujoMaximo=0;
		int aux1, aux2;
		int acumulador[] = new int[grafoPesos.length];// guarda los caminos

		int grafoTemp[][] = new int[grafo.length][grafo.length];
		
		llenarGrafo(grafoTemp,grafo);

		while (bfs(grafoTemp, inicio, fin, acumulador)) 
		{
			int caminoFlujo = Integer.MAX_VALUE;
			

			for (aux1 = fin; aux1 != inicio; aux1 = acumulador[aux1]) {
				aux2 = acumulador[aux1];
				caminoFlujo = Math.min(caminoFlujo, grafoTemp[aux2][aux1]);
			}

			for (aux1 = fin; aux1 != inicio; aux1 = acumulador[aux1]) {
				aux2 = acumulador[aux1];
				grafoTemp[aux2][aux1] -= caminoFlujo;
				grafoTemp[aux1][aux2] += caminoFlujo;
			}
			
			
				
			
			flujoMaximo += caminoFlujo;
		}
		return flujoMaximo;
	}
}

