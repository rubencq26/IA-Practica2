import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class BusquedaBidireccional {
    private ArrayList<Nodo> cerradaInicio;
    private Queue<Nodo> abiertaInicio;
    private ArrayList<Nodo> cerradaFinal;
    private Queue<Nodo> abiertaFinal;
    private Percepcion percepcion;
    private int numNodosExpandidos;
    private Laberinto lab;
    private int numPuntos;

    public BusquedaBidireccional(Laberinto laberinto) {
        percepcion = new Percepcion(laberinto);
        numNodosExpandidos = 0;
        numPuntos = 0;
        lab = new Laberinto(laberinto.getLaberintoChar());
    }

    public void resolverLaberinto() {
        Nodo nodoInicial = new Nodo(null, 1, 1, 'E');
        Nodo nodoFinal = new Nodo(null, lab.getSalida()[0], lab.getSalida()[1], 'S');

        abiertaInicio = new LinkedList<>();
        cerradaInicio = new ArrayList<>();
        cerradaFinal = new ArrayList<>();
        abiertaFinal = new LinkedList<>();

        abiertaInicio.add(nodoInicial);
        abiertaFinal.add(nodoFinal);

        while (!abiertaInicio.isEmpty() && !abiertaFinal.isEmpty()) {
            nodoInicial = abiertaInicio.poll();
            nodoFinal = abiertaFinal.poll();

            cerradaInicio.add(nodoInicial);
            cerradaFinal.add(nodoFinal);

            // Buscar intersección
            for (Nodo n1 : cerradaInicio) {
                if (cerradaFinal.contains(n1)) {
                    Nodo interseccion = n1;

                    // Reconstruir camino desde inicio
                    Nodo camino = interseccion;
                    while (camino != null && camino.getValor() != 'E') {
                        lab.actualizarPosicion(camino.getX(), camino.getY(), '.');
                        numPuntos++;
                        camino = camino.getPadre();
                    }

                    // Reconstruir camino desde final
                    for (Nodo n2 : cerradaFinal) {
                        if (n2.equals(interseccion)) {
                            camino = n2.getPadre(); // para no repetir punto
                            while (camino != null && camino.getValor() != 'S') {
                                lab.actualizarPosicion(camino.getX(), camino.getY(), '.');
                                numPuntos++;
                                camino = camino.getPadre();
                            }
                            break;
                        }
                    }

                    numPuntos--; // Quitar intersección repetida
                    lab.Pintar();
                    System.out.println("Numero de nodos expandidos: " + numNodosExpandidos);
                    System.out.println("Numero de puntos pintados: " + numPuntos);
                    return;
                }
            }

            moverInicio(nodoInicial);
            moverFinal(nodoFinal);
        }

        System.out.println("No se ha encontrado solucion");
        System.out.println("Numero de nodos expandidos: " + numNodosExpandidos);
    }

    public void moverInicio(Nodo actual) {
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

            if (map.get(key) != '#') {
                Nodo hijo = new Nodo(actual, x, y, map.get(key));
                if (!cerradaInicio.contains(hijo) && !abiertaInicio.contains(hijo)) {
                    abiertaInicio.add(hijo);
                    numNodosExpandidos++;
                }
            }
        }
    }

    public void moverFinal(Nodo actual) {
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

            if (map.get(key) != '#') {
                Nodo hijo = new Nodo(actual, x, y, map.get(key));
                if (!cerradaFinal.contains(hijo) && !abiertaFinal.contains(hijo)) {
                    abiertaFinal.add(hijo);
                    numNodosExpandidos++;
                }
            }
        }
    }
}
