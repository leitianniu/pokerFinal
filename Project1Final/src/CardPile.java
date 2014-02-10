import java.io.*;
import java.util.*;

public class CardPile{
	//Card pile = new Card[52];
	List<Card> pile= new ArrayList<>();
	private int num_of_cards;
	private boolean discard;

	public CardPile(){
		num_of_cards = 0;
		discard = true;
	}

	public CardPile(boolean shuffle){
		SuitHandler(pile, 0, 12, 'C');
		SuitHandler(pile, 13, 25, 'H');
		SuitHandler(pile, 26, 38, 'D');
		SuitHandler(pile, 39, 51, 'S');
		num_of_cards = 52;
		discard = false;
		if(shuffle){
			Shuffle_Pile(pile);
		}
	}

	private void Shuffle_Pile(List<Card> pile){
		Collections.shuffle(pile);
	}

	private void SuitHandler(List<Card> pile, int lo, int hi, char suit){
		char[] rank = {'A', '2', '3', '4', '5', '6', '7', '8', '9'
						, 'T', 'J', 'Q', 'K'};
		for(int i = lo; i < hi + 1; i++){
			pile.add(new Card(rank[i%13], suit));
		}
	}

	public void PilePrint(){
		for(int i = 0; i < num_of_cards; i++){
			pile.get(i).printCard();
		}
		System.out.print("\n");
	}
	public void deal_cards(List<List<Card>> all_players ){
		for(int i = 0; i < 5; i++){
			for(int j = 0; j < all_players.size(); j++){
				all_players.get(j).add(drawCard());
			}
		}
	}
	public Card drawCard(){
		if(num_of_cards == 0){
			System.out.println("No more cards to draw.");
			return null;
		}
		if(discard == true){
			System.out.println("Cannot draw from this pile.");
			return null;
		}
		num_of_cards--;
		return pile.remove(0);
	}

	public void insert_card(Card thrown_away){
		pile.add(thrown_away);
		num_of_cards++;

	}
}