  /**
 * @(#)PokemonArena.java
 * Pokemon Project
 *
 * @Tailai Wang
 * @version 1.00 2017/11/30
 */

import java.util.*;
import java.io.*;

public class PokemonArena {
	private static ArrayList<Pokemon>pokemans;
	private static ArrayList<Pokemon>playerPokemans;
	private static ArrayList<Pokemon>enemyPokemans;
	private static String onField = ""; //name of player pokemon on field
 	private static int index = -1; //index in playerPokemans of player's pokemon on field
 	private static Boolean bigSkip = false;
 	private static Boolean bigEnemySkip = false;
 	
  	public static void main(String[]args) {
    	pokemans = new ArrayList<Pokemon>(); //full list of pokemon
    	playerPokemans = new ArrayList<Pokemon>(); //player's pokemon
    	enemyPokemans = new ArrayList<Pokemon>(); //all other pokemon
    	
    	welcomeMessage();
    	readFile();
    	pokemonSelection();
    	enemyCreator();
    	
    	Collections.shuffle(enemyPokemans); //randomizing the enemyPokemans
    	
    	int round = 1; 
    	
    	if (firstTurn() == 1){ //MAKE THIS WORK
    		System.out.println("Player Starts");
    		while (validGame()){
    			if (round == 1){
    				choosePokes();
    			}//end if
    			chooseEnemy();
    			playerMove();
    			calculateDamage();
    			if (validGame() && bigEnemySkip==false){
    				enemyMove();
    				calculateDamage();
    			}//end if
    			bigEnemySkip = false;
    			addEnergy();
    			round++;
    		}//end while
    	}//end if
    	else{ //MAKE THIS WORK TOO
    		System.out.println("Enemy Starts");
    		while (validGame()){
    			if (round == 1){
    				choosePokes();
    			}//end if
    			chooseEnemy();
    			enemyMove();
    			calculateDamage();
    			if (validGame() && bigSkip ==false){
    				playerMove();
    				calculateDamage();
    			}//end if
    			bigSkip = false;
    			addEnergy();
    			round++;
    			
    		}//end while
    	}//end else
    	
   		checkWinner(); //validGame doesn't tell you the result, it only tells you if the game is over
    
   }//end main
   public static void welcomeMessage(){
   		
   		try{
   			Scanner inFile = new Scanner((new BufferedReader(new FileReader("junk.txt"))));
   			while (inFile.hasNextLine()){ //printing ASCII stuff
   				System.out.println(inFile.nextLine());
   			}//end while
   		}//end try
   		
   		catch(IOException ex){
   			;
   		}
   		System.out.print("\n");	 
   		System.out.print("Hello Trainer! Welcome to the Pulse Pokemon Arena! \n"+
   			"Your mission is to bring your team of 4 Pokemon and face off against\n"+
   			"a MASSIVE lineup of enemy Pokemon. Will you become Trainer Supreme?\n"+
   			"or will you just be another loser...? Good Luck! \n");
   		System.out.println("Press Enter to Continue");
   		Scanner kb = new Scanner (System.in);
   		String nothing = kb.nextLine();
   		printDivider();
   }
   
   public static void printDivider(){
   		System.out.println("================================================");
   }
   public static void readFile(){
   		String fileName = "pokemon.txt"; //reading the data file
    	Scanner kb = new Scanner(System.in);
    	
    	try{
    		Scanner inFile = new Scanner(new File(fileName));
    		int n = inFile.nextInt();
    		inFile.nextLine(); //dummy nextLine
    		for (int i = 0; i<n ; i++){
    			Pokemon temp = new Pokemon(inFile.nextLine());
    			pokemans.add(temp); 
    		}//end for
    		
    	}//end try
    	catch (IOException ex){
    		System.out.println("Dude, did you misplace pokemon.txt?");
    		
    	}//end catch
   }
   
