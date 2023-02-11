
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Hugo Suarez Navarro
 */
public abstract class GestionIndexado {

    protected static int REG;
    protected static RandomAccessFile flujo;
    protected static boolean eof;

    protected boolean abrirFichero(String ruta, String modo) throws FileNotFoundException {
        boolean abierto = false;
        flujo = new RandomAccessFile(ruta, modo);
        abierto = true;
        return abierto;
    }

    protected boolean cerrarFichero() throws IOException{
        boolean cerrado = false;
        flujo.close();
        cerrado = true;
        return cerrado;
   }
    
    protected Object leer(){
       Object este=null; 
        
       return este;
    }
    
    
    
}
