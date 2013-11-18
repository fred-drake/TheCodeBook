package com.freddrake.thecodebook.substitution;

import com.freddrake.thecodebook.Crypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 *
 */
public class SimpleSubstitution implements Crypt {
    private Logger logger = LoggerFactory.getLogger(SimpleSubstitution.class);
    private String plaintext;
    private String ciphertext;
    private FrequencyAnalysisFactory frequencyAnalysisFactory;

    public SimpleSubstitution(String plaintext, String ciphertext,
            FrequencyAnalysisFactory frequencyAnalysis) {
        this.plaintext = plaintext;
        this.ciphertext = ciphertext;
        this.frequencyAnalysisFactory = frequencyAnalysis;
    }

    @Override
    public String decrypt(String input) {
        FrequencyAnalysis frequencyAnalysis =
                frequencyAnalysisFactory.getDefaultInstance(input);
        frequencyAnalysis.analyze();

        List<WordAnalysis> analysises = frequencyAnalysis.getWordAnalysises();

        StringBuffer output = new StringBuffer();
        for(Iterator<WordAnalysis> iter = analysises.iterator(); iter.hasNext();) {
            WordAnalysis wordAnalysis = iter.next();
            Set<String> potentialMatches = wordAnalysis.getPotentialMatchAndConfidenceMap().keySet();
            String word = wordAnalysis.getEncryptedWord();
            for(Iterator<String> matchIter = potentialMatches.iterator(); matchIter.hasNext();) {
                word = matchIter.next();
                break;
            }
            output.append(word);
            if (iter.hasNext()) {
                output.append(" ");
            }
        }
        return output.toString();
    }

    @Override
    public String encrypt(String input) {
        StringBuilder sb = new StringBuilder();
        input.chars().forEach((cint) -> {
            char c = (char) cint;
            int index = plaintext.indexOf(c);
            if (index == -1) {
                sb.append(c);
            } else {
                sb.append(ciphertext.charAt(index));
            }
        });

        return sb.toString();
    }

    public String getPlaintext() {
        return plaintext;
    }

    public void setPlaintext(String plaintext) {
        this.plaintext = plaintext;
    }

    public String getCiphertext() {
        return ciphertext;
    }

    public void setCiphertext(String ciphertext) {
        this.ciphertext = ciphertext;
    }
}
