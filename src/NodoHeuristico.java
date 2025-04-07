public class NodoHeuristico extends Nodo {
    private double heuristica;

    public NodoHeuristico(Nodo p, int x, int y, char valor) {
        super(p,x,y,valor);
    }

    public void generarHeuristica(int tipo, int [] salida) {
        //0 Distancia a manhattan
        //1 Distancia Euclidea
        switch (tipo) {
            case 0:
                heuristica = (salida[0] - getX()) + (salida[1] - getY());
                break;
            case 1:
                heuristica = (Math.sqrt(Math.pow(salida[0] - getX(),2) + Math.pow(salida[1] - getY(),2)));
        }
    }

    public double getHeuristica() {
        return heuristica;
    }
}
