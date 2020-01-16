package com.example.webview;
import java.util.Date;
public class Price {
    private  double index;
    private double price;
    private  Date date;

    public Price(){
        //
    }

    public Price(double index, double price, Date date){
        this.index =index;
        this.price = price;
        this.date = date;
    }

    public  void setPrice(double price){

        this.price = price;
    }

    public void setIndex(double index){
        this.index = index;
    }

    public void setDate(Date date){
        this.date = date;
    }

    public double getIndex(){
        return index;
    }

    public double getPrice(){
        return price;
    }

    public Date getDate(){
        return date;
    }

}
