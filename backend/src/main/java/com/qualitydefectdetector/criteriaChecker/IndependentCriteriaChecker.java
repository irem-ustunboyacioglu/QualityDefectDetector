package com.qualitydefectdetector.criteriaChecker;

import com.qualitydefectdetector.model.CriteriaCheckResult;
import com.qualitydefectdetector.model.UserStory;
import com.qualitydefectdetector.nlpprocessor.ZemberekProcessor;
import com.qualitydefectdetector.parser.UserStoryParser;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.qualitydefectdetector.enums.CriteriaType.INDEPENDENT;
import static com.qualitydefectdetector.model.CriteriaCheckResult.CriteriaCheckResultBuilder.aCriteriaCheckResultBuilder;
import static java.util.regex.Pattern.matches;

@Service
public class IndependentCriteriaChecker {

    public final ZemberekProcessor zemberekProcessor;
    private final UserStoryParser userStoryParser;

    private final static String DEPENDENT_SENTENCE_FORMAT = ".*(KH|kh|Kh)[1-9]+.*";

    public IndependentCriteriaChecker(ZemberekProcessor zemberekProcessor, UserStoryParser userStoryParser) {
        this.zemberekProcessor = zemberekProcessor;
        this.userStoryParser = userStoryParser;
    }

    public CriteriaCheckResult checkUserStorySetIsIndependent(List<String> sentences) {
        Set<Integer> dependentSentences = new HashSet<>();
        for (int i = 0; i < sentences.size(); i++) {
            UserStory userStory = parse(sentences.get(i));
            final String goal = userStory.getGoal();
            final String reason = userStory.getReason();
            if (matches(DEPENDENT_SENTENCE_FORMAT, goal)) {
                dependentSentences.add(i + 1);
            }
            if (reason != null) {
                if (matches(DEPENDENT_SENTENCE_FORMAT, reason)) {
                    dependentSentences.add(i + 1);
                }
            }
        }

        if (!dependentSentences.isEmpty()) {
            return aCriteriaCheckResultBuilder()
                    .satisfiesThisCriteria(false)
                    .errorMessage("Bağımlı cümleler: " + dependentSentences.toString())
                    .description(INDEPENDENT.getDescription())
                    .build();
        }

        return aCriteriaCheckResultBuilder()
                .satisfiesThisCriteria(true)
                .errorMessage("")
                .description(INDEPENDENT.getDescription())
                .build();
    }

    public UserStory parse(String sentence) {
        return userStoryParser.parseSentenceWithType(sentence);
    }
}
