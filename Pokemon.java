/**
 * @(#)Pokemon.java
 * Pokemon Project
 *
 * @Tailai Wang
 * @version 1.00 2017/11/29
 */
//This is the Pokemon class, where we control everything that a Pokemon is. 
import java.util.*;
import java.io.*;
public class Pokemon {
	public static final String[] types = {"Earth","Fire","Grass","Water","Fighting","Electric"};
	
    private String name;
    private Boolean stun = false;
    private Boolean disable = false;
    private int hp;
    private int maxHp;
    private int energy = 50;
    public String type;
    private String resistance;
    private String weakness;
    private int numAttacks;
    public ArrayList<Move>moves;
    
    public Pokemon(String line){ //constructor
    	String[]statsArray = line.split(","); //splits the line based on commas
    	name = statsArray[0]; //handing the appropriate values to the appropriate variables
    	hp = Integer.parseInt(statsArray[1]);
    	maxHp = hp;
    	type = statsArray[2];
    	resistance = statsArray[3];
    	weakness = statsArray[4];
    	numAttacks = Integer.parseInt(statsArray[5]);
    	
    	moves = new ArrayList<Move>(); //starting an arrayList of Moves 
    	for (int i = 0; i < numAttacks; i++){
    		Move temp = new Move(statsArray, i*4); //adding the move to the arraylist
    		moves.add(temp);
    	}//end for	
    }//end constructor
    
   //GETTERS AND SETTERS BELOW HERE
    public String getName(){
    	return name;
    }//end getName
    
    public String getType(){
    	return type;
    }//end getType
    
    public int getHealth(){
    	return hp;
    }//end getHealth	
    
    public int getMaxHealth(){
    	return maxHp;
    }//end getMaxHealth
    public void setHealth(int x){
    	hp = x;
    }//end setHealth
    
    public int getEnergy(){
    	return energy;
    }//end getEnergy
    
    public void setEnergy(int x){
    	energy = x;
    }//end setEnergy
    
    public String getResistance(){
    	return resistance;
    }//end getResistance
    
    public String getWeakness(){
    	return weakness;
    }//end getWeakness
    public String[] getMove(int index){
    	return (moves.get(index)).getAttack();
    }//end getMove
    
    public String getMoveName(int index){
    	return (moves.get(index)).getAttackName();
    }//end getMoveName
    
    public int getMoveCost(int index){
    	return (moves.get(index)).getAttackCost();
    }//end getMoveCost
    
    public int getNumAttacks(){
    	return numAttacks;
    }//end getNumAttacks
    
    public void setStun(Boolean x){
    	stun = x;
    }//end setStun
    
    public void setDisable(){
    	disable = true;
    }//end setDisable
    
    public Boolean getStun(){
    	return stun;
    }//end getStun
    
    public Boolean getDisable(){
    	return disable;
    }//end getDisable
}//end Pokemon