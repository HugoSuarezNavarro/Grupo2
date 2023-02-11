
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Hugo Suárez Navarro
 */
public class GestionFicheroIndexado {

    private static final int TAM_REG = 91;
    private static TreeMap<String, Integer> claves = new TreeMap<>();
    private static boolean eof;
    private static File ficheroIndices = new File("FICHEROINDICES.DAT");
    private static File fiche = new File("FICHE.DAT");
    private static File fichendx = new File("FICHENDX.DAT");

    /**
     * Lee los datos contenidos en FICHE.DAT y los almacena en un nuevo fichero
     * binario de nombre FICHENDX.DAT
     *
     * @throws IOException
     */
    public static void exportarFiche() throws IOException {

        String nomApes;
        String dni;
        int indice = 1;
        DataInputStream lecturaSec = null;
        DataOutputStream escrituraSec = null;
        ObjectOutputStream escrituraCabecera = null;
        try {
            lecturaSec = new DataInputStream(new FileInputStream(fiche));
            escrituraSec = new DataOutputStream(new FileOutputStream(fichendx));
            escrituraCabecera = new ObjectOutputStream(new FileOutputStream(ficheroIndices));
            eof = false;
            // Pide el dni del empleado y lo valida            
            while (!eof) {
                nomApes = "";
                try {
                    // Escribre los regs del empleado en FICHENDX.DAT
                    // conforme los va leyendo campo a campo de FICHE.DAT  
                    // excepto el dni, que lo va pidiendo conforme antes de escribir
                    // el reg en el FICHENDX.DAT                    
                    for (int i = 0; i < 30; i++) {
                        nomApes += lecturaSec.readChar();
                    }
                    dni = APIEmpleado.construyeDNI();
                    escrituraSec.writeChars(dni);
                    escrituraSec.writeChars(nomApes);
                    escrituraSec.writeChar(lecturaSec.readChar());
                    escrituraSec.writeFloat(lecturaSec.readFloat());
                    escrituraSec.writeShort(lecturaSec.readShort());
                    escrituraSec.writeByte(lecturaSec.readByte());
                    escrituraSec.writeByte(lecturaSec.readByte());
                    escrituraSec.writeChar(lecturaSec.readChar());
                    escrituraSec.writeByte(lecturaSec.readByte());
                    // Guardo los DNIs(claves) y su respectivo numero de introduccion
                    // que es el indice del registro en el fichero
                    claves.put(dni, indice);
                    indice++;
                } catch (EOFException e) {
                    eof = true;
                } finally {
                    if (lecturaSec != null) {
                        lecturaSec.close();
                    }
                    if (escrituraSec != null) {
                        escrituraSec.close();
                    }
                    if (escrituraCabecera != null) {
                        escrituraCabecera.close();
                    }
                }
            }
            // Guardo la estructura del fichero indexado(TreeMap) en el fichero de objetos
            // DNI(clave)<-->INDICE(valor)
            escrituraCabecera.writeObject(claves);
        } catch (FileNotFoundException ex) {
            System.out.println("El fichero FICHE.DAT no existe.");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } finally {
            if (lecturaSec != null) {
                lecturaSec.close();
            }
            if (escrituraSec != null) {
                escrituraSec.close();
            }
            if (escrituraCabecera != null) {
                escrituraCabecera.close();
            }
        }
    }

