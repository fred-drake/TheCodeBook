package com.freddrake.thecodebook.test;

import com.freddrake.thecodebook.substitution.*;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

/**
 *
 */
public class SimpleSubstitutionTest {
    private SimpleSubstitution crypt;

    @Before
    public void setUp() {
        String plaintext =  "abcdefghijklmnopqrstuvwxyz";
        String ciphertext = "defghijklmnopqrstuvwzyzabc"; // Shifting three letters to the left
        String[] testWords = new String[]{"happy", "are", "this", "and", "how"};
        InMemoryWordChecker checker = new InMemoryWordChecker(Arrays.asList(testWords));
        FrequencyAnalysisFactory frequencyAnalysisFactory =
                new FrequencyAnalysisFactory();
        frequencyAnalysisFactory.setWordChecker(checker);
        crypt = new SimpleSubstitution(plaintext, ciphertext, frequencyAnalysisFactory);
    }

    @Test
    public void simpleTest() {

        Assert.assertEquals("kdssb", crypt.encrypt("happy"));
        Assert.assertEquals("happy", crypt.decrypt("kdssb"));
    }

    @Test
    public void multiWordTest() {
        Assert.assertEquals("kdssb duh wklv", crypt.encrypt("happy are this"));
        Assert.assertEquals("happy are this", crypt.decrypt("kdssb duh wklv"));
    }

    @Test
    public void normalMessageTest() {
        String plaintext =  "abcdefghijklmnopqrstuvwxyz";
        String ciphertext = "defghijklmnopqrstuvwzyzabc"; // Shifting three letters to the left

        TextFileWordChecker checker = new TextFileWordChecker(
                this.getClass().getClassLoader().getResource("english-word-list.txt"));
        FrequencyAnalysisFactory frequencyAnalysisFactory =
                new FrequencyAnalysisFactory();
        frequencyAnalysisFactory.setWordChecker(checker);
        crypt = new SimpleSubstitution(plaintext, ciphertext, frequencyAnalysisFactory);

        Assert.assertEquals("wklv lv d qrupdo phvvdjh wkdw zh zdqw wr hqfubsw",
                crypt.encrypt("this is a normal message that we want to encrypt"));
        Assert.assertEquals("this is a normal message that we want to encrypt",
                crypt.decrypt("wklv lv d qrupdo phvvdjh wkdw zh zdqw wr hqfubsw"));
    }
}
