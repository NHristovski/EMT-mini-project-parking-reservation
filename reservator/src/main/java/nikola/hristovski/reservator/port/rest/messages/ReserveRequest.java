package nikola.hristovski.reservator.port.rest.messages;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReserveRequest {
    private String parkingId;
    private String parkingSlotId;
    private Instant from;
    private Instant to;
}
