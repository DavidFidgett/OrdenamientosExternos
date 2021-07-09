package mezclaEquilibrada;

import extra.Archivos;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * Clase MezclaEquilibrada para ordenamiento externo.
 * Toma la direccion de un archivo de texto existente, genera un nuevo
 * archivo con los valores del archivo existente, y realiza un ordenamiento
 * de Mezcla Equilibrada sobre estos valores apoyandose del uso de archivos
 * auxiliares.
 * Los archivos auxiliares y el archivo donde se guardan los datos ya ordenados
 * se encuentran en la carpeta "\archivosAuxiliares".
 * El formato aceptado para el archivo ingresado es de texto libre (.txt).
 * El formato que deberan tener los valores en estos archivos seran numeros los
 * cuales deberan de estar separados con comas "," y sin espacios entre estos
 * caracteres.
 * Cada iteracion realizada durante el ordenamiento se podra observar en una nueva
 * linea tanto de los archivos auxiliares como del archivo que contiene las claves
 * iniciales a ordenar.
 *
 * @author David Gutierrez Marin
 * @version 1.2
 */
public class MezclaEquilibrada extends Archivos {
    /**
     * String con la direccion de la carpeta donde se encuentran los archivos utilizados
     * para la implementacion del ordenamiento por Mezcla Equilibrada.
     */
    private static String relPath;
    /**
     * String con la terminacion del tipo de archivo (.txt) que se trabajara.
     */
    private static final String txt = ".txt";
    /**
     * String con la direccion del archivo f0.
     */
    private static String f0;
    /**
     * String con la direccion del archivo f1.
     */
    private static String f1;
    /**
     * String con la direccion del archivo f2.
     */
    private static String f2;
    /**
     * Queue con el tamaño de los bloques de datos ordenados que se encuentran
     * en el archivo auxiliar f1.
     */
    private static Queue<Integer> f1Blocks;
    /**
     * Queue con el tamaño de los bloques de datos ordenados que se encuentran
     * en el archivo auxiliar f2.
     */
    private static Queue<Integer> f2Blocks;

    /**
     * Realiza un ordenamiento externo por el metodo de Mezcla Equilibrada.
     * Este ordenamiento se realiza a partir de los valores que se encuentren
     * en el archivo con la direccion ingresada. Se realiza una copia de estos
     * valores en el archivo f0, y se crean los archivos auxiliares f1 y f2 (en
     * el caso de que estos no existieran previamente). Cada iteracion de este
     * ordenamiento se guarda en una nueva linea de estos archivos mencionados.
     * El primer paso es realizar las particiones de bloques del archivo f0 y
     * guardar estos bloques que se encuentren ordenados en los archivos auxiliares
     * f1 y f2, alternandose entre estos, por medio del metodo
     * {@link #partition(int)}. Posteriormente se procedera a obtener
     * el numero de bloques que contiene cada uno de estos archivos auxiliares, asi
     * como el tamaño de los mismos con el metodo {@link #getBlockSize(String, int)}
     * los cuales nos seran de utilidad en la siguiente parte del ordenamiento que
     * consiste en mezclar los valores ya ordenados de cada bloque de los archivos
     * auxiliares f1 y f2 en un nuevo bloque ordenado en el archivo f0. Esto se
     * realizara a traves del metodo {@link #merge(int)}. Se agregaran los
     * saltos de linea correspondientes para el correcto funcionamiento del
     * ordenamiento, asi como para la visualizacion de las iteraciones en cada
     * uno de los archivos.
     * Este procedimiento se realizara hasta que en la ultima mezcla realizada, la
     * cantidad de bloques tanto del archivo auxiliar f1 como del archivo auxiliar
     * f2 sea de 1, lo cual nos indicara que ya se realizo la ultima mezcla posible
     * en el ordenamiento, mezclando el ultimo bloque de cada archivo auxiliar en un
     * solo bloque ordenado en el archivo f0.
     * Finalmente, en la ultima linea del archivo f0 podremos ver todos los valores
     * ya ordenados (solo ordenamiento ascendente hasta ahora.).
     * @param srcPath Direccion del archivo con las claves a ordenar.
     */
    public static void sort(String srcPath){
        createFiles(srcPath);
        clearAllFiles();
        copyFromOriginal(srcPath);
        int i = 0;
        int f1Partitions, f2Partitions;
        System.out.println("\n Lista original: ");
        System.out.print("\n f0:");
        printFirstLine(f0);
        do {
            partition(i);
            System.out.println("\n Iteracion #" + (i+1));
            System.out.print("\n f1:");
            printBlocks(f1, i);
            System.out.print("\n f2:");
            printBlocks(f2, i);
            f1Blocks = getBlockSize(f1, i);
            f2Blocks = getBlockSize(f2, i);
            f1Partitions = f1Blocks.size();
            f2Partitions = f2Blocks.size();
            insertEmptyLine(f0);
            merge(i++);
            System.out.print("\n f0:");
            printBlocks(f0, i);
            insertEmptyLine(f1);
            insertEmptyLine(f2);
        } while (f1Partitions != 1 || f2Partitions != 1);
        System.out.println("\n FINAL");
        System.out.print(" f0:");
        printLine(f0, i);
        trimAuxFiles();
    }

