package gt.org.kinal.controlesdeseleccion;

/**
 * Created by Angel Chanquin on 21/02/2015.
 */
public class Titular {
    private String titulo;
    private String subTitulo;

    public Titular(String titulo, String subTitulo) {
        this.titulo = titulo;
        this.subTitulo = subTitulo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSubTitulo() {
        return subTitulo;
    }

    public void setSubTitulo(String subTitulo) {
        this.subTitulo = subTitulo;
    }
}
