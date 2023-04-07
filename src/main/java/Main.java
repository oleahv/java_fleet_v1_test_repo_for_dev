import static java.lang.System.exit;

public class Main {
    public static void main(String[] args) {
        UserActions userActions = new UserActions();
        boolean stopLoopFlag = false;
        while(!stopLoopFlag) {
            stopLoopFlag = userActions.DetermineActionBasedOnUserInputFromTerminal();
        }
        // Used for exiting the application, as there seems to be threads running from hmkit
        exit(0);


       /* System.out.println("Hello world!");
        Requester requester = new Requester();
        HandlerJWT handlerJWT = new HandlerJWT();
        //handlerJWT.CreateJWT();
        requester.Test1Func();
        System.out.println("T1, done");
        String res2 = requester.Test2Func();
        System.out.println(res2); */
    }
}