package nikola.hristovski.parking.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nikola.hristovski.sharedkernel.domain.base.AbstractEntity;

import javax.persistence.*;
import java.time.Instant;
import java.util.Set;

@Entity
@Table(name = "parking_slot")
@Getter
//@Setter
@AllArgsConstructor
public class ParkingSlot extends AbstractEntity<ParkingSlotId> {

    @Version
    private Long version;

    private boolean disabledPeopleOnly;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    Set<Occupation> occupations;

    public ParkingSlot() {

    }

    public void markAsDisabledPeopleOnly() {
        this.disabledPeopleOnly = true;
    }

    public boolean isAvailableFor(Instant from, Instant to) {
        return occupations.stream().
                filter(occupation -> !occupation.isCanceled())
                .noneMatch(occupation -> occupation.isInterferingWith(from, to));
    }

    public ParkingSlot(ParkingSlotId parkingSlotId, boolean disabledPeopleOnly, Set<Occupation> occupations) {
        super(parkingSlotId);
        this.disabledPeopleOnly = disabledPeopleOnly;
        this.occupations = occupations;
    }

    public void addOccupation(Occupation occupation) {
        this.occupations.add(occupation);
    }

    public Occupation getOccupation(OccupationId occupationId) {
        return occupations.stream()
                .filter(occupation -> occupation.getId().equals(occupationId))
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }

    public Occupation getOccupation(Instant from, Instant to) {
        return occupations.stream()
                .filter(occupation -> occupation.getFrom().equals(from) && occupation.getTo().equals(to))
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }


}
