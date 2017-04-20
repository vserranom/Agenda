package com.example.agenda;

import android.app.Application;

import io.realm.Realm;

public class RealmIO extends Application{

    public void onCreate (){
        super.onCreate();
        Realm.init(this);
        System.out.println("REALMIO INICIALITZAT");
    }
}