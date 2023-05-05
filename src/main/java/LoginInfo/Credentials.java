package LoginInfo;

import com.highmobility.hmkitfleet.HMKitFleet;
import com.highmobility.hmkitfleet.model.Brand;

public class Credentials {

    // Login to


    // Determiner if the application is in production or sandbox
    private final HMKitFleet.Environment environment = HMKitFleet.Environment.SANDBOX;
    
    // TODO: covert to work with a list
    private static final String vin = "1HMR47JWD7CDOW62D";

    // TODO: perform a check wich brand it is
    private static final Brand carBrand = Brand.SANDBOX;

    // used as an iss for the requests
    private static final String serviceAccountAPIkey =  "";

    // Be careful when pasting the key. Can cause issues when "\" is involved
    private static final String serviceAccountPrivateKey = "";
    private static final String clientCertificate = "";
    private static final String clientPrivateKey = "";
    private static final String oAuthClientId = "";
    private static final String oAuthClientSecret = "";

    // URI that the user can enter into a browser to start the Oauth flow
    private final String REDIRECT_URI = "http://localhost:8080/callback";


    // https://sandbox.api.high-mobility.com/v1

    // URI to connect to. Sandbox is for the testing environment
    private String DeterminEnvironment() {
        if (environment == HMKitFleet.Environment.SANDBOX) {
            //sandbox
            return "https://sandbox.owner-panel.high-mobility.com/oauth/new";
        } else {
            // production
            return "https://api.high-mobility.com/v1/oauth/authorize";
        }
    }
    private final String AUTHORIZATION_ENDPOINT = DeterminEnvironment();


    // ---------------- get methods ---------------


    public HMKitFleet.Environment getEnvironment() {
        return environment;
    }
    public String getVin() {
        return vin;
    }

    public Brand getCarBrand() {
        return carBrand;
    }

    public String getServiceAccountAPIkey() {
        return serviceAccountAPIkey;
    }

    public String getServiceAccountPrivateKey() {
        return serviceAccountPrivateKey;
    }

    public String getClientCertificate() {
        return clientCertificate;
    }

    public String getClientPrivateKey() {
        return clientPrivateKey;
    }

    public String getOAuthClientId() {
        return oAuthClientId;
    }

    public String getOAuthClientSecret() {
        return oAuthClientSecret;
    }

    public String getREDIRECT_URI() {
        return REDIRECT_URI;
    }

    public String getAUTHORIZATION_ENDPOINT() {
        return AUTHORIZATION_ENDPOINT;
    }

}
