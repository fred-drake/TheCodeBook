package com.freddrake.thecodebook.substitution;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * Handles the measuring of frequency analysis with a given encrypted message.
 */
public class DefaultFrequencyAnalysis implements FrequencyAnalysis {
    private Logger logger = LoggerFactory.getLogger(DefaultFrequencyAnalysis.class);
    private String encryptedMessage;
    private WordChecker wordChecker;
    private List<WordAnalysis> wordAnalysises;

    public DefaultFrequencyAnalysis(String encryptedMessage, WordChecker wordChecker) {
        this.encryptedMessage = encryptedMessage;
        this.wordChecker = wordChecker;
        wordAnalysises = new ArrayList<>();
    }

    public void analyze() {
        encryptedMessage = encryptedMessage.toLowerCase(); // Ignore case

        // Frequency analysis across the entire message
        Map<Character, Integer> letterFrequencies = new HashMap<>();
        int[] totalLetters = new int[]{0}; // array hackery for use in lambda
        encryptedMessage.chars().forEach((cint) -> {
            char c = (char) cint;
            if (c == ' ') return; // Ignore spaces
            if (letterFrequencies.get(c) == null) {
                letterFrequencies.put(c, 0);
            }
            letterFrequencies.put(c, letterFrequencies.get(c) + 1);
            totalLetters[0]++;
        });

        Map<Character, BigDecimal> letterFrequencyByPercentage = new TreeMap<>((o1, o2) ->
            letterFrequencies.get(o1).compareTo(letterFrequencies.get(o2))
        );
        letterFrequencies.keySet().forEach((c) -> {
            BigDecimal pct = new BigDecimal(letterFrequencies.get(c)).divide(
                    new BigDecimal(totalLetters[0]), 3, RoundingMode.DOWN);
            letterFrequencyByPercentage.put(c, pct);
        });

        // Split words and check potential matches across all words
        String[] words = encryptedMessage.split("\\s+");
        if (words.length == 0) {
            words = new String[]{ encryptedMessage };
        }

        for(String word : words) {
            List<Integer> encryptedWordRepeatPattern = getWordRepeatPattern(word);

            List<String> matchingWordPatterns = new ArrayList<>();
            wordChecker.processWords((w) -> {
                List<Integer> wordRepeatPattern = getWordRepeatPattern(w);
                if (encryptedWordRepeatPattern.equals(wordRepeatPattern)) {
                    matchingWordPatterns.add(w);
                }
                return true;
            });

            WordAnalysis wordAnalysis = new WordAnalysis(word);
            if (matchingWordPatterns.size() > 0) {
                BigDecimal averageConfidence = new BigDecimal("1").divide(
                        new BigDecimal(matchingWordPatterns.size()), 3, RoundingMode.DOWN);
                matchingWordPatterns.forEach((w) -> {
                    wordAnalysis.getPotentialMatchAndConfidenceMap().put(w, averageConfidence);
                });
            }
            wordAnalysises.add(wordAnalysis);
        }

        // TODO - Walk through word analysises to adjust confidence scores,
        // TODO - probably using the letterFrequencyByPercentage map

    }

    /**
     * Produces a character pattern list based on the index of the first
     * character found in the word.  For instance, "hello" would be
     * represented by the integer list 0, 1, 2, 2, 4.  Any other word with
     * a similar repeat pattern ("happy", "bells", etc) would have this
     * same return value.
     */
    public List<Integer> getWordRepeatPattern(String word) {
        // Check for repeat letter pattern
        List<Character> repeatCharacterList = new ArrayList<>();
        List<Integer> characterIndexes = new ArrayList<>(word.length());
        word.chars().forEach((cint) -> {
            char c = (char) cint;
            int index = repeatCharacterList.indexOf(c);
            if (index == -1) {
                index = repeatCharacterList.size();
                repeatCharacterList.add(c);
            }
            characterIndexes.add(index);
        });

        return characterIndexes;
    }

    public List<WordAnalysis> getWordAnalysises() {
        return wordAnalysises;
    }
}
