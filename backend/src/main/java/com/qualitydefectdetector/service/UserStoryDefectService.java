package com.qualitydefectdetector.service;

import com.qualitydefectdetector.criteriaChecker.AtomicCriteriaChecker;
import com.qualitydefectdetector.criteriaChecker.MinimalCriteriaChecker;
import com.qualitydefectdetector.criteriaChecker.UniformCriteriaChecker;
import com.qualitydefectdetector.criteriaChecker.WellFormedCriteriaChecker;
import com.qualitydefectdetector.enums.CriteriaType;
import com.qualitydefectdetector.model.CriteriaCheckResult;
import com.qualitydefectdetector.model.SetOfUserStoryReport;
import com.qualitydefectdetector.model.SingleUserStoryReport;
import com.qualitydefectdetector.model.UserStory;
import com.qualitydefectdetector.nlpprocessor.ZemberekProcessor;
import com.qualitydefectdetector.parser.UserStoryParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.qualitydefectdetector.enums.UserStoryType.UNDEFINED;
import static com.qualitydefectdetector.model.SetOfUserStoryReport.SetOfUserStoryReportBuilder.aSetOfUserStoryReport;
import static com.qualitydefectdetector.model.SingleUserStoryReport.SingleUserStoryReportBuilder.aSingleUserStoryReport;
import static java.util.stream.Collectors.toMap;

@Service
public class UserStoryDefectService {

    private final UserStoryParser userStoryParser;
    private final ZemberekProcessor zemberekProcessor;
    private final WellFormedCriteriaChecker wellFormedCriteriaChecker;
    private final AtomicCriteriaChecker atomicCriteriaChecker;
    private final MinimalCriteriaChecker minimalCriteriaChecker;
    private final UniformCriteriaChecker uniformCriteriaChecker;

    @Autowired
    public UserStoryDefectService(UserStoryParser userStoryParser,
                                  ZemberekProcessor zemberekProcessor,
                                  WellFormedCriteriaChecker wellFormedCriteriaChecker,
                                  AtomicCriteriaChecker atomicCriteriaChecker,
                                  MinimalCriteriaChecker minimalCriteriaChecker,
                                  UniformCriteriaChecker uniformCriteriaChecker) {
        this.userStoryParser = userStoryParser;
        this.zemberekProcessor = zemberekProcessor;
        this.wellFormedCriteriaChecker = wellFormedCriteriaChecker;
        this.atomicCriteriaChecker = atomicCriteriaChecker;
        this.minimalCriteriaChecker = minimalCriteriaChecker;
        this.uniformCriteriaChecker = uniformCriteriaChecker;
    }

    public SetOfUserStoryReport analyseMultipleUserStories(List<String> sentences) {
        SetOfUserStoryReport setOfUserStoryReport = checkCriteriaForSet(sentences);
        for(String sentence: sentences){
            setOfUserStoryReport.getSingleUserStoryReportList()
                    .add(analyseUserStory(sentence));
        }
        return setOfUserStoryReport;
    }

    public SetOfUserStoryReport checkCriteriaForSet(List<String> sentences){
        SetOfUserStoryReport setOfUserStoryReport = aSetOfUserStoryReport()
                .setCriteriaResults()
                .singleUserStoryReportList()
                .build();

        setOfUserStoryReport.getSetCriteriaResults()
                .put(CriteriaType.UNIFORM,uniformCriteriaChecker.checkUserStorySetIsUniform(sentences));

        return setOfUserStoryReport;
    }

