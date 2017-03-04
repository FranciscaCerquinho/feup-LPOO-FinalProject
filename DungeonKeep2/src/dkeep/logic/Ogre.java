package dkeep.logic;
import dkeep.logic.Entity;
import dkeep.cli.UserInteraction;

import java.util.Random;
import java.util.Vector;


public class Ogre extends Entity{

	private int posx, posy;

	public enum Direction{
		RIGHT,LEFT,UP,DOWN
	}

	private Direction directions[] = {Direction.RIGHT, Direction.LEFT, Direction.UP, Direction.DOWN};

	public Ogre(int level)
	{
		posx = 1;
		posy = 4;

	}
	public Ogre getOgre(){
		return this;
	}

	public int getPosy(){
		return posy;
	}

	public int getPosx(){
		return posx;
	}

	public void setPosx(int posx){
		this.posx = posx;
	}

	public void setPosy(int posy){
		this.posy = posy;
	}

	public void movement(){

		int pos_rand;
		Random rand = new Random();
		pos_rand = rand.nextInt(4);

		Direction direction = directions[pos_rand];

		switch(direction) {
		case UP:
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
			break;
		}

	}
}

