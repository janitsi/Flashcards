package flashcards;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Singleton Logger class
 */
class MyLogger {

	private static MyLogger myLogger = null;
	private final List<String> log;

	private MyLogger() {
		this.log = new ArrayList<>();
	}

	static MyLogger getInstance() {
		if (myLogger == null) {
			myLogger = new MyLogger();
		}
		return myLogger;
	}

	void print(final String logEntry) {

		System.out.println(logEntry);
		log(logEntry);
	}

	void log(final String logEntry) {
		log.add(logEntry + "\n");
	}

	void save() {

		final Scanner sc = new Scanner(System.in);

		print("File name:");
		final String fileName = sc.nextLine();
		log(fileName);

		final File file = new File(fileName);

		try (PrintWriter writer = new PrintWriter(file)) {

			for (String entry : this.log) {
				writer.print(entry);
			}
			String saved = "The log has been saved.";
			System.out.println(saved);
			writer.println(saved); // Write last line

			this.log.clear(); // Flush log

		} catch (FileNotFoundException e) {
			System.out.print("File not found.\n");
		}
	}

}
