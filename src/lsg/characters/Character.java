package lsg.characters;

import java.util.Locale;

import javafx.beans.property.SimpleDoubleProperty;
import lsg.exceptions.*;
import lsg.armor.DragonSlayerLeggings;
import lsg.bags.Bag;
import lsg.bags.Collectible;
import lsg.bags.SmallBag;
import lsg.consumables.drinks.Coffee;
import lsg.consumables.drinks.Drink;
import lsg.consumables.drinks.Whisky;
import lsg.consumables.drinks.Wine;
import lsg.consumables.food.Americain;
import lsg.consumables.food.Food;
import lsg.consumables.food.Hamburger;
import lsg.consumables.repair.RepairKit;
import lsg.helper.Dice;
import lsg.weapons.Weapon;
import lsg.consumables.Consumable;

public abstract class Character {

	private static final String MSG_ALIVE = "(ALIVE)" ;
	private static final String MSG_DEAD = "(DEAD)" ;
	
	private String name ; // Nom du personnage
	
	private int maxLife, life ; 		// Nombre de points de vie restants
	private int maxStamina, stamina ;	// Nombre de points d'action restants
	
	private Weapon weapon ;

	private Consumable consumable;

	protected Bag bag;

	private SimpleDoubleProperty lifeRate = new SimpleDoubleProperty();
	private SimpleDoubleProperty stamRate = new SimpleDoubleProperty();

	public SimpleDoubleProperty lifeRateProperty(){
		return lifeRate;
	}

	public SimpleDoubleProperty stamRateProperty(){
		return stamRate;
	}

	public Consumable getConsumable() {
		return consumable;
	}

	public void setConsumable(Consumable consumable) {
		this.consumable = consumable;
	}

	private final Dice dice101 = new Dice(101) ;
	
	public Character(String name) {
		this.name = name ;
		this.bag = new SmallBag();
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getMaxLife() {
		return maxLife;
	}
	
	protected void setMaxLife(int maxLife) {
		this.maxLife = maxLife;
		lifeRate.set((double)life/maxLife);
	}
	
	public int getLife() {
		return life;
	}
	
	protected void setLife(int life) {
		this.life = life;
		lifeRate.set((double)life/maxLife);
	}
	
	public int getMaxStamina() {
		return maxStamina;
	}
	
	protected void setMaxStamina(int maxStamina) {
		this.maxStamina = maxStamina;
		stamRate.set((double)stamina/maxStamina);
	}
	
	public int getStamina() {
		return stamina;
	}
	
	protected void setStamina(int stamina) {
		this.stamina = stamina;
		stamRate.set((double)stamina/maxStamina);
	}
	
	public boolean isAlive(){
		return life > 0 ;
	}
	
	public void printStats(){
		System.out.println(this);
	}

	public static String LIFE_STAT_STRING = "life";

	public static String STAM_STAT_STRING = "stamina";

	public static String PROTEC_STAT_STRING = "protection";

	public static String BUFF_STAT_STRING = "buff";
	
	@Override
	public String toString() {
		
		String classe = getClass().getSimpleName() ;
		String life = String.format("%5d", getLife()) ; 
		String stam = String.format("%5d", getStamina()) ; 
		String protection = String.format(Locale.US, "%6.2f", computeProtection()) ;
		String buff = String.format(Locale.US, "%6.2f", computeBuff()) ;
		
		String msg = String.format("%-20s %-20s " + LIFE_STAT_STRING + ":%-10s " + STAM_STAT_STRING + ":%-10s " + PROTEC_STAT_STRING + ":%-10s " + BUFF_STAT_STRING + ":%-10s", "[ " + classe + " ]", getName(), life, stam, protection, buff) ;
		
		String status ;
		if(isAlive()){
			status = MSG_ALIVE ;
		}else{
			status = MSG_DEAD ;
		}
		
		return msg + status ;
	}
	
	public int attack() throws WeaponNullException, WeaponBrokenException, StaminaEmptyException{
		return attackWith(this.getWeapon()) ;
	}
	
	/**
	 * Calcule une attaque en fonction d'une arme.
	 * Le calcul dépend des statistiques de l'arme, de la stamina (restante) du personnage et des buffs eventuels
	 * 
	 * @param weapon : l'arme utilisée.
	 * @return la valeur de l'attaque eventuellement buffée ; 0 si l'arme est cassée.
	 */
	protected int attackWith(Weapon weapon) throws WeaponNullException, WeaponBrokenException, StaminaEmptyException {
		if(weapon == null) throw new WeaponNullException();
		if(weapon.isBroken()) throw new WeaponBrokenException(weapon);
		if(stamina == 0) throw new StaminaEmptyException();
		int min = weapon.getMinDamage() ;
		int max = weapon.getMaxDamage() ;
		int cost = weapon.getStamCost() ;

		int attack = 0 ;
		
		if(!weapon.isBroken()){
			attack = min + Math.round((max-min) * dice101.roll() / 100.f) ;
			int stam = getStamina() ;
			if(cost <= stam){ // il y a assez de stam pour lancer l'attaque
				setStamina(getStamina()-cost);
			}else{
				attack = Math.round(attack * ((float)stam / cost)) ;
				setStamina(0);
			}
			
			weapon.use();
		}
		
		return attack + Math.round(attack*computeBuff()/100);
	}
	
	public Weapon getWeapon() {
		return weapon;
	}
	
	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}
	
