import com.highmobility.hmkitfleet.HMKitFleet;
import com.highmobility.hmkitfleet.ServiceAccountApiConfiguration;
import com.highmobility.hmkitfleet.model.*;
import com.highmobility.hmkitfleet.network.Response;
import kotlinx.serialization.json.Json;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;
import static java.lang.String.format;

public class Requester {
    ControlMeasure measure = new Odometer(110000, Odometer.Length.KILOMETERS);
    List<ControlMeasure> measures = List.of(measure);

    // Path to directory that stores json files (access tokens)
    String baseURI = "src/main/java/accessTokens";

    // Vin number of the vehicle. Can do asynch auth with a list (confirm)
    String vin = "1HMHLS5MF8GFR2DOE";
    Logger logger = Logger.getLogger("LoggerTest1");


   // https://github.com/highmobility/hmkit-fleet-consumer/blob/main/hmkit-fleet-consumer/src/main/java/WebServer.java

    HMKitFleet hmkitFleet = HMKitFleet.INSTANCE;


    // Authenticate the user to HM with app. Sends a request to link the car with the app on HM. (Note, not instant)
    // (might be intended form High Mobility)
    public void ClientCertificate(){

        // https://docs.high-mobility.com/guides/getting-started/fleet/
        ServiceAccountApiConfiguration configuration = new ServiceAccountApiConfiguration(
                "4641e174-4134-44e0-a7ad-8b7110dddeb0",
                "-----BEGIN PRIVATE KEY-----\nMIGHAgEAMBMGByqGSM49AgEGCCqGSM49AwEHBG0wawIBAQQgE76M1h3vVMe0qX8i\ngVxXT61MzKsXag4EeBp0LT0hnsmhRANCAASX/N2hmY9Y55zEboEGlyVCr/5YZ7BK\\nyeh8zs/MFmTaUWQUfcV1BleFsCmkg6AuAYYHUUHFpf69i8dhOHI2rjSf\n-----END PRIVATE KEY-----",
                "c2JveJwx7g2/p85JCq/pUSLiz/aXJv0Ge4+D8bmi1igcIP82CFkmPkwPK3ZUPcFBZ7Oxs4q7TmuM9RkFVrGvZfHkkWCp1HyydPNzgnjOFRmRaYf5uXqthRLnIoiHA1mUuF/ZZGWvKzahOSLfwErpFzkO6soV1SRL2BtzrUxI1kHK5XmNR/0CBZ7ksK23zG22OsRrObdRTo0W",
                "+MEim4mx4J89ML6+wNkgwR9oaUyTvwDsiMZ3J7RKn2c=",
                "53ea2ef0-8fbc-4b98-b3b7-edbafebe5ca4",
                "GgmBzs2Lo9UKGcnc6ftuAg0AzzQ63OYE"
        );
        hmkitFleet.setEnvironment(HMKitFleet.Environment.SANDBOX);
        HMKitFleet.INSTANCE.setConfiguration(configuration);

        Response<RequestClearanceResponse> response =
                null;
        // LEGG TIL MERZEDES-BENZ VERDIER SOM TRENGS. Se nederst i kommentert ut kode.
        try {
            response = hmkitFleet.requestClearance(vin, Brand.SANDBOX, measures).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

        if (response.getResponse() != null) {
            RequestClearanceResponse requestClearanceResponse = response.getResponse();

            if (requestClearanceResponse.getStatus() == ClearanceStatus.Status.ERROR) {
                // description property is filled when the status is ERROR

                // WARNING, changed to .info instead of .error
                logger.info(format("Request clearance error: %s", requestClearanceResponse.getDescription()));
            } else {
                logger.info(format("requestClearances response: %s", requestClearanceResponse));
            }
        }
        else {
            logger.info(format("requestClearances error: %s", response.getError().getTitle()));
        }

    }

    // Checks the status of clearance based on vin number
    public String CheckClearanceStatus(){
        Response<List<ClearanceStatus>> response = null;
        try {
            response = hmkitFleet.getClearanceStatuses().get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

        if (response.getResponse() != null) {
            logger.info(format("getClearanceStatuses response"));
            for (ClearanceStatus status : response.getResponse()) {
                logger.info(format("status: %s:%s",
                        status.getVin(),
                        status.getStatus()));
            }
        } else {
            logger.info(format("getClearanceStatuses error: %s", response.getError().getTitle()));
        }

        return response.getResponse().toString();

    }

    /*public void Case2(){
        Response<List<ClearanceStatus>> response = null;
        try {
            response = hmkitFleet.getClearanceStatuses().get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

        if (response.getResponse() != null) {
            logger.info(format("getClearanceStatuses response"));
            for (ClearanceStatus status : response.getResponse()) {
                logger.info(format("status: %s:%s",
                        status.getVin(),
                        status.getStatus()));
            }
        } else {
            logger.info(format("getClearanceStatuses error: %s", response.getError().getTitle()));
        }
    }
    */


    public void Case3(){



    }
    public void DeleteClearance(){
        Response<RequestClearanceResponse> response = null;
        try {
            response = hmkitFleet.deleteClearance(vin).get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        if (response.getError() != null) {
                logger.info(format("deleteClearance error: %s", response.getError().getDetail()));
            } else {
                logger.info("deleteClearance success %s");
            }

    }

    public void GetEligibility(){
        Response<EligibilityStatus> response = null;
        try {
            response = hmkitFleet.getEligibility(vin, Brand.SANDBOX).get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

        if (response.getResponse() != null) {
            logger.info(format("getEligibility response: %s", response.getResponse()));
        } else {
            logger.info(format("getEligibility error: %s", response.getError().getTitle()));
        }

    }

    // Historical
    public void GetClearanceStatusesOfAllCars() {
        Response<List<ClearanceStatus>> response = null;
        try {
            response = hmkitFleet.getClearanceStatuses().get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

        if (response.getResponse() != null) {
            logger.info("getClearanceStatuses response");
            for (ClearanceStatus status : response.getResponse()) {
                logger.info(format("status: %s:%s",
                        status.getVin(),
                        status.getStatus()));
            }
        } else {
            logger.info(format("getClearanceStatuses error: %s", response.getError().getTitle()));
        }
    }


    // Checks if the file exists. Creates it if it does not
    //https://www.webucator.com/article/how-to-display-the-contents-of-a-directory-in-java/
    public File FileChecker() {
        String expectedFileName = vin + ".json";

        // Check if vehicle access token is on file, create one if not
        //Path filePath = Path.of( baseURI + vin + ".json");
        File tokenDirectory = new File(baseURI);

        File[] filesInDirectory = tokenDirectory.listFiles();
        for (int i = 0; i < Objects.requireNonNull(filesInDirectory).length; i++) {
            if (filesInDirectory[i].getName().equals(expectedFileName)) {
                System.out.println("found file");
                return filesInDirectory[i];
            }
        }

        //File not found. Creating it
        try {
            Response<VehicleAccess> accessResponse = hmkitFleet.getVehicleAccess(vin).get();
            VehicleAccess serverVehicleAccess = accessResponse.getResponse();
            System.out.println(serverVehicleAccess);

            /*
            String encoded = Json.Default.encodeToString(VehicleAccess.Companion.serializer(), serverVehicleAccess);

            // TODO: store securely
            Files.write(baseURI, encoded.getBytes(), StandardOpenOption.CREATE);*/

        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }


        //File not found. Creating it
        // Create file

        // Just to avoid error for now. DOES NOT EXIST
        File file = new File("TEST");
        return file;
    }

    public void Case6() {
        File file;
        String content = null;
        file = FileChecker();
        /*
        Path filePath = Path.of(file.toURI());
        System.out.println(filePath);
        try {
            content = new String(Files.readAllBytes(filePath));
            //System.out.println(content);
        } catch (IOException e) {
            //throw new RuntimeException(e);
        }
        System.out.println(content);
  */
  /*      String encoded = Json.Default.encodeToString(VehicleAccess.Companion.serializer(), content);


        // TODO: store securely
        Files.write(baseURI, encoded.getBytes(), StandardOpenOption.CREATE);
*/

    }

 /* if (!file.isFile()) {
            System.out.println("File does not exist");
            try {
                Response<VehicleAccess> accessResponse = hmkitFleet.getVehicleAccess(vin).get();
                VehicleAccess serverVehicleAccess = accessResponse.getResponse();

            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
            System.out.println(file);

        } else {
            System.out.println("File exists");
        }*/

}
  /*
    // for mercedes benze
    ControlMeasure measure = new Odometer(110000, Odometer.Length.KILOMETERS);
List<ControlMeasure> measures = List.of(measure);

Response<RequestClearanceResponse> response =
        hmkitFleet.requestClearance(vin, Brand.MERCEDES_BENZ, measures).get();

     */

 /*     ServiceAccountApiConfiguration configuration = new ServiceAccountApiConfiguration(
            "e6396b08-ca81-4aa0-be1c-c395f1a27e77", // Add Service Account API Key here
            "-----BEGIN PRIVATE KEY-----\nMIGHAgEAMBMGByqGSM49AgEGCCqGSM49AwEHBG0wawIBAQQgzlCnmkVsYaZVgS6x\nnRNeRH67i8PS0pCsbUftvG+2thGhRANCAASQQRzR2SA441J9v2oynG5dZF8NYGO4\nMQuDdezfzgefyUFIkO5bsbeRRG0hZbLnSE5G+WwoDVBvFppNEWfzNDK2\n-----END PRIVATE KEY-----", // Add Service Account Private Key here
            "",
            "",
            "53ea2ef0-8fbc-4b98-b3b7-edbafebe5ca4", // Add OAuth2 Client ID here
            "GgmBzs2Lo9UKGcnc6ftuAg0AzzQ63OYE"  // Add OAuth2 Client Secret here
            );
    HMKitFleet.INSTANCE.setConfiguration(configuration);
    */


/*
Path path = Paths.get("vehicleAccess.json");
        System.out.println(path);

        // use the stored VehicleAccess if it exists
        Optional<VehicleAccess> storedVehicleAccess = vehicleAccessStore.read(vin);
        if (storedVehicleAccess.isPresent()) {
            return storedVehicleAccess.get();
        }

        // download VehicleAccess if it does not exist
        Response<VehicleAccess> accessResponse = hmkitFleet.getVehicleAccess(vin).get();
        if (accessResponse.getError() != null) {
            throw new RuntimeException(accessResponse.getError().getDetail());
        }


        // store the downloaded vehicle access
        VehicleAccess serverVehicleAccess = accessResponse.getResponse();
        vehicleAccessStore.store(serverVehicleAccess);

        return serverVehicleAccess;

 */