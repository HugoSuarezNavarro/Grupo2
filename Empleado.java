/**
 *
 * @author Cristian Moreno Berlanga
 */
public class Empleado {

    private String nomApe;
    private Sexo sexo;
    private float salario;
    private Fecha alta;
    private TipoEmpleado tipoEmple;
    private Provincia provincia;
    private String dni;

    public Empleado(String nomApe, Sexo sexo, float salario, Fecha alta,
            TipoEmpleado tipoEmple, Provincia provincia,String dni) {
        this.nomApe = nomApe;
        this.sexo = sexo;
        this.salario = salario;
        this.alta = alta;
        this.tipoEmple = tipoEmple;
        this.provincia = provincia;
        this.dni=dni;
    }

    public String getDni() {
        return dni;
    }  
    
    public String getNomApe() {
        return nomApe;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public float getSalario() {
        return salario;
    }

    public Fecha getFechaAlta() {
        return alta;
    }

    public TipoEmpleado getTipoEmple() {
        return tipoEmple;
    }

    public Provincia getProvincia() {
        return provincia;
    }

    public void setNomApe(String nomApe) {
        if (nomApe.length() > 0 && nomApe.length() <= 30) {
            this.nomApe = nomApe;
        }
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public void setSalario(float salario) {
        if (salario > 0) {
            this.salario = salario;
        }
    }

    public void setFechaAlta(Fecha fecha) {
        if (Fecha.validarFecha(fecha.toString())) {
            this.alta = fecha;
        }

    }

    public void setTipoEmple(TipoEmpleado tipoEmple) {
        this.tipoEmple = tipoEmple;
    }

    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
    }


}