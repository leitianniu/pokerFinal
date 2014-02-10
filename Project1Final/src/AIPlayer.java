import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

import org.omg.CORBA.PUBLIC_MEMBER;

public class AIPlayer{
	
	static int top_of_deck = 0;
	static boolean extra_discard = false;
	public static void initiate_hand(List<Card> ai_hand, CardPile deck){
			for(int i = 0; i < 5; i++){
				ai_hand.add(deck.drawCard());
				//ai_hand.get(i).printCard();
				top_of_deck++;
		}
	}
	
	public static void print_aihand(List<Card> ai_hand){
		Card current_card;
		
		for (int i = 0; i < ai_hand.size(); i++) {
			System.out.print(" " +  (i+1) + ")");
			current_card = ai_hand.get(i);
			current_card.printCard();
		}
		System.out.println("");
	}
	
	public static void discard_draw(List<Card> ai_hand, CardPile deck, CardPile discardpile, int compnum){
		
		int aihand_value = handEvaluator.hand_calculator(ai_hand, false);
		int numToDiscard = 0;

		if(aihand_value == 12 || aihand_value == 4){
			ai_hand.remove(4);
			numToDiscard++;
			System.out.println("Computer player " + compnum + " has discarded 1 card.\n");
		}
		else if(aihand_value >= 7){
			System.out.println("Computer player " + compnum + " has discarded 0 cards.\n");
		}
		else if(aihand_value == 6){
			ai_hand.remove(4);
			ai_hand.remove(3);
			numToDiscard += 2;
			System.out.println("Computer player " + compnum + " has discarded 2 cards.\n");
		}
		else if(aihand_value == 2){
			ai_hand.remove(4);
			ai_hand.remove(3);
			ai_hand.remove(2);
			numToDiscard += 3;
			System.out.println("Computer player " + compnum + " has discarded 3 cards.\n");
		}
		else{
			//check for flushes
			boolean four_flush = fourflush_check(ai_hand);
			if(four_flush == true){
				numToDiscard++;
				System.out.println("Computer player " + compnum + " has discarded 1 card.\n");
			}
			
			if(four_flush == false){
				int temp_val = 0;
				boolean seq = false;
				int index;
				for(index = 0; index < 2; index++){
					seq = true;
					for(int j = index; j < 3 + index; j++){
						if(ai_hand.get(j).getValue() != ai_hand.get(j+1).getValue() + 1)
							seq = false;
					}
						if(seq == true)
							break;
				}
				if(seq == true){
					if(index == 0)
						ai_hand.remove(4);
					else if(index == 1)
						ai_hand.remove(0);
					numToDiscard++;
					System.out.println("Computer player " + compnum + " has discarded 1 card.\n");
				}
				else{
					if(ai_hand.get(0).getValue() == 13){
						ai_hand.remove(4);
						ai_hand.remove(3);
						ai_hand.remove(2);
						ai_hand.remove(1);
						numToDiscard += 4;
						System.out.println("Computer player " + compnum + " has discarded 4 cards.\n");
					}
					else{
						ai_hand.remove(4);
						ai_hand.remove(3);
						ai_hand.remove(2);
						numToDiscard += 3;
						System.out.println("Computer player " + compnum + " has discarded 3 cards.\n");
					}
				}
			}


		}
		// draw cards to fill hand

		for(int i=0;i<numToDiscard;i++){
			ai_hand.add(deck.drawCard());
			top_of_deck++;
		}
		//reset players hand and sorts it again:
		for(int i = 0; i < 5; i++){
			ai_hand.get(i).reset_Matching();
		}
		handEvaluator.hand_pairing(ai_hand);
	}

	private static boolean fourflush_check(List<Card> ai_hand){
			boolean four_flush = false;
			int counter = 1, h = 0;
			char temp_suit;
			for(h = 0; h < 2; h++){
				temp_suit = ai_hand.get(h).getSuit();
				for(int j = h + 1; j < 5; j++){
					if(temp_suit == ai_hand.get(j).getSuit())
						counter++;
				}
				if(counter == 4){
					four_flush = true;
					break;
				}
				else
					counter = 1;
			}

			if(four_flush == true){
				temp_suit = ai_hand.get(h).getSuit();

				for(int i = h; i < 5; i++){
					if(ai_hand.get(i).getSuit() != temp_suit){
						ai_hand.remove(i);
						break;
					}
				}
			}
			return four_flush;
	}
	
	// end of class
}