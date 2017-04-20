package com.example.agenda;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import io.realm.Realm;
import io.realm.RealmResults;

public class Listar extends AppCompatActivity {

    ListView listView;
    String genero;
    int edadMa;
    int edadMi;
    RealmResults<Person> personas;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar);

        realm=Realm.getDefaultInstance();
        lecturaDatosActivity();
        consultaRealm();
        RealmAdapter adapter= new RealmAdapter(getApplicationContext(),personas);
        listView=(ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }

    public void lecturaDatosActivity() {
        Bundle datos = this.getIntent().getExtras();

        ///peta aqui java.lang.ClassCastException: java.lang.Integer cannot be cast to java.lang.String
        edadMa = datos.getInt("edadMa");
        edadMi = datos.getInt("edadMi");
        genero = datos.getString("genero");
        System.out.println(genero);
    }

    public void consultaRealm(){
        if (edadMa==0){
            personas = realm.where(Person.class)
                    .equalTo("genero",genero)
                    .findAll();
        }else{
            personas = realm.where(Person.class)
                    .equalTo("genero",genero)
                    .greaterThanOrEqualTo("edad",edadMi)
                    .lessThanOrEqualTo("edad",edadMa)
                    .findAll();
        }
    }
}
