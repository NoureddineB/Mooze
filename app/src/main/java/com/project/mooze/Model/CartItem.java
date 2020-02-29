package com.project.mooze.Model;



import com.project.mooze.Model.Order.Order;
import com.project.mooze.Model.Order.Starter;
import com.project.mooze.Model.Restaurent.Dessert;
import com.project.mooze.Model.Restaurent.Main;
import com.project.mooze.Model.Restaurent.Menus;
import com.project.mooze.Model.Restaurent.RestaurantStarter;

public class CartItem {

    public Dessert dessert;
    public Menus menus;
    public RestaurantStarter starter;
    public Main main;
    public Object object = new Object();
    public int quantity = 0;


    public <T> CartItem(T t,int quantity){
        if (t instanceof Dessert) {
            this.dessert = (Dessert) t;
            object = t;
        }
        if (t instanceof Menus) {
            this.menus = (Menus) t;
            object = t;
        }
        if (t instanceof RestaurantStarter) {
            this.starter = (RestaurantStarter) t;
            object = t;
        }
        if (t instanceof Main) {
            this.main = (Main) t;
            object = t;
        }
       this.quantity = quantity;
    }
}
