package com.natali_pi.home_money.models;

import com.natali_pi.home_money.utils.PURPOSE;
import com.natali_pi.home_money.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Natali-Pi on 21.11.2017.
 */

public class Family {
    private String id;
    private String name;
    private ArrayList<Spending> spendings;
    private ArrayList<Spending> plannedSpendings;
    private ArrayList<String> humanIds;
    private ArrayList<Category> categories;

    private Money limit;


    public Family() {
        //    this.name = "Пятковские";
        /*this.spended = new ArrayList<>();
        spended.add(new Spending("1501"));
        //spended.add(new Spending("1401"));
        spended.add(new Spending("951"));
        spended.add(new Spending("921"));
        spended.add(new Spending("900"));
        spended.add(new Spending("830"));
        spended.add(new Spending("825"));
        spended.add(new Spending("100"));
        spended.add(new Spending("80"));
        spended.add(new Spending("90"));
        this.plannedSpends = plannedSpends;*/
        this.humanIds = humanIds;
        this.limit = limit;
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }
    public void updateCategory(Category update) {
        for (int i = 0; i < categories.size(); i++) {
            Category category = categories.get(i);
            if (category.getId().equals(update.getId())){
                categories.set(i, update);
                return;
            }
        }
        categories.add(update);
    }
    public String getName() {
        return name;
    }

    public List<Spending> getSpendings(PURPOSE purpose) {
        if (purpose == PURPOSE.SPENDED) {
            return spendings;
        } else {
            return plannedSpendings;
        }
    }

    public Spending getSpendingById(PURPOSE purpose, String id) {
        for (Spending spending : getSpendings(purpose)) {
            if (spending.getId().equals(id)) {
                return spending;
            }
        }
        return null;
    }

    public ArrayList<Spending> getPlannedSpendings() {
        return plannedSpendings;
    }

    public String getId() {
        return id;
    }

    public ArrayList<String> getHumanIds() {
        return humanIds;
    }

    public Money getLimit() {
        return limit;
    }

    public void setSpending(PURPOSE purpose, Spending spending) {
        //this.spending = spending;//TODO set spending
        for (int i = 0; i < getSpendings(purpose).size(); i++) {
            if (getSpendings(purpose).get(i).getId().equals(spending.getId())) {
                getSpendings(purpose).set(i, spending);
                return;
            }
        }
        getSpendings(purpose).add(spending);
    }

    public ArrayList<ArrayList<Spending>> getSpendingsByMMonth(PURPOSE purpose) {
        ArrayList<ArrayList<Spending>> result = new ArrayList<ArrayList<Spending>>();
        for (int i = 0; i < getSpendings(purpose).size(); i++) {//крутим все траты
            boolean monthFound = false;
            for (ArrayList<Spending> spendingsInMonth : result) {//ищем есть ли такой месяц уже в результате

                if (spendingsInMonth.get(0).getSpendingMonth() == getSpendings(purpose).get(i).getSpendingMonth()) {
                    spendingsInMonth.add(getSpendings(purpose).get(i));
                    monthFound = true;
                    break;
                }

            }
            if (!monthFound) {
                ArrayList<Spending> newSpendings = new ArrayList<>();
                newSpendings.add(this.getSpendings(purpose).get(i));
                result.add(newSpendings);
            }
        }


        int n = result.size();
        int k;
        for (int m = n; m >= 0; m--) {
            for (int i = 0; i < n - 1; i++) {
                k = i + 1;
                if (result.get(i).get(0).getSpendingMonth() < result.get(k).get(0).getSpendingMonth()) {
                    result.set(k, result.set(i, result.get(k)));
                }
            }

        }

        return result;
    }

    public void hideCategory(String id) {
        for (Category category : categories) {
            if (category.getId().equals(id)) {
                category.setHiden(true);
            }
        }
    }

    public Money calculateSpendingsMoney(PURPOSE purpose) {
        ArrayList<ArrayList<Spending>> spendingsByMMonth = getSpendingsByMMonth(purpose);
        Money summ = new Money();
        if (spendingsByMMonth.size() > 0) {
            if (spendingsByMMonth.get(0).get(0).getSpendingMonth() == Utils.getThisMonth()) {
                for (int i = 0; i < spendingsByMMonth.get(0).size(); i++) {
                    summ = Money.sum(summ, spendingsByMMonth.get(0).get(i).getSum());
                }
            }
        }
        return summ;
    }

    public void setLimit(Money limit) {
        this.limit = limit;
    }

    public HashMap<String, Money> getCategoriesMoneyByMonth(int[] date) {
        HashMap<String, Money> categoriesSums = new HashMap<>();
        ArrayList<Spending> spendings = null;

        ArrayList<ArrayList<Spending>> spendingsByMMonth = getSpendingsByMMonth(PURPOSE.SPENDED);
        for (ArrayList<Spending> spendingArrayList : spendingsByMMonth) {
            if (spendingArrayList.get(0).getSpendingMonth() == (date[0]*100) + date[1]+1) {
                spendings = spendingArrayList;
                break;
            }

        }
        if (spendings==null){
            return categoriesSums;
        }
        for (int i = 0; i < spendings.size(); i++) {
            if (categoriesSums.get(spendings.get(i).getCategory()) == null) {
                categoriesSums.put(spendings.get(i).getCategory(), spendings.get(i).getSum());
            } else {
                categoriesSums.put(spendings.get(i).getCategory(), Money.sum(categoriesSums.get(spendings.get(i).getCategory()), spendings.get(i).getSum()));
            }
        }
        return categoriesSums;
    }

    public ArrayList<CategoryMoney> mapCategoriesMoney(HashMap<String, Money> categoriesSums) {

        ArrayList<CategoryMoney> categoryMonies = new ArrayList<>();
        for (int i = 0; i < categories.size(); i++) {
            if (categoriesSums.get(categories.get(i).getId()) != null) {
                categoryMonies.add(new CategoryMoney(categories.get(i), categoriesSums.get(categories.get(i).getId())));
            }
        }
        return categoryMonies;
    }

    public ArrayList<Integer> getCategoriesPercentsByMonth(HashMap<String, Money> categoriesSums) {


        float total = 0.0f;
        ArrayList<Float> moneys = new ArrayList<>();
        for (int i = 0; i < categories.size(); i++) {
            if (categoriesSums.get(categories.get(i).getId()) != null) {
                moneys.add(categoriesSums.get(categories.get(i).getId()).getAsFloat());
                total += categoriesSums.get(categories.get(i).getId()).getAsFloat();
            }
        }

        if (total == 0) {
            return new ArrayList<>();
        }
        ArrayList<Integer> slices = new ArrayList<>();
        for (int i = 0; i < moneys.size(); i++) {
            slices.add((int) (moneys.get(i) / (total / 100)));
        }
        return slices;
    }
}
