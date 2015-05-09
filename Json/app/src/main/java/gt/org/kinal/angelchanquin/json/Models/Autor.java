package gt.org.kinal.angelchanquin.json.Models;

/**
 * Created by angelchanquin on 5/9/2015.
 */
public class Autor {

    private int id;
    private String nombre;

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Autor(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
}
