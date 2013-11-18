package com.freddrake.thecodebook.substitution;

/**
 *
 */
public class FrequencyAnalysisFactory {
    private WordChecker wordChecker;

    public FrequencyAnalysis getDefaultInstance(String encryptedMessage) {
        return new DefaultFrequencyAnalysis(encryptedMessage, wordChecker);
    }

    public WordChecker getWordChecker() {
        return wordChecker;
    }

    public void setWordChecker(WordChecker wordChecker) {
        this.wordChecker = wordChecker;
    }
}
