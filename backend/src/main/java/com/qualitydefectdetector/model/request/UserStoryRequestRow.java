package com.qualitydefectdetector.model.request;

import com.qualitydefectdetector.enums.UserStoryType;

public class UserStoryRequestRow {

    private UserStoryType userStoryType;
    private String userStorySentence;
    private String role;
    private String goal;
    private String reason;

    public UserStoryType getUserStoryType() {
        return userStoryType;
    }

    public void setUserStoryType(UserStoryType userStoryType) {
        this.userStoryType = userStoryType;
    }

    public String getUserStorySentence() {
        return userStorySentence;
    }

    public void setUserStorySentence(String userStorySentence) {
        this.userStorySentence = userStorySentence;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }


    public static final class UserStoryRequestRowBuilder {
        private UserStoryType userStoryType;
        private String userStorySentence;
        private String role;
        private String goal;
        private String reason;

        private UserStoryRequestRowBuilder() {
        }

        public static UserStoryRequestRowBuilder anUserStoryRequestRow() {
            return new UserStoryRequestRowBuilder();
        }

        public UserStoryRequestRowBuilder userStorySentenceType(UserStoryType userStoryType) {
            this.userStoryType = userStoryType;
            return this;
        }

        public UserStoryRequestRowBuilder userStorySentence(String userStorySentence) {
            this.userStorySentence = userStorySentence;
            return this;
        }

        public UserStoryRequestRowBuilder role(String role) {
            this.role = role;
            return this;
        }

        public UserStoryRequestRowBuilder goal(String goal) {
            this.goal = goal;
            return this;
        }

        public UserStoryRequestRowBuilder reason(String reason) {
            this.reason = reason;
            return this;
        }

        public UserStoryRequestRow build() {
            UserStoryRequestRow userStoryRequestRow = new UserStoryRequestRow();
            userStoryRequestRow.setUserStoryType(userStoryType);
            userStoryRequestRow.setUserStorySentence(userStorySentence);
            userStoryRequestRow.setRole(role);
            userStoryRequestRow.setGoal(goal);
            userStoryRequestRow.setReason(reason);
            return userStoryRequestRow;
        }
    }
}
