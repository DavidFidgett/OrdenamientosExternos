package radix;

import extra.Archivos;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Clase Radix para ordenamiento externo.
 * Toma la direccion de un archivo de texto existente, genera
 * un nuevo archivo con los valores del archivo existente, y realiza
 * un ordenamiento Radix sobre estos valores en el archivo nuevo apoyandose
 * de archivos auxiliares para el almacenamiento de las claves en orden
 * de acuerdo al valor de sus digitos individuales.
 * Los archivos auxiliares y el archivo con las claves ordenadas se
 * encuentran en la carpeta "\archivosAuxiliares".
 * El formato aceptado para el archivo ingresado es de texto libre (.txt).
 * El formato que deben de tener los valores dentro de estos archivos seran
 * numeros de hasta tres digitos en el rango 1-999.
 * Estos numeros deberan estar separados con comas "," y sin espacios
 * entre estos caracteres.
 * Cada iteracion realizada durante el ordenamiento se podra observar en una nueva
 * linea tanto de los archivos auxiliares como del archivo que contiene las claves
 * iniciales a ordenar.
 *
 * @author David Gutierrez Marin
 * @version 1.4
 * Fecha: 07/07/2021
 */
public class Radix extends Archivos {
    /**
     * String con la direccion de la carpeta donde se encuentra el archivo ordenado.
     */
    private static String relPath;
    /**
     * String con la direccion de la carpeta donde se encuentran los archivos auxiliares.
     */
    private static String auxPath;
    /**
     * String con la terminacion del tipo de archivo (.txt) que se trabajara.
     */
    private static final String txt = ".txt";
    /**
     * String con el nombre del archivo que contendra los valores ordenados.
     */
    private static final String fileSorted = "listaRadix";
    /**
     * String con la direccion del archivo donde se encuentra el archivo ordenado.
     */
    private static String list;

    private static final int maxLength = 3;

    /**
     * Realiza un ordenamiento Radix externo ascendente a partir de los
     * valores que se encuentren en el archivo con la direccion
     * ingresada. Hace una copia de este archivo en la carpeta
     * files  y crea los archivos
     * auxiliares que necesita el ordenamiento para su implementacion
     * (si es que no se han creado previamente). Las iteraciones del
     * ordenamiento se guardan en nuevas lineas en cada uno de los archivos
     * utilizados en el algoritmo, y el archivo final con los valores
     * ordenados lo podremos observar en la ultima linea del
     * archivo "listaRadix.txt".
     * Este algoritmo funciona (hasta el momento) para valores con una
     * longitud de 3 digitos (falta la implementacion para valores < 100).
     * @param srcPath Direccion del archivo con las claves a ordenar.
     */
    public static void sort(String srcPath){
        createFiles(srcPath);
        clearAllFiles();
        copyFromOriginal(srcPath);
        int iter = 0;
        try {
            for (int i = maxLength-1; i >= 0; i--) {
                System.out.println("\n Iteracion #" +(iter+1));
                System.out.print("\n list:");
                printLine(list, iter);
                Scanner s = new Scanner(new File(list));
                s.useDelimiter(",");
                for (int j = 0; j < 2 - i; j++) {
                    s.nextLine();
                }
                while (s.hasNext()){
                    String num = s.next();
                    String numAux = paddingZeros(num);
                    char c = numAux.charAt(i);
                    String f = auxPath + c + txt;
                    addNumToFile(f, num);
                }
                trimAuxFiles();
                insertEmptyLine(list);
                System.out.println("\n Aux Files: ");
                printLineAllAux(iter);
                for (int j = 0; j < 10; j++) {
                    String f = auxPath + j + txt;
                    Scanner sf = new Scanner(new File(f));
                    for (int k = 0; k < 2 - i; k++) {
                        sf.nextLine();
                    }
                    while (sf.hasNext()){
                        addNumToFile(list, sf.next());
                    }
                    sf.close();
                }
                iter++;
                emptyLineAllAux();
                trimLastChar(list);
                s.close();
            }
            trimAuxFiles();
            trimAuxFiles();
            System.out.println("\n Archivo ordenado: ");
            System.out.print("\n list:");
            printSortedLine(list, iter);
        } catch (IOException e){
            System.out.println(" Error en el ordenamiento.");
        }
//        return new File(list);
    }

