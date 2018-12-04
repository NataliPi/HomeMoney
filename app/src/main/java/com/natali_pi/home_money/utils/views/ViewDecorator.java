package com.natali_pi.home_money.utils.views;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.natali_pi.home_money.R;
import com.natali_pi.home_money.main.DaySpendingsFragment;

/**
 * Created by Konstantyn Zakharchenko on 22.08.2018.
 */

public class ViewDecorator {
    Context context;
    LinearLayout layout;
    boolean isBase = false;
    TYPE type = TYPE.EMPTY;
    ViewDecorator previous;
    View view;
    private DaySpendingsFragment.SIZE size;

    public ViewDecorator(Context context, LinearLayout layout) {
        this.context = context;
        this.layout = layout;
        isBase = true;
    }

    public ViewDecorator(ViewDecorator previous, DaySpendingsFragment.SIZE size) {
        this.previous = previous;
        context = previous.context;
        this.size = size = normalizeSize(size);
        view = LayoutInflater.from(context).inflate(R.layout.spending_item, null);
        LinearLayout child;
        switch (previous.type) {
            case EMPTY:
            case BB:
            case BHLHL:
            case BHLSS:
            case BVLVL:
            case BVLSS:
            case BSSSS:
            case HLHL:
            case HLSS:
            case VLVLVLVL:
            case VLVLVLSS:
            case VLVLSSSS:
            case VLSSSSSS:
            case SSSSSSSS:
                                 child = prepareHorizontalLayout();
                    getBaseLayout().addView(child);
                    child.addView(view);
                    layout = child;


                break;
            case B:
                if (size == DaySpendingsFragment.SIZE._2X2
                        || size == DaySpendingsFragment.SIZE._1X2) {
                    previous.layout.addView(view);
                    layout = previous.layout;

                }
                if (size == DaySpendingsFragment.SIZE._2X1) {
                    child = prepareVerticalLayout();
                    previous.layout.addView(child);
                    child.addView(view);
                    layout = child;
                }
                if (size == DaySpendingsFragment.SIZE._1X1) {
                    child = prepareVerticalLayout();
                    previous.layout.addView(child);
                    child.addView(child = prepareHorizontalLayout());
                    child.addView(view);
                    layout = child;
                }

                break;
            case HL:
            case HLS:
            case BHLS:
            case S:
            case SS:
            case SSS:
            case SSSSS:
            case SSSSSS:
            case SSSSSSS:
            case BVLS:
            case VLS:
            case VLSS:
            case VLVLS:
            case VLVLSSS:
            case VLSSSSS:
            case VLSSSS:
            case VLVLVLS:
            case BS:
            case BSSS:
                previous.layout.addView(view);
                layout = previous.layout;
                break;
            case VL:
                if (size == DaySpendingsFragment.SIZE._1X2) {
                    previous.layout.addView(view);
                    layout = previous.layout;
                }
                if (size == DaySpendingsFragment.SIZE._1X1) {
                    child = prepareVerticalLayout();
                    previous.layout.addView(child);
                    child.addView(child = prepareHorizontalLayout());
                    child.addView(view);
                    layout = child;
                }

                break;
            case BHL:
                if (size == DaySpendingsFragment.SIZE._2X1) {
                    previous.layout.addView(view);
                    layout = previous.layout;
                }
                if (size == DaySpendingsFragment.SIZE._1X1) {
                    child = prepareHorizontalLayout();
                    previous.layout.addView(child);
                    child.addView(view);
                    layout = child;
                }
                break;
            case BVL:
                if (size == DaySpendingsFragment.SIZE._1X2) {
                    previous.layout.addView(view);
                    layout = previous.layout;
                }
                if (size == DaySpendingsFragment.SIZE._1X1) {
                    child = prepareVerticalLayout();
                    previous.layout.addView(child);
                    child.addView(view);
                    layout = child;
                }
                break;
            case BSS:
                child = prepareHorizontalLayout();
                ((LinearLayout) previous.previous.layout.getParent()).addView(child);
                child.addView(view);
                layout = child;
                break;
            case VLVL:
                if (size == DaySpendingsFragment.SIZE._1X2) {
                    previous.layout.addView(view);

                    layout = previous.layout;
                }
                if (size == DaySpendingsFragment.SIZE._1X1) {
                    child = prepareVerticalLayout();
                    previous.layout.addView(child);
                    child.addView(child = prepareHorizontalLayout());
                    child.addView(view);
                    layout = child;
                }
                break;
            case VLVLVL:
                if (size == DaySpendingsFragment.SIZE._1X2) {
                    previous.layout.addView(view);
                    layout = previous.layout;
                }
                if (size == DaySpendingsFragment.SIZE._1X1) {
                    child = prepareVerticalLayout();
                    previous.layout.addView(child);
                    child.addView(view);
                    layout = child;
                }
                break;

            case VLVLSS:

                child = prepareHorizontalLayout();
                ((LinearLayout) previous.previous.layout.getParent()).addView(child);
                child.addView(view);
                layout = child;
                break;
            case VLSSS:
                child = prepareHorizontalLayout();
                ((LinearLayout) previous.previous.previous.layout.getParent()).addView(child);
                child.addView(view);
                layout = child;
                break;
            case SSSS:
                child = prepareHorizontalLayout();
                ((LinearLayout) previous.previous.previous.previous.layout.getParent()).addView(child);
                child.addView(view);
                layout = child;
                break;
        }

        type = getNextType(previous.type, size);
    }

