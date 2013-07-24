package com.qsoft.persistence.dao;

import com.qsoft.persistence.entities.BankAccount;
import com.qsoft.persistence.entities.Transaction;
import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.IDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;

/**
 * Created with IntelliJ IDEA.
 * User: Khiem
 * Date: 7/22/13
 * Time: 1:36 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-config.xml"})
public class TestBankAccountDAO {
    private static Validator validation;
    @Autowired
    private BankAccountDAO bankAccountDAO;

    @Autowired
    private TransactionDAO transactionDAO;

    @Autowired
    private
    DataSource dataSourceTest;

    private IDatabaseTester iDatabasetest;

    @Before
    public void setup() throws Exception
    {
        IDataSet dataSet = readDataSet();
        cleanlyInsert(dataSet);


    }
    @BeforeClass
    public static void init(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validation = factory.getValidator();
    }

    private IDataSet readDataSet() throws Exception
    {
        return new FlatXmlDataSetBuilder().build(System.class.getResource("/dataset.xml"));
    }

    private void cleanlyInsert(IDataSet dataSet) throws Exception
    {
        iDatabasetest = new DataSourceDatabaseTester(dataSourceTest);
        iDatabasetest.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        iDatabasetest.setDataSet(dataSet);
        iDatabasetest.onSetup();
    }

    @After
    public void tearDown() throws Exception
    {
        iDatabasetest.onTearDown();
    }

    @Test
    public void testGetInfoABankAccountFromDB(){
        BankAccount bankAccount = bankAccountDAO.find("0123456789");
        assertFalse(bankAccount==null);
        assertEquals("0123456789", bankAccount.getNumber_acc());
        assertEquals(100.0, bankAccount.getBalance());
        assertEquals(1000, bankAccount.getTime_stamp());
    }

    @Test
    public void testSaveABankAccount(){
        BankAccount bankAccount = new BankAccount("0987654321", 100, 1000L, "deposit");
        bankAccountDAO.save(bankAccount);
        BankAccount account = bankAccountDAO.find("0987654321");
        assertEquals(account.getNumber_acc(), bankAccount.getNumber_acc());
        assertEquals(account.getTime_stamp(), bankAccount.getTime_stamp());
        assertEquals(account.getBalance(), bankAccount.getBalance());
        assertEquals(account.getDes(), bankAccount.getDes());
    }
    @Test
    public void testOpenABankAccountWithBalanceLessThanZero(){
        BankAccount bankAccount = new BankAccount("1234567089",-1000, 100L, "deposit");
        Set<ConstraintViolation<BankAccount>> violations = validation.validate(bankAccount, ValidateBalance.class);
        assertEquals(violations.size(),1);
        assertEquals("Balance must greater than 0", violations.iterator().next().getMessage());

    }
    @Test
    public void testOpentABankAccountWithNumberAccountLessThan10Number(){
        BankAccount bankAccount = new BankAccount("123456789", 1000, 1000L, "deposit");
        Set<ConstraintViolation<BankAccount>> violations = validation.validate(bankAccount, ValidateNumberAcc.class);
        assertEquals(violations.size(),1);
        assertEquals("Number Bank Acccount must has length is 10", violations.iterator().next().getMessage());

    }
    @Test
    public void testOpentABankAccountWithNumberAccountGreaterThan10Number(){
        BankAccount bankAccount = new BankAccount("12345678900", 1000, 1000L, "deposit");
        Set<ConstraintViolation<BankAccount>> violations = validation.validate(bankAccount, ValidateNumberAcc.class);
        assertEquals(violations.size(),1);
        assertEquals("Number Bank Acccount must has length is 10", violations.iterator().next().getMessage());

    }

    @Test
    public void testOpentABankAccountWithNoTimeStamp(){
        BankAccount bankAccount = new BankAccount("12345678900", 1000, "deposit");
        Set<ConstraintViolation<BankAccount>> violations = validation.validate(bankAccount, ValidateTimeStamp.class);
        assertEquals(violations.size(),1);
        assertEquals("TimeStamp is compulsory", violations.iterator().next().getMessage());

    }
    @Test
    public void testDoTransactionWithAmountNegative(){
        Transaction transaction = new Transaction("0123456789", -100, "deposit", 100L);
        Set<ConstraintViolation<Transaction>> violations = validation.validate(transaction, ValidateAmount.class);
        assertEquals(violations.size(), 1);
        assertEquals("Amount for do transaction must greater 0", violations.iterator().next().getMessage());
    }
    @Test
    public void testGetAllTransactionFromDB(){
        List<Transaction> transactionList = transactionDAO.getAllTransaction("0123456789");
        assertEquals(transactionList.size(), 3);
        assertEquals(transactionList.get(0).getDes(), "deposit");
        assertEquals(transactionList.get(0).getBalance(), 10000.0);
        assertEquals(transactionList.get(2).getTime_stamp(),60000);
    }

    @Test
    public void testGetAllTransactionFromStartTimeToStopTime(){
        List<Transaction> listTransaction = transactionDAO.getAllTransaction("0123456789",2500L, 70000L);
        assertEquals(listTransaction.size(), 2);
        assertEquals(listTransaction.get(0).getTime_stamp(),30000L);
        assertEquals(listTransaction.get(0).getBalance(),1000.0);
        assertEquals(listTransaction.get(0).getDes(),"withdraw");
        assertEquals(listTransaction.get(1).getTime_stamp(), 60000);
    }

    @Test
    public void testGetAllNTransaction(){
        List<Transaction> listTransaction = transactionDAO.getAllTransaction("0123456789",3);
        assertEquals(listTransaction.size(), 3);
        assertEquals(listTransaction.get(1).getTime_stamp(),30000L);
        assertEquals(listTransaction.get(1).getBalance(),1000.0);
        assertEquals(listTransaction.get(1).getDes(),"withdraw");
        assertEquals(listTransaction.get(2).getTime_stamp(), 60000);
    }

}
