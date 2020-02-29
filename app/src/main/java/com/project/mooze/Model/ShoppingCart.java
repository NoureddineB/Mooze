package com.project.mooze.Model;

import android.content.Context;
import android.widget.Toast;

import com.project.mooze.Model.Order.Starter;
import com.project.mooze.Model.Restaurent.Dessert;
import com.project.mooze.Model.Restaurent.Main;
import com.project.mooze.Model.Restaurent.Menus;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class ShoppingCart {

    public void addItem(CartItem cartItem,Context context) {
        List<CartItem> cart = ShoppingCart.getCart(context);
        CartItem targetItem = singleOrNull(cart,cartItem);
        if (targetItem == null) {
            cartItem.quantity++;
            cart.add(cartItem);
        } else {
            targetItem.quantity++;
        }
        ShoppingCart.saveCart(cart,context);
    }

    private CartItem singleOrNull(List<CartItem> cartItemList,CartItem cartItem){
        int id = 0;
        if (cartItem.object == new Dessert()){
            id = cartItem.dessert.getId();
        }
        if (cartItem.object == new Main()){
            id = cartItem.main.getId();
        }
        if (cartItem.object == new Starter()){
            id = cartItem.starter.getId();
        }
        if (cartItem.object == new Menus()){
            id = cartItem.menus.getId();
        }
        if (cartItemList.size() == 1 && cartItemList.size() == id){
            return cartItemList.get(0);
        }else{
        return null;
        }
    }


    public void removeItem(CartItem cartItem, Context context) {
        List<CartItem> cart = ShoppingCart.getCart(context);
        CartItem targetItem = singleOrNull(cart,cartItem);
        if (targetItem != null) {
            if (targetItem.quantity > 0) {
                targetItem.quantity--;
            } else {
                cart.remove(targetItem);
            }
        }

        ShoppingCart.saveCart(cart,context);
    }
    public static void clearCart(List<CartItem> cart,Context context) {
       if (cart != null){
           cart.clear();
       }
        ShoppingCart.saveCart(cart,context);
    }

    private  static void saveCart(List<CartItem> cart,Context context) {
        Paper.init(context);
        Paper.book().write("cart", cart);
    }

    public static List<CartItem> getCart(Context context) {
        Paper.init(context);
        return Paper.book().read("cart",new ArrayList<CartItem>());
    }

    public static int getShoppingCartSize(Context context) {
        int cartSize;
        cartSize = ShoppingCart.getCart(context).size();
        return cartSize;
    }
}

