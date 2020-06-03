package com.qualitydefectdetector.model;

import java.util.HashMap;
import java.util.Map;

public class SingleUserStoryReport {

    private Map<String, CriteriaCheckResult> criteriaCheckResults;
    private UserStory userStory;

    public SingleUserStoryReport(Map<String, CriteriaCheckResult> criteriaCheckResults, UserStory userStory) {
        this.criteriaCheckResults = criteriaCheckResults;
        this.userStory = userStory;
    }

    public Map<String, CriteriaCheckResult> getCriteriaCheckResults() {
        return criteriaCheckResults;
    }

    public void setCriteriaCheckResults(Map<String, CriteriaCheckResult> criteriaCheckResults) {
        this.criteriaCheckResults = criteriaCheckResults;
    }

    public UserStory getUserStory() {
        return userStory;
    }

    public void setUserStory(UserStory userStory) {
        this.userStory = userStory;
    }

    public static final class SingleUserStoryReportBuilder {
        private Map<String, CriteriaCheckResult> criteriaCheckResults;
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
