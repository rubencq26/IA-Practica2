//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Laberinto lab = new Laberinto();
        lab.cargarLaberinto("maze3.txt");
        BusquedaAnchura ba = new BusquedaAnchura(lab);
        BusquedaProfundidad bp = new BusquedaProfundidad(lab);
        lab.Pintar();
        ba.resolverLaberinto();
        bp.resolverLaberinto(1000000000);

    }
}