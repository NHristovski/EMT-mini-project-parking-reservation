package nikola.hristovski.sharedkernel.domain.time;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import nikola.hristovski.sharedkernel.domain.base.ValueObject;
import org.springframework.lang.NonNull;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;


@Embeddable
@MappedSuperclass
@Getter
//@Setter
@Slf4j
public class OpenHours implements ValueObject {

    // TODO SHOULD BE LOCAL TIME NOT INSTANT
    private final Instant openingUTC;
    private final Instant closingUTC;

    public OpenHours() {
        this.openingUTC = Instant.now();
        this.closingUTC = Instant.now();
    }

    @JsonCreator
    public OpenHours(@NonNull Instant openingUTC,@NonNull Instant closingUTC) {

        this.openingUTC = openingUTC;
        this.closingUTC = closingUTC;
    }

    public OpenHoursZoned showTimeOpen(ZoneId zoneId) {
        return new OpenHoursZoned(this.openingUTC.atZone(zoneId), this.closingUTC.atZone(zoneId), zoneId);
    }

    public boolean isOpenNow() {
        //TODO CHANGE THIS METHOD when opening and closing hours are no longer Instant
        int closingHour = closingUTC.atZone(ZoneOffset.UTC).getHour();
        int now = Instant.now().atZone(ZoneOffset.UTC).getHour();
        return now <= closingHour;
    }

    public Instant getOpeningUTC() {
        return this.openingUTC;
    }

    public Instant getClosingUTC() {
        return this.closingUTC;
    }

    @Override
//    @JsonValue
    public String toString() {
        return "OpenHours{" +
                "openingUTC=" + this.openingUTC +
                ", closingUTC=" + this.closingUTC +
                '}';
    }
}
