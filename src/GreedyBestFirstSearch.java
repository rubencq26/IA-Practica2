import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class GreedyBestFirstSearch {
    private LinkedList<NodoHeuristico> abiertos; // Lista enlazada abierta
    private ArrayList<NodoHeuristico> cerrados; // Lista cerrada
    private Laberinto lab;
    private int numNodosExpandidos;
    private int numPuntosPintados;
    Percepcion percepcion;
    int[] salida;

    // Constructor
    public GreedyBestFirstSearch(Laberinto lab) {
        abiertos = new LinkedList<>();
        cerrados = new ArrayList<>();
        this.lab = new Laberinto(lab.getLaberintoChar());
        numNodosExpandidos = 0;
        numPuntosPintados = 0;
        salida = lab.getSalida();
        percepcion = new Percepcion(lab);
    }

    // Metodo para insertar directamente en la cola abierta en su posicion correspondiente
    private void insertarOrdenado(NodoHeuristico nodo) {
        double f = nodo.getHeuristica(); // Funcion heuristica
        int i = 0;
        while ( i < abiertos.size()) {
            NodoHeuristico actual = abiertos.get(i);
            double factual = actual.getHeuristica();
            if(f <= factual) {
                break;
            }
            i++;
        }
        abiertos.add(i, nodo);
    }

    public void resolverLaberinto(int heur, boolean activo) {
        NodoHeuristico nodoInicial = new NodoHeuristico(null, 1, 1, 'E', 0); // Nodo Inicial
        nodoInicial.generarHeuristica(heur, salida, lab, activo);
        insertarOrdenado(nodoInicial);

        while (!abiertos.isEmpty()) {
            NodoHeuristico nodo = abiertos.removeFirst(); //Sacamos primer elemento de la cola

            // Condicion de derrota
            if (nodo.getValor() == 'S') {
                // Se encontró la salida
                NodoHeuristico camino = nodo.getPadre();
                while (camino != null && camino.getValor() != 'E') {
                    lab.actualizarPosicion(camino.getX(), camino.getY(), '.');
                    numPuntosPintados++;
                    camino = camino.getPadre();
                }
                lab.Pintar();
                System.out.println("Número de nodos expandidos: " + numNodosExpandidos);
                System.out.println("Numero de puntos pintados: " + numPuntosPintados);
                return;
            }

            cerrados.add(nodo); // Insertamos nodo explorado en la lista cerrada

            HashMap<Integer, Character> map = percepcion.percibir(nodo.getX(), nodo.getY());
            for (Integer key : map.keySet()) {
                int x = nodo.getX();
                int y = nodo.getY();

                // Segun la direccion
                switch (key) {
                    case 0 -> y--; // Izquierda
                    case 1 -> x--; // Arriba
                    case 2 -> y++; // Derecha
                    case 3 -> x++; // Abajo
                }

                // Verificar que no haya pared
                if (map.get(key) != '#') {
                    NodoHeuristico hijo = new NodoHeuristico(nodo, x, y, map.get(key), nodo.getCoste() + 1);
                    hijo.generarHeuristica(heur, salida, lab, activo);
                    if (!cerrados.contains(hijo) && !abiertos.contains(hijo)) {
                        insertarOrdenado(hijo);
                        numNodosExpandidos++;
                    }
                }
            }
        }

        // Mensaje de derrota
        System.out.println("No se ha encontrado la solucion ");
        System.out.println("Num nodos expandidos: " + numNodosExpandidos);

        }
    }