    /**
     * Este metodo realiza las particiones del archivo f0, guardando los
     * bloques que ya se encuentren ordenados en este archivo en los archivos
     * auxiliares f1 y f2, alternando entre estos dos cada bloque ordenado leido.
     * La forma en que se realiza esto es que se lee cada dato del archivo f0, y se
     * compara esta clave con el valor del numero leido previo. Si este valor leido
     * es mayor al valor anterior, significa que tanto este valor como el previo se
     * encuentran ordenados (esto en el caso de ordenamiento ascendente) y se guardara
     * este valor en el archivo auxiliar correspondiente. En caso de que el valor
     * actual sea menor que el valor anterior, nos indicara que este pertenece a un
     * nuevo bloque, y se guardara en el archivo auxiliar diferente al que se estaban
     * guardando los datos, y procedera a guardar los demas valores leidos que se
     * encuentren ordenados en este archivo auxiliar diferente al anterior.
     * La forma en como se implemento fue a traves de un contador. Cada vez que se
     * detectara un nuevo bloque, el contador aumentaba. Si este contador es un numero
     * par, las claves se almacenaran en el archivo auxiliar f1. Caso contrario que el
     * contador sea un numero non, los valores se guardaran en el archivo auxiliar
     * f2, logrando de esta manera poder alternar el almacenamiento de bloques entre
     * ambos archivos auxiliares.
     * Al igual que en la mayoria de los metodos utilizados en estos ordenamientos
     * externos, es importante conocer el numero de la iteracion que se esta
     * realizando para saber cuantas lineas de los archivos deben ser ignoradas
     * para la correcta lectura y ordenamiento de los valores.
     * @param iteration Numero de la iteracion que se realiza en el sort.
     */
    private static void partition(int iteration){
        try {
            Scanner sF0 = new Scanner(new File(f0));
            sF0.useDelimiter(",");
            for (int i = 0; i < iteration; i++) {
                sF0.nextLine();
            }
            int prev = -1;
            int cont = 0;
            while (sF0.hasNext()){
                String num = sF0.next();
                int n = Integer.parseInt(num);
                if (n < prev){
                    cont++;
                }
                prev = n;
                if (cont % 2 == 0){
                    addNumToFile(f1, num);
                } else {
                    addNumToFile(f2, num);
                }
            }
            trimLastChar(f1);
            trimLastChar(f2);
            sF0.close();
        } catch (IOException e){
            System.out.println(" Error en particion de valores.");
        }
    }

