package com.qualitydefectdetector.model;

public class CriteriaCheckResult {
    private boolean satisfiesThisCriteria;
    private String errorMessage;
    private boolean hasSuggestion;
    private String suggestion;

    public boolean isSatisfiesThisCriteria() {
        return satisfiesThisCriteria;
    }

    public void setSatisfiesThisCriteria(boolean satisfiesThisCriteria) {
        this.satisfiesThisCriteria = satisfiesThisCriteria;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean isHasSuggestion() {
        return hasSuggestion;
    }

    public void setHasSuggestion(boolean hasSuggestion) {
        this.hasSuggestion = hasSuggestion;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }


    public static final class CriteriaCheckResultBuilder {
        private boolean satisfiesThisCriteria;
        private String errorMessage;
        private boolean hasSuggestion;
        private String suggestion;

        private CriteriaCheckResultBuilder() {
        }

        public static CriteriaCheckResultBuilder aCriteriaCheckResultBuilder() {
            return new CriteriaCheckResultBuilder();
        }

        public CriteriaCheckResultBuilder satisfiesThisCriteria(boolean satisfiesThisCriteria) {
            this.satisfiesThisCriteria = satisfiesThisCriteria;
            return this;
        }

        public CriteriaCheckResultBuilder errorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
            return this;
        }

        public CriteriaCheckResultBuilder hasSuggestion(boolean hasSuggestion) {
            this.hasSuggestion = hasSuggestion;
            return this;
        }

        public CriteriaCheckResultBuilder suggestion(String suggestion) {
            this.suggestion = suggestion;
            return this;
        }

        public CriteriaCheckResult build() {
            CriteriaCheckResult criteriaCheckResult = new CriteriaCheckResult();
            criteriaCheckResult.setSatisfiesThisCriteria(satisfiesThisCriteria);
            criteriaCheckResult.setErrorMessage(errorMessage);
            criteriaCheckResult.setHasSuggestion(hasSuggestion);
            criteriaCheckResult.setSuggestion(suggestion);
            return criteriaCheckResult;
        }
    }
}
