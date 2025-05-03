import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        int opc1, opc2, opc3;
        boolean activo = false;
        int limite;
        Scanner scanner = new Scanner(System.in);
        Laberinto laberinto = new Laberinto();
        do{
            menu1();
            opc1 = scanner.nextInt();
            if(opc1 == 4){
                break;
            }
            switch(opc1){
                case 1:
                    System.out.println("Introduzca el alto: ");
                    int x = scanner.nextInt();
                    if(x < 1){
                        break;
                    }
                    System.out.println("Introduzca el ancho: ");
                    int y = scanner.nextInt();
                    if(y < 1){
                        break;
                    }
                    System.out.println("Introduzca la dificultad(0-10): ");
                    int dificultad = scanner.nextInt();
                    if(dificultad < 0 || dificultad > 10){
                        break;
                    }
                    laberinto = new Laberinto();
                    laberinto.generaLaberinto(x,y,dificultad);
                    break;
                case 2:
                    System.out.println("Introduzca el nombre del fichero: ");
                    String nombre = scanner.next();
                    laberinto = new Laberinto();
                    laberinto.cargarLaberinto(nombre);
                    break;
                case 3:
                    if( laberinto != null ){
                        System.out.println("Introduzca el nombre del fichero a guardar: ");
                        String nombre2 = scanner.next();
                        laberinto.almacenarLaberinto(nombre2);
                    }else{
                        System.out.println("No se ha encontrado ningun laberinto");
                    }
                    break;
            }
            laberinto.Pintar();
            do{
                menu2();
                opc2 = scanner.nextInt();
                if(opc2 == 9){
                    break;
                }
                switch(opc2){
                    case 1:
                        BusquedaAnchura ba = new BusquedaAnchura(laberinto);
                        ba.resolverLaberinto();
                        break;
                    case 2:
                        BusquedaProfundidad bp = new BusquedaProfundidad(laberinto);
                        bp.resolverLaberinto();
                        break;
                    case 3:
                        GreedyBestFirstSearch gb = new GreedyBestFirstSearch(laberinto);
                        menu3();
                        opc3 = scanner.nextInt();
                        if(opc3 == 4){
                            break;
                        }
                        activo = menu4();
                        gb.resolverLaberinto(opc3,activo);
                        break;
                    case 4:
                        Astar a = new Astar(laberinto);
                        menu3();
                        opc3 = scanner.nextInt();
                        if(opc3 == 4){
                            break;
                        }
                        activo = menu4();
                        a.resolverLaberinto(opc3,activo);
                        break;
                    case 5:
                        IDAstar id = new IDAstar(laberinto);
                        menu3();
                        opc3 = scanner.nextInt();
                        if(opc3 == 4){
                            break;
                        }
                        activo = menu4();
                        System.out.println("Que limite de profundidad desea imponer: ");
                        limite = scanner.nextInt();
                        id.resolverLaberinto(opc3, activo, limite);
                        break;
                    case 6:
                        BusquedaLimite bl = new BusquedaLimite(laberinto);
                        System.out.println("Que limite de profundidad desea imponer: ");
                        limite = scanner.nextInt();
                        bl.resolverLaberinto(limite);
                        break;
                    case 7:
                        ProfundidadIterativa pr = new ProfundidadIterativa(laberinto);
                        pr.resolverLaberinto();
                        break;
                    case 8:
                        BusquedaBidireccional bb = new BusquedaBidireccional(laberinto);
                        bb.resolverLaberinto();
                        break;
                }
            }while (opc2 != 9);

        }while(opc1 == 4);






    }
    public static void menu1(){
        System.out.println("1. Generar Laberinto");
        System.out.println("2. Cargar Laberinto");
        System.out.println("3. Almacenar Laberinto");
        System.out.println("4. Salir");
        System.out.println("Elija una opción: ");

    }

    public static void menu2(){
        System.out.println("1. Busqueda en Anchura");
        System.out.println("2. Busqueda en Profundidad");
        System.out.println("3. Greedy Best Fits Seach");
        System.out.println("4. A*");
        System.out.println("5. IDA*");
        System.out.println("6. Busqueda en Profundidad con Limite");
        System.out.println("7. Busqueda en Profundidad Iterativa");
        System.out.println("8. Busqueda Bidireccional");
        System.out.println("9. Atrás");
        System.out.println("Elija una opción: ");
    }

    public static void menu3(){
        System.out.println("0. Camino a Manhattan");
        System.out.println("1. Euclidea");
        System.out.println("2. Manhattan + numero de paredes alrededor");
        System.out.println("3. Manhattan + Euclidea");
        System.out.println("4. Atrás");
        System.out.println("Elija una opción: ");
    }

    public static boolean menu4(){
        System.out.println("¿Desea activar la opcion que el coste a la derecha valga 2 y hacia abajo 3? (1.Si  - 2. No ");
        Scanner scanner = new Scanner(System.in);
        int opc1 = scanner.nextInt();
        boolean activo = false;
        if(opc1 == 1){
            activo = true;
        }

        return activo;
    }

}