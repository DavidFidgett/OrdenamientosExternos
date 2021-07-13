# Ordenamientos Externos

Proyecto Ordenamientos Externos
Fecha: 8 de Julio de 2021

La version requerida de Java Runtime Environment (JRE) para
poder ejecutar este programa es la version 11.01

Si se desea conocer la version con la que se cuenta, escribir y
ejecutar el siguiente comando en el Simbolo de Sistema o en la
Terminal, dependiendo del sistema operativo que se este utilizando:

`java -version`

Para correr el programa .jar:

En Windows:
 Opcion 1:
 Doble click en en archivo "run.bat".
 El programa se ejecutara automaticamente en la consola.

 Opcion 2:
 Abrir el Simbolo de Sistema (windows + r, cmd, ENTER) y
 dirigirse hasta la ubicacion donde se encuentra el archivo
 OrdenamientosExternos.jar.

 Si se tiene problemas al navegar en el Simbolo de
 Sistema, para acceder rapidamente a la ubicacion se pude
 escribir "cd" seguido de un espacio y posteriormente arrastrar
 la carpeta que contiene el ejecutable .jar hacia el Simbolo
 de Sistema, esto copiara la direccion donde se encuetra el
 archivo OrdenamientosExternos.jar al Simbolo de Sistema.
 Posteriormente solo se presiona ENTER y se habra ubicado en
 la direccion adecuada.

 Una vez que se encuentra en esta ubicacion, escribir en la
 linea de comandos lo siguiente para ejecutar el programa:

 `java -jar OrdenamientosExternos.jar`

En Linux:
 Opcion 1:
 Doble click en el archivo ".sh".
 El programa se ejecutara automaticamente en la terminal.

 Opcion 2:
 Abrir la terminal (alt + t en la mayoria de distribuciones) y
 dirigirse hacia la ubicacion del archivo OrdenamientosExternos.jar.

 Si se tiene dificultad navegando los directorios en la terminal, se
 puede acceder de manera sencilla a la direccion que deseamos dando
 click derecho en la carpeta que contiene el archivo .jar a ejecutar
 y escogiendo la opcion "Abrir en la Terminal" del menu (funciona en
 la mayoria de las distribuciones de Linux).

 Una vez que nos encontremos en la ubicacion deseada, escribimos el
 siguiene comando en la terminal y lo ejecutamos:

 `java -jar OrdenamientosExternos.jar`

////**** EJECUCION DEL PROGRAMA ****////

Lo primero que mostrara el programa en la terminal sera una frase
pidiendonos ingresar el nombre del archivo que deseamos ordenar.

Los archivos validos para este programa son archivos de texto
libre (.txt), con valores numericos que se encuentren en el rango
de 1-999. Cada valor numerico debera estar separado por una coma ","
y sin espacios entre los caracteres.

(Se agregan dos archivos de prueba en la carpeta para poder ver el
formato valido, asi como para poder usarlos como archivos de prueba)

Al momento de ingresar el nombre del archivo, debemos de tener en cuenta
que este se debe de encontrar en la misma direccion donde se encuentra
el archivo .jar.
Al ingresar el nombre del archivo es indistinto si este se ingresa con
la terminacion .txt o no.

Se verificara la existencia del archivo una vez ingresado el nombre.
En caso de que este no exista, pedira que se ingrese el nombre del
archivo hasta que se detecte un archivo existente.

Una vez que se comprueba la existencia del archivo a ordenar, se accedera
al menu de ordenamiento. Tambien se mostrara el nombre del archivo que se
esta ordenando.

Existen 5 opciones en el menu:

 1.- Polifase
 2.- Mezcla Equilibrada
 3.- Radix
 4.- Seleccionar nuevo archivo
 5.- Salir

Las primeras tres opciones redigiran a un submenu correspondiente al ordenamiento
externo seleccionado. En el submenu se tendran otras dos opciones:

 1.- Ordenamiento Ascendente
 2.- Ordenamiento Descendente

