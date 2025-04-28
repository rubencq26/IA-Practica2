import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Astar {
    private Queue<NodoHeuristico> abiertos;
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

    private void ordenarCola() {
        ArrayList<NodoHeuristico> aux = new ArrayList<>();
        while (!abiertos.isEmpty()) {
            aux.add(abiertos.poll());
        }
        while (!aux.isEmpty()) {
            int posMin = 0;
            for (int i = 0; i < aux.size(); i++) {
                if ((aux.get(i).getHeuristica() + aux.get(i).getCoste()) < (aux.get(posMin).getHeuristica() + aux.get(posMin).getCoste())) {
                    posMin = i;
                }
                abiertos.add(aux.get(posMin));
                aux.remove(posMin);
            }
        }
    }

    public void resolverLaberinto(int heur, boolean activo) {
        NodoHeuristico nodo = new NodoHeuristico(null, 1, 1, 'E', 0);
        abiertos.add(nodo);
        while (nodo.getValor() != 'S' && !abiertos.isEmpty()) {
            nodo = abiertos.poll();
            cerrados.add(nodo);

            HashMap<Integer, Character> map = percepcion.percibir(nodo.getX(), nodo.getY());
            for (Integer key : map.keySet()) {
                int x = nodo.getX();
                int y = nodo.getY();

                if (map.get(key) != '#') {
                    switch (key) {
                        case 0 -> y--;
                        case 1 -> x--;
                        case 2 -> y++;
                        case 3 -> x++;
                    }
                    NodoHeuristico hijo = new NodoHeuristico(nodo, x, y, map.get(key), nodo.getCoste() + 1);
                    hijo.generarHeuristica(heur, salida, lab, activo);
                    if (!cerrados.contains(hijo)) {
                        abiertos.add(hijo);
                        numNodosExpandidos++;
                    }
                }
            }
            ordenarCola();
        }
            if (nodo.getValor() == 'S') {
                nodo = nodo.getPadre();
                while (nodo != null) {
                    lab.actualizarPosicion(nodo.getX(), nodo.getY(), '.');
                    numPuntosPintados++;
                    nodo = nodo.getPadre();
                }
                lab.Pintar();
                System.out.println("Número de nodos expandidos: " + numNodosExpandidos);
                System.out.println("Numero de puntos pintados: " + numPuntosPintados);
            } else {
                System.out.println("No se ha encontrado la solucion ");
                System.out.println("Num nodos expandidos: " + numNodosExpandidos);
            }
        }
    }



