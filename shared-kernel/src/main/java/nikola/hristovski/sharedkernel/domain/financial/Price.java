package nikola.hristovski.sharedkernel.domain.financial;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nikola.hristovski.sharedkernel.domain.base.ValueObject;
import org.springframework.lang.NonNull;

import javax.persistence.Embeddable;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

@Embeddable
@Getter
public class Price implements ValueObject {

    @JsonProperty("currency")
    private final Currency currency;

    @JsonProperty("amount")
    private final Integer amount;

    @JsonProperty("discountLimit")
    private final Integer discountLimitInHours;

    @JsonProperty("discountPercent")
    private final Integer discountPercent;

    public Price(){
        this.currency = null;
        this.amount = 0;
        this.discountLimitInHours = 0;
        this.discountPercent = 0;
    }

    @JsonCreator
    public Price(@NonNull @JsonProperty("currency") Currency currency,
                 @JsonProperty("amount") int amount,
                 @JsonProperty("discountLimit") int discountLimitInHours,
                 @JsonProperty("discountPercent") int discountPercent) {
        this.currency = Objects.requireNonNull(currency, "currency must not be null");
        this.amount = amount;
        this.discountLimitInHours = discountLimitInHours;
        this.discountPercent = discountPercent;
    }

    public Money calculatePrice(Instant from, Instant to) {
        long hours = from.until(to, ChronoUnit.HOURS);

        double fullPrice = hours * amount;

        if (hours < discountLimitInHours) {
            return new Money(currency, fullPrice);
        }

        double discount = (fullPrice / 100.0) * discountPercent;
        return new Money(this.currency, fullPrice - discount);
    }
}
