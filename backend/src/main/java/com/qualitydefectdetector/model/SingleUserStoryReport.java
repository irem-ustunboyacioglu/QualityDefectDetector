package com.qualitydefectdetector.model;

import com.qualitydefectdetector.enums.CriteriaType;

import java.util.HashMap;

public class SingleUserStoryReport {

    private HashMap<CriteriaType, CriteriaCheckResult> criteriaCheckResults;
    private UserStory userStory;

    public SingleUserStoryReport(HashMap<CriteriaType, CriteriaCheckResult> criteriaCheckResults, UserStory userStory) {
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

    public static final class SingleUserStoryReportBuilder {
        private HashMap<CriteriaType, CriteriaCheckResult> criteriaCheckResults;
        private UserStory userStory;

        private SingleUserStoryReportBuilder() {
        }

        public static SingleUserStoryReportBuilder aSingleUserStoryReport() {
            return new SingleUserStoryReportBuilder();
        }

        public SingleUserStoryReportBuilder criteriaCheckResults() {
            this.criteriaCheckResults = new HashMap<>();
            return this;
        }

        public SingleUserStoryReportBuilder userStory(UserStory userStory) {
            this.userStory = userStory;
            return this;
        }

        public SingleUserStoryReport build() {
            return new SingleUserStoryReport(criteriaCheckResults, userStory);
        }
    }
}
