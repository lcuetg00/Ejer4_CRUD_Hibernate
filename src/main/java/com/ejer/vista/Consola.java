package com.ejer.vista;
import com.ejer.controller.ClienteControlador;
import com.ejer.hibernate.entity.Cliente;
import com.ejer.hibernate.entity.EnumClienteTipo;
import com.ejer.service.ClienteService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
    private static final int OPCIONANYADIRCLIENTE = 1;
    /**
     * Representa la opción de consultar cliente a la base de datos
     */
    private static final int OPCIONCONSULTARCLIENTE = 2;
    /**
     * Representa la opción de borrar cliente en la base de datos
     */
    private static final int OPCIONBORRARCLIENTE = 3;
    /**
     * Representa la opción de editar cliente en la base de datos
     */
    private static final int OPCIONEDITARCLIENTE = 4;
    /**
     * Representa la opción de listar clientes a la base de datos
     */
    private static final int OPCIONLISTARCLIENTES = 5;
    /**
     * Representa la opción de salir de la aplicación en el menú
     */
    private static final int OPCIONSALIR = 6;

    private static final String ESC = "ESC";

    //Función de imprimir listas
    private static final int NUMERO_ELEMENTOS_PAGINA = 1;


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

    //Variables de fechas:
    private static final String FORMATOfECHA = "dd-MM-yyyy HH:mm";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(FORMATOfECHA);


    /**
     * Instancia de ClienteControlador
     */
    private final ClienteControlador clienteControlador;

    /**
     * Logger para la clase Consola
     */
    static private final Logger LOGGER = LogManager.getLogger(Consola.class);

    /**
     * Constructor que crea la instancia de clienteControlador
     */
    public Consola() {
        clienteControlador = new ClienteControlador();
    }


    public String consolaPedirNombre() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Escriba el nombre del cliente a insertar");
        String nombre = scanner.next().trim();
        return nombre;
    }

    public String consolaPedirNumIdentificacion() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Escriba el Número de Identificación");
        boolean validacionIdentificacion = false;
        String numIdentificacion = null;
        while(validacionIdentificacion == false) {
            numIdentificacion = scanner.next().trim();
            if(ESC.equalsIgnoreCase(numIdentificacion)) {
                validacionIdentificacion = true;
            }
            if(clienteControlador.validarNumeroDocumentacion(numIdentificacion)) {
                validacionIdentificacion = true;
            }
            if(validacionIdentificacion == false) {
                System.out.println("Número de Identificación inválido. Compruébelo y vuelva a insertarlo");
            }
        }
        return numIdentificacion;
    }

    public String consolaPedirFecha() {
        Scanner scanner = new Scanner(System.in);
        boolean exito = false;
        String fechaAlta = null;
        System.out.println("Escriba la fecha de alta. Pulsa enter para poner la fecha y hora actual");
        while(exito == false) {
            scanner = new Scanner(System.in);
            fechaAlta = scanner.nextLine().trim();
            if(ESC.equalsIgnoreCase(fechaAlta)) {
                exito = true;
            }
            if(exito == false) {
                try {
                    LocalDateTime.parse(fechaAlta, FORMATTER);
                    exito = true;
                } catch (DateTimeParseException e) {
                    //no es una fecha parseable
                    System.out.println("No ha escrito una fecha válida. Vuelva a escribirla utilizando el formato dado");
                }
            }
        }

        return fechaAlta;
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
                        if(LOGGER.isInfoEnabled()) {
                            LOGGER.info("Se ha seleccionado la opción de insertar cliente");
                        }
                        boolean salir = false;
                        Cliente clienteInsertar = new Cliente();
                        clienteInsertar.setEnumCliente(EnumClienteTipo.SOCIO);
                        if(salir == false) {
                            System.out.println("Escriba el nombre del cliente a insertar");
                            String nombre = scanner.next().trim();
                            if(ESC.equalsIgnoreCase(nombre)) {
                                salir = true;
                            } else {
                                if(LOGGER.isInfoEnabled()) {
                                    LOGGER.info("Se ha insertado el nombre: {}",nombre);
                                }
                                clienteInsertar.setNombreCliente(nombre);
                            }
                        }

                        if(salir == false) {
                            System.out.println("Escriba el primer apellido del cliente a insertar");
                            String apellido1 = scanner.next().trim();
                            if(ESC.equalsIgnoreCase(apellido1)) {
                                salir = true;
                            } else {
                                clienteInsertar.setPrimerApellidoCliente(apellido1);
                                if(LOGGER.isInfoEnabled()) {
                                    LOGGER.info("Se ha insertado el apellido: {}",apellido1);
                                }
                            }
                        }

                        if(salir == false) {
                            System.out.println("Escriba el segundo apellido. Dato no requerido, pulsa enter para saltar");
                            scanner = new Scanner(System.in);
                            String apellido2 = scanner.nextLine().trim();
                            if(ESC.equalsIgnoreCase(apellido2)) {
                                salir = true;
                            } else {
                                clienteInsertar.setSegundoApellidoCliente("".equals(apellido2) ? null : apellido2);
                                if(LOGGER.isInfoEnabled()) {
                                    LOGGER.info("Se ha insertado el segundo apellido: {}",apellido2);
                                }
                            }
                        }

                        if(salir == false) {

                            String numIdentificacion = this.consolaPedirNumIdentificacion();

                            if(ESC.equalsIgnoreCase(numIdentificacion)) {
                                salir = true;
                            } else {
                                clienteInsertar.setNumIdentificacion(numIdentificacion);
                                if(LOGGER.isInfoEnabled()) {
                                    LOGGER.info("Se ha insertado el número de identifiación: {}", numIdentificacion);
                                }
                            }
                        }

                        if(salir == false) {
                            String fechaAlta = this.consolaPedirFecha();
                            if(ESC.equalsIgnoreCase(fechaAlta)) {
                                salir = true;
                            } else {
                                //DateTimeParseException
                                clienteInsertar.setFechaAltaCliente("".equals(fechaAlta) ? LocalDateTime.now() : LocalDateTime.parse(fechaAlta, FORMATTER));
                                if(LOGGER.isInfoEnabled()) {
                                    LOGGER.info("Se ha insertado la fecha de alta");
                                }
                            }
                        }

                        if(salir == false) {
                            System.out.println("Escriba la cuota máxima de pago. Dato no requerido, pulsa enter para saltar");
                            System.out.println("Formato: número separado en sus decimales por un punto. Ejemplo: 10.90");
                            scanner = new Scanner(System.in);
                            String cuota = scanner.nextLine().trim();
                            if(ESC.equalsIgnoreCase(cuota)) {
                                salir = true;
                            } else {
                                //FIXME comprobar formato del decimal
                                clienteInsertar.setCuotaMaximaPago("".equals(cuota) ? null : new BigDecimal(cuota));
                            }
                        }


                        this.clearConsola();
                        this.imprimirMenu();

                        if(salir == false ) {
                            try {
                                clienteControlador.insertarElemento(clienteInsertar);
                                System.out.println(ANSI_BLUE + "Cliente insertado Correctamente" + ANSI_RESET);
                            } catch(InvalidParameterException e) {
                                System.out.println(ANSI_YELLOW_BACKGROUND + "El Número de Identificador no es válido." + ANSI_RESET);
                            } catch(PersistenceException e) {
                                System.out.println(ANSI_YELLOW_BACKGROUND + "Ya existe un cliente con el mismo Número de Indentificación" + ANSI_RESET);
                            }
                        } else {
                            System.out.println("Se ha salido al menú principal");
                            if(LOGGER.isInfoEnabled()) {
                                LOGGER.info("Se ha vuelto al menú principal");
                            }
                        }

                        break;

                    case OPCIONCONSULTARCLIENTE:
                        boolean finalizado = false;
                        while(finalizado == false) {
                            System.out.println("Escriba el Número de Identificación (DNI, NIF, CIF) del cliente que quiere buscar");
                            String numeroIdentificacion = scanner.next().trim();
                            if(ESC.equalsIgnoreCase(numeroIdentificacion)) {
                                finalizado = true;
                                this.clearConsola();
                                this.imprimirMenu();
                                System.out.println("Se ha salido al menú principal");
                            }else {
                                try {
                                    Cliente clienteConsulta;
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
                        }

                        break;
                    case OPCIONBORRARCLIENTE:
                        System.out.println("Escriba el Número de Identificación del cliente que quiere borrar");
                        boolean validacionIdentificacionBorrar = false;
                        String numIdentificacionBorrar;
                        while(validacionIdentificacionBorrar == false) {
                            numIdentificacionBorrar = scanner.next().trim();
                            if(clienteControlador.validarNumeroDocumentacion(numIdentificacionBorrar)) {
                                //Es válido
                                validacionIdentificacionBorrar = true;
                                try {
                                    clienteControlador.eliminarElemento(numIdentificacionBorrar);
                                    System.out.println("Cliente con número de identificación " + numIdentificacionBorrar + " ha sido borrado.");
                                } catch(NoResultException e) {
                                    System.out.println("No se ha encontrado un cliente en la base de datos con ese número de identificación");
                                }

                            } else {
                                System.out.println(ANSI_GREEN_BACKGROUND + "Número de Identificación inválido. Compruébelo y vuelva a insertarlo" + ANSI_RESET);
                            }
                        }
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
        Scanner scanner;
        int numCliente = 1;
        //Comprobamos las páginas que se pueden imprimir
        int paginas = lista.size()/NUMERO_ELEMENTOS_PAGINA;
        if(lista.size()%NUMERO_ELEMENTOS_PAGINA != 0) {
            paginas++;
        }

        this.clearConsola();
        boolean salir = false;

        for(int i = 0; i < paginas; i++) { //vamos por cada página
            if(salir == false) {
                for (int j = 0; j < NUMERO_ELEMENTOS_PAGINA; j++) { //imprimimos los datos de los clientes si existen
                    int indice = (i * NUMERO_ELEMENTOS_PAGINA) + j;
                    if (indice >= 0 && indice < lista.size()) {
                        if (i == paginas - 1) {
                            this.imprimirMenu();
                        }
                        System.out.println(ANSI_BLUE +"------------------");
                        System.out.println("Cliente número " + numCliente + ":");
                        System.out.println(lista.get(indice).getMetadatos());
                        numCliente++;
                        System.out.println("------------------");
                    }
                }
                if (i != paginas - 1) {
                    String linea = "texto";
                    while ((!("".equals(linea)) && (salir == false))) {
                        this.clearConsola();
                        System.out.println("Para pasar de página pulse enter");
                        scanner = new Scanner(System.in);
                        linea = scanner.nextLine().trim();
                        if(ESC.equalsIgnoreCase(linea)) {
                            salir = true;
                        }
                    }
                }
            }
        }
        if(salir == false) {
            System.out.println("Terminada la lista"  + ANSI_RESET);
        } else {
            System.out.println("Termina la operación de listar clientes"  + ANSI_RESET);
        }
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