package gt.org.kinal.controlesdeseleccion;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class ListViewActivity extends ActionBarActivity {

    private Titular[] datos = new Titular[]{
            new Titular("Titulo 1", "Subtitulo Largo 1"),
            new Titular("Titulo 2", "Subtitulo Largo 2"),
            new Titular("Titulo 3", "Subtitulo Largo 3"),
            new Titular("Titulo 4", "Subtitulo Largo 4"),
            new Titular("Titulo 5", "Subtitulo Largo 5"),
            new Titular("Titulo 6", "Subtitulo Largo 6"),
            new Titular("Titulo 7", "Subtitulo Largo 7"),
            new Titular("Titulo 8", "Subtitulo Largo 8"),
            new Titular("Titulo 9", "Subtitulo Largo 9"),
            new Titular("Titulo 10", "Subtitulo Largo 10"),
            new Titular("Titulo 11", "Subtitulo Largo 11"),
            new Titular("Titulo 12", "Subtitulo Largo 12"),
            new Titular("Titulo 13", "Subtitulo Largo 13"),
            new Titular("Titulo 14", "Subtitulo Largo 14"),

    };

    private ListView listaOpciones;
    private TextView lblOpSeleccionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);


        listaOpciones = (ListView)findViewById(R.id.listOpciones);
        lblOpSeleccionada = (TextView)findViewById(R.id.lblSeleccion);

        View header = getLayoutInflater().inflate(R.layout.list_header, null);
        listaOpciones.addHeaderView(header);

        AdaptadorTitulares adaptador = new AdaptadorTitulares(this, datos);
        listaOpciones.setAdapter(adaptador);

        listaOpciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0){
                    Titular item = (Titular)parent.getItemAtPosition(position);

                    lblOpSeleccionada.setText("Opcion Seleccionada: " + item.getTitulo());
                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_view, menu);
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
}
