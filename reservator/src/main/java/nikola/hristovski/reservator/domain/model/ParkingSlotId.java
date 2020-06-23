package nikola.hristovski.reservator.domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import nikola.hristovski.sharedkernel.domain.base.DomainObjectId;

import javax.persistence.Embeddable;

@Embeddable
public class ParkingSlotId extends DomainObjectId {

    public ParkingSlotId() {
        super(DomainObjectId.randomId(ParkingSlotId.class).toString());
    }

    @JsonCreator
    public ParkingSlotId(String id) {
        super(id);
    }

}
