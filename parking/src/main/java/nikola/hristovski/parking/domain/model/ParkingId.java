package nikola.hristovski.parking.domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import nikola.hristovski.sharedkernel.domain.base.DomainObjectId;

import javax.persistence.Embeddable;

@Embeddable
public class ParkingId extends DomainObjectId {

    public ParkingId() {
        super(DomainObjectId.randomId(ParkingId.class).toString());
    }

    @JsonCreator
    public ParkingId(String id) {
        super(id);
    }
}