   public static void pokemonSelection(){
   		Scanner kb = new Scanner(System.in);
   		printPokemonInfo();
   		while (true){
   			printDivider();
    		int choice = 0;
    		if(playerPokemans.size() < 4){ //safety net
    			System.out.println("Enter Pokemon #"+ (playerPokemans.size()+1));
    			choice = kb.nextInt();
    		}//end if
    		else{
    			break;
    		}//end else
    		if (choice < pokemans.size() && choice >= 0 && !(playerPokemans.contains(pokemans.get(choice)))){ //safety net
    			playerPokemans.add(pokemans.get(choice));
    			System.out.println("You selected "+pokemans.get(choice).getName()+
    				"| Type: "+pokemans.get(choice).getType().toUpperCase());
    		}//end if
    		else{
    			System.out.println("Invalid Input, Try Again");
    		}//end else
    	}//end while
   }//end pokemonSelection
   
   public static void enemyCreator(){
   		for (int i = 0; i < pokemans.size(); i++){ //adds everything that isn't in playerPokemans
    		if (!(playerPokemans.contains(pokemans.get(i)))){
    			enemyPokemans.add(pokemans.get(i));
    		}//end if
    		
    	}//end for
    	printDivider();
   }//end enemyCreator
   
   public static void printPlayerInfo(){
   		for (int i = 0; i < playerPokemans.size() ; i++){ //prints to show all remaining playerPokemans
   			Pokemon temp = playerPokemans.get(i);
   			System.out.println(i+". "+temp.getName()+"| Type: "+temp.getType().toUpperCase()+
   				"| Health: "+ temp.getHealth()+"| Energy: "+temp.getEnergy());
   		}//end for
   }//end printPlayerInfo
   
   public static void printPokemonInfo(){ //prints all pokemon for pokemonSelection
   		for (int i = 0; i < pokemans.size() ; i++){
   			Pokemon temp = pokemans.get(i);
   			System.out.println(i+". "+temp.getName()+"| Type: "+temp.getType().toUpperCase()+
   				"| Health: "+ temp.getHealth()+"| Energy: "+temp.getEnergy());
   		}//end for
   }
   
   public static int firstTurn(){ //random selection of who starts
   		Random rand = new Random();
   		int n = rand.nextInt(2)+1;
   		return n; //returns either 1 or 2
   }//end firstTurn
   
   public static Boolean validGame(){
   		if (playerPokemans.size() == 0){ //if all playerPokemans have fainted
   			return false;
   		}//end if
   		if (enemyPokemans.size() == 0){ //if all enemyPokemans have fainted
   			return false;
   		}//end if
   		return true;
   }//end validGame
   
   public static void choosePokes(){
   		//trainer selecting which pokemon to use for this. 
   		System.out.println("You have "+playerPokemans.size()+" Pokemon left");
   		printPlayerInfo();
   		System.out.println("Which Pokemon would you like to use?");
   		
   		while (true){
   			Scanner kb = new Scanner(System.in);
   			int n = kb.nextInt();
   			if (n>=0 && n < playerPokemans.size()){ //safety net
   				onField = playerPokemans.get(n).getName();
   				index = n;
   				System.out.println(onField+", I choose you!");
   				break;
   			}//end if
   			else{
   				System.out.println("Invalid Input");
   			}//end else
   			
   			printDivider();
   		}//end while
   		
   }//end choosePokes
   
   public static void chooseEnemy(){
   		System.out.println("You are battling "+enemyPokemans.get(0).getName()+
   			"| Type: "+enemyPokemans.get(0).getType().toUpperCase()); 
   		printDivider();
   }//end chooseEnemy
   
   public static void playerMove(){
   		Scanner kb = new Scanner(System.in);
   		System.out.println(onField+" is currently on the field. What will Trainer do?");
   		System.out.println("1: Attack");
   		System.out.println("2: Retreat/Switch");
   		System.out.println("3: Pass");
   		int choice = 0;
   		while (true){
   			choice = kb.nextInt();
   			if (choice == 1 && playerPokemans.get(index).getStun()==true){ //safety net
   				System.out.println("Invalid, this pokemon is stunned");
   			}//end if
   			else if (choice > 0 && choice <=3){ //safety net
   				break;
   			}//end else if 
   			else{ 
   				System.out.println("Invalid Input.");
   			}//end else
   		}//end while
   		printDivider();
   		if (choice == 1){
   			choseAttack();
   		}//end if
   		else if (choice == 2){
   			choseSwitch();
   		}//end else if
   		else{
   			chosePass();
   		}//end else
   		
   }//end playerMoves
   
