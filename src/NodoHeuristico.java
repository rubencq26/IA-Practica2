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


    public NodoHeuristico(NodoHeuristico p, int x, int y, char valor) {
        Padre = p;
        this.x = x;
        this.y = y;
        this.valor = valor;
        heuristica = 100000;
        coste = 0;
    }
    public NodoHeuristico(NodoHeuristico p, int x, int y, char valor, int coste) {
        Padre = p;
        this.x = x;
        this.y = y;
        this.valor = valor;
        heuristica = 100000;
        this.coste = coste;
    }

    public void generarHeuristica(int tipo, int [] salida, Laberinto labe) {
        //0 Distancia a manhattan
        //1 Distancia Euclidea
        switch (tipo) {
            case 0:
                heuristica = (salida[0] - getX()) + (salida[1] - getY());
                break;
            case 1:
                heuristica = (Math.sqrt(Math.pow(salida[0] - getX(),2) + Math.pow(salida[1] - getY(),2)));
                break;
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
                heuristica =  (salida[0] - getX()) + (salida[1] - getY()) + numObstaculos;
                break;
            case 3:
                char [][] laberin = labe.getLaberintoChar();
                heuristica = (int) laberin[x][y];
                break;
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


