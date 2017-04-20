package com.example.agenda;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import io.realm.Realm;
import io.realm.RealmResults;

public class TabModificar extends android.support.v4.app.Fragment {
    private View view;
    ListView listView;
    String genero;
    int edadMa;
    int edadMi;
    RealmResults<Person> personas;
    Realm realm;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.activity_tab_modificar, container, false);
        cargarDatos();
        consultaRealm();
        RealmAdapter adapter= new RealmAdapter(view.getContext(),personas);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(view.getContext(),Modificar.class);
                i.putExtra("pos",position);
                startActivity(i);
            }
        });
        return view;
    }

    private void cargarDatos() {
        realm=Realm.getDefaultInstance();
        listView=(ListView) view.findViewById(R.id.listView2);
    }


    public void consultaRealm(){
        personas = realm.where(Person.class)
                .findAll();
    }
}

