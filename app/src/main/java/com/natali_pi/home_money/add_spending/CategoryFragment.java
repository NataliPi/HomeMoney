package com.natali_pi.home_money.add_spending;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.View;
import android.widget.ListView;

import com.natali_pi.home_money.BaseFragment;
import com.natali_pi.home_money.R;
import com.natali_pi.home_money.login.LoginActivity;
import com.natali_pi.home_money.models.Category;
import com.natali_pi.home_money.models.Spending;
import com.natali_pi.home_money.utils.ChangeCategoryDialog;
import com.natali_pi.home_money.utils.DataBase;
import com.natali_pi.home_money.utils.OneButtonDialog;

import java.util.ArrayList;

/**
 * Created by Natali-Pi on 24.11.2017.
 */

public class CategoryFragment extends BaseFragment {
    RecyclerView list;
    SpendingPresenter presenter;

    public void setPresenter(SpendingPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    protected void resolveDaggerDependencies() {

    }

    @Override
    protected int contentView() {
        return R.layout.fragment_category;
    }

    @Override
    protected View onCreateView(View root) {
        list = root.findViewById(R.id.list);
        ArrayList<Category> categories = DataBase.getInstance().getFamily().getCategories();
            setAdapter(categories);
        list.getRecycledViewPool().setMaxRecycledViews(0, 0);
        list.getRecycledViewPool().setMaxRecycledViews(1, 0);
        return root;
    }

    public void setAdapter(ArrayList<Category> categories) {
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        list.setAdapter(new CategoryListAdapter(categories, (category) -> {
            if (category != null) {
                presenter.setCategory(category);
            } else {
                new ChangeCategoryDialog(presenter.getView(), null,(icon, newCategory)->{
                        presenter.updateCategory(icon, newCategory);
                }).show();
                /*new OneButtonDialog(getActivity(), OneButtonDialog.DIALOG_TYPE.INPUT_ONLY)
                        .setTitle(getString(R.string.cattegory_addition))
                        .setEditTextHint(getString(R.string.enter_name))
                        .setOkListener((categoryName) -> {
                            presenter.setCategory(new Category(categoryName));
                        })
                        .build();*/
            }
        }));
        list.setHasFixedSize(true);

        Spending spending;
        if(getArguments() != null && (spending = (Spending)getArguments().getSerializable(DATA)) != null){
            ((CategoryListAdapter) list.getAdapter()).setCategory(spending.getCategory());
        }

        setSecondOptionButtonListener((v)->{
            ((CategoryListAdapter)list.getAdapter()).setEditable(true);
                getBaseActivity().hideSecondOption();
                getBaseActivity().setupOption(R.drawable.ok);

        });
    }


    public boolean isEditable() {
        return ((CategoryListAdapter)list.getAdapter()).isEditable();
    }

    public void finishEditable() {
        ((CategoryListAdapter)list.getAdapter()).setEditable(false);
        getBaseActivity().showSecondOption();
        getBaseActivity().setupOption(R.drawable.plus);
    }

    public void setEditable() {
        ((CategoryListAdapter)list.getAdapter()).setEditable(true);

    }
}
