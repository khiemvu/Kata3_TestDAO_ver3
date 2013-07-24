package com.qsoft.persistence.entities;

import com.qsoft.persistence.dao.ValidateAmount;

import javax.persistence.*;
import javax.validation.constraints.Min;

/**
 * Created with IntelliJ IDEA.
 * User: Khiem
 * Date: 7/22/13
 * Time: 4:10 AM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="transaction")
@SequenceGenerator(name="id_transaction_seq", sequenceName = "id_transaction_seq", initialValue = 1, allocationSize = 1)
public class Transaction {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_transaction_seq")
    @Id
    @Column(name="id_transaction")
    private long id;

    @Column(name= "number_account")
    private String number_account;

    @Column(name="amount")
    private double balance;

    @Column(name="des")
    private String des;

    @Column(name="time_stamp")
    private long time_stamp;

    public Transaction(String number_account, double balance, String des, long time_stamp) {
        this.number_account = number_account;
        this.balance = balance;
        this.des = des;
        this.time_stamp = time_stamp;
    }

    public Transaction() {
    }
    @Min(value = 0, message = "Amount for do transaction must greater 0", groups = ValidateAmount.class)
    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public long getTime_stamp() {
        return time_stamp;
    }

    public void setTime_stamp(long time_stamp) {
        this.time_stamp = time_stamp;
    }

    public String getNumber_account() {
        return number_account;
    }

    public void setNumber_account(String number_account) {
        this.number_account = number_account;
    }
}
