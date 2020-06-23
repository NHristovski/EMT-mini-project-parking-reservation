package nikola.hristovski.parking.integration;

import nikola.hristovski.sharedkernel.domain.base.RemoteEventLog;
import nikola.hristovski.sharedkernel.domain.financial.Money;
import nikola.hristovski.sharedkernel.infra.eventlog.RemoteEventLogService;
import nikola.hristovski.sharedkernel.infra.eventlog.StoredDomainEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Service
public class ReservationsEventLogService implements RemoteEventLogService {

    private final RestTemplate restTemplate;
    private final String serverUrl;

    ReservationsEventLogService(@Value("${app.reservation-catalog.url}") String serverUrl,
                         @Value("${app.reservation-catalog.connect-timeout-ms}") int connectTimeout,
                         @Value("${app.reservation-catalog.read-timeout-ms}") int readTimeout) {
        this.serverUrl = serverUrl;
        restTemplate = new RestTemplate();

        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(connectTimeout);
        requestFactory.setReadTimeout(readTimeout);

        restTemplate.setRequestFactory(requestFactory);
    }

    private UriComponentsBuilder uri() {
        return UriComponentsBuilder.fromUriString(serverUrl);
    }

    @Override
    public String source() {
        return "reservations";
    }

    @Override
    public RemoteEventLog currentLog(long lastProcessedId) {
        List<StoredDomainEvent> response = restTemplate.exchange(uri().path("/api/event-log/" + lastProcessedId)
                        .toUriString(),
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<StoredDomainEvent>>() {
                }).getBody();

        return new ReservationsRemoteEventLog(response);
    }
}
