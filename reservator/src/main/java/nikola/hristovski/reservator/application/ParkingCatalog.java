package nikola.hristovski.reservator.application;

import nikola.hristovski.sharedkernel.domain.financial.Money;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;

import java.time.Instant;

public interface ParkingCatalog {

    Money getPrice(String parkingId, Instant from, Instant to);

    boolean canReserve(String parkingId, String parkingSlotId, Instant from, Instant to);
}
