package com.qsoft.persistence.dao;

import com.qsoft.persistence.entities.Transaction;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Khiem
 * Date: 7/22/13
 * Time: 4:15 AM
 * To change this template use File | Settings | File Templates.
 */
public interface TransactionDAO {

    void save(Transaction transaction);

    public List<Transaction> getAllTransaction(String number_acc);

    public List<Transaction> getAllTransaction(String number_acc, Long time_start, Long time_stop);

    public List<Transaction> getAllTransaction(String number_acc, int number);
}
