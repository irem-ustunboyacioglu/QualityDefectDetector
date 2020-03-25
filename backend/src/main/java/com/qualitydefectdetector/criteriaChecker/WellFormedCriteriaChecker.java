package com.qualitydefectdetector.criteriaChecker;

import com.qualitydefectdetector.model.CriteriaCheckResult;
import com.qualitydefectdetector.model.UserStory;
import com.qualitydefectdetector.nlpprocessor.ZemberekProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zemberek.morphology.analysis.SingleAnalysis;

import java.util.List;

@Service
public class WellFormedCriteriaChecker {

    public final ZemberekProcessor zemberekProcessor;

    @Autowired
    public WellFormedCriteriaChecker(ZemberekProcessor zemberekProcessor) {
        this.zemberekProcessor = zemberekProcessor;

    }

    public CriteriaCheckResult checkRolePart(UserStory userStory) {
        String rolePart = userStory.getRole().toLowerCase();
        List<SingleAnalysis> analysis = zemberekProcessor.analyzeAndDisambiguate(userStory.getGoal());

        if ((!rolePart.contains("bir") && rolePart.split("\\s+").length < 2) ||
                (rolePart.contains("bir") && rolePart.contains("olarak") && rolePart.split("\\s+").length == 2)) {
            return CriteriaCheckResult.CriteriaCheckResultBuilder.aCriteriaCheckResultBuilder()
                    .satisfiesThisCriteria(false)
                    .errorMessage("A user story should contain a role part in format of \"Bir [persona] olarak\" or [persona] olarak\"")
                    .build();
        }
        boolean verbExist = true;
        for (SingleAnalysis item : analysis){
            if(item.formatLong().contains("Verb")){
                verbExist = true;
            }
        }
        if(verbExist){
            return CriteriaCheckResult.CriteriaCheckResultBuilder.aCriteriaCheckResultBuilder()
                    .satisfiesThisCriteria(false)
                    .errorMessage("A user story's role part should not contain a verb")
                    .build();
        }
        return CriteriaCheckResult.CriteriaCheckResultBuilder.aCriteriaCheckResultBuilder()
                .satisfiesThisCriteria(true)
                .errorMessage("")
                .build();
    }

    public CriteriaCheckResult checkGoalPart(UserStory userStory) {
        List<SingleAnalysis> analysis = zemberekProcessor.analyzeAndDisambiguate(userStory.getGoal());

        boolean directObjectExist = false;
        for (SingleAnalysis item : analysis) {
            if (item.formatLong().contains("Noun") && item.formatLong().contains("Acc")) {
                directObjectExist = true;
            }
        }
        if (!directObjectExist) {
            return CriteriaCheckResult.CriteriaCheckResultBuilder.aCriteriaCheckResultBuilder()
                    .satisfiesThisCriteria(false)
                    .errorMessage("A user story's mean part should contain a direct object")
                    .build();
        }
        return CriteriaCheckResult.CriteriaCheckResultBuilder.aCriteriaCheckResultBuilder()
                .satisfiesThisCriteria(true)
                .errorMessage("")
                .build();
    }
}
