package io.github.blackfishlabs.precificaapp.domain;

import java.math.BigDecimal;

public final class ValuePerHour {
    private String id;
    private Integer workHour;
    private Integer weekDays;
    private BigDecimal moneyFocus;
    private BigDecimal value;

    public String getId() {
        return id;
    }

    public ValuePerHour withId(final String id) {
        this.id = id;
        return this;
    }

    public Integer getWorkHour() {
        return workHour;
    }

    public ValuePerHour withWorkHour(final Integer workHour) {
        this.workHour = workHour;
        return this;
    }

    public Integer getWeekDays() {
        return weekDays;
    }

    public ValuePerHour withWeekDays(final Integer weekDays) {
        this.weekDays = weekDays;
        return this;
    }

    public BigDecimal getMoneyFocus() {
        return moneyFocus;
    }

    public ValuePerHour withMoneyFocus(final BigDecimal moneyFocus) {
        this.moneyFocus = moneyFocus;
        return this;
    }

    public BigDecimal getValue() {
        return value;
    }

    public ValuePerHour withValue(final BigDecimal value) {
        this.value = value;
        return this;
    }
}
