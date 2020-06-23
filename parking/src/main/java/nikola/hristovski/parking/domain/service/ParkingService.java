package nikola.hristovski.parking.domain.service;

import lombok.RequiredArgsConstructor;
import nikola.hristovski.parking.domain.exception.FailedToReserveParkingSpotException;
import nikola.hristovski.parking.domain.model.*;
import nikola.hristovski.parking.domain.repository.ParkingRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class ParkingService {

    private final ParkingRepository parkingRepository;

    public void reserve(ParkingId parkingId, UserId userId, ParkingSlotId parkingSlotId, Instant from, Instant to)
            throws FailedToReserveParkingSpotException {
        Parking parking = parkingRepository.findById(parkingId)
                .orElseThrow(() -> new FailedToReserveParkingSpotException("Parking does not exist"));

        ParkingSlot parkingSlot = parking.getParkingSlots().stream()
                .filter(slot -> slot.getId().equals(parkingSlotId))
                .findFirst()
                .orElseThrow(() -> new FailedToReserveParkingSpotException("ParkingSlot does not exist"));

        if (!parkingSlot.isAvailableFor(from,to)){
            throw new FailedToReserveParkingSpotException("ParkingSlot is not available!");
        }

        parkingSlot.getOccupations().add(new Occupation(from, userId, to, false));

        parkingRepository.save(parking);
    }

}
