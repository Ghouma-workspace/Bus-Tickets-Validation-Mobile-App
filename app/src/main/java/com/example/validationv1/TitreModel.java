package com.example.validationv1;

public class TitreModel {
    private int id;
    private int num;
    private String date;

    //Constructor
    public TitreModel(int id, String date, int num) {
        this.id = id;
        this.num = num;
        this.date = date;
    }

    public TitreModel() {
    }

    //toString


    @Override
    public String toString() {
        return "TitreModel{" +
                "id=" + id +
                " , num" + num +
                ", date='" + date + '\'' +
                '}';
    }

    //Getters And Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
