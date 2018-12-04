package com.natali_pi.home_money.models;

import com.natali_pi.home_money.utils.Currency;

import java.io.Serializable;

/**
 * Created by Natali-Pi on 21.11.2017.
 */

public class SpendingComponent implements Serializable {
    private String name = "";
    private Money price = new Money();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Money getPrice() {
        return price;
    }

    public void setPrice(Money price) {
        this.price = price;
    }
    public void setPrice(String price) {
        this.price = new Money(Float.parseFloat(price));
    }
}
