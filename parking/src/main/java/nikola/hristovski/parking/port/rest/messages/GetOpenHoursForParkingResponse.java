package nikola.hristovski.parking.port.rest.messages;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nikola.hristovski.sharedkernel.domain.time.OpenHoursZoned;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetOpenHoursForParkingResponse {
    private OpenHoursZoned openHoursZoned;
}
