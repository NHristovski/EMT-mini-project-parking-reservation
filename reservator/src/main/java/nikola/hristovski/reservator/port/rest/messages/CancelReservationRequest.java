package nikola.hristovski.reservator.port.rest.messages;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nikola.hristovski.reservator.domain.model.ReservationId;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CancelReservationRequest {
    String reservationId;
}
