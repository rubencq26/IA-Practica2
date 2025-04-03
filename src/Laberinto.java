import java.io.*;
import java.io.BufferedReader;
import java.util.Random;
import java.util.ArrayList;


public class Laberinto {
    private int alto; //entero que almacena el alto del laberinto
    private int ancho; //entero que almacena el ancho del laberinto
    private char[][] laberintoChar; //array bidimensional de char para crear un laberinto por cordenadas
    private Random rand; //Objeto tipo Random para generar numeros aleatorios

    //Constructor
    public Laberinto() {//constructor vacio de la clase Laberinto
        laberintoChar = null; //inicializamos el laberinto en nulo
        rand = new Random(); //rervamos memoria para el objeto rand
    }

    public Laberinto(char[][] laberintoChar) {//constructor de copia
        actualizarLaberinto(laberintoChar);
        rand = new Random();
    }

    public void actualizarPosicion(int x, int y, char signo ) {//actualiza una posicion del laberinto
        if(laberintoChar[x][y] != 'E' && laberintoChar[x][y] != 'S') {//Si la posicion no es salida ni entrada la cambiamos por el carater pasado por parametro
            laberintoChar[x][y] = signo;
        }

    }

    public void actualizarLaberinto(char[][] laberinto) {//actualiza el laberinto haciendo una copia de el
        this.alto = laberinto.length;
        this.ancho = laberinto[0].length;
        this.laberintoChar = new char[alto][ancho];

        for (int i = 0; i < alto; i++) {
            this.laberintoChar[i] = laberinto[i].clone(); // Copia profunda del array
        }
    }


    public int getAlto() {
        return alto;
    }
    public int getAncho() {
        return ancho;
    }
    public void setAlto(int alto) {
        this.alto = alto;
    }
    public void setAncho(int ancho) {
        this.ancho = ancho;
    }

    public void generaLaberinto(int alto, int ancho,int dificultad) { //Crea un laberinto pasandole un acho un alto y una dificultad que sera la probabilidad *10 de que se genere una pared
        this.alto = alto;
        this.ancho = ancho;
        this.laberintoChar = new char[alto][ancho];
        for(int i = 0;i < this.alto;i++) {
            for(int j = 0;j < this.ancho;j++) {
                if(i == 0 || i == this.alto -1 || j == 0 || j == this.ancho - 1) {//Si es un borde genera una pared
                    laberintoChar[i][j] = '#';
                }else{
                    int numAleatorio = rand.nextInt(10);//Segun el numero aleatorio que salga genera en una posicion xy una pared o un camino
                    if(numAleatorio < dificultad){
                        laberintoChar[i][j] = '#';
                    }else{
                        laberintoChar[i][j] = ' ';
                    }

                }

            }

        }
        laberintoChar[1][1] = 'E'; //Colocacion de entrada en la esquina superior izquierda
        laberintoChar[alto - 2][ancho - 2] = 'S'; //Colocacion salida esquina inferior derecha
    }

    public void Pintar(){ //Saca por pantalla una representacion del laberinto
        for(int i = 0; i < this.alto; i++) {
            for(int j = 0; j <= this.ancho; j++) {
                if(j == this.ancho) {
                    System.out.print('\n');
                }else{
                    System.out.print(laberintoChar[i][j]);
                }

            }
        }

    }

    public boolean esVacio(){ //Devuelve si hay o no laberinto
        if(laberintoChar == null){
            return true;
        }
        return false;
    }

    public char[][] getLaberintoChar() {
        return laberintoChar;
    }  //devuelve el array del laberinto

    public void almacenarLaberinto(String nombreArchivo) { //Almacena en un fichero el lanberinto
        String linea;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo))){
            for(int i = 0; i < this.alto; i++) {
                linea = new String();
                linea = "";
                for(int j = 0; j < this.ancho; j++) {
                    linea = linea + laberintoChar[i][j];
                }
                writer.write(linea);
                writer.newLine();
            }
            System.out.println("Archivo almacenado correctamente");
        }catch (IOException e){
            System.out.println("Ocurrio un error al escribir el archivo");
            e.printStackTrace();
        }
    }

    public void cargarLaberinto(String nombreArchivo) { //Extrae un laberinto guardado previamente en un fichero
        ArrayList<String> matriz = new ArrayList<>();
        String linea;
        try(BufferedReader reader = new BufferedReader(new FileReader(nombreArchivo))){
            while ((linea = reader.readLine()) != null){
                matriz.add(linea);
            }

            linea = matriz.get(0);
            this.alto = matriz.size();
            this.ancho = linea.length();
            laberintoChar = new char[alto][ancho];

            for(int i = 0;i < matriz.size();i++) {
                linea = matriz.get(i);
                for(int j = 0;j < linea.length();j++) {

                    laberintoChar[i][j] = linea.charAt(j);
                }
            }
            System.out.println("Archivo cargado correctamente");
        }catch ( IOException e){
            System.out.println("Ocurrio un error al escribir el archivo");
            e.printStackTrace();
        }
    }

    public int[] getSalida(){ //Devuelve la posicion de la salida del laberinto
        int[] salida = new int[2];
        salida[0] = this.alto - 2;
        salida[1] = this.ancho - 2;
        return salida;
    }
}