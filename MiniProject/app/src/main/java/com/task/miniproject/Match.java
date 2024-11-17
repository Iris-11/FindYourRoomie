package com.task.miniproject;

public class Match {
    private User user;
    private int score;

    public Match(User user, int score){
        this.user=user;
        this.score=score;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
