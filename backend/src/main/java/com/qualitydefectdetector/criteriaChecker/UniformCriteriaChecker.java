package com.qualitydefectdetector.criteriaChecker;

import com.qualitydefectdetector.enums.CriteriaType;
import com.qualitydefectdetector.model.CriteriaCheckResult;
import com.qualitydefectdetector.nlpprocessor.ZemberekProcessor;
import com.qualitydefectdetector.parser.UserStoryParser;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

import static com.qualitydefectdetector.enums.CriteriaType.UNIFORM;
import static com.qualitydefectdetector.model.CriteriaCheckResult.CriteriaCheckResultBuilder.aCriteriaCheckResultBuilder;

@Service
public class UniformCriteriaChecker {

    public final ZemberekProcessor zemberekProcessor;
    private final UserStoryParser userStoryParser;

    public UniformCriteriaChecker(ZemberekProcessor zemberekProcessor, UserStoryParser userStoryParser) {
        this.zemberekProcessor = zemberekProcessor;
        this.userStoryParser = userStoryParser;
    }

    public CriteriaCheckResult checkUserStorySetIsUniform(List<String> sentences) {
        HashSet<String> userStoryTypes = new HashSet<>();
        for (String userStory : sentences) {
            userStoryTypes.add(userStoryParser.parseSentenceWithType(userStory).getUserStoryType());
        }
        if (userStoryTypes.size() == 1) {
            return aCriteriaCheckResultBuilder()
                    .satisfiesThisCriteria(true)
                    .errorMessage("")
                    .description(UNIFORM.getDescription())
                    .build();
        } else {
            return aCriteriaCheckResultBuilder()
                    .satisfiesThisCriteria(false)
                    .errorMessage("Bu kullanıcı hikaye seti birden fazla format içeriyor.")
                    .description(UNIFORM.getDescription())
                    .build();
        }
    }
}