   public static void choseAttack(){
   		Scanner kb = new Scanner (System.in);
   		for (int i = 0; i < playerPokemans.size(); i++){
   			if(playerPokemans.get(i).getName() == onField){
   				index = i; //index in array of pokemon that is onField
   			}//end if
   		}//end for
   		System.out.println("Trainer wants to Attack! Your options are:");
   		
   		for (int i = 0; i < playerPokemans.get(index).getNumAttacks(); i++){ //listing the attacks
   			String []temp = playerPokemans.get(index).getMove(i);
   			String special;
   			if (temp[3].equals(" ")){
   				special = ("No special");
   			}//end if
   			else{
   				special = temp[3];
   			}//end else
   			System.out.println(i+": "+temp[0]+"| Special: "+special);
   		}//end for
   		System.out.println(playerPokemans.get(index).getName()+" has "+playerPokemans.get(index).getEnergy()+" energy left.");
   		while (true){
   			int attackChoice = kb.nextInt();
   			if (attackChoice < 0 || attackChoice > playerPokemans.get(index).getNumAttacks()){ //safety net
   				System.out.println("Invalid Input");
   			}
   			else if (playerPokemans.get(index).getEnergy() < playerPokemans.get(index).getMoveCost(attackChoice)){ //if there's not enough energy
   				System.out.println("Not enough energy.");
   				printDivider();
   				playerMove();
   			}//end if
   			else{
   				System.out.println(playerPokemans.get(index).getName()+" used "
   					+playerPokemans.get(index).getMoveName(attackChoice)+"!");
   				int temp = playerPokemans.get(index).getEnergy(); //used to subract energy
   				playerPokemans.get(index).setEnergy(temp - playerPokemans.get(index).getMoveCost(attackChoice));
   				useAttack(playerPokemans.get(index).getMove(attackChoice),"enemy"); //using the attack
   				break;
   			}//end else
   		}//end while
   		printDivider();	
   }//end choseAttack
   
