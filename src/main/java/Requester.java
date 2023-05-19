import LoginInfo.Credentials;
import com.highmobility.autoapi.*;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.CheckControlMessage;
import com.highmobility.autoapi.value.OemTroubleCodeValue;
import com.highmobility.hmkitfleet.HMKitFleet;
import com.highmobility.hmkitfleet.ServiceAccountApiConfiguration;
import com.highmobility.hmkitfleet.model.*;
import com.highmobility.hmkitfleet.network.Response;
import com.highmobility.hmkitfleet.network.TelematicsCommandResponse;
import com.highmobility.hmkitfleet.network.TelematicsResponse;
import kotlinx.serialization.json.Json;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static java.lang.String.format;

/**
 * Copyright <2023> <oleahv>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
public class Requester {


    Credentials credentials = new Credentials();
    ControlMeasure measure = new Odometer(2050, Odometer.Length.KILOMETERS);
    List<ControlMeasure> measures = List.of(measure);

    // Path to directory that stores json files (access tokens)
    String baseURI = "src/main/java/accessTokens";


    // 1HMHLS5MF8GFR2DOE
    // Vin number of the vehicle. Can do asynch auth with a list (confirm)


    // Path to where the accessToken is stored as a .json file
    Path pathAccessToken = Paths.get(baseURI + "/" + credentials.getVin() + "_vehicleAccess.json");

    //final Logger logger = Logger.getLogger("LoggerTest1");
    final Logger logger = LoggerFactory.getLogger(this.getClass());


    // https://github.com/highmobility/hmkit-fleet-consumer/blob/main/hmkit-fleet-consumer/src/main/java/WebServer.java


    HMKitFleet hmkitFleet = HMKitFleet.INSTANCE;
/*

ServiceAccountApiConfiguration configuration = new ServiceAccountApiConfiguration(
    "", // Add Service Account API Key here
    "", // Add Service Account Private Key here
    "eHZobb7vxXQcyCNIdIx2zN9jA1nGIeCVKz24xgZZ6/C/JD8VmbbYVM9zo3SOVaJupg49hihLez4PlvF/pvGai+D8nKUHIb7HjEc5uuhEpBESG02lyi5Q4B5CTaJz3ydI6u8nYANKUS27KsOWVeeoltxy/fpS8DCWH5nP1HLJmn06sVG2b/7F+AeN69cRbxUFPTsCgPoBMXbm",
    "rcU6lKuV2bL1SQVjjjIeLKFMcbEfpRfITmgKfV7LdBg=",
    "", // Add OAuth2 Client ID here
    ""  // Add OAuth2 Client Secret here

    eHZobb7vxXQcyCNIdIx2zN9jA1nGIeCVKz24xgZZ6/C/JD8VmbbYVM9zo3SOVaJupg49hihLez4PlvF/pvGai+D8nKUHIb7HjEc5uuhEpBESG02lyi5Q4B5CTaJz3ydI6u8nYANKUS27KsOWVeeoltxy/fpS8DCWH5nP1HLJmn06sVG2b/7F+AeN69cRbxUFPTsCgPoBMXbm",
    "rcU6lKuV2bL1SQVjjjIeLKFMcbEfpRfITmgKfV7LdBg=
);
HMKitFleet.INSTANCE.setConfiguration(configuration);

 */
    public void InstanceHMKit() {
        // https://docs.high-mobility.com/guides/getting-started/fleet/
        ServiceAccountApiConfiguration configuration = new ServiceAccountApiConfiguration(
                credentials.getServiceAccountAPIkey(),
                credentials.getServiceAccountPrivateKey(),
                credentials.getClientCertificate(),
                credentials.getClientPrivateKey(),
                credentials.getOAuthClientId(),
                credentials.getOAuthClientSecret()

                /*"4641e174-4134-44e0-a7ad-8b7110dddeb0",
                "-----BEGIN PRIVATE KEY-----\nMIGHAgEAMBMGByqGSM49AgEGCCqGSM49AwEHBG0wawIBAQQgE76M1h3vVMe0qX8i\ngVxXT61MzKsXag4EeBp0LT0hnsmhRANCAASX/N2hmY9Y55zEboEGlyVCr/5YZ7BK\\nyeh8zs/MFmTaUWQUfcV1BleFsCmkg6AuAYYHUUHFpf69i8dhOHI2rjSf\n-----END PRIVATE KEY-----",
                "c2JveJwx7g2/p85JCq/pUSLiz/aXJv0Ge4+D8bmi1igcIP82CFkmPkwPK3ZUPcFBZ7Oxs4q7TmuM9RkFVrGvZfHkkWCp1HyydPNzgnjOFRmRaYf5uXqthRLnIoiHA1mUuF/ZZGWvKzahOSLfwErpFzkO6soV1SRL2BtzrUxI1kHK5XmNR/0CBZ7ksK23zG22OsRrObdRTo0W",
                "+MEim4mx4J89ML6+wNkgwR9oaUyTvwDsiMZ3J7RKn2c=",
                "53ea2ef0-8fbc-4b98-b3b7-edbafebe5ca4",
                "GgmBzs2Lo9UKGcnc6ftuAg0AzzQ63OYE"*/

        );
        hmkitFleet.setConfiguration(configuration);
        hmkitFleet.setEnvironment(credentials.getEnvironment());
        //HMKitFleet.INSTANCE.setConfiguration(configuration);



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
        Response<ClearanceStatus> response = null;
        try {
            response = hmkitFleet.getClearanceStatus(credentials.getVin()).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

        if (response.getResponse() != null) {
            logger.info(format("getClearanceStatuses response"));
                    System.out.println(response.getResponse());
                    System.out.println("0: " + response.getResponse());
                    System.out.println("1: " + response.getResponse().toString());
                    System.out.println("2: " + response.getResponse().getStatus());
                    System.out.println("3: " + response.getResponse().getStatus().toString().toLowerCase());
                    /*logger.info(format("status: %s:%s",
                            status.getVin(),
                            status.getStatus()));*/


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
    public String FileChecker() {
        String fileContentFromSearch = SearchForFile();
        if (fileContentFromSearch != null) {
            System.out.println("found file without creation");
            return fileContentFromSearch;
        }

        
        //File not found. Creating it
        try {
            Response<VehicleAccess> accessResponse = hmkitFleet.getVehicleAccess(credentials.getVin()).get();
            if (accessResponse.getError() != null) {
                throw new RuntimeException(accessResponse.getError().getDetail());
            }
            //System.out.println(" DDDD::::::::::::" + accessResponse.getResponse().getAccessToken().getAccessToken());


            VehicleAccess serverVehicleAccess = accessResponse.getResponse();
            System.out.println(serverVehicleAccess);


            String encoded = Json.Default.encodeToString(VehicleAccess.Companion.serializer(), serverVehicleAccess);

            //TODO: store securely with password
            Files.write(pathAccessToken, encoded.getBytes(), StandardOpenOption.CREATE);


        } catch (InterruptedException | ExecutionException | IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }
    
    public String SearchForFile() {
        String expectedFileContent = null;


        try {
            // Reads the content of the file at filepath into the variable (TODO: make it safe)
            expectedFileContent = new String(Files.readAllBytes(Path.of("src/main/java/accessTokens/"  + credentials.getVin() + "_vehicleAccess.json")));
        } catch (IOException e) {
            return null;
        }
        return expectedFileContent;
        /*// Check if vehicle access token is on file, create one if not
        File tokenDirectory = new File(baseURI);
        File[] filesInDirectory = tokenDirectory.listFiles();
        for (int i = 0; i < Objects.requireNonNull(filesInDirectory).length; i++) {
            if (filesInDirectory[i].getName().equals(expectedFileName)) {
                System.out.println("found file");
                return filesInDirectory[i];
            }
        }*/
    }


    public void Telematics(String fileContent) {

        // read from string into VehicleAccess object
        VehicleAccess vehicleAccess = Json.Default.decodeFromString(VehicleAccess.Companion.serializer(), fileContent);


        Command diagnosticsCommand = new Diagnostics.GetState(Diagnostics.PROPERTY_ODOMETER);

        Command maintenanceCommand = new Maintenance.GetState(Maintenance.PROPERTY_NEXT_INSPECTION_DATE, Maintenance.PROPERTY_TIME_TO_NEXT_SERVICE,
                Maintenance.PROPERTY_BRAKE_FLUID_CHANGE_DATE, Maintenance.PROPERTY_TIME_TO_NEXT_OIL_SERVICE,
                Maintenance.PROPERTY_DISTANCE_TO_NEXT_OIL_SERVICE, Maintenance.PROPERTY_DISTANCE_TO_NEXT_SERVICE);



        SendC(diagnosticsCommand, vehicleAccess);
        SendC(maintenanceCommand, vehicleAccess);

        //Command getVehicleSpeed = new Diagnostics.GetState(Diagnostics.PROPERTY_ODOMETER);
       // Command getVehicleSpeed = new Maintenance.GetState(Maintenance.PROPERTY_NEXT_INSPECTION_DATE);



        //Command getVehicleSpeed = new Diagnostics.GetState(Diagnostics.PROPERTY_OEM_TROUBLE_CODE_VALUES);


        //Bytes getVehicleSpeed = new Diagnostics.GetState(Diagnostics.PROPERTY_SPEED);


        //Command getVehicleSpeed = new Diagnostics.GetState();


        /*
        Response<VehicleAccess> vehicleAccessResponse2;
        try {
            vehicleAccessResponse2 = hmkitFleet.getVehicleAccess(credentials.getVin()).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }*/






    }

    public void SendC(Command command, VehicleAccess vehicleAccess) {

        Diagnostics.State diagnostics;
        Maintenance.State maintenance;


        TelematicsResponse telematicsResponse;
        try {
            // Request live data from the car trough High Mobility
            telematicsResponse = hmkitFleet.sendCommand(command, vehicleAccess).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        logger.info(format("Got telematicsResponse response: %s - %s", telematicsResponse.getResponse(), telematicsResponse.getErrors()));

        if (telematicsResponse.getResponse() == null || telematicsResponse.getResponse().getStatus() != TelematicsCommandResponse.Status.OK) {
            throw new RuntimeException("There was an error");
        }

        Command commandFromVehicle = CommandResolver.resolve(telematicsResponse.getResponse().getResponseData());

        // Determine if Diagnostics or Maintenance
        if (commandFromVehicle instanceof Diagnostics.State) {
            diagnostics = (Diagnostics.State) commandFromVehicle;

            // Checks what data responses one was able to retrieve
            if (diagnostics.getOdometer().getValue() != null) {
                // TODO:
                logger.info(format(
                        "Got diagnostics response: %s",
                        diagnostics.getOdometer().getValue().getValue()));

            } else if (diagnostics.getOemTroubleCodeValues() != null) {
                //TODO:
                for (Property<OemTroubleCodeValue> oemTroubleCodeValue : diagnostics.getOemTroubleCodeValues()) {
                    System.out.println(oemTroubleCodeValue.getValue());
            }
            } else if (diagnostics.getCheckControlMessages() != null) {
                for (Property<CheckControlMessage> checkControlMessage : diagnostics.getCheckControlMessages()) {
                    System.out.println(checkControlMessage.getValue().getStatus());
                    System.out.println(checkControlMessage.getValue());
                }

                } else {
                logger.info(format(" > diagnostics.bytes: %s", diagnostics));
            }
        } else if (commandFromVehicle instanceof Maintenance.State) {
            maintenance = (Maintenance.State) commandFromVehicle;

            if (maintenance.getNextInspectionDate().getValue() != null) {
                Date nextInspectionDate = maintenance.getNextInspectionDate().getValue().getTime();

                Calendar timestamp = maintenance.getNextInspectionDate().getTimestamp();
                Long longTimestamp = null;
                if(timestamp != null) {
                    longTimestamp = timestamp.getTimeInMillis();
                }
                // Sends to DB

                logger.info(format(
                        "Got maintenance response: %s",
                        maintenance.getNextInspectionDate().getValue().getTime()));
                System.out.println(maintenance.getNextInspectionDate().getTimestamp().getTime());
                System.out.println(maintenance.getNextInspectionDate().getValue().getTime());

            } else if (maintenance.getTimeToNextService().getValue() != null) {
                System.out.println(maintenance.getTimeToNextService().getValue().getValue());

            }  else if (maintenance.getBrakeFluidChangeDate().getValue() != null) {
                System.out.println(maintenance.getBrakeFluidChangeDate().getValue().getTime());

            } else if (maintenance.getTimeToNextOilService().getValue() != null) {
                System.out.println(maintenance.getTimeToNextOilService().getValue().getValue());

            } else if (maintenance.getDistanceToNextOilService().getValue() != null) {
                System.out.println(maintenance.getDistanceToNextOilService().getValue().getValue());

            } else if (maintenance.getDistanceToNextService().getValue() != null) {
                System.out.println(maintenance.getDistanceToNextService().getValue().getValue());

            }
        }



        if (maintenance.getNextInspectionDate().getValue() != null) {
                logger.info(format(
                        "Got maintenance response: %s",
                        maintenance.getNextInspectionDate().getValue().getTime()));
                System.out.println(maintenance.getNextInspectionDate().getTimestamp().getTime());
                System.out.println(maintenance.getNextInspectionDate().getValue().getTime());
            }
        } else if (commandFromVehicle instanceof FailureMessage.State) {
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