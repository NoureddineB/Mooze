package com.project.mooze.Model;



import com.project.mooze.Model.Order.Order;
import com.project.mooze.Model.Order.Starter;
import com.project.mooze.Model.Order.Suggestion;
import com.project.mooze.Model.Restaurent.Dessert;
import com.project.mooze.Model.Restaurent.Drink;
import com.project.mooze.Model.Restaurent.Main;
import com.project.mooze.Model.Restaurent.Menus;
import com.project.mooze.Model.Restaurent.RestaurantStarter;

public class CartItem {

    public Dessert dessert;
    public Menus menus;
    public Starter starter;
    public Main main;
    public Drink drink;
    public Suggestion suggestion;
    public double price;
    public int restoid;
    public Object object = new Object();
    public int quantity = 0;


    public <T> CartItem(Drink drink,T t, int quantity,double price,int restoid){
        if (t instanceof Dessert) {
            this.dessert = (Dessert) t;
            this.drink = null;
            this.price = price;
            this.restoid = restoid;
            object = t;
        }
        if (t instanceof Menus) {
            this.menus = (Menus) t;
            this.drink = drink;
            this.price = price;
            this.restoid = restoid;
            object = t;
        }
        if (t instanceof Starter) {
            this.starter = (Starter) t;
            this.drink = null;
            this.price = price;
            this.restoid = restoid;
            object = t;
        }
        if (t instanceof Main) {
            this.main = (Main) t;
            this.drink = null;
            this.price = price;
            this.restoid = restoid;
            object = t;
        }
        if (t instanceof Suggestion) {
            this.suggestion= (Suggestion) t;
            this.drink = null;
            this.price = price;
            this.restoid = restoid;
            object = t;
        }
       this.quantity = quantity;
    }
}
