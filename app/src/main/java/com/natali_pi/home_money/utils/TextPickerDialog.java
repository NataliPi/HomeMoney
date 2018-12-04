package com.natali_pi.home_money.utils;

import android.content.Context;
import android.support.annotation.NonNull;

import android.widget.NumberPicker;
import android.widget.TextView;
import java.util.List;


public class TextPickerDialog extends PickerDialog{

    List<String> items;
    public TextPickerDialog(Context context, String dialogTitle, int startValue, List<String> items) {
        super(context, dialogTitle, startValue, toArray(items));
        this.items = items;

    }
private static String[] toArray(List<String> values){
        String[] array = new String[values.size()];
    for (int i = 0; i < values.size(); i++) {
        array[i] = values.get(i);
    }
return array;
    }
    @Override
    protected void setUpStyle(@NonNull NumberPicker[] integerNumberPickers, @NonNull NumberPicker[] decimalNumberPickers, TextView result) {
        if(values.length-1 >= 0) {
            integerNumberPickers[0].setMinValue(0);
            integerNumberPickers[0].setMaxValue(values.length - 1);
            integerNumberPickers[0].setDisplayedValues(values);

        }
    }

    @Override
    public String getInnerResultString(float collectedValue) {
        if(values.length-1 >= 0) {
            return values[(int) collectedValue];
        } else {
            return "";
        }
    }

    @Override
    @Deprecated
    public void show() {
        super.show();
    }

    public TextPickerDialog showMe(){
        if(values.length-1 >= 0) {
            super.show();
        }
        return this;
    }

    @Override
    public TextPickerDialog setOnDoneListener(OnDoneListener onDoneListener) {
        super.setOnDoneListener(onDoneListener);
        return this;
    }
}
