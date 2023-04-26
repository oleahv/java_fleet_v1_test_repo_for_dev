package server;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Base64;
import java.util.concurrent.CompletableFuture;



// This class is based on ChatGPT from openai.com
public class WebServer {


    // Oauth client ID and secret
    private static final String CLIENT_ID = "53ea2ef0-8fbc-4b98-b3b7-edbafebe5ca4";
    private static final String CLIENT_SECRET = "GgmBzs2Lo9UKGcnc6ftuAg0AzzQ63OYE";

    // The URL to High Mobility's authorization endpoint
    private static final String AUTHORIZATION_ENDPOINT = "https://api.high-mobility.com/v1/oauth/authorize";

    // The redirect URI that you registered with High Mobility when you created your client ID and secret
    private static final String REDIRECT_URI = "http://localhost:8080/callback";

    private static final String vin = "1HM2FORNG3EWOG91V";


    public static void main(String[] args) throws Exception {
        // Step 1: Redirect the user to High Mobility's authorization endpoint to request permission for your application to access their resources
        String authorizationUrl = AUTHORIZATION_ENDPOINT + "?client_id=" + CLIENT_ID + "&redirect_uri=" + REDIRECT_URI + "&response_type=code";
        System.out.println("Please visit this URL to authorize your application: " + authorizationUrl);

        // Step 2: Implement web server to handle incoming requests on the redirect URI
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/callback", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                String query = exchange.getRequestURI().getQuery();
                String authorizationCode = query.substring(query.indexOf("=") + 1);

                // Step 3: Exchange authorization code for access token using High Mobility's token endpoint
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create("https://api.high-mobility.com/v1/oauth/token"))
                        .header("Content-Type", "application/x-www-form-urlencoded")
                        .POST(HttpRequest.BodyPublishers.ofString("grant_type=authorization_code&code=" + authorizationCode + "&redirect_uri=" + REDIRECT_URI))
                        .header("Authorization", "Basic " + Base64.getEncoder().encodeToString((CLIENT_ID + ":" + CLIENT_SECRET).getBytes()))
                        .build();

                CompletableFuture<HttpResponse<String>> futureResponse = client.sendAsync(request, BodyHandlers.ofString());
                futureResponse.thenApply(HttpResponse::body)
                        .thenAccept(System.out::println)
                        .join();

                String response = "Authorization code: " + authorizationCode;
                exchange.sendResponseHeaders(200, response.getBytes().length);
                exchange.getResponseBody().write(response.getBytes());
                exchange.close();
            }
        });
        server.start();

        // Step 4: After getting the access token, use it to make requests to the Fleet API
        String accessToken = "";

        try {
            File file = new File("src/main/java/accessTokens/" + vin + "_vehicleAccess.json");
            // TODO: FIX later
            JsonParser parser = new JsonParser();
            JsonObject jsonObject = parser.parse(new FileReader(file)).getAsJsonObject();
            accessToken = jsonObject.get("access_token").getAsString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Example request to get list of vehicles
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.high-mobility.com/v1/fleet/vehicles"))
                .header("Authorization", "Bearer " + accessToken)
                .build();

        CompletableFuture<HttpResponse<String>> futureResponse = client.sendAsync(request, BodyHandlers.ofString());
        futureResponse.thenApply(HttpResponse::body)
                .thenAccept(System.out::println)
                .join();
    }
}
