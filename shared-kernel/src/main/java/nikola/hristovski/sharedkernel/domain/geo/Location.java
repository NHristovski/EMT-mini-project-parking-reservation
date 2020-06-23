package nikola.hristovski.sharedkernel.domain.geo;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.Setter;
import nikola.hristovski.sharedkernel.domain.base.ValueObject;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;

@Embeddable
@MappedSuperclass
@Getter
//@Setter
public class Location implements ValueObject {

    @Embedded
    private Address address;

    @Embedded
    private Coordinates coordinates;

    public Location(Address address, Coordinates coordinates) {
        this.address = address;
        this.coordinates = coordinates;
    }

    public Location(){
        this.address = null;
        this.coordinates = null;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void changeAddress(Address newAddress) {
        this.address = newAddress;
        Coordinates newCoordinates = getCoordinatesForAddress(newAddress);
        this.coordinates = newCoordinates;
    }

    public void changeCoordinates(Coordinates newCoordinates) {
        this.coordinates = newCoordinates;
        Address newAddress = getAddressForCoordinates(newCoordinates);
        this.address = newAddress;
    }

    private Coordinates getCoordinatesForAddress(Address address) {
        // dummy implementation;
        return new Coordinates(1.1d, 2.2d);
    }

    private Address getAddressForCoordinates(Coordinates coordinates) {
        // dummy implementation;
        return new Address(new Street("dummy street", "5a"), new CityName("Skopje"));
    }

//    @JsonValue
    @Override
    public String toString() {
        return "Location{" +
                "address=" + address +
                ", coordinates=" + coordinates +
                '}';
    }
}
