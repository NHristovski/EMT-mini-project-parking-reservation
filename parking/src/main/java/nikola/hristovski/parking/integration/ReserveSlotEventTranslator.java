package nikola.hristovski.parking.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import nikola.hristovski.sharedkernel.domain.base.DomainEvent;
import nikola.hristovski.sharedkernel.infra.eventlog.RemoteEventTranslator;
import nikola.hristovski.sharedkernel.infra.eventlog.StoredDomainEvent;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class ReserveSlotEventTranslator implements RemoteEventTranslator {

    private final ObjectMapper objectMapper;

    public ReserveSlotEventTranslator(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean supports(StoredDomainEvent remoteEvent) {
        return remoteEvent.domainEventClassName()
                .equals("nikola.hristovski.reservator.domain.event.ReserveSlot");
    }

    @Override
    public Optional<DomainEvent> translate(StoredDomainEvent remoteEvent) {
        ReserveSlotEvent value = remoteEvent.toDomainEvent(objectMapper, ReserveSlotEvent.class);
        return Optional.of(value);
    }
}
