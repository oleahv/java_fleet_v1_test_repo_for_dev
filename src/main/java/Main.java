import server.HttpMethods;


import static java.lang.System.exit;

public class Main {
    public static void main(String[] args) {
        /*server.HttpMethods httpMethods = new HttpMethods();
        httpMethods.CreateServer();*/

        UserActions userActions = new UserActions();
        Requester requester = new Requester();
        requester.InstanceHMKit();
        // Read filenumber and brand from a file.

        // TODO: Read vin and brand from csv file (NOTE: odometer for sandbox and mercedes-benz)

        boolean stopLoopFlag = false;
        while(!stopLoopFlag) {
            stopLoopFlag = userActions.DetermineActionBasedOnUserInputFromTerminal();
        }

       // WebServer webServer = new WebServer(requester.vin);
        // Used for exiting the application, as there seems to be threads running from hmkit
        exit(0);


    }
}