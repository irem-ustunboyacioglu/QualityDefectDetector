package com.qualitydefectdetector.controller;

import com.qualitydefectdetector.model.CriteriaCheckResult;
import com.qualitydefectdetector.model.Report;
import com.qualitydefectdetector.model.UserStory;
import com.qualitydefectdetector.service.UserStoryDefectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user-story-defects")
public class UserStoryDefectController {

    private final UserStoryDefectService userStoryDefectService;

    @Autowired
    public UserStoryDefectController(UserStoryDefectService userStoryDefectService) {
        this.userStoryDefectService = userStoryDefectService;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/analyse")
    public Report analyse(@RequestParam String sentence) {
        return userStoryDefectService.analyseUserStory(sentence);
    }

    @GetMapping("/parse-format")
    public UserStory detect(@RequestParam String sentence) {
        return userStoryDefectService.parse(sentence);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/check-spelling")
    public List<List<String>> suggestionForSpelling(@RequestParam String sentence) {
        return userStoryDefectService.suggestionForSpelling(sentence);
    }

    @GetMapping("/well-formed")
    public CriteriaCheckResult checkWellFormedCriteria(@RequestParam String sentence) {
        return userStoryDefectService.checkWellFormedCriteria(sentence);
    }

    @GetMapping("/atomic")
    public CriteriaCheckResult checkAtomicCriteria(@RequestParam String sentence) {
        return userStoryDefectService.checkAtomicCriteria(sentence);
    }

    @GetMapping("/minimal")
    public CriteriaCheckResult checkMinimalCriteria(@RequestParam String sentence) {
        return userStoryDefectService.checkMinimalCriteria(sentence);
    }

}
