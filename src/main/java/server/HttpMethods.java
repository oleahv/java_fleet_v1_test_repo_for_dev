package server;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpMethods {

    private static final String simulatorURL = "https://sandbox.rest-api.high-mobility.com/v5";
    private static final String productionURL = "https://api.high-mobility.com/v5";

    private static final int port = 8080;
    HttpServer httpServer;

    public void GetRequest() {
        //something
    }

    // https://www.codeproject.com/Tips/1040097/Create-a-Simple-Web-Server-in-Java-HTTP-Server
    public void CreateServer() {
        try {
            httpServer = HttpServer.create(new InetSocketAddress(port), 0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Server created. Starting at port " + port);
  /*      httpServer.createContext("/", new RootHandler());
        httpServer.createContext("/echoHeader", new EchoHeaderHandler());
        httpServer.createContext("/echoGet", new EchoGetHandler());
        httpServer.createContext("/echoPost", new EchoPostHandler());
        httpServer.setExecutor(null);
        httpServer.start();
*/


    }
}
