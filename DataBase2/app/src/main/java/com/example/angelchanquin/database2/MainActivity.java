package com.example.angelchanquin.database2;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends ActionBarActivity {

    private EditText txtCodigo;
    private EditText txtNombre;

    private Button btnInsertar;
    private Button btnActualizar;
    private Button btnEliminar;

    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtCodigo = (EditText)findViewById(R.id.TxtReg);
        txtNombre = (EditText)findViewById(R.id.TxtVal);

        btnInsertar = (Button)findViewById(R.id.BtnInsertar);
        btnActualizar = (Button)findViewById(R.id.BtnActualizar);
        btnEliminar = (Button)findViewById(R.id.BtnEliminar);

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

            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cod = txtCodigo.getText().toString();

                db.delete("Usuarios", "codigo = " + cod, null);
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
