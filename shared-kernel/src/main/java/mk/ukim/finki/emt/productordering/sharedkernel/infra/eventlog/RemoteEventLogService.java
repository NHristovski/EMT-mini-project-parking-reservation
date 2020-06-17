package mk.ukim.finki.emt.productordering.sharedkernel.infra.eventlog;


import mk.ukim.finki.emt.productordering.sharedkernel.domain.base.RemoteEventLog;

public interface RemoteEventLogService {

    String source();

    RemoteEventLog currentLog(long lastProcessedId);

}
