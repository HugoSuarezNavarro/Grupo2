
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
/**
 *
 * @author Cristian Jose Moreno Berlanga
 */
public class Fecha {
    private short anio;
    private byte mes;
    private byte dia;
    private static final String FORMATO_FECHA="dd/MM/yyyy";

    public Fecha(short anio, byte mes, byte dia) {
        
            this.anio = anio;
            this.mes = mes;
            this.dia = dia;
        
    }

    public short getAnio() {
        return anio;
    }

    public byte getMes() {
        return mes;
    }

    public byte getDia() {
        return dia;
    }

    public static boolean validarFecha(String fecha) {
        
        boolean esValido=true;
        
        try {
            DateFormat df = new SimpleDateFormat(FORMATO_FECHA);
            df.setLenient(false);
            df.parse(fecha);
        } catch (ParseException e) {
            esValido= false;
        }

        return esValido;
    
    }

    public static Fecha darFechaActual() {
        GregorianCalendar cal = new GregorianCalendar();
        return new Fecha((short) cal.get(GregorianCalendar.YEAR),
                (byte) (cal.get(GregorianCalendar.MONTH) + 1), (byte) cal.get(GregorianCalendar.DAY_OF_MONTH));
    }
    public void setFecha(short anio, byte mes, byte dia){
        
            this.anio = anio;
            this.mes = mes;
            this.dia = dia;
         
    }
    public static int tiempoEntreFechas(Fecha fecha){
        Fecha hoy= Fecha.darFechaActual();
        int fechaHoy,nFecha;
        fechaHoy=(hoy.getAnio()*10000)+(hoy.getMes()*100)+(hoy.getDia());
        nFecha=(fecha.getAnio()*10000)+(fecha.getMes()*100)+(fecha.getDia());
        return fechaHoy-nFecha;
        
    }
    @Override
    public String toString() {
        String fecha = "";
        if (dia < 10) {
            fecha += "0" + dia + "/";
        } else {
            fecha += dia + "/";
        }
        if (mes < 10) {
            fecha += "0" + mes + "/" + anio;
        } else {
            fecha += mes + "/" + anio;
        }
        return fecha;
    }

}