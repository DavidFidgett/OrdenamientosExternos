package polifase;

import extra.Archivos;
import extra.Heap;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class Polifase extends Archivos {
    private static final String relPath = abs;
    private static String f0;
    private static String f1;
    private static String f2;
    private static String f3;
    private static Queue<Integer> q0;
    private static Queue<Integer> qa;
    private static Queue<Integer> qb;

    public static void sort(String srcPath, int n){
        initialize(srcPath);
        System.out.print("\n f0:");
        printFirstLine(f0);
        int iteration = 0;
        int salir = -1;
        phaseOne(n);
        System.out.println("\n Fase 1: ");
        printLines(iteration);
        do {
            System.out.println("\n Iteracion #" + (iteration+1));
            lineab(iteration);
            merge(iteration++);
            if (q0.size() == 1){
                salir = iteration;
            } else {
                splitQueue();
                printLines(iteration);
            }
        } while (salir == -1);
        printSorted(salir);
    }

    private static void phaseOne(int n){
        try {
            Scanner s = new Scanner(new File(f0));
            s.useDelimiter(",");
            List<Integer> list = new LinkedList<>();
            int cont = 0;
            while (s.hasNext()){
                list.add(Integer.parseInt(s.next()));
                if (list.size() == n || !s.hasNext()){
                    int[] arr = listToIntArr(list);
                    Heap.sort(arr);
                    if (cont++ % 2 == 0){
                        qa.add(arr.length);
                        for (int i : arr) {
                            String num = Integer.toString(i);
                            addNumToFile(f1, num);
                        }
                    } else {
                        qb.add(arr.length);
                        for (int i : arr) {
                            String num = Integer.toString(i);
                            addNumToFile(f2, num);
                        }
                    }
                    list.clear();
                }
            }
            s.close();
            trimab(f1, f2);
        } catch (IOException e){
            System.out.println(" Error en fase 1.");
        }
    }

    private static void merge(int iteration){
        String fa = f0;
        String fb = f3;
        String fx = f1;
        String fy = f2;
        int breakLines = (iteration+1) / 2;
        if (iteration % 2 == 0){
            fa = f1;
            fb = f2;
            fx = f0;
            fy = f3;
            breakLines = iteration / 2;
        }
        try {
            Scanner sFa = new Scanner(new File(fa));
            Scanner sFb = new Scanner(new File(fb));
            sFa.useDelimiter(",");
            sFb.useDelimiter(",");
            int cont = 0;
            String f;
            for (int i = 0; i < breakLines; i++) {
                sFa.nextLine();
                sFb.nextLine();
            }
            while (!qa.isEmpty() && !qb.isEmpty()){
                int n1 = qa.poll();
                int n2 = qb.poll();
                q0.add(n1+n2);
                if (cont % 2 == 0){
                    f = fx;
                } else {
                    f = fy;
                }
                int i = 0, j = 0;
                String numFa = sFa.next();
                String numFb = sFb.next();
                int nFa = Integer.parseInt(numFa);
                int nFb = Integer.parseInt(numFb);
                while (i < n1 && j < n2){
                    if (nFa <= nFb){
                        addNumToFile(f, numFa);
                        i++;
                        if (i < n1){
                            numFa = sFa.next();
                            nFa = Integer.parseInt(numFa);
                        }
                    } else {
                        addNumToFile(f, numFb);
                        j++;
                        if (j < n2){
                            numFb = sFb.next();
                            nFb = Integer.parseInt(numFb);
                        }
                    }
                }
                while (i < n1){
                    addNumToFile(f, numFa);
                    i++;
                    if (i < n1){
                        numFa = sFa.next();
                    }
                }
                while (j < n2){
                    addNumToFile(f, numFb);
                    j++;
                    if (j < n2){
                        numFb = sFb.next();
                    }
                }
                cont++;
            }
            if (cont % 2 == 0){
                f = fx;
            } else {
                f = fy;
            }
            if (!qa.isEmpty()){
                q0.add(qa.poll());
            }
            if (!qb.isEmpty()){
                q0.add(qb.poll());
            }
            while (sFa.hasNext()){
                String num = sFa.next();
                addNumToFile(f, num);
            }
            while (sFb.hasNext()){
                String num = sFb.next();
                addNumToFile(f, num);
            }
            sFa.close();
            sFb.close();
            trimab(fa, fb);
        } catch (IOException e){
            System.out.println(" Error en merge.");
        }
    }

    // METODOS ORDENAMIENTO DESCENDIENTE

    public static void sortDesc(String srcPath, int n){
        initialize(srcPath);
        System.out.print("\n f0:");
        printFirstLine(f0);
        int iteration = 0;
        int salir = -1;
        phaseOneDesc(n);
        System.out.println("\n Fase 1: ");
        printLinesDesc(iteration);
        do {
            System.out.println("\n Iteracion #" + (iteration+1));
            lineab(iteration);
            mergeDesc(iteration++);
            if (q0.size() == 1){
                salir = iteration;
            } else {
                splitQueue();
                printLinesDesc(iteration);
            }
        } while (salir == -1);
        printSorted(salir);
    }

    private static void phaseOneDesc(int n){
        try {
            Scanner s = new Scanner(new File(f0));
            s.useDelimiter(",");
            List<Integer> list = new LinkedList<>();
            int cont = 0;
            while (s.hasNext()){
                list.add(Integer.parseInt(s.next()));
                if (list.size() == n || !s.hasNext()){
                    int[] arr = listToIntArr(list);
                    Heap.sortDesc(arr);
                    if (cont++ % 2 == 0){
                        qa.add(arr.length);
                        for (int i : arr) {
                            String num = Integer.toString(i);
                            addNumToFile(f1, num);
                        }
                    } else {
                        qb.add(arr.length);
                        for (int i : arr) {
                            String num = Integer.toString(i);
                            addNumToFile(f2, num);
                        }
                    }
                    list.clear();
                }
            }
            s.close();
            trimab(f1, f2);
        } catch (IOException e){
            System.out.println(" Error en fase 1.");
        }
    }

    private static void mergeDesc(int iteration){
        String fa = f0;
        String fb = f3;
        String fx = f1;
        String fy = f2;
        int breakLines = (iteration+1) / 2;
        if (iteration % 2 == 0){
            fa = f1;
            fb = f2;
            fx = f0;
            fy = f3;
            breakLines = iteration / 2;
        }
        try {
            Scanner sFa = new Scanner(new File(fa));
            Scanner sFb = new Scanner(new File(fb));
            sFa.useDelimiter(",");
            sFb.useDelimiter(",");
            int cont = 0;
            String f;
            for (int i = 0; i < breakLines; i++) {
                sFa.nextLine();
                sFb.nextLine();
            }
            while (!qa.isEmpty() && !qb.isEmpty()){
                int n1 = qa.poll();
                int n2 = qb.poll();
                q0.add(n1+n2);
                if (cont % 2 == 0){
                    f = fx;
                } else {
                    f = fy;
                }
                int i = 0, j = 0;
                String numFa = sFa.next();
                String numFb = sFb.next();
                int nFa = Integer.parseInt(numFa);
                int nFb = Integer.parseInt(numFb);
                while (i < n1 && j < n2){
                    if (nFa >= nFb){
                        addNumToFile(f, numFa);
                        i++;
                        if (i < n1){
                            numFa = sFa.next();
                            nFa = Integer.parseInt(numFa);
                        }
                    } else {
                        addNumToFile(f, numFb);
                        j++;
                        if (j < n2){
                            numFb = sFb.next();
                            nFb = Integer.parseInt(numFb);
                        }
                    }
                }
                while (i < n1){
                    addNumToFile(f, numFa);
                    i++;
                    if (i < n1){
                        numFa = sFa.next();
                    }
                }
                while (j < n2){
                    addNumToFile(f, numFb);
                    j++;
                    if (j < n2){
                        numFb = sFb.next();
                    }
                }
                cont++;
            }
            if (cont % 2 == 0){
                f = fx;
            } else {
                f = fy;
            }
            if (!qa.isEmpty()){
                q0.add(qa.poll());
            }
            if (!qb.isEmpty()){
                q0.add(qb.poll());
            }
            while (sFa.hasNext()){
                String num = sFa.next();
                addNumToFile(f, num);
            }
            while (sFb.hasNext()){
                String num = sFb.next();
                addNumToFile(f, num);
            }
            sFa.close();
            sFb.close();
            trimab(fa, fb);
        } catch (IOException e){
            System.out.println(" Error en merge.");
        }
    }

    //

    private static void initialize(String srcPath){
        createFolderAndFiles(srcPath);
        clearAllFiles();
        initQueue();
        copyContentsFromFile(srcPath, f0);
    }

    private static void createFolderAndFiles(String srcPath){
        String path = relPath + "poli_" + fileName(srcPath);
        createNewFolder(path);
        f0 = path + "/f0.txt";
        f1 = path + "/f1.txt";
        f2 = path + "/f2.txt";
        f3 = path + "/f3.txt";
        createFile(f0);
        createFile(f1);
        createFile(f2);
        createFile(f3);
    }

    private static void clearAllFiles(){
        clearFile(f0);
        clearFile(f1);
        clearFile(f2);
        clearFile(f3);
    }

    private static void initQueue(){
        q0 = new LinkedList<>();
        qa = new LinkedList<>();
        qb = new LinkedList<>();
    }

    private static int[] listToIntArr(List<Integer> list){
        int n = list.size();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = list.get(i);
        }
        return arr;
    }

    private static void splitQueue(){
        int cont = 0;
        while (!q0.isEmpty()){
            if (cont++ % 2 == 0){
                qa.add(q0.poll());
            } else {
                qb.add(q0.poll());
            }
        }
    }

    private static void printLines(int iterarion){
        if (iterarion % 2 == 0){
            System.out.print("\n f1:");
            printBlocks(f1, iterarion/2);
            System.out.print("\n f2:");
            printBlocks(f2, iterarion/2);
        } else {
            System.out.print("\n f0:");
            printBlocks(f0, (iterarion+1)/2);
            System.out.print("\n f3:");
            printBlocks(f3, (iterarion+1)/2);
        }
    }

    private static void printLinesDesc(int iterarion){
        if (iterarion % 2 == 0){
            System.out.print(" f1:");
            printBlocksDesc(f1, iterarion/2);
            System.out.print(" f2:");
            printBlocksDesc(f2, iterarion/2);
        } else {
            System.out.print(" f0:");
            printBlocksDesc(f0, (iterarion+1)/2);
            System.out.print(" f3:");
            printBlocksDesc(f3, (iterarion+1)/2);
        }
    }

    private static void printSorted(int iteration){
        if (iteration % 2 == 0){
            System.out.println(" Sorted in f1.");
            System.out.print(" f1:");
            printSortedLine(f1, iteration/2);
        } else {
            System.out.println(" Sorted in f0.");
            System.out.print(" f0");
            printSortedLine(f0, (iteration+1)/2);
        }
    }

    private static void trimab(String a, String b){
        trimLastChar(a);
        trimLastChar(b);
    }

    private static void lineab(int iteration){
        if (iteration % 2 == 0){
            insertEmptyLine(f0);
            insertEmptyLine(f3);
        } else {
            insertEmptyLine(f1);
            insertEmptyLine(f2);
        }
    }

}
