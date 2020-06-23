package nikola.hristovski.parking.port.rest.messages;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nikola.hristovski.parking.domain.model.Parking;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetAllAvailableParkingsResponse {
    List<Parking> parkingList;
}
