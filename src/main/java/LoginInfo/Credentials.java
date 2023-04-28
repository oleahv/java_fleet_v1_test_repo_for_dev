package LoginInfo;

import com.highmobility.hmkitfleet.model.Brand;

public class Credentials {

    // Login to


    private final String vin = "1HM2FORNG3EWOG91V";
    private final Brand carBrand = Brand.SANDBOX;


    public String getVin() {
        return vin;
    }

    public Brand getCarBrand() {
        return carBrand;
    }
}
