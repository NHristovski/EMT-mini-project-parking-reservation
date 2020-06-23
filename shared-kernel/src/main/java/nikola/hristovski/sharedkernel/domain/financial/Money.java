package nikola.hristovski.sharedkernel.domain.financial;

import lombok.Getter;
import nikola.hristovski.sharedkernel.domain.base.ValueObject;
import org.springframework.lang.NonNull;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Objects;

@Embeddable
@Getter
public class Money implements ValueObject {

    @Enumerated(value = EnumType.STRING)
    private final Currency currency;

    private final Double amount;

    public Money(@NonNull Currency currency, @NonNull Double amount) {
        this.currency = currency;
        this.amount = amount;
    }

    public static Money valueOf(Currency currency, Double amount) {
        return new Money(currency,amount);
    }

    private Money() {
        this.currency=null;
        this.amount = 0d;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Money money = (Money) obj;
        return amount == money.amount &&
                currency == money.currency;
    }

    @Override
    public int hashCode() {
        return Objects.hash(currency, amount);
    }

    @Override
    public String toString() {
        return String.format("%s %d", currency, amount);
    }
}
