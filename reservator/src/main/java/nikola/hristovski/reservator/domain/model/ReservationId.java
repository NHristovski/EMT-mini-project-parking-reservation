package nikola.hristovski.reservator.domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.EqualsAndHashCode;
import nikola.hristovski.sharedkernel.domain.base.DomainObjectId;

import javax.persistence.Embeddable;

@Embeddable
@EqualsAndHashCode
public class ReservationId extends DomainObjectId {

    public ReservationId() {
        super(DomainObjectId.randomId(ReservationId.class).toString());
    }

    @JsonCreator
    public ReservationId(String id) {
        super(id);
    }
}