   public static void useAttack(String[]move,String target){
   		Boolean wildCard = false; //flags that indicate to the program that we need to skip the else at the bottom
	   	Boolean wildStorm = false;
	   	Boolean skip = false; 
	   	Boolean enemyWildCard = false; 
   		Boolean enemyWildStorm = false;
   		Boolean enemySkip = false;
   		if (target == "enemy" && playerPokemans.get(index).getStun() == false){
	   		String special = move[3];
	   		if (!(special.equals(" "))){ //if there is a special
	   			Random rand = new Random();
	   			int rando = rand.nextInt(2)+0; //random number either 1 or 0
	   			if (rando == 1 && special.equals("stun")){ //stuns target
	   				System.out.println(playerPokemans.get(index).getName()+" landed a stun!");
	   				enemyPokemans.get(0).setStun(true);
	   			}//end if
	   			else if (rando == 1 && special.equals("wild card")){ //attack fails
	   				System.out.println("Wild Card! Attack Failed");
	   				wildCard = true;
	   			}//end if
	   			else if (rando == 1 && special.equals("wild storm")){ //wild storm can go on forever
	   				wildStorm = true;
	   				skip = true;
	   				rawPlayerAttack(move);
	   				while (wildStorm){ 
	   					Random randInt = new Random();
	   					int nextRand = randInt.nextInt(2)+0;
	   					if(nextRand == 1){
	   						System.out.println("Wild Storm!");
	   						rawPlayerAttack(move); //keeps sending raw attacks
	   					}//end if
	   					else{
	   						System.out.println("Wild Storm Over!");
	   						wildStorm = false;
	   					}//end else
	   				}//end while wildStorm
	   			}//end else if
	   			
	   			else if (rando == 1 && special.equals("disable")){
	   				System.out.println(playerPokemans.get(index).getName()+" landed a DISABLE!");
	   				enemyPokemans.get(0).setDisable();
	   			}//end else if 
	   			
	   			else if (rando == 1 && special.equals("recharge")){
	   				System.out.println(playerPokemans.get(index).getName()+ "Got an Energy Recharge!");
	   				int currEnergy = playerPokemans.get(index).getEnergy();
	   				if (currEnergy < 30){
	   					playerPokemans.get(index).setEnergy(currEnergy+20);
	   				}//end if
	   				else{
	   					playerPokemans.get(index).setEnergy(50);
	   				}//end else1
	   			}//end else if
	   		}//end specials
   		}//end if
   		
   		else if (target == "player" && enemyPokemans.get(0).getStun() == false){
   			String special = move[3];
	   		if (!(special.equals(" "))){ //if there is a special
	   			Random rand = new Random();
	   			int rando = rand.nextInt(2)+0; //random number either 1 or 0
	   			if (rando == 1 && special.equals("stun")){ //stuns target
	   				System.out.println(enemyPokemans.get(0).getName()+" landed a stun!");
	   				playerPokemans.get(index).setStun(true); //changes the variable
	   			}//end if
	   			else if (rando == 1 && special.equals("wild card")){ //50/50 chance that the attack fails
	   				System.out.println("Wild Card! Attack Failed");
	   				enemyWildCard = true;
	   			}//end if
	   			else if (rando == 1 && special.equals("wild storm")){
	   				enemyWildStorm = true;
	   				enemySkip = true; //passes over the else at the bottom
	   				rawEnemyAttack(move);
	   				while (enemyWildStorm){
	   					Random randInt = new Random();
	   					int nextRand = randInt.nextInt(2)+0;
	   					if(nextRand ==1){ //wild storm can go on forever
	   						System.out.println("Enemy Wild Storm!");
	   						rawEnemyAttack(move); //keeps attacking
	   					}//end if
	   					else{
	   						System.out.println("Enemy Wild Storm Over!");
	   						enemyWildStorm = false;
	   					}//end else
	   				}//end while wildStorm
	   			}//end else if
	   			
	   			else if (rando == 1 && special.equals("disable")){
	   				System.out.println(enemyPokemans.get(0).getName()+" landed a DISABLE!");
	   				playerPokemans.get(index).setDisable(); //disable is a flag in the Pokemon file
	   			}//end else if 
	   			
	   			else if (rando == 1 && special.equals("recharge")){
	   				int currEnergy = enemyPokemans.get(0).getEnergy();
	   				System.out.println(enemyPokemans.get(0).getName()+ "Got an Energy Recharge!");
	   				if (currEnergy < 30){ //ensuring that the energy doesn't pass the max energy
	   					enemyPokemans.get(0).setEnergy(currEnergy+20);
	   				}//end if
	   				else{
	   					enemyPokemans.get(0).setEnergy(50);
	   				}//end else
	   			}//end else if
	   		}//end specials
   		}
   		if (target == "enemy" && wildCard == false && skip == false){
   			rawPlayerAttack(move); //just the raw attack, no specials
   		}//end enemy target
   		else if (target == "player" && enemyWildCard == false && enemySkip == false){
   			rawEnemyAttack(move); //just the raw attack, no specials
   		}// end Player target
   		wildCard = false; //resetting flags
   		skip = false;
   		enemyWildCard = false;
   		enemySkip = false;
   		printDivider();
   }//end useAttack
   
