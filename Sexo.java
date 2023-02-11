
/**
 *
 * @author Cristian Jose Moreno Berlanga
 */
public enum Sexo {

    H("Hombre"),
    M("Mujer");
    private String letra;

    private Sexo(String nLetra) {
        letra = nLetra;
    }

    public String getLetra() {
        return letra;
    }
}