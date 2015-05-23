package com.example.angelchanquin.notificaciones;

import android.support.v4.app.FragmentManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.angelchanquin.notificaciones.helpers.DialogoAlerta;
import com.example.angelchanquin.notificaciones.helpers.DialogoConfirmacion;
import com.example.angelchanquin.notificaciones.helpers.DialogoPersonalizado;
import com.example.angelchanquin.notificaciones.helpers.DialogoSeleccion;


public class MainActivity extends ActionBarActivity {

    private Button btnNotificacion;
    private Button btnAlerta;
    private static final int NOTIFICATION_ALERTA_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnNotificacion = (Button) findViewById(R.id.SendNotification);
        btnAlerta = (Button) findViewById(R.id.SendAlert);

        btnNotificacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent notificationActivityIntent = new Intent(getApplicationContext(), NotificationActivity.class);
                startActivity(notificationActivityIntent);


                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(MainActivity.this)
                                .setSmallIcon(android.R.drawable.stat_sys_warning)
                                .setLargeIcon((((BitmapDrawable) getResources().getDrawable(R.mipmap.ic_launcher)).getBitmap()))
                                .setContentTitle("Mensaje de Alerta")
                                .setContentText("Ejemplo de Notificacion")
                                .setContentInfo("4")
                                .setTicker("Alerta!");

                Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
                notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                PendingIntent contentIntent = PendingIntent.getActivity(MainActivity.this, 0, notificationIntent, 0);

                mBuilder.setContentIntent(contentIntent);

                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                Notification notification = mBuilder.build();
                notification.flags |= Notification.FLAG_AUTO_CANCEL;

                notificationManager.notify(NOTIFICATION_ALERTA_ID,  notification);

            }
        });

        btnAlerta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                //DialogoAlerta dialogo = new DialogoAlerta();
                //DialogoConfirmacion dialogo = new DialogoConfirmacion();
//                DialogoSeleccion dialogo = new DialogoSeleccion();
                DialogoPersonalizado dialogo = new DialogoPersonalizado();
                dialogo.show(fragmentManager, "tagAlerta");
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