    /**
     * Metodo merge basado en la version utilizada con arreglos.
     * Este metodo realiza un merge entre los bloques de valores ya ordenados
     * que se encuentran en los archivos auxiliares f1 y f2, guardando estos
     * valores en el archivo f0 mientras estos estan siendo leidos de dichos
     * archivos auxiliares.
     * Apoyandose de los queues que contienen el tamaño de los bloques ordenados
     * de cada uno de los archivos auxiliares, se hara un ordenamiento merge de
     * los valores en estos bloques. Se leera el primer valor que contiene cada uno
     * de los bloques auxiliares, y se compararan entre ellos. El valor que sea el
     * menor de estos dos sera guardado en el archivo f0, y se leera el siguiente
     * valor que se encuentre en este archivo, hasta que sea el ultimo valor
     * de dicho bloque. Una vez que se hayan leido y guardado todos los valores de
     * cualquiera de los dos bloques, se tendra entendido que el resto de los valores
     * en el otro bloque son mayores (en el caso del ordenamiento ascendente) y se
     * procedera a leer y guardar estos valores en el archivo f0, generando un nuevo
     * bloque ordenado del tamaño de la suma de la cantidad de elementos de los dos
     * bloques que se mezclaron.
     * Una vez leidos todos los bloques de un archivo auxiliar, puede ocurrir el
     * caso de que uno de los dos archivos auxiliares tenga un bloque mas de valores
     * ordenados que el otro archivo. En esta situacion, terminada la mezcla de todos
     * los demas bloques, el bloque restante pasara a ser copiado en su totalidad
     * al archivo f0.
     * Al igual que en la mayoria de los metodos utilizados en estos ordenamientos
     * externos, es importante conocer el numero de la iteracion que se esta
     * realizando para saber cuantas lineas de los archivos deben ser ignoradas
     * para la correcta lectura y ordenamiento de los valores.
     * @param iteration Numero de la iteracion que se realiza en el sort.
     */
    private static void merge(int iteration){
        try {
            Scanner sF1 = new Scanner(new File(f1));
            Scanner sF2 = new Scanner(new File(f2));
            sF1.useDelimiter(",");
            sF2.useDelimiter(",");
            for (int i = 0; i < iteration; i++) {
                sF1.nextLine();
                sF2.nextLine();
            }
            while (!f1Blocks.isEmpty() && !f2Blocks.isEmpty()){
                int n1 = f1Blocks.poll();
                int n2 = f2Blocks.poll();
                int i = 0, j = 0;
                String numF1 = sF1.next();
                String numF2 = sF2.next();
                int nF1 = Integer.parseInt(numF1);
                int nF2 = Integer.parseInt(numF2);
                while (i < n1 && j < n2){
                    if (nF1 <= nF2){
                        addNumToFile(f0, numF1);
                        i++;
                        if (i < n1){
                            numF1 = sF1.next();
                            nF1 = Integer.parseInt(numF1);
                        }
                    } else {
                        addNumToFile(f0, numF2);
                        j++;
                        if (j < n2){
                            numF2 = sF2.next();
                            nF2 = Integer.parseInt(numF2);
                        }
                    }
                }
                while (i < n1){
                    addNumToFile(f0, numF1);
                    i++;
                    if (i < n1){
                        numF1 = sF1.next();
                    }
                }
                while (j < n2){
                    addNumToFile(f0, numF2);
                    j++;
                    if (j < n2){
                        numF2 = sF2.next();
                    }
                }
            }
            while (sF1.hasNext()){
                String num = sF1.next();
                addNumToFile(f0, num);
            }
            while (sF2.hasNext()){
                String num = sF2.next();
                addNumToFile(f0, num);
            }
            sF1.close();
            sF2.close();
            trimLastChar(f0);
        } catch (IOException e){
            System.out.println(" Error en merge.");
        }
    }

