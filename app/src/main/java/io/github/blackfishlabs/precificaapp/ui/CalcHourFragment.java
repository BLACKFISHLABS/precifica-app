package io.github.blackfishlabs.precificaapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputLayout;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.UUID;

import butterknife.BindView;
import butterknife.OnTextChanged;
import io.github.blackfishlabs.precificaapp.R;
import io.github.blackfishlabs.precificaapp.domain.ValuePerHour;
import io.github.blackfishlabs.precificaapp.helper.Constants;
import io.github.blackfishlabs.precificaapp.helper.MoneyTextWatcher;
import io.github.blackfishlabs.precificaapp.helper.NumberUtils;
import io.github.blackfishlabs.precificaapp.ui.common.BaseFragment;

import static java.util.Objects.requireNonNull;

public class CalcHourFragment extends BaseFragment {

    public static final String TAG = CalcHourFragment.class.getName();

    @BindView(R.id.input_layout_work_hour)
    TextInputLayout mInputLayoutWorkHour;
    @BindView(R.id.input_layout_week_days)
    TextInputLayout mInputLayoutWeekDays;
    @BindView(R.id.input_layout_money_focus)
    TextInputLayout mInputLayoutMoneyFocus;
    @BindView(R.id.calculated)
    TextView mCalculated;

    private ValuePerHour valuePerHour;

    @Override
    protected int provideContentViewResource() {
        return R.layout.fragment_calc_hour;
    }

    public static CalcHourFragment newInstance() {
        return new CalcHourFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle inState) {
        View view = super.onCreateView(inflater, container, inState);

        requireNonNull(mInputLayoutMoneyFocus.getEditText()).addTextChangedListener(new MoneyTextWatcher(mInputLayoutMoneyFocus.getEditText()));

        valuePerHour = new ValuePerHour()
                .withId(UUID.randomUUID().toString())
                .withWorkHour(NumberUtils.toInt(""))
                .withWeekDays(NumberUtils.toInt(""))
                .withMoneyFocus(BigDecimal.ZERO)
                .withValue(BigDecimal.ZERO);

        populate();

        return view;
    }

    private void populate() {
        requireNonNull(mInputLayoutWorkHour.getEditText()).setText(String.valueOf(valuePerHour.getWorkHour()));
        requireNonNull(mInputLayoutWeekDays.getEditText()).setText(String.valueOf(valuePerHour.getWeekDays()));
        requireNonNull(mInputLayoutMoneyFocus.getEditText()).setText(NumberUtils.currencyToString(valuePerHour.getMoneyFocus()));
    }

    @OnTextChanged(R.id.edit_text_work_hour)
    void onEditTextWorkHourChanged(CharSequence text) {
        valuePerHour.withWorkHour(NumberUtils.toInt(text.toString()));
        calculate();
    }

    @OnTextChanged(R.id.edit_text_week_days)
    void onEditTextWeekDaysChanged(CharSequence text) {
        valuePerHour.withWeekDays(NumberUtils.toInt(text.toString()));
        calculate();
    }

    @OnTextChanged(R.id.edit_text_money_focus)
    void onEditTextMoneyFocusChanged(CharSequence text) {
        valuePerHour.withMoneyFocus(NumberUtils.toCurrencyBigDecimal(text.toString()));
        calculate();
    }

    private void calculate() {
        int hourPerMonth = (valuePerHour.getWorkHour() * valuePerHour.getWeekDays()) * 4; // weeks

        if (hourPerMonth > 0) {
            BigDecimal total = valuePerHour.getMoneyFocus().divide(BigDecimal.valueOf(hourPerMonth), RoundingMode.HALF_UP);
            valuePerHour.withValue(total);
        } else {
            valuePerHour.withValue(BigDecimal.ZERO);
        }

        setCalculatedValue();
    }

    private void setCalculatedValue() {
        String valorString = NumberFormat.getCurrencyInstance(Constants.PT_BR_DEFAULT_LOCALE).format(valuePerHour.getValue());
        mCalculated.setText(valorString);
    }

}
