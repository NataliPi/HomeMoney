package com.natali_pi.home_money.add_spending;

import android.graphics.Bitmap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.natali_pi.home_money.BaseFragment;
import com.natali_pi.home_money.R;
import com.natali_pi.home_money.models.Money;
import com.natali_pi.home_money.models.Spending;
import com.natali_pi.home_money.utils.Currency;
import com.natali_pi.home_money.utils.DataBase;
import com.natali_pi.home_money.utils.views.DropdownView;
import com.squareup.picasso.Picasso;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickResult;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

/**
 * Created by Natali-Pi on 22.11.2017.
 */

public class SpendingFragment extends BaseFragment {
    private ComponentsAdapter componentsAdapter;
    private EditText name;
    private DropdownView date;
    private SpendingPresenter presenter = SpendingPresenter.getInstance();
    private DropdownView currency;
    private EditText price;
    private RecyclerView componentsHolder;
    boolean calculateSumm = true;
    private String id;
    @Override
    protected void resolveDaggerDependencies() {

    }

    @Override
    protected int contentView() {
        return R.layout.fragment_add_spending;
    }


    @Override
    protected View onCreateView(View root) {
        ImageView spendPhoto = (ImageView) root.findViewById(R.id.spendingPhoto);
        spendPhoto.setOnClickListener((v) -> {
            PickImageDialog.build(getBaseActivity().getPickSetup())
                    .setOnPickResult(new IPickResult() {
                        @Override
                        public void onPickResult(PickResult result) {

                            Bitmap bitmap = result.getBitmap();
                            if (bitmap != null) {
                                presenter.setSpendingPicture(bitmap);
                                spendPhoto.setImageBitmap(new CropCircleTransformation().transform(bitmap.copy(null, false)));
                            }

                        }
                    }).show(getBaseActivity());
        });

        name = (EditText) root.findViewById(R.id.name);
        price = (EditText) root.findViewById(R.id.price);
        currency = (DropdownView) root.findViewById(R.id.currency);
        currency.setItems(Currency.getAsList());
        currency.setDefaultFirst(DataBase.getInstance().getCurrentCurrency());

        date = (DropdownView) root.findViewById(R.id.date);
        date.setTodayDate();
        componentsHolder = (RecyclerView) root.findViewById(R.id.componentsHolder);
        getBaseActivity().setUpItemTouchHelper(componentsHolder);
        getBaseActivity().setUpAnimationDecoratorHelper(componentsHolder);
        componentsHolder.setLayoutManager(new LinearLayoutManager(getActivity()));
        Spending spending;
        if(getArguments() != null && (spending = (Spending)getArguments().getSerializable(DATA)) != null){
            id = spending.getId();
            name.setText(spending.getName());
            date.setDate(spending.getDate());

            price.setText(spending.getSum().toString());
            calculateSumm = false;

            Picasso.with(getActivity()).load(spending.getPhoto())
                    .transform(new CropCircleTransformation())
                    .placeholder(R.drawable.photo)
                    .into(spendPhoto);
            componentsAdapter = new ComponentsAdapter(spending.getComponents(), true);
        } else {
            componentsAdapter = new ComponentsAdapter(null, true);
        }
        componentsHolder.setAdapter(componentsAdapter);
        componentsAdapter.setSummListener((summ) -> {
            if (calculateSumm) {
                price.setText(summ.toString());
                currency.setDefaultFirst(summ.getCurrency().getValue());
            }
        });
        price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (charSequence.toString().equals("")) {
                    calculateSumm = true;
                } else {
                    if(charSequence.toString().contains(".") && charSequence.toString().split("\\.").length>1 &&charSequence.toString().split("\\.")[1].length() >=3){
                        String[] parts = charSequence.toString().split("\\.");
                        String result = parts[0] +"."+ parts[1].charAt(0) + parts[1].charAt(1);
                        price.setText(result);
                        if(start+1 < charSequence.length()) {
                            price.setSelection(start + 1);
                        }else {
                            price.setSelection(charSequence.length()-1);
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        price.setOnFocusChangeListener((v, d) -> {
            if (d) {
                calculateSumm = false;
            }
        });
        LinearLayout addComponent = (LinearLayout) root.findViewById(R.id.addComponent);
        addComponent.setOnClickListener((View view) -> {
            componentsAdapter.addSpendingComponent(root);
        });

        return root;
    }

    public Spending getSpending() {
        Spending spending = new Spending(name.getText().toString());
        spending.setId(id);
        spending.setDate(date.getData());
        if(!calculateSumm) {
            spending.setSumm(new Money(price.getText().toString(), currency.getData()));
        }
        spending.setComponents(componentsAdapter.getItems());
        return spending;
    }
}
