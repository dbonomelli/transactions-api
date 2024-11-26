package cl.citi.transactions.service.taxes;

import cl.citi.transactions.exceptions.BusinessException;
import cl.citi.transactions.model.Tax;
import cl.citi.transactions.model.dto.response.GenericResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TaxServiceImplTest {
    @InjectMocks
    private TaxServiceImpl taxService;

    private Tax tax;

    @BeforeEach
    public void setUp() {
        tax = new Tax();
        tax.setId("1");
        tax.setName("Tax with 10%");
        tax.setDescription("10%");
        tax.setRate(0.10);
    }

    @Test
    public void testSubmitTax() {
        GenericResponse response = taxService.submitTax(tax);

        assertEquals("200", response.getCode());
        assertEquals("Tax added", response.getMessage());
        assertEquals(2, taxService.getAllTaxes().size());
    }

    @Test
    public void testGetAllTaxes() {
        List<Tax> taxes = taxService.getAllTaxes();
        assertNotNull(taxes);
        assertEquals(1, taxes.size());
    }

    @Test
    public void testGetTaxById() {
        taxService.submitTax(tax);
        Tax foundTax = taxService.getTaxById("1");
        assertNotNull(foundTax);
        assertEquals("Tax with 10%", foundTax.getName());
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            taxService.getTaxById("999");
        });
        assertEquals("404", exception.getCode());
        assertEquals("No tax found for given id", exception.getMessage());
    }
}