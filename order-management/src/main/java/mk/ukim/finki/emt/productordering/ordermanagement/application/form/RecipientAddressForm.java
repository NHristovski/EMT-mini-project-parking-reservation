package mk.ukim.finki.emt.productordering.ordermanagement.application.form;

import lombok.Data;
import mk.ukim.finki.emt.productordering.sharedkernel.domain.geo.CityName;
import mk.ukim.finki.emt.productordering.sharedkernel.domain.geo.Country;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class RecipientAddressForm implements Serializable {

    @NotEmpty
    private String name;
    @NotEmpty
    private String address;
    @NotNull
    private CityName city;
    @NotNull
    private Country country;


}
