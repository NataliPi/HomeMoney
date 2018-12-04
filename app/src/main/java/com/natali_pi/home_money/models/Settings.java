package com.natali_pi.home_money.models;

import com.natali_pi.home_money.utils.Currency;

/**
 * Created by Konstantyn Zakharchenko on 24.01.2018.
 */

public class Settings {
    private Currency defaultCurrency = Currency.USD;
    private boolean notificationOn = true;
    private boolean passwordProtected = false;
    private boolean hasPremium = false;
    private String aboutLink = "";

    public Currency getDefaultCurrency() {
        return defaultCurrency;
    }

    public void setDefaultCurrency(Currency defaultCurrency) {
        this.defaultCurrency = defaultCurrency;
    }

    public boolean isNotificationOn() {
        return notificationOn;
    }

    public void setNotificationOn(boolean notificationOn) {
        this.notificationOn = notificationOn;
    }

    public boolean isPasswordProtected() {
        return passwordProtected;
    }

    public void setPasswordProtected(boolean passwordProtected) {
        this.passwordProtected = passwordProtected;
    }

    public boolean isHasPremium() {
        return hasPremium;
    }

    public void setHasPremium(boolean hasPremium) {
        this.hasPremium = hasPremium;
    }

    public String getAboutLink() {
        return aboutLink;
    }

    public void setAboutLink(String aboutLink) {
        this.aboutLink = aboutLink;
    }
}
