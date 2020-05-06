package com.qualitydefectdetector.criteriaChecker;

import com.qualitydefectdetector.enums.UserStoryType;
import com.qualitydefectdetector.model.CriteriaCheckResult;
import com.qualitydefectdetector.model.UserStory;
import com.qualitydefectdetector.nlpprocessor.ZemberekProcessor;
import com.qualitydefectdetector.parser.UserStoryParser;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

import static com.qualitydefectdetector.model.CriteriaCheckResult.CriteriaCheckResultBuilder.aCriteriaCheckResultBuilder;

@Service
public class UniformCriteriaChecker {

    public final ZemberekProcessor zemberekProcessor;
    private final UserStoryParser userStoryParser;

    public UniformCriteriaChecker(ZemberekProcessor zemberekProcessor,UserStoryParser userStoryParser) {
        this.zemberekProcessor = zemberekProcessor;
        this.userStoryParser = userStoryParser;
    }

    public CriteriaCheckResult checkUserStorySetIsUniform(List<String> sentences){
        HashSet<UserStoryType> userStoryTypes = new HashSet<>();
        for(String userStory: sentences){
            userStoryTypes.add(userStoryParser.parseSentenceWithType(userStory).getUserStoryType());
        }
        if(userStoryTypes.size() == 1){
            return aCriteriaCheckResultBuilder()
                    .satisfiesThisCriteria(true)
                    .errorMessage("")
                    .build();
        }
        else {
            return aCriteriaCheckResultBuilder()
                    .satisfiesThisCriteria(false)
                    .errorMessage("This user story set apply more than one template, therefore other criteria weren't checked.")
                    .build();
        }
    }
}
