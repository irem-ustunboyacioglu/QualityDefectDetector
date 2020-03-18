package com.qualitydefectdetector.enums;

public enum UserStoryType {

    ROLE_GOAL_REASON(".*olarak.*(istiyor(um|uz)|ihtiyacım(ız)? var).*böylece.*"),
    ROLE_REASON_GOAL(".*olarak.* için .*(istiyor(um|uz)|ihtiyacım(ız)? var)"),
    ROLE_REASON(".*olarak.*(istiyor(um|uz)|ihtiyacım(ız)? var)");

    private String formatRegex;

    UserStoryType(String formatRegex) {
        this.formatRegex = formatRegex;
    }

    public String getFormatRegex() {
        return formatRegex;
    }
}
