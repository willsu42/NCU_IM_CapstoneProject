package com.example.webview;
import java.util.Date;
public class Price {
    private  String index;
    private double price;
    private  Date date;

    public Price(){
        //
    }

    public Price(String index, double price, Date date){
        this.index =index;
        this.price = price;
        this.date = date;
    }

    public  void setPrice(double price){

        this.price = price;
    }

    public void setIndex(String index){
        this.index = index;
    }

    public void setDate(Date date){
        this.date = date;
    }

    public String getIndex(){
        return index;
    }

    public double getPrice(){
        return price;
    }

    public Date getDate(){
        return date;
    }

}
