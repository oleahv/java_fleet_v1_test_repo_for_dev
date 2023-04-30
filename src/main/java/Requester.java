import LoginInfo.Credentials;
import com.highmobility.autoapi.*;
import com.highmobility.hmkitfleet.HMKitFleet;
import com.highmobility.hmkitfleet.ServiceAccountApiConfiguration;
import com.highmobility.hmkitfleet.model.*;
import com.highmobility.hmkitfleet.network.Response;
import com.highmobility.hmkitfleet.network.TelematicsCommandResponse;
import com.highmobility.hmkitfleet.network.TelematicsResponse;
import kotlinx.serialization.json.Json;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

import static java.lang.String.format;


public class Requester {

    Credentials credentials = new Credentials();
    ControlMeasure measure = new Odometer(110000, Odometer.Length.KILOMETERS);
    List<ControlMeasure> measures = List.of(measure);

    // Path to directory that stores json files (access tokens)
    String baseURI = "src/main/java/accessTokens";


    // 1HMHLS5MF8GFR2DOE
    // Vin number of the vehicle. Can do asynch auth with a list (confirm)


    // Path to where the accessToken is stored as a .json file
    Path pathAccessToken = Paths.get(baseURI + "/" + credentials.getVin() + "_vehicleAccess.json");

    Logger logger = Logger.getLogger("LoggerTest1");


    // https://github.com/highmobility/hmkit-fleet-consumer/blob/main/hmkit-fleet-consumer/src/main/java/WebServer.java


    HMKitFleet hmkitFleet = HMKitFleet.INSTANCE;

    public void InstanceHMKit() {
        // https://docs.high-mobility.com/guides/getting-started/fleet/
        ServiceAccountApiConfiguration configuration = new ServiceAccountApiConfiguration(
                credentials.getServiceAccountAPIkey(),
                credentials.getServiceAccountPrivateKey(),
                credentials.getClientCertificate(),
                credentials.getClientPrivateKey(),
                credentials.getOauthClientId(),
                credentials.getoAuthClientSecret()

                /*"4641e174-4134-44e0-a7ad-8b7110dddeb0",
                "-----BEGIN PRIVATE KEY-----\nMIGHAgEAMBMGByqGSM49AgEGCCqGSM49AwEHBG0wawIBAQQgE76M1h3vVMe0qX8i\ngVxXT61MzKsXag4EeBp0LT0hnsmhRANCAASX/N2hmY9Y55zEboEGlyVCr/5YZ7BK\\nyeh8zs/MFmTaUWQUfcV1BleFsCmkg6AuAYYHUUHFpf69i8dhOHI2rjSf\n-----END PRIVATE KEY-----",
                "c2JveJwx7g2/p85JCq/pUSLiz/aXJv0Ge4+D8bmi1igcIP82CFkmPkwPK3ZUPcFBZ7Oxs4q7TmuM9RkFVrGvZfHkkWCp1HyydPNzgnjOFRmRaYf5uXqthRLnIoiHA1mUuF/ZZGWvKzahOSLfwErpFzkO6soV1SRL2BtzrUxI1kHK5XmNR/0CBZ7ksK23zG22OsRrObdRTo0W",
                "+MEim4mx4J89ML6+wNkgwR9oaUyTvwDsiMZ3J7RKn2c=",
                "53ea2ef0-8fbc-4b98-b3b7-edbafebe5ca4",
                "GgmBzs2Lo9UKGcnc6ftuAg0AzzQ63OYE"*/

        );
        hmkitFleet.setEnvironment(credentials.getEnvironment());
        HMKitFleet.INSTANCE.setConfiguration(configuration);

    }



    // Authenticate the user to HM with app. Sends a request to link the car with the app on HM. (Note, not instant)
    // (might be intended form High Mobility)
    public void ClientCertificate() {

        // https://docs.high-mobility.com/guides/getting-started/fleet/


        Response<RequestClearanceResponse> response =
                null;
        // LEGG TIL MERZEDES-BENZ VERDIER SOM TRENGS. Se nederst i kommentert ut kode.
        try {
            response = hmkitFleet.requestClearance(credentials.getVin(), credentials.getCarBrand(), measures).get();
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
        } else {
            logger.info(format("requestClearances error: %s", response.getError().getTitle()));
        }

    }

