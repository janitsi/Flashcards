package flashcards;

import static flashcards.CmdReader.readCmdLine;

class MenuController {

	static Menu selectMenuItem() {

		// Loop until valid action selected
		while (true) {

			String selection = readCmdLine(getMenu()).toLowerCase();
			// Iterate menu to find matching action
			for (Menu item : Menu.values()) {
				if (selection.equals(item.get())) {
					return item;
				}
			}
		}
	}

	static String getMenu() {

		StringBuilder builder = new StringBuilder("Input the action (");
		for (Menu item : Menu.values()) {
			builder.append(item.get()).append(", ");
		}
		builder.delete(builder.length() - 2, builder.length()).append("):");
		return builder.toString();
	}

}

