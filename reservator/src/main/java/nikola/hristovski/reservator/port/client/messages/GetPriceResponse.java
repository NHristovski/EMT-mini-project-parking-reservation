package nikola.hristovski.reservator.port.client.messages;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nikola.hristovski.sharedkernel.domain.financial.Money;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
//TODO MOVE IN SHARED KERNEL
public class GetPriceResponse {
    Money price;
}
