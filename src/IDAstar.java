import java.util.*;

public class IDAstar {
    private Stack<NodoHeuristico> abiertos;
    private ArrayList<NodoHeuristico> cerrados;
    private Laberinto lab;
    private int numNodosExpandidos;
    private int numPuntosPintados;
    private Percepcion percepcion;
    private int [] salida;
    private double prof;
    private double sigProf;

    public IDAstar(Laberinto lab){

        cerrados = new ArrayList<>();
        this.lab = new Laberinto(lab.getLaberintoChar());
        numNodosExpandidos = 0;
        numPuntosPintados = 0;
        salida = lab.getSalida();
        percepcion = new Percepcion(lab);
    }

    public void resolverLaberinto(int heur, boolean activo, int limite) {
        NodoHeuristico nodoInicial = new NodoHeuristico(null, 1, 1, 'E', 0);
        nodoInicial.generarHeuristica(heur, salida, lab, activo);
        prof = nodoInicial.getHeuristica();

        while (prof < limite) {
            abiertos = new Stack<>();
            cerrados = new ArrayList<>();
            sigProf = Double.MAX_VALUE;

            abiertos.push(nodoInicial);

            while (!abiertos.isEmpty()) {
                NodoHeuristico actual = abiertos.pop();
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

                HashMap<Integer, Character> map = percepcion.percibir(actual.getX(), actual.getY());
                for (Integer key : map.keySet()) {
                    int x = actual.getX();
                    int y = actual.getY();

                    switch (key) {
                        case 0 -> y--;
                        case 1 -> x--;
                        case 2 -> y++;
                        case 3 -> x++;
                    }

                    if (map.get(key) != '#') {
                        NodoHeuristico hijo = new NodoHeuristico(actual, x, y, map.get(key), actual.getCoste() + 1);
                        hijo.generarHeuristica(heur, salida, lab, activo);
                        double f = hijo.getCoste() + hijo.getHeuristica();

                        if (f > prof) {
                            sigProf = Math.min(sigProf, f);
                        } else if (!cerrados.contains(hijo) && !abiertos.contains(hijo)) {
                            abiertos.push(hijo);
                            numNodosExpandidos++;
                        }
                    }
                }
            }

            // Nueva iteración con mayor profundidad
            prof = sigProf;
        }

        System.out.println("No se ha encontrado la solucion ");
        System.out.println("Num nodos expandidos: " + numNodosExpandidos);
    }



}


