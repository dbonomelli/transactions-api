package cl.citi.transactions.service.transactions;

import cl.citi.transactions.exceptions.BusinessException;
import cl.citi.transactions.model.Tax;
import cl.citi.transactions.model.Transaction;
import cl.citi.transactions.model.dto.response.GenericResponse;
import cl.citi.transactions.service.taxes.TaxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService{

    @Autowired
    private TaxService taxService;

    private final List<Transaction> transactions = createDummyTransactions();

    @Override
    public GenericResponse submitTransaction(Transaction transaction) {
        GenericResponse response = new GenericResponse();
        try{
            int newId = Integer.parseInt(transactions.get(transactions.size() -1 ).getId()) + 1;
            transaction.setId(String.valueOf(newId));
            transaction.setAmountAfterTax(calculateTax(transaction));
            transaction.setTransactionDate(new Date());
            transactions.add(transaction);
            response.setCode("200");
            response.setMessage("Transaction added");
        }catch (Exception ex){
            throw ex;
        }
        return response;
    }

    @Override
    public List<Transaction> getAllTransactions() {
        try{
            if(!transactions.isEmpty()){
                return transactions;
            }
        }catch (Exception ex){
            throw new BusinessException("500", ex.getMessage());
        }
        return transactions;
    }

    @Override
    public Transaction getTransactionById(String id) {
        Transaction transaction;
        try{
            transaction = transactions.stream().filter(t -> t.getId().equals(id)).findFirst().orElseThrow();
        }catch (Exception ex){
            throw new BusinessException("500", ex.getMessage());
        }
        return transaction;
    }

    private Double calculateTax(Transaction transaction){
        double newAmount = 0;
        try{
            if(transaction != null){
                String taxRuleId = transaction.getTaxRuleId();
                Tax tax = taxService.getTaxById(taxRuleId);
                if(tax != null){
                    newAmount = transaction.getAmount() + (tax.getRate() * transaction.getAmount());
                }else{
                    throw new BusinessException("404", "Tax Rule Id not found");
                }
            }
        } catch (Exception ex){
            throw ex;
        }
        return newAmount;
    }

    private List<Transaction> createDummyTransactions(){
        List<Transaction> dummyTransactions = new ArrayList<>();
        Transaction transaction = new Transaction();
        transaction.setId("1");
        transaction.setDetails("Payment to Citi");
        transaction.setAmount(16.25);
        transaction.setTransactionDate(new Date());
        transaction.setTaxRuleId("1");
        transaction.setAmountAfterTax(17.875);
        dummyTransactions.add(transaction);
        return dummyTransactions;
    }
}
