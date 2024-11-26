package cl.citi.transactions.controller;

import cl.citi.transactions.model.Transaction;
import cl.citi.transactions.model.dto.response.GenericResponse;
import cl.citi.transactions.service.transactions.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/transactions")
@CrossOrigin("http://localhost:4200")
@RestController
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @PostMapping()
    public ResponseEntity<GenericResponse> submitTransaction(@RequestBody Transaction transaction){
        return new ResponseEntity<>(transactionService.submitTransaction(transaction), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<Transaction>> getAllTransactions(){
        return new ResponseEntity<>(transactionService.getAllTransactions(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable String id){
        return new ResponseEntity<>(transactionService.getTransactionById(id), HttpStatus.OK);
    }



}
