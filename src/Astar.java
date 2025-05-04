import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Astar {
    private LinkedList<NodoHeuristico> abiertos; //Cola de abiertos, lista enlazada, mejor para insertar, y me ahorro estructuras auxiliares para ordenar una cola
    private ArrayList<NodoHeuristico> cerrados; //Lista de nodos cerrados
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
        double f = nodo.getCoste() + nodo.getHeuristica(); //almacenar la funcion heuristica del nodo en una variable
        int i = 0;
        while ( i < abiertos.size()) { //buscamos la posicion correcta a insertar
            NodoHeuristico actual = abiertos.get(i);
            double factual = actual.getCoste() + actual.getHeuristica();
            if(f <= factual) { //Al ser una lista ordenada cuando la función heuristica encuentra el primer menor lo inserta en su posicion
                break;
            }
            i++;
        }
        abiertos.add(i, nodo); //Al ser lista enlazada requiere muy pocos recursos insertar un elemento
    }

    public void resolverLaberinto(int heur, boolean activo) {
        NodoHeuristico nodoInicial = new NodoHeuristico(null, 1, 1, 'E', 0); //Estado Inicial
        nodoInicial.generarHeuristica(heur, salida, lab, activo);
        insertarOrdenado(nodoInicial); //Insertamos en la cola abierta

        while (!abiertos.isEmpty()) {
            NodoHeuristico nodo = abiertos.removeFirst(); //sacamos nodo de la cola abierta


            if (nodo.getValor() == 'S') { //condicion de victoria
                // Se encontró la salida
                NodoHeuristico camino = nodo.getPadre();
                while (camino != null && camino.getValor() != 'E') { //Bucle para crear el camino a la meta
                    lab.actualizarPosicion(camino.getX(), camino.getY(), '.');
                    numPuntosPintados++;
                    camino = camino.getPadre();
                }
                lab.Pintar();
                System.out.println("Número de nodos expandidos: " + numNodosExpandidos);
                System.out.println("Numero de puntos pintados: " + numPuntosPintados);
                return;
            }

            cerrados.add(nodo); //añadimos el nodo ya visitado a la lista de cerrados

            HashMap<Integer, Character> map = percepcion.percibir(nodo.getX(), nodo.getY()); //Usamos metodo percibir para generar hijos
            for (Integer key : map.keySet()) {
                int x = nodo.getX();
                int y = nodo.getY();

                switch (key) {
                    case 0 -> y--; // Izquierda
                    case 1 -> x--; // Arriba
                    case 2 -> y++; // Derecha
                    case 3 -> x++; // Abajo
                }

                if (map.get(key) != '#') { // Verificar que no sea pared
                    NodoHeuristico hijo = new NodoHeuristico(nodo, x, y, map.get(key), nodo.getCoste() + 1); // Generar hijo
                    hijo.generarHeuristica(heur, salida, lab, activo);
                    if (!cerrados.contains(hijo) && !abiertos.contains(hijo)) { // Gracias al metodo equals que solo compara la x e y usamos contains para saber si el nodo ya está contenido
                        insertarOrdenado(hijo); // Insertamos hijo
                        numNodosExpandidos++;
                    }
                }
            }
        }
        // Solo ejecuta el mensaje cuando no encuentra solución
        System.out.println("No se ha encontrado la solucion ");
        System.out.println("Num nodos expandidos: " + numNodosExpandidos);

        }
    }



