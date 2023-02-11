
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Administrador
 */
public class pruebatreempa {

    private static File FicheroIndices = new File("FicheroIndices.dat");

    public static void main(String[] args) {
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(FicheroIndices));
            ois = new ObjectInputStream(new FileInputStream(FicheroIndices));
            Map<String, Integer> esto = new TreeMap<String, Integer>();
            esto.put("100000a", 1);//91
            esto.put("100001a", 10);//10*91
            esto.put("100002a", 20);//20*91
            esto.put("100003a", 30);//30*91
            esto.put("000000a", 40);//40*91
            //System.out.println(esto);

            TreeMap<Integer, String> loOtro = new TreeMap<Integer, String>();
            for (Map.Entry<String, Integer> entry : esto.entrySet()) {
                loOtro.put(entry.getValue(), entry.getKey());
            }
            //System.out.println(loOtro);
            oos.writeObject(esto);
            TreeMap aux = (TreeMap)ois.readObject();
            // Mientras haya objetos
            while (aux != null) {
                if (aux instanceof TreeMap) {
                    System.out.println(aux);//devuelve to el tremap
                    System.out.println(aux.firstEntry());//devuelve la primera entrada clave valor
                    System.out.println(aux.firstKey());//devuelve la primera clave
                    System.out.println(aux.values());//devuelve los valores
                    
                    System.out.println(aux.size());//devuelve el tamaño
                    System.out.println(aux.containsKey("100000a"));//devuelve true si la key esta
                    System.out.println(aux.containsValue(40));//devuelve true si el valors esta
                    System.out.println(aux.remove("100000a"));//deletea una clave-valor 
                    System.out.println(aux);//devuelve
                    String key;
                    for (Map.Entry<String, Integer> entry : esto.entrySet()) {
                        key=entry.getKey();// asi saco todas las claves
                        
                    }
                    
                }
                aux = (TreeMap)ois.readObject();
            }            
        } catch (IOException ex) {
        } catch (ClassNotFoundException ex) {
        } finally {
            try {
                oos.close();
            } catch (IOException ex) {
            }
            try {
                ois.close();
            } catch (IOException ex) {
            }
        }
    }
}
/*
 public void anhadeFichero(String fichero) {
 try {
 // Se usa un ObjectOutputStream que no escriba una cabecera en
 // el stream.
 MiObjectOutputStream oos = new MiObjectOutputStream(
 new FileOutputStream(fichero, true));
 // Se hace el new fuera del bucle, sólo hay una instancia de persona.
 // Se debe usar entonces writeUnshared().
 Persona p = new Persona(5);
 for (int i = 5; i < 10; i++) {
 p.setPersona(i);   // Se rellenan los datos de Persona.
 oos.writeUnshared(p);
 }
 oos.close();
 } catch (Exception e) {
 e.printStackTrace();
 }

 }
 */
