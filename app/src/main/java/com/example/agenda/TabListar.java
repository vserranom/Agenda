package com.example.agenda;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class TabListar extends android.support.v4.app.Fragment {
    private View view;
    private int edadMa=0, edadMi=0;
    private String genero;
    private Button save;
    private EditText edadMin;
    private EditText edadMax;
    private RadioButton donaLlistar;
    private RadioButton homeLlistar;
    private TextView textView;
    private Boolean ok=false;
    private Boolean okNumber;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.activity_tab_listar,container,false);
        edadMa=0;
        edadMi=0;
        cargarViews();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    edadMa= Integer.parseInt(edadMax.getText().toString());
                    edadMi= Integer.parseInt(edadMin.getText().toString());
                    okNumber=true;
                }catch (NumberFormatException e){
                    okNumber=false;
                }
                if (homeLlistar.isChecked()==true){
                    genero="HOME";
                }else if (donaLlistar.isChecked()){
                    genero="DONA";
                }
                comprobarDatos();

                System.out.println(ok);

                if (ok==true){
                    Intent intent = new Intent(view.getContext(),Listar.class);
                    if (edadMa==0 && edadMi==0){
                        System.out.println("a="+genero);
                        intent.putExtra("genero",genero);
                        intent.putExtra("edadMa",0);
                        intent.putExtra("edadMi",0);
                    }else {
                        intent.putExtra("edadMi",edadMi);
                        intent.putExtra("edadMa",edadMa);
                        intent.putExtra("genero",genero);
                    }
                    startActivity(intent);

                }
            }
        });

        return view;
    }

    public void comprobarDatos(){
        if (homeLlistar.isChecked()==false && donaLlistar.isChecked()==false){
            textView.setText("No has elegido genero.");
            ok=false;
        }else if(edadMa==0){
            ok=true;
        }else if (edadMi>=edadMa){
            textView.setText("Error a les dades");
        }else if(edadMa<=edadMi){
            textView.setText("Error a les dades");
        }else{
            ok=true;
        }

    }

    public void cargarViews(){
        save = (Button) view.findViewById(R.id.find);
        edadMin = (EditText)view.findViewById(R.id.edadMin);
        edadMax = (EditText)view.findViewById(R.id.edadMax);
        homeLlistar= (RadioButton)view.findViewById(R.id.homeLlistar);
        donaLlistar= (RadioButton)view.findViewById(R.id.donaLlistar);
        textView =(TextView)view.findViewById(R.id.textView2);
    }

}
