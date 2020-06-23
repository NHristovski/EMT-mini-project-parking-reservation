package nikola.hristovski.reservator.domain.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import nikola.hristovski.reservator.domain.model.ParkingId;
import nikola.hristovski.reservator.domain.model.ParkingSlotId;
import nikola.hristovski.reservator.domain.model.UserId;
import nikola.hristovski.sharedkernel.domain.base.DomainEvent;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ReserveSlot implements DomainEvent {

    @JsonProperty("parkingId")
    private final ParkingId parkingId;

    @JsonProperty("parkingSlotId")
    private final ParkingSlotId parkingSlotId;

    @JsonProperty("userId")
    private final UserId userId;

    @JsonProperty("from")
    private final Instant from;
    @JsonProperty("to")
    private final Instant to;

    @JsonProperty("occurredOn")
    private final Instant occurredOn;

    @JsonCreator
    public ReserveSlot(
            @JsonProperty("parkingId") @NonNull ParkingId parkingId,
            @JsonProperty("userId") @NonNull UserId userId,
            @JsonProperty("parkingSlotId") @NonNull ParkingSlotId parkingSlotId,
            @JsonProperty("from") @NonNull Instant from,
            @JsonProperty("to") @NonNull Instant to,
            @JsonProperty("occurredOn") @NonNull Instant occurredOn) {
        this.parkingId = Objects.requireNonNull(parkingId, "parkingId must not be null");
        this.userId = Objects.requireNonNull(userId, "userId must not be null");
        this.parkingSlotId = Objects.requireNonNull(parkingSlotId, "parkingSlotId must not be null");
        this.from = Objects.requireNonNull(from, "from must not be null");
        this.to = Objects.requireNonNull(to, "to must not be null");
        this.occurredOn = Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }

    @Override
    @NonNull
    public Instant occurredOn() {
        return occurredOn;
    }
}
