package com.qualitydefectdetector.criteriaChecker;

import com.qualitydefectdetector.model.CriteriaCheckResult;
import com.qualitydefectdetector.model.UserStory;
import com.qualitydefectdetector.nlpprocessor.ZemberekProcessor;
import com.qualitydefectdetector.parser.UserStoryParser;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.stereotype.Service;
import zemberek.morphology.analysis.SingleAnalysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static com.qualitydefectdetector.enums.CriteriaType.CONFLICT_FREE;
import static com.qualitydefectdetector.model.CriteriaCheckResult.CriteriaCheckResultBuilder.aCriteriaCheckResultBuilder;

@Service
public class ConflictFreeCriteriaChecker {

    public final ZemberekProcessor zemberekProcessor;
    private final UserStoryParser userStoryParser;

    public ConflictFreeCriteriaChecker(ZemberekProcessor zemberekProcessor, UserStoryParser userStoryParser) {
        this.zemberekProcessor = zemberekProcessor;
        this.userStoryParser = userStoryParser;
    }

    public CriteriaCheckResult checkUserStorySetHasConflict(List<String> sentences) {
        HashMap<String, Integer> verbs = new HashMap<>();
        List<Pair<Integer,Integer>> conflictedSentenceNumbers = new ArrayList<>();
        List<Pair<String, String>> conflictedVerbs = new ArrayList<>();

        final List<UserStory> userStories = sentences.stream().map(this::parse).collect(Collectors.toList());

        for (int i = 0; i < userStories.size(); i++) {
            final UserStory userStory = userStories.get(i);
            List<SingleAnalysis> analysis = zemberekProcessor.analyzeAndDisambiguate(userStory.getGoal());
            for (SingleAnalysis item : analysis) {
                String itemAnalysis = item.formatLong();
                if (itemAnalysis.contains("Verb") && itemAnalysis.contains("+me:Neg")) {
                    final String turnedVerb = itemAnalysis.replace("+me:Neg", "");
                    if (verbs.containsKey(turnedVerb)) {
                        if (verbs.get(turnedVerb) != i + 1) {
                            conflictedVerbs.add(new Pair<>(item.getLemmas().get(item.getLemmas().size() - 1), turnedVerb.substring(1, turnedVerb.indexOf(":"))));
                            conflictedSentenceNumbers.add(new Pair<>(verbs.get(turnedVerb), i + 1));
                        }
                    } else {
                        verbs.put(itemAnalysis, i + 1);
                    }
                } else if (itemAnalysis.contains("Verb")) {
                    if (itemAnalysis.contains("|mek")) {
                        final String turnedVerb = itemAnalysis.replace("|mek", "+me:Neg|mek");
                        if (verbs.containsKey(turnedVerb)) {
                            if (verbs.get(turnedVerb) != i + 1) {
                                conflictedVerbs.add(new Pair<>(item.getLemmas().get(item.getLemmas().size() - 1), turnedVerb.substring(1, turnedVerb.indexOf("mek")) + "memek"));
                                conflictedSentenceNumbers.add(new Pair<>(verbs.get(turnedVerb), i + 1));
                            }
                        }
                    } else if (itemAnalysis.contains("|mak")) {
                        final String turnedVerb = itemAnalysis.replace("|mak", "+ma:Neg|mak");
                        if (verbs.containsKey(turnedVerb)) {
                            if (verbs.get(turnedVerb) != i + 1) {
                                conflictedVerbs.add(new Pair<>(item.getLemmas().get(item.getLemmas().size() - 1), turnedVerb.substring(1, turnedVerb.indexOf("mak")) + "mamak"));
                                conflictedSentenceNumbers.add(new Pair<>(verbs.get(turnedVerb), i + 1));
                            }
                        }
                    }
                    verbs.put(itemAnalysis, i + 1);
                }
            }
        }

        String conflictedSentences = "";
        int count = 0;
        for (Pair<Integer, Integer> pair : conflictedSentenceNumbers) {
            final String story1 = userStories.get(pair.a - 1).getUserStorySentence();
            final String story2 = userStories.get(pair.b - 1).getUserStorySentence();
            if (checkRemainingPartsEqual(story1, story2, conflictedVerbs.get(count))) {
                conflictedSentences += pair.toString() + " ";
            }
            count++;
        }

        if (!conflictedSentences.equals("")) {
            return aCriteriaCheckResultBuilder()
                    .satisfiesThisCriteria(false)
                    .errorMessage("Zıtlık bulunan cümleler: " + conflictedSentences)
                    .description(CONFLICT_FREE.getDescription())
                    .build();
        }

        return aCriteriaCheckResultBuilder()
                .satisfiesThisCriteria(true)
                .errorMessage("")
                .description(CONFLICT_FREE.getDescription())
                .build();
    }

    public boolean checkRemainingPartsEqual(String part1, String part2, Pair<String, String> conflictedVerbs) {
        if (part1.contains(conflictedVerbs.a)) {
            part1 = part1.replace(conflictedVerbs.a, "");
            part2 = part2.replace(conflictedVerbs.b, "");
            return part1.equals(part2);
        }
        part1 = part1.replace(conflictedVerbs.b, "");
        part2 = part2.replace(conflictedVerbs.a, "");
        return part1.equals(part2);
    }

    public UserStory parse(String sentence) {
        return userStoryParser.parseSentenceWithType(sentence);
    }
}
