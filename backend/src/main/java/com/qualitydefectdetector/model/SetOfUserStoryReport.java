package com.qualitydefectdetector.model;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SetOfUserStoryReport {

    private Map<String, CriteriaCheckResult> setCriteriaResults;
    private List<SingleUserStoryReport> singleUserStoryReportList;

    public SetOfUserStoryReport(Map<String, CriteriaCheckResult> setCriteriaResults, List<SingleUserStoryReport> singleUserStoryReportList) {
        this.setCriteriaResults = setCriteriaResults;
        this.singleUserStoryReportList = singleUserStoryReportList;
    }

    public Map<String, CriteriaCheckResult> getSetCriteriaResults() {
        return setCriteriaResults;
    }

    public void setSetCriteriaResults(Map<String, CriteriaCheckResult> setCriteriaResults) {
        this.setCriteriaResults = setCriteriaResults;
    }

    public List<SingleUserStoryReport> getSingleUserStoryReportList() {
        return singleUserStoryReportList;
    }

    public void setSingleUserStoryReportList(List<SingleUserStoryReport> singleUserStoryReportList) {
        this.singleUserStoryReportList = singleUserStoryReportList;
    }

    public static final class SetOfUserStoryReportBuilder {
        private Map<String, CriteriaCheckResult> setCriteriaResults;
        private List<SingleUserStoryReport> singleUserStoryReportList;

        private SetOfUserStoryReportBuilder() {
        }

        public static SetOfUserStoryReportBuilder aSetOfUserStoryReport() {
            return new SetOfUserStoryReportBuilder();
        }

        public SetOfUserStoryReportBuilder setCriteriaResults() {
            this.setCriteriaResults = new HashMap<>();
            return this;
        }

        public SetOfUserStoryReportBuilder singleUserStoryReportList() {
            this.singleUserStoryReportList = new ArrayList<>();
            return this;
        }

        public SetOfUserStoryReport build() {
            return new SetOfUserStoryReport(setCriteriaResults, singleUserStoryReportList);
        }
    }
}
