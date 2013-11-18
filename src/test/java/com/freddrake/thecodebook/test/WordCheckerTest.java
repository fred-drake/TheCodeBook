package com.freddrake.thecodebook.test;

import com.freddrake.thecodebook.substitution.InMemoryWordChecker;
import com.freddrake.thecodebook.substitution.TextFileWordChecker;
import junit.framework.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 *
 */
public class WordCheckerTest {
    @Test
    public void testTextFileWordChecker() throws Exception {
        TextFileWordChecker checker = new TextFileWordChecker(
                this.getClass().getClassLoader().getResource("simple-word-list.txt"));
        int[] index = new int[]{0};
        String[] testWords = new String[]{"happy", "are", "this", "and", "how"};
        checker.processWords((word) -> {
            Assert.assertEquals(testWords[index[0]], word);
            index[0]++;
            return true;
        });
    }

    @Test
    public void testInMemoryWordChecker() throws Exception {
        String[] testWords = new String[]{"happy", "are", "this", "and", "how"};
        InMemoryWordChecker checker = new InMemoryWordChecker(Arrays.asList(testWords));
        int[] processCount = new int[]{0};

        checker.processWords((word) -> {
            processCount[0]++;
            return true;
        });
        Assert.assertEquals(5, processCount[0]);

        processCount[0] = 0;
        checker.processWords((word) -> {
            processCount[0]++;
            return !word.equals("this");
        });
        Assert.assertEquals(3, processCount[0]);
    }
}
