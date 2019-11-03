package io.github.blackfishlabs.precificaapp.domain;

import com.google.common.collect.Lists;

import java.util.List;

public final class Order {

    private String id;
    private List<Question> questions = Lists.newArrayList();

    public Order() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
