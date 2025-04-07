import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

public class BusquedaProfundidad {
    private Percepcion percepcion;
    private Laberinto laberinto;
    private Stack<Nodo> pilaAbierta;
    private HashSet<Nodo> colaCerrada;
    private int numExpansiones;
    private boolean win;
    private int numPuntos;

    public BusquedaProfundidad(Laberinto lab) {
        laberinto = new Laberinto(lab.getLaberintoChar());
        percepcion = new Percepcion(laberinto);
        pilaAbierta = new Stack<>();
        colaCerrada = new HashSet<>();
        numExpansiones = 0;
        numPuntos = 0;
    }

    public void resolverLaberinto(int maxIteraciones) {
        int contador = 0;
        Nodo nodo = new Nodo(null, 1,1,'E');

        pilaAbierta.push(nodo);
        colaCerrada.add(nodo);
        while(!pilaAbierta.isEmpty()) {
            if(contador == maxIteraciones) {
                break;
            }
            contador++;
            nodo = pilaAbierta.peek();
            pilaAbierta.pop();
            if(nodo.getValor() == 'S'){
                win = true;
                break;
            }
            HashMap<Integer, Character> map = percepcion.percibir(nodo.getX(), nodo.getY());
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
                Nodo nuevo = new Nodo(nodo, x, y, map.get(key));
                boolean encontrado = false;
                for(Nodo n : colaCerrada){
                    if(n.equals(nuevo)){
                        encontrado = true;
                    }
                }

                if(!encontrado){

                    colaCerrada.add(nuevo);
                    pilaAbierta.push(nuevo);
                    numExpansiones++;
                }

            }
        }
        if(win){
            nodo = nodo.getPadre();
            while(nodo != null) {

                laberinto.actualizarPosicion(nodo.getX(), nodo.getY(), '.');
                nodo = nodo.getPadre();
                numPuntos++;
            }
            laberinto.Pintar();
            System.out.println("Numero de nodos expandidos: " + numExpansiones);
            System.out.println("Numero de puntos pintados: " + numPuntos);
        }else{
            System.out.println("No se pudo resolver laberinto");
        }
    }
}


