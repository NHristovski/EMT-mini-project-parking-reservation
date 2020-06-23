package nikola.hristovski.parking.port.rest.messages;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetCanReserveResponse {
    private boolean canReserve;
}
