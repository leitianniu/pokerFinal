import java.io.*;
import java.util.*;
public class Card implements Comparable {
	
	private char rank;
	private char suit;

	//matching is used to determine if a card has
	//the same value as another card in the hand
	//0 matching = no cards of the same kind
	//1 matching = 1 other cards of the same kind
	//2 matching = 2 other cards of the same kind
	private int matching;
	private int value;

	static private boolean init_vflag = false;
	static List<Character> hand_values = new ArrayList<Character>();

	public Card(char initRank, char initSuit){
		rank = initRank;
		suit = initSuit;
		matching = 0;

		if(init_vflag == false)
			init_vlist();
		value = hand_values.indexOf(new Character(rank));
	}

	//this overrides the compare function in Comparable library
	//helps sort the hand when callling Collections.sort();
	@Override
	public int compareTo(Object o){
		Card c = (Card) o;

		int check = compare(c.matching, matching);

		return check != 0 ? check
						  : compare(c.value, value);
	}

	private static int compare(int a, int b){
		return a - b;
	}

	public void printCard(){

		System.out.print(rank + "" + suit + " ");
	}
	public void incr_matching(){
		matching++;
	}
	public char getRank(){
		return rank;
	}

	public char getSuit(){
		return suit;
	}

	public int getValue(){
		return value;
	}

	public int getMatching(){
		return matching;
	}
	public void reset_Matching(){
		matching = 0;
	}


	private static void init_vlist(){
		hand_values.add(new Character('0'));
		hand_values.add(new Character('2'));
		hand_values.add(new Character('3'));
		hand_values.add(new Character('4'));
		hand_values.add(new Character('5'));
		hand_values.add(new Character('6'));
		hand_values.add(new Character('7'));
		hand_values.add(new Character('8'));
		hand_values.add(new Character('9'));
		hand_values.add(new Character('T'));
		hand_values.add(new Character('J'));
		hand_values.add(new Character('Q'));
		hand_values.add(new Character('K'));
		hand_values.add(new Character('A'));

		init_vflag = true;
	}

}