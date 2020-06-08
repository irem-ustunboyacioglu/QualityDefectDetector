package com.qualitydefectdetector.model;

public class CriteriaCheckResult {
    private boolean satisfiesThisCriteria;
    private String errorMessage;
    private String description;

    public boolean isSatisfiesThisCriteria() {
        return satisfiesThisCriteria;
    }

    public void setSatisfiesThisCriteria(boolean satisfiesThisCriteria) {
        this.satisfiesThisCriteria = satisfiesThisCriteria;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static final class CriteriaCheckResultBuilder {
        private boolean satisfiesThisCriteria;
        private String errorMessage;
        private String description;


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

        public CriteriaCheckResultBuilder description(String description) {
            this.description = description;
            return this;
        }

        public CriteriaCheckResult build() {
            CriteriaCheckResult criteriaCheckResult = new CriteriaCheckResult();
            criteriaCheckResult.setSatisfiesThisCriteria(satisfiesThisCriteria);
            criteriaCheckResult.setErrorMessage(errorMessage);
            criteriaCheckResult.setDescription(description);
            return criteriaCheckResult;
        }
    }
}
