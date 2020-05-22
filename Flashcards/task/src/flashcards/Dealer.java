package flashcards;

import static flashcards.CmdReader.readCmdLine;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;

/**
 * The Dealer class handles the deck of cards and provides methods to read cards from
 * standard input, remove cards and deal cards (play the game).
 */
class Dealer {

	private static final MyLogger logger = MyLogger.getInstance();
	private final Deck deck;

	Dealer() {
		this(new Deck());
	}

	Dealer(Deck deck) {
		this.deck = deck;
	}

	void play() {

		int cardsPlayed = 0;
		int cardsRequested = Integer.parseInt(readCmdLine("How many times to ask"));

		// Play the game
		while (cardsPlayed++ < cardsRequested) {

			String term = deck.randomTerm();
			String answer = readCmdLine(String.format("Print the definition of \"%s\":", term));
			String definition = deck.getDefinition(term);

			StringBuilder result = new StringBuilder();
			if (answer.equalsIgnoreCase(definition)) {
				result.append("Correct answer.");
			} else {
				deck.updateStats(term);
				result.append(String.format("Wrong answer. (The correct one is \"%s\"", definition));
				if (deck.hasDefinition(answer)) {
					result.append(String.format(", you've just written the definition of \"%s\" card",
							deck.getTerm(answer)));
				}
				result.append(".)");
			}
			logger.print(result.toString());
		}
	}

	void addCard() {

		String term = readCmdLine("The card:");
		if (deck.hasTerm(term)) {
			logger.print(String.format("The card \"%s\" already exists.", term));
			return;
		}

		String definition = readCmdLine("The definition of the card:");
		if (deck.hasDefinition(definition)) {
			logger.print(String.format("The definition \"%s\" already exists.", definition));
			return;
		}

		deck.addUniqueCard(term, definition);
		logger.print(String.format("The pair (\"%s\":\"%s\") has been added.", term, definition));

	}

	void removeCard() {

		String term = readCmdLine("The card:");
		if (deck.removeCard(term)) {
			logger.print("The card has been removed.");
		} else {
			logger.print(String.format("Can't remove \"%s\": there is no such card.", term));
		}
	}

	void printHardestCards() {

		var stats = deck.hardestCard(); // Map
		if (stats.isEmpty()) {
			logger.print("There are no cards with errors.");
		} else {

			Iterator<String> iterator = stats.keySet().iterator();
			String term = iterator.next();    // Fetch first term
			int high = stats.get(term);

			//Compose answer depending whether single or multiple highest card(s)
			StringBuilder sb = new StringBuilder();
			if (stats.size() == 1) { // Single term
				sb.append("The hardest card is \"")
				  .append(term)
				  .append("\". You have ")
				  .append(high)
				  .append(" errors answering it.");
			} else { // Multiple terms
				sb.append("The hardest cards are \"")
				  .append(term) // First term fetched already
				  .append("\", \"");
				do {
					sb.append(iterator.next()).append("\", \"");    // Append other terms
				} while (iterator.hasNext());
				sb.delete(sb.length() - 3, sb.length())
				  .append(". You have ")
				  .append(high)
				  .append(" errors answering them.");
			}
			logger.print(sb.toString());
		}
	}

	void resetAllStats() {
		deck.resetAllStats();
		logger.print("Card statistics has been reset.");
	}

	void importDeck() {
		try {
			int cardsImported = Filer.importDeck(deck);
			logger.print(String.format("%d cards have been loaded.", cardsImported));
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
		}
	}
	void importDeck(final File file) {
		try {
			int cardsImported = Filer.importDeck(deck, file);
			logger.print(String.format("%d cards have been loaded.", cardsImported));
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
		}
	}

	void exportDeck() {
		try {
			int cardsExported = Filer.exportDeck(deck);
			logger.print(String.format("%d cards have been saved.", cardsExported));
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
		}
	}
	void exportDeck(final File file) {
		try {
			int cardsExported = Filer.exportDeck(deck, file);
			logger.print(String.format("%d cards have been saved.", cardsExported));
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
		}
	}

}
