package com.qsoft.persistence.dao.impl;

import com.qsoft.persistence.dao.BankAccountDAO;
import com.qsoft.persistence.entities.BankAccount;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Created with IntelliJ IDEA.
 * User: Khiem
 * Date: 7/22/13
 * Time: 4:18 AM
 * To change this template use File | Settings | File Templates.
 */
@Transactional
@Component
public class BankAccountDAOImpl implements BankAccountDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(BankAccount bankAccount) {
        entityManager.persist(bankAccount);
    }

    @Override
    public BankAccount find(String number_acc) {
        Query query = entityManager.createQuery("select b from BankAccount b where b.number_acc = :number_acc");
        query.setParameter("number_acc", number_acc);
        return (BankAccount) query.getSingleResult();
    }
}
