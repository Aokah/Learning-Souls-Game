package lsg.consumables;

import lsg.consumables.drinks.Coffee;
import lsg.consumables.drinks.Whisky;
import lsg.consumables.drinks.Wine;
import lsg.consumables.food.Americain;
import lsg.consumables.food.Hamburger;

public class MenuBestOfV1 {
    private static int NB_MAX_ITEMS = 5;
    private static Consumable[] menu = new Consumable[NB_MAX_ITEMS];
    //private static Consumable[] menu2 = {new Hamburger(), new Wine(), new Americain(), new Coffee(), new Whisky()};

    private MenuBestOfV1(){
        menu[0] = new Hamburger();
        menu[1] = new Wine();
        menu[2] = new Americain();
        menu[3] = new Coffee();
        menu[4] = new Whisky();
    }

    /*public String toString(){
        String msg = String.format("MenuBestOfV1 : %n 1 : %-20s %n 2 : %-20s %n 3 : %-20s %n 4 : %-20s %n 5 : %-20s", menu[0].toString(), menu[1].toString(), menu[2].toString(), menu[3].toString(), menu[4].toString());
        return msg;
    }*/

    public String toString(){
        String msg = "MenuBestOfV1 : ";
        int i=1;
        for(Consumable m: menu){
            msg += "\n" + i + " : " + m.toString();
            i++;
        }
        return msg;
    }

    public static void main(String[] args) {
        MenuBestOfV1 menu = new MenuBestOfV1();
        System.out.println(menu);
    }
}


