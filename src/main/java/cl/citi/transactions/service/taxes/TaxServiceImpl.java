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

    /**
     * Creates a new Tax Rule
     *
     * @param tax
     * @return Generic response if correctly passed information
     */
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

    /**
     * Gets all the tax rules created
     * @return a list of tax rules
     */
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

    /**
     * Gets a tax rule using its ID from the array
     * @param id
     * @return a Tax Class containing the information
     */
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

    /**
     * Creates dummy data so the array doesn't start empty
     *
     * @return a list of dummy Tax Rules
     */
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
