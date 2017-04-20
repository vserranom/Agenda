package com.example.agenda;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import io.realm.Realm;
import io.realm.RealmResults;

public class TabEsborrar extends android.support.v4.app.Fragment {
    private View view;
    ListView listView;
    RealmResults<Person> personas;
    Realm realm;
    RealmAdapter adapter;
    public AlertDialog.Builder dialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.activity_tab_esborrar, container, false);
        cargarDatos();
        consultaRealm();
        adapter= new RealmAdapter(view.getContext(),personas);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                borrarPersona(position);
            }
        });
        return view;
    }

    private void borrarPersona(final int position) {
        dialog.setMessage("Estàs segur que vols esborrar?")
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                Person p= personas.get(position);
                                System.out.println(p.toString());
                                //realm.beginTransaction();
                                personas.get(position).deleteFromRealm();
                                // realm.commitTransaction();
                                adapter.notifyDataSetChanged();
                            }
                        });
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_BACK) {
                            dialog.dismiss();
                        }
                        return true;
                    }
                })
                .show();
    }

    private void cargarDatos() {
        realm=Realm.getDefaultInstance();
        listView=(ListView) view.findViewById(R.id.listView3);
        dialog = new AlertDialog.Builder(view.getContext());
    }

    public void consultaRealm(){
        personas = realm.where(Person.class)
                .findAll();
    }
}

