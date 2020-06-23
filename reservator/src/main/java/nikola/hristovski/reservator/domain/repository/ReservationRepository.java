package nikola.hristovski.reservator.domain.repository;

import nikola.hristovski.reservator.domain.model.Reservation;
import nikola.hristovski.reservator.domain.model.ReservationId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, ReservationId> {
}
