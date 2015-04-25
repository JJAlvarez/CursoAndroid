package com.example.angelchanquin.database3;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    private EditText txtCodigo;
    private EditText txtNombre;
    private TextView txtResultado;

    private Button btnInsertar;
    private Button btnActualizar;
    private Button btnEliminar;
    private Button btnConsultar;

    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtCodigo = (EditText)findViewById(R.id.TxtReg);
        txtNombre = (EditText)findViewById(R.id.TxtVal);
        txtResultado = (TextView)findViewById(R.id.TxtResultado);

        btnInsertar = (Button)findViewById(R.id.BtnInsertar);
        btnActualizar = (Button)findViewById(R.id.BtnActualizar);
        btnEliminar = (Button)findViewById(R.id.BtnEliminar);
        btnConsultar = (Button)findViewById(R.id.BtnConsultar);

        UsuariosSQLiteHelper usdbh = new UsuariosSQLiteHelper(this, "DBUsuarios", null, 1);

        db = usdbh.getWritableDatabase();

        btnInsertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cod = txtCodigo.getText().toString();
                String nom = txtNombre.getText().toString();

//                String sql = "INSERT INTO Usuarios (codigo, nombre) VALUES ('" + cod + "'," + nom + "')";
//                db.execSQL(sql);

                ContentValues nuevoRegistro = new ContentValues();
                nuevoRegistro.put("codigo", cod);
                nuevoRegistro.put("nombre", nom);

                db.insert("Usuarios", null, nuevoRegistro);
                txtCodigo.setText("");
                txtNombre.setText("");
            }
        });

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cod = txtCodigo.getText().toString();
                String nom = txtNombre.getText().toString();

                ContentValues valores = new ContentValues();
                valores.put("nombre", nom);

                db.update("Usuarios", valores, "codigo = " + cod, null);
                txtCodigo.setText("");
                txtNombre.setText("");
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cod = txtCodigo.getText().toString();

                db.delete("Usuarios", "codigo = " + cod, null);
                txtCodigo.setText("");
                txtNombre.setText("");
            }
        });

        btnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Alternativa 1: metodo rawQuery

//                Cursor c = db.rawQuery("SELECT codigo, nombre FROM Usuarios", null);

                String[] campos = new String[] {"codigo", "nombre"};
                Cursor c = db.query("Usuarios", campos, null, null, null, null, null);

                txtResultado.setText("");

                if(c.moveToFirst()){
                    do{
                        String cod = c.getString(0);
                        String nom = c.getString(1);
                        txtResultado.append(" " + cod + " - " + nom + "\n");
                    }while(c.moveToNext());
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