    public SingleUserStoryReport analyseUserStory(String sentence) {
        SingleUserStoryReport singleUserStoryReport = aSingleUserStoryReport()
                .criteriaCheckResults()
                .build();

        CriteriaCheckResult wellFormedResult = checkWellFormedCriteria(sentence);
        singleUserStoryReport.getCriteriaCheckResults().put(CriteriaType.WELL_FORMED, wellFormedResult);

        CriteriaCheckResult atomicResult = checkAtomicCriteria(sentence);
        singleUserStoryReport.getCriteriaCheckResults().put(CriteriaType.ATOMIC, atomicResult);

        CriteriaCheckResult minimalResult = checkMinimalCriteria(sentence);
        singleUserStoryReport.getCriteriaCheckResults().put(CriteriaType.MINIMAL, minimalResult);

        CriteriaCheckResult fullSentenceResult = checkFullSentenceCriteria(sentence);
        singleUserStoryReport.getCriteriaCheckResults().put(CriteriaType.FULL_SENTENCE, fullSentenceResult);

        CriteriaCheckResult spellingResult = checkSpelling(sentence);
        singleUserStoryReport.getCriteriaCheckResults().put(CriteriaType.SPELLING, spellingResult);

        singleUserStoryReport.setUserStory(parse(sentence));

        return singleUserStoryReport;
    }

    public CriteriaCheckResult checkSpelling(String sentence) {
        List<String> words = userStoryParser.parseSentence(sentence);
        int numOfWrongSpelledWords = 0;
        for (String word : words) {
            if (!zemberekProcessor.checkSpelling(word)) {
                numOfWrongSpelledWords++;
            }
        }
        if (numOfWrongSpelledWords == 0) {
            return CriteriaCheckResult.CriteriaCheckResultBuilder.aCriteriaCheckResultBuilder()
                    .satisfiesThisCriteria(true)
                    .errorMessage("")
                    .build();
        }
        return CriteriaCheckResult.CriteriaCheckResultBuilder.aCriteriaCheckResultBuilder()
                .satisfiesThisCriteria(false)
                .errorMessage("This user story has " + numOfWrongSpelledWords + " word(s) wrongly spelled!")
                .build();
    }

    public Map<String, List<String>> suggestionForSpelling(String sentence) {
        List<String> words = userStoryParser.parseSentence(sentence);
        return words.stream()
                .collect(toMap(word -> word, zemberekProcessor::suggestionForSpelling))
                .entrySet().stream()
                .filter(map -> !(map.getValue()).isEmpty())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public UserStory parse(String sentence) {
        return userStoryParser.parseSentenceWithType(sentence);
    }

    public CriteriaCheckResult checkWellFormedCriteria(String sentence) {
        UserStory userStory = parse(sentence);

        if (userStory.getUserStoryType().equals(UNDEFINED)) {
            return notCheckedBecauseOfFormat();
        }

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

        if (userStory.getUserStoryType().equals(UNDEFINED)) {
            return notCheckedBecauseOfFormat();
        }
        return atomicCriteriaChecker.checkIsAtomic(userStory);
    }

    public CriteriaCheckResult checkMinimalCriteria(String sentence) {
        CriteriaCheckResult extraNoteResult = minimalCriteriaChecker.checkIfThereExistExtraNote(sentence);
        if (!extraNoteResult.isSatisfiesThisCriteria()) {
            return extraNoteResult;
        }

        CriteriaCheckResult isOneSentenceResult = minimalCriteriaChecker.checkIfItIsOneSentence(sentence);
        if (!isOneSentenceResult.isSatisfiesThisCriteria()) {
            return isOneSentenceResult;
        }

        return CriteriaCheckResult.CriteriaCheckResultBuilder.aCriteriaCheckResultBuilder()
                .satisfiesThisCriteria(true)
                .errorMessage("")
                .build();
    }

    public CriteriaCheckResult checkFullSentenceCriteria(String sentence) {
        return CriteriaCheckResult.CriteriaCheckResultBuilder.aCriteriaCheckResultBuilder()
                .satisfiesThisCriteria(true)
                .errorMessage("")
                .build();
    }

    private CriteriaCheckResult notCheckedBecauseOfFormat() {
        return CriteriaCheckResult.CriteriaCheckResultBuilder.aCriteriaCheckResultBuilder()
                .satisfiesThisCriteria(false)
                .errorMessage("This criteria has not been checked because of format error!")
                .build();
    }
}
