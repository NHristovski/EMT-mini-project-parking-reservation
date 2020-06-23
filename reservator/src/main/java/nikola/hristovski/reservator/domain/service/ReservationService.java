package nikola.hristovski.reservator.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nikola.hristovski.reservator.domain.model.Reservation;
import nikola.hristovski.reservator.domain.model.ReservationId;
import nikola.hristovski.reservator.domain.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public boolean canCancelTheReservation(ReservationId reservationId){

        Reservation reservation = reservationRepository.findById(reservationId).orElse(null);

        if (reservation == null){
            return false;
        }

        return Instant.now().plus(1,DAYS).isBefore(reservation.getFrom());
    }
}
