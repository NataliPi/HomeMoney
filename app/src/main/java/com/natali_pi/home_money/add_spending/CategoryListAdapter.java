package com.natali_pi.home_money.add_spending;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.natali_pi.home_money.BaseAdapter;
import com.natali_pi.home_money.R;
import com.natali_pi.home_money.models.Category;
import com.natali_pi.home_money.utils.ChangeCategoryDialog;
import com.natali_pi.home_money.utils.OneButtonDialog;
import com.natali_pi.home_money.utils.SwipeableViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Konstantyn Zakharchenko on 30.01.2018.
 */

public class CategoryListAdapter extends BaseAdapter<Category> {
    private SpendingPresenter presenter = SpendingPresenter.getInstance();
    private OnChooseListener listener;
    private ArrayList<RelativeLayout> holders = new ArrayList<>();
    boolean isEditable = false;

    //public CategoryAdapter(ArrayList<Category> categories, Context context, ) {
    public CategoryListAdapter(List<Category> items, OnChooseListener listener) {
        super(items);
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return super.getItemCount() + 1;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent);
    }

    @Override
    protected void onItemSwiped(SwipeableViewHolder viewHolder) {

    }

    @Override
    protected void onItemNormal(SwipeableViewHolder viewHolder, int position) {

        ViewHolder vh = (ViewHolder) viewHolder;
        holders.add(position, vh.holder);
        if (position < getItems().size()) {
            if (getItems().get(position).isHiden()) {
                vh.holder.setVisibility(View.GONE);
            }
            Picasso.with(vh.holder.getContext()).load(getItems().get(position).getPhoto())
                    .placeholder(vh.holder.getResources().getDrawable(R.drawable.camera))
                    .error(vh.holder.getResources().getDrawable(R.drawable.camera))
                    .into(vh.icon);

            if (getItems().get(position).isStandard()) {
                vh.text.setText(presenter.getStringResourceByName(getItems().get(position).getName()));
            } else {
                vh.text.setText(getItems().get(position).getName());
            }
            vh.holder.setOnClickListener((v) -> {
                clearBackgrounds();
                vh.holder.setBackgroundResource(R.color.yellow);
                listener.choosen(getItems().get(position));
            });
            if (id != null && getItems().get(position).getId().equals(id)) {
                vh.holder.setBackgroundResource(R.color.yellow);
                listener.choosen(getItems().get(position));
            }
        } else {
            vh.text.setText(R.string.add);
            vh.icon.setImageResource(R.drawable.plus);
            vh.holder.setOnClickListener((v) -> {
                clearBackgrounds();
                //vh.holder.setBackgroundResource(R.color.yellow);
                listener.choosen(null);
            });
        }
        onEditChangeListener.add((boolean isEditable) -> {
            if (position < getItems().size()) {
                if (isEditable) {

                    vh.edit.setVisibility(View.VISIBLE);
                    vh.edit.setOnClickListener(v -> new ChangeCategoryDialog(presenter.getView(), getItems().get(position), (icon, category) -> {
                        if (!getItems().get(position).getName().equals(category.getName()) || icon != null) {
                            presenter.updateCategory(icon, category);
                        }

                    }).show());
                    vh.delete.setVisibility(View.VISIBLE);
                    vh.delete.setOnClickListener((v) -> {
                        new OneButtonDialog(vh.delete.getContext(), OneButtonDialog.DIALOG_TYPE.MESSAGE_ONLY)
                                .setMessage(vh.delete.getContext().getString(R.string.delete_category, getItems().get(position).getName())).setOkListener((f) -> {
                            presenter.hideCategory(getItems().get(position).getId());
                        }).build();
                    });

                    vh.holder.setOnClickListener(null);

                } else {

                    vh.edit.setVisibility(View.GONE);
                    vh.delete.setVisibility(View.GONE);
                    vh.icon.setOnClickListener(null);

                    vh.holder.setOnClickListener((v) -> {
                        clearBackgrounds();
                        vh.holder.setBackgroundResource(R.color.yellow);
                        listener.choosen(getItems().get(position));
                    });
                }
            }
        });

        setEditable(isEditable);
    }

    public boolean isEditable() {
        return isEditable;
    }

    ArrayList<OnEditChangeListener> onEditChangeListener = new ArrayList<>();

    interface OnEditChangeListener {
        void onChange(boolean isEditable);
    }

    public void setEditable(boolean isEditable) {
        this.isEditable = isEditable;

        for (OnEditChangeListener onEditChangeListener : onEditChangeListener) {
            onEditChangeListener.onChange(isEditable);
        }
    }

    @Override
    protected void onItemDeleted(Category item) {
        // presenter.hideCategory(item.getId());

    }

    private String id;

    public void setCategory(String id) {
        this.id = id;
    }

    public interface OnChooseListener {
        void choosen(Category category);
    }

    public void clearBackgrounds() {
        for (RelativeLayout holder : holders) {
            holder.setBackgroundResource(R.color.white);
        }
    }


    static class ViewHolder extends SwipeableViewHolder {
        public ViewHolder(ViewGroup parent) {
            super(parent);
        }

        RelativeLayout holder;
        TextView text;
        ImageView icon;

        ImageView edit;
        ImageView delete;

        @Override
        public View prepareMainView() {
            View view = inflate(R.layout.item_category);

            edit = view.findViewById(R.id.edit);
            delete = view.findViewById(R.id.delete);
            holder = (RelativeLayout) view.findViewById(R.id.holder);
            text = (TextView) view.findViewById(R.id.text);
            icon = (ImageView) view.findViewById(R.id.icon);
            return view;
        }
    }
}
