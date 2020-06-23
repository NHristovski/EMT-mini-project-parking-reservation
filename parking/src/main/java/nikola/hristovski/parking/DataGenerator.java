package nikola.hristovski.parking;

import lombok.extern.slf4j.Slf4j;
import nikola.hristovski.parking.domain.model.*;
import nikola.hristovski.parking.domain.repository.ParkingRepository;
import nikola.hristovski.sharedkernel.domain.financial.Currency;
import nikola.hristovski.sharedkernel.domain.financial.Price;
import nikola.hristovski.sharedkernel.domain.geo.*;
import nikola.hristovski.sharedkernel.domain.time.OpenHours;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Set;

import static java.time.temporal.ChronoUnit.HOURS;

@Component
@Slf4j
class DataGenerator {

    private final ParkingRepository parkingRepository;

    DataGenerator(ParkingRepository parkingRepository) {
        this.parkingRepository = parkingRepository;
    }

    @PostConstruct
    @Transactional
    public void generateData() {
        Instant today = Instant.now();
        Instant tomorrow = today.plus(24, HOURS);
        if (parkingRepository.findAll().size() == 0) {
            Parking parking = new Parking(new ParkingId("1"),
                    new Location(
                            new Address(new Street("name", "number"), new CityName("skopje")),
                            new Coordinates(1.1d, 2.2d)
                    ),
                    new OpenHours(today, tomorrow),
                    Set.of(
                            new ParkingSlot(
                                    new ParkingSlotId("1"),
                                    false,
                                    Set.of(
                                            new Occupation(
                                                    Instant.now(),
                                                    new UserId("1"),
                                                    Instant.now().plus(1, ChronoUnit.HOURS)
                                            )
                                    )
                            ),
                            new ParkingSlot(
                                    new ParkingSlotId("2"),
                                    true,
                                    Set.of(
                                            new Occupation(
                                                    Instant.now(),
                                                    new UserId("1"),
                                                    Instant.now().plus(2, ChronoUnit.HOURS)
                                            ),
                                            new Occupation(
                                                    Instant.now(),
                                                    new UserId("2"),
                                                    Instant.now().plus(3, ChronoUnit.HOURS)
                                                    )
                                    )
                            )
                    ),
                    new Price(Currency.MKD, 30, 3, 20));

            parkingRepository.save(parking);
        }
    }
}