Con estas opciones podremos escoger el criterio de la secuencia del ordenamiento.

En el caso especifico del Ordenamiento por Polifase, tambien se nos pedira un numero
el cual correspondera al numero de claves que se leeran durante la primer fase para
generar la primera iteracion de bloques del ordenamiento.

Cuando se ordena un archivo, en la carpeta donde nos encontramos ejecutando el
programa se generara una nueva carpeta que tendra como formato el siguiente
nombre:

"<TipoDeOrdenamiento>_<NombreDelArchivoOriginal>"

Donde TipoDeOrdenamiento correspondera al tipo de ordenamiento que se selecciono
para los valores del archivo, siendo:

 "poli" Para ordenamiento por Polifase.
 "mezeq" Para ordenamiento por Mezcla Equilibrada.
 "radix" Par ordenamiento por Radix.

Y NombreDelArchivoOriginal el nombre del archivo que contiene las claves a ordenar.

Dentro de esta carpeta se generaran todos los archivos necesarios para implementar
el ordenamiento correspondiente, mismas donde se almacenaran las iteraciones de
cada algoritmo, asi como la lista final de valores ordenados de acuerdo al criterio
seleccionado.

En el caso de Polifase se generaran 4 archivos auxiliares:
f0.txt, f1.txt, f2.txt y f3.txt.
Los valores originales contenidos en el archivo a ordenar se copiaran al archivo
auxiliar f0.txt, y a partir de esta primer linea de valores en el archivo se
ejecutara el ordenamiento.
El archivo que contendra los valores ordenados dependera del numero de iteraciones
que realizo el algoritmo. El mismo programa nos indica si la lista de valores
ordenados se encuentra en la ultima linea del archivo f0.txt, o en la ultima linea
del archivo f1.txt.

Para Mezcla Equilibrada se generaran 3 archivos auxiliares:
f0.txt, f1.txt y f2.txt.
Los valores originales contenidos en el archivo a ordenar se copiaran al archivo
auxiliar f0.txt, y a partir de esta primer linea de valores en el archivo se
ejecutara el ordenamiento.
La lista de valores ordenados siempre se encontrara en la ultima linea del archivo
f0.txt.

Para Radix se generaran 11 archivos auxiliares:
listaRadix.txt, f0.txt, f1.txt, f2.txt, ..., f9.txt.
Los valores originales contenidos en el archivo a ordenar se copiaran al archivo
auxiliar listaRadix.txt, y a partir de esta primer linea de valores en el archivo se
ejecutara el ordenamiento.
La lista de valores ordenados siempre se encontrara en la ultima linea del archivo
listaRadix.txt.

La cuarta opcion del menu principal nos permitira seleccionar un archivo diferente
para realizar ordenamientos sobre este.
Al igual que al inicio del programa, ingresaremos el nombre del archivo de texto
libre, siendo indistinto de si se ingresa con la terminaicon ".txt" o no.
Realizara la misma comprobacion de si el archivo existe para poder continuar el
programa.

La opcion de Salir terminara el programa.

Se pueden ver los archivos correspondientes a cada ordenamiento realizado en
la carpeta con el nombre generado previamente explicado. Mismas que se crearan
en la carpeta que contiene nuestro programa.

////**** Opcion 0 ****////

Para ayudar en las pruebas del programa, si en el menu principal se escoge
"0" como opcion, se ejecutara un bloque de codigo que nos permitira crear
un archivo de texto libre. Se nos pedira el nombre que deseamos que tenga
el archivo nuevo (con o sin terminacion .txt) y posteriormente nos pedira el
numero de valores que queremos que contenga dicho archivo.
Se creara este nuevo archivo en la misma carpeta donde estamos ejecutando
el programa, con el formato especificados, la cantidad de valores
ingresados generados de manera aleatoria, y el nombre ingresado.
Una vez generado este nuevo archivo, se pueden generar mas, o se pueden escoger
durante la misma ejecucion del programa, escogiendo la opcion numero 4 del menu
principal e ingresando el nombre correcto de estos.
