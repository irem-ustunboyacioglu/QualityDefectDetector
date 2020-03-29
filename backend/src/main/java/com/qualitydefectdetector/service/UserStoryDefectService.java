package com.qualitydefectdetector.service;

import com.qualitydefectdetector.criteriaChecker.AtomicCriteriaChecker;
import com.qualitydefectdetector.criteriaChecker.MinimalCriteriaChecker;
import com.qualitydefectdetector.criteriaChecker.WellFormedCriteriaChecker;
import com.qualitydefectdetector.model.CriteriaCheckResult;
import com.qualitydefectdetector.model.UserStory;
import com.qualitydefectdetector.nlpprocessor.ZemberekProcessor;
import com.qualitydefectdetector.parser.UserStoryParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserStoryDefectService {

    private final UserStoryParser userStoryParser;
    private final ZemberekProcessor zemberekProcessor;
    private final WellFormedCriteriaChecker wellFormedCriteriaChecker;
    private final AtomicCriteriaChecker atomicCriteriaChecker;
    private final MinimalCriteriaChecker minimalCriteriaChecker;

    @Autowired
    public UserStoryDefectService(UserStoryParser userStoryParser,
                                  ZemberekProcessor zemberekProcessor,
                                  WellFormedCriteriaChecker wellFormedCriteriaChecker,
                                  AtomicCriteriaChecker atomicCriteriaChecker,
                                  MinimalCriteriaChecker minimalCriteriaChecker) {
        this.userStoryParser = userStoryParser;
        this.zemberekProcessor = zemberekProcessor;
        this.wellFormedCriteriaChecker = wellFormedCriteriaChecker;
        this.atomicCriteriaChecker = atomicCriteriaChecker;
        this.minimalCriteriaChecker = minimalCriteriaChecker;
    }


    public List<List<String>> checkSpells(String sentence) {
        List<String> words = userStoryParser.parseSentence(sentence);
        return words.stream()
                .map(zemberekProcessor::checkSpell)
                .collect(Collectors.toList());
    }

    public UserStory parse(String sentence) {
        return userStoryParser.parseSentenceWithType(sentence);
    }

    public CriteriaCheckResult checkWellFormedCriteria(String sentence) {
        UserStory userStory = parse(sentence);

        CriteriaCheckResult rolePartResult = wellFormedCriteriaChecker.checkRolePart(userStory);
        if (!rolePartResult.isSatisfiesThisCriteria()) {
            return rolePartResult;
        }

        CriteriaCheckResult meansPartResult = wellFormedCriteriaChecker.checkGoalPart(userStory);
        if (!meansPartResult.isSatisfiesThisCriteria()) {
            return meansPartResult;
        }

        return CriteriaCheckResult.CriteriaCheckResultBuilder.aCriteriaCheckResultBuilder()
                .satisfiesThisCriteria(true)
                .errorMessage("")
                .build();
    }

    public CriteriaCheckResult checkAtomicCriteria(String sentence) {
        UserStory userStory = parse(sentence);
        return atomicCriteriaChecker.checkIsAtomic(userStory);
    }
    public CriteriaCheckResult checkMinimalCriteria(String sentence) {
        CriteriaCheckResult extraNoteResult = minimalCriteriaChecker.checkIfThereExistExtraNote(sentence);
        if(!extraNoteResult.isSatisfiesThisCriteria()){
            return extraNoteResult;
        }

        CriteriaCheckResult isOneSentenceResult = minimalCriteriaChecker.checkIfItIsOneSentence(sentence);
        if(!isOneSentenceResult.isSatisfiesThisCriteria()){
            return isOneSentenceResult;
        }

        return CriteriaCheckResult.CriteriaCheckResultBuilder.aCriteriaCheckResultBuilder()
                .satisfiesThisCriteria(true)
                .errorMessage("")
                .build();
    }
}
