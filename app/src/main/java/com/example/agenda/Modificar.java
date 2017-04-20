package com.example.agenda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.exceptions.RealmPrimaryKeyConstraintException;

public class Modificar extends AppCompatActivity {
    int pos;
    RealmResults<Person> personas;
    private String n="";
    private String g;
    private String d;
    private Date f;
    private String na;
    private TextView textView;
    private EditText nombre;
    private EditText dni;
    private RadioButton dona;
    private RadioButton home;
    private EditText fechaNacimiento;
    private Button button;
    private Realm realm;
    private  Boolean ok;
    private  Boolean fechaOk;
    Person p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar);
        realm=Realm.getDefaultInstance();
        cargarDatos();
        leerRealmBBDD();
        p = personas.get(pos);
        cargarValores(p);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                n=nombre.getText().toString().trim();
                na= fechaNacimiento.getText().toString().trim();
                d=dni.getText().toString().trim();
                try {
                    f=cambiarStringDate(na);
                    fechaOk=true;
                } catch (ParseException e) {
                    System.out.println("a");
                    fechaOk=false;
                    System.out.println(fechaOk);
                    e.printStackTrace();
                }
                if (home.isChecked()==true){
                    g="HOME";
                }else if (dona.isChecked()){
                    g="DONA";
                }
                comprobarDatos();

                try {
                    if (ok==true){
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm bgRealm) {
                                p.setName(n);
                                p.setFechaNacimiento(f);
                                p.setGenero(g);
                                int edad=p.getAge();
                                p.setEdad(edad);
                            }
                        });
                        Toast.makeText(getBaseContext(),"Persona Modificada",Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getBaseContext(),Menu.class);
                        startActivity(i);
                    }else if (ok==false){
                        Toast.makeText(getBaseContext(), "Error en la lectura de datos.", Toast.LENGTH_SHORT).show();
                    }
                }catch (RealmPrimaryKeyConstraintException e){
                    textView.setText("Aquest dni ya esta a la BBDD");
                }

            }
        });
    }

    private void cargarValores(Person p) {
        nombre.setText(p.getName());
        dni.setText(p.getDni());
        if (p.getGenero().equals("HOME")){
            home.setChecked(true);
        }else if (p.getGenero().equals("DONA")){
            dona.setChecked(true);
        }
        Date d= p.getFechaNacimiento();
        SimpleDateFormat df = new SimpleDateFormat("yyyy");
        String any = df.format(d);
        int dia=d.getDay();
        int mes=d.getMonth();
        StringBuilder f= new StringBuilder();
        f.append(dia+"/"+mes+"/"+any);
        String fecha=new String(f);
        System.out.println(fecha);
        fechaNacimiento.setText(fecha);
    }

    private void leerRealmBBDD() {
        personas = realm.where(Person.class)
                .findAll();
    }

    private Date cambiarStringDate(String naix) throws ParseException {
        Calendar calendario = GregorianCalendar.getInstance();
        Date fecha = calendario.getTime();
        SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd/MM/yyyy");
        fecha = formatoDeFecha.parse(naix);
        return fecha;
    }

    private void cargarDatos() {
        textView=(TextView)findViewById(R.id.textView2);
        nombre=(EditText)findViewById(R.id.name2);
        fechaNacimiento=(EditText)findViewById(R.id.naixament2);
        home= (RadioButton)findViewById(R.id.home2);
        dona= (RadioButton)findViewById(R.id.dona2);
        button=(Button) findViewById(R.id.Añadir2);
        dni=(EditText) findViewById(R.id.dni2);
        pos= this.getIntent().getExtras().getInt("pos");

    }

    public void comprobarDatos(){
        System.out.println("2"+fechaOk);
        if (home.isChecked()==false && dona.isChecked()==false){
            textView.setText("No hi ha genere seleccionat");
            ok=false;
        }else{
            if (fechaOk==false){
                ok=false;
                textView.setText("Data naixament incorrecte");
            }else{
                if (n.equals("")){
                    textView.setText("Nom requerit");
                }else {
                    ok=true;
                }
            }
        }

    }
}
