package com.natali_pi.home_money.utils;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PickDateDialog implements View.OnClickListener {
    Context context;
    int[] selectedKeys = new int[3];
    OnSelectListener onSelectListener;
    SimpleDateFormat day = new SimpleDateFormat("dd");
    SimpleDateFormat month = new SimpleDateFormat("MM");
    SimpleDateFormat year = new SimpleDateFormat("yyyy");

    public PickDateDialog(Context context,OnSelectListener onSelectListener) {
        selectedKeys = new int[3];
        this.context = context;
        selectedKeys[0] = Integer.parseInt(year.format(new Date()));
        selectedKeys[1] = Integer.parseInt(month.format(new Date())) - 1;
        selectedKeys[2] = Integer.parseInt(day.format(new Date()));
        this.onSelectListener = onSelectListener;
    }

    public interface OnSelectListener {
        void onSelected(int[] selectedKeys);
    }

    @Override
    public void onClick(View view) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int pickedYear, int pickedMonth, int pickedDay) {
                selectedKeys[0] = pickedYear;
                selectedKeys[1] = pickedMonth;
                selectedKeys[2] = pickedDay;
//                ((TextView) view).setText(getText());
                onSelectListener.onSelected(selectedKeys);
            }
        }, selectedKeys[0], selectedKeys[1], selectedKeys[2]);

        datePickerDialog.show();


    }

    public String getText() {
        return selectedKeys[2] + "/" + (selectedKeys[1] + 1) + "/" + selectedKeys[0];
    }
}
