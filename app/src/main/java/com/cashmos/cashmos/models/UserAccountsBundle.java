package com.cashmos.cashmos.models;

import com.cashmos.cashmos.contracts.Account;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by brightantwiboasiako on 9/4/17.
 */

public class UserAccountsBundle
{

    private User user;
    private Business[] businesses;

    private User getUser() {
        return user;
    }

    private Business[] getBusinesses() {
        return businesses;
    }

    public List<Account> getAccounts() {
        ArrayList<Account> accounts = new ArrayList<>();
        accounts.add((Account) getUser());
        accounts.addAll(Arrays.asList(getBusinesses()));
        return accounts;

    }
}

