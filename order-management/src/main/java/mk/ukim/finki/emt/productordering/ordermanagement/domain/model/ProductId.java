package mk.ukim.finki.emt.productordering.ordermanagement.domain.model;


import com.fasterxml.jackson.annotation.JsonCreator;
import mk.ukim.finki.emt.productordering.sharedkernel.domain.base.DomainObjectId;

import javax.persistence.Embeddable;

@Embeddable
public class ProductId extends DomainObjectId {

    private ProductId() {
        super(DomainObjectId.randomId(ProductId.class).toString());
    }

    @JsonCreator
    public ProductId(String id) {
        super(id);
    }


}
