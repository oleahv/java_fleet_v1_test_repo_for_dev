import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;



public class UserActions {

    Requester requester = new Requester();

    /** bufferedReader.close();  Remember to do it at the correct place*/
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    public int ReadUserInputFromTerminal() {
        String inputFromUserString = null;
        // Could look into having the user spell it out, should be able to avoid issues with wrong format that way.
        int inputFromUserInt = -1;

        // Menu information
        System.out.println("""

                 Please input value 1-4 for actions.\s
                 0: Exit program\s
                 1: Auth\s
                 2: Get clearance status\s
                 3: Get vehicle access (WIP)\s
                 4: Delete car\s
                 5: Get clearance statuses of all cars\s
                 6: Post request. Register car based on vin\s
                 7: Send telematics command (speed)\
                """);

        try {
            inputFromUserString = bufferedReader.readLine();
            inputFromUserInt = Integer.parseInt(inputFromUserString);
        } catch (IOException | NumberFormatException e) {
            throw new RuntimeException(e);
            //System.out.println("Input in not a valid option");
        }
        return inputFromUserInt;
    }


    public boolean DetermineActionBasedOnUserInputFromTerminal() {
        int inputValue = ReadUserInputFromTerminal();
        switch(inputValue) {
            case 0:
                System.out.println("0: Exiting program");
                DoCase0();
                return true;
            case 1:
                System.out.println("1: Auth");
                boolean carIsEligible = requester.GetEligibility();
                if (carIsEligible) {
                    DoCase1();
                } else {
                    System.out.println("The car is not eligible, and can ergo not be added");
                }
                //requester.GetEligibility();
                break;
            case 2:
                System.out.println("2: Get clearance status");
                //String requestResponse  =
                requester.CheckClearanceStatus();
                // Works as a log. TODO: remove when not needed anymore (one everything works as expected)
                //System.out.println(requestResponse);
                break;
            case 3:
                // NOTE: persistent, so store securely
                System.out.println("3: Get vehicle access token (WIP)");
                requester.GetAccessToken();
                break;
            case 4:
                System.out.println("4: Delete car");
                requester.DeleteClearance();
                break;
            case 5:
                System.out.println("5: Get clearance status of all cars");
                requester.GetClearanceStatusesOfAllCars();
                break;
            case 6:
                System.out.println("6: Post request. Register car based on vin");
                requester.FileChecker();
                break;
            case 7:
                System.out.println("Send telematics commands");
                File file;
                file = requester.FileChecker();
                requester.Telematics(file);
            default:
                System.out.println("Input error. Please only use single digit number");
                break;
        }
        return false;
    }

    public void DoCase0() {
        System.out.println("Closing...");
        try {
            bufferedReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void DoCase1() {
        System.out.println("Hello Auth!");
        //HandlerJWT handlerJWT = new HandlerJWT();
        //handlerJWT.CreateJWT();
        requester.ClientCertificate();
        System.out.println("ClientCertificate :::::::::::::::: done");
        //String res2 = requester.CheckClearanceStatus();
        //System.out.println(res2);
    }


}