    /**
     * Realiza un ordenamiento Radix descendiente externo a partir de los
     * valores que se encuentren en el archivo con la direccion
     * ingresada. Hace una copia de este archivo en la carpeta
     * files y crea los archivos
     * auxiliares que necesita el ordenamiento para su implementacion
     * (si es que no se han creado previamente). Las iteraciones del
     * ordenamiento se guardan en nuevas lineas en cada uno de los archivos
     * utilizados en el algoritmo, y el archivo final con los valores
     * ordenados lo podremos observar en la ultima linea del
     * archivo "listaRadix.txt".
     * Este algoritmo funciona (hasta el momento) para valores con una
     * longitud de 3 digitos (falta la implementacion para valores < 100).
     * @param srcPath Direccion del archivo con las claves a ordenar.
     */
    public static void sortDesc(String srcPath){
        // Generamos los archivos auxiliares a utilizar, si es que estos
        // no existen.
        createFiles(srcPath);
        // Limpiamos el contenido de los archivos auxiliares, asi en caso de
        // que contengan algo, no afecten el ordenamiento.
        clearAllFiles();
        // Copiamos el contenido del archivo que se encuentra en la direccion
        // srcPath a nuestro archivo "listaRadix.txt" para realizar el
        // ordenamiento sobre este archivo, para no modificar el archivo
        // original ingresado.
        copyFromOriginal(srcPath);
        // Contador para saber el numero de la iteracion que estamos realizando
        // y que nos servira para la impresion de los contenidos de los archivos.
        int iter = 0;
        // Iniciamos el ciclo para leer el digito en la posicion
        // i(empezando con el menos significativo) de cada
        // valor en nuestro archivo y, acorde con el valor de este
        // lo agregamos a si "queue" (archivo auxiliar) correspondiente.
        try {
            for (int i = maxLength-1; i >= 0; i--) {
                System.out.println("\n Iteracion #" +(iter+1));
                System.out.print("\n list:");
                printLine(list, iter);
                Scanner s = new Scanner(new File(list));
                s.useDelimiter(","); // Con este metodo usamos las comas como separacion.
                for (int j = 0; j < 2 - i; j++) {
                    /* Usamos este ciclo para saltarnos lineas en la lectura del
                     Archivo original y leer solo los datos de la iteracion que
                     nos interesa.
                     En la primera iteracion no realizara ningun salto de linea
                     porque queremos leer los valores desde el inicio, pero a partir
                     de la segunda iteracion, queremos ignorar esa primera linea, por que
                     en la segunda linea estan los valores en el orden que necesitamos para
                     esa iteracion, y asi sucesivamente.
                     */
                    s.nextLine();
                }
                /*
                Durante la lectura de nuestro archivo original, vamos obteniendo los valores
                de esta y asignandolos a sus archivos auxiliares hasta que lleguemos al final
                del archivo original.
                 */
                while (s.hasNext()){
                    String num = s.next();
                    // Verificamos si el numero leido tiene menos digitos que el maximo de
                    // digitos necesarios, y en caso de que no tenga la longitud maxima, se
                    // le agregaran "0"s a la izquierda de la cadena del numero para su
                    // correcto ordenamiento en la funcion.
                    String numAux = paddingZeros(num);
                    // Leemos el digito en la posicion indicada por el iterador "i"
                    // para el ordenamiento.
                    char c = numAux.charAt(i);
                    String f = auxPath + c + txt;
                    addNumToFile(f, num);
                }
                /*
                Terminada la lectura del archivo original, borramos el ultimo ","
                ingresado durante la lectura del archivo original de todos los
                archivos auxiliares, e insertamos un salto de linea al archivo original
                para que en el siguiente paso, al desencolar los datos, queden guardados
                en una nueva linea para su lectura en la siguiente iteracion.
                 */
                trimAuxFiles();
                insertEmptyLine(list);
                // Imprimimos el contanido de los archivos auxiliares.
                System.out.println("\n Aux Files: ");
                printLineAllAux(iter);
                /*
                Leemos los archivos auxiliares en orden (si queremos ordenamiento ascendente
                los leemos del 0-9, y en caso de ordenamiento descendente los leemos del
                9-0) y vamos guardando de manera ordenada estos valores en la nueva linea de
                nuestro archivo que contiene los valores originales.
                 */
                for (int j = 9; j >= 0; j--) {
                    String f = auxPath + j + txt;
                    Scanner sf = new Scanner(new File(f));
                    /*
                    Misma situacion que con los saltos de linea de la iteracion sobre el
                    archivo original, pero ahora sobre los archivos auxiliares.
                     */
                    for (int k = 0; k < 2 - i; k++) {
                        sf.nextLine();
                    }
                    while (sf.hasNext()){
                        addNumToFile(list, sf.next());
                    }
                    sf.close();
                }
                /*
                Al final de la lectura de los archivos auxiliares, añadimos un
                salto de linea a todos estos archivos para que en la siguiente
                iteracion se guarden los valores en una nueva linea en el archivo.
                Ademas, removemos la "," que se guarda en el archivo original.
                 */
                iter++;
                emptyLineAllAux();
                trimLastChar(list);
                s.close();
            }
            trimAuxFiles();
            trimAuxFiles();
            System.out.println("\n Archivo ordenado: ");
            System.out.print("\n list:");
            printSortedLine(list, iter);
        } catch (IOException e){
            System.out.println(" Error en el ordenamiento.");
        }
    }

