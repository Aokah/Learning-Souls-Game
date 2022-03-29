package lsg.consumables;

import lsg.consumables.drinks.Coffee;
import lsg.consumables.drinks.Whisky;
import lsg.consumables.drinks.Wine;
import lsg.consumables.food.Americain;
import lsg.consumables.food.Hamburger;

import java.util.HashSet;

public class MenuBestOfV2{
    private static java.util.HashSet<Consumable> menu = new HashSet<>();

    private MenuBestOfV2(){
        menu.add(new Hamburger());
        menu.add(new Wine());
        menu.add(new Americain());
        menu.add(new Coffee());
        menu.add(new Whisky());
    }

    public String toString(){
        String msg = "MenuBestOfV2 : ";
        int i=1;
        for(Consumable m: menu){
            msg += "\n" + i + " : " + m.toString();
            i++;
        }
        return msg;
    }

    public static void main(String[] args) {
        MenuBestOfV2 menu = new MenuBestOfV2();
        System.out.println(menu);
    }
}


