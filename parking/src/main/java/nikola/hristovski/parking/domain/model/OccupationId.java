package nikola.hristovski.parking.domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import nikola.hristovski.sharedkernel.domain.base.DomainObjectId;

import javax.persistence.Embeddable;

@Embeddable
public class OccupationId extends DomainObjectId {

    public OccupationId() {
        super(DomainObjectId.randomId(OccupationId.class).toString());
    }

    @JsonCreator
    public OccupationId(String id) {
        super(id);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
