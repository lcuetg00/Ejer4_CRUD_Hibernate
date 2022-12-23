package com.ejer.vista;
import com.ejer.controller.ClienteControlador;
import com.ejer.hibernate.entity.Cliente;
import com.ejer.hibernate.entity.EnumClienteTipo;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.List;
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
     * Hace que el texto escrito sea verde.
     */
    public static final String ANSI_GREEN = "\u001B[32m";

    /**
     * Utilizada al imprimir por pantalla caracteres.
     * Hace que el texto escrito sea azul.
     */
    public static final String ANSI_BLUE = "\u001B[34m";

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
        this.clearConsola();
        System.out.println("Ejercicio 4: CRUD");
        imprimirMenu();

        while (opcion != OPCIONSALIR) {
            try {
                System.out.println(ANSI_GREEN + "Escriba la opción que quiere realizar:" + ANSI_RESET);
                //Leemos la operación que quiere realizar el usuario
                opcion = scanner.nextInt();

                switch (opcion) {
                    case OPCIONANYADIRCLIENTE:
                        Cliente clienteInsertar = new Cliente();
                        clienteInsertar.setEnumCliente(EnumClienteTipo.SOCIO);
                        System.out.println("Escriba el nombre del cliente a insertar");
                        clienteInsertar.setNombreCliente(scanner.next());
                        System.out.println("Escriba el primer apellido del cliente a insertar");
                        clienteInsertar.setPrimerApellidoCliente(scanner.next());
                        System.out.println("Escriba el segundo apellido. Dato no requerido, pulsa enter para saltar");
                        scanner = new Scanner(System.in);
                        String apellido2 = scanner.nextLine();
                        clienteInsertar.setSegundoApellidoCliente(apellido2.equals(Character.MIN_VALUE) ? apellido2 : null);
                        System.out.println("Escriba el Número de Identificación");
                        clienteInsertar.setNumIdentificacion(scanner.next());
                        System.out.println("Escriba la fecha de alta. Pulsa enter para poner la fecha y hora actual");
                        //FIXME formato fecha de alta. comprobar en el service el formato de la fecha de alta
                        scanner = new Scanner(System.in);
                        String fechaAlta = scanner.nextLine();
                        clienteInsertar.setFechaAltaCliente(fechaAlta.equals(Character.MIN_VALUE) ? LocalDateTime.parse(fechaAlta) : LocalDateTime.now());
                        System.out.println("Escriba la cuota máxima de pago. Dato no requerido, pulsa enter para saltar");
                        System.out.println("Formato: número separado en sus decimales por un punto. Ejemplo: 10.90");
                        scanner = new Scanner(System.in);
                        String cuota = scanner.nextLine();
                        clienteInsertar.setCuotaMaximaPago(cuota.equals(Character.MIN_VALUE) ? new BigDecimal(cuota) : null);

                        this.clearConsola();
                        this.imprimirMenu();
                        try {
                            clienteControlador.insertarCliente(clienteInsertar);
                            System.out.println(ANSI_BLUE + "Cliente insertado Correctamente" + ANSI_RESET);
                        } catch(InvalidParameterException e) {
                            System.out.println(ANSI_YELLOW_BACKGROUND + "El Número de Identificador no es válido." + ANSI_RESET);
                        } catch(PersistenceException e) {
                            System.out.println(ANSI_YELLOW_BACKGROUND + "Ya existe un cliente con el mismo Número de Indentificación" + ANSI_RESET);
                        }

                        break;

                    case OPCIONCONSULTARCLIENTE:
                        boolean finalizado = false;
                        while(finalizado == false) {
                            System.out.println("Escriba el Número de Identificación (DNI, NIF, CIF) del cliente que quiere buscar");
                            String numeroIdentificacion = scanner.next();
                            Cliente clienteConsulta;
                            try {
                                clienteConsulta = clienteControlador.findCliente(numeroIdentificacion);
                                this.clearConsola();
                                this.imprimirMenu();
                                System.out.println(ANSI_BLUE + "Resultado de la consulta:" + ANSI_RESET);
                                System.out.println(ANSI_BLUE + clienteConsulta.getMetadatos() + ANSI_RESET);

                                finalizado = true;
                            } catch (NoResultException e) {
                                this.clearConsola();
                                this.imprimirMenu();
                                System.out.println(ANSI_YELLOW_BACKGROUND + "No existe el cliente introducido en la base de datos" + ANSI_RESET);
                                finalizado = true;
                            } catch (InvalidParameterException e) {
                                System.out.println(ANSI_YELLOW_BACKGROUND + "El número de identificación que ha introducido no es válido. Compruebe que es correcto" + ANSI_RESET);
                            }
                        }

                        break;
                    case OPCIONBORRARCLIENTE:
                        break;
                    case OPCIONEDITARCLIENTE:
                        break;
                    case OPCIONLISTARCLIENTES:
                        this.clearConsola();
                        this.imprimirMenu();
                        List<Cliente> lista = clienteControlador.getListaElementosOrdenadorNumeroIdentificacion();
                        if(lista != null) {
                            if(lista.size() == 0) {
                                System.out.println("No hay clientes almacenados");
                            } else {
                                this.imprimirListaClientes(lista);
                            }
                        }

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

    public void imprimirListaClientes(List<Cliente> lista) {
        int numCliente = 1;
        System.out.println(ANSI_BLUE +"------------------");
        for(Cliente i : lista) {
            System.out.println("Cliente número " + numCliente + ":");
            System.out.println(i.getMetadatos());
            numCliente++;
        }
        System.out.println("------------------" + ANSI_RESET);
    }

    /**
     * Imprime el menú de opciones
     */
    public void imprimirMenu() {
        System.out.println(ANSI_GREEN + "_____________________________________________________________________" );
        System.out.println("1) Insertar cliente");
        System.out.println("2) Consultar cliente");
        System.out.println("3) Borrar cliente");
        System.out.println("4) Editar cliente");
        System.out.println("5) Listar clientes");
        System.out.println("6) Salir");
        System.out.println("_____________________________________________________________________"+ ANSI_RESET);
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