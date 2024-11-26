package cl.citi.transactions.service.transactions;

import cl.citi.transactions.model.Transaction;
import cl.citi.transactions.model.dto.response.GenericResponse;

import java.util.List;

public interface TransactionService {
    GenericResponse submitTransaction(Transaction transaction);
    List<Transaction> getAllTransactions();
    Transaction getTransactionById(String id);

}
