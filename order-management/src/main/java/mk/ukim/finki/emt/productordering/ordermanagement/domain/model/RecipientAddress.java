package mk.ukim.finki.emt.productordering.ordermanagement.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import mk.ukim.finki.emt.productordering.sharedkernel.domain.geo.Address;
import mk.ukim.finki.emt.productordering.sharedkernel.domain.geo.CityName;
import mk.ukim.finki.emt.productordering.sharedkernel.domain.geo.Country;
import org.springframework.lang.NonNull;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class RecipientAddress extends Address {

    @Column(name = "recipient_name")
    private String name;

    @SuppressWarnings("unused") // Used by JPA only.
    protected RecipientAddress() {
    }

    public RecipientAddress(@NonNull String name, @NonNull String address,
                            @NonNull CityName city, @NonNull Country country) {
        super(address, city, country);
        this.name = Objects.requireNonNull(name, "name must not be null");
    }

    @NonNull
    @JsonProperty("name")
    public String name() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        RecipientAddress that = (RecipientAddress) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name);
    }
}