    /**
     * Crea los archivos necesarios para la implementacion de
     * Radix Sort externo. Los archivos "f0.txt", "f1.txt", ..., "f9.txt"
     * se usaran como los Queues que almacenaran los valores en relacion
     * con el digito que se este comprobando en cada iteracion, en el
     * respectivo archivo con su digito correspondiente.
     * De igual manera genera el archivo "listaRadix.txt" que guardara
     * los valores iniciales a ordenar, asi como el dequeue que ocurra
     * en cada iteracion hasta que en ese mismo archivo se tengan todos
     * los valores ordenados.
     */
    private static void createFiles(String srcPath){
        relPath = abs + "radix_" + fileName(srcPath);
        createNewFolder(relPath);
        list = relPath + "/" + fileSorted + txt;
        auxPath = relPath + "/f";
        createFile(list);
        for (int i = 0; i < 10; i++) {
            String f = auxPath + i + txt;
            createFile(f);
        }
    }

    /**
     * Borra los contenidos de todos los archivos para poder
     * empezar un ordenamiento desde cero.
     */
    private static void clearAllFiles(){
        clearFile(list);
        for (int i = 0; i < 10; i++) {
            String f = auxPath + i + txt;
            clearFile(f);
        }
    }

    /**
     * Copia los contenidos del archivo que se encuentre en la
     * direccion ingresada en el archivo "listaRadix.txt" para
     * poder realizar el ordenamiento sobre este archivo
     * sin modificar el archivo original.
     * @param srcPath Direccion del archivo original.
     */
    private static void copyFromOriginal(String srcPath){
        copyContentsFromFile(srcPath, list);
    }

    /**
     * Borra la ultima "," ingresada por la funcion
     * {@link Archivos#addNumToFile(String, String)}
     * al final de cada iteracion que se realiza sobre el
     * archivo original en todos los archivos auxiliares.
     * Esto para evitar problemas de lectura de los valores
     * durante el ordenamiento.
     */
    private static void trimAuxFiles(){
        for (int i = 0; i < 10; i++) {
            String f = auxPath + i + txt;
            trimLastChar(f);
        }
    }

    /**
     * Escribe un salto de linea en todos los archivos auxiliares
     * despues de la iteracion que ocurre en el ordenamiento en el
     * que se "desencolan" los valores de estos archivos auxiliares
     * para ser guardados en el archivo original.
     * El salto de linea nos indicara una nueva iteracion del
     * algoritmo en los archivos utilizados.
     */
    private static void emptyLineAllAux(){
        for (int i = 0; i < 10; i++) {
            String f = auxPath + i + txt;
            insertEmptyLine(f);
        }
    }

    /**
     * En el caso de que la longitud del valor ingresado sea menor a la
     * longitud maxima, se agregan ceros a la izquierda de la cadena hasta
     * que tenga la longitud maxima, esto con la finalidad de que al realizar
     * el ordenamiento, puedan asignarse al Queue correspondiente al valor
     * 0 (minimo), y que tambien tengan la longitud suficiente para que al mo-
     * mento de acomodar los valores, no se genere un error de intento de acceso
     * a un indice no valido.
     * @param val String del numero al que se le agregaran ceros a la izquierda.
     * @return String de longitud maxima con el numero ingresado con ceros a la izquierda
     */
    private static String paddingZeros(String val){
        StringBuilder sb = new StringBuilder();
        while (sb.length() < maxLength - val.length()){
            sb.append('0');
        }
        sb.append(val);
        return sb.toString();
    }

    /**
     * Imprime el contenido de todos los archivos auxiliares que
     * se utilizan en la implementacion del ordenamiento, de acuerdo
     * a la iteracion que se esta realizando.
     * @param iteration Numero de la iteracion que se realiza en el sort.
     */
    private static void printLineAllAux(int iteration){
        for (int i = 0; i < 10; i++) {
            String f = auxPath + i + txt;
            System.out.print("\n f" + i + ":");
            printLine(f, iteration);
        }
    }

}
