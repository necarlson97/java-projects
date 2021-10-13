package hangman;

import static org.junit.Assert.assertEquals;

/**
 * A class to play the hangman game.
 */
public class LinkedListGame {

	public static void main(String[] args) {
          Words words = new Words("data/grade2-words.txt");
          String word = words.pick();
          GameIO gameIO = new ConsoleGameIO();
          GameModel model = new LinkedListGameModel("Volume");
          GameController gameController = new GameController(gameIO, model);
          
          gameController.play();
	}

}

