import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Player {
    List<Integer> cards = new ArrayList<Integer>();
    int highestCard = 0;
    int totalWins = 0;

    public void addCard(int value) {
        cards.add(value);
    }

    public int playCard() {
        if (cards.size() == 0) {
            return -1;
        }
        int value = cards.get(0);
        cards.remove(0);
        return value;
    }

    public void winHand(int value) {
        if (highestCard < value) {
            highestCard = value;
        }
        totalWins++;
    }
}

class Deck {
    List<Integer> cards = new ArrayList<Integer>(52);
    Random rand = new Random();
    public Deck() {
        populate();
    }

    private void populate() {
        for (int i = 0; i < 52; i++) {
            cards.add(i+1);
        }
    }

    public int dealCard() {
        int index = rand.nextInt(cards.size());
        int value = cards.get(index);
        cards.remove(index);
        return value;
    }
}

class Game {
    Deck deck = new Deck();
    Player player1 = new Player();
    Player player2 = new Player();

    public void deal() {
        for (int i = 0; i < 26; i++) {
            player1.addCard(deck.dealCard());
            player2.addCard(deck.dealCard());
        }
    }

    public void play() {
        deal();
        for (int i = 0; i < 26; i++) {
            int val1 = player1.playCard();
            int val2 = player2.playCard();
            if (val1 > val2) {
                player1.winHand(val1);
            } else {
                player2.winHand(val2);
            }
        }

        if (player1.totalWins > player2.totalWins) {
            System.out.println("Player1 wins!!!");
        } else if (player2.totalWins > player1.totalWins) {
            System.out.println("Player2 wins!!!");
        } else {
            if (player1.highestCard > player2.highestCard) {
                System.out.println("Player1 wins!!!");
            } else {
                System.out.println("Player2 wins!!!");
            }
        }
    }
}

public class CardGame {
    public static void main(String[] args) {
        Game game = new Game();
        game.play();
    }
}
