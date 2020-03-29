package com.qualitydefectdetector.criteriaChecker;

import com.qualitydefectdetector.enums.UserStoryType;
import com.qualitydefectdetector.model.CriteriaCheckResult;
import com.qualitydefectdetector.model.UserStory;
import com.qualitydefectdetector.nlpprocessor.ZemberekProcessor;
import org.springframework.stereotype.Service;
import zemberek.morphology.analysis.SingleAnalysis;

import java.util.List;

import static com.qualitydefectdetector.model.CriteriaCheckResult.CriteriaCheckResultBuilder.aCriteriaCheckResultBuilder;


@Service
public class AtomicCriteriaChecker {

    public final ZemberekProcessor zemberekProcessor;

    public AtomicCriteriaChecker(ZemberekProcessor zemberekProcessor) {
        this.zemberekProcessor = zemberekProcessor;
    }

    public CriteriaCheckResult checkIsAtomic(UserStory userStory) {
        if (userStory.getUserStoryType() != UserStoryType.ROLE_GOAL) {
            List<SingleAnalysis> analysis = zemberekProcessor.analyzeAndDisambiguate(userStory.getReason());
            for (SingleAnalysis item : analysis) {
                String itemAnalysis = item.formatLong();
                if (itemAnalysis.contains("Conj")) {

                    return aCriteriaCheckResultBuilder()
                            .satisfiesThisCriteria(false)
                            .errorMessage("A user story should explain a requirement for exactly one feature.")
                            .build();
                }
            }
        }

        List<SingleAnalysis> analysis = zemberekProcessor.analyzeAndDisambiguate(userStory.getGoal());
        for (SingleAnalysis item : analysis) {
            String itemAnalysis = item.formatLong();
            if (itemAnalysis.contains("Conj")) {
                return aCriteriaCheckResultBuilder()
                        .satisfiesThisCriteria(false)
                        .errorMessage("A user story should explain a requirement for exactly one feature.")
                        .build();
            }
        }

        return aCriteriaCheckResultBuilder()
                .satisfiesThisCriteria(true)
                .errorMessage("")
                .build();
    }
}
