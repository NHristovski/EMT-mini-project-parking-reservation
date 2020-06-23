package nikola.hristovski.sharedkernel.domain.time;

import nikola.hristovski.sharedkernel.domain.base.ValueObject;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class OpenHoursZoned implements ValueObject {

    private ZonedDateTime openingZoned;
    private ZonedDateTime closingZoned;
    private ZoneId zoneId;

    public OpenHoursZoned(ZonedDateTime openingZoned, ZonedDateTime closingZoned, ZoneId zoneId) {
        this.openingZoned = openingZoned;
        this.closingZoned = closingZoned;
        this.zoneId = zoneId;
    }

    public ZonedDateTime getOpeningZoned() {
        return openingZoned;
    }

    public ZonedDateTime getClosingZoned() {
        return closingZoned;
    }

    public ZoneId getZoneId() {
        return zoneId;
    }
}
