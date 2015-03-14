package gt.org.kinal.fragmentsdemo;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class ListadoFragment extends Fragment {

    private Correo[] datos =
            new Correo[]{
                    new Correo("Juan Perez", "Curso Android", "Cuando es la entrega de proyecto?"),
                    new Correo("Jesus Solis", "Curso Matematica", "Cuando es el examen final?"),
                    new Correo("Jose Perez", "Fiesta", "Que va a haber hoy?")
            };
    private ListView lstListadoCorreos;

    private CorreosListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_listado, container, false);
    }

    @Override
    public void onActivityCreated(Bundle state){
        super.onActivityCreated(state);

        lstListadoCorreos = (ListView)getView().findViewById(R.id.LstListadoCorreos);
        lstListadoCorreos.setAdapter(new AdaptadorCorreos(this));

        lstListadoCorreos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> list, View view, int pos, long id){
                if (listener != null){
                    listener.onCorreoSeleccionado(
                            (Correo)lstListadoCorreos.getAdapter().getItem(pos));
                }
            }
        });

    }

    class AdaptadorCorreos extends ArrayAdapter<Correo>{

        Activity context;

        AdaptadorCorreos(Fragment context){
            super(context.getActivity(), R.layout.correo_listitem, datos);
            this.context = context.getActivity();
        }

        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = context.getLayoutInflater();
            View item = inflater.inflate(R.layout.correo_listitem, null);

            TextView lblDe = (TextView)item.findViewById(R.id.LblDe);
            lblDe.setText(datos[position].getDe());

            TextView lblAsunto = (TextView)item.findViewById(R.id.LblAsunto);
            lblAsunto.setText(datos[position].getAsunto());

            return item;
        }

    }

    public interface CorreosListener{
        void onCorreoSeleccionado(Correo c);
    }

    public void setCorreosListener(CorreosListener listener){
        this.listener = listener;
    }

}
