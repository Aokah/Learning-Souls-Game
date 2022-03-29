package lsg.consumables;

import javafx.beans.property.SimpleBooleanProperty;
import lsg.bags.Collectible;
import lsg.exceptions.ConsumeEmptyException;

public class Consumable implements Collectible {

    private String name;
    private int capacity;
    private String stat;
    private SimpleBooleanProperty isEmpty;

    private void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    protected void setCapacity(int capacity) {
        this.capacity = capacity;
        if(capacity == 0){
            isEmpty.set(true);
        } else {
            isEmpty.set(false);
        }
    }

    public String getStat() {
        return stat;
    }

    private void setStat(String stat) {
        this.stat = stat;
    }

    public SimpleBooleanProperty isEmptyProperty(){
        return isEmpty;
    }

    public Consumable(String name, int capacity, String stat){
        isEmpty = new SimpleBooleanProperty();
        setName(name);
        setCapacity(capacity);
        setStat(stat);
    }

    public String toString(){
        String msg = String.format("%-10s [%-2d %-4s point(s)]", this.name, this.capacity, this.stat);
        return msg;
    }

    public int use() throws ConsumeEmptyException {
        if(this.capacity == 0) throw new ConsumeEmptyException(this);
        int value = this.capacity;
        setCapacity(0);
        return value;
    }

    @Override
    public int getWeight() {
        return 1;
    }
}