	/**
	 * @return le nombre de points de buff du personnage
	 */
	protected abstract float computeBuff() ; 
	
	/**
	 * @return le nombre de points de protection du personnage
	 */
	protected abstract float computeProtection() ;
	
	/**
	 * Calcule le nombre de PV retires en tenant compte de la protection
	 * @param value : le montant des degats reçus
	 * @return le nombre de PV effectivement retires (value reduite par la protecion si assez de vie ; le reste de la vie sinon)
	 */
	public int getHitWith(int value){
		
		int life = getLife() ;
		int dmg ;
		float protection = computeProtection() ;
		if(protection > 100) protection = 100 ; // si la protection depasse 100, elle absorbera 100% de l'attaque
		value = Math.round(value - (value * protection / 100)) ;
		dmg = (life > value) ? value : life ; 
		setLife(life-dmg);
		return dmg ;

	}

	private void drink(Drink drink) throws ConsumeNullException, ConsumeEmptyException {
		if(drink == null) throw new ConsumeNullException(drink);
		System.out.println(getName() + " drinks " + drink);
		int value = drink.use();
		setStamina(getStamina()+value);

		value = (getStamina() > getMaxStamina()) ? getMaxStamina() : getStamina();
		setStamina(value);
	}

	private void eat(Food food) throws ConsumeNullException, ConsumeEmptyException{
		if(food == null) throw new ConsumeNullException(food);
		System.out.println(getName() + " eats " + food);
		int value = food.use();
		setLife(getLife()+value);

		value = (getLife() > getMaxLife()) ? getMaxLife() : getLife();
		setLife(value);
	}

	private void repairWeaponWith(RepairKit kit) throws WeaponNullException, ConsumeNullException{
		if(weapon == null) throw new WeaponNullException();
			String msg = String.format("%-5s repairs %-20s with %-20s", getName(), weapon.toString(), kit.toString());
			System.out.println(msg);
			weapon.repairWith(kit);
	}

	public void use(Consumable consumable) throws ConsumeRepairNullWeaponException, ConsumeNullException, ConsumeEmptyException {
		if(consumable == null) throw new ConsumeNullException(consumable);
		if(consumable instanceof Food){
			eat((Food)consumable); //on teste le cast d'item
		}
		else if(consumable instanceof Drink){
			drink((Drink)consumable); //cela fonctionne
		}
		else if(consumable instanceof RepairKit){
			try {
				repairWeaponWith((RepairKit) consumable);
			} catch (WeaponNullException e){
				throw new ConsumeRepairNullWeaponException(consumable);
			}
		}
	}

	public void consume() throws ConsumeNullException, ConsumeEmptyException, ConsumeRepairNullWeaponException{
		use(this.consumable);
	}

