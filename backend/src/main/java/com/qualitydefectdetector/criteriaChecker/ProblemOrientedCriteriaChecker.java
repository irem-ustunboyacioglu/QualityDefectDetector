package com.qualitydefectdetector.criteriaChecker;

import com.qualitydefectdetector.model.CriteriaCheckResult;
import com.qualitydefectdetector.model.UserStory;
import com.qualitydefectdetector.nlpprocessor.ZemberekProcessor;
import org.springframework.stereotype.Component;
import zemberek.morphology.analysis.SingleAnalysis;

import java.util.List;

import static com.qualitydefectdetector.model.CriteriaCheckResult.CriteriaCheckResultBuilder.aCriteriaCheckResultBuilder;

@Component
public class ProblemOrientedCriteriaChecker {

    private final ZemberekProcessor zemberekProcessor;

    public ProblemOrientedCriteriaChecker(ZemberekProcessor zemberekProcessor) {
        this.zemberekProcessor = zemberekProcessor;
    }

    public CriteriaCheckResult checkIsProblemOriented(UserStory userStory) {

        List<SingleAnalysis> analysis = zemberekProcessor.analyzeAndDisambiguate(userStory.getGoal());
        for (SingleAnalysis item : analysis) {
            String itemAnalysis = item.formatLong();
            if (itemAnalysis.contains("ByDoingSo")) {
                return aCriteriaCheckResultBuilder()
                        .satisfiesThisCriteria(false)
                        .errorMessage("Uyarı! Bir kullanıcı hikayesi sadece sorunu belirtmelidir, çözümü içermemelidir.")
                        .build();
            }
        }

        return aCriteriaCheckResultBuilder()
                .satisfiesThisCriteria(true)
                .errorMessage("")
                .build();
    }
}
