import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;


public class BusquedaAnchura {
    private ArrayList<Nodo> colaCerrada;
    private Queue<Nodo> colaAbierta;
    private Percepcion percepcion;
    private int numNodosExpandidos;
    private boolean win;
    private Laberinto lab;

    public BusquedaAnchura(Laberinto laberinto) {
        colaCerrada = new ArrayList<Nodo>();
        colaAbierta = new LinkedList<>();
        percepcion = new Percepcion(laberinto);
        numNodosExpandidos = 0;
        win = false;
        lab = new Laberinto(laberinto.getLaberintoChar());
    }

    public void resolverLaberinto(){

    }

}
