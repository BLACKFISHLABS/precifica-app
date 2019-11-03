package io.github.blackfishlabs.precificaapp.ui.calculate;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.stepstone.stepper.VerificationError;

import io.github.blackfishlabs.precificaapp.R;
import io.github.blackfishlabs.precificaapp.ui.common.BaseStepperActivity;

public class CalculateActivity extends BaseStepperActivity {

    @Override
    protected void provideSteps() {
        mStepperAdapter.addStep(QualityLevelFragment.newInstance(), "Qualidade do Aplicativo");
        mStepperAdapter.addStep(TypeApplicationFragment.newInstance(), "Tipo do Aplicativo");
        mStepperAdapter.addStep(DesignApplicationFragment.newInstance(), "Aparência do Aplicativo");
        mStepperAdapter.addStep(FinancialFeedbackFragment.newInstance(), "Retorno Financeiro");
        mStepperAdapter.addStep(LoginApplicationFragment.newInstance(), "Autenticação de Usuário");
        mStepperAdapter.addStep(WebSiteIntegrationFragment.newInstance(), "Integração com Pagina Web");
        mStepperAdapter.addStep(AdminPanelFragment.newInstance(), "Painel Administrativo");
        mStepperAdapter.addStep(InternationalApplicationFragment.newInstance(), "Internacionalização");
        mStepperAdapter.addStep(ProjectStatusFragment.newInstance(), "Status Atual do Projeto");
        mStepperAdapter.addStep(FinishStepFragment.newInstance(), "Orçamento");

        mStepperLayout.setOffscreenPageLimit(mStepperAdapter.getCount());
    }

    @Override
    protected int provideContentViewResource() {
        return R.layout.calculate_activity;
    }

    @Override
    protected void onCreate(@Nullable Bundle inState) {
        super.onCreate(inState);

        setAsInitialFlowActivity();
        mStepperLayout.setCurrentStepPosition(0);
    }

    @Override
    public void onCompleted(View completeButton) {
        finish();
    }

    @Override
    public void onError(VerificationError verificationError) {

    }
}
