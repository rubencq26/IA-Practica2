//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Laberinto lab = new Laberinto();
        lab.cargarLaberinto("maze3.txt");
        lab.Pintar();
        GreedyBestFirstSearch g = new GreedyBestFirstSearch(lab);
        g.resolverLaberinto(0);
        g = new GreedyBestFirstSearch(lab);
        g.resolverLaberinto(1);
        g = new GreedyBestFirstSearch(lab);
        g.resolverLaberinto(2);



    }
}