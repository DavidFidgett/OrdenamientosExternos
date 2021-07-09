package extra;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.Random;
import java.util.Scanner;

/**
 * La clase "Archivos" implementa varios metodos de ayuda para
 * la lectura, escritura, creacion, entre otros, de archivos.
 *
 * @author David Gutierrez Marin
 * @version 3.0
 */
public class Archivos {

    /**
     * String con la direccion absoluta del ejecutable obtenida con
     * {@link #getAbsolutePath()} para el facil acceso a esta, tomando
     * en cuenta que las demas clases son sub-clases de esta misma.
     */
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

    /**
     * Creara un nuevo archivo en la carpeta donde se encuentre
     * ubicado el archivo ejecutable, con el nombre ingresado y
     * con "n" cantidad de valores generados de manera aleatoria
     * en el rango especificado (1-999).
     * Si ya existe un archivo con este nombre, no se creara.
     * @param path Nombre del archivo a crear.
     * @param n Cantidad de valores aleatorios.
     */
    public static void createNewRandomFile(String path, int n){
        if (checkFile(path)){
            System.out.println(" Ya se encuentra un archivo con este nombre.");
        } else {
            createFile(path);
            Random r = new Random();
            for (int i = 0; i < n; i++) {
                int num = r.nextInt(999) + 1;
                addNumToFile(path, Integer.toString(num));
            }
            trimLastChar(path);
        }
    }

    /**
     * Verifica si el archivo en la direccion ingresada existe o no.
     * @param path Direccion del archivo a comprobar.
     * @return True si el archivo ya existe, False si no existe dicho archivo.
     */
    public static boolean checkFile(String path){
        File file = new File(path);
        return file.exists();
    }

    /**
     * FUNCION DE SUMA IMPORTANCIA PARA LA ADECUADA EJECUCION DEL PROGRAMA
     * UNA VEZ QUE SE ENCUENTRE EN FORMATO .JAR
     * Esta funcion creara un tipo de dato "File" utilizando como parametro
     * principal la direccion de esta misma clase.
     * A partir de la clase de este archivo, con
     * {@link Class#getProtectionDomain()} obtenemos el dominio protegido de
     * esta clase, para posteriormente con
     * {@link ProtectionDomain#getCodeSource()} obtener el codigo fuente, a
     * partir del cual obtendremos un URL con su ubicacion utilizando el
     * metodo {@link CodeSource#getLocation()}. Una vez que tengamos esta
     * URL, utilizamos el metodo {@link URL#toURI()} para darle un formato
     * apropiado a la URL que pueda generar problemas, tales como un caracter
     * que no sea valido, o  algun espacio, para finalmente utilizar el metodo
     * {@link File#getPath()} para obtener un String que contendra la direccion
     * absoluta dentro de la computadora donde se encuentre el archivo
     * ejecutable que estemos utilizando.
     * Con esta direccion podremos siempre saber la ubicacion de la carpeta que
     * contiene al archivo ejecutable .jar (substrayendo el nombre de este archivo
     * al String con la direccion absoluta) para poder tanto leer los archivos que
     * se encuentren en esta ubicacion, asi como para poder crear las carpetas y
     * los archivos que son fundamentales para la ejecucion del programa.
     * Esta ubicacion sera donde se crearan todos los archivos que genere el
     * programa, y tambien es la direccion donde se tienen que encontrar los
     * archivos con claves que se desean ordenar.
     * @author David Gutierrez Marin
     * @return Direccion absoluta donde se encuentra el archivo ejecutable .jar.
     */
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

}
