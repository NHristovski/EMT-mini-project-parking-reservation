package nikola.hristovski.parking.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nikola.hristovski.sharedkernel.domain.base.AbstractEntity;
import nikola.hristovski.sharedkernel.domain.financial.Money;
import nikola.hristovski.sharedkernel.domain.financial.Price;
import nikola.hristovski.sharedkernel.domain.geo.Location;
import nikola.hristovski.sharedkernel.domain.time.OpenHours;

import javax.persistence.*;
import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "parking")
@NoArgsConstructor
@AllArgsConstructor
@Getter
//@Setter
public class Parking extends AbstractEntity<ParkingId> {

    @Version
    private Long version;

    @Embedded
    private Location location;

    @Embedded
    private OpenHours openHours;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<ParkingSlot> parkingSlots;

    @Embedded
    private Price slotPrice;

    public Parking(ParkingId id, Location location, OpenHours openHours, Set<ParkingSlot> parkingSlots, Price slotPrice) {
        super(id);
        this.location = location;
        this.openHours = openHours;
        this.parkingSlots = parkingSlots;
        this.slotPrice = slotPrice;
    }

    public Set<ParkingSlot> getFreeParkingSlots(Instant from, Instant to, boolean includeParkingSlotsForDisabledPeople) {
        Set<ParkingSlot> allAvailable = parkingSlots
                .stream()
                .filter(parkingSlot -> parkingSlot.isAvailableFor(from, to))
                .collect(Collectors.toSet());

        if (!includeParkingSlotsForDisabledPeople) {
            return allAvailable.stream()
                    .filter(slot -> !slot.isDisabledPeopleOnly())
                    .collect(Collectors.toSet());
        }

        return allAvailable;
    }

    public Money calculatePrice(Instant from, Instant to) {
        return slotPrice.calculatePrice(from, to);
    }

    public ParkingSlot getParkingSlot(ParkingSlotId parkingSlotId) {
        return parkingSlots.stream()
                .filter(parkingSlot -> parkingSlot.getId().equals(parkingSlotId))
                .findFirst().orElseThrow(RuntimeException::new);
    }
}