    private DaySpendingsFragment.SIZE normalizeSize(DaySpendingsFragment.SIZE size) {
        if (size == DaySpendingsFragment.SIZE._2X1 || size == DaySpendingsFragment.SIZE._1X2) {
            switch (previous.type) {
                case B:
                case EMPTY:
                case BB:
                case BHLHL:
                case HLSS:
                case BHLSS:
                case BVLVL:
                case BVLSS:
                case BSSSS:
                case HLHL:
                case VLVLVLVL:
                case VLVLVLSS:
                case VLVLSSSS:
                case VLSSSSSS:
                case SSSSSSSS:
                    return randomizeSize();
                case BHL:
                case HL:
                    return DaySpendingsFragment.SIZE._2X1;
                case BVL:
                case VL:
                case VLVL:
                case VLVLVL:
                    return DaySpendingsFragment.SIZE._1X2;
                    default:
                        return size;
            }
        } else {
            return size;
        }
    }

    private DaySpendingsFragment.SIZE randomizeSize() {

        return Math.random() >= 0.5d ? DaySpendingsFragment.SIZE._2X1 : DaySpendingsFragment.SIZE._1X2;
    }

    private TYPE getNextType(TYPE previousType, DaySpendingsFragment.SIZE size) {
        switch (previousType) {
            case EMPTY:
            case BB:
            case BHLHL:
            case BHLSS:
            case BVLVL:
            case BVLSS:
            case BSSSS:
            case HLHL:
            case HLSS:
            case VLVLVLVL:
            case VLVLVLSS:
            case VLVLSSSS:
            case VLSSSSSS:
            case SSSSSSSS:
                return getTypeBySize(size);
            default:
                return TYPE.valueOf("" + previousType + getTypeBySize(size).name());
        }
    }

    private TYPE getTypeBySize(DaySpendingsFragment.SIZE size) {
        switch (size) {
            case _2X2:
                return TYPE.B;
            case _2X1:
                return TYPE.HL;
            case _1X2:
                return TYPE.VL;
            case _1X1:
                return TYPE.S;
        }
        return TYPE.EMPTY;
    }

    private LinearLayout getBaseLayout() {
        if (isBase) {
            return layout;
        } else {
            return previous.getBaseLayout();
        }
    }

    private LinearLayout prepareHorizontalLayout() {
        LinearLayout line = new LinearLayout(context);
        line.setOrientation(LinearLayout.HORIZONTAL);
        return line;
    }

    private LinearLayout prepareVerticalLayout() {
        LinearLayout line = new LinearLayout(context);
        line.setOrientation(LinearLayout.VERTICAL);
        return line;
    }

    public View getView() {
        return view;
    }

    public DaySpendingsFragment.SIZE getSize() {
        return size;
    }

    public enum TYPE {
        EMPTY,
        B,
        BB,
        BHL,
        BHLHL,
        BHLS,
        BHLSS,
        BVL,
        BVLVL,
        BVLS,
        BVLSS,
        BS,
        BSS,
        BSSS,
        BSSSS,
        HL,
        HLS,
        HLSS,
        HLHL,
        VLVLVLVL,
        VLVLVL,
        VLVLVLS,
        VLVLVLSS,
        VLVL,
        VLVLS,
        VLVLSS,
        VLVLSSS,
        VLVLSSSS,
        VL,
        VLS,
        VLSS,
        VLSSS,
        VLSSSS,
        VLSSSSS,
        VLSSSSSS,
        S,
        SS,
        SSS,
        SSSS,
        SSSSS,
        SSSSSS,
        SSSSSSS,
        SSSSSSSS,
    }


}
