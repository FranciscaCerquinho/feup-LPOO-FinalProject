package dkeep.logic;


import dkeep.logic.Game.Direction;
/**
 * 
 * Hero.java - A subclass of Entity
 *
 */
public class Hero extends Entity{

	private char symbol;
	/**
	 * Constructor of this class and initialize the symbol that represents the hero
	 * @param posx represents the coordinate x of the position of the Hero
	 * @param posy represents the coordinate y of the position of the Hero
	 *
	 */
	public Hero(int posx, int posy) {
		super(posx, posy);
		symbol = 'H';
	}

	/**
	 * @return symbol that represents the hero
	 */
	public char getSymbol(){
		return symbol;
	}

	/**
	 * Sets the symbol of the hero
	 * @param symbol
	 */
	public void setSymbol(char symbol){
		this.symbol=symbol;
	}
	
	public void moveHero(Direction direction){
		switch(direction) { case UP:
			posx--;
		break;
		case DOWN:
			posx++;
		break;
		case RIGHT:
			posy++;
		break;
		case LEFT:
			posy--;
		break;}
	}

	/**
	 * Method responsible to move the hero
	 * @param direction that specifies the direction of the hero
	 * @param board
	 */
	public void movement(Direction direction, Board b){
		boolean invalid=false;
		if(!checkIfMovementIsValid(direction,b))
			invalid=true;
		if(!invalid){
			moveHero(direction); } }
}