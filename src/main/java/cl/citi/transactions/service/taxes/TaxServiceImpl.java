package cl.citi.transactions.service.taxes;

import cl.citi.transactions.exceptions.BusinessException;
import cl.citi.transactions.model.Tax;
import cl.citi.transactions.model.dto.response.GenericResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaxServiceImpl implements TaxService{

    private final List<Tax> taxes = createDummyTaxes();
    @Override
    public GenericResponse submitTax(Tax tax) {
        GenericResponse response = new GenericResponse();
        try{
            int newId = Integer.parseInt(taxes.get(taxes.size() -1 ).getId()) + 1;
            tax.setId(String.valueOf(newId));
            taxes.add(tax);
            response.setCode("200");
            response.setMessage("Tax added");
        }catch (Exception ex){
            throw new BusinessException("500", ex.getMessage());
        }
        return response;
    }

    @Override
    public List<Tax> getAllTaxes() {
        try{
            if(!taxes.isEmpty()){
                return taxes;
            }
        }catch (Exception ex){
            throw new BusinessException("500", ex.getMessage());
        }
        return taxes;
    }

    @Override
    public Tax getTaxById(String id) {
        Tax foundTax;
        try{
            foundTax = taxes.stream().filter(tax -> tax.getId().equals(id)).findFirst().orElseThrow();
        }catch (Exception ex){
            throw new BusinessException("404", "No tax found for given id");
        }
        return foundTax;
    }

    private List<Tax> createDummyTaxes(){
        List<Tax> dummyTaxes = new ArrayList<>();
        Tax tax = new Tax();
        tax.setId("1");
        tax.setName("Tax with 10%");
        tax.setDescription("10%");
        tax.setRate(0.10);
        dummyTaxes.add(tax);
        return dummyTaxes;
    }
}
