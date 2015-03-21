package com.example.angelchanquin.menuscontextuales;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    private TextView lblMensaje;
    private ListView lstLista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lblMensaje = (TextView)findViewById(R.id.lblMensaje);
        lstLista = (ListView)findViewById(R.id.LstLista);

        String[] datos = new String[]{
                "Elemento 1", "Elemento 2", "Elemento 3", "Elemento 4", "Elemento 5",
                "Elemento 6", "Elemento 7", "Elemento 8", "Elemento 9", "Elemento 10",
                "Elemento 11", "Elemento 12", "Elemento 13", "Elemento 14", "Elemento 15",
                "Elemento 16", "Elemento 17", "Elemento 18", "Elemento 19", "Elemento 20",
                "Elemento 21", "Elemento 22", "Elemento 23", "Elemento 24", "Elemento 25",
                "Elemento 26", "Elemento 27", "Elemento 28", "Elemento 29", "Elemento 30"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, datos);

        lstLista.setAdapter(adapter);

        registerForContextMenu(lblMensaje);
        registerForContextMenu(lstLista);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getMenuInflater();

        if(v.getId() == R.id.lblMensaje){
            inflater.inflate(R.menu.menu_ctx_etiqueta, menu);
        }
        else if(v.getId() == R.id.LstLista){
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;

            menu.setHeaderTitle(
                lstLista.getAdapter().getItem(info.position).toString()
            );
            inflater.inflate(R.menu.menu_ctx_lista, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()){
            case R.id.CtxLblOpt1:
                lblMensaje.setText("Etiqueta: Opción 1 seleccionada!");
                return true;
            case R.id.CtxLblOpt2:
                lblMensaje.setText("Etiqueta: Opción 2 seleccionada!");
                return true;
            case R.id.CtxLstOpc1List:
                lblMensaje.setText("Lista[" + info.position + "]: Opción 1 seleccionada!");
                return true;
            case R.id.CtxLstOpc2List:
                lblMensaje.setText("Lista[" + info.position + "]: Opción 2 seleccionada!");
                return true;
            default:
                return super.onContextItemSelected(item);
        }
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
}
