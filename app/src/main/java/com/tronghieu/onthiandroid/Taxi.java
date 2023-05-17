package com.tronghieu.onthiandroid;

public class Taxi {

    private int id;


    float getTotal(){
        return this.price * (100 - this.getDiscount())/100;
    }


    private int Discount;

    public int getDiscount() {
        return Discount;
    }

    public void setDiscount(int discount) {
        Discount = discount;
    }

    private String plate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlate() {
        return plate;
    }

    public Taxi(){

    }

    public Taxi(int id, String plate, String road, float price, int discount) {
        this.id = id;
        this.plate = plate;
        this.road = road;
        this.price = price;
        this.Discount = discount;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getRoad() {
        return road;
    }

    public void setRoad(String road) {
        this.road = road;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    private String road;
    private float price;



}
