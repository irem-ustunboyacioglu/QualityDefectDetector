package com.qualitydefectdetector.criteriaChecker;

import com.qualitydefectdetector.model.CriteriaCheckResult;
import com.qualitydefectdetector.model.UserStory;
import com.qualitydefectdetector.nlpprocessor.ZemberekProcessor;
import org.springframework.stereotype.Service;
import zemberek.morphology.analysis.SingleAnalysis;

import java.util.List;

import static com.qualitydefectdetector.enums.UserStoryType.ROLE_GOAL;
import static com.qualitydefectdetector.model.CriteriaCheckResult.CriteriaCheckResultBuilder.aCriteriaCheckResultBuilder;


@Service
public class AtomicCriteriaChecker {

    public final ZemberekProcessor zemberekProcessor;

    public AtomicCriteriaChecker(ZemberekProcessor zemberekProcessor) {
        this.zemberekProcessor = zemberekProcessor;
    }

    public CriteriaCheckResult checkIsAtomic(UserStory userStory) {
        if (!userStory.getUserStoryType().equals(ROLE_GOAL.getDisplayName())) {
            List<SingleAnalysis> analysis = zemberekProcessor.analyzeAndDisambiguate(userStory.getReason());
            for (SingleAnalysis item : analysis) {
                String itemAnalysis = item.formatLong();
                if (itemAnalysis.contains("Conj")) {

                    return aCriteriaCheckResultBuilder()
                            .satisfiesThisCriteria(false)
                            .errorMessage("Bir kullanıcı hikayesi sadece bir özellikle ilgili bir gereksinim içermelidir.")
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
                        .errorMessage("Bir kullanıcı hikayesi sadece bir özellikle ilgili bir gereksinim içermelidir.")
                        .build();
            }
        }

        return aCriteriaCheckResultBuilder()
                .satisfiesThisCriteria(true)
                .errorMessage("")
                .build();
    }
}
