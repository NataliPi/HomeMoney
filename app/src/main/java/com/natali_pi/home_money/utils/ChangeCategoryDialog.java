package com.natali_pi.home_money.utils;

import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.natali_pi.home_money.BaseActivity;
import com.natali_pi.home_money.R;
import com.natali_pi.home_money.models.Category;
import com.squareup.picasso.Picasso;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.enums.EPickType;
import com.vansuita.pickimage.listeners.IPickResult;

import java.util.List;


public class ChangeCategoryDialog extends Dialog {

    private Category category;
    private Bitmap newPhoto = null;
    private BaseActivity context;
    private OnDoneListener onDoneListener;
    public ChangeCategoryDialog(BaseActivity context, Category category, OnDoneListener onDoneListener) {
        super(context);
        this.category = category;
        this.context = context;
        this.onDoneListener = onDoneListener;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_change_category);
        ImageView icon = findViewById(R.id.icon);
        if (category == null){
            category = new Category(null);

        }
        Picasso.with(getContext()).load(category.getPhoto())
                .placeholder(getContext().getResources().getDrawable(R.drawable.camera))
                .error(getContext().getResources().getDrawable(R.drawable.camera))
                .into(icon);
        icon.setOnClickListener((v)->{


        PickImageDialog.build(new PickSetup()
                        .setTitle(getContext().getString(R.string.download_image))
                        .setTitleColor(getContext().getResources().getColor(R.color.darkViolet))
                        .setBackgroundColor(getContext().getResources().getColor(R.color.white))
                        //.setProgressText("progresstext")
                        .setCancelText(getContext().getString(R.string.cancel))
                        .setCancelTextColor(getContext().getResources().getColor(R.color.darkViolet))
                        //.setButtonTextColor(yourColor)
                        //.setDimAmount(yourFloat)
                        //.setFlip(true)
                        //.setMaxSize(500)
                        .setPickTypes(EPickType.GALLERY, EPickType.CAMERA)
                        //.setCameraButtonText(yourText)
                        //.setGalleryButtonText(yourText)
                        .setIconGravity(Gravity.LEFT)
                        //.setButtonOrientation(LinearLayoutCompat.VERTICAL)
                        .setSystemDialog(false))
                .setOnPickResult(new IPickResult() {
                    @Override
                    public void onPickResult(PickResult result) {
                        icon.setImageBitmap(result.getBitmap());
                        newPhoto = result.getBitmap();
                    }
                }).show(context);
        });

        EditText categoryName = findViewById(R.id.categoryName);
        categoryName.setText(category.getName());
        TextView cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(v->dismiss());
        TextView ok = findViewById(R.id.ok);
        ok.setOnClickListener((v)->{
            Category newCategory = new Category(categoryName.getText().toString());
            newCategory.setId(category.getId());
            newCategory.setPhoto(category.getRawPhoto());
            if (onDoneListener != null){
                onDoneListener.onDone(newPhoto, newCategory);
            }
            dismiss();
        });
    }

    public interface OnDoneListener{
        void onDone(Bitmap icon, Category category);
    }
}
