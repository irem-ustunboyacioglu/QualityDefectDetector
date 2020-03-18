package com.qualitydefectdetector.parser;

import com.google.common.collect.Sets;
import com.qualitydefectdetector.model.request.UserStory;
import com.qualitydefectdetector.nlpprocessor.ZemberekProcessor;
import org.antlr.v4.runtime.Token;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Pattern;

import static com.qualitydefectdetector.enums.UserStoryType.ROLE_GOAL_REASON;
import static com.qualitydefectdetector.model.request.UserStory.UserStoryBuilder.aUserStory;

@Service
public class UserStoryParser {

    private final ZemberekProcessor zemberekProcessor;

    public UserStoryParser(ZemberekProcessor zemberekProcessor) {
        this.zemberekProcessor = zemberekProcessor;
    }

    public List<String> parseSentence(String sentence) {
        String normalizedSentence = normalize(sentence);
        return Arrays.asList(normalizedSentence.split("\\s+"));
    }

    public UserStory parseSentenceWithType(String sentence) {
        String normalizedUserStory = normalize(sentence);
        List<Token> tokens = zemberekProcessor.tokenize(sentence);

        if (Pattern.matches(ROLE_GOAL_REASON.getFormatRegex(), normalizedUserStory)) {
            return parseRoleReasonGoalFormat(tokens, sentence);
        } else {
            return null;
        }
    }

    private UserStory parseRoleReasonGoalFormat(List<Token> tokens, String userStory) {
        String rolePart = userStory.substring(0, parseRole(tokens));
        String goalPart = userStory.substring(parseRole(tokens) + 7, parseGoal(tokens) + 1);
        String reasonPart = userStory.substring(parseGoal(tokens) + 1);
        return aUserStory()
                .role(rolePart)
                .goal(goalPart)
                .reason(reasonPart)
                .userStoryType(ROLE_GOAL_REASON)
                .userStorySentence(userStory)
                .build();
    }

    public int parseRole(List<Token> tokens) {
        for (Token token : tokens) {
            if (token.getText().equals("olarak")) {
                return token.getStartIndex();
            }
        }
        return -1;
    }

    public int parseGoal(List<Token> tokens) {
        HashSet<String> keyWords1 = Sets.newHashSet("istiyorum", "istiyoruz");
        HashSet<String> keyWords2 = Sets.newHashSet("ihtiyacım", "ihtiyacımız");
        for (int i = 0; i < tokens.size() - 1; i++) {
            Token token = tokens.get(i);
            if (keyWords1.contains(token.getText())) {
                return token.getStopIndex();
            } else if (keyWords2.contains(token.getText()) && tokens.get(i + 1).getText().equals("var")) {
                return tokens.get(i + 1).getStopIndex();
            }
        }
        return -1;
    }

    private String normalize(String input) {
        return input.replaceAll("[^a-zA-Z0-9 ÇçĞğİıÖöŞşÜü]", "").toLowerCase();
    }
}
