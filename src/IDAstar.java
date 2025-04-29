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

    public void resolverLaberinto(int heur, boolean activo, int limite){
        NodoHeuristico nodo = new NodoHeuristico(null, 1, 1, 'E', 0);
        nodo.generarHeuristica(heur, salida ,lab, activo);
        prof = nodo.getHeuristica();

        while(nodo.getValor() != 'S' && prof < limite){
            abiertos = new Stack<NodoHeuristico>();
            cerrados.clear();
            sigProf = Integer.MAX_VALUE;
            abiertos.push(nodo);


            while ( nodo.getValor() != 'S' && !abiertos.isEmpty()){
                nodo = abiertos.pop();
                cerrados.add(nodo);
                HashMap<Integer, Character> map = percepcion.percibir(nodo.getX(), nodo.getY());
                for (Integer key : map.keySet()) {
                    int x = nodo.getX();
                    int y = nodo.getY();

                    if (map.get(key) != '#') {
                        switch (key) {
                            case 0 -> y--;
                            case 1 -> x--;
                            case 2 -> y++;
                            case 3 -> x++;
                        }
                        NodoHeuristico hijo = new NodoHeuristico(nodo, x, y, map.get(key), nodo.getCoste() + 1);
                        hijo.generarHeuristica(heur, salida, lab, activo);
                        double f = hijo.getCoste() + hijo.getHeuristica();
                        if(f > prof){
                            sigProf = Math.min(sigProf, f);
                        }else  if(!cerrados.contains(hijo)) {
                            abiertos.add(hijo);
                            numNodosExpandidos++;
                        }
                    }
                }
            }
            prof = sigProf;
        }
        if (nodo.getValor() == 'S') {
            nodo = nodo.getPadre();
            while (nodo != null) {
                lab.actualizarPosicion(nodo.getX(), nodo.getY(), '.');
                numPuntosPintados++;
                nodo = nodo.getPadre();
            }
            lab.Pintar();
            System.out.println("Número de nodos expandidos: " + numNodosExpandidos);
            System.out.println("Numero de puntos pintados: " + numPuntosPintados);
        } else {
            System.out.println("No se ha encontrado la solucion ");
            System.out.println("Num nodos expandidos: " + numNodosExpandidos);
        }


    }

}
