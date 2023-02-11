
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author Cristian Jose Moreno Berlanga
 */
public class APIEmpleado {

    static private BufferedReader TECLADO = new BufferedReader(new InputStreamReader(System.in));

    public static boolean continuar() throws IOException {
        String continuar;
        do {
            System.out.print("Desea continuar?(S/N): ");
            continuar = TECLADO.readLine();
        } while (!continuar.equalsIgnoreCase("S")
                && !continuar.equalsIgnoreCase("N"));
        return continuar.equalsIgnoreCase("S");
    }

    public static String construyeNomApe() throws IOException {
        String nomApe;
        do {
            System.out.print("Introducir nombre y apellidos (30 car max): ");
            nomApe = TECLADO.readLine();
        } while (nomApe.isEmpty() || nomApe.length() > 30);
        return nomApe;
    }

    public static boolean validaDni(String dni) {
        char[] tablaLetras = {'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D',
            'X', 'B', 'N', 'J', 'Z', 'S', 'Q', 'V', 'H', 'L', 'C', 'K', 'E'};
        char letra;
        byte resto;
        boolean esDniValido = false;
        letra = dni.charAt(8);
        dni = dni.substring(0, 8);
        // El modulo de la division de los 8 digitos de control del DNI entre 23
        // nos da la letra correcta segun el orden establecido en tablaLetras
        resto = (byte) (Integer.valueOf(dni) % 23);
        if (letra == tablaLetras[resto]) {
            esDniValido = true;
        }
        return esDniValido;
    }

    public static String construyeDNI() throws IOException {
        String dni;
        do {
            System.out.print("Introducir dni empleado: ");
            dni = TECLADO.readLine().toUpperCase();
        } while (dni.isEmpty() || !dni.matches("[0-9]{8}[A-Z]") || !validaDni(dni));
        return dni;
    }

    public static Sexo construyeSexo(char sexo) {
        Sexo eSexo = null;
        if (sexo == Sexo.HOMBRE.getLetra()) {
            eSexo = Sexo.HOMBRE;
        } else {
            eSexo = Sexo.MUJER;
        }
        return eSexo;
    }

    public static Sexo insertaSexo() throws IOException {
        String sexo;
        do {
            System.out.print("Introducir sexo del empleado (H/M): ");
            sexo = TECLADO.readLine().toUpperCase();
        } while (sexo.isEmpty() || !sexo.equalsIgnoreCase("H")
                && !sexo.equalsIgnoreCase("M"));
        return construyeSexo(sexo.charAt(0));
    }

    public static float construyeSalario() throws IOException {
        float salario = 0;
        boolean noNum = false;
        do {
            try {
                System.out.print("Introducir salario (mayor de 0): ");
                salario = Float.parseFloat(TECLADO.readLine());
            } catch (NumberFormatException nfe) {
                noNum = true;
            }
        } while (noNum || salario <= 0);
        return salario;
    }

    public static Provincia construyeProvincia(byte digito) {
        Provincia eProvincia = null;
        switch (digito) {

            case 1:
                eProvincia = Provincia.ALMERIA;
                break;
            case 2:
                eProvincia = Provincia.CADIZ;
                break;
            case 3:
                eProvincia = Provincia.CORDOBA;
                break;
            case 4:
                eProvincia = Provincia.GRANADA;
                break;
            case 5:
                eProvincia = Provincia.HUELVA;
                break;
            case 6:
                eProvincia = Provincia.JAEN;
                break;
            case 7:
                eProvincia = Provincia.MALAGA;
                break;
            case 8:
                eProvincia = Provincia.SEVILLA;
                break;
        }
        return eProvincia;
    }

    public static Provincia insertaProvincia() throws IOException {
        Provincia eProvincia = null;
        byte provincia = 0;
        boolean noNum = false;
        do {
            try {
                System.out.print("Indicar el numero de la provincia del empleado:"
                        + "\n1:Almería\n2:Cádiz\n3:Córdoba\n4:Granada\n5:Huelva"
                        + "\n6:Jaén\n7:Málaga\n8:Sevilla\n");
                provincia = Byte.parseByte(TECLADO.readLine());
            } catch (NumberFormatException nfe) {
                noNum = true;
            }
        } while (noNum || provincia <= 0 || provincia > 8);
        return construyeProvincia(provincia);
    }

    public static Fecha construyeFecha() throws IOException {

        short anio;
        byte mes, dia;
        do {
            do {
                System.out.println("Introduce el anio de ingreso del empleado: ");
                anio = Short.parseShort(TECLADO.readLine());
            } while (anio < 1 || anio > Fecha.hoy().getAnio());
            do {
                System.out.println("Introduce el mes de ingreso del empleado: ");
                mes = Byte.parseByte(TECLADO.readLine());
            } while (mes < 1 || mes > 12);
            do {
                System.out.println("Introduce el dia de ingreso del empleado: ");
                dia = Byte.parseByte(TECLADO.readLine());
            } while (dia < 1 || dia > 31);
        } while (!Fecha.validarFecha(anio, mes, dia));
        return new Fecha(anio, mes, dia);
    }

    public static TipoEmpleado construyeTipo(char letra) {
        TipoEmpleado temple = null;
        if (letra == 'D') {
            temple = TipoEmpleado.DOMICILIO;
        } else if (letra == 'C') {
            temple = TipoEmpleado.COMISION;
        } else {
            temple = TipoEmpleado.FIJO;
        }
        return temple;
    }

    public static TipoEmpleado insertaTipo() throws IOException {

        String tipo;
        do {
            System.out.print("Introducir el tipo de empleado (F/D/C): ");
            tipo = TECLADO.readLine().toUpperCase();
        } while (tipo.isEmpty() || !tipo.equalsIgnoreCase("F")
                && !tipo.equalsIgnoreCase("D") && !tipo.equalsIgnoreCase("C"));
        return construyeTipo(tipo.charAt(0));
    }
}
