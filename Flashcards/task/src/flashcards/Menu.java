package flashcards;

enum Menu {

	ADD("add"),
	REMOVE("remove"),
	IMPORT("import"),
	EXPORT("export"),
	ASK("ask"),
	EXIT("exit"),
	LOG("log"),
	HARDEST("hardest card"),
	RESET_STATS("reset stats");

	private final String text;

	Menu(String text) {
		this.text = text;
	}

	public String get() {
		return text;
	}

}
