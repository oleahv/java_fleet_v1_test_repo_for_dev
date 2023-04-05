public class Main {
    public static void main(String[] args) {
        UserActions userActions = new UserActions();
        boolean stopLoopFlag = false;
        while(!stopLoopFlag) {
            stopLoopFlag = userActions.DetermineActionBasedOnUserInputFromTerminal();
        }


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