    /**
     * Dado un archivo con direccion f, este metodo generara y
     * retornara un Queue el cual guardara el tamaño de cada uno de los
     * bloques ordenados que contiene el archivo. La utilidad de este queue
     * es poder saber el numero de datos a leer durante el proceso de mezcla
     * para que durante la mezcla de los bloques de los archivos auxiliares
     * solo se lean los datos correspondientes a cada bloque, sin que se lea
     * algun valor adicional de dicho bloque, lo cual podria generar que algun
     * valor no sea agregado en el orden establecido, dandonos como resultado
     * una serie de datos no ordenados. Este metodo leera la cantidad de valores
     * que se encuentren en el archivo, guardando esta cantidad en un contador, hasta
     * que se detecte que el valor leido es menor al valor anterior, lo cual
     * representaria que inicio un nuevo bloque. Cuando esto ocurre, agregamos al
     * queue este contador que contendra el la cantidad de valores en el bloque, lo
     * cual correspondera al tamaño del mismo. El contador se reiniciara a 0 y se
     * procedera a obtener el tamaño del siguiente bloque.
     * Es importante conocer el numero de iteracion que se esta realizando en el
     * ordenamiento, para saber cuantas lineas deben de omitirse en la lectura
     * del archivo auxiliar.
     * @param f String con la direccion del archivo.
     * @param iteration Numero de la iteracion que se realiza en el sort.
     * @return Queue con los valores que representan el tamaño de cada bloque.
     */
    private static Queue<Integer> getBlockSize(String f, int iteration){
        Queue<Integer> blocks = new LinkedList<>();
        try {
            Scanner sF = new Scanner(new File(f));
            sF.useDelimiter(",");
            for (int i = 0; i < iteration; i++) {
                sF.nextLine();
            }
            int prev = -1;
            int cont = 0;
            while (sF.hasNext()){
                String num = sF.next();
                int n = Integer.parseInt(num);
                if (n < prev){
                    blocks.add(cont);
                    cont = 0;
                }
                prev = n;
                cont++;
            }
            blocks.add(cont);
            sF.close();
        } catch (IOException e){
            System.out.println(" Error al obtener tamaño de particiones.");
        }
        return blocks;
    }

    // METODOS PARA ORDENAMIENTO DESCENDIENTE

    public static void sortDesc(String srcPath){
        createFiles(srcPath);
        clearAllFiles();
        copyFromOriginal(srcPath);
        int i = 0;
        int f1Partitions, f2Partitions;
        System.out.println("\n Lista original: ");
        System.out.print("\n f0:");
        printFirstLine(f0);
        do {
            partitionDesc(i);
            System.out.println("\n Iteracion #" + (i+1));
            System.out.print("\n f1:");
            printBlocksDesc(f1, i);
            System.out.print("\n f2:");
            printBlocksDesc(f2, i);
            f1Blocks = getBlockSizeDesc(f1, i);
            f2Blocks = getBlockSizeDesc(f2, i);
            f1Partitions = f1Blocks.size();
            f2Partitions = f2Blocks.size();
            insertEmptyLine(f0);
            mergeDes(i++);
            System.out.print("\n f0:");
            printBlocksDesc(f0, i);
            insertEmptyLine(f1);
            insertEmptyLine(f2);
        } while (f1Partitions != 1 || f2Partitions != 1);
        System.out.println("\n FINAL");
        System.out.print(" f0:");
        printLine(f0, i);
        trimAuxFiles();
    }

    private static void partitionDesc(int iteration){
        try {
            Scanner sF0 = new Scanner(new File(f0));
            sF0.useDelimiter(",");
            for (int i = 0; i < iteration; i++) {
                sF0.nextLine();
            }
            int prev = 9999;
            int cont = 0;
            while (sF0.hasNext()){
                String num = sF0.next();
                int n = Integer.parseInt(num);
                if (n > prev){
                    cont++;
                }
                prev = n;
                if (cont % 2 == 0){
                    addNumToFile(f1, num);
                } else {
                    addNumToFile(f2, num);
                }
            }
            trimLastChar(f1);
            trimLastChar(f2);
            sF0.close();
        } catch (IOException e){
            System.out.println(" Error en particion de valores.");
        }
    }

