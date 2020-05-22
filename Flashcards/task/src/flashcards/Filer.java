package flashcards;

import static flashcards.CmdReader.readCmdLine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * The Filer class provides static methods for persistence.
 */
class Filer {

	private static final String SEPARATOR = "::";

	/**
	 * Prompts for filename and reads cards from text file.
	 * Update card if term exist or add new card if term is unique.
	 *
	 * @param deck Deck where cards are loaded
	 */
	static int importDeck(final Deck deck) throws FileNotFoundException {

		final File file = new File(readCmdLine("File name:"));
		return importDeck(deck, file);
	}

	/**
	 * Reads cards from text file provided as argument to this function.
	 * Update card if term exist or add new card if term is unique.
	 *
	 * @param deck Deck where cards are loaded
	 * @param file import file handle
	 */
	static int importDeck(final Deck deck, final File file) throws FileNotFoundException {

		int cardsImported = 0;
		try (final Scanner sc = new Scanner(file)) {

			while (sc.hasNext()) {
				String[] data = sc.nextLine().split(SEPARATOR);
				deck.updateCard(data[0], data[1]);
				deck.updateStats(data[0], Integer.parseInt(data[2]));
				cardsImported++;
			}
			return cardsImported;
		}
	}

	/**
	 * Saves deck to file as text.
	 *
	 * @param deck Deck containing cards to export to file
	 */
	static int exportDeck(final Deck deck) throws FileNotFoundException {

		final File file = new File(readCmdLine("File name:"));
		return exportDeck(deck, file);
	}

	/**
	 * Writes deck to file as text.
	 *
	 * @param deck Deck containing cards to export to file
	 * @param file export file handle
	 */
	static int exportDeck(final Deck deck, File file) throws FileNotFoundException {

		int cardsExported = 0;
		try (PrintWriter writer = new PrintWriter(file)) {

			for (String term : deck.termSet()) {
				writer.printf("%s%s%s%s%d\n", term, SEPARATOR,
						deck.getDefinition(term), SEPARATOR, deck.getStats(term));
				cardsExported++;
			}
		}
		return cardsExported;
	}

}
