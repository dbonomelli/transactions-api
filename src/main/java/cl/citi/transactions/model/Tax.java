package cl.citi.transactions.model;

import lombok.Data;


@Data
public class Tax {
    private String id;
    private String name;
    private String description;
    private Double rate = 0.10;
}
