package com.qsoft.persistence.entities;

import com.qsoft.persistence.dao.ValidateBalance;
import com.qsoft.persistence.dao.ValidateNumberAcc;
import com.qsoft.persistence.dao.ValidateTimeStamp;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


/**
 * Created with IntelliJ IDEA.
 * User: Khiem
 * Date: 7/22/13
 * Time: 4:03 AM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="bank_account")
@SequenceGenerator(sequenceName = "id_acc_seq", name = "id_acc_seq", initialValue = 1, allocationSize = 1)
public class BankAccount {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_acc_seq")
    @Id
    @Column(name="id")
    private Long id;

    @Column(name= "number_acc")
    private String number_acc;

    @Column(name="balance")
    private double balance;

    @Column(name="des")
    private String des;

    @Column(name="time_stamp")
    private long time_stamp;

    public BankAccount(String number_acc, double balance, long time_stamp, String des) {
        this.number_acc = number_acc;
        this.balance = balance;
        this.time_stamp = time_stamp;
        this.des = des;
    }

    public BankAccount() {
    }

    public BankAccount(String number_acc, double balance, String des) {
        this.number_acc = number_acc;
        this.balance = balance;
        this.des = des;
    }

    @Length(max = 10, min = 10, message = "Number Bank Acccount must has length is 10", groups = ValidateNumberAcc.class)
    public String getNumber_acc() {
        return number_acc;
    }

    public void setNumber_acc(String number_acc) {
        this.number_acc = number_acc;
    }
    @Min(value = 0, message = "Balance must greater than 0", groups = ValidateBalance.class)
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
    @NotNull(message = "TimeStamp is compulsory", groups = ValidateTimeStamp.class)
    public long getTime_stamp() {
        return time_stamp;
    }

    public void setTime_stamp(long time_stamp) {
        this.time_stamp = time_stamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
