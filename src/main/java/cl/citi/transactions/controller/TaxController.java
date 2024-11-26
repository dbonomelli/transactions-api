package cl.citi.transactions.controller;

import cl.citi.transactions.model.Tax;
import cl.citi.transactions.service.taxes.TaxService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cl.citi.transactions.model.dto.response.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/taxes")
public class TaxController {
    @Autowired
    private TaxService taxService;

    @PostMapping
    public ResponseEntity<GenericResponse> submitTax(@RequestBody Tax tax) {
        return new ResponseEntity<>(taxService.submitTax(tax), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Tax>> getAllTaxes() {
        return new ResponseEntity<>(taxService.getAllTaxes(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tax> getTaxById(@PathVariable String id) {
        return new ResponseEntity<>(taxService.getTaxById(id), HttpStatus.OK);
    }
}
