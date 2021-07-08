package extra;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.channels.FileChannel;
import java.util.Random;
import java.util.Scanner;

/**
 * La clase "Archivos" implementa varios metodos de ayuda para
 * la lectura, escritura, creacion, entre otros, de archivos.
 *
 * @author David Gutierrez Marin
 * @version 2.2
 */
public class Archivos {

    public static final String abs = getAbsolutePath();

    /**
     * Crea un nuevo archivo en la direccion ingresada.
     * @param pathname Direccion donde se creara el nuevo archivo.
     */
    public static void createFile(String pathname){
        try{
            File file = new File(pathname);
            if (file.createNewFile()){
                System.out.println(" Archivo creado: " + file.getName());
            } else {
                System.out.println(" El archivo ya existe.");
            }
        } catch (IOException e){
            System.out.println(" Error en crear archivo " +  pathname);
        }
    }

    /**
     * Borra los contenidos de la direccion del archivo ingresada.
     * @param pathname Direccion del archivo a limpiar.
     */
    public static void clearFile(String pathname){
        try{
            File file = new File(pathname);
            PrintWriter writer =  new PrintWriter(file);
            writer.print("");
            writer.close();
        } catch (IOException e){
            System.out.println(" Error al limpiar archivo.");
        }
    }

    /**
     * Agrega un numero al final de un archivo con una coma como separacion.
     * @param pathname Direccion del archivo.
     * @param num Numero a ingresar.
     */
    public static void addNumToFile(String pathname, String num){
        try {
            FileWriter fileWriter = new FileWriter(pathname, true);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            writer.append(num);
            writer.append(",");
            writer.close();
            fileWriter.close();
        } catch (IOException e){
            System.out.println(" Error al ingresar numero al archivo.");
        }
    }

    /**
     * Inserta un salto de linea en el archivo ingresado.
     * @param pathname Direccion del archivo.
     */
    public static void insertEmptyLine(String pathname){
        try {
            FileWriter fileWriter = new FileWriter(pathname, true);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            writer.newLine();
            writer.close();
            fileWriter.close();
        } catch (IOException e){
            System.out.println(" Error en insertar salto de linea.");
        }
    }

    /**
     * Borra el ultimo caracter que se encuentre en el archivo.
     * Usado principalmente para que, terminando de ingresar los valores
     * para una iteracion, borre la ultima coma agregada por el
     * metodo {@link #addNumToFile(String, String)}.
     * @param pathname Direccion del archivo.
     */
    public static void trimLastChar(String pathname){
        try {
            File file = new File(pathname);
            FileChannel fileChannel = new FileOutputStream(file, true).getChannel();
            fileChannel.truncate(fileChannel.size()-1);
            fileChannel.close();
        } catch (IOException e){
            System.out.println(" Error al borrar el ultimo caracter.");
        }
    }

    /**
     * Copia los contenidos de un archivo existente a otro archivo
     * existente, borrando los contenidos previos de este.
     * @param srcPath Direccion del archivo origen.
     * @param destPath Direccion del archivo destino.
     */
    public static void copyContentsFromFile(String srcPath, String destPath){
        try {
            File srcFile = new File(srcPath);
            FileWriter writer = new FileWriter(destPath);
            Scanner scanner = new Scanner(srcFile);
            while (scanner.hasNext()){
                writer.append(scanner.nextLine());
            }
            writer.flush();
            writer.close();
            scanner.close();
        } catch (IOException e){
            System.out.println(" Error al copiar de otro archivo.");
            e.printStackTrace();
        }
    }

    /**
     * Imprime los valores que se encuentran en el archivo
     * de la direccion ingresada.
     * @param pathname Direccion del archivo.
     */
    public static void printFirstLine(String pathname){
        try {
            Scanner s = new Scanner(new File(pathname));
            s.useDelimiter(",");
            StringBuilder sb = new StringBuilder(" {");
            while (s.hasNext()){
                sb.append(s.next());
                if (s.hasNext()){
                    sb.append(",");
                }
            }
            sb.append("}");
            String firstLine = sb.toString();
            System.out.println(firstLine);
            s.close();
        } catch (IOException e){
            System.out.println(" Error al imprimir primer linea del archivo.");
        }
    }

    /**
     * Imprime los valores que se encuentran en el archivo de la direccion
     * ingresada de acuerdo a la iteracion actual del ordenamiento (recordar
     * que en esta implimentacion, cada linea nueva en los archivos representa
     * una nueva iteracion del ordenamiento).
     * @param pathname Direccion del archivo.
     * @param iteration Numero de la iteracion que se realiza en el sort.
     */
    public static void printLine(String pathname, int iteration){
        try {
            Scanner s = new Scanner(new File(pathname));
            s.useDelimiter(",");
            StringBuilder sb = new StringBuilder(" {");
            for (int i = 0; i < iteration; i++) {
                s.nextLine();
            }
            while (s.hasNext()){
                sb.append(s.next());
                if (s.hasNext()){
                    sb.append(",");
                }
            }
            sb.append("}");
            String firstLine = sb.toString();
            System.out.println(firstLine);
            s.close();
        } catch (IOException e){
            System.out.println(" Error en la impresion de linea del archivo.");
        }
    }

