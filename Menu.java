
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;

/**
 *
 * @author Hugo Suarez Navarro
 */
public class Menu {

    public static byte opciones() {
        BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
        byte op = 0;
        boolean esNum = true;
        System.out.println("1. Altas.\n2. Bajas.\n3. Modificar salario."
                + "\n4. Listado de empleados, ordenado por DNI."
                + "\n5. Listado de empleados por orden de introducción.\n6. Salir."
                + "\nElija la opción para continuar: ");
        do {
            try {
                op = Byte.valueOf(teclado.readLine());
            } catch (NumberFormatException ex) {
                System.out.println("No se ha introducido un numero");
                esNum = false;
            } catch (IOException ex) {
                System.err.println("Error de entrada - salida");
            }
        } while (!esNum);
        return op;
    }

    public static void main(String[] args) throws IOException {
        byte op = 0;
        LinkedList<Empleado> emples = new LinkedList<>();
        // Exporta los datos de FICHE.DAT a FICHENDX.DAT si no existe este ultimo
        if (!new File("FICHENDX.DAT").exists()) {
            GestionFicheroIndexado.exportarFiche();
        }
        GestionFicheroIndexado.cargarFichero();
        do {
            op = opciones();
            switch (op) {
                case 1:
                    GestionFicheroIndexado.aniadirEmple();
                    break;
                case 2:
                    GestionFicheroIndexado.eliminarEmple(APIEmpleado.construyeDNI());
                    break;
                case 3:
                    GestionFicheroIndexado.modificarSalario(APIEmpleado.construyeDNI(), APIEmpleado.construyeSalario());
                    break;
                case 4:
                    emples = GestionFicheroIndexado.ordenarPorDni();
                    for (Empleado emp : emples) {
                        System.out.println("\nEmpleado con Dni: " + emp.getDni()
                                + "\nNombre y apellidos: " + emp.getNomApe()
                                + "\nSexo del empleado: " + emp.getSexo().getNombre()
                                + "\n----------------");
                    }
                    break;
                case 5:
                    emples = GestionFicheroIndexado.leerFichero();
                    for (Empleado emp : emples) {
                        System.out.println("\nEmpleado con Dni: " + emp.getDni()
                                + "\nNombre y apellidos: " + emp.getNomApe()
                                + "\nSexo del empleado: " + emp.getSexo().getNombre()
                                + "\n----------------");
                    }
                    break;
                case 6:
                    break;
                default:
            }
        } while (op != 6);
        GestionFicheroIndexado.guardarFichero();
    }
}