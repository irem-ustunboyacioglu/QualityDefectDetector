package com.qualitydefectdetector.enums;

public enum UserStoryType {

    ROLE_GOAL_REASON(".*olarak.*(istiyor(um|uz)|ihtiyacım(ız)? var).*böylece.*","ROL_AMAÇ_FAYDA"),
    ROLE_REASON_GOAL(".*olarak.* için .*(istiyor(um|uz)|ihtiyacım(ız)? var)","ROL_FAYDA_AMAÇ"),
    ROLE_GOAL(".*olarak.*(istiyor(um|uz)|ihtiyacım(ız)? var)","ROL_AMAÇ"),
    UNDEFINED("","TANIMSIZ");

    private String formatRegex;
    private String displayName;

    UserStoryType(String formatRegex, String displayName) {
        this.formatRegex = formatRegex;
        this.displayName = displayName;
    }

    public String getFormatRegex() {
        return formatRegex;
    }

    public String getDisplayName() {
        return displayName;
    }
}
