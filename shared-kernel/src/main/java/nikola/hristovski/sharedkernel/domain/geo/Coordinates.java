package nikola.hristovski.sharedkernel.domain.geo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.Setter;
import nikola.hristovski.sharedkernel.domain.base.ValueObject;
import org.springframework.lang.NonNull;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

@Embeddable
//TODO maybe remove mapped
@MappedSuperclass
@Getter
//@Setter
public class Coordinates implements ValueObject {
    private final Double x;
    private final Double y;

    //unused
    public Coordinates() {
        this.x = 0d;
        this.y = 0d;
    }

    @JsonCreator
    public Coordinates(@NonNull Double x, @NonNull Double y) {
        this.x = Objects.requireNonNull(x, "street must not be null");
        this.y = Objects.requireNonNull(y, "number must not be null");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return x.equals(that.x) &&
                y.equals(that.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
//    @JsonValue
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
