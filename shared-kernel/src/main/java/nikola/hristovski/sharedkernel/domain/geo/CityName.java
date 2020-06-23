package nikola.hristovski.sharedkernel.domain.geo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.Setter;
import nikola.hristovski.sharedkernel.domain.base.ValueObject;
import org.springframework.lang.NonNull;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

/**
 * Value object representing the name of a city.
 */
@Embeddable
@Getter
//@Setter
public class CityName implements ValueObject {

    @Column(name="city_name")
    private final String name;

    //unused
    private CityName() {this.name="";}

    @JsonCreator
    public CityName(@NonNull String name) {
        this.name = Objects.requireNonNull(name, "name must not be null");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CityName cityName = (CityName) o;
        return Objects.equals(name, cityName.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

//   0 @JsonValue
    @Override
    public String toString() {
        return "CityName{" +
                "name='" + name + '\'' +
                '}';
    }
}
