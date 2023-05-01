import server.WebServer;

import static java.lang.System.exit;

public class Main {
    public static void main(String[] args) {

        boolean RunWebServerFlag = false;
        if (!RunWebServerFlag) {

            WebServer webServer = new WebServer();
            webServer.RunWebServer();
            UserActions userActions = new UserActions();
            Requester requester = new Requester();
            requester.InstanceHMKit();
            // Read filenumber and brand from a file.

            // TODO: Read vin and brand from csv file (NOTE: odometer for sandbox and mercedes-benz)

            // Makes sure that the application does not finish prematurely
            boolean stopLoopFlag = false;
            while (!stopLoopFlag) {
                stopLoopFlag = userActions.DetermineActionBasedOnUserInputFromTerminal();
            }
        } else {

        }

        // Used for exiting the application, as there seems to be threads running from hmkit
        exit(0);


    }
}