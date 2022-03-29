package lsg.consumables;

import lsg.consumables.drinks.Coffee;
import lsg.consumables.drinks.Whisky;
import lsg.consumables.drinks.Wine;
import lsg.consumables.food.Americain;
import lsg.consumables.food.Hamburger;

import java.util.HashSet;

public class MenuBestOfV3 extends HashSet<Consumable>{

    private MenuBestOfV3(){
        this.add(new Hamburger());
        this.add(new Wine());
        this.add(new Americain());
        this.add(new Coffee());
        this.add(new Whisky());
    }

    public String toString(){
        String msg = "MenuBestOfV3 : ";
        int i=1;
        for(Consumable m: this){
            msg += "\n" + i + " : " + m.toString();
            i++;
        }
        return msg;
    }

    public static void main(String[] args) {
        MenuBestOfV3 menu = new MenuBestOfV3();
        System.out.println(menu);
    }
}


