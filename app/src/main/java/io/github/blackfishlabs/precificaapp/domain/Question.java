package io.github.blackfishlabs.precificaapp.domain;

public final class Question {

    private String id;
    private String question;
    private String answer;
    private double value;

    private Question() {
    }

    public Question(String id, String question, String answer, double value) {
        this.id = id;
        this.question = question;
        this.answer = answer;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
