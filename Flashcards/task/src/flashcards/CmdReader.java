package flashcards;

import java.util.Scanner;

public class CmdReader {

	private static final MyLogger logger = MyLogger.getInstance();
	private static final Scanner sc = new Scanner(System.in);

	/**
	 * Prompt text and read answer from command line.
	 * @param prompt question to output to user
	 * @return answer provided
	 */
	static String readCmdLine(final String prompt) {

		logger.print(prompt);
		String answer = sc.nextLine();
		logger.log(answer);
		return answer;
	}
}