    /**
     * Obtiene un TreeMap que representa la estructura de FICHENDX.DAT leyendo
     * un fichero de objetos que contiene dicha estructura y abre los distintos
     * flujos que van a trabajar sobre FICHENDX.DAT
     */
    public static void cargarFichero() throws IOException {
        try {
            // Flujos del fichero de indices
            ObjectInputStream lecturaCabecera = new ObjectInputStream(new FileInputStream(ficheroIndices));
            // Leemos la estructura del fichero de indices
            eof = false;
            while (!eof) {
                try {
                    claves = (TreeMap) lecturaCabecera.readObject();
                } catch (EOFException e) {
                    eof = true;
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("No se encuentra el fichero FICHENDX.DAT");
        } catch (ClassNotFoundException ex) {
            System.out.println("No se encuentra el fichero de indices");
        }
    }
    public static void guardarFichero() throws IOException {
        ObjectOutputStream escrituraCabecera = new ObjectOutputStream(new FileOutputStream(ficheroIndices));
        // Reescribe el fichero de objetos para almacenar la nueva estructura
        escrituraCabecera.writeObject(claves);
        escrituraCabecera.close();

    }
    /**
     * Lectura secuencial del fichero que devuelve una lista enlazada con todos
     * los empleados almacenados en este
     *
     * @return
     */
    public static LinkedList<Empleado> leerFichero() throws IOException {
        String nomApes, dni;
        LinkedList<Empleado> empleados = new LinkedList<>();
        eof = false;
        RandomAccessFile lectorRandom = new RandomAccessFile(fichendx, "rw");
        lectorRandom.seek(0);
        while (!eof) {
            try {
                dni = "";
                for (int i = 0; i < 9; i++) {
                    dni += lectorRandom.readChar();
                }
                nomApes = "";
                for (int i = 0; i < 30; i++) {
                    nomApes += lectorRandom.readChar();
                }
                empleados.add(new Empleado(dni, nomApes.trim(), APIEmpleado.construyeSexo(lectorRandom.readChar()), 
                        lectorRandom.readFloat(), new Fecha(lectorRandom.readShort(), lectorRandom.readByte(),
                        lectorRandom.readByte()), APIEmpleado.construyeTipo(lectorRandom.readChar()),
                        APIEmpleado.construyeProvincia(lectorRandom.readByte())));
            } catch (EOFException eofe) {
                eof = true;
            }
        }
        lectorRandom.close();
        return empleados;
    }
    /**
     * Lectura aleatoria desde el registro con el indice pasado por parametro
     *
     * @param indice
     * @return
     */
    public static Empleado leerRegistro(int indice) {
        Empleado e = null;
        String nomApes, dni;
        try {
            RandomAccessFile lectorRandom = new RandomAccessFile(fichendx, "rw");
            lectorRandom.seek((indice - 1) * TAM_REG);
            dni = "";
            for (int i = 0; i < 9; i++) {
                dni += lectorRandom.readChar();
            }
            nomApes = "";
            for (int i = 0; i < 30; i++) {
                nomApes += lectorRandom.readChar();
            }
            e = new Empleado(dni, nomApes.trim(), APIEmpleado.construyeSexo(lectorRandom.readChar()), 
                    lectorRandom.readFloat(), new Fecha(lectorRandom.readShort(), lectorRandom.readByte(),
                    lectorRandom.readByte()), APIEmpleado.construyeTipo(lectorRandom.readChar()),
                    APIEmpleado.construyeProvincia(lectorRandom.readByte()));
            lectorRandom.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return e;
    }

    /**
     * @return lista de empleados por orden de DNI
     */
    public static LinkedList<Empleado> ordenarPorDni() {
        LinkedList<Empleado> emples = new LinkedList<>();
        // Recorro el TreeMap para leer del fichero segun el orden de los DNIs
        for (Map.Entry<String, Integer> entrada : claves.entrySet()) {
            // Posiciona el puntero en el registro que tiene como clave este DNI,
            // lo lee y devuelve el empleado
            emples.add(leerRegistro(entrada.getValue()));
        }
        return emples;
    }
    /**
     * Recupera el indice correspondiente al dni que recive como parametro
     * almacenado en el TreeMap
     *
     * @param dni
     * @return indice del registro que tiene como clave el dni pasado por
     * parametro
     */
    

    public static void aniadirEmple() {
        try {
            RandomAccessFile lectorRandom = new RandomAccessFile(fichendx, "rw");
            // Pide los datos del empleado a introducir en el fichero
            Empleado emple = new Empleado(APIEmpleado.construyeDNI(), APIEmpleado.construyeNomApe(),
                    APIEmpleado.insertaSexo(), APIEmpleado.construyeSalario(), APIEmpleado.construyeFecha(),
                    APIEmpleado.insertaTipo(), APIEmpleado.insertaProvincia());
            // Añade el DNI del empelado al TreeMap con su indice correspondiente
            lectorRandom.seek((claves.size()) * TAM_REG);
            claves.put(emple.getDni(), claves.size() + 1);
            // Adicion del registro al fichero
            lectorRandom.writeChars(emple.getDni());
            lectorRandom.writeChars(construyeString(emple.getNomApe(), 30));
            lectorRandom.writeChar(emple.getSexo().getLetra());
            lectorRandom.writeFloat(emple.getSalario());
            lectorRandom.writeShort(emple.getFecha().getAnio());
            lectorRandom.writeByte(emple.getFecha().getMes());
            lectorRandom.writeByte(emple.getFecha().getDia());
            lectorRandom.writeChar(emple.getTipoEmple().getCharTipo());
            lectorRandom.writeByte(emple.getProvincia().getNumeroProv());

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    public static void eliminarEmple(String dni) throws IOException {
        RandomAccessFile lectorRandom = new RandomAccessFile(fichendx, "rw");
        Empleado e;
        if (claves.containsKey(dni)) {
            System.out.println("Se va a proceder a borrar el empleado: "
                    + leerRegistro(claves.get(dni)).getNomApe());
            if (APIEmpleado.continuar()) {
                e = leerRegistro(claves.size());
                // Posiciona el puntero en el registro que se quiere eliminar 
                // y lo sobreescribre con el ultimo empleado leido anteriormente
                // evitando así dejar huecos
                lectorRandom.seek((claves.get(dni)-1) * TAM_REG);
                // Cambia en el TreeMap el indice al que se ha movido el ultimo registro
                // rellenando el hueco del registro eliminado
                claves.put(e.getDni(), claves.get(dni));
                claves.remove(dni);
                lectorRandom.writeChars(e.getDni());
                lectorRandom.writeChars(construyeString(e.getNomApe(), 30));
                lectorRandom.writeChar(e.getSexo().getLetra());
                lectorRandom.writeFloat(e.getSalario());
                lectorRandom.writeShort(e.getFecha().getAnio());
                lectorRandom.writeByte(e.getFecha().getMes());
                lectorRandom.writeByte(e.getFecha().getDia());
                lectorRandom.writeChar(e.getTipoEmple().getCharTipo());
                lectorRandom.writeByte(e.getProvincia().getNumeroProv());
                lectorRandom.setLength((claves.size())*TAM_REG);
            }
        }
    }

    public static boolean modificarSalario(String dni, float nuevoSalario) throws IOException {
        boolean modificado = false;
        RandomAccessFile lectorRandom = new RandomAccessFile(fichendx, "rw");
        if (claves.containsKey(dni)) {
            lectorRandom.seek((claves.get(dni) - 1) * TAM_REG + 18 + 60 + 2);
            System.out.println("El salario actual es " + lectorRandom.readFloat());
            if (APIEmpleado.continuar()) {
                // Retrocedo 4 bytes para sobreescribir el salario
                lectorRandom.seek(lectorRandom.getFilePointer() - 4);
                lectorRandom.writeFloat(nuevoSalario);
                modificado = true;
            }
        }
        return modificado;
    }

    private static String construyeString(String cadena, int longCad) {
        StringBuilder builder = new StringBuilder(cadena);
        builder.setLength(longCad);
        return builder.toString();
    }
}
