package com.example.agenda;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class TabAnadir extends Fragment {

    private String n="";
    private String g;
    private Date fecha;
    private String na;
    private String d="";
    private TextView textView;
    private EditText nombre,apellidos,dni;
    private RadioButton dona;
    private RadioButton home;
    private EditText fechaNacimiento;
    private Button button;
    private View view;
    private Realm realm;
    private Boolean ok;
    private Boolean fechaOk;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void listar() {
        RealmResults<Person> results = realm.where(Person.class).findAll();
        for(Person persona : results){
            System.out.println(persona.toString());
        }
    }

    private Date cambiarStringDate(String naix) throws ParseException {
        Calendar calendario = GregorianCalendar.getInstance();
        Date fecha = calendario.getTime();
        SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd/MM/yyyy");
        fecha = formatoDeFecha.parse(naix);
        return fecha;
    }
    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(inflater, container, savedInstanceState);
        view =  inflater.inflate(R.layout.activity_anadir, container, false);

        textView=(TextView)view.findViewById(R.id.textView);
        nombre=(EditText) view.findViewById(R.id.name);
        apellidos=(EditText) view.findViewById(R.id.apellidos);
        fechaNacimiento=(EditText) view.findViewById(R.id.naixament);
        home= (RadioButton)view.findViewById(R.id.home);
        dona= (RadioButton)view.findViewById(R.id.dona);
        button=(Button) view.findViewById(R.id.Añadir);
        dni=(EditText) view.findViewById(R.id.dni);

        realm = Realm.getDefaultInstance();

        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                n = nombre.getText().toString().trim();
                na = fechaNacimiento.getText().toString().trim();
                d = dni.getText().toString().trim();
                try{
                    fecha = cambiarStringDate(na);
                    fechaOk = true;
                } catch (ParseException e){
                    System.out.println("a");
                    fechaOk = false;
                    System.out.println(fechaOk);
                    e.printStackTrace();
                }
                if (home.isChecked()==true){
                    g="HOME";
                }else if (dona.isChecked()==true){
                    g="DONA";
                }
                comprobarDatos();

                try {
                    if (ok==true){
                        realm.executeTransaction(new Realm.Transaction(){

                            @Override
                            public void execute(Realm bgrealm) {
                                Person persona = realm.createObject(Person.class,d);
                                persona.setName(n);
                                persona.setFechaNacimiento(fecha);
                                int edad = persona.getAge();
                                persona.setEdad(edad);
                                persona.setGenero(g);
                            }
                        });
                        listar();
                        Toast.makeText(getContext(),"Dades Guardades a la BBDD",Toast.LENGTH_SHORT).show();

                    } else if (ok == false){
                        Toast.makeText(getContext(),"Error en la lectura de dades",Toast.LENGTH_SHORT).show();
                    }
                }catch (RealmPrimaryKeyConstraintException e) {
                    textView.setText("Aquest DNI ja existeis a la BBDD");
                }
            }
        });
        return view;
    }

    public void comprobarDatos(){
        System.out.println("2"+fechaOk);
        if (home.isChecked()==false && dona.isChecked()==false){
            textView.setText("No hi ha genere seleccionat");
            ok=false;
        }else{
            if (fechaOk==false){
                ok=false;
                textView.setText("Data naixametn incorrecte");
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
