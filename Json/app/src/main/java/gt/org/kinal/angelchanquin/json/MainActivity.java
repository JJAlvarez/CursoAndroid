package gt.org.kinal.angelchanquin.json;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import gt.org.kinal.angelchanquin.json.Handlers.JsonHandler;
import gt.org.kinal.angelchanquin.json.Models.Autor;


public class MainActivity extends ActionBarActivity {

    private Autor[] autoresArray;
    private ListView lvAutores;
    private List<Autor> autoresList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvAutores = (ListView)findViewById(R.id.LvAutores);
        autoresList = new ArrayList<>();

        CargarAutoresTask task = new CargarAutoresTask();
        task.execute();

        lvAutores.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Autor item = (Autor)parent.getItemAtPosition(position);
                Intent intentLibrosPorAutor = new Intent(MainActivity.this, LibrosPorAutorActivity.class);

                Bundle extras = new Bundle();
                extras.putInt("idAutor", item.getId());

                intentLibrosPorAutor.putExtras(extras);

                startActivity(intentLibrosPorAutor);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    private class AdaptadorAutores extends ArrayAdapter<Autor>{
        public AdaptadorAutores(Context context, Autor[] autores){
            super(context, R.layout.list_item_autor, autores);
        }

        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = LayoutInflater.from(getContext());

            View item = inflater.inflate(R.layout.list_item_autor, null);

            TextView txtVNombreAutor = (TextView)item.findViewById(R.id.TxtNombreAutor);
            txtVNombreAutor.setText(autoresArray[position].getNombre());

            return item;
        }
    }

    private class CargarAutoresTask extends AsyncTask<String, Integer, Boolean>{

        private ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);

        protected void onPreExecute(){
            progressDialog.setMessage("Descargando datos...");
            progressDialog.show();
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    CargarAutoresTask.this.cancel(true);
                }
            });
        }

        protected Boolean doInBackground(String... params){
            JsonHandler handler = new JsonHandler();
            autoresList = handler.getAutores();
            return true;
        }

        protected void onPostExecute(Boolean result){
            autoresArray = new Autor[autoresList.size()];
            autoresList.toArray(autoresArray);

            AdaptadorAutores adaptador = new AdaptadorAutores(MainActivity.this, autoresArray);
            lvAutores.setAdapter(adaptador);
            this.progressDialog.dismiss();
        }
    }
}
