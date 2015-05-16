package gt.org.kinal.angelchanquin.json;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import gt.org.kinal.angelchanquin.json.Handlers.JsonHandler;
import gt.org.kinal.angelchanquin.json.Models.Autor;


public class MainActivity extends ActionBarActivity {

    private Autor[] autoresArray;
    private ListView lvAutores;
    private List<Autor> autoresList;
    private ProgressDialog progressDialog;
    private SwipeRefreshLayout refreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvAutores = (ListView)findViewById(R.id.LvAutores);
        autoresList = new ArrayList<>();
        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.SwipeRefreshAutores);

        if(isOnline()){
            progressDialog = new ProgressDialog(this);
            cargarDatos();
        }else{
            Toast.makeText(this, "Network is not available", Toast.LENGTH_LONG).show();
        }

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(isOnline()){
                    refrescarDatos();
                }else{
                    Toast.makeText(MainActivity.this, "Network is not available", Toast.LENGTH_LONG).show();
                    refreshLayout.setRefreshing(false);
                }
            }
        });

        lvAutores.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Autor item = (Autor) parent.getItemAtPosition(position);
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

    public void cargarDatos(){
        progressDialog.setMessage("Cargando datos...");
        progressDialog.show();

        CargarAutoresTask task = new CargarAutoresTask();
        task.execute();
    }

    public void refrescarDatos(){
        CargarAutoresTask task = new CargarAutoresTask();
        task.execute();
    }

    public boolean isOnline(){
        ConnectivityManager manager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = manager.getActiveNetworkInfo();
        if(netInfo != null && netInfo.isConnectedOrConnecting()){
            return true;
        }else{
            return false;
        }
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

        protected Boolean doInBackground(String... params){
            try{
                JsonHandler handler = new JsonHandler(MainActivity.this);
                autoresList = handler.getAutores();
            }catch (Exception e){
                e.printStackTrace();
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, getString(R.string.error_from_server), Toast.LENGTH_LONG).show();
                    }
                });
            }
            return true;
        }

        protected void onPostExecute(Boolean result){
            autoresArray = new Autor[autoresList.size()];
            autoresList.toArray(autoresArray);
            AdaptadorAutores adaptador = new AdaptadorAutores(MainActivity.this, autoresArray);
            lvAutores.setAdapter(adaptador);
            progressDialog.dismiss();
            refreshLayout.setRefreshing(false);
        }
    }
}
