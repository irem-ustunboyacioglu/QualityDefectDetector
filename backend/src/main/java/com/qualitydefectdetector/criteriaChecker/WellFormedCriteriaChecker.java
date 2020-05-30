package com.qualitydefectdetector.criteriaChecker;

import com.qualitydefectdetector.model.CriteriaCheckResult;
import com.qualitydefectdetector.model.UserStory;
import com.qualitydefectdetector.nlpprocessor.ZemberekProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zemberek.morphology.analysis.SingleAnalysis;

import java.util.List;

import static com.qualitydefectdetector.model.CriteriaCheckResult.CriteriaCheckResultBuilder.aCriteriaCheckResultBuilder;

@Service
public class WellFormedCriteriaChecker {

    public final ZemberekProcessor zemberekProcessor;

    @Autowired
    public WellFormedCriteriaChecker(ZemberekProcessor zemberekProcessor) {
        this.zemberekProcessor = zemberekProcessor;
    }

    public CriteriaCheckResult checkRolePart(UserStory userStory) {
        String rolePart = userStory.getRole().toLowerCase();
        List<SingleAnalysis> analysis = zemberekProcessor.analyzeAndDisambiguate(rolePart);

        if ((!rolePart.contains("bir") && rolePart.split("\\s+").length < 2) ||
                (rolePart.contains("bir") && rolePart.contains("olarak") && rolePart.split("\\s+").length == 2)) {
            return aCriteriaCheckResultBuilder()
                    .satisfiesThisCriteria(false)
                    .errorMessage("Bir kullanıcı hikayesinin rol bölümü \"Bir [persona] olarak\" veya \"[persona] olarak\" biçiminde olmalıdır.")
                    .build();
        }
        boolean verbExist = false;
        for (SingleAnalysis item : analysis){
            String itemAnalysis = item.formatLong();
            if(itemAnalysis.contains("→")){
                int index = itemAnalysis.indexOf("→");
                if(itemAnalysis.substring(index+1).contains("Verb")){
                    verbExist = true;
                }
            }else if(itemAnalysis.contains("Verb")){
                verbExist = true;
            }
        }
        if(verbExist){
            return aCriteriaCheckResultBuilder()
                    .satisfiesThisCriteria(false)
                    .errorMessage("Bir kullanıcı hikayesinin rol bölümü fiil içermemelidir.")
                    .build();
        }
        return aCriteriaCheckResultBuilder()
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
            return aCriteriaCheckResultBuilder()
                    .satisfiesThisCriteria(false)
                    .errorMessage("Bir kullanıcı hikayesinin amaç bölümü nesne içermelidir.")
                    .build();
        }
        return aCriteriaCheckResultBuilder()
                .satisfiesThisCriteria(true)
                .errorMessage("")
                .build();
    }
}
