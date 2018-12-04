package com.natali_pi.home_money.utils.views;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.DatePicker;

import com.natali_pi.home_money.R;
import com.natali_pi.home_money.utils.PickerDialog;
import com.natali_pi.home_money.utils.TextPickerDialog;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;


public class DropdownView extends android.support.v7.widget.AppCompatEditText {
    int[] selectedKeys = new int[3];
    List<String> items = new ArrayList<>();
    private boolean isDatePicker = false;
    int dialogButtonStyle = 0;
    private ClickListener clickListener = null;

    public DropdownView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setMaxLines(1);
        setEllipsize(TextUtils.TruncateAt.END);
        setSingleLine(true);

        setFocusable(false);
        setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.DropdownView, 0, 0);
        isDatePicker = a.getBoolean(R.styleable.DropdownView_isDatePicker, false);
        dialogButtonStyle = a.getResourceId(R.styleable.DropdownView_dialogButtonStyle, 0);

        a.recycle();
        prepareLists();
    }

    public void setDefault(String data) {
        setText(data);
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).equals(data)) {
                selectedKeys[0] = i;
            }
        }
    }

    public void setDefaultFirst(String data) {
        setText(data);

        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).equals(data)) {
                String value = items.get(i);
                items.remove(i);
                items.add(0, value);
                selectedKeys[0] = 0;
            }
        }
    }

    public int getCoosenIndex() {
        return selectedKeys[0];
    }


    private void prepareLists() {

        if (isDatePicker) {
            setOnClickListener(new PickDateDialog());
        } else {
            setOnClickListener(new PickTextDialog());
        }

    }

    public void setTodayDate() {
        selectedKeys = new int[3];
        SimpleDateFormat day = new SimpleDateFormat("dd");
        SimpleDateFormat month = new SimpleDateFormat("MM");
        SimpleDateFormat year = new SimpleDateFormat("yyyy");
        Date date = new Date();
        selectedKeys[0] = Integer.parseInt(year.format(date));
        selectedKeys[1] = Integer.parseInt(month.format(date)) - 1;
        selectedKeys[2] = Integer.parseInt(day.format(date));
        setText(getData());
    }

    public void setDate(String dateString) {
        selectedKeys = new int[3];
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");


        try {

            SimpleDateFormat day = new SimpleDateFormat("dd");
            SimpleDateFormat month = new SimpleDateFormat("MM");
            SimpleDateFormat year = new SimpleDateFormat("yyyy");
            Date date = dateFormat.parse(dateString);
            selectedKeys[0] = Integer.parseInt(year.format(date));
            selectedKeys[1] = Integer.parseInt(month.format(date)) - 1;
            selectedKeys[2] = Integer.parseInt(day.format(date));
            setText(getData());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * As this is a EditText, first click will fire OnFocusChange,
     * click on the focused EditText will fire OnClickListener, so both must be identical
     *
     * @param clickListener
     */
    public void setOnClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
        super.setOnFocusChangeListener(clickListener);
        super.setOnClickListener(clickListener);
    }

    public void fireClick() {
        if (clickListener != null) {
            clickListener.onAction();
        }
    }

    @Deprecated
    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        super.setOnClickListener(l);
    }

    @Deprecated
    @Override
    public void setOnFocusChangeListener(OnFocusChangeListener l) {
        super.setOnFocusChangeListener(l);
    }

    /**
     * Owerride onAction which will be used in case of both click and obtain focus
     */
    public abstract class ClickListener implements OnFocusChangeListener, OnClickListener {
        protected abstract void onAction();

        @Override
        public void onFocusChange(View view, boolean hasFocus) {
            if (hasFocus) {
                onAction();
            }
        }

        @Override
        public void onClick(View view) {
            onAction();
        }

    }

    class PickDateDialog extends ClickListener {
        @Override
        protected void onAction() {
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int pickedYear, int pickedMonth, int pickedDay) {
                    selectedKeys[0] = pickedYear;
                    selectedKeys[1] = pickedMonth;
                    selectedKeys[2] = pickedDay;
                    setText(getDateString(selectedKeys[0], selectedKeys[1], selectedKeys[2]));

                }
            }, selectedKeys[0], selectedKeys[1], selectedKeys[2]);

            datePickerDialog.show();
        }
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

    class PickTextDialog extends ClickListener {

        TextPickerDialog dialog = null;

        public PickTextDialog() {
            selectedKeys = new int[1];
        }

        @Override
        protected void onAction() {
            if (isEnabled()) {
                dialog = new TextPickerDialog(getContext(), "" + getHint(), selectedKeys[0], items).setOnDoneListener(new OnDone()).showMe();
            }
        }

        class OnDone implements PickerDialog.OnDoneListener {
            @Override
            public void onDone(float result) {

                if (dialog != null) {
                    selectedKeys[0] = (int) result;
                    setText(dialog.getInnerResultString(selectedKeys[0]));
                }

            }
        }
    }

    public String getData() {
        if (selectedKeys.length > 0) {
            if (isDatePicker) {
                return getDateString(selectedKeys[0], selectedKeys[1], selectedKeys[2]);
            } else {
                if (selectedKeys.length == 1) {
                    return items.get(selectedKeys[0]);
                }
            }
        } return "";
    }

    public static String getDateString(int year, int month, int day) {
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        Calendar calendar = new GregorianCalendar(year, month, day);
        calendar.setTimeZone(TimeZone.getTimeZone("GMT"));
        return format.format(calendar.getTime());

    }


}




