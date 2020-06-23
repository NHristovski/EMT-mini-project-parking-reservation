package nikola.hristovski.sharedkernel.infra.eventlog;


import nikola.hristovski.sharedkernel.domain.base.RemoteEventLog;

public interface RemoteEventLogService {

    String source();

    RemoteEventLog currentLog(long lastProcessedId);

}
