package com.qualitydefectdetector.criteriaChecker;

import com.qualitydefectdetector.model.CriteriaCheckResult;
import com.qualitydefectdetector.model.UserStory;
import com.qualitydefectdetector.nlpprocessor.ZemberekProcessor;
import com.qualitydefectdetector.parser.UserStoryParser;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.qualitydefectdetector.enums.CriteriaType.UNIQUE;
import static com.qualitydefectdetector.model.CriteriaCheckResult.CriteriaCheckResultBuilder.aCriteriaCheckResultBuilder;

@Service
public class UniqueCriteriaChecker {

    public final ZemberekProcessor zemberekProcessor;
    private final UserStoryParser userStoryParser;

    public UniqueCriteriaChecker(ZemberekProcessor zemberekProcessor, UserStoryParser userStoryParser) {
        this.zemberekProcessor = zemberekProcessor;
        this.userStoryParser = userStoryParser;
    }

    public CriteriaCheckResult checkUserStorySetIsUnique(List<String> sentences) {
        HashMap<String, ArrayList<Integer>> userStoryMap = new HashMap<>();
        for (int i = 0; i < sentences.size(); i++) {
            UserStory userStory = parse(sentences.get(i));
            if (userStoryMap.get(userStory.getUserStorySentence()) != null) {
                ArrayList<Integer> listIds = userStoryMap.get(userStory.getUserStorySentence());
                listIds.add(i + 1);
                userStoryMap.put(userStory.getUserStorySentence(), listIds);
            } else {
                ArrayList<Integer> listIds = new ArrayList<>();
                listIds.add(i + 1);
                userStoryMap.put(userStory.getUserStorySentence(), listIds);
            }
        }

        boolean satisfies = true;
        String duplicateSentences = "";
        for (List list : userStoryMap.values()) {
            if (list.size() > 1) {
                satisfies = false;
                duplicateSentences += list.toString() + " ";
            }
        }
        if (!satisfies) {
            return aCriteriaCheckResultBuilder()
                    .satisfiesThisCriteria(false)
                    .errorMessage("Tekrarlanan cümleler: " + duplicateSentences)
                    .description(UNIQUE.getDescription())
                    .build();
        }

        return aCriteriaCheckResultBuilder()
                .satisfiesThisCriteria(true)
                .errorMessage("")
                .description(UNIQUE.getDescription())
                .build();
    }

    public UserStory parse(String sentence) {
        return userStoryParser.parseSentenceWithType(sentence);
    }
}
