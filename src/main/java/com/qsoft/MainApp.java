package com.qsoft;

import com.qsoft.persistence.dao.BankAccountDAO;
import com.qsoft.persistence.entities.BankAccount;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created with IntelliJ IDEA.
 * User: Khiem
 * Date: 7/22/13
 * Time: 3:52 AM
 * To change this template use File | Settings | File Templates.
 */
public class MainApp {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-config.xml");
        BankAccountDAO bankAccountDAO = (BankAccountDAO) applicationContext.getBean("bankAccountDAOImpl");
        BankAccount checkAccount = new BankAccount("1234567890", 100, 10L, "withdraw");
        bankAccountDAO.save(checkAccount);
    }
}
