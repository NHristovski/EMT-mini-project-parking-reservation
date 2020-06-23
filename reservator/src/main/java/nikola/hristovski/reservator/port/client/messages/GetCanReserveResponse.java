package nikola.hristovski.reservator.port.client.messages;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
//TODO MOVE IN SHARED KERNEL
public class GetCanReserveResponse {
    private boolean canReserve;
}
