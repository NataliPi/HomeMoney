package com.natali_pi.home_money.models;

import com.google.gson.annotations.SerializedName;
import com.natali_pi.home_money.utils.Currency;

import java.io.Serializable;

/**
 * Created by Natali-Pi on 21.11.2017.
 */

public class Money implements Serializable {
    @SerializedName("coins")
    private int coins = 0;
    @SerializedName("bill")
    private int bill = 0;
    @SerializedName("currency")
    private Currency currency = Currency.USD;
    @SerializedName("course")
    private float course = 0.0f;

    public Money(int i) {
    bill = i;
    }
    public Money(String value, String currency) {
        try {
            parse(Float.parseFloat(value));
        }catch (Exception e){
        coins = 0;
        bill = 0;
        }
            this.currency = Currency.valueOf(currency);

    }
    public Money(float value) {
        parse(value);
    }
private void parse(float value){
    bill = (int)value;
    coins =(int) ((value - (float) bill) * 100);
}
    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
    public void setCurrency(String currency) {
        this.currency = Currency.valueOf(currency);
    }

    public Currency getCurrency(){
        return currency;
    }

    @Override
    public String toString() {
        return bill+"."+coins;
    }

    public String getPureMoney(){
        return  bill +" "+ currency;
    }

    public int getBill() {
        return bill;
    }

    public Money() {
        coins=0;
        bill=0;

    }

    public static Money sum(Money first, Money second){
        Money sum = new Money();
        sum.bill = first.bill+second.bill;
        sum.coins = first.coins+second.coins;
        if (sum.coins >= 100){
            sum.coins=sum.coins-100;
            sum.bill=sum.bill+1;
        }
        return sum;
    }
    public static Money substract(Money first, Money second){
        Money result = new Money();
        if(first != null && second != null) {

            result.bill = first.bill - second.bill;
            result.coins = first.coins - second.coins;
            if (result.coins < 0) {
                result.coins = result.coins + 100;
                result.bill = result.bill - 1;
            }
        } else if (first != null && second == null ){
            return first;
        } else if (first == null && second != null){
            return second.getNegative();
        }
        return result;
    }

    public static int differance(Money first, Money second){

        return (second.bill*100)/first.bill;


    }
    public Boolean lessThen(Money limit){
        if (this.bill<limit.bill) {
            return true;
        } else {
            if (this.bill==limit.bill && this.coins<limit.coins){
                return true;
            } else {
                return false;
            }
        }
    }

    public Boolean lessOrEqualsThen(Money limit){
        if (this.bill < limit.bill) {
            return true;
        } else {
            if (this.bill==limit.bill && this.coins<=limit.coins){
                return true;
            } else {
                return false;
            }
        }
    }
public float getAsFloat(){
        return bill + (coins/100);
}
    public Money divideBy(float v) {
    return new Money(bill / v) ;
    }

    public Money getNegative() {
        return new Money(-getAsFloat());
    }
}
