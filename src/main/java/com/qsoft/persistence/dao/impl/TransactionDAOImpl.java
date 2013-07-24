package com.qsoft.persistence.dao.impl;

import com.qsoft.persistence.dao.TransactionDAO;
import com.qsoft.persistence.entities.Transaction;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Khiem
 * Date: 7/22/13
 * Time: 4:19 AM
 * To change this template use File | Settings | File Templates.
 */
@Transactional
public class TransactionDAOImpl implements TransactionDAO {
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public void save(Transaction transaction) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<Transaction> getAllTransaction(String number_acc) {
        Query query = entityManager.createQuery("select t from Transaction t where t.number_account = :number_acc");
        query.setParameter("number_acc",number_acc);
        List<Transaction> transactionList = query.getResultList();
        return transactionList;
    }

    @Override
    public List<Transaction> getAllTransaction(String number_acc, Long time_start, Long time_stop) {
        Query query = entityManager.createQuery("select t from Transaction t where t.number_account = :number_acc " +
                "and t.time_stamp >= :time_start and t.time_stamp <= :time_stop");
        query.setParameter("number_acc", number_acc);
        query.setParameter("time_start", time_start);
        query.setParameter("time_stop", time_stop);
        query.setMaxResults(2);
        List<Transaction> list = query.getResultList();
        return list;
    }

    @Override
    public List<Transaction> getAllTransaction(String number_acc, int number) {
        Query query = entityManager.createQuery("select t from Transaction t where t.number_account = :number_acc ");

        query.setParameter("number_acc", number_acc);
        query.setMaxResults(number);
        List<Transaction> list = query.getResultList();
        return list;
    }
}
