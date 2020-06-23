package nikola.hristovski.reservator.port.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nikola.hristovski.reservator.application.ReservationCatalog;
import nikola.hristovski.reservator.domain.model.ReservationId;
import nikola.hristovski.reservator.domain.service.ReservationService;
import nikola.hristovski.reservator.port.rest.messages.CancelReservationRequest;
import nikola.hristovski.reservator.port.rest.messages.CancelReservationResponse;
import nikola.hristovski.reservator.port.rest.messages.ReserveRequest;
import nikola.hristovski.reservator.port.rest.messages.ReserveResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/reservation")
@RequiredArgsConstructor
@Slf4j
public class ReservationController {

    private final ReservationCatalog reservationCatalog;
    private final ReservationService reservationService;

    @PostMapping("/{parkingId}/{parkingStopId}")
    public ResponseEntity<ReserveResponse> reserve(@Valid @RequestBody ReserveRequest reserveRequest) {
        if (!reservationCatalog.canReserve(reserveRequest)) {
            return ResponseEntity.badRequest().build();
        }

        ReservationId reservation = reservationCatalog.createReservation(reserveRequest);

        return ResponseEntity.ok(
                ReserveResponse.builder().reservationId(reservation).build()
        );
    }

    //и исто така може да ја откаже резервацијата до 1 ден пред почнување на истата. ( RESERVATOR )
    @PostMapping("cancel/{parkingId}/{parkingStopId}")
    public ResponseEntity<CancelReservationResponse> cancelReservation(
            @Valid @RequestBody CancelReservationRequest cancelReservationRequest) {

        if (!reservationService.canCancelTheReservation(
                new ReservationId(cancelReservationRequest.getReservationId()))) {
            return ResponseEntity.badRequest().build();
        }

        ReservationId reservationId = reservationCatalog.cancelReservation(cancelReservationRequest);

        return ResponseEntity.ok(
                CancelReservationResponse.builder().reservationId(reservationId).build()
        );
    }

}
