//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Laberinto lab = new Laberinto();
        lab.cargarLaberinto("maze3.txt");
        lab.Pintar();
        IDAstar a = new IDAstar(lab);
        a.resolverLaberinto(1, false,100000000);





    }
}