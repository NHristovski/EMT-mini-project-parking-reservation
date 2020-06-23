package nikola.hristovski.parking.port.rest.messages;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nikola.hristovski.sharedkernel.domain.financial.Money;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetPriceResponse {
    Money price;
}
