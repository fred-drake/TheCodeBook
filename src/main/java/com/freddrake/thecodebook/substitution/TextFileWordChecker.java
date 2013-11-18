package com.freddrake.thecodebook.substitution;

import com.freddrake.thecodebook.CodeBookException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.net.URLDecoder;
import java.util.function.Predicate;

/**
 *
 */
public class TextFileWordChecker implements WordChecker {
    private Logger logger = LoggerFactory.getLogger(TextFileWordChecker.class);

    private File textFile;

    public TextFileWordChecker(String textFile) {
        this.textFile = new File(textFile);
    }

    public TextFileWordChecker(URL fileURL) {
        try {
            this.textFile = new File(URLDecoder.decode(fileURL.getFile(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new CodeBookException("Couldn't get file from URL: "+fileURL, e);
        }
    }

    @Override
    public void processWords(Predicate<String> wordHandler) {
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(
                    new FileReader(textFile));
        } catch (FileNotFoundException e) {
            throw new CodeBookException("Couldn't open file: "+textFile, e);
        }
        try {
            String currentWord;
            while ((currentWord = bufferedReader.readLine()) != null) {
                if (!wordHandler.test(currentWord)) break;
            }
        } catch (IOException e) {
            throw new CodeBookException(e);
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                logger.error("Couldn't close buffered reader", e);
            }
        }
    }
}
