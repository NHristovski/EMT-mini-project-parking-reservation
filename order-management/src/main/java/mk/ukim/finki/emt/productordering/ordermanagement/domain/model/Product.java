package mk.ukim.finki.emt.productordering.ordermanagement.domain.model;

import lombok.Getter;
import mk.ukim.finki.emt.productordering.sharedkernel.domain.financial.Money;

@Getter
public class Product {

    private String name;

    private ProductId id;

    private Money price;

    private int quantity;

}
