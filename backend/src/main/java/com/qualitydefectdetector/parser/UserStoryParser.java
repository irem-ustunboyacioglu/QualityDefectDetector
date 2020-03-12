package com.qualitydefectdetector.parser;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserStoryParser {

    public List<String> parseSentence(String sentence) {
        String normalizedSentence = normalize(sentence);
        return Arrays.asList(normalizedSentence.split("\\s+"));
    }

    private String normalize(String input) {
        return input.replaceAll("[^a-zA-Z0-9 ÇçĞğİıÖöŞşÜü]", "").toLowerCase();
    }
}
