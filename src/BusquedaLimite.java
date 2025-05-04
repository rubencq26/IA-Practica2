import java.util.*;

public class BusquedaLimite {
    private Percepcion percepcion;
    private Laberinto lab;
    private Stack<Nodo> abierto; // Pila abierta
    private ArrayList<Nodo> cerrado; // Lista Cerrada
    private int numNodosExpandidos;
    private int numPuntos;

    // Constructor
    public BusquedaLimite(Laberinto lab) {
        this.lab = new Laberinto(lab.getLaberintoChar());
        percepcion = new Percepcion(lab);
        numNodosExpandidos = 0;
        numPuntos = 0;
    }

    public void resolverLaberinto(int maxIteraciones) {
        Nodo nodoInicial = new Nodo(null, 1, 1, 'E'); // Nodo Inicial
        abierto = new Stack<>();
        cerrado = new ArrayList<>();
        abierto.push(nodoInicial);

        while (!abierto.isEmpty()) {
            Nodo actual = abierto.pop(); // Extraemos el nodo de la pila

            // Condicion de Victoria
            if (actual.getValor() == 'S') {
                Nodo camino = actual.getPadre();
                while (camino != null && camino.getValor() != 'E') {
                    lab.actualizarPosicion(camino.getX(), camino.getY(), '.');
                    numPuntos++;
                    camino = camino.getPadre();
                }
                lab.Pintar();
                System.out.println("Número de nodos expandidos: " + numNodosExpandidos);
                System.out.println("Número de puntos pintados: " + numPuntos);
                return;
            }

            cerrado.add(actual); // Insertamor nodo en la lista cerrada

            HashMap<Integer, Character> percepcionMapa = percepcion.percibir(actual.getX(), actual.getY()); // Percibir el entorno alrededor del nodo

            for (int direccion : percepcionMapa.keySet()) {
                int x = actual.getX();
                int y = actual.getY();
                char valor = percepcionMapa.get(direccion);

                // Movimiento según dirección
                switch (direccion) {
                    case 0 -> y--; // izquierda
                    case 1 -> x--; // arriba
                    case 2 -> y++; // derecha
                    case 3 -> x++; // abajo
                }

                // Si no es pared
                if (valor != '#') {
                    Nodo hijo = new Nodo(actual, x, y, valor);
                    if (hijo.getIteracion() < maxIteraciones) { //Si supera el limite de iteraciones no expande
                        if (!cerrado.contains(hijo) && !abierto.contains(hijo)) {
                            abierto.push(hijo);
                            numNodosExpandidos++;
                        }
                    }
                }
            }
        }

        // Mensaje de fracaso
        System.out.println("No se ha encontrado solución.");
        System.out.println("Número de nodos expandidos: " + numNodosExpandidos);
    }

}


