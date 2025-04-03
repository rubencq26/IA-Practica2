import java.util.HashMap;

public class Percepcion {
    private HashMap<Integer,Character> percepcion; //0=izquierda ; 1=arriba ; 2=derecha ; 3=abajo
    private char laberinto[][];
    Laberinto lab;
    public Percepcion(Laberinto l) {//Constructor
        this.lab = l;
        this.laberinto = new char[lab.getLaberintoChar().length][lab.getLaberintoChar()[0].length];//Reserva de memoria de laberinto

        for (int i = 0; i < laberinto.length; i++) { //Clona el laberinto pasadp parametro a la variable laberinto
            this.laberinto[i] = lab.getLaberintoChar()[i].clone(); // Copia cada fila de la matriz
        }
    }

    public void actualizarLaberinto(char[][] laberinto) {//Funcion para actualizar el laberinto metiendo otro laberinto
        this.laberinto = laberinto;
    }
    public HashMap<Integer,Character> percibir(int i, int j) {//metodo usado para percibir lo que hay alrededor de una posicion del laberinto
        percepcion = new HashMap<>(); //Reservamos memoria para el mapa percepcion
        percepcion.put(0,laberinto[i][j-1]);//izquierda
        percepcion.put(1, laberinto[i-1][j]);//arriba
        percepcion.put(2, laberinto[i][j+1]);//derecha
        percepcion.put(3, laberinto[i+1][j]);//abajo
        return percepcion; //devolvemos el mapa obtenido
    }

}