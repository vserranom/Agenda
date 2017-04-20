package com.example.agenda;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Person extends RealmObject{
    @PrimaryKey
    private String dni;
    private String name;
    private String apellido;
    private Date fechaNacimiento;
    private String genero;
    private int edad;

    public Person(){}

    public Person(String dni,int edad,String genero, String name, Date fechaNacimiento) {
        this.dni=dni;
        this.edad=edad;
        this.genero = genero;
        this.name = name;
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getAge() {
        Calendar calendarNow = new GregorianCalendar(TimeZone.getTimeZone("Europe/Madrid"));
        int monthDay =calendarNow.get(Calendar.DAY_OF_MONTH);
        int month = calendarNow.get(Calendar.MONTH);
        int year= calendarNow.get(Calendar.YEAR);
        StringBuilder s = new StringBuilder();
        s.append(monthDay+"/"+month+"/"+year);
        //String a = new String(s);
        //Date fechaActual= Date.valueOf(a);
        Calendar calendarNaix= Calendar.getInstance();
        calendarNaix.setTime(fechaNacimiento);

        int año = year - calendarNaix.get(Calendar.YEAR);
        int mes =month- calendarNaix.get(Calendar.MONTH);
        int dia = monthDay- calendarNaix.get(Calendar.DATE);
        //Se ajusta el año dependiendo el mes y el día
        if(mes<0 || (mes==0 && dia<0)){
            año--;
        }
        //Regresa la edad en base a la fecha de nacimiento
        return año;

    }

    @Override
    public String toString() {
        return "Person{" +
                "dni='" + dni + '\'' +
                "name='" + name + '\'' +
                "apellido='"+apellido+'\''+
                "fechaNacimiento=" + fechaNacimiento +
                "genero='" + genero + '\'' +
                "edad=" + edad +
                '}';
    }
}


