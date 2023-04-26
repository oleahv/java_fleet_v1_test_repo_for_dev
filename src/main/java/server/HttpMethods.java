package server;

import com.sun.net.httpserver.HttpPrincipal;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpMethods {

    private static final String simulatorURL = "https://sandbox.rest-api.high-mobility.com/v5";
    private static final String APIbaseURL = "https://sandbox.api.high-mobility.com";

    private static final String StringWebhookTest = "https://webhook.site/44d9be78-bddc-4865-bd27-42caf8db6299";
    private static final String productionURL = "https://api.high-mobility.com/v5";

    private static final int port = 8080;
    HttpServer httpServer;

    public void GetRequest() {
        //something
    }

    // https://www.baeldung.com/java-httpclient-post Øverste halvdel
    public void CreateServer() {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(APIbaseURL+ "/v1/auth_tokens"))
                //.uri(URI.create(StringWebhookTest))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response;
        try {

            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Sent response");

        if (response.statusCode() == HttpURLConnection.HTTP_OK) {
            System.out.println("Code ok");
        }



        // https://www.codeproject.com/Tips/1040097/Create-a-Simple-Web-Server-in-Java-HTTP-Server
        /*try {
            httpServer = HttpServer.create(new InetSocketAddress(port), 0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Server created. Starting at port " + port);
        httpServer.createContext("/", new RootHandler());
        httpServer.createContext("/echoHeader", new EchoHeaderHandler());
        httpServer.createContext("/echoGet", new EchoGetHandler());
        httpServer.createContext("/echoPost", new EchoPostHandler());
        httpServer.setExecutor(null);
        httpServer.start();*/



    }
}
