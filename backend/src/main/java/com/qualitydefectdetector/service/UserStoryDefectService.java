package com.qualitydefectdetector.service;

import com.qualitydefectdetector.model.request.UserStoryRequestRow;
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

    @Autowired
    public UserStoryDefectService(UserStoryParser userStoryParser,
                                  ZemberekProcessor zemberekProcessor) {
        this.userStoryParser = userStoryParser;
        this.zemberekProcessor = zemberekProcessor;
    }

    public List<List<String>> checkSpells(String sentence) {
        List<String> words = userStoryParser.parseSentence(sentence);
        return words.stream()
                .map(zemberekProcessor::checkSpell)
                .collect(Collectors.toList());
    }

    public UserStoryRequestRow parse(String sentence){
        return userStoryParser.parseSentenceWithType(sentence);
    }
}
