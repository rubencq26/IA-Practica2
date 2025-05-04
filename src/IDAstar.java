import java.util.*;

public class IDAstar {
    private Stack<NodoHeuristico> abiertos; // Pila abierta
    private ArrayList<NodoHeuristico> cerrados; // Cola cerrada
    private Laberinto lab;
    private int numNodosExpandidos;
    private int numPuntosPintados;
    private Percepcion percepcion;
    private int [] salida;
    private double heuristica;
    private double sigHeuristica;

    public IDAstar(Laberinto lab){
        cerrados = new ArrayList<>();
        this.lab = new Laberinto(lab.getLaberintoChar());
        numNodosExpandidos = 0;
        numPuntosPintados = 0;
        salida = lab.getSalida();
        percepcion = new Percepcion(lab);
    }

    public void resolverLaberinto(int heur, boolean activo, int limite) {
        NodoHeuristico nodoInicial = new NodoHeuristico(null, 1, 1, 'E', 0); // Estado Inicial
        nodoInicial.generarHeuristica(heur, salida, lab, activo);
        heuristica = nodoInicial.getHeuristica();

        // El bucle principal de IDA* basado en la heurística
        while (heuristica <= limite) {
            abiertos = new Stack<>();
            cerrados = new ArrayList<>();
            sigHeuristica = Double.MAX_VALUE;

            abiertos.push(nodoInicial); // Insertamos en la pila el nodo Inicial

            while (!abiertos.isEmpty()) {
                NodoHeuristico actual = abiertos.pop(); // Extraemos de la pila el nodo actual
                cerrados.add(actual);

                if (actual.getValor() == 'S') {
                    // Se encontró la salida
                    NodoHeuristico camino = actual.getPadre();
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

                // Verificar los movimientos posibles
                HashMap<Integer, Character> map = percepcion.percibir(actual.getX(), actual.getY());
                for (Integer key : map.keySet()) {
                    int x = actual.getX();
                    int y = actual.getY();

                    switch (key) {
                        case 0 -> y--; // Izquierda
                        case 1 -> x--; // Arriba
                        case 2 -> y++; // Derecha
                        case 3 -> x++; // Abajo
                    }

                    // Verificar si no es una pared
                    if (map.get(key) != '#') {
                        NodoHeuristico hijo = new NodoHeuristico(actual, x, y, map.get(key), actual.getCoste() + 1);
                        hijo.generarHeuristica(heur, salida, lab, activo);

                        // Calcular f = coste + heurística
                        double f = hijo.getCoste() + hijo.getHeuristica();

                        // Si f es mayor que la heurística actual, actualiza el límite
                        if (f > heuristica) {
                            sigHeuristica = Math.min(sigHeuristica, f);
                        } else if (!cerrados.contains(hijo) && !abiertos.contains(hijo)) {
                            abiertos.push(hijo);
                            numNodosExpandidos++;
                        }
                    }
                }
            }

            // Nueva iteración con mayor heurística
            heuristica = sigHeuristica;
        }

        System.out.println("No se ha encontrado la solución ");
        System.out.println("Num nodos expandidos: " + numNodosExpandidos);
    }
}