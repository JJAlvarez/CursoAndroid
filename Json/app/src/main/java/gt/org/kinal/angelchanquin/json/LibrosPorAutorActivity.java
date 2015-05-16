package gt.org.kinal.angelchanquin.json;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import gt.org.kinal.angelchanquin.json.Handlers.JsonHandler;
import gt.org.kinal.angelchanquin.json.Models.Autor;
import gt.org.kinal.angelchanquin.json.Models.Libro;


public class LibrosPorAutorActivity extends ActionBarActivity {

    private Libro[] librosArray;
    private ListView lvLibros;
    private List<Libro> librosList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libros_por_autor);

        lvLibros = (ListView)findViewById(R.id.LvLibrosPorAutor);
        librosList = new ArrayList<>();

        Bundle extras = this.getIntent().getExtras();

        CargarLibrosPorAutorTask task = new CargarLibrosPorAutorTask();
        task.execute(String.valueOf(extras.getInt("idAutor")));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_libros_por_autor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class AdaptadorLibros extends ArrayAdapter<Libro> {
        public AdaptadorLibros(Context context, Libro[] libros){
            super(context, R.layout.list_item_libro, libros);
        }

        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = LayoutInflater.from(getContext());

            View item = inflater.inflate(R.layout.list_item_libro, null);

            TextView txtVNombreLibro = (TextView)item.findViewById(R.id.TxtNombreLibro);
            txtVNombreLibro.setText(librosArray[position].getNombre());

            ImageView ivPortada = (ImageView)item.findViewById(R.id.imagePortada);
            Bitmap bitmap = null;
            try {
                bitmap = (Bitmap) new CargarImagenesPortadaTask().execute(String.valueOf(position)).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            ivPortada.setImageBitmap(bitmap);

            return item;
        }
    }

    private class CargarLibrosPorAutorTask extends AsyncTask<String, Integer, Boolean>{

        private ProgressDialog progressDialog = new ProgressDialog(LibrosPorAutorActivity.this);

        protected void onPreExecute(){
            progressDialog.setMessage("Descargando datos...");
            progressDialog.show();
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    CargarLibrosPorAutorTask.this.cancel(true);
                }
            });
        }

        protected Boolean doInBackground(String... params){
            JsonHandler handler = new JsonHandler(LibrosPorAutorActivity.this);
            librosList = handler.getLibrosPorAutor(Integer.parseInt(params[0]));

            return true;
        }

        protected void onPostExecute(Boolean result){
            librosArray = new Libro[librosList.size()];
            librosList.toArray(librosArray);

            AdaptadorLibros adaptador = new AdaptadorLibros(LibrosPorAutorActivity.this, librosArray);
            lvLibros.setAdapter(adaptador);
            this.progressDialog.dismiss();
        }
    }

    private class CargarImagenesPortadaTask extends AsyncTask<String, Integer, Bitmap>{

        protected Bitmap doInBackground(String... params){
            int position = Integer.parseInt(params[0]);
            try{
                String imageUrl = librosList.get(position).getImageURL();
                System.out.println(imageUrl);
                InputStream inputStream = (InputStream) new URL(imageUrl).getContent();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                librosList.get(position).setBitmap(bitmap);
                inputStream.close();
                return bitmap;
            }
            catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }
//        protected void onPostExecute(Boolean result){
//
//        }
    }
}
