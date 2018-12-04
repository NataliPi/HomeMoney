package com.natali_pi.home_money.add_spending;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.natali_pi.home_money.BaseAdapter;
import com.natali_pi.home_money.R;
import com.natali_pi.home_money.models.Money;
import com.natali_pi.home_money.models.SpendingComponent;
import com.natali_pi.home_money.utils.Currency;
import com.natali_pi.home_money.utils.DataBase;
import com.natali_pi.home_money.utils.SwipeableViewHolder;
import com.natali_pi.home_money.utils.TextPickerDialog;
import com.natali_pi.home_money.utils.views.DropdownView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Natali-Pi on 22.11.2017.
 */

public class ComponentsAdapter extends BaseAdapter<SpendingComponent> {

    private boolean isChangable;
    private TextPickerDialog dialog = null;

    public ComponentsAdapter(List<SpendingComponent> components, boolean isChangable) {
        super(components);
        this.isChangable = isChangable;
    }


    private OnSummChangedListener summListener;

    public void setSummListener(OnSummChangedListener summListener) {
        this.summListener = summListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(parent);
    }

    @Override
    protected void onItemSwiped(SwipeableViewHolder viewHolder) {

    }

    @Override
    protected void onItemNormal(SwipeableViewHolder viewHolder, final int position) {
        ViewHolder vh = (ViewHolder)viewHolder;
        vh.currency.setItems(Currency.getAsList());
        vh.currency.setDefaultFirst(DataBase.getInstance().getCurrentCurrency());
        vh.spendedName.setText(getItems().get(position).getName());
        vh.price.setText(getItems().get(position).getPrice().toString());
        if(isChangable) {
            vh.spendedName.addTextChangedListener(new ComponentNameWatcher(position) {
                @Override
                public void onTextChanged(CharSequence charSequence, int start , int i1, int i2) {
                    if(getPosition() >= getItems().size()){
                        return;
                    }
                    getItems().get(getPosition()).setName("" + charSequence);

                }
            });


            vh.price.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if(position >= getItems().size()){
                        return;
                    }
                    getItems().get(position).setPrice(new Money(vh.price.getText().toString(),vh.currency.getData()));
                    summListener.onSummChanged(getSumm());

                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        } else {
            vh.spendedName.setEnabled(false);
            vh.price.setEnabled(false);
            vh.currency.setEnabled(false);
        }


    }

    @Override
    protected void onItemDeleted(SpendingComponent item) {

    }

    interface OnSummChangedListener {
        void onSummChanged(Money summ);
    }

    private Money getSumm() {
        Money summ = new Money(0.0f);
        for (SpendingComponent component : getItems()) {
            summ = Money.sum(summ, component.getPrice());
        }
        return summ;
    }


    public void addSpendingComponent(View root) {
        getItems().add(new SpendingComponent());
        notifyDataSetChanged();
        super.hideKeyboard(root);
    }
    static class ViewHolder extends SwipeableViewHolder {
        public ViewHolder(ViewGroup parent) {
            super(parent);
        }

        EditText spendedName;
        EditText price;
        DropdownView currency;

        @Override
        public View prepareMainView() {
            View view = inflate(R.layout.item_component);
            spendedName  = (EditText) view.findViewById(R.id.spendedName);
            price = (EditText) view.findViewById(R.id.price);
            currency = view.findViewById(R.id.currency);
            return view;
        }
    }
    abstract class ComponentNameWatcher implements TextWatcher{
        int position;

        public ComponentNameWatcher(int position) {
            this.position = position;
        }

        public int getPosition() {
            return position;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public abstract void onTextChanged(CharSequence s, int start, int before, int count);

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}
