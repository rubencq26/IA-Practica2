import java.util.ArrayList;

public class Nodo {
    private Nodo Padre;
    private ArrayList<Nodo> Hijos;
    private int x, y;
    private char valor;

    public Nodo(Nodo p, int x, int y, char valor) {
        Padre = p;
        Hijos = new ArrayList<>();
        this.x = x;
        this.y = y;
        this.valor = valor;
    }

    public Nodo getPadre() {
        return Padre;
    }

    public void setPadre(Nodo padre) {
        Padre = padre;
    }

    public ArrayList<Nodo> getHijos() {
        return Hijos;
    }

    public Nodo getHijo(int pos) {
        if(pos > -1 && pos < Hijos.size()) {
            return Hijos.get(pos);
        }else{
            return null;
        }
    }

    public void setHijos(ArrayList<Nodo> hijos) {
        Hijos = hijos;
    }

    public void setHijo(Nodo h){
        Hijos.add(h);
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

    public int[] getPosicion(){
        int[] posicion = new int[2];
        posicion[0] = x;
        posicion[1] = y;
        return posicion;
    }


    public boolean equals(Nodo n){
        if(x == n.getX() && y == n.getY() && valor == n.getValor()){
            return true;
        }else{
            return false;
        }
    }
}
