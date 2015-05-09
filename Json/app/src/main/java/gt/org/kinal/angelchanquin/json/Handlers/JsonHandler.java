package gt.org.kinal.angelchanquin.json.Handlers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import gt.org.kinal.angelchanquin.json.Models.Autor;
import gt.org.kinal.angelchanquin.json.Models.Libro;

/**
 * Created by angelchanquin on 5/9/2015.
 */
public class JsonHandler {

    private final String API_URL = "http://192.168.100.141/webservice/public/api";
    private final String AUTORES_URL = API_URL + "/autores";
    private final String LIBROS_POR_AUTOR_URL = API_URL + "/librosPorAutor?idAutor=";

    private InputStream getInputStream(String uri){
        try{
            URL jsonUrl = new URL(uri);
            return jsonUrl.openConnection().getInputStream();
        }
        catch (MalformedURLException ex){
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        catch (IOException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public String getData(String uri){
        BufferedReader reader = null;
        try{
            StringBuilder sb = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(this.getInputStream(uri)));
            String line;
            while ((line = reader.readLine()) != null){
                sb.append(line + '\n');
            }
            return sb.toString();
        }
        catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        finally {
            if(reader != null){
                try {
                    reader.close();
                }
                catch (IOException e){
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public List<Autor> getAutores(){

        JSONArray array = null;
        List<Autor> autoresList;

        try {
            array = new JSONArray(this.getData(AUTORES_URL));
            autoresList = new ArrayList<>();
        }catch (JSONException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        for (int i = 0; i < array.length(); i++) {
            JSONObject object = null;
            try{
                object = array.getJSONObject(i);
                Autor autor = new Autor(object.getInt("id"), object.getString("nombre"));
                autoresList.add(autor);
            }catch (JSONException e){
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        return autoresList;
    }

    public List<Libro> getLibrosPorAutor(int idAutor){
        JSONArray array = null;
        List<Libro> librosList;

        try {
            array = new JSONArray(this.getData(LIBROS_POR_AUTOR_URL + idAutor));
            librosList = new ArrayList<>();
        }catch (JSONException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        for (int i = 0; i < array.length(); i++) {
            JSONObject object = null;
            try{
                object = array.getJSONObject(i);

                Libro libro = new Libro();
                libro.setId(object.getInt("id"));
                libro.setNombre(object.getString("nombre"));
                libro.setIdAutor(object.getInt("idAutor"));

                librosList.add(libro);
            }catch (JSONException e){
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        return librosList;
    }
}
