package nikola.hristovski.parking.domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import nikola.hristovski.sharedkernel.domain.base.DomainObjectId;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;

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
