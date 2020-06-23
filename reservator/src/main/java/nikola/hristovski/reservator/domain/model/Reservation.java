package nikola.hristovski.reservator.domain.model;

import lombok.*;
import nikola.hristovski.sharedkernel.domain.base.AbstractEntity;
import nikola.hristovski.sharedkernel.domain.financial.Money;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "reservation")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class Reservation extends AbstractEntity<ReservationId> {

    @Version
    private Long version;

    @Embedded
    @AttributeOverride(name="id",column = @Column(name="user_id",nullable = false))
    private UserId userId;

    @Embedded
    @AttributeOverride(name="id",column = @Column(name="parking_id",nullable = false))
    private ParkingId parkingId;

    @Embedded
    @AttributeOverride(name="id",column = @Column(name="parking_slot_id",nullable = false))
    private ParkingSlotId parkingSlotId;

    @Embedded
    @AttributeOverride(name="id",column = @Column(name="occupation_id",nullable = false))
    private OccupationId occupationId;

    @Column(name = "from_instant")
    private Instant from;
    @Column(name = "to_instant")
    private Instant to;

    @Embedded
    private Money totalPrice;

    private boolean deleted;

    public Reservation(UserId userId, ParkingId parkingId, ParkingSlotId parkingSlotId, OccupationId occupationId, Instant from, Instant to, Money totalPrice) {
        super(ReservationId.randomId(ReservationId.class));
        this.userId = userId;
        this.parkingId = parkingId;
        this.parkingSlotId = parkingSlotId;
        this.occupationId = occupationId;
        this.from = from;
        this.to = to;
        this.totalPrice = totalPrice;
        this.deleted = false;
    }

    public void cancel(){
        this.deleted = true;
    }
}
