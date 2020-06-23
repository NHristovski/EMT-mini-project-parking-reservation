package nikola.hristovski.reservator.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nikola.hristovski.reservator.domain.event.CancelReservationForSlot;
import nikola.hristovski.reservator.domain.event.ReserveSlot;
import nikola.hristovski.reservator.domain.model.*;
import nikola.hristovski.reservator.domain.repository.ReservationRepository;
import nikola.hristovski.reservator.port.rest.messages.CancelReservationRequest;
import nikola.hristovski.reservator.port.rest.messages.ReserveRequest;
import nikola.hristovski.sharedkernel.domain.financial.Money;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.time.Instant;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ReservationCatalog {

    private final ReservationRepository reservationRepository;

    private final ApplicationEventPublisher applicationEventPublisher;

    private final Validator validator;

    private final ParkingCatalog parkingCatalog;

    public ReservationId createReservation(@NonNull ReserveRequest reserveRequest) {
        Objects.requireNonNull(reserveRequest, "reserveRequest must not be null");

        var constraintViolations = validator.validate(reserveRequest);

        if (constraintViolations.size() > 0) {
            throw new ConstraintViolationException("The ReserveRequest is not valid", constraintViolations);
        }

        Reservation reservation = reservationRepository.saveAndFlush(toDomainModel(reserveRequest));

        ReserveSlot slotReservedEvent = ReserveSlot.builder()
                .from(reserveRequest.getFrom())
                .to(reserveRequest.getTo())
                .parkingId(new ParkingId(reserveRequest.getParkingId()))
                .parkingSlotId(new ParkingSlotId(reserveRequest.getParkingSlotId()))
                .userId(UserId.randomId(UserId.class))
                .occurredOn(Instant.now())
                .build();

        applicationEventPublisher. publishEvent(slotReservedEvent);

        return reservation.getId();
    }

    public ReservationId cancelReservation(@NonNull CancelReservationRequest cancelReservationRequest) {
        Objects.requireNonNull(cancelReservationRequest, "CancelReservationRequest must not be null");

        var constraintViolations = validator.validate(cancelReservationRequest);

        if (constraintViolations.size() > 0) {
            throw new ConstraintViolationException("The CancelReservationRequest is not valid", constraintViolations);
        }


        Reservation reservation = reservationRepository.findById(
                new ReservationId(cancelReservationRequest.getReservationId()))
                .orElseThrow(RuntimeException::new);

        reservation.cancel();

        reservationRepository.saveAndFlush(reservation);

        CancelReservationForSlot cancelReservationEvent = CancelReservationForSlot.builder()
                .occurredOn(Instant.now())
                .parkingId(reservation.getParkingId())
                .parkingSlotId(reservation.getParkingSlotId())
                .from(reservation.getFrom())
                .to(reservation.getTo())
                .build();

        applicationEventPublisher.publishEvent(cancelReservationEvent);

        return reservation.getId();
    }

    @NonNull
    private Reservation toDomainModel(@NonNull ReserveRequest reserveRequest) {

        Money totalPrice = parkingCatalog
                .getPrice(reserveRequest.getParkingId(), reserveRequest.getFrom(), reserveRequest.getTo());

        return new Reservation(
                new UserId("test user id"),
                new ParkingId(reserveRequest.getParkingId()),
                new ParkingSlotId(reserveRequest.getParkingSlotId()),
                OccupationId.randomId(OccupationId.class),
                reserveRequest.getFrom(),
                reserveRequest.getTo(),
                totalPrice
        );
    }


    public boolean canReserve(ReserveRequest reserveRequest) {
        return parkingCatalog.canReserve(reserveRequest.getParkingId()
        ,reserveRequest.getParkingSlotId(),reserveRequest.getFrom(),reserveRequest.getTo());
    }
}
