package com.qualitydefectdetector.model;


import com.qualitydefectdetector.enums.CriteriaType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SetOfUserStoryReport {

    private HashMap<CriteriaType, CriteriaCheckResult> setCriteriaResults;
    private List<SingleUserStoryReport> singleUserStoryReportList;

    public SetOfUserStoryReport(HashMap<CriteriaType, CriteriaCheckResult> setCriteriaResults, List<SingleUserStoryReport> singleUserStoryReportList) {
        this.setCriteriaResults = setCriteriaResults;
        this.singleUserStoryReportList = singleUserStoryReportList;
    }

    public HashMap<CriteriaType, CriteriaCheckResult> getSetCriteriaResults() {
        return setCriteriaResults;
    }

    public void setSetCriteriaResults(HashMap<CriteriaType, CriteriaCheckResult> setCriteriaResults) {
        this.setCriteriaResults = setCriteriaResults;
    }

    public List<SingleUserStoryReport> getSingleUserStoryReportList() {
        return singleUserStoryReportList;
    }

    public void setSingleUserStoryReportList(List<SingleUserStoryReport> singleUserStoryReportList) {
        this.singleUserStoryReportList = singleUserStoryReportList;
    }

    public static final class SetOfUserStoryReportBuilder {
        private HashMap<CriteriaType, CriteriaCheckResult> setCriteriaResults;
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
