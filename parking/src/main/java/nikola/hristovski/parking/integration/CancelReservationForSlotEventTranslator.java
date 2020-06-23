package nikola.hristovski.parking.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import nikola.hristovski.sharedkernel.domain.base.DomainEvent;
import nikola.hristovski.sharedkernel.infra.eventlog.RemoteEventTranslator;
import nikola.hristovski.sharedkernel.infra.eventlog.StoredDomainEvent;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CancelReservationForSlotEventTranslator implements RemoteEventTranslator {

    private final ObjectMapper objectMapper;

    public CancelReservationForSlotEventTranslator(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean supports(StoredDomainEvent remoteEvent) {
        return remoteEvent.domainEventClassName()
                .equals("nikola.hristovski.reservator.domain.event.CancelReservationForSlot");
    }

    @Override
    public Optional<DomainEvent> translate(StoredDomainEvent remoteEvent) {
        return Optional.of(remoteEvent.toDomainEvent(objectMapper, CancelReservationForSlotEvent.class));
    }
}
