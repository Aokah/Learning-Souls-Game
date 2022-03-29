package lsg.bags;

import lsg.armor.DragonSlayerLeggings;
import lsg.armor.RingedKnightArmor;
import lsg.consumables.Consumable;
import lsg.exceptions.BagFullException;
import lsg.weapons.ShotGun;

import java.util.HashSet;

public class Bag {
    private int capacity;
    private int weight;
    private HashSet<Collectible> items = new HashSet<>();
    public static String BULLET_POINT = "\u2219";


    public Bag(int capacity){
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getWeight() {
        return weight;
    }

    public void push(Collectible item) throws BagFullException{
        if(item.getWeight() <= getCapacity() - getWeight()){
            items.add(item);
            this.weight += item.getWeight();
        } else throw new BagFullException(this);
    }

    public Collectible pop(Collectible item){
        for(Collectible t: items){
            if(t==item){
                items.remove(t);
                this.weight -= t.getWeight();
                return t;
            }
        }
        return null;
    }

    public boolean contains(Collectible item){
        for(Collectible t: items){
            if(t==item){
                return true;
            }
        }
        return false;
    }

    public Collectible[] getItems(){
        Collectible[] tab = new Collectible[items.size()];
        int i = 0;
        for(Collectible t: items){
            tab[i] = t;
            i++;
        }

        return tab;
    }

    public String toString(){
        if(this == null) return "null";
        /*else if(items.size() == 0){
            return "Empty";
        }
        else {*/
            String CLASS_NAME = getClass().getSimpleName();
            int ITEM_NUMB = items.size();

            String msg = String.format("%-3s [ %-1d items | %-2d/%-2d kg ]\n", CLASS_NAME, ITEM_NUMB, this.getWeight(), this.getCapacity());
            Collectible[] tab = getItems();
            for (Collectible t : tab) {
                msg += String.format("%-1s %-20s [%-1d kg]\n", BULLET_POINT, t.toString(), t.getWeight());
            }

            return msg;
        //}
    }

    public static void transfer(Bag from, Bag into) {
        if(from == null) return;
        if(into == null) return;
        for(Collectible t: from.getItems()){
            try{
                into.push(from.pop(t));
            } catch (BagFullException e){}
        }
    }

    public static void main(String[] args) {

    }


}
