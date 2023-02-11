
/**
 *
 * @author Francisco Javier Diosdado
 */
public enum Provincia {

    ALMERIA, CADIZ, CORDOBA, GRANADA, HUELVA, JAEN, MALAGA, SEVILLA;

    private Provincia() {
    }

    public static Provincia obtenerProvincia(int numProvincia) {
        Provincia provincia = null;
        switch (numProvincia) {
            case 1:
                provincia = Provincia.ALMERIA;
                break;
            case 2:
                provincia = Provincia.CADIZ;
                break;
            case 3:
                provincia = Provincia.CORDOBA;
                break;
            case 4:
                provincia = Provincia.GRANADA;
                break;
            case 5:
                provincia = Provincia.HUELVA;
                break;
            case 6:
                provincia = Provincia.JAEN;
                break;
            case 7:
                provincia = Provincia.MALAGA;
                break;
            case 8:
                provincia = Provincia.SEVILLA;
                break;
        }
        return provincia;
    }
}