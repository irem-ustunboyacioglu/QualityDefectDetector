package com.qualitydefectdetector.controller;

import com.qualitydefectdetector.model.CriteriaCheckResult;
import com.qualitydefectdetector.model.SetOfUserStoryReport;
import com.qualitydefectdetector.model.SingleUserStoryReport;
import com.qualitydefectdetector.model.UserStory;
import com.qualitydefectdetector.reader.CsvReader;
import com.qualitydefectdetector.reader.TxtReader;
import com.qualitydefectdetector.reader.XlsxReader;
import com.qualitydefectdetector.service.UserStoryDefectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

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
    public SingleUserStoryReport analyse(@RequestParam String sentence) {
        return userStoryDefectService.analyseUserStory(sentence);
    }

    @GetMapping("/parse-format")
    public UserStory detect(@RequestParam String sentence) {
        return userStoryDefectService.parse(sentence);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/check-spelling")
    public Map<String, List<String>> suggestionForSpelling(@RequestParam String sentence) {
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

    @GetMapping("/problem-oriented")
    public CriteriaCheckResult checkProblemOrientedCriteria(@RequestParam String sentence) {
        return userStoryDefectService.checkProblemOrientedCriteria(sentence);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/analyse-file")
    public SetOfUserStoryReport analyseFile(@RequestParam("file") MultipartFile uploadingFile) throws IOException {
        String fileName = uploadingFile.getOriginalFilename();
        if(Pattern.matches(".*\\.txt",fileName)){
            List<String> userStories = TxtReader.readFile(uploadingFile);
            return userStoryDefectService.analyseMultipleUserStories(userStories);
        }
        else if(Pattern.matches(".*\\.csv",fileName)) {
            List<String> userStories = CsvReader.readFile(uploadingFile);
            return userStoryDefectService.analyseMultipleUserStories(userStories);
        }
        else if(Pattern.matches(".*\\.xlsx",fileName)) {
            List<String> userStories = XlsxReader.readFile(uploadingFile);
            return userStoryDefectService.analyseMultipleUserStories(userStories);
        }
        else{
            throw new IllegalArgumentException("Sadece txt, csv veya xlsx dosyaları yükleyebilirsiniz.");
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/analyse-multiple")
    public SetOfUserStoryReport analyseFile(@RequestParam("sentences") List<String> sentences) {
        return userStoryDefectService.analyseMultipleUserStories(sentences);
    }

}
