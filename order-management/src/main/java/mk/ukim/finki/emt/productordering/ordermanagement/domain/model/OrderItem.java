package mk.ukim.finki.emt.productordering.ordermanagement.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import mk.ukim.finki.emt.productordering.sharedkernel.domain.base.AbstractEntity;
import mk.ukim.finki.emt.productordering.sharedkernel.domain.base.DomainObjectId;
import mk.ukim.finki.emt.productordering.sharedkernel.domain.financial.Money;
import org.springframework.lang.NonNull;

import javax.persistence.*;

@Entity
@Table(name="order_items")
@Getter
public class OrderItem extends AbstractEntity<OrderItemId> {

    @Embedded
    @AttributeOverride(name="id",column = @Column(name="product_id",nullable = false))
    private ProductId productId;

    @Embedded
    private Money itemPrice;

    @Column(name = "qty", nullable = false)
    private int quantity;

    private OrderItem() {
    }

    OrderItem(@NonNull ProductId productId, @NonNull Money itemPrice, @NonNull int quantity) {
        super(DomainObjectId.randomId(OrderItemId.class));
        setProductId(productId);
        setItemPrice(itemPrice);
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        this.quantity = quantity;
    }

    public void setProductId(ProductId productId) {
        this.productId = productId;
    }

    public void setItemPrice(Money itemPrice) {
        this.itemPrice = itemPrice;
    }

    public void setQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        this.quantity = quantity;
    }

    @NonNull
    @JsonProperty("qty")
    public int quantity() {
        return quantity;
    }

    public Money subtotal() {
        return itemPrice.multiply(quantity);
    }



}
