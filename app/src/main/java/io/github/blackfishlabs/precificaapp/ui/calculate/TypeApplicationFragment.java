package io.github.blackfishlabs.precificaapp.ui.calculate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.snackbar.Snackbar;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import io.github.blackfishlabs.precificaapp.R;
import io.github.blackfishlabs.precificaapp.domain.Question;
import io.github.blackfishlabs.precificaapp.ui.calculate.event.AddedQuestionEvent;
import io.github.blackfishlabs.precificaapp.ui.common.BaseFragment;

public class TypeApplicationFragment extends BaseFragment implements Step {

    @BindView(R.id.option_2_1)
    LinearLayout op1;
    @BindView(R.id.quest_answer_2_1)
    TextView answer1;

    @BindView(R.id.option_2_2)
    LinearLayout op2;
    @BindView(R.id.quest_answer_2_2)
    TextView answer2;

    @BindView(R.id.option_2_3)
    LinearLayout op3;
    @BindView(R.id.quest_answer_2_4)
    TextView answer3;

    @BindView(R.id.question_2)
    TextView question2;

    private Map<String, Question> selectedItems = Maps.newHashMap();
    private Question selectedItem;
    private static final String QUESTION_ID = "2";

    public static TypeApplicationFragment newInstance() {
        return new TypeApplicationFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle inState) {
        View view = super.onCreateView(inflater, container, inState);

        getQuestionsState();

        op1.setOnClickListener(v -> {
            changeQuestion(op1, Lists.newArrayList(op2, op3));
            selectedItem = new Question(QUESTION_ID,
                    question2.getText().toString(),
                    answer1.getText().toString(), 5000.00);
        });

        op2.setOnClickListener(v -> {
            changeQuestion(op2, Lists.newArrayList(op1, op3));
            selectedItem = new Question(QUESTION_ID,
                    question2.getText().toString(),
                    answer2.getText().toString(), 10000.00);

        });

        op3.setOnClickListener(v -> {
            changeQuestion(op3, Lists.newArrayList(op1, op2));
            selectedItem = new Question(QUESTION_ID,
                    question2.getText().toString(),
                    answer3.getText().toString(), 15000.00);
        });

        return view;
    }

    private void changeQuestion(LinearLayout selected, List<LinearLayout> unselected) {
        selected.setBackgroundResource(R.drawable.bg_rounded_selected);
        for (LinearLayout linearLayout : unselected) {
            linearLayout.setBackgroundResource(R.drawable.bg_rounded);
        }
    }

    private void getQuestionsState() {
        AddedQuestionEvent event = eventBus().getStickyEvent(AddedQuestionEvent.class);
        selectedItems.clear();
        if (event != null && !event.getQuestions().isEmpty()) {
            selectedItems.putAll(event.getQuestions());
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        eventBus().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        eventBus().unregister(this);
    }

    @Subscribe
    public void onMessageEvent(AddedQuestionEvent event) {
    }

    @Override
    public VerificationError verifyStep() {
        if (selectedItem != null) {
            selectedItems.remove(selectedItem.getId());
            selectedItems.put(selectedItem.getId(), selectedItem);
            eventBus().post(AddedQuestionEvent.newEvent(selectedItems));

            return null;
        } else {
            return new VerificationError(getString(R.string.select_one_answer));
        }
    }

    @Override
    public void onSelected() {

    }

    @Override
    public void onError(@NonNull VerificationError error) {
        Snackbar.make(getView(), error.getErrorMessage(), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    protected int provideContentViewResource() {
        return R.layout.fragment_type_app;
    }
}
