package mk.ukim.finki.emt.productordering.ordermanagement.domain.model;


import mk.ukim.finki.emt.productordering.sharedkernel.domain.base.DomainObjectId;

import javax.persistence.Embeddable;

@Embeddable
public class ProductId extends DomainObjectId {

    public ProductId(String id) {
        super(id);
    }


}
