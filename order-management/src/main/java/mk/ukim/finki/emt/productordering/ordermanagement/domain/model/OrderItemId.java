package mk.ukim.finki.emt.productordering.ordermanagement.domain.model;


import mk.ukim.finki.emt.productordering.sharedkernel.domain.base.DomainObjectId;

import javax.persistence.Embeddable;

@Embeddable
public class OrderItemId extends DomainObjectId {

    private String id;

    public OrderItemId(String uuid) {
        super(uuid);
        this.id=uuid;
    }
}
