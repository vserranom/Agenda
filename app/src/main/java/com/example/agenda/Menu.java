package com.example.agenda;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;

public class Menu extends FragmentActivity {

    private FragmentTabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        tabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        tabHost.setup (this, getSupportFragmentManager(), android.R.id.tabcontent);
        tabHost.addTab(tabHost.newTabSpec("Afegir").setIndicator("Afegir"),TabAnadir.class,null);
        tabHost.addTab(tabHost.newTabSpec("LLista").setIndicator("LLista"),TabListar.class, null);
        tabHost.addTab(tabHost.newTabSpec("Modifica").setIndicator("Modifica"),TabModificar.class, null);
        tabHost.addTab(tabHost.newTabSpec("Borra").setIndicator("Borra"),TabEsborrar.class, null);
    }
}
