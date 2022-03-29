package lsg.exceptions;

public class NoBagException extends Exception{
    public NoBagException(){
        super("No Bag !");
    }

    public String toString(){
        return "No Bag !";
    }
}
