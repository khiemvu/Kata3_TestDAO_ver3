package com.qsoft.persistence.dao;

import com.qsoft.persistence.entities.BankAccount;

/**
 * Created with IntelliJ IDEA.
 * User: Khiem
 * Date: 7/22/13
 * Time: 4:14 AM
 * To change this template use File | Settings | File Templates.
 */
public interface BankAccountDAO {
    public void save(BankAccount bankAccount);

    public void find(String number_acc);
}
