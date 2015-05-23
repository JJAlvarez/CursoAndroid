package com.example.angelchanquin.contentproviders;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog.Calls;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.angelchanquin.contentproviders.ClientesProvider.Clientes;

public class MainActivity extends ActionBarActivity {

    private Button btnInsertar;
    private Button btnConsultar;
    private Button btnEliminar;
    private TextView txtResultado;
    private Button btnLlamadas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtResultado = (TextView) findViewById(R.id.TxtResultado);
        btnConsultar = (Button) findViewById(R.id.BtnConsultar);
        btnInsertar = (Button) findViewById(R.id.BtnInsertar);
        btnEliminar = (Button) findViewById(R.id.BtnEliminar);
        btnLlamadas = (Button) findViewById(R.id.BtnLlamadas);

        btnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] campos = new String[] {
                        Clientes._ID,
                        Clientes.COL_NOMBRE,
                        Clientes.COL_TELEFONO,
                        Clientes.COL_EMAIL
                };
                Uri clientesUri = ClientesProvider.CONTENT_URI;
                ContentResolver cr = getContentResolver();

                Cursor cursor = cr.query(clientesUri, campos, null, null, null);

                if(cursor.moveToFirst()){
                    String nombre;
                    String telefono;
                    String email;

                    int colNombre = cursor.getColumnIndex(Clientes.COL_NOMBRE);
                    int colTelefono = cursor.getColumnIndex(Clientes.COL_TELEFONO);
                    int colEmail = cursor.getColumnIndex(Clientes.COL_EMAIL);

                    do{
                        nombre = cursor.getString(colNombre);
                        telefono = cursor.getString(colTelefono);
                        email = cursor.getString(colEmail);

                        txtResultado.append(nombre + " - " + telefono + " - " + email + "\n");
                    } while (cursor.moveToNext());
                }
             }
        });

        btnInsertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put(Clientes.COL_NOMBRE, "ClienteN");
                values.put(Clientes.COL_TELEFONO, "92139798");
                values.put(Clientes.COL_EMAIL, "laksjdf@lasd.com");

                ContentResolver cr = getContentResolver();

                cr.insert(ClientesProvider.CONTENT_URI, values);
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentResolver cr = getContentResolver();
                cr.delete(ClientesProvider.CONTENT_URI, Clientes.COL_NOMBRE + " = 'ClienteN'", null);
            }
        });

        btnLlamadas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] campos = new String[]{
                        Calls.TYPE,
                        Calls.NUMBER
                };
                Uri llamadasUri = Calls.CONTENT_URI;

                ContentResolver cr = getContentResolver();

                Cursor cursor = cr.query(llamadasUri, campos, null, null, null);

                if(cursor.moveToFirst()){
                    int tipo;
                    String tipoLlamada = "";
                    String telefono;


                    int colTipo = cursor.getColumnIndex(Calls.TYPE);
                    int colTelefono = cursor.getColumnIndex(Calls.NUMBER);

                    txtResultado.setText("");

                    do{
                        tipo = cursor.getInt(colTipo);
                        telefono = cursor.getString(colTelefono);
                        switch (tipo){
                            case Calls.INCOMING_TYPE:
                                tipoLlamada = "ENTRANTE";
                                break;
                            case Calls.OUTGOING_TYPE:
                                tipoLlamada = "SALIENTE";
                                break;
                            case Calls.MISSED_TYPE:
                                tipoLlamada = "PERDIDA";
                                break;
                            default:
                                break;
                        }

                        txtResultado.append(tipoLlamada + " - " + telefono + "\n");
                    }while (cursor.moveToNext());
                }
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
}
