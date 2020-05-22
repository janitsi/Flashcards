package flashcards;

import java.util.*;

public class Deck {

	private final Map<String, String> deck = new HashMap<>();
	private final Map<String, Integer> stats = new HashMap<>();
	private final List<String> terms = new ArrayList<>(); // To store keys for random access

	/**
	 * Check both term and definition are unique (and only then) add card to the deck.
	 *
	 * @param term       term of the card
	 * @param definition definition of the term
	 */
	void addUniqueCard(final String term, final String definition) {

		if (hasTerm(term) || hasDefinition(definition)) {
			return;
		}
		deck.put(term, definition);
		terms.add(term);
	}

	/**
	 * Add card to the deck.
	 *
	 * @param term       term of the card
	 * @param definition definition of the term
	 */
	void updateCard(final String term, final String definition) {

		if (!hasTerm(term)) {
			terms.add(term);
		}
		deck.put(term, definition);
	}

	boolean removeCard(final String term) {
		if (!terms.remove(term)) {
			return false;
		}
		stats.remove(term);
		return deck.remove(term) != null;
	}

	/**
	 * Check if any card contains the definition and return the term of card
	 *
	 * @param definition by which matching card (term) is searched
	 * @return term matching definition
	 */
	String getTerm(final String definition) {

		for (var card : deck.entrySet()) {
			if (definition.equals(card.getValue())) {
				return card.getKey();
			}
		}
		return null;
	}

	String getDefinition(final String term) {
		return deck.get(term);
	}

	boolean hasTerm(final String term) {
		return deck.containsKey(term);
	}

	boolean hasDefinition(final String definition) {
		return deck.containsValue(definition);
	}

	Set<String> termSet() {
		return deck.keySet();
	}

	String randomTerm() {

		Random random = new Random();
		return terms.get(random.nextInt(terms.size()));
	}

	/**
	 * Increase error count by 1
	 * @param term card to update
	 */
	void updateStats(final String term) {
		int errors = stats.getOrDefault(term, 0) + 1;
		stats.put(term, errors);
	}

	/**
	 * Explicitly set error count
	 * @param term card to update
	 * @param errors new error count
	 */
	void updateStats(final String term, final int errors) {
		stats.put(term, errors);
	}

	int getStats(final String term) {
		return stats.getOrDefault(term, 0);
	}

	void resetAllStats() {
		stats.clear();
	}

	Map<String, Integer> hardestCard() {

		Map<String, Integer> topErrors = new HashMap<>();
		int high = 0;   // Highest error count

		if (!stats.isEmpty()) {
			for (String term : stats.keySet()) {
				int errors = stats.get(term);
				if (errors > high) {            // Single highest card - flush and add to list
					high = errors;
					topErrors.clear();
					topErrors.put(term, high);
				} else if (errors == high) {    // Multiple cards with same highest - add to list
					topErrors.put(term, high);
				}
			}
		}
		return topErrors;

	}

}
