//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        Laberinto lab = new Laberinto();
        lab.cargarLaberinto("maze1.txt");
        BusquedaAnchura ba = new BusquedaAnchura(lab);
        lab.Pintar();
        ba.resolverLaberinto(10000000);
    }
}