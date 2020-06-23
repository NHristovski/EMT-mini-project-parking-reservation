package nikola.hristovski.reservator.port.client;

import lombok.extern.slf4j.Slf4j;
import nikola.hristovski.reservator.application.ParkingCatalog;
import nikola.hristovski.reservator.port.client.messages.GetCanReserveResponse;
import nikola.hristovski.reservator.port.client.messages.GetPriceResponse;
import nikola.hristovski.sharedkernel.domain.financial.Money;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Instant;
import java.util.Objects;

@Service
@Slf4j
public class ParkingCatalogClient implements ParkingCatalog {

    private final RestTemplate restTemplate;
    private final String serverUrl;

    ParkingCatalogClient(@Value("${app.parking-catalog.url}") String serverUrl,
                         @Value("${app.parking-catalog.connect-timeout-ms}") int connectTimeout,
                         @Value("${app.parking-catalog.read-timeout-ms}") int readTimeout) {
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
    public Money getPrice(String parkingId, Instant from, Instant to) {
        try {
            GetPriceResponse response = restTemplate.exchange(uri().path("/api/parkings/"
                    + parkingId + "/price")
                            .queryParam("from", from.toString())
                            .queryParam("to", to.toString())
                            .toUriString(),
                    HttpMethod.GET, null,
                    new ParameterizedTypeReference<GetPriceResponse>() {
                    }).getBody();

            Objects.requireNonNull(response, "Response must not be null!");

            return response.getPrice();
        } catch (Exception ex) {
            log.error("Error retrieving price", ex);
            return null;
        }
    }

    @Override
    public boolean canReserve(String parkingId, String parkingSlotId, Instant from, Instant to) {
        GetCanReserveResponse response = restTemplate.exchange(uri().path("/api/parkings/"
                        + parkingId + "/" + parkingSlotId + "/canReserve")
                        .queryParam("from", from.toString())
                        .queryParam("to", to.toString())
                        .toUriString(),
                HttpMethod.GET, null,
                new ParameterizedTypeReference<GetCanReserveResponse>() {
                }).getBody();

        Objects.requireNonNull(response,"Response can not be null!");
        return response.isCanReserve();
    }
}
