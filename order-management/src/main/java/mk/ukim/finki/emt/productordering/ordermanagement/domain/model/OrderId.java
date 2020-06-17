package mk.ukim.finki.emt.productordering.ordermanagement.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import mk.ukim.finki.emt.productordering.sharedkernel.domain.base.DomainObjectId;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@EqualsAndHashCode
public class OrderId extends DomainObjectId {

    private OrderId() {
        super("");
    }

    public OrderId(@NonNull String id) {
        super(id);
    }
}
