import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class FlowMap {
    private int[][] flowMap;
    private Laberinto lab;
    int [] salida;

    public FlowMap(Laberinto lab) {
        this.lab = new Laberinto(lab.getLaberintoChar());
        salida = lab.getSalida();
        flowMap = new int[lab.getAlto()][lab.getAncho()];
        generarFlowMap();
    }

    public void generarFlowMap() {
        Queue<NodoHeuristico> abierto = new LinkedList<>();
        ArrayList<NodoHeuristico> cerrado = new ArrayList();
        Laberinto temp = new Laberinto(lab.getLaberintoChar());
        for(int i = 0; i < temp.getAlto(); i++){
            for(int j = 0; j < temp.getAncho(); j++){
                if(i == 0 || j == 0 || i == temp.getAlto() - 1 || j == temp.getAncho() - 1 ){
                    temp.actualizarPosicion(i,j, '-');
                    flowMap[i][j] = -1;
                }
            }
        }
        Percepcion percepcion = new Percepcion(temp);

        NodoHeuristico nodo = new NodoHeuristico(null, salida[0],salida[1] , 'S',0);
        abierto.add(nodo);
        while (!abierto.isEmpty()) {
            nodo = abierto.poll();
            cerrado.add(nodo);
            flowMap[nodo.getX()][nodo.getY()] = nodo.getCoste();
            HashMap<Integer, Character> mapa = new HashMap<Integer, Character>();
            mapa = percepcion.percibir(nodo.getX(), nodo.getY());

            for( int key : mapa.keySet()){
                int x = nodo.getX();
                int y = nodo.getY();
                if(mapa.get(key) != '-') {

                    switch (key) {
                        case 0 -> y--;
                        case 1 -> x--;
                        case 2 -> y++;
                        case 3 -> x++;
                    }
                    NodoHeuristico hijo = new NodoHeuristico(nodo,x,y,mapa.get(key), nodo.getCoste()+1);
                    if(!cerrado.contains(hijo)){
                        abierto.add(hijo);
                    }
                }

            }

        }
    }

    public int[][] getFlowMap() {
        return flowMap;
    }

    public void pintarFlowMap() {
        for(int i = 0; i < lab.getAlto(); i++){
            for(int j = 0; j < lab.getAncho(); j++){
                System.out.print(flowMap[i][j] + " ");
            }
            System.out.println();
        }
    }
}
