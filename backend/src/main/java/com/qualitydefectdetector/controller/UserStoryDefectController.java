package com.qualitydefectdetector.controller;

import com.qualitydefectdetector.service.UserStoryDefectService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/check-spelling")
    public List<List<String>> checkSpells(@RequestParam String sentence) {
        return userStoryDefectService.checkSpells(sentence);
    }
}
