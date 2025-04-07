import javax.print.attribute.HashPrintJobAttributeSet;
import java.util.*;

public class GreedyBestFirstSearch {
    private Queue<NodoHeuristico> abiertos;
    private ArrayList<NodoHeuristico> cerrados;
    private Laberinto lab;
    private int numNodosExpandidos;
    private int numPuntosPintados;
    Percepcion percepcion;
    int [] salida;

    public GreedyBestFirstSearch(Laberinto lab) {
        abiertos = new LinkedList<>();
        this.lab = new Laberinto(lab.getLaberintoChar());
        numNodosExpandidos = 0;
        numPuntosPintados = 0;
        salida = lab.getSalida();
        percepcion = new Percepcion(lab);
    }

    private void ordenarCola(){
        ArrayList<NodoHeuristico> aux = new ArrayList<>();
        int pos = 0;
        while(!abiertos.isEmpty()){
            aux.add(abiertos.poll());
        }

        for(int i = 0; i < aux.size(); i++){
            for(int j = 0; j < aux.size(); j++){
                if(aux.get(pos).getHeuristica() > aux.get(j).getHeuristica()){
                    pos = j;
                }
            }
            abiertos.add(aux.get(pos));
            aux.remove(pos);
        }
    }

    public void resolverLaberinto(int heur){

        NodoHeuristico nodo = new NodoHeuristico(null,1,1, 'E');
        nodo.generarHeuristica(heur, salida);
        abiertos.add(nodo);

        while(!abiertos.isEmpty() && nodo.getValor() != 'S'){
            abiertos.poll();
            cerrados.add(nodo);
            HashMap<Integer,Character> map = percepcion.percibir(nodo.getX(), nodo.getY());
            for(Integer key : map.keySet()) {
                int x = nodo.getX();
                int y = nodo.getY();

                if(map.get(key) != '#'){
                    switch (key){
                        case 0 -> y--;
                        case 1 -> x--;
                        case 2 -> y++;
                        case 3 -> x++;
                    }
                }
                NodoHeuristico hijo = new NodoHeuristico(nodo,x,y,map.get(key));
                hijo.generarHeuristica(heur, salida);
                if(!cerrados.contains(hijo)){
                    abiertos.add(hijo);
                    numNodosExpandidos++;
                }

                ordenarCola();

         }
        }
    }
}
