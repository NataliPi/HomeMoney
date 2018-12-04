package com.natali_pi.home_money.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.natali_pi.home_money.R;
import com.natali_pi.home_money.models.Money;
import com.natali_pi.home_money.utils.views.DropdownView;

/**
 * Created by Konstantyn Zakharchenko on 09.04.2018.
 */

public class MoneyDialog extends OneButtonDialog {
    View view;
    /**
     * Easy to use tool for simple dialogs.
     *
     * @param ctx        any Android Context
     * @param dialogType type of the dialog, to show.
     */
    public MoneyDialog(Context ctx, DIALOG_TYPE dialogType) {
        super(ctx, dialogType);
    }

    @Override
    public OneButtonDialog build() {
        OneButtonDialog oneButtonDialog = super.build();
        view = LayoutInflater.from(getContext()).inflate(R.layout.money_dialog, layout, false);
        layout.addView(view);
        layout.removeView(layout.findViewWithTag(TAG_EDIT_TEXT));
        DropdownView currency = view.findViewById(R.id.currency);
        currency.setItems(Currency.getAsList());
        currency.setDefaultFirst(DataBase.getInstance().getCurrentCurrency());

        return oneButtonDialog;
    }

    public OneButtonDialog setOkListenerForMoney(OnMoneyEnteredListener okListener) {
        return super.setOkListener((v)->{
            if (okListener != null){
                EditText moneyText = view.findViewById(R.id.money);
                if(!moneyText.getText().toString().equals("")) {
                    String money = moneyText.getText().toString();
                    DropdownView currency = view.findViewById(R.id.currency);
                    okListener.onOk(new Money(money, currency.getData()));
                } else {
                    okListener.onOk(null);
                }
            }
        });
    }
    @Deprecated
    @Override
    public OneButtonDialog setOkListener(OKListener okListener) {
        return null;
    }
   public interface OnMoneyEnteredListener{
        void onOk(Money money);
    }
}