    // Checks the status of clearance based on vin number
    public String CheckClearanceStatus() {
        Response<List<ClearanceStatus>> response = null;
        try {
            response = hmkitFleet.getClearanceStatuses().get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

        if (response.getResponse() != null) {
            logger.info(format("getClearanceStatuses response"));
            for (ClearanceStatus status : response.getResponse()) {
                if (status.getVin().equals(credentials.getVin())) {
                    logger.info(format("status: %s:%s",
                            status.getVin(),
                            status.getStatus()));
                }
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


    public void GetAccessToken() {
        String apiKey = "4641e174-4134-44e0-a7ad-8b7110dddeb0";
        String apiSecret = "-----BEGIN PRIVATE KEY-----\nMIGHAgEAMBMGByqGSM49AgEGCCqGSM49AwEHBG0wawIBAQQg+MEim4mx4J89ML6+\nwNkgwR9oaUyTvwDsiMZ3J7RKn2ehRANCAASPg/G5otYoHCD/NghZJj5MDyt2VD3B\nQWezsbOKu05rjPUZBVaxr2Xx5JFgqdR8snTzc4J4zhUZkWmH+bl6rYUS\n-----END PRIVATE KEY-----\n\n";


        String encodedCredentials = Base64.getEncoder()
                .encodeToString((apiKey + ":" + apiSecret).getBytes(StandardCharsets.UTF_8));

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://fleet.high-mobility.com/v1/oauth2/token"))
                .header("Authorization", "Basic " + encodedCredentials)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString("grant_type=client_credentials"))
                .build();

        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        String accessToken = response.body();

        System.out.println(accessToken);

    }

    public void DeleteClearance() {
        Response<RequestClearanceResponse> response = null;
        try {
            response = hmkitFleet.deleteClearance(credentials.getVin()).get();
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

    public boolean GetEligibility() {
        Response<EligibilityStatus> response = null;
        try {
            response = hmkitFleet.getEligibility(credentials.getVin(), credentials.getCarBrand()).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

        if (response.getResponse() != null) {
            logger.info(format("getEligibility response: %s", response.getResponse()));
            // If the car is eligible, return true to signal that it should continue with registration
            if (response.getResponse().getEligible()) {
                return true;
            }
        } else {
            logger.info(format("getEligibility error: %s", response.getError().getTitle()));
        }

        return false;
    }

    // Historical
    public void GetClearanceStatusesOfAllCars() {
        Response<List<ClearanceStatus>> response = null;
        try {
            response = hmkitFleet.getClearanceStatuses().get();
        } catch (InterruptedException | ExecutionException e) {
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
        File fileResultFromSearch = SearchForFile();
        if (fileResultFromSearch != null) {
            System.out.println("found file without creation");
            return fileResultFromSearch;
        }

        
        //File not found. Creating it
        try {
            Response<VehicleAccess> accessResponse = hmkitFleet.getVehicleAccess(credentials.getVin()).get();
            if (accessResponse.getError() != null) {
                throw new RuntimeException(accessResponse.getError().getDetail());
            }


            VehicleAccess serverVehicleAccess = accessResponse.getResponse();
            System.out.println(serverVehicleAccess);

            String encoded = Json.Default.encodeToString(VehicleAccess.Companion.serializer(), serverVehicleAccess);

            //TODO: store securely with password
            Files.write(pathAccessToken, encoded.getBytes(), StandardOpenOption.CREATE);


        } catch (InterruptedException | ExecutionException | IOException e) {
            throw new RuntimeException(e);
        }

        
        File file = SearchForFile();
        
        // Response of file was not found, and had to be created.
        return file;
    }
    
    public File SearchForFile() {
        String expectedFileName = credentials.getVin() + "_vehicleAccess.json";
        // Check if vehicle access token is on file, create one if not
        File tokenDirectory = new File(baseURI);
        File[] filesInDirectory = tokenDirectory.listFiles();
        for (int i = 0; i < Objects.requireNonNull(filesInDirectory).length; i++) {
            if (filesInDirectory[i].getName().equals(expectedFileName)) {
                System.out.println("found file");
                return filesInDirectory[i];
            }
        }
        return null;
    }


    public void Telematics(File file) {
        // read from file into VehicleAccess object
        String content = null;
        try {
            content = new String(Files.readAllBytes(Path.of(file.toURI())));


        } catch (IOException e) {
            throw new RuntimeException(e);
        }


       // System.out.println(":::::::::::::" + hmkitFleet.getVehicleAccess(vin));

        VehicleAccess vehicleAccess = Json.Default.decodeFromString(VehicleAccess.Companion.serializer(), content);
        //System.out.println(vehicleAccess);

        Command getVehicleSpeed = new Diagnostics.GetState(Diagnostics.PROPERTY_SPEED);
        //Command getVehicleSpeed = new Diagnostics.GetState();


        TelematicsResponse telematicsResponse;
        try {
            // Sends command to the car (Get request)
            telematicsResponse = hmkitFleet.sendCommand(getVehicleSpeed, vehicleAccess).get();
            //telematicsResponse = hmkitFleet.sendCommand(new Bytes(getVechicleSpeedCommand), vehicleAccess).get();

        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        logger.info(format("Got telematics response: %s - %s", telematicsResponse.getResponse(), telematicsResponse.getErrors()));

        if (telematicsResponse.getResponse() == null || telematicsResponse.getResponse().getStatus() != TelematicsCommandResponse.Status.OK) {
            throw new RuntimeException("There was an error");
        }

        Command commandFromVehicle = CommandResolver.resolve(telematicsResponse.getResponse().getResponseData());
        //int calue_t = CommandResolver.resolve(telematicsResponse.getResponse().getResponseData()).getCommandType();





        if (commandFromVehicle instanceof Diagnostics.State) {
            Diagnostics.State diagnostics = (Diagnostics.State) commandFromVehicle;
            //String stringText = new String(diagnostics.getByteArray());
            System.out.println(diagnostics.getSpeed().getValue());
            if (diagnostics.getSpeed().getValue() != null) {
                logger.info(format(
                        "Got diagnostics response: %s",
                        //diagnostics.getSpeed().getValue()));
                        diagnostics.getSpeed().getValue().getValue()));
            } else {
                logger.info(format(" > diagnostics.bytes: %s", diagnostics));
            }
        }
        else if (commandFromVehicle instanceof FailureMessage.State) {
            FailureMessage.State failure = (FailureMessage.State) commandFromVehicle;
            if (failure.getFailedMessageID().getValue() == Identifier.VEHICLE_STATUS &&
                    failure.getFailedMessageType().getValue() == Type.GET) {
                // The Get Vehicle Status command failed.
                if (failure.getFailureReason().getValue() == FailureMessage.FailureReason.UNAUTHORISED) {
                    // The command failed because the vehicle is not authorized. Try to connect
                    // to vehicle again
                }
            }
        }






    }


}


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