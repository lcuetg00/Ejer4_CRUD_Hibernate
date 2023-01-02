package com.ejer.vista;
import com.ejer.controller.ClienteControlador;
import com.ejer.hibernate.entity.Cliente;
import com.ejer.hibernate.entity.EnumClienteTipo;
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

    /**
     * Variable utilizada para saber cuándo el usuario quiere salir de un menú
     */
    private static final String ESC = "ESC";

    //Función de imprimir listas
    /**
     * Utilizada para mostrar tantos elementos como el valor de esta variable a la hora de listar los elementos
     */
    private static final int NUMERO_ELEMENTOS_PAGINA = 2;

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

    //Información de un cliente

    /**
     * Contiene stríng en minúsculas del parámetro nombre del cliente
     */
    private static final String PARAMETRO_NOMBRE = "nombre";

    /**
     * Contiene stríng en minúsculas del parámetro primer apellido del cliente
     */
    private static final String PARAMETRO_APELLIDO1 = "primer apellido";

    /**
     * Contiene stríng en minúsculas del parámetro segundo apellido del cliente
     */
    private static final String PARAMETRO_APELLIDO2 = "segundo apellido";

    /**
     * Contiene stríng en minúsculas del parámetro fecha de alta del cliente
     */
    private static final String PARAMETRO_FECHA_ALTA = "fecha de alta";

    /**
     * Contiene stríng en minúsculas del parámetro cuota máxima del cliente
     */
    private static final String PARAMETRO_CUOTA_MAXIMA = "cuota maxima";

    //Valores BigDecimal para las cuotas mínimas y máximas

    /**
     * Cuota mínima que se le puede asignar a un cliente
     */
    private static final BigDecimal CUOTA_MINIMA = BigDecimal.TEN;

    /**
     * Cuota máxima que se le puede asignar a un cliente
     */
    private static final BigDecimal CUOTA_MAXIMA = new BigDecimal("10000");


    //Opciones si o no
    private static final String OPCION_SI = "SI";
    private static final String OPCION_S = "S";
    private static final String OPCION_NO = "NO";
    private static final String OPCION_N = "N";


    //Variables de fechas:
    /**
     * Expresión utilizada para crear un DateTimeFormatter
     */
    private static final String FORMATOFECHA = "dd-MM-yyyy HH:mm";
    /**
     * Formateador utilizado para que la fecha tenga que utilizar la expresión {@link #FORMATOFECHA}
     */
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(FORMATOFECHA);


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


    /**
     * Pide por pantalla el nombre del cliente. Este nombre no puede ser vacío
     * Utiliza {@link Scanner#next()} para recoger el nombre, haciéndole {@link String#trim()} para quitarle los espacios al final
     * @return El nombre pedido por pantalla
     */
    public String consolaPedirNombre() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Escriba el nombre del cliente");
        String nombre = scanner.next().trim();
        return nombre;
    }

    /**
     * Pide por pantalla el primer apellido del cliente. Este nombre no puede ser vacío
     * Utiliza {@link Scanner#next()} para recoger el apellido, haciéndole {@link String#trim()} para quitarle los espacios al final
     * @return El primer apellido pedido por pantalla
     * @return
     */
    public String consolaPedirPrimerApellido() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Escriba el primer apellido del cliente");
        String apellido1 = scanner.next().trim();
        return apellido1;
    }

    /**
     * Pide por pantalla el segundo apellido del cliente. Este puede una cadena vacía ""
     * Utiliza {@link Scanner#nextLine()} para recoger el apellido, haciéndole {@link String#trim()} para quitarle los espacios al final.
     * @return El segundo apellido pedido por pantalla
     * @return
     */
    public String consolaPedirSegundoApellido() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Escriba el segundo apellido del cliente");
        String apellido2 = scanner.nextLine().trim();
        return apellido2;
    }

    /**
     * Pide por pantalla el número de identificación del cliente.
     * Este número es pedido hasta que ocurran 2 casos:
     * 1) El número de identificación sea válido. Se valida llamando al método {@link ClienteControlador#validarNumeroDocumentacion(String)}
     * 2) La cadena es igual a la opción de salir {@link #ESC}
     * Utiliza {@link Scanner#next()} para recoger el número de identificación, haciéndole {@link String#trim()} para quitarle los espacios al final.
     * @return El número de identificación validado o la opción de salir {@link #ESC}
     */
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

    /**
     * Pide por pantalla la fecha de alta del cliente.
     * Esta fecha es pedida hasta que ocurran 3 casos:
     * 1) La fecha sea esté en un formato válido dado por {@link #FORMATOFECHA}. Se comprueba que sea válida la fecha si se le puede hacer el método {@link LocalDateTime#parse(CharSequence, DateTimeFormatter)}
     * 2) La cadena es igual a la opción de salir {@link #ESC}
     * 3) La cadena es igual a la cadena vacía ""
     * Utiliza {@link Scanner#nextLine()} para recoger la fecha, haciéndole {@link String#trim()} para quitarle los espacios al final.
     * @return String que es igual a la fecha de alta en el formato {@link #FORMATOFECHA}, la opción de salir {@link #ESC} o una cadena vacía ""
     */
    public String consolaPedirFecha() {
        Scanner scanner = new Scanner(System.in);
        boolean exito = false;
        String fechaAlta = null;
        System.out.println("Escriba la fecha de alta de la siguiente forma: " + FORMATOFECHA  + RETORNO_CARRO);
        while(exito == false) {
            fechaAlta = scanner.nextLine().trim();
            if(ESC.equalsIgnoreCase(fechaAlta) || "".equals(fechaAlta)) {
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
     * Pide por pantalla la cuota máxima del cliente.
     * Esta fecha es pedida hasta que ocurran 3 casos:
     * 1) La cadena iguala un número decimal separado por un '.', y está comprendido entre {@link #CUOTA_MINIMA} y {@link #CUOTA_MAXIMA}
     * 2) La cadena es igual a la opción de salir {@link #ESC}
     * 3) La cadena es igual a la cadena vacía ""
     * Utiliza {@link Scanner#nextLine()} para recoger la fecha, haciéndole {@link String#trim()} para quitarle los espacios al final.
     * @return String que es igual a un BigDecimal, la opción de salir {@link #ESC} o una cadena vacía ""
     */
    public String consolaPedirCuotaMaxima() {
        Scanner scanner = new Scanner(System.in);
        boolean exito = false;
        String cuota = null;
        System.out.println("Escriba la cuota máxima de pago. Valor comprendido entre 10 y 10000.");
        System.out.println("Formato: número separado en sus decimales por un punto. Ejemplo: 10.90");
        while(exito == false) {
            cuota = scanner.nextLine().trim();
            if(ESC.equalsIgnoreCase(cuota) || "".equals(cuota)) {
                exito = true;
            }
            if(exito == false) {
                try {
                    BigDecimal cuotaMax = new BigDecimal(cuota);

                    if((cuotaMax.compareTo(CUOTA_MINIMA) == -1) ||
                    (cuotaMax.compareTo(CUOTA_MAXIMA) == 1)) {
                        System.out.println("Cuota máxima incorrecta. Introduzca un valor decimal entre 10 y 10000");
                    } else {
                        exito = true;
                    }
                } catch (NumberFormatException e) {
                    //no es una número decimal con el formato correcto
                    System.out.println("No ha escrito un número decimal en el formato dado. Vuelva a escribir un valor.");
                }
            }
        }

        return cuota;
    }

    /**
     * Utiliza los parámetros agumentados para preguntar al usuario si quiere realizar los cambios de la operación anterior a llamar a esta función
     * @param parametro
     * @param antiguo
     * @param nuevo
     * @return true si la cadena leida es {@link #OPCION_S} o {@link #OPCION_SI}
     *         false si la cadena leida es {@link #OPCION_N} o {@link #OPCION_NO}
     */
    public boolean consolaPreguntarSiCambiosSonCorrectos(String parametro, String antiguo, String nuevo) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Se cambiará el parámetro " + parametro + " " + (antiguo == null ? "(no tenía) " : antiguo) + " por el nuevo " + nuevo);
        System.out.println("Si quiere continuar, escriba \"S\". Si no, escriba \"N\"");
        String opcion = scanner.nextLine().trim();
        while(true) {
            if(OPCION_S.equalsIgnoreCase(opcion) || OPCION_SI.equalsIgnoreCase(opcion)) {
                return true;
            }
            if(OPCION_N.equalsIgnoreCase(opcion) || OPCION_NO.equalsIgnoreCase(opcion)) {
                return false;
            }
            System.out.println("Seleccione una de las dos opciones posibles");
            opcion = scanner.nextLine().trim();
        }


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
                System.out.println(ANSI_GREEN + "Escriba la opción que quiere realizar." + ANSI_RESET);
                //Leemos la operación que quiere realizar el usuario
                opcion = scanner.nextInt();

                switch (opcion) {
                    case OPCIONANYADIRCLIENTE:
                        System.out.println("Seleccionado añadir cliente. Si desea salir de la operación, escriba " + ESC);
                        if(LOGGER.isInfoEnabled()) {
                            LOGGER.info("Se ha seleccionado la opción de insertar cliente");
                        }
                        boolean salir = false;
                        Cliente clienteInsertar = new Cliente();
                        clienteInsertar.setEnumCliente(EnumClienteTipo.SOCIO);
                        if(salir == false) {
                            String nombre = consolaPedirNombre();
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
                            String apellido1 = this.consolaPedirPrimerApellido();
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
                            System.out.println(ANSI_GREEN + "Dato no requerido, pulsa enter para saltar" + ANSI_RESET);
                            String apellido2 = this.consolaPedirSegundoApellido();
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
                            System.out.println(ANSI_GREEN + "Pulsa enter para poner la fecha y hora actual" + ANSI_RESET);
                            String fechaAlta = this.consolaPedirFecha();
                            if(ESC.equalsIgnoreCase(fechaAlta)) {
                                salir = true;
                            } else {
                                clienteInsertar.setFechaAltaCliente("".equals(fechaAlta) ? LocalDateTime.now() : LocalDateTime.parse(fechaAlta, FORMATTER));
                                if(LOGGER.isInfoEnabled()) {
                                    LOGGER.info("Se ha insertado la fecha de alta");
                                }
                            }
                        }

                        if(salir == false) {
                            System.out.println(ANSI_GREEN + "Dato no requerido, pulsa enter para saltar" + ANSI_RESET);
                            String cuota = this.consolaPedirCuotaMaxima();
                            if(ESC.equalsIgnoreCase(cuota)) {
                                salir = true;
                            } else {
                                clienteInsertar.setCuotaMaximaPago("".equals(cuota) ? null : new BigDecimal(cuota));
                            }
                        }

                        this.clearConsola();
                        this.imprimirMenu();

                        if(salir == false ) {
                            try {
                                clienteControlador.insertarElemento(clienteInsertar);
                                if(LOGGER.isInfoEnabled()) {
                                    LOGGER.info("Se ha insertado el cliente desde el menú");
                                }
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
                        System.out.println("Seleccionado consultar cliente. Si desea salir de la operación, escriba " + ESC);
                        if(LOGGER.isInfoEnabled()) {
                            LOGGER.info("Se ha seleccionado la opción de consultar cliente");
                        }
                        boolean finalizado = false;
                        while(finalizado == false) {
                            String numeroIdentificacion = this.consolaPedirNumIdentificacion();
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
                                    if(LOGGER.isInfoEnabled()) {
                                        LOGGER.info("Se han mostrado los datos del cliente y se ha vuelto al menú");
                                    }
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
                        System.out.println("Seleccionado borrar cliente. Si desea salir de la operación, escriba " + ESC);
                        boolean validacionIdentificacionBorrar = false;
                        String numIdentificacionBorrar = this.consolaPedirNumIdentificacion();

                        if(ESC.equalsIgnoreCase(numIdentificacionBorrar)) {
                            finalizado = true;
                            this.clearConsola();
                            this.imprimirMenu();
                            System.out.println("Se ha salido al menú principal");
                        }else {
                            try {
                                clienteControlador.eliminarElemento(numIdentificacionBorrar);
                                this.clearConsola();
                                this.imprimirMenu();
                                System.out.println("Cliente con número de identificación " + numIdentificacionBorrar + " ha sido borrado.");
                                if(LOGGER.isInfoEnabled()) {
                                    LOGGER.info("Se ha borrado el cliente y se ha vuelto al menú principal");
                                }
                            } catch(NoResultException e) {
                                this.clearConsola();
                                this.imprimirMenu();
                                System.out.println("No se ha encontrado un cliente en la base de datos con ese número de identificación");
                            }
                        }
                        break;
                    case OPCIONEDITARCLIENTE:
                        boolean salirEditar = false;
                        Cliente clienteConsulta = null;
                        System.out.println("Seleccionado editar cliente. Si desea salir de la operación, escriba " + ESC);
                        if(LOGGER.isInfoEnabled()) {
                            LOGGER.info("Se ha elegido la opción de editar cliente");
                        }
                        boolean finalizadoEncontrarCliente = false;
                        while(finalizadoEncontrarCliente == false) {
                            String numeroIdentificacion = this.consolaPedirNumIdentificacion();
                            if(ESC.equalsIgnoreCase(numeroIdentificacion)) {
                                finalizadoEncontrarCliente = true;
                                salirEditar = true;
                            }else {
                                try {
                                    clienteConsulta = clienteControlador.findCliente(numeroIdentificacion);
                                    this.clearConsola();
                                    this.imprimirMenu();

                                    finalizadoEncontrarCliente = true;
                                } catch (NoResultException e) {
                                    this.clearConsola();
                                    this.imprimirMenu();
                                    System.out.println(ANSI_YELLOW_BACKGROUND + "No existe el cliente introducido en la base de datos" + ANSI_RESET);
                                    finalizadoEncontrarCliente = true;
                                } catch (InvalidParameterException e) {
                                    System.out.println(ANSI_YELLOW_BACKGROUND + "El número de identificación que ha introducido no es válido. Compruebe que es correcto" + ANSI_RESET);
                                }
                            }
                        }

                        if (salirEditar == false) {
                            int opcionActualizar =-1;
                            this.imprimirOpcionesEditarCliente(clienteConsulta);
                            System.out.println("Seleccione la opción que desee modificar");

                            boolean seleccionadaOpcion = false;
                            while(seleccionadaOpcion == false) {
                                try {
                                    opcionActualizar = scanner.nextInt();


                                    switch (opcionActualizar) {
                                        case 0:
                                            if(LOGGER.isInfoEnabled()) {
                                                LOGGER.info("Se ha seleccionado la opción 0, para salir al menú");
                                            }
                                            seleccionadaOpcion = true;
                                            break;

                                        case 1:
                                            if(LOGGER.isInfoEnabled()) {
                                                LOGGER.info("Se ha seleccionado la opción 1, para cambiar el nombre");
                                            }
                                            seleccionadaOpcion = true;
                                            String nuevoNombre = this.consolaPedirNombre();
                                            if (consolaPreguntarSiCambiosSonCorrectos(PARAMETRO_NOMBRE, clienteConsulta.getNombreCliente(), nuevoNombre)) {
                                                clienteConsulta.setNombreCliente(nuevoNombre);
                                                clienteControlador.updateElemento(clienteConsulta);
                                            }
                                            break;

                                        case 2:
                                            if(LOGGER.isInfoEnabled()) {
                                                LOGGER.info("Se ha seleccionado la opción 2, para cambiar el primer apellido");
                                            }
                                            seleccionadaOpcion = true;
                                            String nuevoApellido1 = this.consolaPedirPrimerApellido();
                                            if (consolaPreguntarSiCambiosSonCorrectos(PARAMETRO_APELLIDO1, clienteConsulta.getPrimerApellidoCliente(), nuevoApellido1)) {
                                                clienteConsulta.setPrimerApellidoCliente(nuevoApellido1);
                                                clienteControlador.updateElemento(clienteConsulta);
                                            }
                                            break;

                                        case 3:
                                            if(LOGGER.isInfoEnabled()) {
                                                LOGGER.info("Se ha seleccionado la opción 3, para cambiar el segundo apellido");
                                            }
                                            seleccionadaOpcion = true;
                                            String nuevoApellido2 = this.consolaPedirSegundoApellido();
                                            if (consolaPreguntarSiCambiosSonCorrectos(PARAMETRO_APELLIDO2, clienteConsulta.getSegundoApellidoCliente(), nuevoApellido2)) {
                                                clienteConsulta.setSegundoApellidoCliente(nuevoApellido2);
                                                clienteControlador.updateElemento(clienteConsulta);
                                            }
                                            break;
                                        case 4:
                                            if(LOGGER.isInfoEnabled()) {
                                                LOGGER.info("Se ha seleccionado la opción 4, para cambiar la fecha de alta");
                                            }
                                            seleccionadaOpcion = true;
                                            String nuevaFechaAlta = this.consolaPedirFecha();
                                            if (consolaPreguntarSiCambiosSonCorrectos(PARAMETRO_FECHA_ALTA, clienteConsulta.getFechaAltaCliente().toString(), nuevaFechaAlta)) {
                                                clienteConsulta.setFechaAltaCliente(LocalDateTime.parse(nuevaFechaAlta, FORMATTER));
                                                clienteControlador.updateElemento(clienteConsulta);
                                            }
                                            break;

                                        case 5:
                                            if(LOGGER.isInfoEnabled()) {
                                                LOGGER.info("Se ha seleccionado la opción 5, para cambiar la cuota maxima");
                                            }
                                            seleccionadaOpcion = true;
                                            if(clienteConsulta.getEnumCliente() != EnumClienteTipo.REGISTRADO) {
                                                throw new InputMismatchException();
                                            }
                                            String nuevaCuotaMax = this.consolaPedirCuotaMaxima();
                                            if (consolaPreguntarSiCambiosSonCorrectos(PARAMETRO_CUOTA_MAXIMA, clienteConsulta.getCuotaMaximaPago().toString(), nuevaCuotaMax)) {
                                                clienteConsulta.setCuotaMaximaPago(new BigDecimal(nuevaCuotaMax));
                                                clienteControlador.updateElemento(clienteConsulta);
                                            }
                                            break;

                                    }
                                } catch (InputMismatchException e) {
                                    System.out.println(ANSI_YELLOW_BACKGROUND + "Opción tecleada incorrecta. Seleccione una de las opciones disponibles" + ANSI_RESET);
                                    scanner.nextLine();
                                }
                            }

                        }

                        this.clearConsola();
                        this.imprimirMenu();
                        System.out.println("Se ha vuelto al menú principal");
                        break;

                    case OPCIONLISTARCLIENTES:
                        System.out.println("Seleccionado listar clientes. Si desea salir de la operación, escriba " + ESC);
                        if(LOGGER.isInfoEnabled()) {
                            LOGGER.info("Se ha seleccionado la opción para listar a los clientes");
                        }
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
                        System.out.println("Saliendo del programa");
                        if(LOGGER.isInfoEnabled()) {
                            LOGGER.info("Se ha seleccionado la para salir del programa");
                        }
                        break;

                    default:
                        this.clearConsola();
                        this.imprimirMenu();
                        System.out.println(ANSI_YELLOW_BACKGROUND + "Opción tecleada incorrecta. Seleccione una de las opciones disponibles" + ANSI_RESET);


                }
                //Ocurre cuando metemos un caracter dierente a un número
            } catch (InputMismatchException e) {
                System.out.println(ANSI_YELLOW_BACKGROUND + "Opción tecleada incorrecta. Seleccione una de las opciones disponibles" + ANSI_RESET);
                scanner.nextLine();

            } catch (InvalidParameterException e){
                System.out.println(e.toString());
            }
        }
    }

    /**
     * Imprime una lista de parámetros que pueden ser editados en un cliente
     * Dependiendo de si el cliente es {@link EnumClienteTipo#SOCIO} {@link EnumClienteTipo#REGISTRADO} imprimirá unas opciones u otras
     * @param cliente
     */
    public void imprimirOpcionesEditarCliente(Cliente cliente) {
        if(cliente == null) {
            throw new InvalidParameterException();
        }
        System.out.println(ANSI_BLUE + cliente.getMetadatos() + ANSI_RESET);
        System.out.println(ANSI_BLUE + "Selecciona qué campo quieres actualizar:");
        System.out.println("0) Salir (no modificar nada)");
        System.out.println("1) Nombre");
        System.out.println("2) Primer Apellido");
        System.out.println("3) Segundo Apellido");
        System.out.println("4) Fecha de alta");
        if (cliente.getEnumCliente() == EnumClienteTipo.REGISTRADO) {
            System.out.println("5) Cuota Máxima");
        }
    }

    /**
     * Imprime la lista de los clientes en forma de páginas
     * Cada página puede contener {@link #NUMERO_ELEMENTOS_PAGINA} por cada página
     * Para pasar de página, el usuario debe de pulsar enter (escribir la cadena vacía "")
     * Para salir sin llegar al final de la lista, el usuario debe de escribir {@link #ESC}
     * @param lista
     */
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
                        //this.clearConsola();
                        System.out.println("Para pasar de página pulse enter");
                        scanner = new Scanner(System.in);
                        linea = scanner.nextLine().trim();
                        if(ESC.equalsIgnoreCase(linea)) {
                            salir = true;
                        }
                        this.clearConsola();
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
     * Imprime el menú de opciones del menú principal
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