package cl.citi.transactions.service.taxes;

import cl.citi.transactions.model.Tax;
import cl.citi.transactions.model.dto.response.GenericResponse;

import java.util.List;

public interface TaxService {
    GenericResponse submitTax(Tax tax);
    List<Tax> getAllTaxes();
    Tax getTaxById(String id);
}
