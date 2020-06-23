package nikola.hristovski.sharedkernel.domain.geo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.Setter;
import nikola.hristovski.sharedkernel.domain.base.ValueObject;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.Objects;

@Embeddable
@MappedSuperclass
@Getter
//@Setter
public class Address implements ValueObject {


    @Column(name = "street")
    @Embedded
    private Street street;

    @Column(name = "city")
    @Embedded
    private CityName city;

    @SuppressWarnings("unused") // Used by JPA only.
    protected Address() {
    }

    public Address(@NonNull Street street, @NonNull CityName city) {
        this.street = street;
        this.city = city;
    }

    @NonNull
    @JsonProperty("street")
    public Street street() {
        return street;
    }

    @NonNull
    @JsonProperty("city")
    public CityName city() {
        return city;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(street, address.street) &&
                Objects.equals(city, address.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, city);
    }

//    @JsonValue
    @Override
    public String toString() {
        return "Address{" +
                "street=" + street +
                ", city=" + city +
                '}';
    }
}
