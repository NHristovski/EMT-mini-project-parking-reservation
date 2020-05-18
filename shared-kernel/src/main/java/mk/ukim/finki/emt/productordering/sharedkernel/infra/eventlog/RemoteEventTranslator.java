package mk.ukim.finki.emt.productordering.sharedkernel.infra.eventlog;


import mk.ukim.finki.emt.productordering.sharedkernel.domain.base.DomainEvent;

import java.util.Optional;

public interface RemoteEventTranslator {

    boolean supports(StoredDomainEvent storedDomainEvent);

    Optional<DomainEvent> translate(StoredDomainEvent remoteEvent);
}
