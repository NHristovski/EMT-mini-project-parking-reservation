package nikola.hristovski.sharedkernel.domain.base;


import nikola.hristovski.sharedkernel.infra.eventlog.StoredDomainEvent;

import java.util.List;

public interface RemoteEventLog {

    List<StoredDomainEvent> events();
}
