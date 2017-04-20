package com.example.agenda;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.DynamicRealm;
import io.realm.DynamicRealmObject;
import io.realm.FieldAttribute;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;

public class MyMigration implements RealmMigration {
    @Override
    public void migrate(DynamicRealm realm, long oldV, long newV) {
        RealmSchema schema =realm.getSchema();
        if (oldV==0){
            RealmObjectSchema personSchema = schema.get("Person");

            personSchema
                    .addField("sumaAstral",int.class, FieldAttribute.REQUIRED)
                    .addIndex("sumaAstral")
                    .transform(new RealmObjectSchema.Function(){
                        @Override
                        public void apply(DynamicRealmObject obj) {
                            obj.set("sumaAstral",sumaAstral(obj.getDate("fechaNacimiento")));
                        }
                    });
            oldV++;
        }
    }

    private int sumaAstral(Date fechaNacimiento) throws NumberFormatException {

        SimpleDateFormat df = new SimpleDateFormat("dd");
        String dia = df.format(fechaNacimiento);

        SimpleDateFormat df3 = new SimpleDateFormat("MM");
        String mes = df3.format(fechaNacimiento);

        SimpleDateFormat df2 = new SimpleDateFormat("yyyy");
        String any = df2.format(fechaNacimiento);

        String t = new String(dia+mes+any);

        int total =Integer.parseInt(t);
        int a = 0;
        int suma = 0;

        while(total != 0){
            suma += total % 10;
            total /= 10;
        }

        if(suma > 9){
            while(suma != 0){
                a += suma % 10;
                suma /= 10;
            }
        }
        else{
            a = suma;
        }

        System.out.println(a);

        return a;

    }

}

