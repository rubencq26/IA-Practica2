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
        cerrados = new ArrayList<>();
        this.lab = new Laberinto(lab.getLaberintoChar());
        numNodosExpandidos = 0;
        numPuntosPintados = 0;
        salida = lab.getSalida();
        percepcion = new Percepcion(lab);
    }

    private void ordenarCola() {
        ArrayList<NodoHeuristico> aux = new ArrayList<>();

        // Vaciar la cola en la lista auxiliar
        while (!abiertos.isEmpty()) {
            aux.add(abiertos.poll());
        }

        // Ordenar manualmente (tipo selección)
        while (!aux.isEmpty()) {
            int posMin = 0;
            for (int i = 1; i < aux.size(); i++) {
                if (aux.get(i).getHeuristica() < aux.get(posMin).getHeuristica()) {
                    posMin = i;
                }
            }
            // Añadir el menor a la cola y eliminarlo de la lista
            abiertos.add(aux.get(posMin));
            aux.remove(posMin);
        }
    }

    public void resolverLaberinto(int heur){
        Laberinto laberinto = new Laberinto(lab.getLaberintoChar());
        if(heur ==3){
            FlowMap flowMap = new FlowMap(lab);
            int [][] fmap = flowMap.getFlowMap();
            char [][] laberintoChar = new char[lab.getAlto()][lab.getAncho()];

            for(int i =0; i < lab.getAlto(); i++){
                for(int j =0; j < lab.getAncho(); j++){
                    laberintoChar[i][j] = (char) fmap[i][j];
                }
            }
            laberinto.actualizarLaberinto(laberintoChar);
        }

        NodoHeuristico nodo = new NodoHeuristico(null,1,1, 'E');
        nodo.generarHeuristica(heur, salida,laberinto, false);
        abiertos.add(nodo);

        while(!abiertos.isEmpty() && nodo.getValor() != 'S'){
            nodo = abiertos.poll();
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
                    NodoHeuristico hijo = new NodoHeuristico(nodo,x,y,map.get(key));
                    hijo.generarHeuristica(heur, salida,laberinto, false);
                    if(!cerrados.contains(hijo)){
                        abiertos.add(hijo);
                        numNodosExpandidos++;
                    }
                }
            }
            ordenarCola();

        }
        if(nodo.getValor() == 'S'){
            nodo = nodo.getPadre();
            while(nodo != null){
                lab.actualizarPosicion(nodo.getX(), nodo.getY(), '.');
                numPuntosPintados++;
                nodo = nodo.getPadre();
            }
            lab.Pintar();
            System.out.println("Número de nodos expandidos: " + numNodosExpandidos);
            System.out.println("Numero de puntos pintados: " + numPuntosPintados);
        }else{
            System.out.println("No se ha encontrado la solucion ");
            System.out.println("Num nodos expandidos: " + numNodosExpandidos);
        }
    }
}
