package com.qualitydefectdetector.model;

import com.qualitydefectdetector.enums.CriteriaType;

import java.util.HashMap;

public class Report {

    private HashMap<CriteriaType, CriteriaCheckResult> criteriaCheckResults;
    private UserStory userStory;

    public Report(HashMap<CriteriaType, CriteriaCheckResult> criteriaCheckResults, UserStory userStory) {
        this.criteriaCheckResults = criteriaCheckResults;
        this.userStory = userStory;
    }

    public HashMap<CriteriaType, CriteriaCheckResult> getCriteriaCheckResults() {
        return criteriaCheckResults;
    }

    public void setCriteriaCheckResults(HashMap<CriteriaType, CriteriaCheckResult> criteriaCheckResults) {
        this.criteriaCheckResults = criteriaCheckResults;
    }

    public UserStory getUserStory() {
        return userStory;
    }

    public void setUserStory(UserStory userStory) {
        this.userStory = userStory;
    }

    public static final class ReportBuilder {
        private HashMap<CriteriaType, CriteriaCheckResult> criteriaCheckResults;
        private UserStory userStory;

        private ReportBuilder() {
        }

        public static ReportBuilder aReport() {
            return new ReportBuilder();
        }

        public ReportBuilder criteriaCheckResults() {
            this.criteriaCheckResults = new HashMap<>();
            return this;
        }

        public ReportBuilder userStory(UserStory userStory) {
            this.userStory = userStory;
            return this;
        }

        public Report build() {
            return new Report(criteriaCheckResults, userStory);
        }
    }
}
