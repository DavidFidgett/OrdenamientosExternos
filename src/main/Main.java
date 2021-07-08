package main;

import extra.Archivos;
import extra.EDA2;
import mezclaEquilibrada.MezclaEquilibrada;
import polifase.Polifase;
import radix.Radix;

import java.util.Scanner;

public class Main extends Archivos {
    private static final String absPath = abs;


    public static void main(String[] args) throws InterruptedException{
        Scanner sc = new Scanner(System.in);
        int menu, sub;
        int sleepTime = 100;
        String filename, file;
        System.out.println();
        printDependencies();
        System.out.println();
        System.out.println(EDA2.eda2);
        System.out.println("\n Proyecto 1: Ordenamiento Externo");
        filename = getFileName();
        file = absPath + filename;
        while (!checkFile(file)){
            System.out.println(" El Archivo " + filename + " no existe.");
            filename = getFileName();
            file = absPath + filename;
        }
        do {
            System.out.println("\n Menu Ordenamientos \n");
            System.out.println(" Archivo Actual: " + filename + "\n");
            System.out.println(" 1.- Polifase");
            System.out.println(" 2.- Mezcla Equilibrada");
            System.out.println(" 3.- Radix");
            System.out.println(" 4.- Seleccionar nuevo archivo");
            System.out.println(" 5.- Salir");
            System.out.print("\n Ingrese numero de opcion y presione ENTER: ");
            menu = sc.nextInt();
            switch (menu){
                case 0:
                    String name = getFileName();
                    System.out.print("\n Ingrese numero de valores a generar: ");
                    int size = sc.nextInt();
                    createNewRandomFile(absPath+name, size);
                    break;
                case 1:
                    System.out.println("\n Polifase");
                    System.out.println(" 1.- Ordenamiento Ascendente");
                    System.out.println(" 2.- Ordenamiento Descendente");
                    System.out.print("\n Ingrese numero de opcion y presione ENTER: ");
                    sub = sc.nextInt();
                    if (sub == 1){
                        System.out.print("\n Ingrese el tamaño del bloque: ");
                        int n = sc.nextInt();
                        System.out.println("\n Ordenando " + filename + " por Polifase: ");
                        Thread.sleep(sleepTime);
                        Polifase.sort(file, n);
                    } else if (sub == 2) {
                        System.out.print("\n Ingrese el tamaño del bloque: ");
                        int n = sc.nextInt();
                        System.out.println("\n Ordenando " + filename + " por Polifase: ");
                        Thread.sleep(sleepTime);
                        Polifase.sortDesc(file, n);
                    }
                    break;
                case 2:
                    System.out.println("\n Mezcla Equilibrada");
                    System.out.println(" 1.- Ordenamiento Ascendente");
                    System.out.println(" 2.- Ordenamiento Descendente");
                    System.out.print("\n Ingrese numero de opcion y presione ENTER: ");
                    sub = sc.nextInt();
                    if (sub == 1){
                        System.out.println("\n Ordenando " + filename + " por Mezcla Equilibrada: ");
                        Thread.sleep(sleepTime);
                        MezclaEquilibrada.sort(file);
                    } else if (sub == 2){
                        System.out.println("\n Ordenando " + filename + " por Mezcla Equilibrada: ");
                        Thread.sleep(sleepTime);
                        MezclaEquilibrada.sortDesc(file);
                    }
                    break;
                case 3:
                    System.out.println("\n Radix");
                    System.out.println(" 1.- Ordenamiento Ascendente");
                    System.out.println(" 2.- Ordenamiento Descendente");
                    System.out.print("\n Ingrese numero de opcion y presione ENTER: ");
                    sub = sc.nextInt();
                    if (sub == 1){
                        System.out.println("\n ordenando " + filename + " por Radix: ");
                        Thread.sleep(sleepTime);
                        Radix.sort(file);
                    } else if (sub == 2){
                        System.out.println("\n ordenando " + filename + " por Radix: ");
                        Thread.sleep(sleepTime);
                        Radix.sortDesc(file);
                    }
                    break;
                case 4:
                    filename = getFileName();
                    file = absPath + filename;
                    while (!checkFile(file)){
                        System.out.println(" El Archivo " + filename + " no existe.");
                        filename = getFileName();
                        file = absPath + filename;
                    }
                    break;
                case 5:
                    System.out.println("\n Adios ((: \n");
                    break;
                default:
                    System.out.println("\n Opcion no valida.");
                    break;
            }
        } while (menu != 5);
        Thread.sleep(2000);
    }

    private static String getFileName(){
        Scanner sc = new Scanner(System.in);
        String filename;
        do {
            System.out.print("\n Ingrese nombre del archivo a ordenar y presione ENTER: ");
            filename = sc.next();
        } while (filename == null);
        if (!filename.endsWith(".txt")){
            filename = filename + ".txt";
        }
        return filename;
    }

}
