import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class ProfundidadIterativa {
    private Percepcion percepcion;
    private Laberinto lab;
    private Stack<Nodo> abierto;
    private ArrayList<Nodo> cerrado;
    private int numNodosExpandidos;
    private int numPuntos;

    // Constructor
    public ProfundidadIterativa(Laberinto lab) {
        this.lab = new Laberinto(lab.getLaberintoChar());
        percepcion = new Percepcion(lab);
        numNodosExpandidos = 0;
        numPuntos = 0;
    }

    // Metodo para resolver el laberinto
    public void resolverLaberinto(){
        int limite = lab.getAlto() * lab.getAlto();

        for(int i = 1 ; i <= limite ; i++){
            if(resolverConLimite(i)){
                return;
            }
        }
        System.out.println("No se ha encontrado solución.");
        System.out.println("Número de nodos expandidos: " + numNodosExpandidos);



    }

    // Metodo para iterar segun un limite
    public boolean resolverConLimite(int Limite) {
        Nodo nodoInicial = new Nodo(null, 1, 1, 'E');
        abierto = new Stack<>(); // Pila abierta
        cerrado = new ArrayList<>(); // Lista cerrada
        abierto.push(nodoInicial);

        while (!abierto.isEmpty()) {
            Nodo actual = abierto.pop(); // Extraer nodo actual de la pila

            // Condicion de victoria
            if (actual.getValor() == 'S') {
                Nodo camino = actual.getPadre();
                while (camino != null && camino.getValor() != 'E') {
                    lab.actualizarPosicion(camino.getX(), camino.getY(), '.');
                    numPuntos++;
                    camino = camino.getPadre();
                }
                lab.Pintar();
                System.out.println("Número de nodos expandidos: " + numNodosExpandidos);
                System.out.println("Número de puntos pintados: " + numPuntos);
                return true;
            }

            cerrado.add(actual);

            HashMap<Integer, Character> percepcionMapa = percepcion.percibir(actual.getX(), actual.getY());

            for (int direccion : percepcionMapa.keySet()) {
                int x = actual.getX();
                int y = actual.getY();
                char valor = percepcionMapa.get(direccion);

                // Movimiento según dirección
                switch (direccion) {
                    case 0 -> y--; // izquierda
                    case 1 -> x--; // arriba
                    case 2 -> y++; // derecha
                    case 3 -> x++; // abajo
                }

                // Si no es pared
                if (valor != '#') {
                    Nodo hijo = new Nodo(actual, x, y, valor);
                    if(hijo.getIteracion() < Limite) {
                        if (!cerrado.contains(hijo) && !abierto.contains(hijo)) {
                            abierto.push(hijo);
                            numNodosExpandidos++;
                        }
                    }
                }
            }
        }
        return false;

    }

}


