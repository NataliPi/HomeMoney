package com.natali_pi.home_money.models;

/**
 * Created by Konstantyn Zakharchenko on 19.07.2018.
 */

public class CategoryMoney {
    private Category category;
    private Money money;

    public CategoryMoney(Category category, Money money) {
        this.category = category;
        this.money = money;
    }

    public Category getCategory() {
        return category;
    }

    public Money getMoney() {
        return money;
    }
}
