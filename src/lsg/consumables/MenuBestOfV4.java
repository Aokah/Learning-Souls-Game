package lsg.consumables;

import lsg.consumables.drinks.Coffee;
import lsg.consumables.drinks.Whisky;
import lsg.consumables.drinks.Wine;
import lsg.consumables.food.Americain;
import lsg.consumables.food.Hamburger;
import lsg.consumables.repair.RepairKit;

import java.util.HashSet;

public class MenuBestOfV4 extends java.util.LinkedHashSet<Consumable>{

    public MenuBestOfV4(){
        this.add(new Hamburger());
        this.add(new Wine());
        this.add(new Americain());
        this.add(new Coffee());
        this.add(new Whisky());
        this.add(new RepairKit());
    }

    public String toString(){
        String msg = "MenuBestOfV4 : ";
        int i=1;
        for(Consumable m: this){
            msg += "\n" + i + " : " + m.toString();
            i++;
        }
        return msg;
    }

    public static void main(String[] args) {
        MenuBestOfV4 menu = new MenuBestOfV4();
        System.out.println(menu);
    }
}


