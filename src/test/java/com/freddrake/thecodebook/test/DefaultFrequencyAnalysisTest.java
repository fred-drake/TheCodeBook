package com.freddrake.thecodebook.test;

import com.freddrake.thecodebook.substitution.*;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.*;

/**
 *
 */
public class DefaultFrequencyAnalysisTest {
    private WordChecker wordChecker;

    @Before
    public void setUp() throws Exception {
        String[] words = new String[]{ "happy", "are", "this", "and", "how" };
        wordChecker = new InMemoryWordChecker(Arrays.asList(words));
    }

    @Test
    public void testSimpleAnalyze() throws Exception {
        String encryptedMessage = "kdssb duh wklv"; // "happy are this"
        DefaultFrequencyAnalysis analysis = new DefaultFrequencyAnalysis(
                encryptedMessage, wordChecker);
        analysis.analyze();
        List<WordAnalysis> wordAnalysises = analysis.getWordAnalysises();
        Assert.assertNotNull(wordAnalysises);
        Assert.assertEquals(3, wordAnalysises.size());

        WordAnalysis wordAnalysys;
        List<String> potentialMatchList;
        Map<String, BigDecimal> potentialMatches;

        wordAnalysys = wordAnalysises.get(0);
        Assert.assertEquals(1, wordAnalysys.getPotentialMatchAndConfidenceMap().size());
        potentialMatches = wordAnalysys.getPotentialMatchAndConfidenceMap();
        potentialMatchList = new ArrayList<>(potentialMatches.keySet());
        Assert.assertEquals("happy", potentialMatchList.get(0));
        Assert.assertEquals(new BigDecimal("1.000"), potentialMatches.get(potentialMatchList.get(0)));

        wordAnalysys = wordAnalysises.get(1);
        Assert.assertEquals(3, wordAnalysys.getPotentialMatchAndConfidenceMap().size());
        potentialMatches = wordAnalysys.getPotentialMatchAndConfidenceMap();
        potentialMatchList = new ArrayList<>(potentialMatches.keySet());
        Assert.assertEquals("are", potentialMatchList.get(0));
        Assert.assertEquals(new BigDecimal("0.333"), potentialMatches.get(potentialMatchList.get(0)));

        wordAnalysys = wordAnalysises.get(2);
        Assert.assertEquals(1, wordAnalysys.getPotentialMatchAndConfidenceMap().size());
        potentialMatches = wordAnalysys.getPotentialMatchAndConfidenceMap();
        potentialMatchList = new ArrayList<>(potentialMatches.keySet());
        Assert.assertEquals("this", potentialMatchList.get(0));
        Assert.assertEquals(new BigDecimal("1.000"), potentialMatches.get(potentialMatchList.get(0)));
    }
}
