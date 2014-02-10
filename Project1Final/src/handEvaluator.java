import java.io.*;
import java.util.*;

public class handEvaluator{
	
	public static void hand_pairing(List<Card> hand){
		for(int i = 0; i < 5; i++){
			for(int j = i+1; j < 5; j++){
				if(hand.get(i).getValue() == hand.get(j).getValue()){
					hand.get(i).incr_matching();
					hand.get(j).incr_matching();
				}
			}
		}
		Collections.sort(hand);
	}

	public static int hand_calculator(List<Card> hand, boolean print_hand){
		int match_total = 0;

		//adds the the hands match pairings.
		//then the total will help evaluate the hand
		//special cases are needed for flush, straight, straight flush
		//and high card.

		for(int i = 0; i < 5; i++){
			match_total += hand.get(i).getMatching();
		}

		//System.out.println("total: " + match_total);
		switch(match_total){
			case 0:
				return special_hands(hand, print_hand);
			case 2:
				if(print_hand)
					System.out.println("has one pair\n");
				return match_total;
			case 4:
				if(print_hand)
					System.out.println("has two pair\n");
				return match_total;
			case 6:
				if(print_hand)
					System.out.println("has three of a kind\n");
				return match_total;
			case 8:
				if(print_hand)
					System.out.println("has a full house\n");
				//returning 9 because three of a king < straight < flush < full house
				//							6				7		8			9
				return match_total+1;
			case 12:
				if(print_hand)
					System.out.println("has four of a kind\n");
				return match_total;
			default:
				return match_total;
		}

	}
	public static int tie_breaker(List<Card> x_hand, List<Card> y_hand){
		//boolean same_values = false;
		for(int i = 0; i < 5; i++){
			//Debug:
			//System.out.println(x_hand.get(i).getValue() + "|" + y_hand.get(i).getValue());
			if(x_hand.get(i).getValue() > y_hand.get(i).getValue())
				return 1;
			else if(x_hand.get(i).getValue() < y_hand.get(i).getValue())
				return -1;
		}

		return 0;
	}
	private static int special_hands(List<Card> hand, boolean print_hand){
		//check for straight:
		boolean straight = true, flush = true;

		//straight check:
		for(int i = 0; i < 4; i++){
			if(hand.get(i).getValue() != (hand.get(i+1).getValue() + 1))
				straight = false;
		}

		//flush check:
		for(int i = 0; i < 4; i++){
			if(hand.get(i).getSuit() != (hand.get(i+1).getSuit()))
				flush = false;
		}

		if(straight && flush){
			//System.out.println("You have a straight flush");
			return 13;
		}
		else if(flush){
			if(print_hand)
				System.out.println("has a flush\n");
			return 8;
		}
		else if(straight){
			if(print_hand)
				System.out.println("has a straight\n");
			return 7;
		}
		else{
			if(print_hand){
				System.out.print("The highest card is: ");
				hand.get(0).printCard();
				System.out.println("\n");
			}
			return 0;
		}	
	}
}