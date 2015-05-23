package com.example.angelchanquin.notificaciones.helpers;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.Toast;

/**
 * Created by angelchanquin on 5/23/2015.
 */
public class DialogoSeleccion extends DialogFragment{

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final String[] items = {"Spanish", "English", "French"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Seleccion")
//                .setItems(items, new DialogInterface.OnClickListener() {
//                .setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener()
//                      @Override
//                      public void onClick(DialogInterface dialog, int which) {
//                          Toast.makeText(getActivity(), "Opcion elegida: " + items[which], Toast.LENGTH_LONG).show();
//                      }
//                  });
               .setMultiChoiceItems(items, null, new DialogInterface.OnMultiChoiceClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                       Toast.makeText(getActivity(), "Opcion elegida: " + items[which], Toast.LENGTH_LONG).show();
                   }
               });
        return builder.create();
    }
}
