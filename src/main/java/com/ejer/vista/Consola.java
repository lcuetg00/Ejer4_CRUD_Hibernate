package com.ejer.vista;
import com.ejer.controller.ClienteControlador;
import com.ejer.hibernate.entity.Cliente;

import java.security.InvalidParameterException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Clase encargada de la interacción con el usuario
 * Lee las entradas del usuario para realizar las operacones necesarias
 * @author Luis Cueto
 */
public class Consola {
    //Opciones del menú
    /**
     * Representa la opción de anyadir clientes a la base de datos
     */
    public static final int OPCIONANYADIRCLIENTE = 1;
    /**
     * Representa la opción de consultar cliente a la base de datos
     */
    public static final int OPCIONCONSULTARCLIENTE = 2;
    /**
     * Representa la opción de borrar cliente en la base de datos
     */
    public static final int OPCIONBORRARCLIENTE = 3;
    /**
     * Representa la opción de editar cliente en la base de datos
     */
    public static final int OPCIONEDITARCLIENTE = 4;
    /**
     * Representa la opción de listar clientes a la base de datos
     */
    public static final int OPCIONLISTARCLIENTES = 5;
    /**
     * Representa la opción de salir de la aplicación en el menú
     */
    public static final int OPCIONSALIR = 6;

    //Formato de texto
    /**
     * Utilizada al imprimir por pantalla caracteres.
     * Hace que el texto escrito tengan un fondo verde.
     */
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";

    /**
     * Utilizada al imprimir por pantalla caracteres.
     * Hace que texto escrito tengan un fondo amarillo.
     */
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m"; //Para que el texto tenga un fondo amarillo

    /**
     * Utilizada al imprimir por pantalla caracteres.
     * Hace que texto escrito tenga el formato por defecto.
     */
    public static final String ANSI_RESET = "\u001B[0m"; //Para devolver el texto a la normalidad

    /**
     * Retorno de carro proporcionado por el sistema que se esté utilizando
     */
    public static final String RETORNO_CARRO = System.getProperty("line.separator");

    /**
     * Instancia de ClienteControlador
     */
    private final ClienteControlador clienteControlador;

    /**
     * Constructor que crea la instancia de clienteControlador
     */
    public Consola() {
        clienteControlador = new ClienteControlador();
    }

    /**
     * Inicia la interfaz de la consola.
     * Presenta el menú al usuario, lee sus entradas y realiza las operaciones necesarias
     */
    public void iniciarConsola() {
        Scanner scanner = new Scanner(System.in);
        //Almacenará la opción del menú que introducirá el usuario
        int opcion = 0;

        System.out.println("Ejercicio 4: CRUD");
        imprimirMenu();

        while (opcion != OPCIONSALIR) {
            try {
                //Leemos la operación que quiere realizar el usuario
                opcion = scanner.nextInt();

                switch (opcion) {
                    case OPCIONANYADIRCLIENTE:
                        break;
                    case OPCIONCONSULTARCLIENTE:
                        System.out.println("Escriba el Número de Identificación (DNI, NIF, CIF) del cliente que quiere buscar");
                        String numeroIdentificacion = scanner.next();
                        Cliente clienteConsulta;
                        try {
                            clienteConsulta = clienteControlador.findCliente(numeroIdentificacion);
                        } catch (InvalidParameterException e) {

                        }
                        break;
                    case OPCIONBORRARCLIENTE:
                        break;
                    case OPCIONEDITARCLIENTE:
                        break;
                    case OPCIONLISTARCLIENTES:
                        break;
                    case OPCIONSALIR:
                        break;
                    default:
                        this.clearConsola();
                        this.imprimirMenu();
                        System.out.println(ANSI_YELLOW_BACKGROUND + "Opción tecleada incorrecta. Seleccione una de las opciones disponibles" + ANSI_RESET);


                }
                //Ocurre cuando metemos un caracter dierente a un número
            } catch (InputMismatchException e) {
                System.out.println(ANSI_YELLOW_BACKGROUND + "Opción tecleada incorrecta. Seleccione una de las opciones disponibles" + ANSI_RESET);
                //clear consola
                this.imprimirMenu();
                scanner.nextLine();

            } catch (InvalidParameterException e){
                System.out.println(e.toString());
            }
        }
    }

    /**
     * Imprime el menú de opciones
     */
    public void imprimirMenu() {
        System.out.println("_____________________________________________________________________");
        System.out.println("1) Insertar cliente");
        System.out.println("2) Consultar cliente");
        System.out.println("3) Borrar cliente");
        System.out.println("4) Editar cliente");
        System.out.println("5) Listar clientes");
        System.out.println("6) Salir");
        System.out.println("_____________________________________________________________________");
    }

    /**
     * Limpia el texto que aparece en la consola
     */
    public void clearConsola() {
        try {
            final String os = System.getProperty("os.name");

            if(os.contains("Windows")) {
                //Para sistemas de Windows
                //Para windows, ejecutamos el interpretador de lineas 'cmd'
                //Luego le decimos que ejecute el comando '/c cls'
                //Conectamos el output de ese comando con inheritIO() para limpiar la consola en Windows
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
            //Probarlo en otros sistemas operativos
            //else { //Para cualquier otro sistema operativo
            //    Runtime.getRuntime().exec("clear");
            //}
        }
        //Error producido en la entrada o salida
        catch (final Exception e) {
            System.out.println(e.toString());
        }
    }



}