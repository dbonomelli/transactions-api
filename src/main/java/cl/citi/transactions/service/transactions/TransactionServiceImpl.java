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

    /**
     * Submits a transaction into the array provided above, checks last id and adds one
     * sets the new value of the amount after taxes and the current date
     *
     * @param transaction
     * @return Generic response if saved correctly
     */
    @Override
    public GenericResponse submitTransaction(Transaction transaction) {
        GenericResponse response = new GenericResponse();
        try{
            int newId = Integer.parseInt(transactions.get(transactions.size() -1 ).getId()) + 1;
            transaction.setId(String.valueOf(newId));
            transaction.setTax(getTaxValue(transaction));
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

    private Double getTaxValue(Transaction transaction){
        double tax = 0;
        try{
            if(transaction != null){
                String taxRuleId = transaction.getTaxRuleId();
                Tax taxObject = taxService.getTaxById(taxRuleId);
                if(taxObject != null){
                    tax = taxObject.getRate() * transaction.getAmount();
                }else{
                    throw new BusinessException("404", "Tax Rule Id not found");
                }
            }
        }catch (Exception ex){
            throw ex;
        }


        return tax;
    }

    /**
     * Gets all the transactions that are currently saved in the array
     *
     * @return List of transactions
     */
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

    /**
     * Finds a transaction using via its ID
     *
     * @param id
     * @return the given transaction if found, else returns an exception
     */
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

    /**
     * Checks the variables, if not empty, gets the Tax Rule and the amount from the transaction
     *
     * @param transaction
     * @return a Double with an updated value of the amount
     */

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

    /**
     * Creates dummy data for the array, so it doesn't start empty
     *
     * @return List of transactions created in the dummy data
     */
    private List<Transaction> createDummyTransactions(){
        List<Transaction> dummyTransactions = new ArrayList<>();
        Transaction transaction = new Transaction();
        transaction.setId("1");
        transaction.setDetails("Payment to Citi");
        transaction.setAmount(16.25);
        transaction.setTax(1.625);
        transaction.setTransactionDate(new Date());
        transaction.setTaxRuleId("1");
        transaction.setAmountAfterTax(17.875);
        dummyTransactions.add(transaction);
        return dummyTransactions;
    }
}
