package com.qualitydefectdetector.model;

public class CriteriaCheckResult {
    private boolean satisfiesThisCriteria;
    private String errorMessage;

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
    public static final class CriteriaCheckResultBuilder {
        private boolean satisfiesThisCriteria;
        private String errorMessage;

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

        public CriteriaCheckResult build() {
            CriteriaCheckResult criteriaCheckResult = new CriteriaCheckResult();
            criteriaCheckResult.setSatisfiesThisCriteria(satisfiesThisCriteria);
            criteriaCheckResult.setErrorMessage(errorMessage);
            return criteriaCheckResult;
        }
    }

}
