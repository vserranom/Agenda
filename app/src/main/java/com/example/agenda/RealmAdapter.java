package com.example.agenda;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import io.realm.OrderedRealmCollection;
import io.realm.RealmBaseAdapter;

public class RealmAdapter extends RealmBaseAdapter<Person> implements ListAdapter {

    OrderedRealmCollection<Person> persones;
    Context context;

    public RealmAdapter(Context context, @Nullable OrderedRealmCollection<Person> data) {
        super(data);
        this.context = context;
        this.persones = data;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.persona, null);

            TextView textViewDni = (TextView) convertView.findViewById(R.id.textViewDni);
            TextView textViewName = (TextView) convertView.findViewById(R.id.nameTextView);
            TextView textViewApellido = (TextView) convertView.findViewById(R.id.apellidosTextView);
            TextView textViewEdad = (TextView) convertView.findViewById(R.id.edadTextView);
            TextView textViewGenero = (TextView) convertView.findViewById(R.id.genereTextView);
            TextView textViewFechaNaixement = (TextView) convertView.findViewById(R.id.fechaNacimientoTextView);

            textViewName.setTextColor(Color.BLACK);
            textViewApellido.setTextColor(Color.BLACK);
            textViewEdad.setTextColor(Color.BLACK);
            textViewGenero.setTextColor(Color.BLACK);
            textViewFechaNaixement.setTextColor(Color.BLACK);
            textViewDni.setTextColor(Color.BLACK);

            Person p = persones.get(position);
            textViewDni.setText(p.getDni());
            textViewName.setText(p.getName());
            textViewApellido.setText(p.getApellido());
            textViewEdad.setText(String.valueOf(p.getAge()));
            textViewGenero.setText(p.getGenero());
            textViewFechaNaixement.setText(p.getFechaNacimiento().toString());
        }
        return convertView;
    }
}
