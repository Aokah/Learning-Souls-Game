package lsg.characters;

import lsg.armor.ArmorItem;
import lsg.armor.BlackWitchVeil;
import lsg.armor.RingedKnightArmor;
import lsg.buffs.rings.Ring;
import lsg.consumables.repair.RepairKit;
import lsg.exceptions.NoBagException;

public class Hero extends Character{
	
	private static String DEFAULT_NAME = "Aokha" ; // Nom par défaut
	private static int DEFAULT_MAX_LIFE = 100 ; // Nombre de points de vie par défaut
	private static int DEFAULT_MAX_STAMINA = 50 ; // Nombre de points d'action par défaut
	
	private static int MAX_ARMOR_PIECES = 3 ; // nombre max de pièces d'armures
	
	private static int MAX_RINGS = 2 ; // nombre max d'anneaux
	
	private ArmorItem[] armor ; // l'ensemble des pieces d'armure portees par le hero
	
	private Ring[] rings ;

	private int stamRegen = 10;
	private int lifeRegen = 20;

	public Hero(String name){
		super(name) ;
		setMaxLife(DEFAULT_MAX_LIFE);
		setLife(DEFAULT_MAX_LIFE) ;
		setMaxStamina(DEFAULT_MAX_STAMINA);
		setStamina(DEFAULT_MAX_STAMINA) ;
		
		armor = new ArmorItem[MAX_ARMOR_PIECES] ;
		rings = new Ring[MAX_RINGS] ;
	}
	
	public Hero(){
		this(DEFAULT_NAME) ;
	}

	public void setStamRegen(int stamRegen) {
		this.stamRegen = stamRegen;
	}

	public int getStamRegen() {
		return stamRegen;
	}

	public int getLifeRegen(){
		return lifeRegen;
	}

	public void setLifeRegen(int lifeRegen){
		this.lifeRegen = lifeRegen;
	}

	/**
	 * Place une piece d'armure
	 * @param item : la piece d'amure 
	 * @param slot : slot a utiliser (NB: si une piece était en place, elle est remplacee)
	 */
	public void setArmorItem(ArmorItem item, int slot){
		int index = slot-1 ;
		if(index >=0 && index < MAX_ARMOR_PIECES){
			armor[index] = item ;
		}
	}
	
	/**
	 * @return un tableau contenant les pièces d'armure effectivement portées
	 */
	public ArmorItem[] getArmorItems() {
		
		// Comptage du nombre de pièces portées (NB: on pourrait être plus efficace avec une Collection... cf. plus tard)
		int count = 0 ;
		for (ArmorItem item: armor){
			if(item != null) count++ ;
		}
		
		// Création et remplissage du tableau résultat
		ArmorItem[] items = new ArmorItem[count] ;
		int i = 0 ;
		for(ArmorItem item: armor) {
			if(item != null){
				items[i] = item ;
				i++ ;
			}
		}
		
		return items ;
	}
	
	/**
	 * Place un anneau
	 * @param ring
	 * @param slot : slot a utiliser (NB: si un anneau était en place, il est remplacee)
	 */
	public void setRing(Ring ring, int slot){
		int index = slot-1 ;
		if(index >=0 && index < MAX_RINGS){
			rings[index] = ring ;
			ring.setHero(this);
		}
	}
	
	public Ring[] getRings() {
		
		// Comptage du nombre d'anneaux (NB: on pourrait être plus efficace avec une Collection... cf. plus tard)
		int count = 0 ;
		for (Ring ring: rings){
			if(ring != null) count++ ;
		}
		
		// Création et remplissage du tableau résultat
		Ring[] result = new Ring[count] ;
		int i = 0 ;
		for(Ring r: rings) {
			if(r != null){
				result[i] = r ;
				i++ ;
			}
		}
		
		return result ;
	}

	public void printRings(Ring[] rings){
		String msg = "RINGS " ;
		String item ;
		for(int i=0 ; i<MAX_RINGS ; i++){
			item = (rings[i] !=null) ? rings[i].toString() : "empty" ;
			msg = String.format("%s %2d:%-30s", msg, i+1, item) ;
		}
		System.out.println(msg);
	}
	
	/**
	 * @return la valeur totale de l'armure du hero
	 */
	public float getTotalArmor(){
		float total = 0 ;
		for(ArmorItem i : armor){
			total = (i != null) ? total + i.getArmorValue() : total ;
		}
		return total ;
	}
		
	/**
	 * @return une representation de l'armure portee par le hero
	 */
	public String armorToString(){
		String msg = "ARMOR " ;
		String item ;
		for(int i=0 ; i<MAX_ARMOR_PIECES ; i++){
			item = (armor[i] !=null) ? armor[i].toString() : "empty" ;
			msg = String.format("%s %2d:%-30s", msg, i+1, item) ; 
		}
		return msg + "TOTAL:" + getTotalArmor() ;
	}

	@Override
	protected float computeProtection() {
		return getTotalArmor() ;
	}
	
	@Override
	protected float computeBuff() {
		float total = 0 ;
		for(Ring r : rings){
			total = (r != null) ? total + r.computeBuffValue() : total ;
		}
		return total ;
	}

	public void equip(ArmorItem item, int slot) throws NoBagException {
		if(bag == null) throw new NoBagException();
		if(this.bag.contains(item)){
			this.setArmorItem((ArmorItem)this.pullOut(item), slot);
			System.out.println("\t   and equips it !");
		}
	}

	public void equip(Ring ring, int slot) throws NoBagException {
		if(bag == null) throw new NoBagException();
		if(this.bag.contains(ring)){
			this.setRing((Ring)this.pullOut(ring), slot);
			System.out.println("\t   and equips it !");
		}
	}

	public void recuperate(){
		if(stamRegen + getStamina() > getMaxStamina()){
			setStamina(getMaxStamina());
		} else {
			setStamina(getStamina() + stamRegen);
		}

		if(lifeRegen + getLife() > getMaxLife()){
			setLife(getMaxLife());
		} else {
			setLife(getLife() + lifeRegen);
		}
	}
}
