package cl.citi.transactions.model;

import lombok.Data;

import java.util.Date;

@Data
public class Transaction {
    private String id;
    private Double amount;
    private Double amountAfterTax;
    private String taxRuleId;
    private Double tax;
    private Date transactionDate;
    private String details;
}
