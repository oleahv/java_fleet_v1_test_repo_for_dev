import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UserActions {

    public int ReadUserInputFromTerminal() {
        String inputFromUserString = null;
        int inputFromUserInt = -1;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("""

                 Please input value 1-4 for actions.\s
                 0: Exit program\s
                 1: Auth\s
                 2: Get\s
                 3: Post\s
                 4: Delete\
                """);

        try {
            inputFromUserString = bufferedReader.readLine();
            inputFromUserInt = Integer.parseInt(inputFromUserString);
        } catch (IOException | NumberFormatException e) {
            // nothing
            System.out.println("Input in not a valid option");
        }

        return inputFromUserInt;
    }

    public boolean DetermineActionBasedOnUserInputFromTerminal() {
        int inputValue = ReadUserInputFromTerminal();
        switch(inputValue) {
            case 0:
                System.out.println("0: Exiting program");
                return true;
            case 1:
                System.out.println("1: Auth");
                DoCase1();
                break;
            case 2:
                System.out.println("2: Get");
                break;
            case 3:
                System.out.println("3: Post");
                break;
            case 4:
                System.out.println("4: Delete");
                break;
            default:
                System.out.println("Input error. Please only use single digit number from 1-4");
                break;
        }
        return false;
    }

    public void DoCase1() {
        System.out.println("Hello world!");
        Requester requester = new Requester();
        //HandlerJWT handlerJWT = new HandlerJWT();
        //handlerJWT.CreateJWT();
        requester.Test1Func();
        System.out.println("T1, done");
        String res2 = requester.Test2Func();
        System.out.println(res2);
    }
}

