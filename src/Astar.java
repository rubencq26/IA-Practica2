import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Astar {
    private LinkedList<NodoHeuristico> abiertos;
    private ArrayList<NodoHeuristico> cerrados;
    private Laberinto lab;
    private int numNodosExpandidos;
    private int numPuntosPintados;
    Percepcion percepcion;
    int[] salida;

    public Astar(Laberinto lab) {
        abiertos = new LinkedList<>();
        cerrados = new ArrayList<>();
        this.lab = new Laberinto(lab.getLaberintoChar());
        numNodosExpandidos = 0;
        numPuntosPintados = 0;
        salida = lab.getSalida();
        percepcion = new Percepcion(lab);
    }

    private void insertarOrdenado(NodoHeuristico nodo) {
        double f = nodo.getCoste() + nodo.getHeuristica();
        int i = 0;
        while ( i < abiertos.size()) {
            NodoHeuristico actual = abiertos.get(i);
            double factual = actual.getCoste() + actual.getHeuristica();
            if(f <= factual) {
                break;
            }
            i++;
        }
        abiertos.add(i, nodo);
    }

    public void resolverLaberinto(int heur, boolean activo) {
        NodoHeuristico nodoInicial = new NodoHeuristico(null, 1, 1, 'E', 0);
        nodoInicial.generarHeuristica(heur, salida, lab, activo);
        insertarOrdenado(nodoInicial);

        while (!abiertos.isEmpty()) {
            NodoHeuristico nodo = abiertos.removeFirst();


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

            cerrados.add(nodo);

            HashMap<Integer, Character> map = percepcion.percibir(nodo.getX(), nodo.getY());
            for (Integer key : map.keySet()) {
                int x = nodo.getX();
                int y = nodo.getY();

                switch (key) {
                    case 0 -> y--;
                    case 1 -> x--;
                    case 2 -> y++;
                    case 3 -> x++;
                }

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

        System.out.println("No se ha encontrado la solucion ");
        System.out.println("Num nodos expandidos: " + numNodosExpandidos);

        }
    }