    public static void printFullFile(String srcpath){
        try {
            Scanner sc = new Scanner(new File(srcpath));
            while (sc.hasNext()){
                System.out.println(sc.nextLine());
            }
            sc.close();
        } catch (IOException e){
            System.out.println(" Error al imprimir archivo.");
        }
    }

    /**
     * Imprime los bloques de valores ordenados que se encuentran en la
     * linea de la iteracion actual en el archivo que se encuentra en la
     * direccion ingresada. Su uso principal es imprimir las iteraciones en
     * los ordenamientos de Mezcla Equilibrada y de Polifase.
     * @param pathname Direccion del archivo.
     * @param iteration Numero de la iteracion que se realiza en el sort.
     */
    public static void printBlocks(String pathname, int iteration){
        try {
            Scanner s = new Scanner(new File(pathname));
            s.useDelimiter(",");
            StringBuilder sb = new StringBuilder(" {[");
            for (int i = 0; i < iteration; i++) {
                s.nextLine();
            }
            int prev = -1;
            while (s.hasNext()){
                String num = s.next();
                int n = Integer.parseInt(num);
                if (n < prev){
                    sb.deleteCharAt(sb.length() - 1);
                    sb.append("],[");
                }
                prev = n;
                sb.append(num);
                if (s.hasNext()){
                    sb.append(",");
                }
            }
            sb.append("]}");
            String blocks = sb.toString();
            System.out.println(blocks);
        } catch (IOException e){
            System.out.println(" Error al imprimir bloques de un archivo.");
        }
    }

    public static void printBlocksDesc(String pathname, int iteration){
        try {
            Scanner s = new Scanner(new File(pathname));
            s.useDelimiter(",");
            StringBuilder sb = new StringBuilder(" {[");
            for (int i = 0; i < iteration; i++) {
                s.nextLine();
            }
            int prev = 9999;
            while (s.hasNext()){
                String num = s.next();
                int n = Integer.parseInt(num);
                if (n > prev){
                    sb.deleteCharAt(sb.length() - 1);
                    sb.append("],[");
                }
                prev = n;
                sb.append(num);
                if (s.hasNext()){
                    sb.append(",");
                }
            }
            sb.append("]}");
            String blocks = sb.toString();
            System.out.println(blocks);
        } catch (IOException e){
            System.out.println(" Error al imprimir bloques de un archivo.");
        }
    }

    public static void printSortedLine(String pathname, int iteration){
        try {
            Scanner sc = new Scanner(new File(pathname));
            sc.useDelimiter(",");
            for (int i = 0; i < iteration; i++) {
                sc.nextLine();
            }
            System.out.print(" {");
            while (sc.hasNext()){
                System.out.print(sc.next());
                if (sc.hasNext()){
                    Thread.sleep(0);
                    System.out.print(",");
                }
            }
            System.out.println("}");
            sc.close();
        } catch (IOException | InterruptedException e){
            System.out.println(" Error al imprimir valores ordenados.");
        }
    }

    /**
     * Regresa el nombre del archivo ubicado en la direccion
     * ingresada, sin la terminacion ".txt".
     * @param srcPath Direccion del archivo.
     * @return Nombre del archivo sin extension.
     */
    public static String fileName(String srcPath){
        File file = new File(srcPath);
        String filename = file.getName();
        return filename.substring(0, filename.length()-4);
    }

    /**
     * Crea un nuevo folder en la direccion ingresada.
     * @param path Direccion donde se creara la nueva carpeta.
     */
    public static void createNewFolder(String path){
        File file = new File(path);
        if (file.mkdir()){
            System.out.println(" Carpeta creada con exito.");
        }
    }

    public static void createNewRandomFile(String path, int n){
        createFile(path);
        Random r = new Random();
        for (int i = 0; i < n; i++) {
            int num = r.nextInt(999) + 1;
            addNumToFile(path, Integer.toString(num));
        }
        trimLastChar(path);
    }

    public static boolean checkFile(String path){
        File file = new File(path);
        return file.exists();
    }

    public static String getAbsolutePath() {
        try {
            File file = new File(Archivos.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            String thisPath = file.getPath();
            String fileName = file.getName();
            int pathEnd = thisPath.length() - fileName.length();
            return thisPath.substring(0, pathEnd);
        } catch (URISyntaxException exception){
            System.out.println(" Error al obtener la direccion absoluta.");
        }
        return "";
    }

    public static String getThisPath(){
        try {
            File file = new File(Archivos.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            return file.getPath();
        } catch (URISyntaxException e) {
            System.out.println(" Error al obtener esta direccion.");
        }
        return "";
    }

}