   public static void calculateDamage(){
   		if (playerPokemans.get(index).getHealth() <= 0){ //if the pokemon has fainted
   			System.out.println(playerPokemans.get(index).getName()+" has fainted! Who will replace them?");
   			playerPokemans.remove(index); //removing the fainted pokemon from the list
   			for (int i = 0; i < playerPokemans.size(); i++){ //listing options
   				System.out.println(i+". "+playerPokemans.get(i).getName());
   			}//end for
   			bigSkip = true;
   			if (validGame()){
   				while (true){
   					Scanner kb = new Scanner(System.in);
   					System.out.println("Enter your choice:");
   					int temp = kb.nextInt();
   					if (temp >= 0 && temp < playerPokemans.size()){ //safety net
   						index = temp;
   						onField = playerPokemans.get(index).getName();
   						System.out.println("Trainer Selected "+onField+" |Type :" +playerPokemans.get(index).getType().toUpperCase());
   						break;
   					}//end if
   					else{
   						System.out.println("Invalid Input");
   					}//end else
   				
   				}//end while
   			}
   			
   			
   		}//end if
   		
   		else{
   			System.out.println(playerPokemans.get(index).getName()+" has "+playerPokemans.get(index).getHealth()+" health left");
   		}//end else
   		
   		if (enemyPokemans.get(0).getHealth() <= 0){ //if enemy faints
   			bigEnemySkip = true;
   			System.out.println(enemyPokemans.get(0).getName()+" has fainted! Your Pokemon receive a health bonus!");
   			enemyPokemans.remove(0); //removes enemy from list, next in line becomes element 0
   			if (validGame()){
   				System.out.println(enemyPokemans.get(0).getName()+ " is entering the field!");
   			}
   			for (int i = 0; i < playerPokemans.size(); i++){
   				int currHealth = playerPokemans.get(i).getHealth(); //to ensure that health doesn't exceed limit
   				playerPokemans.get(i).setEnergy(50); //playerPokemans all get full energy
   				if(currHealth > playerPokemans.get(i).getMaxHealth()-20){
   					playerPokemans.get(i).setHealth(playerPokemans.get(i).getMaxHealth()); //sets to maxhealth
   				}//end if
   				else{
   					playerPokemans.get(i).setHealth(currHealth+20); //if below maxhealth
   				}//end else
   			}//end for
   		}//end if
   		
   		else{
   			System.out.println(enemyPokemans.get(0).getName()+" has "+enemyPokemans.get(0).getHealth()+" health left");
   		}//end else
   		printDivider();
   }//checkHealth
   
   public static void choseSwitch(){
   		Scanner kb = new Scanner(System.in);
   		System.out.println("Who would you like to switch in?");
   		printPlayerInfo(); //display
   		int switched = 0; //switch selection
   		while (true){
   			switched = kb.nextInt();
   			if (switched >= 0 && switched < playerPokemans.size()){ //safety net
   				break;
   			}//end if
   			else{
   				System.out.println("Invalid Input.");
   			}//end else
   		}//end while true
   		onField = playerPokemans.get(switched).getName();
   		index = switched; //switching the index of the trainer pokemon that's onField
   		printDivider();
   }//end choseSwitch
   
   public static void chosePass(){
   		System.out.println("Trainer Passed!");
   		playerPokemans.get(index).setStun(false);
   		printDivider();
   }//end pass   	
   
   public static void enemyPass(){
   		System.out.println(enemyPokemans.get(0).getName()+" Passed!");
   		enemyPokemans.get(0).setStun(false);
   		printDivider();
   }//end enemyPass
   
   public static void enemyMove(){
   		int enemyEnergy = enemyPokemans.get(0).getEnergy(); //current energy
   		int AttacksIndex = enemyPokemans.get(0).getNumAttacks(); //number of attacks
   		Random rand = new Random();
   		int n = rand.nextInt(AttacksIndex)+0; //random selection of moves
   		if (enemyPokemans.get(0).getMoveCost(n) > enemyEnergy){ //if the move is too expensive
   			if (enemyPokemans.get(0).getNumAttacks() > 1){ //if there is an alternative to the move
   				if (enemyPokemans.get(0).getMoveCost(0) <= enemyEnergy){ //if the enemy can use the alternative
   					System.out.println(enemyPokemans.get(0).getName()+" used "+enemyPokemans.get(0).getMoveName(0)+"!");
   					useAttack(enemyPokemans.get(0).getMove(0),"player"); 
   				}//end if
   				else if (enemyPokemans.get(0).getMoveCost(1) <= enemyEnergy){ //if the other attack can be used
   					System.out.println(enemyPokemans.get(0).getName()+" used "+enemyPokemans.get(0).getMoveName(1)+"!");
   					useAttack(enemyPokemans.get(0).getMove(1),"player");
   				}//end else if
   				else{ //pass due to not enough energy
   					enemyPass();
   				}//end else
   			}//end if
   			
   			else{ //pass due to no other alternatives
   				enemyPass();
   			}//end else
   		}//end if
   		else{ //use the initially selected attack from the random.
   			System.out.println(enemyPokemans.get(0).getName() + " used "+ enemyPokemans.get(0).getMoveName(n)+"!");
   			useAttack(enemyPokemans.get(0).getMove(n),"player"); 
   		}//end else		
   }//end enemyMove
   
