package com.example.angelchanquin.xml;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;


public class MainActivity extends ActionBarActivity {

    //private TextView txtResultado;
    private ListView lstNoticias;
    private Button btnCargar;
    private List<Noticia> noticias;
    private Noticia[] noticiasArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //txtResultado = (TextView)findViewById(R.id.txtResultado);
        btnCargar = (Button)findViewById(R.id.btnCargar);
        lstNoticias = (ListView)findViewById(R.id.LstNoticias);

        btnCargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CargarXmlTask tarea = new CargarXmlTask();
                tarea.execute("http://www.europapress.es/rss/rss.aspx");
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

    private class CargarXmlTask extends AsyncTask<String, Integer, Boolean>{

        private ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);

        protected  void onPreExecute(){
            progressDialog.setMessage("Descargando datos...");
            progressDialog.show();
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    CargarXmlTask.this.cancel(true);
                }
            });
        }

        protected Boolean doInBackground(String... params){
            RssParserDom saxParser = new RssParserDom(params[0]);
            noticias = saxParser.parse();
            return true;
        }

        protected void onPostExecute(Boolean result){
            noticiasArray = new Noticia[noticias.size()];
            noticias.toArray(noticiasArray);

            AdaptadorNoticias adaptador =
                    new AdaptadorNoticias(MainActivity.this, noticiasArray);

            lstNoticias.setAdapter(adaptador);

            this.progressDialog.dismiss();

        }
    }

    private class AdaptadorNoticias extends ArrayAdapter<Noticia>{
        public AdaptadorNoticias(Context context, Noticia[] noticias){
            super(context, R.layout.listitem_noticia, noticias);
        }

        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View item = inflater.inflate(R.layout.listitem_noticia, null);

            TextView lblTitulo = (TextView)item.findViewById(R.id.LblTitulo);
            lblTitulo.setText(noticiasArray[position].getTitulo());

            TextView lblFecha = (TextView)item.findViewById(R.id.LblFecha);
            lblFecha.setText(noticiasArray[position].getPubDate());

            return(item);
        }
    }
}
