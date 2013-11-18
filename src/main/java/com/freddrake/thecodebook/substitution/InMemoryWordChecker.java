package com.freddrake.thecodebook.substitution;

import java.util.List;
import java.util.function.Predicate;

/**
 * Supplies a list of words that are stored in memory.  Typically only used
 * for reasonably short dictionaries or testing purposes.
 */
public class InMemoryWordChecker implements WordChecker {
    private List<String> words;

    public InMemoryWordChecker(List<String> words) {
        this.words = words;
    }

    @Override
    public void processWords(Predicate<String> wordHandler) {
        for(String word : words) {
            if (!wordHandler.test(word)) break;
        }
    }
}
