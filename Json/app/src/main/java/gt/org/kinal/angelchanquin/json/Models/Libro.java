package gt.org.kinal.angelchanquin.json.Models;

/**
 * Created by angelchanquin on 5/9/2015.
 */
public class Libro {

    private int id;
    private String nombre;
    private int idAutor;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(int idAutor) {
        this.idAutor = idAutor;
    }
}
