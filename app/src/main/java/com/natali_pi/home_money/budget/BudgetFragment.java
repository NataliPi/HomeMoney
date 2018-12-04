package com.natali_pi.home_money.budget;

import android.text.InputType;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.natali_pi.home_money.BaseFragment;
import com.natali_pi.home_money.R;
import com.natali_pi.home_money.models.Money;
import com.natali_pi.home_money.utils.DataBase;
import com.natali_pi.home_money.utils.MoneyDialog;
import com.natali_pi.home_money.utils.OneButtonDialog;
import com.natali_pi.home_money.utils.PURPOSE;

/**
 * Created by Natali-Pi on 10.12.2017.
 */

public class BudgetFragment extends BaseFragment {

    @Override
    protected void resolveDaggerDependencies() {

    }

    @Override
    protected int contentView() {
        return R.layout.fragment_budget;
    }

    @Override
    protected View onCreateView(View root) {
        TextView plannedMoney = (TextView) root.findViewById(R.id.planned_money);

        plannedMoney.setText(prepareLimitText(DataBase.getInstance().getFamily().getLimit()));
        ImageView pen = (ImageView) root.findViewById(R.id.pen);
        pen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MoneyDialog) new MoneyDialog(getActivity(), OneButtonDialog.DIALOG_TYPE.INPUT_ONLY)
                        .setTitle(getString(R.string.enter_plan))
                        .setInputType(InputType.TYPE_CLASS_NUMBER)
                        .setEditTextHint(getString(R.string.budget))
                        .setCustomTextStyle(R.style.standard_margin)
                        .setCustomTitleStyle(R.style.dialog_title_style))
                        .setOkListenerForMoney(money -> {
                            plannedMoney.setText(prepareLimitText(money));
                            DataBase.getInstance().setFamillyLimit(money);
                        })
                        .build();
            }
        });
        TextView spendedMoneyView = (TextView) root.findViewById(R.id.spendedMoney);
        TextView planedSpendingsView = (TextView) root.findViewById(R.id.planedSpendings);
        TextView balanceMoney = (TextView) root.findViewById(R.id.balanceMoney);
        TextView balanceCurrency = (TextView) root.findViewById(R.id.balanceCurrency);
        TextView balanceError = (TextView) root.findViewById(R.id.balanceError);
        DataBase.getInstance().subscribeOnSpendings((family)->{
            Money spendedMoney = family.calculateSpendingsMoney(PURPOSE.SPENDED);
            Money planedSpendings = family.calculateSpendingsMoney(PURPOSE.PLANED);

            if (family.getLimit() != null) {
                Money balance = Money.substract(Money.substract(family.getLimit(), spendedMoney), planedSpendings);
                balanceMoney.setText(""+balance.getBill());
                balanceCurrency.setText(DataBase.getInstance().getCurrentCurrency());
                balanceMoney.setVisibility(View.VISIBLE);
                balanceCurrency.setVisibility(View.VISIBLE);
                balanceError.setVisibility(View.GONE);

            } else {
                balanceMoney.setVisibility(View.GONE);
                balanceCurrency.setVisibility(View.GONE);
                balanceError.setVisibility(View.VISIBLE);

            }
            spendedMoneyView.setText(spendedMoney.getPureMoney());
            planedSpendingsView.setText(planedSpendings.getPureMoney());


        });





        return root;
    }

    private String prepareLimitText(Money limit) {
        if (limit != null) {
            return limit.getPureMoney();
        } else {
            return getString(R.string.infinity);
        }
    }
}
