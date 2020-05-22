package flashcards;

import static flashcards.Menu.EXIT;

import java.io.File;

public class Main {

	private static final MyLogger logger = MyLogger.getInstance();
	private static File exportFile = null;

	public static void main(String[] args) {

		final Dealer dealer = new Dealer();

		boolean exportOnExit = false;
		for (int i = 0; i < args.length; i++) {
			if (args[i].equals("-import")) {
				File file = new File(args[i + 1]);
				dealer.importDeck(file);
			} else if (args[i].equals("-export")) {
				exportFile = new File(args[i + 1]);
				exportOnExit = true;
			}
		}

		Menu selection;
		do {
			selection = MenuController.selectMenuItem();
			switch (selection) {
				case ADD:
					dealer.addCard();
					break;
				case REMOVE:
					dealer.removeCard();
					break;
				case IMPORT:
					dealer.importDeck();
					break;
				case EXPORT:
					dealer.exportDeck();
					break;
				case ASK:
					dealer.play();
					break;
				case LOG:
					logger.save();
					break;
				case HARDEST:
					dealer.printHardestCards();
					break;
				case RESET_STATS:
					dealer.resetAllStats();
					break;
			}
			logger.print("");

		} while (selection != EXIT);
		System.out.println("Bye bye!");

		if (exportOnExit && exportFile != null) {
			dealer.exportDeck(exportFile);
		}
	}

}