	public void pickUp(Collectible item) throws NoBagException, BagFullException{
		if(bag == null) throw new NoBagException();
		int remaining = bag.getCapacity() - bag.getWeight();
		if(bag.getWeight() < bag.getCapacity()){
			if(item.getWeight() <= remaining){
				this.bag.push(item);
				System.out.println(this.getName() + " picks up " + item);
			}
		}
	}

	public Collectible pullOut(Collectible item) throws NoBagException{
		if(bag == null) throw new NoBagException();
		if(bag.contains(item)){
			System.out.println(this.getName() + " pulls out " + item.toString());
			return this.bag.pop(item);
		}
		return null;
	}

	public void printBag(){
		System.out.println("BAG : " + this.bag);
	}

	public int getBagCapacity() throws NoBagException {
		if(bag == null) throw new NoBagException();
		return this.bag.getCapacity();
	}

	public int getBagWeight() throws NoBagException {
		if(bag == null) throw new NoBagException();
		return this.bag.getWeight();
	}

	public Collectible[] getBagItems() throws NoBagException {
		if(bag == null) throw new NoBagException();
		return this.bag.getItems();
	}

	public Bag setBag(Bag bag) throws BagFullException {
		String oldBagName, newBagName ;
		String nullBagName = "null" ;

		try{
			oldBagName = this.bag.getClass().getSimpleName();
		} catch (NullPointerException e){
			oldBagName = nullBagName;
		}

		try{
			newBagName = bag.getClass().getSimpleName();
		} catch (NullPointerException e){
			newBagName = nullBagName;
		}

		System.out.println(name + " changes " + oldBagName + " for " + newBagName);

		Bag tmp = this.bag;
		Bag.transfer(this.bag, bag);
		this.bag = bag;
		return tmp;
	}

	public void equip(Weapon weapon) throws NoBagException, WeaponNullException {
		if(bag == null) throw new NoBagException();
		if(weapon == null) throw new WeaponNullException();
		if(this.bag.contains(weapon)){
			this.setWeapon((Weapon)this.pullOut(weapon));
			System.out.println("\t   and equips it !");
		}
	}

	public void equip(Consumable consumable) throws NoBagException, ConsumeNullException {
		if(bag == null) throw new NoBagException();
		if(consumable == null) throw new ConsumeNullException(consumable);
		if(this.bag.contains(consumable)){
			this.setConsumable((Consumable)this.pullOut(consumable));
			System.out.println("\t   and equips it !");
		}
	}

	private Consumable fastUseFirst(Class<? extends Consumable> type) throws ConsumeNullException, ConsumeEmptyException, ConsumeRepairNullWeaponException, NoBagException{
		if(type == null) return null;
		Collectible[] tab = this.bag.getItems();
		for(Collectible t: tab){
			if(type.isInstance(t)){
				this.use((Consumable)t);
				Consumable tmp = (Consumable) t;
				if(tmp.getCapacity() == 0){
					this.pullOut(t);
				}
				return (Consumable)t;
			}
		}
		return null;
	}

	public Drink fastDrink() throws ConsumeNullException, ConsumeEmptyException, NoBagException{
		System.out.println(getName() + " drinks FAST : ");
		Drink drink = null;
		try {
			drink = (Drink) fastUseFirst(Drink.class);
		} catch(ConsumeRepairNullWeaponException rnw){
			rnw.printStackTrace();
		}
		return drink;
	}

	public Food fastEat() throws ConsumeNullException, ConsumeEmptyException, ConsumeRepairNullWeaponException, NoBagException{
		System.out.println(getName() + " eats FAST : ");
		Food food = null;
		try {
			food = (Food)fastUseFirst(Food.class);
		} catch (ConsumeRepairNullWeaponException rnw){
			rnw.printStackTrace();
		}
		return food;
	}

	public RepairKit fastRepair() throws ConsumeNullException, ConsumeEmptyException, ConsumeRepairNullWeaponException, NoBagException{
		System.out.println(getName() + " repairs FAST : ");
		return (RepairKit)fastUseFirst(RepairKit.class);
	}

	public void printWeapon(){
		System.out.println(String.format("WEAPON : " + getWeapon()));
	}

	public void printConsumable(){
		System.out.println(String.format("CONSUMABLE : %-20s", getConsumable()));
	}

}
