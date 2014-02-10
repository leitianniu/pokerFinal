import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

public class UserPlayer{
	
	static int top_of_deck = 0;
	static boolean extra_discard = false;
	static boolean num_in_range = false;
	private static int[] sorted = null;
	private static boolean after_discard = false;

	public static void initiate_hand(List<Card> player_hand, CardPile deck){
		for(int i = 0; i < 5; i++){
			player_hand.add(deck.drawCard());
			//player_hand.get(i).printCard();
			top_of_deck++;
		}
	}
	public static void print_phand(List<Card> player_hand){
		System.out.print("Your hand:");
		for (int i = 0; i < player_hand.size(); i++) {
			System.out.print(" " +  (i+1) + ")");
			if(player_hand.get(i).getRank() == 'A')
				extra_discard = true;
			player_hand.get(i).printCard();
		}
		System.out.println("");
		if(!after_discard){
			if(extra_discard == true){
				System.out.println("Since you have an Ace you can keep the Ace and " +
			                    "discard the other four cards.\n");
			}
		else
			extra_discard = false;
		}
	}

	public static void discard_draw(List<Card> player_hand, CardPile deck, CardPile discardpile){
		int numToDiscard = 0;
		int maxDiscard = 3;
		int temp;
		if(extra_discard == true){
			maxDiscard = 4;
		}

		System.out.println("List the cards numbers you wish to discard. No negative numbers please! > ");
		System.out.println("(enter each card number followed by a space. If no cards need to be discarded, enter a 0)");
		
		// read a line of input
		Scanner readLine = new Scanner(System.in);
		String line = readLine.nextLine();
		// split the line of input into tokens of integers
		String[] intTokens = line.split("[^\\d]+");

		// DEBUG message
		//System.out.println("There are " + intTokens.length + " ints in the input");

		for(int n=0; n<intTokens.length; n++){
			temp = Integer.parseInt(intTokens[n]);
			if (temp > 5 || temp < 0) {
				num_in_range = false;
				break;
			}
			else{
				num_in_range = true;
			}
		}

		// if the user tries to discard more cards than allowed, keep reading inputs
		while(line.isEmpty() || (intTokens.length > maxDiscard) || num_in_range == false){
			if(intTokens.length > maxDiscard){
				System.out.println("Attempting to discard more than the max number of cards allowed: " + maxDiscard);
			}
			if (num_in_range == false) {
				System.out.println("Input is out of range, please enter only number 1-5");
			}

			line = readLine.nextLine();
			if(line.isEmpty()){
				System.out.println("Input cannot be blank, please enter the cards again");
				continue;
			}
			intTokens = line.split("[^\\d]+");
			System.out.println("There are " + intTokens.length + " ints in the input");

			for(int n=0; n<intTokens.length; n++){
				temp = Integer.parseInt(intTokens[n]);
				if (temp > 5 || temp <=0) {
					num_in_range = false;
					break;
				}
				else{
					num_in_range = true;
				}
			}


		}
			
		// now that we have the correct amount of cards to discard, we sort the input
		numToDiscard = intTokens.length;
		Arrays.sort(intTokens);
		// then convert the ints from string to int
		sorted = new int[intTokens.length];
		for(int k=0;k<intTokens.length;k++){
			try {
				sorted[k] = Integer.parseInt(intTokens[k]);
			    } catch (NumberFormatException nfe) {};
		}

		//If they user doesn't want to discard then they enter 1 0
		//this changes numTodiscard;
		if(intTokens.length == 1 && sorted[0] == 0)
			numToDiscard = 0;

		// test to see the int array
		//System.out.println("The sorted line of input is" + Arrays.toString(sorted));
		
		
		int debug_value = 0;
		int index_check = 1;
		
		for (int i=0;i<numToDiscard;i++){
			debug_value = sorted[i] - index_check;
			player_hand.get(debug_value).printCard();
			// remove from hand
			discardpile.insert_card(player_hand.remove(debug_value));
			index_check++;
		}
		if(numToDiscard != 0)
			System.out.println("are discarded, drawing cards to fill hand\n");
		// draw cards to fill hand

		for(int i=0;i<numToDiscard;i++){
			player_hand.add(deck.drawCard());
			top_of_deck++;
		}
		//resest players hand and sorts it again:
		if(numToDiscard != 0){
			for(int i = 0; i < 5; i++){
				player_hand.get(i).reset_Matching();
			}
			handEvaluator.hand_pairing(player_hand);
		}
		after_discard = true;

	}



	//OLD DISCARD CODE
	/*public static void discard_draw(List<Card> player_hand, CardPile deck, CardPile discardpile){
		int numToDiscard = 0;
		int[] cardsToDiscard = new int[4];

		Scanner in = new Scanner(System.in);
		Pattern delimiters = Pattern.compile(System.getProperty("line.separator")+"|\\s");
		in.useDelimiter(delimiters);	
		System.out.println("List the cards numbers you wish to discard. > ");
		System.out.println("(enter each card number followed by a space, or hit enter twice)");
		
		int debug_value = 0;
		int index_check = 1;

		while(in.hasNextInt()){
			numToDiscard++;
			cardsToDiscard[numToDiscard] = in.nextInt();
			debug_value = cardsToDiscard[numToDiscard] - index_check;
			player_hand.get(debug_value).printCard();
			// remove from hand
			//player_hand.remove(cardsToDiscard[numToDiscard] - 1);
			discardpile.insert_card(player_hand.remove(debug_value));
			index_check++;
		}
		// draw cards to fill hand

		for(int i=0;i<numToDiscard;i++){
			player_hand.add(deck.drawCard());
			top_of_deck++;
		}
		//resest players hand and sorts it again:
		for(int i = 0; i < 5; i++){
			player_hand.get(i).reset_Matching();
		}
		handEvaluator.hand_pairing(player_hand);
		print_phand(player_hand);
	}*/
}