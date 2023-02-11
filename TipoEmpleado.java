
/**
 *
 * @author Hugo Suarez Navarro
 */
public enum TipoEmpleado {

    C("A comisión"),
    F("Fijo"),
    D("A domicilio");
    private String nombreTipo;

    private TipoEmpleado(String nombreTipo) {
        nombreTipo = nombreTipo;        
    }
    public String getNombreTipo() {
        return nombreTipo;
    }
}