package nikola.hristovski.parking.port.rest;

import lombok.extern.slf4j.Slf4j;
import nikola.hristovski.parking.application.ParkingCatalog;
import nikola.hristovski.parking.domain.model.ParkingId;
import nikola.hristovski.parking.domain.model.ParkingSlotId;
import nikola.hristovski.parking.port.rest.messages.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.ZoneId;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/parkings")
@Slf4j
class ParkingController {

    private final ParkingCatalog parkingCatalog;

    ParkingController(ParkingCatalog parkingCatalog) {
        this.parkingCatalog = parkingCatalog;
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetParkingResponse> getParkingById(@PathVariable("id") String parkingId) {
        return parkingCatalog.findById(new ParkingId(parkingId))
                .map(parking -> ResponseEntity.ok(
                        GetParkingResponse.builder()
                                .parking(parking)
                                .build()
                ))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<GetAllAvailableParkingsResponse> getAllAvailableParkings() {
        return ResponseEntity.ok(
                GetAllAvailableParkingsResponse.builder()
                        .parkingList(
                                parkingCatalog.findAll().stream()
                                        .peek(parking -> System.out.print(parking.getId().toString() + " is open now: "
                                        + parking.getOpenHours().isOpenNow()))
                                        .filter(parking -> parking.getOpenHours().isOpenNow())
                                        .collect(Collectors.toList())
                        )
                        .build()
        );
    }

    @GetMapping("/{id}/openHours/{zone}")
    public ResponseEntity<GetOpenHoursForParkingResponse> getOpenHoursForParking(
            @PathVariable("id") String parkingId,
            @PathVariable("zone") String zone) {

        ZoneId zoneId = ZoneId.of(zone);

        return parkingCatalog.findById(new ParkingId(parkingId))
                .map(parking -> ResponseEntity.ok(
                        GetOpenHoursForParkingResponse.builder()
                                .openHoursZoned(parking.getOpenHours().showTimeOpen(zoneId))
                                .build()
                ))
                .orElse(ResponseEntity.notFound().build());
    }


    @GetMapping("/{id}/price")
    public ResponseEntity<GetPriceResponse> getPrice(@PathVariable("id") String parkingId,
                                                     @RequestParam("from") String from,
                                                     @RequestParam("to") String to){
        return parkingCatalog.findById(new ParkingId(parkingId))
                .map(parking -> ResponseEntity.ok(
                        GetPriceResponse.builder()
                                .price(parking.calculatePrice(Instant.parse(from),Instant.parse(to)))
                                .build()
                ))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{parkingId}/{parkingSlotId}/canReserve")
    public ResponseEntity<GetCanReserveResponse> canReserve(@PathVariable("parkingId") String parkingId,
                                                            @PathVariable("parkingSlotId") String parkingSlotId,
                                                            @RequestParam("from") String from,
                                                            @RequestParam("to") String to){
        return parkingCatalog.findById(new ParkingId(parkingId))
                .map(parking -> ResponseEntity.ok(
                        GetCanReserveResponse.builder()
                            .canReserve(
                                    parking.getParkingSlot(new ParkingSlotId(parkingSlotId))
                                    .isAvailableFor(Instant.parse(from),Instant.parse(to))
                            )
                                .build()
                ))
                .orElse(ResponseEntity.notFound().build());
    }
}