    private static void mergeDes(int iteration){
        try {
            Scanner sF1 = new Scanner(new File(f1));
            Scanner sF2 = new Scanner(new File(f2));
            sF1.useDelimiter(",");
            sF2.useDelimiter(",");
            for (int i = 0; i < iteration; i++) {
                sF1.nextLine();
                sF2.nextLine();
            }
            while (!f1Blocks.isEmpty() && !f2Blocks.isEmpty()){
                int n1 = f1Blocks.poll();
                int n2 = f2Blocks.poll();
                int i = 0, j = 0;
                String numF1 = sF1.next();
                String numF2 = sF2.next();
                int nF1 = Integer.parseInt(numF1);
                int nF2 = Integer.parseInt(numF2);
                while (i < n1 && j < n2){
                    if (nF1 >= nF2){
                        addNumToFile(f0, numF1);
                        i++;
                        if (i < n1){
                            numF1 = sF1.next();
                            nF1 = Integer.parseInt(numF1);
                        }
                    } else {
                        addNumToFile(f0, numF2);
                        j++;
                        if (j < n2){
                            numF2 = sF2.next();
                            nF2 = Integer.parseInt(numF2);
                        }
                    }
                }
                while (i < n1){
                    addNumToFile(f0, numF1);
                    i++;
                    if (i < n1){
                        numF1 = sF1.next();
                    }
                }
                while (j < n2){
                    addNumToFile(f0, numF2);
                    j++;
                    if (j < n2){
                        numF2 = sF2.next();
                    }
                }
            }
            while (sF1.hasNext()){
                String num = sF1.next();
                addNumToFile(f0, num);
            }
            while (sF2.hasNext()){
                String num = sF2.next();
                addNumToFile(f0, num);
            }
            sF1.close();
            sF2.close();
            trimLastChar(f0);
        } catch (IOException e){
            System.out.println(" Error en merge.");
        }
    }

    private static Queue<Integer> getBlockSizeDesc(String f, int iteration){
        Queue<Integer> blocks = new LinkedList<>();
        try {
            Scanner sF = new Scanner(new File(f));
            sF.useDelimiter(",");
            for (int i = 0; i < iteration; i++) {
                sF.nextLine();
            }
            int prev = 9999;
            int cont = 0;
            while (sF.hasNext()){
                String num = sF.next();
                int n = Integer.parseInt(num);
                if (n > prev){
                    blocks.add(cont);
                    cont = 0;
                }
                prev = n;
                cont++;
            }
            blocks.add(cont);
            sF.close();
        } catch (IOException e){
            System.out.println(" Error al obtener tamaño de particiones.");
        }
        return blocks;
    }


    /**
     * Crea los archivos necesarios para la implementacion de
     * Mezcla Equilibrada Externa. Los archivos "f0.txt", "f1.txt" y "f2.txt"
     * seran los que utilizara el ordenamiento durante su implementacion.
     * Los archivos f1 y f2 almacenaran en cada iteracion los bloques de
     * valores ya ordenados que se encuentren en f0, alternando entre estos
     * el almacenamiento de dichos bloques, mientras el archivo f0 en cada
     * iteracion almacenara la mezcla de los bloques ordenados que se encuentren
     * en los archivos f1 y f2 para ir generando bloques de mayor tamaño que ya
     * se encuentren ordenados.
     */
    private static void createFiles(String srcPath){
        relPath = abs + "mezeq_" +fileName(srcPath);
        createNewFolder(relPath);
        f0 = relPath + "/f0" + txt;
        f1 = relPath + "/f1" + txt;
        f2 = relPath + "/f2" + txt;
        createFile(f0);
        createFile(f1);
        createFile(f2);
    }

    /**
     * Borra los contenidos de todos los archivos para poder
     * empezar un ordenamiento desde cero.
     */
    private static void clearAllFiles(){
        clearFile(f0);
        clearFile(f1);
        clearFile(f2);
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
        trimLastChar(f1);
        trimLastChar(f1);
        trimLastChar(f2);
        trimLastChar(f2);
    }

    /**
     * Copia los contenidos del archivo que se encuentre en la
     * direccion ingresada en el archivo "listaRadix.txt" para
     * poder realizar el ordenamiento sobre este archivo
     * sin modificar el archivo original.
     * @param srcPath Direccion del archivo original.
     */
    private static void copyFromOriginal(String srcPath){
        copyContentsFromFile(srcPath, f0);
    }
}
