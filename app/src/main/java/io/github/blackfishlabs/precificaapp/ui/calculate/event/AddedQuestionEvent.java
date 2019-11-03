package io.github.blackfishlabs.precificaapp.ui.calculate.event;

import java.util.Map;

import io.github.blackfishlabs.precificaapp.domain.Question;

public class AddedQuestionEvent {

    private Map<String, Question> mQuestions;

    public AddedQuestionEvent(Map<String, Question> questionMap) {
        mQuestions = questionMap;
    }

    public static AddedQuestionEvent newEvent(final Map<String, Question> questions) {
        return new AddedQuestionEvent(questions);
    }

    public Map<String, Question> getQuestions() {
        return mQuestions;
    }
}
