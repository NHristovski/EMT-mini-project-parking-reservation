package nikola.hristovski.sharedkernel.domain.geo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.Setter;
import nikola.hristovski.sharedkernel.domain.base.ValueObject;
import org.springframework.lang.NonNull;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

@Embeddable
// TODO mozno e da ne treba mapped supperclass
@MappedSuperclass
@Getter
//@Setter
public class Street implements ValueObject {

    @Column(name = "address")
    private String street;

    @Column(name = "number")
    private String number;

    //unused
    private Street() {
        this.street = "";
        this.number = "";
    }

    @JsonCreator
    public Street(@NonNull String street, @NonNull String number) {
        this.street = Objects.requireNonNull(street, "street must not be null");
        this.number = Objects.requireNonNull(number, "number must not be null");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Street street1 = (Street) o;
        return street.equals(street1.street) &&
                number.equals(street1.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, number);
    }

    @Override
//    @JsonValue
    public String toString() {
        return "Street{" +
                "street='" + street + '\'' +
                ", number='" + number + '\'' +
                '}';
    }
}
