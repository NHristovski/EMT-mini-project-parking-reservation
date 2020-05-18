package mk.ukim.finki.emt.productordering.productcatalog.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import mk.ukim.finki.emt.productordering.sharedkernel.domain.base.AbstractEntity;
import mk.ukim.finki.emt.productordering.sharedkernel.domain.financial.Money;

import javax.persistence.*;

@Entity
@Table(name="product")
public class Product extends AbstractEntity<ProductId> {

    @Version
    private Long version;

    @Column(name = "name", nullable = false)
    private String name;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "amount")),
            @AttributeOverride(name = "currency", column = @Column(name = "currency"))
    })
    private Money price;

    private int quantity;

    @Column(name = "deleted", nullable = false)
    private boolean deleted = false;

    public Product() {

    }

    @JsonProperty("name")
    public String name() {
        return name;
    }

    public void setPrice(Money price) {
        this.price = price;
    }

    public Money getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
