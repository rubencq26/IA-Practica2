import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class NodoHeuristico{
    private double heuristica;
    private int coste;
    private NodoHeuristico Padre;
    private int x, y;
    private char valor;

    // Constructor
    public NodoHeuristico(NodoHeuristico p, int x, int y, char valor, int coste) {
        Padre = p;
        this.x = x;
        this.y = y;
        this.valor = valor;
        heuristica = 100000;
        this.coste = coste;
    }

    public void generarHeuristica(int tipo, int [] salida, Laberinto labe, boolean activo) {
        //0 Distancia a manhattan
        //1 Distancia Euclidea
        switch (tipo) {
            // Camino a Manhattan
            case 0:
                heuristica = Math.abs(salida[0] - getX()) + Math.abs(salida[1] - getY());
                break;
           // Distancia euclidea
            case 1:
                heuristica = (Math.sqrt(Math.pow(Math.abs(salida[0] - getX()),2) + Math.pow(Math.abs(salida[1] - getY()),2)));
                break;
            // Camino a Manhattan + numObstaculos
            case 2:
                HashMap <Integer, Character> mapa = new HashMap();
                Laberinto lab = new Laberinto(labe.getLaberintoChar());


                for(int i = 0; i < lab.getAlto(); i++){
                    for(int j = 0; j < lab.getAncho(); j++){
                        if(i == 0 || j == 0 || i == lab.getAlto() - 1 || j == lab.getAncho() - 1 ){
                            lab.actualizarPosicion(i,j, '-');

                        }
                    }
                }
                Percepcion percibir = new Percepcion(lab);
                int numObstaculos=0;
                mapa = percibir.percibir(x, y);
                for( int key : mapa.keySet()){
                    if(mapa.get(key) == '#'){
                        numObstaculos++;
                    }
                }

                if((x == 1 && y == 1) || (x == lab.getAlto() - 2 && y == 1) || (x == 1 && y == lab.getAncho() - 2) || (x == lab.getAlto() - 2 && y == lab.getAncho() - 2) ) {
                    numObstaculos -= 2;
                } else if (x == lab.getAlto() - 2 || y == lab.getAncho() - 2 || x == 1 || y == 1) {
                    numObstaculos--;
                }
                heuristica =  Math.abs(salida[0] - getX()) + Math.abs(salida[1] - getY()) + numObstaculos;
                break;
            // Manhattan + Euclidea
            case 3:
                heuristica = (Math.sqrt(Math.pow(salida[0] - getX(),2) + Math.pow(salida[1] - getY(),2))) + (salida[0] - getX()) + (salida[1] - getY());
                break;
        }

        if (activo) {
            if(getPadre() != null) {
                if (getPadre().getX() == x - 1) {
                    heuristica += 2;
                } else if (getPadre().getY() == y - 1) {
                    heuristica += 3;
                }
            }
        }
    }



    public double getHeuristica() {
        return heuristica;
    }

    public void setHeuristica(double heuristica) {
        this.heuristica = heuristica;
    }

    public int getCoste() {
        return coste;
    }

    public void setCoste(int coste) {
        this.coste = coste;
    }

    // Dos nodos se consideran iguales si tienen el mismo valor de x e y
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        NodoHeuristico n = (NodoHeuristico) obj;
        return x == n.x && y == n.y && valor == n.valor;
    }

    public int[] getPosicion(){
        int[] posicion = new int[2];
        posicion[0] = x;
        posicion[1] = y;
        return posicion;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public char getValor() {
        return valor;
    }

    public void setValor(char valor) {
        this.valor = valor;
    }

    public NodoHeuristico getPadre() {
        return Padre;
    }

    public void setPadre(NodoHeuristico padre) {
        Padre = padre;
    }
}


