package com.freddrake.thecodebook.substitution;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Organizes matches of potential words for a given analysis
 */
public class WordAnalysis {
    private String encryptedWord;
    private Map<String, BigDecimal> potentialMatchAndConfidenceMap;

    public WordAnalysis(String encryptedWord) {
        this.encryptedWord = encryptedWord;
        potentialMatchAndConfidenceMap = new LinkedHashMap<>();
    }

    public Map<String, BigDecimal> getPotentialMatchAndConfidenceMap() {
        return potentialMatchAndConfidenceMap;
    }

    public String getEncryptedWord() {
        return encryptedWord;
    }
}
