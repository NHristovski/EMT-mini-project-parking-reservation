package nikola.hristovski.parking.integration;

import lombok.RequiredArgsConstructor;
import nikola.hristovski.sharedkernel.domain.base.RemoteEventLog;
import nikola.hristovski.sharedkernel.infra.eventlog.StoredDomainEvent;

import java.util.List;

@RequiredArgsConstructor
public class ReservationsRemoteEventLog implements RemoteEventLog {

    private final List<StoredDomainEvent> events;

    @Override
    public List<StoredDomainEvent> events() {
        return events;
    }
}
