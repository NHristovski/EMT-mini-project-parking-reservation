package nikola.hristovski.parking.domain.repository;

import nikola.hristovski.parking.domain.model.Parking;
import nikola.hristovski.parking.domain.model.ParkingId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingRepository extends JpaRepository<Parking, ParkingId> {
}
