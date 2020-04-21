package io.github.blackfishlabs.precificaapp.ui.calculate;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

import org.greenrobot.eventbus.Subscribe;

import java.text.NumberFormat;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.github.blackfishlabs.precificaapp.R;
import io.github.blackfishlabs.precificaapp.domain.Order;
import io.github.blackfishlabs.precificaapp.domain.Question;
import io.github.blackfishlabs.precificaapp.helper.AndroidUtils;
import io.github.blackfishlabs.precificaapp.ui.calculate.event.AddedQuestionEvent;
import io.github.blackfishlabs.precificaapp.ui.common.BaseFragment;

public class FinishStepFragment extends BaseFragment implements Step {

    @BindView(R.id.btn_share)
    Button btnShare;
    @BindView(R.id.btn_share_bf)
    Button btnShareBF;
    @BindView(R.id.btn_open)
    Button btnOpen;
    @BindView(R.id.btn_more)
    Button btnMore;
    @BindView(R.id.value)
    TextView valueCalculated;

    private String myData = "";
    private Order currentOrder;

    private Map<String, Question> selectedItems = Maps.newHashMap();

    public static FinishStepFragment newInstance() {
        return new FinishStepFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle inState) {
        View view = super.onCreateView(inflater, container, inState);

        currentOrder = new Order();
        currentOrder.setId("ORCA-".concat(AndroidUtils.randomAlphaNumeric(10)));

        return view;
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
        if (event != null && !event.getQuestions().isEmpty()) {
            selectedItems.putAll(event.getQuestions());
            currentOrder.setQuestions(Lists.newArrayList(selectedItems.values()));
        }
    }

    public String calculate() {
        double total = 0.0;
        for (Question question : currentOrder.getQuestions()) {
            total += question.getValue();
        }

        double valorMarkup = (total * 25.0) / 100;
        total += valorMarkup;

        Locale ptBr = new Locale("pt", "BR");
        String valorString = NumberFormat.getCurrencyInstance(ptBr).format(Math.round(total));
        valueCalculated.setText(valorString);
        return valorString;
    }

    private void details() {
        String items = "";
        myData = ""
                .concat(currentOrder.getId())
                .concat("\n")
                .concat("=========================")
                .concat("\n");

        Collections.sort(currentOrder.getQuestions(), (o1, o2) -> Integer.compare(Integer.parseInt(o1.getId()), Integer.parseInt(o2.getId())));
        for (Question question : currentOrder.getQuestions()) {
            items = items.concat(question.getQuestion())
                    .concat("\n")
                    .concat("● ".concat(question.getAnswer())
                            .concat("\n")
                            .concat("\n"));
        }

        myData = myData
                .concat(items)
                .concat("Total do Custo Estimado")
                .concat("\n")
                .concat("=========================")
                .concat("\n")
                .concat(calculate());
    }

    @OnClick(R.id.btn_share)
    void btnShare() {
        details();
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");

        share.putExtra(Intent.EXTRA_SUBJECT, "Custo Estimado");
        share.putExtra(Intent.EXTRA_TEXT, myData);

        startActivity(Intent.createChooser(share, "Compartilhar Meu Custo Estimado!"));
    }

    @OnClick(R.id.btn_share_bf)
    void btnShareBF() {
        details();
        navigate().toWebSite("https://wa.me/5565996168022?text=" + myData);
    }

    @OnClick(R.id.btn_open)
    void btnOpen() {
        details();
        AlertDialog.Builder alert = new AlertDialog.Builder(getHostActivity());

        alert.setTitle("Custo Estimado");
        alert.setIcon(R.drawable.ic_content_paste);

        alert.setMessage(myData);

        alert.setCancelable(false);
        alert
                .setPositiveButton("FECHAR", (dialogInterface, i) -> {
                });

        AlertDialog alertDialog = alert.create();
        alertDialog.show();

        TextView textView = alertDialog.findViewById(android.R.id.message);
    }

    @OnClick(R.id.btn_more)
    void btnMore() {
        AlertDialog.Builder alert = new AlertDialog.Builder(getHostActivity());

        alert.setTitle("Informações");
        alert.setIcon(R.drawable.ic_content_paste);

        alert.setMessage("O desenvolvimento de aplicativos para celular em média consome de 100 a 500 horas para ser feito.\n\n" +
                "Tem o custo aproximado entre R$ 3 mil e R$ 20 mil.\n\n" +
                "Aplicativos mais complexos podem custar entre R$ 20 mil e R$ 50 mil.\n\n" +
                "Um projeto simples demora de 2 a 4 meses e médios/grandes projetos de 4 meses a 12 meses.\n\n" +
                "Mesmo em um projeto de 100 horas de trabalho, não é possível resolver em um mês, " +
                "pois as etapas exigem interação de pessoas distintas e isso impacta na sequência contínua do projeto.\n\n" +
                "Depois de pronto, o aplicativo ainda terá outros custos: manutenção, evolução e hospedagem em um servidor.\n\n" +
                "O custo para contratar esses serviços inicia em aproximadamente R$ 250,00 mensais.\n\n" +
                "No caso de aplicativos complexos, ou com grande volume de uso e que vão exigir servidores mais potentes, " +
                "o custo total pode variar entre R$ 500,00 a R$ 5 mil por mês.\n\n" +
                "Se o projeto não der certo, dificilmente encontrará outro fornecedor disponível a continuar o projeto, " +
                "pois é muito custoso e as vezes inviável trabalhar sobre algo feito da forma errada, ou com tecnologias que não são as escolhidas pelo desenvolvedor.\n\n" +
                "É fundamental sentir confiança total do fornecedor antes da contratação.\n\n\n" +
                "Lembrem-se, um aplicativo é um software, não um site.");

        alert.setCancelable(false);
        alert
                .setPositiveButton("FECHAR", (dialogInterface, i) -> {
                });

        AlertDialog alertDialog = alert.create();
        alertDialog.show();

        TextView textView = alertDialog.findViewById(android.R.id.message);
    }

    @Override
    public VerificationError verifyStep() {
        return null;
    }

    @Override
    public void onSelected() {
        calculate();
    }

    @Override
    public void onError(@NonNull VerificationError error) {

    }

    @Override
    protected int provideContentViewResource() {
        return R.layout.fragment_finish;
    }
}
