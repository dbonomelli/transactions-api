package cl.citi.transactions.service.transactions;

import cl.citi.transactions.exceptions.BusinessException;
import cl.citi.transactions.model.Tax;
import cl.citi.transactions.model.Transaction;
import cl.citi.transactions.model.dto.response.GenericResponse;
import cl.citi.transactions.service.taxes.TaxService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

    private List<Transaction> transactions;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Mock
    private TaxService taxService;

    private Transaction transaction;

    @BeforeEach
    public void setUp() {
        transaction = new Transaction();
        transaction.setId("1");
        transaction.setDetails("Payment to Citi");
        transaction.setAmount(16.25);
        transaction.setTaxRuleId("1");
        transaction.setTransactionDate(new Date());
    }

    @Test
    public void testSubmitTransaction() {
        Tax tax = new Tax();
        tax.setRate(0.10);
        when(taxService.getTaxById("1")).thenReturn(tax);
        GenericResponse response = transactionService.submitTransaction(transaction);
        assertEquals("200", response.getCode());
        assertEquals("Transaction added", response.getMessage());
        assertEquals(17.875, transaction.getAmountAfterTax());
        assertEquals(2, transactionService.getAllTransactions().size());
    }

    @Test
    public void testGetAllTransactions() {
        List<Transaction> transactions = transactionService.getAllTransactions();
        assertNotNull(transactions);
        assertEquals(1, transactions.size());
    }

    @Test
    public void testGetTransactionById() {
        when(taxService.getTaxById("1")).thenReturn(new Tax()); // Mock tax service
        transactionService.submitTransaction(transaction);
        Transaction foundTransaction = transactionService.getTransactionById("1");
        assertNotNull(foundTransaction);
        assertEquals("Payment to Citi", foundTransaction.getDetails());
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            transactionService.getTransactionById("999");
        });
        assertEquals("500", exception.getCode());
    }
}