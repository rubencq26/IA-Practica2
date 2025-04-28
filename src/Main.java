//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Laberinto lab = new Laberinto();
        lab.cargarLaberinto("maze3.txt");
        lab.Pintar();
        Astar a = new Astar(lab);
        a.resolverLaberinto(0, true);
        a = new Astar(lab);
        a.resolverLaberinto(2, true);
        a = new Astar(lab);
        a.resolverLaberinto(1, true);
        a = new Astar(lab);
        a.resolverLaberinto(4, true);
        a = new Astar(lab);
        a.resolverLaberinto(0, false);
        a = new Astar(lab);
        a.resolverLaberinto(2, false);
        a = new Astar(lab);
        a.resolverLaberinto(1, false);
        a = new Astar(lab);
        a.resolverLaberinto(4, false);
        a = new Astar(lab);



    }
}