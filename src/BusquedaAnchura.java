import java.util.*;


public class BusquedaAnchura {
    private ArrayList<Nodo> colaCerrada; // Lista de nodos Cerrados
    private Queue<Nodo> colaAbierta; // Cola de nodos abiertos
    private Percepcion percepcion;
    private int numNodosExpandidos;
    private Laberinto lab;
    private int numPuntos;

    public BusquedaAnchura(Laberinto laberinto) {

        percepcion = new Percepcion(laberinto);
        numNodosExpandidos = 0;
        numPuntos = 0;

        lab = new Laberinto(laberinto.getLaberintoChar());
    }

    public void resolverLaberinto() {
       colaCerrada = new ArrayList<>();
       colaAbierta = new LinkedList<>(); // En java no existen las colas como tal y derivan de una Linked List

        Nodo nodo = new Nodo(null, 1, 1, 'E'); //Nodo Inicial
        colaAbierta.add(nodo);

        while (!colaAbierta.isEmpty()) {
            Nodo actual = colaAbierta.poll(); // Extraemos el primer nodo de la cola

            if (actual.getValor() == 'S') { // Condición de victoria
                Nodo camino = actual.getPadre();
                while (camino != null && camino.getValor() != 'E') { //Bucle para actualizar laberinto y pintar el camino encontrado
                    lab.actualizarPosicion(camino.getX(), camino.getY(), '.');
                    numPuntos++;
                    camino = camino.getPadre();
                }
                lab.Pintar();
                System.out.println("Número de nodos expandidos: " + numNodosExpandidos);
                System.out.println("Numero de puntos pintados: " + numPuntos);
                return;
            }
            colaCerrada.add(actual); // Añadimos el nodo a la lista de explorados

            HashMap<Integer, Character> map = percepcion.percibir(actual.getX(), actual.getY()); //Generamos hash map para percibir el entorno

            for (Integer key : map.keySet()) {
                int x = actual.getX();
                int y = actual.getY();
                switch (key) {
                    case 0 -> y--; // Arriba
                    case 1 -> x--; // Izquierda
                    case 2 -> y++; // Abajo
                    case 3 -> x++; // Derecha
                }

                if (map.get(key) != '#') {  // Verificar que no sea pared
                    Nodo hijo = new Nodo(actual, x, y, map.get(key));
                    if(!colaCerrada.contains(hijo) && !colaAbierta.contains(hijo)) {
                        colaAbierta.add(hijo); //Insertar hijp
                        numNodosExpandidos++;
                    }
                }
            }
        }

        // Mensaje de derrota
        System.out.println("No se ha encontrado la solución");
        System.out.println("Numero de nodos expandidos: " + numNodosExpandidos);

    }

}
