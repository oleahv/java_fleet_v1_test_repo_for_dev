import com.highmobility.hmkitfleet.model.ClearanceStatus;
import com.highmobility.hmkitfleet.network.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static java.lang.String.format;

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
                 3: Post\s
                 4: Delete\
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
                DoCase1();
                break;
            case 2:
                System.out.println("2: Get clearance status");
                requester.Case2();
                break;
            case 3:
                // NOTE: persistent, so store securely
                System.out.println("3: Get vehicle access");
                requester.Case3();
                break;
            case 4:
                System.out.println("4: Delete");
                requester.Case4();
                break;
            default:
                System.out.println("Input error. Please only use single digit number from 1-4");
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
        requester.Test1Func();
        System.out.println("T1, done");
        String res2 = requester.Test2Func();
        System.out.println(res2);
    }


}

