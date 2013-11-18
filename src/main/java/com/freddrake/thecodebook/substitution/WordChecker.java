package com.freddrake.thecodebook.substitution;

import java.util.function.Predicate;

/**
 *
 */
public interface WordChecker {
    /**
     * Process words based on a given handler function.  If the function returns
     * false, no further processing is done.
     */
    void processWords(Predicate<String> wordHandler);
}
