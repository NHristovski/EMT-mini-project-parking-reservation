package nikola.hristovski.parking.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nikola.hristovski.parking.domain.exception.FailedToReserveParkingSpotException;
import nikola.hristovski.parking.domain.model.*;
import nikola.hristovski.parking.domain.repository.ParkingRepository;
import nikola.hristovski.parking.domain.service.ParkingService;
import nikola.hristovski.parking.integration.CancelReservationForSlotEvent;
import nikola.hristovski.parking.integration.ReserveSlotEvent;
import nikola.hristovski.parking.port.rest.messages.GetPriceResponse;
import nikola.hristovski.sharedkernel.domain.financial.Money;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ParkingCatalog {

    private final ParkingRepository parkingRepository;
    private final ParkingService parkingService;

    @NonNull
    public List<Parking> findAll() {
        return parkingRepository.findAll();
    }

    @NonNull
    public Optional<Parking> findById(@NonNull ParkingId parkingId) {
        Objects.requireNonNull(parkingId, "parkingId must not be null");
        return parkingRepository.findById(parkingId);
    }

    public void reserve(@NonNull ParkingId parkingId, @NonNull UserId userId, @NonNull ParkingSlotId parkingSlotId,
                        @NonNull Instant from, @NonNull Instant to)
            throws FailedToReserveParkingSpotException {

        Objects.requireNonNull(parkingId, "parkingId must not be null");
        Objects.requireNonNull(parkingSlotId, "parkingSlotId must not be null");
        Objects.requireNonNull(from, "from must not be null");
        Objects.requireNonNull(to, "to must not be null");
        parkingService.reserve(parkingId, userId, parkingSlotId, from, to);
    }

    public Money getPrice(ParkingId parkingId, Instant from, Instant to){
        return findById(parkingId)
                .map(parking -> parking.calculatePrice(from,to))
                .orElseThrow(RuntimeException::new);
    }


    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void onReserveSlot(ReserveSlotEvent event) {
        log.info("IN ON RESERVE SLOT EVENT");

        Parking parking = parkingRepository.findById(event.getParkingId()).orElseThrow(RuntimeException::new);

        ParkingSlot parkingSlot = parking.getParkingSlot(event.getParkingSlotId());

        parkingSlot.addOccupation(new Occupation(event.getFrom(), event.getUserId(), event.getTo(), false));

        parkingRepository.save(parking);
        log.info("Parking Reserved");
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void onCancelReservationForSlot(CancelReservationForSlotEvent event) {
        log.info("IN ON CANCEL RESERVATION FOR SLOT EVENT");

        Parking parking = parkingRepository.findById(event.getParkingId()).orElseThrow(RuntimeException::new);

        ParkingSlot parkingSlot = parking.getParkingSlot(event.getParkingSlotId());

        Occupation occupation = parkingSlot.getOccupation(event.getFrom(),event.getTo());

        occupation.cancel();

        parkingRepository.save(parking);

        log.info("Reservation canceled");
    }
}
