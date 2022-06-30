package com.example.myapplication.Model;

import android.net.Uri;

import java.io.Serializable;

public class Writer  implements Serializable {
    private String Name;
    private String Surname;
    private String Patronymic;
    private String Number;
    private String image;

    public Writer(String toString, String toString1, String toString2, String toString3, String path) {
        Name=toString;
        Surname=toString1;
        Patronymic=toString2;
        Number=toString3;
        setImage(path);
    }
    @Override
    public String toString(){
        return "Name="+Name+" Surname="+Surname+" Patronymic="+Patronymic;
    }
    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSurname() {
        return Surname;
    }

    public void setSurname(String surname) {
        Surname = surname;
    }

    public String getPatronymic() {
        return Patronymic;
    }

    public void setPatronymic(String patronymic) {
        Patronymic = patronymic;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