   public static void rawPlayerAttack(String[]move){
   		int damage = 0;
   		if (enemyPokemans.get(0).getResistance() == playerPokemans.get(index).getType()){ //if resistance,damage is reduced
   				damage = Integer.parseInt(move[2])/2;
   				System.out.println("It's not very effective...");
   		}//end if 
   		else if (enemyPokemans.get(0).getWeakness() == playerPokemans.get(index).getType()){ //if weakness, damage is increased
   			damage = Integer.parseInt(move[2])*2;
   			System.out.println("It's Super Effective!");
   		}//end else if
   		else{ //regular attack
   			damage  = Integer.parseInt(move[2]);
   			System.out.println("Hit!"); 
   		}
   		if (playerPokemans.get(index).getDisable() == true){ //removing damage for disabled pokemon
   			if (damage > 10){
   				damage -= 10;
   			}
   			else{
   				damage = 0;
   			}
   		}
   		System.out.println("The attack did "+damage+" damage");
   		int currHp = enemyPokemans.get(0).getHealth(); //we don't care if it goes below zero,since the calculatedamage method will check
   		enemyPokemans.get(0).setHealth(currHp - damage); 
   }//end rawPlayerAttack
   
   public static void rawEnemyAttack(String[]move){
   		int damage = 0; //always starts an attack at 0
   		if (playerPokemans.get(index).getResistance() == enemyPokemans.get(0).getType()){ //if resistance
   			damage = Integer.parseInt(move[2])/2;
   			System.out.println("It's not very effective...");
   		}//end if
   		else if (playerPokemans.get(index).getWeakness() == enemyPokemans.get(0).getType()){ //if weakness
   			damage = Integer.parseInt(move[2])*2;
   			System.out.println("It's Super Effective!");
   		}//end else if
   		else{ //regular attack
   			damage = Integer.parseInt(move[2]);
   			System.out.println("Hit!"); 
   		}//end else
   		if (enemyPokemans.get(0).getDisable() == true){
   			if (damage > 10){ //damage lowered for disabled pokemon
   				damage -= 10;
   			}//end if
   			else{
   				damage = 0;
   			}//end else
  			
   		}//end if
   		System.out.println("The attack did "+damage+" damage");
   		int currHp = playerPokemans.get(index).getHealth(); 
   		playerPokemans.get(index).setHealth(currHp-damage); //we don't care if it's negative, the calculate damage method will handle
   }//end rawEnemyAttack
   
   public static void addEnergy(){
   		for (int i = 0; i < playerPokemans.size(); i++){
   			int temp = playerPokemans.get(i).getEnergy(); //to ensure the max amount isn't surpassed
   			playerPokemans.get(i).setStun(false); //reset stuns at the end of round
   			if (temp > 40){
   				playerPokemans.get(i).setEnergy(50); //add energy
   			}//end if
   			else{
   				playerPokemans.get(i).setEnergy(temp+10); //add energy
   			}//end else
   		}//end for
   		
   		for (int i = 0; i < enemyPokemans.size(); i++){
   			int current = enemyPokemans.get(i).getEnergy(); //to ensure the max amount isn't surpassed
   			enemyPokemans.get(i).setStun(false); //reset stuns at the end of the round
   			if (current > 40){
   				enemyPokemans.get(i).setEnergy(50); //add energy
   			}//end if
   			else{
   				enemyPokemans.get(i).setEnergy(current + 10); //add energy
   			}//end else
   		}//end for
   		
   		
   }//end addEnergy
   
   public static void checkWinner(){
   		if (enemyPokemans.size() == 0){ //all enemies are dead
   			System.out.println("CONGRATULATIONS YOU WIN! We have a new Trainer Supreme!");	
   		}//END IF
   		else{ //all players are dead
   			System.out.println("Sadly, you've lost. Try again?");
   		}//end else
   }//end checkWinner
   
}//end PokemonArena