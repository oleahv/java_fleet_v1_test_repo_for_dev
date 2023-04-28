package LoginInfo;

import com.highmobility.hmkitfleet.HMKitFleet;
import com.highmobility.hmkitfleet.model.Brand;

public class Credentials {

    // Login to


    // Determiner if the application is in production or sandbox
    private final HMKitFleet.Environment environment = HMKitFleet.Environment.SANDBOX;
    private static final String vin = "1HM2FORNG3EWOG91V";
    private static final Brand carBrand = Brand.SANDBOX;

    private static final String serviceAccountAPIkey = "4641e174-4134-44e0-a7ad-8b7110dddeb0";
    private static final String serviceAccountPrivateKey = "-----BEGIN PRIVATE KEY-----\\nMIGHAgEAMBMGByqGSM49AgEGCCqGSM49AwEHBG0wawIBAQQgk7TZBUOiwL5vbj9n\\nr9ezG5BqQckFzSE6YqaArmVZfgChRANCAAT1mGPGkPxnrd1FVYe61CheAWMUh6o9\\nZ/PtLeYaRMROPDqHwPXYGl1HBMv2+FhyKBAHdr3s1EgHXoP5dZppxhnJ\\n-----END PRIVATE KEY-----";
    private static final String clientCertificate = "c2JveJwx7g2/p85JCq/pUSLiz/aXJv0Ge4+D8bmi1igcIP82CFkmPkwPK3ZUPcFBZ7Oxs4q7TmuM9RkFVrGvZfHkkWCp1HyydPNzgnjOFRmRaYf5uXqthRLnIoiHA1mUuF/ZZGWvKzahOSLfwErpFzkO6soV1SRL2BtzrUxI1kHK5XmNR/0CBZ7ksK23zG22OsRrObdRTo0W";
    private static final String clientPrivateKey = "+MEim4mx4J89ML6+wNkgwR9oaUyTvwDsiMZ3J7RKn2c=";
    private static final String oauthClientId = "53ea2ef0-8fbc-4b98-b3b7-edbafebe5ca4";
    private static final String oAuthClientSecret = "GgmBzs2Lo9UKGcnc6ftuAg0AzzQ63OYE";

    // URI that the user can enter into a browser to start the Oauth flow
    private final String REDIRECT_URI = "http://localhost:8080/callback";


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

    public String getOauthClientId() {
        return oauthClientId;
    }

    public String getoAuthClientSecret() {
        return oAuthClientSecret;
    }

    public String getREDIRECT_URI() {
        return REDIRECT_URI;
    }

    public String getAUTHORIZATION_ENDPOINT() {
        return AUTHORIZATION_ENDPOINT;
    }

}
