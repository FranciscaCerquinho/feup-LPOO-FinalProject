package dkeep.logic;
import dkeep.logic.Entity;
import dkeep.logic.Game.Direction;
import java.util.Random;

/**
 * 
 * Ogre.java - A subclass of Entity
 *
 */
public class Ogre extends Entity{

	private char symbol;
	private int[] pos_club = {1,1};
	private boolean key=false;
	private int nrOfMoves=-1;

	/**
	 * Constructor of this class and initialize the symbol that represents the Ogre
	 * @param posx represents the coordinate x of the position of the Ogre
	 * @param posy represents the coordinate y of the position of the Ogre
	 *
	 */
	public Ogre(int posx, int posy) {
		super(posx, posy);
		symbol = 'O';
	}
	/*
	public Ogre getOgre(){
		return this;
	}
	 */
	/**
	 * @return symbol that represents the Ogre
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

	/**
	 * @return key
	 */
	public boolean getKey(){
		return key;
	}

	/**
	 * Method that sets the key
	 * @param b
	 */
	public void setKey(boolean b){
		key=b;
	}
	/*
	public int getNrOfMoves(){
		return nrOfMoves;
	}

	public void setNrOfMoves(int nr){
		nrOfMoves=nr;
	}
	 */
	/**
	 * Method responsible to move the ogres and stun them (they stop moving for two turns)
	 * @param direction
	 * @param board
	 * @param hero
	 */
	public void movement(Direction direction, Board b, Hero hero){

		int pos_rand = boardLimits(b);
		boolean invalid=false;

		if(nrOfMoves ==2){
			symbol='O';
			nrOfMoves=-1;
		}
		if(nrOfMoves >-1)
			nrOfMoves++;

		if(!checkIfMovementIsValid(direction,b))
			invalid=true;

		if(verifyI(direction, b))
			invalid=true;

		if(ogreNextToTheHero(direction,b,hero)){
			symbol='8';
			nrOfMoves++;
		}

		if(nrOfMoves == -1){
			if(!invalid){

				switch(pos_rand) {
				case 0:
					posy++;
					break;
				case 1:
					posy--;
					break;
				case 2:
					posx--;
					break;
				case 3:
					posx++;
					break;

				}
			}
		}
	}

	/**
	 * Method that checks if the ogre is next to the hero
	 * @param direction
	 * @param board
	 * @param hero
	 * @return true if the ogre is next to the hero
	 */
	public boolean ogreNextToTheHero(Direction direction, Board b, Hero hero){

		if(hero.getSymbol()=='K'){
			if( (posx-1 == hero.getPosx() && posy == hero.getPosy()) || 
					(posx+1 == hero.getPosx() && posy == hero.getPosy()) ||
					(posx == hero.getPosx() && posy+1 == hero.getPosy()) ||
					(posx == hero.getPosx() && posy-1== hero.getPosy()))
				return true;
			else
				return false;
		}
		else
			return false;
	}

	/**
	 * Method that verifies if the hero is next to the door
	 * @param direction
	 * @param board
	 * @return true if the hero is next to the door
	 */
	public boolean verifyI(Direction direction, Board b){

		switch(direction) {
		case UP:{
			if(b.getBoard()[posx-1][posy] == 'I')
				return true;
			else
				return false;
		}
		case DOWN:{
			if(b.getBoard()[posx+1][posy] == 'I')
				return true;
			else
				return false;
		}
		case RIGHT:{
			if(b.getBoard()[posx][posy+1] == 'I')
				return true;
			else
				return false;
		}
		case LEFT:{
			if(b.getBoard()[posx][posy-1] == 'I')
				return true;
			else
				return false;
		}
		}
		return true;

	}
	
	public boolean checkIfClubHasCorrectedPosition(Board b, int x, int y){
		if (b.getBoard()[x][y] == 'X' ||  b.getBoard()[x][y] == 'I')
			return false;
		else if(b.getBoard()[x][y] == 'k')
		{
			pos_club[0]=x;
			pos_club[1]=y;
			return true;
		}
		else if(b.getBoard()[x][y] != 'A' && b.getBoard()[x][y] != ' ')
			return false;
		else{
			b.getBoard()[x][y]= '*';
			pos_club[0]=x;
			pos_club[1]=y;
			return true;
		}
	}

	/**
	 * Method responsible to move club
	 * @param board
	 */
	public void club(Board b){

		while(true){
			int pos_rand;
			Random rand = new Random();
			pos_rand = rand.nextInt(4);
			switch(pos_rand) {
			case 0:{
				if(checkIfClubHasCorrectedPosition(b, posx-1, posy))
					return;
				else
					break;
			}
			case 1:{
				if(checkIfClubHasCorrectedPosition(b, posx, posy+1))
					return;
				else
					break;
			}
			case 2:{
				if(checkIfClubHasCorrectedPosition(b, posx+1, posy))
					return;
				else
					break;
			}
			case 3:{
				if(checkIfClubHasCorrectedPosition(b, posx, posy-1))
					return;
				else
					break;
			}
			}
		}
	}

	/**
	 * @param direction
	 * @param board
	 */
	public void movement(Direction direction, Board b) {

	}


}

