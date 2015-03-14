package com.example.angelchanquin.menus;

import android.content.res.Configuration;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class MainActivity extends ActionBarActivity {

    private String[] opcionesMenu;
    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private CharSequence tituloSeccion;
    private CharSequence tituloApp;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        opcionesMenu = new String[] {"Opción 1", "Opción 2", "Opción 3"};
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawerList = (ListView)findViewById(R.id.left_drawer);

        drawerList.setAdapter( new ArrayAdapter<String>(
                getSupportActionBar().getThemedContext(),
                //(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) ? android.R.layout.simple_list_item_activated_1 :  android.R.layout.simple_list_item_1
                android.R.layout.simple_list_item_activated_1
                , opcionesMenu
        ));

        drawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Fragment fragment = null;

                switch (position){
                    case 0:
                        fragment = new Fragment1();
                        break;
                    case 1:
                        fragment = new Fragment2();
                        break;
                    case 2:
                        fragment = new Fragment3();
                        break;
                }

                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame,fragment).commit();

                drawerList.setItemChecked(position, true);

                tituloSeccion = opcionesMenu[position];
                getSupportActionBar().setTitle(tituloSeccion);

                drawerLayout.closeDrawer(drawerList);
            }
        });

        tituloSeccion = getTitle();
        tituloApp = getTitle();

        drawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout,
                R.drawable.ic_action_action_view_headline,
                R.string.drawer_open,
                R.string.drawer_close) {
            public void onDrawerClosed(View view){
                getSupportActionBar().setTitle(tituloSeccion);
                ActivityCompat.invalidateOptionsMenu(MainActivity.this);
            }
            public void onDrawerOpened(View view){
                getSupportActionBar().setTitle(tituloApp);
                ActivityCompat.invalidateOptionsMenu(MainActivity.this);
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        boolean menuAbierto = drawerLayout.isDrawerOpen(drawerList);

        if(menuAbierto){
            menu.findItem(R.id.menu_new).setVisible(false);
            menu.findItem(R.id.menu_save).setVisible(false);
        }
        else{
            menu.findItem(R.id.menu_new).setVisible(true);
            menu.findItem(R.id.menu_save).setVisible(true);
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        if(drawerToggle.onOptionsItemSelected(item)){
            return true;
        }

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       switch (id){
           case R.id.menu_new:
               Log.i("ActionBar", "Nuevo!");
               return true;
           case R.id.menu_save:
               Log.i("ActionBar", "Guardar!");
               return true;
           case R.id.action_settings:
               Log.i("ActionBar", "Preferencias");
               return true;
           default:
               return super.onOptionsItemSelected(item);
       }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }
}
