package com.qualitydefectdetector.model.request;

import com.qualitydefectdetector.enums.UserStoryType;

public class UserStory {

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

    public static final class UserStoryBuilder {
        private UserStoryType userStoryType;
        private String userStorySentence;
        private String role;
        private String goal;
        private String reason;

        private UserStoryBuilder() {
        }

        public static UserStoryBuilder aUserStory() {
            return new UserStoryBuilder();
        }

        public UserStoryBuilder userStoryType(UserStoryType userStoryType) {
            this.userStoryType = userStoryType;
            return this;
        }

        public UserStoryBuilder userStorySentence(String userStorySentence) {
            this.userStorySentence = userStorySentence;
            return this;
        }

        public UserStoryBuilder role(String role) {
            this.role = role;
            return this;
        }

        public UserStoryBuilder goal(String goal) {
            this.goal = goal;
            return this;
        }

        public UserStoryBuilder reason(String reason) {
            this.reason = reason;
            return this;
        }

        public UserStory build() {
            UserStory userStory = new UserStory();
            userStory.setUserStoryType(userStoryType);
            userStory.setUserStorySentence(userStorySentence);
            userStory.setRole(role);
            userStory.setGoal(goal);
            userStory.setReason(reason);
            return userStory;
        }
    }
}
