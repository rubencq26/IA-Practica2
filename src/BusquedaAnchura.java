import java.util.*;


public class BusquedaAnchura {
    private HashSet<Nodo> colaCerrada;
    private Queue<Nodo> colaAbierta;
    private Percepcion percepcion;
    private int numNodosExpandidos;
    private Laberinto lab;

    public BusquedaAnchura(Laberinto laberinto) {
        colaCerrada = new HashSet<>();
        colaAbierta = new LinkedList<>();
        percepcion = new Percepcion(laberinto);
        numNodosExpandidos = 0;

        lab = new Laberinto(laberinto.getLaberintoChar());
    }

    public void resolverLaberinto() {
        Queue<Nodo> colaAbierta = new LinkedList<>();
        Set<Nodo> colaCerrada = new HashSet<>();

        Nodo nodo = new Nodo(null, 1, 1, 'E');
        colaAbierta.add(nodo);
        colaCerrada.add(nodo);

        boolean win = false;

        while (!colaAbierta.isEmpty()) {
            nodo = colaAbierta.poll();

            if (nodo.getValor() == 'S') {
                win = true;
                break;
            }

            HashMap<Integer, Character> map = percepcion.percibir(nodo.getX(), nodo.getY());

            for (Integer i : map.keySet()) {
                int x = nodo.getX();
                int y = nodo.getY();

                if (map.get(i) != '#') {  // Verificar que no sea pared
                    switch (i) {
                        case 0 -> y--; // Arriba
                        case 1 -> x--; // Izquierda
                        case 2 -> y++; // Abajo
                        case 3 -> x++; // Derecha
                    }

                    Nodo nuevoNodo = new Nodo(nodo, x, y, map.get(i));
                    boolean encontrado = false;
                    for(Nodo n : colaCerrada) {
                        if(n.equals(nuevoNodo)) {
                            encontrado = true;
                        }
                    }
                    if (!encontrado) {
                        colaCerrada.add(nuevoNodo);
                        colaAbierta.add(nuevoNodo);
                        numNodosExpandidos++;
                    }
                }
            }
        }

        if (win) {
            nodo = nodo.getPadre();
            while (nodo != null) {
                lab.actualizarPosicion(nodo.getX(), nodo.getY(), '.');
                nodo = nodo.getPadre();
            }
            lab.Pintar();
            System.out.println("Número de nodos expandidos: " + numNodosExpandidos);
        } else {
            System.out.println("Meta no encontrada");
        }
    }

}
